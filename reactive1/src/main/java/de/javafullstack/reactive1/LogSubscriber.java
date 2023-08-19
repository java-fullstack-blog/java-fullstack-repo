package de.javafullstack.reactive1;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class LogSubscriber<T> implements Subscriber<T> {

    private static final Logger log = LoggerFactory.getLogger(LogSubscriber.class);

    
    @Override
    public void onSubscribe(final Subscription s) {
        log.info("onSubscribe");
        // Erst durch den Aufruf der request() Methode wird die Verarbeitung gestartet
        s.request(Integer.MAX_VALUE);
      }

    @Override
    public void onNext(final T t) {
        log.info("onNext");
    }

    @Override
    public void onError(final Throwable t) {
        log.info("onError");
        onComplete();
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
