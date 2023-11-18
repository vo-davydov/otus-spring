package ru.otus.changelog;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.reactivestreams.client.ClientSession;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.api.annotations.*;
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

@ChangeUnit(id = "book-initializer", order = "1", author = "davydov")
public class BookInitializerChangeUnit {

    public static String BOOKS_COLLECTION = "book";

    private static Logger logger = LoggerFactory.getLogger(BookInitializerChangeUnit.class);

    private

    @BeforeExecution void beforeExecution(MongoDatabase mongoDatabase) {
        SubscriberSync<Void> subscriber = new MongoSubscriberSync<>();
        mongoDatabase.createCollection(BOOKS_COLLECTION).subscribe(subscriber);
        subscriber.await();
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(MongoDatabase mongoDatabase) {
        SubscriberSync<Void> subscriber = new MongoSubscriberSync<>();
        mongoDatabase.getCollection(BOOKS_COLLECTION).drop().subscribe(subscriber);
        subscriber.await();
    }

    @Execution
    public void execution(ClientSession clientSession, MongoDatabase mongoDatabase) {
        SubscriberSync<InsertManyResult> subscriber = new MongoSubscriberSync<>();
        mongoDatabase.getCollection(BOOKS_COLLECTION, Book.class)
                .insertMany(getBooks())
                .subscribe(subscriber);

        InsertManyResult result = subscriber.getFirst();
        logger.info("BookInitializerChangeLog.execution wasAcknowledged: {}", result.wasAcknowledged());
        result.getInsertedIds()
                .forEach((key, value) -> logger.info("update id[{}] : {}", key, value));
    }

    @RollbackExecution
    public void rollbackExecution(ClientSession clientSession, MongoDatabase mongoDatabase) {
        SubscriberSync<DeleteResult> subscriber = new MongoSubscriberSync<>();

        mongoDatabase
                .getCollection(BOOKS_COLLECTION, Book.class)
                .deleteMany(clientSession, new Document())
                .subscribe(subscriber);
        DeleteResult result = subscriber.getFirst();
        logger.info("BookInitializerChangeLog.rollbackExecution wasAcknowledged: {}", result.wasAcknowledged());
        logger.info("BookInitializerChangeLog.rollbackExecution deleted count: {}", result.getDeletedCount());
    }

    private static List<Book> getBooks() {
        var author1 = new Author("Jack London");
        var author2 = new Author("Jordan Peterson");
        var author3 = new Author("Daniel Kahneman");

        var genre1 = new Genre("psychology");
        var genre2 = new Genre("economy");
        var genre3 = new Genre("adventures");

        var book1 = new Book("White Fang", author1, genre3);
        var book2 = new Book("The sea-wolf", author1, genre3);
        var book3 = new Book("Thinking fast and slow", author3, genre2);
        var book4 = new Book("12 Rules for Life", author2, genre1);
        var book5 = new Book("12 More Rules for Life", author2, genre1);

        return List.of(book1, book2, book3, book4, book5);
    }

}
