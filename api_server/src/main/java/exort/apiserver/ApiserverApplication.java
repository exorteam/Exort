package exort.apiserver;

import exort.api.http.member.service.AssoMemServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import exort.api.http.perm.service.PermServiceImpl;

@SpringBootApplication
@Import({PermServiceImpl.class, AssoMemServiceImpl.class})

public class ApiserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiserverApplication.class, args);
	}
}
