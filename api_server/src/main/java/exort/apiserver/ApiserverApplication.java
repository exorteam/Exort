package exort.apiserver;

import exort.api.http.finance.service.FinanceImpl;
import exort.api.http.review.service.ReviewServiceImpl;
import exort.api.http.comment.service.CommentServiceImplement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import exort.api.http.activity.service.ActivityServiceImpl;
import exort.api.http.member.service.AssoMemServiceImpl;
import exort.api.http.perm.service.PermServiceImpl;

@SpringBootApplication
@Import({
		PermServiceImpl.class,
		AssoMemServiceImpl.class,
		ActivityServiceImpl.class,
		ReviewServiceImpl.class,
		CommentServiceImplement.class,
		FinanceImpl.class
})
public class ApiserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}
}
