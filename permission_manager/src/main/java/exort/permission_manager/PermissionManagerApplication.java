package exort.permission_manager;

import exort.api.http.common.errorhandler.ApiErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApiErrorHandler.class)
public class PermissionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionManagerApplication.class, args);
    }

}
