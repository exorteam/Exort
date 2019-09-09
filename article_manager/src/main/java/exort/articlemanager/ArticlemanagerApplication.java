package exort.articlemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import exort.api.http.common.errorhandler.ApiErrorHandler;

@SpringBootApplication
@Import({ApiErrorHandler.class})
public class ArticlemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticlemanagerApplication.class, args);
	}

}
