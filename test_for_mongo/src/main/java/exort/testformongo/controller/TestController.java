package exort.testformongo.controller;

import exort.testformongo.entity.Association;
import exort.testformongo.service.TestService;
import exort.testformongo.serviceImpl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {
    @Autowired
    private TestService service ;

    @GetMapping("/associations")
    public boolean listAssociations(@RequestBody Association body ){
        return service.createAssociation(body.getName(),body.getLogo(),body.getDescription());
    }


}
