package exort.association_member_manager;

import exort.api.http.common.errorhandler.ApiErrorHandler;
import exort.api.http.perm.service.PermServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({PermServiceImpl.class, ApiErrorHandler.class})
public class AssociationMemberManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssociationMemberManagementApplication.class, args);
    }

}
