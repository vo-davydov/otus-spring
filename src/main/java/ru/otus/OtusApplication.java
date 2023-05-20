package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.core.MessageSourceHelper;
import ru.otus.dao.QuestionDao;
import ru.otus.service.GameService;

@SpringBootApplication
public class OtusApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(OtusApplication.class, args);
		var game = context.getBean("gameServiceImpl", GameService.class);
		game.start();
	}

}
