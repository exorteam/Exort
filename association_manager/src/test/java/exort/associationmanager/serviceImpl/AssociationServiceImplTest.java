package exort.associationmanager.serviceImpl;

import java.util.*;

import exort.api.http.perm.entity.Role;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.MyObject;
import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociationServiceImplTest {

    @Autowired
    private AssociationRepository repository;
    @Autowired
    private AssociationService service;
    @Autowired
    private PermService permService;

    private final int 		ASSOCIATION_NUM 	= 10;
    private final String 	AUTO_ID_NAME 	= "article_test";
    private List<Association> 	associations 		= new ArrayList<>();

    @Before
    public void createTestAssociation(){
        for(int i=0;i<ASSOCIATION_NUM;i++){
            Association article = new Association();
            String Id = new ObjectId().toString();
            article.setId(Id);
            article.setName(UUID.randomUUID().toString());
            article.setDescription(UUID.randomUUID().toString());
            article.setLogo(UUID.randomUUID().toString());
            article.setTags(Arrays.asList("q","w","e"));
            article.setState(1);
            associations.add(article);
        }
    }

    @After
    public void clearTestAssociation(){
        repository.deleteAll(associations);
        associations.clear();
    }


    @Test
    public void testCreate() {
        List<String> ids = new ArrayList<>();
        for(int i=0;i<ASSOCIATION_NUM;i++){
            final Association e = associations.get(i);
            service.createAssociationWithId(e.getId(),e.getName(),e.getDescription(),e.getTags(),e.getLogo());
            ids.add(e.getId());
        }
        for(int i=0;i<ids.size();i++){
            String Id = ids.get(i);
            Association contrast = service.getAssociation(Id);
            Assert.assertEquals(contrast,associations.get(i));
        }

    }

    @Test
    public void testGet() {
        repository.saveAll(associations);
        for(int i=0;i<ASSOCIATION_NUM;i++){
            final Association contrast = service.getAssociation(associations.get(i).getId());
            Assert.assertEquals(associations.get(i),contrast);
        }
    }
//
    @Test
    public void testUpdate() {
        repository.saveAll(associations);
        for(int i=0;i<ASSOCIATION_NUM;i++){
            final Association e = associations.get(i);
            final String title = UUID.randomUUID().toString();
            final String content = UUID.randomUUID().toString();
            e.setName(title);
            e.setDescription(content);
            e.setLogo(content);
            e.setTags(Arrays.asList("a","s","d"));
            service.editAssociation(e.getId(),e.getName(),e.getDescription(),e.getTags(),e.getLogo());
            associations.set(i,e);
        }
        for(int i=0;i<ASSOCIATION_NUM;i++){
            Association e = associations.get(i);
            Association contrast = repository.findById(e.getId()).get();
            Assert.assertEquals(e,contrast);
        }
    }
////
    @Test
    public void testBlockAndUnblock() {
        repository.saveAll(associations);
        for(int i=0;i<ASSOCIATION_NUM;i++){
            final String id = associations.get(i).getId();
            final Association e1 = repository.findById(id).get();
            Assert.assertEquals(e1.getState(), Integer.valueOf(1));

            service.patchAssociation(id,"block","18+");
            final Association e2 = repository.findById(id).get();
            Assert.assertEquals(e2.getState(),Integer.valueOf(0));

            service.patchAssociation(id,"unblock","");
            final Association e3 = repository.findById(id).get();
            Assert.assertEquals(e3.getState(),Integer.valueOf(1));
        }
    }

    @Test
    public void testListWithFilter() {
        Association e = associations.get(0);
        String newName = UUID.randomUUID().toString();
        e.setName(newName);
        String newdesc = UUID.randomUUID().toString();
        e.setDescription(newdesc);
        List<String> newTags = Arrays.asList("z","x","c");
        e.setTags(newTags);
        e.setState(-3);
        repository.save(e);

        final AssociationFilterParams filter1 = new AssociationFilterParams();
        filter1.setKeyword(e.getName());
        Assert.assertTrue(service.listAssociations(filter1,0,1).getContent().contains(e));

        final AssociationFilterParams filter2 = new AssociationFilterParams();
        filter2.setState(-3);
        System.out.println(service.listAssociations(filter2,0,700).getContent());
        Assert.assertTrue(service.listAssociations(filter2,0,700).getContent().contains(e));

        final AssociationFilterParams filter3 = new AssociationFilterParams();
        filter3.setKeyword(e.getName());
        Assert.assertTrue(service.listAssociations(filter3,0,1).getContent().contains(e));

        final AssociationFilterParams filter4 = new AssociationFilterParams();
        filter4.setTags(Arrays.asList("x"));
        Assert.assertTrue(service.listAssociations(filter4,0,1).getContent().contains(e));
    }
    @Test
    public void TestHandleApplication(){
        Association e = associations.get(0);
        String newName = UUID.randomUUID().toString();
        e.setName(newName);
        String newdesc = UUID.randomUUID().toString();
        e.setDescription(newdesc);
        List<String> newTags = Arrays.asList("r","t","y");
        e.setTags(newTags);
        e.setState(1);
        MyObject myObject = new MyObject();
        myObject.setName(e.getName());
        myObject.setTags(e.getTags());
        myObject.setLogo(e.getLogo());
        myObject.setDescription(e.getDescription());
        Application application = new Application();
        application.setType("createAssociation");
        application.setObject(myObject);

        permService.createRole(new Role("SysManager","the admin of exort system"));
        permService.grantRoles(Long.valueOf(123456),"System",Arrays.asList("SysManager"));

        Assert.assertTrue(service.handleAsoociationApplication(Long.valueOf(123456),"accept",application));
        final AssociationFilterParams filter4 = new AssociationFilterParams();
        filter4.setTags(Arrays.asList("y"));
        filter4.setKeyword(e.getName());
        filter4.setState(1);
        Association e2 = service.listAssociations(filter4,0,100).getContent().get(0);
        Assert.assertEquals(e2.getName(),e.getName());


    }

}


