package exort.associationmanager;

import exort.api.http.perm.service.PermServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({PermServiceImpl.class})
public class AssociationManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssociationManagerApplication.class, args);
    }

}
