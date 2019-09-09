package exort.association_member_manager.config;


import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

public class TaskRunner implements ApplicationRunner, Ordered {

    final private static String MEMBER = "association_member";
    final private static String MANAGER = "association_root";

    @Autowired
    private PermService ps;

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ps.createRole(new Role(MANAGER));
        ps.createRole(new Role(MEMBER));
    }
}