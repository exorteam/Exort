package exort.testformongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"exort.testformongo.**"})
public class TestForMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestForMongoApplication.class, args);
    }

}
