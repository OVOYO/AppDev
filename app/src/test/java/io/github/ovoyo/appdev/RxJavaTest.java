package io.github.ovoyo.appdev;


import org.junit.Test;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTest {

    private static final String TAG = "RxJavaTest";

    Disposable disposable;

    @Test
    public void test1() {

        Observable.create((ObservableOnSubscribe<String>) emitter -> {

            LogUtil.log(TAG, "Observable thread id: " + Thread.currentThread().getName());

            emitter.onNext("Hi !");
            LogUtil.log(TAG, "Hi !");

            emitter.onNext("RxJava");
            LogUtil.log(TAG, "RxJava");

            emitter.onNext("Yeah !");
            LogUtil.log(TAG, "Yeah !");

            emitter.onComplete();
            LogUtil.log(TAG, "onComplete in emitter");
        })

                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.log(TAG, "onSubscribe: ");
                        disposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.log(TAG, "onNext: " + s);

                        LogUtil.log(TAG, "Observer thread id: " + Thread.currentThread().getName());

                        if (s.equals("RxJava") && disposable != null) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.log(TAG, "onError: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.log(TAG, "onComplete: ");
                    }
                });

    }

    /**
     * Map 对上游发送的事件应用一个函数，使之转换成任意类型
     */
    @Test
    public void testMap() {
        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                })
                .map(integer -> "get number " + integer.toString())
                .subscribe(s -> LogUtil.log(TAG, s));
    }

    /**
     * FlatMap 将上游发出的每个事件重新组装成一个 Observable ，然后发送每个 Observable 中的事件，
     * 这些事件并不是严格的按照顺寻发送，若需要严格顺序使用 ConcatMap
     */
    @Test
    public void testFlatMap() {
        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                })
                .flatMap(integer -> {
                    List<String> stringList = new ArrayList<>();
                    for (int i = 0; i < integer; i++) {
                        stringList.add(integer.toString() + " : " + i);
                    }
                    return Observable.fromIterable(stringList);
                })
                .subscribe(s -> {
                    LogUtil.log(TAG, s);
                });

    }

    /**
     * Zip 将多个 Observable 发送的事件通过一个函数严格按照顺序合并，并且以最短事件个数为合并之后的时间长度
     * 即：Observable1 有事件 1、2、3、4 Observable2 有事件 A、B、C，则合并后发送的事件为 1A、2B、3C
     * Tips: O1、O2 事件的发送顺序是无序的
     */
    @Test
    public void testZip() {
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.log(TAG, "1");
                emitter.onNext(1);
                Thread.sleep(1000);

                LogUtil.log(TAG, "2");
                emitter.onNext(2);
                Thread.sleep(1000);

                LogUtil.log(TAG, "3");
                emitter.onNext(3);
                Thread.sleep(1000);

                LogUtil.log(TAG, "4");
                emitter.onNext(4);
                Thread.sleep(1000);
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtil.log(TAG, "A");
                emitter.onNext("A");
                Thread.sleep(1000);

                LogUtil.log(TAG, "B");
                emitter.onNext("B");
                Thread.sleep(1000);

                LogUtil.log(TAG, "C");
                emitter.onNext("C");
                Thread.sleep(1000);
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(integerObservable, stringObservable, (integer, s) -> integer + s)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.log(TAG, s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Flowable 与 Observable 不同，其通过 Subscription 通知上游下游处理事件的能力，
     * 超出能力之外则按 BackpressureStrategy 处理。默认处理能力 128
     * ERROR 超出则抛出异常
     * BUFFER 修改默认处理能力，与 Observable 类似，可能 OOM
     * DROP 随机抓取处理能力个数的事件处理，并不保证最新
     * LATEST 与 DROP 类似，但保证是最新的事件
     * 类似于主动拉取合适的事件个数来处理。
     */
    @Test
    public void testFlowable() {
        Flowable
                .create((FlowableOnSubscribe<String>) emitter -> {

                    LogUtil.log(TAG, "A");
                    emitter.onNext("A");

                    LogUtil.log(TAG, "B");
                    emitter.onNext("B");

                    LogUtil.log(TAG, "C");
                    emitter.onNext("C");

                    for (int i = 0; i < 127; i++) {
                        emitter.onNext(String.valueOf(i));
                    }

                }, BackpressureStrategy.ERROR)
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        s.request(2L);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.log(TAG, s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
