package exort.testformongo.service;

import exort.testformongo.serviceImpl.ServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest


public class test_serviceTest {
    @Autowired
    private TestService service;

    @Test
    public void testCreateAssociation(){
        Assert.assertEquals(service.createAssociation("qqq","www","eee"),true);
    }

}