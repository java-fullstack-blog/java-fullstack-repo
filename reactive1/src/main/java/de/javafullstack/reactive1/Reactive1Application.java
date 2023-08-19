package de.javafullstack.reactive1;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

@SpringBootApplication
public class Reactive1Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Reactive1Application.class);

	private static final String CREATED = "created";

	private static final String REAKTIV = "reaktiv";

	private static final String NUMMER = "nummer";

	Locale locale = new Locale("de", "DE");
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", locale);

	public static void main(String[] args) {
		SpringApplication.run(Reactive1Application.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		// Mit der lokalen Mongo DB verbinden
		MongoClient mongoClient = MongoClients.create();

		// Datenbank "test" öffnen. Wenn die Datenbank nicht existiert, wird sie automatisch erzeugt.
		MongoDatabase database = mongoClient.getDatabase("test");

		// Die Collection "reactive1" öffnen. Wenn die Collection nicht existiert, wird sie automatisch erzeugt.
		MongoCollection<Document> collection = database.getCollection("reactive1");

		// Alle Dokumente aus der Collection löschen
		collection.deleteMany(new Document()).subscribe(new LogSubscriber<>());

		Document document1 = new Document(NUMMER, "1")
				.append(REAKTIV, "true")
				.append(CREATED, sdf.format(new Date()));

		Document document2 = new Document(NUMMER, "2")
				.append(REAKTIV, "true")
				.append(CREATED, sdf.format(new Date()));

		// ein einzelnes Dokument einfügen
		collection.insertOne(document1).subscribe(new LogSubscriber<>());
		
		collection.insertOne(document2).subscribe(new LogSubscriber<>());
		
		Document document3 = new Document(NUMMER, "3")
				.append(REAKTIV, "true")
				.append(CREATED, sdf.format(new Date()));

		Document document4 = new Document(NUMMER, "4")
				.append(REAKTIV, "true")
				.append(CREATED, sdf.format(new Date()));

		// Mehrere Dokumente einfügen
		collection.insertMany(Arrays.asList(document3, document4)).subscribe(new LogSubscriber<>());
		
		// Ein Dokument updaten
		collection.updateOne(eq(NUMMER, 4), set(NUMMER, 44))
    		.subscribe(new LogSubscriber<>());
		
		// Mehrere Dokumente updaten
		collection.updateMany(lte(NUMMER, 3), inc(NUMMER, 1))
    		.subscribe(new LogSubscriber<>());
		
	    // Ein Dokument löschen
		collection.deleteOne(eq(NUMMER, 44))
    		.subscribe(new LogSubscriber<>());
		
		// Mehrere Dokumente löschen
		collection.deleteMany(lte(NUMMER, 3))
    		.subscribe(new LogSubscriber<>());
		// Warten bis alle Subscriber fertig sind
		Thread.sleep(2000);
		printCollectionTest(collection);
	}

	void printCollectionTest(MongoCollection<Document> collection) {
		log.info("Lade alle Dokumente und gebe sie aus");
		// alle Dokumente laden
		collection.find().subscribe(new DocumentSubscriber());
	}

}
