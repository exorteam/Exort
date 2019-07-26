package exort.associationmanager.serviceImpl;

import java.util.*;

import exort.associationmanager.entity.Association;
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

    private final int 		ASSOCIATION_NUM 	= 10;
    private final String 	AUTO_ID_NAME 	= "article_test";
    private List<Association> 	associations 		= new ArrayList<>();

    @Before
    public void createTestArticles(){
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
    public void clearTestArticles(){
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

//    @Test
//    public void testGet() {
//        repository.saveAll(articles);
//        for(int i=0;i<ASSOCIATION_NUM;i++){
//            final Article contrast = service.getArticle(articles.get(i).getId());
//            Assert.assertEquals(articles.get(i),contrast);
//        }
//    }
//
//    @Test
//    public void testUpdate() {
//        repository.saveAll(articles);
//        for(int i=0;i<ASSOCIATION_NUM;i++){
//            final Article e = articles.get(i);
//            final String title = UUID.randomUUID().toString();
//            final String content = UUID.randomUUID().toString();
//            e.setTitle(title);
//            e.setContent(content);
//            articles.set(i,e);
//            Assert.assertTrue(service.updateArticle(e.getId(),title,content));
//        }
//        for(int i=0;i<ASSOCIATION_NUM;i++){
//            Article e = articles.get(i);
//            Article contrast = repository.findById(e.getId()).get();
//            Assert.assertEquals(e,contrast);
//        }
//    }
//
//    @Test
//    public void testPublishAndWithdraw() {
//        repository.saveAll(articles);
//        for(int i=0;i<ASSOCIATION_NUM;i++){
//            final int id = articles.get(i).getId();
//            final Article e1 = repository.findById(id).get();
//            Assert.assertEquals(e1.getState(),ArticleStatus.UNPUBLISHED);
//
//            service.publishArticle(id);
//            final Article e2 = repository.findById(id).get();
//            Assert.assertEquals(e2.getState(),ArticleStatus.PUBLISHED);
//
//            service.withdrawArticle(id);
//            final Article e3 = repository.findById(id).get();
//            Assert.assertEquals(e3.getState(),ArticleStatus.UNPUBLISHED);
//        }
//    }
//
//    @Test
//    public void testListWithFilter() {
//        final Date rightNow = new Date();
//        Random rand = new Random(rightNow.getTime());
//        final int authorId = rand.nextInt();
//        ArrayList<Integer> authors = new ArrayList<>();
//        authors.add(authorId);
//        for(int i=0;i<5;i++){
//            authors.add(rand.nextInt());
//        }
//        Article e = articles.get(0);
//        e.setCreateTime(rightNow);
//        e.setAuthors(authors);
//        repository.save(e);
//
//        final ArticleFilterParams filter1 = new ArticleFilterParams();
//        filter1.setKeyword(e.getTitle());
//        Assert.assertTrue(service.listArticle(filter1).contains(e));
//
//        final ArticleFilterParams filter2 = new ArticleFilterParams();
//        filter2.setState(ArticleStatus.UNPUBLISHED);
//        Assert.assertTrue(service.listArticle(filter2).contains(e));
//
//        final ArticleFilterParams filter3 = new ArticleFilterParams();
//        filter3.setStartTime(new Date(rightNow.getYear()-1,rightNow.getMonth(),rightNow.getDay()));
//        filter3.setEndTime(new Date(rightNow.getYear()+1,rightNow.getMonth(),rightNow.getDay()));
//        Assert.assertTrue(service.listArticle(filter3).contains(e));
//
//        final ArticleFilterParams filter4 = new ArticleFilterParams();
//        filter4.setAuthorId(authorId);
//        Assert.assertTrue(service.listArticle(filter4).contains(e));
//
//    }

}
//import exort.api.http.review.entity.Application;
//import exort.associationmanager.entity.Association;
//import exort.associationmanager.repository.AssociationRepository;
//import exort.associationmanager.service.AssociationService;
//import org.bson.types.ObjectId;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public  class AssociationServiceImplTest {
//    private List<String> Ids = new LinkedList<>();
//    @Autowired
//    AssociationService associationService;
//    @Autowired
//    AssociationRepository associationRepository;
////    @BeforeEach
////    void setUp() {
////        String objectId1 = new ObjectId().toString();
////        String objectId2 = new ObjectId().toString();
////        String objectId3 = new ObjectId().toString();
////        String objectId4 = new ObjectId().toString();
////        String objectId5 = new ObjectId().toString();
////        Ids.add(objectId1);
////        Ids.add(objectId2);
////        Ids.add(objectId3);
////        Ids.add(objectId4);
////        Ids.add(objectId5);
////        associationRepository.save(new Association(objectId1,"test1","desc1","logo1", Arrays.asList("T1","T2"),1,null));
////        associationRepository.save(new Association(objectId2,"test2","desc2","logo2", Arrays.asList("T1","T2"),1,null));
////        associationRepository.save(new Association(objectId3,"test3","desc3","logo3", Arrays.asList("T1","T2"),1,null));
////        associationRepository.save(new Association(objectId4,"test4","desc4","logo4", Arrays.asList("T1","T2"),1,null));
////        associationRepository.save(new Association(objectId5,"test5","desc5","logo5", Arrays.asList("T1","T2"),0,"18+"));
////    }
//
//    @Test
//    public void testCreateAssociation(){
//        int sizeb = associationRepository.findAll().size();
//        associationService.createAssociation("test6","desc6",Arrays.asList("T1","T2"),"logo6");
//        List<Association> associations = associationRepository.findAll();
//        assertEquals(sizeb+1, associations.size());
////        assertEquals(perms.get(4), perm);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
////import java.util.Arrays;
////import java.util.List;
////import java.util.UUID;
////
////import exort.api.http.common.entity.PagedData;
////import exort.api.http.perm.entity.Role;
////import exort.api.http.perm.service.PermService;
////import exort.api.http.review.entity.Application;
////import exort.associationmanager.entity.*;
////import exort.associationmanager.service.AssociationService;
////import org.junit.Assert;
////import org.junit.Test;
////import org.junit.runner.RunWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.test.context.junit4.SpringRunner;
////
////@RunWith(SpringRunner.class)
////@SpringBootTest
////public class AssociationServiceImplTest {
////
////   @Autowired
////   private AssociationService service;
////    @Autowired
////    private PermService permService;
////
////    @Test
////    public void testCreateAssociation1(){
////        service.createAssociation("qqwrv"+"create",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString());
////        String assoId = service.listAssociations(new AssociationFilterParams("qqwrvcreate",(List<String>)Arrays.asList("a","f"),1),0,1).getContent().get(0).getId();
////        Association association = service.getAssociation(assoId);
////        Assert.assertEquals(association.getName(),"qqwrvcreate");
////        Assert.assertEquals(association.getTags(),(List<String>)Arrays.asList("a","f"));
////    }
////     @Test
////     public void testCreateAssociation2(){
////         service.createAssociation("qqwrv"+"create",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString());
////         String assoId = service.listAssociations(new AssociationFilterParams("qqwrvcreate",(List<String>)Arrays.asList("a","f"),1),0,1).getContent().get(0).getId();
////         Association association = service.getAssociation(assoId);
////         Assert.assertEquals(association.getName(),"qqwrvcreate");
////         Assert.assertEquals(association.getTags(),(List<String>)Arrays.asList("a","f"));
////     }
////
////    @Test
////    public void testGetAssociation(){
////        service.createAssociation("qqwrv"+"get",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString());
////
////
////        PagedData<Association> associations = new PagedData<>();
////        AssociationFilterParams params = new AssociationFilterParams();
////        params.setState(1);
////        params.setTags(Arrays.asList("a","g"));
////        params.setKeyword("qqwrv"+"get");
////        associations = service.listAssociations(params,0,6);
////        String assoId=associations.getContent().get(0).getId();
//// //		association = new Association();
////        Association association = service.getAssociation(assoId);
////        Assert.assertEquals(association.getName(),"qqwrv"+"get");
////
////    }
////
////  @Test
////  public void testListAssociation(){
////       Association new_association = new Association();
////       new_association.setName("qqwrv"+"list");
////       new_association.setDescription(UUID.randomUUID().toString());
////       new_association.setLogo(UUID.randomUUID().toString());
////       new_association.setTags( Arrays.asList("a","f"));
////
////       for (int i = 0; i <10 ; i++) {
////           service.createAssociation(new_association.getName(),new_association.getDescription(),new_association.getTags(),new_association.getLogo());
////       }
////
////       PagedData<Association> associations =new PagedData<>();
////       AssociationFilterParams params = new AssociationFilterParams();
////       params.setState(1);
////       params.setTags(Arrays.asList("a","g"));
////       params.setKeyword("qqwrv"+"list");
////       associations= service.listAssociations(params,0,6);
////       Assert.assertEquals(associations.getContent().size(),6);
////       Assert.assertEquals(associations.getPageNum(),(Integer) 0);
////       Assert.assertEquals(associations.getPageSize(),(Integer) 6);
////  }
////
////   @Test
////   public void testDeleteAssociation(){
////
////       Association new_association = new Association();
////       new_association.setName("qqwrv"+"delete");
////       new_association.setDescription(UUID.randomUUID().toString());
////       new_association.setLogo(UUID.randomUUID().toString());
////       new_association.setTags( Arrays.asList("a","f"));
////       for (int i = 0; i <10 ; i++) {
////           service.createAssociation(new_association.getName(),new_association.getDescription(),new_association.getTags(),new_association.getLogo());
////       }
////
////
////
////       PagedData<Association> associations =new PagedData<>();
////       AssociationFilterParams params = new AssociationFilterParams();
////       params.setState(1);
////       params.setTags(Arrays.asList("a","g"));
////       params.setKeyword("qqwrv"+"delete");
////       associations = service.listAssociations(params,0,9);
////
////       for (int i = 0; i <9 ; i++) {
////           String assoId=associations.getContent().get(i).getId();
////           if(!service.deleteAssociation(assoId)){
////               Assert.assertEquals(true,false);
////           }
////       }
////   }
//////
////   @Test
////   public void testEditAssociation(){
////       PagedData<Association> associations = new PagedData<>();
////       AssociationFilterParams params = new AssociationFilterParams();
////       params.setState(1);
////       params.setTags(Arrays.asList("a","g"));
////       params.setKeyword("qqwrv");
////       associations = service.listAssociations(params,0,6);
////       String assoId=associations.getContent().get(0).getId();
////       Association association = service.editAssociation(assoId,"eewrd","混沌陨石",Arrays.asList("e","w"),"None");
////       Assert.assertEquals(association.getName(),"eewrd");
////       Assert.assertEquals(association.getDescription(),"混沌陨石");
////   }
//////
////    @Test
////    public void testPatchAssociation(){
////        PagedData<Association> associations =new PagedData<>();
////        AssociationFilterParams params = new AssociationFilterParams();
////        params.setState(1);
////        params.setTags(Arrays.asList("a","g"));
////        params.setKeyword("qqwrv");
////        associations = service.listAssociations(params,0,6);
////        String assoId=associations.getContent().get(0).getId();
////
////        service.patchAssociation(assoId,"block","!*+");
////        Association association = service.getAssociation(assoId);
////        Assert.assertEquals(association.getState(),(Integer)0);
////    }
////
////   @Test
////   public void testHandleAssociation(){
////       MyObject myObject = new MyObject();
////       myObject.setDescription(UUID.randomUUID().toString());
////       myObject.setLogo(UUID.randomUUID().toString());
////       myObject.setName("qqwrvhandle");
////       myObject.setTags(Arrays.asList("a","f"));
////
////       Application application= new Application();
////       application.setApplicantId(Long.valueOf(111));
////       application.setCreatedTime(null);
////       application.setHandledTime(null);
////       application.setId(Long.valueOf(123));
////       application.setMaterialIds(Arrays.asList("aqwe","qwef"));
////       application.setState("pending");
////       application.setObject(myObject);
////       application.setType("createAssociation");
////
////       permService.createRole(new Role("SysManager","the admin of exort system"));
////       permService.grantRoles(Long.valueOf(12345),"System",Arrays.asList("SysManager"));
////
////
////
////
////       if (service.handleAsoociationApplication( Long.valueOf( 12345),"accept",application)){
////           String assoId = service.listAssociations(new AssociationFilterParams("qqwrvhandle",(List<String>)Arrays.asList("a","f"),1),0,1).getContent().get(0).getId();
////           Association association = service.getAssociation("");
////           Assert.assertEquals(association.getName(),"qqwrvhandle");
////           Assert.assertEquals(association.getTags(),(List<String>)Arrays.asList("a","f"));
////       }
////       else {
////           Assert.assertEquals(true,false);
////       }
////
////   }
////
////
////}
