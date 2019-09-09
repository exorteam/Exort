package exort.review_manager;

import exort.api.http.activity.service.ActivityServiceImpl;
import exort.api.http.assomgr.service.AssociationServiceImpl;
import exort.api.http.common.errorhandler.ApiErrorHandler;
import exort.api.http.member.service.AssoMemServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        ApiErrorHandler.class,
        AssoMemServiceImpl.class,
        AssociationServiceImpl.class,
        ActivityServiceImpl.class
})
public class ReviewManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewManagerApplication.class, args);
    }

}
