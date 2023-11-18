package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.domain.Tutorial;

@Repository
public interface TutorialRepository extends ReactiveMongoRepository<Tutorial, String> {

    Flux<Tutorial> findByPublished(boolean published);

    Flux<Tutorial> findByTitleContaining(String title);

}
