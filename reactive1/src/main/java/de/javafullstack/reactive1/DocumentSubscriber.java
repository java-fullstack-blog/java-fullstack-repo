package de.javafullstack.reactive1;

import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentSubscriber  implements Subscriber<Document> {

    private static final Logger log = LoggerFactory.getLogger(DocumentSubscriber.class);

    @Override
    public void onComplete() {
        log.info("DocumentSubscriber completed");
    }

    @Override
    public void onError(Throwable arg0) {
        log.info("Fehler aufgetreten");
    }

    @Override
    public void onNext(Document d) {
        log.info(d.toJson());
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Integer.MAX_VALUE);
    }
    
}
