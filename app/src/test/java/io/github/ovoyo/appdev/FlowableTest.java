package io.github.ovoyo.appdev;


import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;

public class FlowableTest {

    private static final String TAG = "FlowableTest";

    public static void main(String[] args) {
        testFlowable();
    }

    private static void testFlowable(){

        Flowable
                .create((FlowableOnSubscribe<String>) emitter -> {

                    LogUtil.log(TAG, "A");
                    emitter.onNext("A");

                    LogUtil.log(TAG, "B");
                    emitter.onNext("B");

                    LogUtil.log(TAG, "C");
                    emitter.onNext("C");

//                    for (int i = 0; i < 127; i++) {
//                        emitter.onNext(String.valueOf(i));
//                    }

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
