package exort.associationmanager.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import exort.associationmanager.entity.*;
import exort.associationmanager.service.AssociationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociationServiceImplTest {

   //	@Autowired
//	private AssociationRepository repository;
   @Autowired
   private AssociationService service;

   @Test
   public void testCreateAssociation(){
       ResponseBody responseBody = service.createAssociation("qqwrv"+"create",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString(),);
       Assert.assertEquals(responseBody.getError(),"");
       Assert.assertEquals(responseBody.getMessage(),"");
   }

   @Test
   public void testGetAssociation(){
       Association association = new Association();
       association.setName("qqwrv"+"get");
       association.setDescription(UUID.randomUUID().toString());
       association.setLogo(UUID.randomUUID().toString());
       association.setTags( Arrays.asList("a","f"));
       ResponseBody responseBody = new ResponseBody();
       service.createAssociation(association.getName(),association.getDescription(),association.getTags(),association.getLogo());

       AssociationList associations =new AssociationList();
       AssociationFilterParams params = new AssociationFilterParams();
       params.setState(1);
       params.setTags(Arrays.asList("a","g"));
       params.setKeyword("qqwrv"+"get");
       responseBody = service.listAssociations(params,0,6);
       associations = (AssociationList) responseBody.getData();
       String assoId=associations.getContent().get(0).getId();
//		association = new Association();
       responseBody = service.getAssociation(assoId);
       association = (Association) responseBody.getData();
       Assert.assertEquals(association.getName(),"qqwrv"+"get");

   }

//    @Test
//    public void testListAssociation(){
//        Association new_association = new Association();
//        new_association.setName("qqwrv"+"list");
//        new_association.setDescription(UUID.randomUUID().toString());
//        new_association.setLogo(UUID.randomUUID().toString());
//        new_association.setTags( Arrays.asList("a","f"));
//        for (int i = 0; i <10 ; i++) {
//            service.createAssociation(new_association.getName(),new_association.getDescription(),new_association.getTags(),new_association.getLogo());
//        }



//        AssociationList associations =new AssociationList();
//        AssociationFilterParams params = new AssociationFilterParams();
//        params.setState(1);
//        params.setTags(Arrays.asList("a","g"));
//        params.setKeyword("qqwrv"+"list");
//        ResponseBody responseBody = service.listAssociations(params,0,6);
//        associations = (AssociationList) responseBody.getData();
//        Assert.assertEquals(associations.getContent().size(),6);
// //		Assert.assertEquals(associations.getTotalSize(),(Integer) 12);
//        Assert.assertEquals(associations.getPageNumber(),(Integer) 0);
//        Assert.assertEquals(associations.getPageSize(),(Integer) 6);




//    }

//    @Test
//    public void testDeleteAssociation(){

//        Association new_association = new Association();
//        new_association.setName("qqwrv"+"delete");
//        new_association.setDescription(UUID.randomUUID().toString());
//        new_association.setLogo(UUID.randomUUID().toString());
//        new_association.setTags( Arrays.asList("a","f"));
//        for (int i = 0; i <10 ; i++) {
//            service.createAssociation(new_association.getName(),new_association.getDescription(),new_association.getTags(),new_association.getLogo());
//        }



//        AssociationList associations =new AssociationList();
//        AssociationFilterParams params = new AssociationFilterParams();
//        params.setState(1);
//        params.setTags(Arrays.asList("a","g"));
//        params.setKeyword("qqwrv"+"delete");
//        ResponseBody responseBody = service.listAssociations(params,0,9);
//        associations = (AssociationList) responseBody.getData();

//        for (int i = 0; i <9 ; i++) {
//            String assoId=associations.getContent().get(i).getId();
//            responseBody = service.deleteAssociation(assoId);

//            Assert.assertEquals(responseBody.getError(),"");
//            Assert.assertEquals(responseBody.getMessage(),"");
// //			Assert.assertEquals(repository.findById(assoId),null);
//        }

//    }

//    @Test
//    public void testEditAssociation(){
//        AssociationList associations =new AssociationList();
//        AssociationFilterParams params = new AssociationFilterParams();
//        params.setState(1);
//        params.setTags(Arrays.asList("a","g"));
//        params.setKeyword("qqwrv");
//        ResponseBody responseBody = service.listAssociations(params,0,6);
//        associations = (AssociationList) responseBody.getData();
//        String assoId=associations.getContent().get(0).getId();

//        responseBody = service.editAssociation(assoId,"eewrd","混沌陨石",Arrays.asList("e","w"),"None");
//        Association association = (Association) responseBody.getData();
//        Assert.assertEquals(responseBody.getError(),"");
//        Assert.assertEquals(responseBody.getMessage(),"");
//        Assert.assertEquals(association.getName(),"eewrd");
//        Assert.assertEquals(association.getDescription(),"混沌陨石");
//    }

//    @Test
//    public void testPatchAssociation(){
//        AssociationList associations =new AssociationList();
//        AssociationFilterParams params = new AssociationFilterParams();
//        params.setState(1);
//        params.setTags(Arrays.asList("a","g"));
//        params.setKeyword("qqwrv");
//        ResponseBody responseBody = service.listAssociations(params,0,6);
//        associations = (AssociationList) responseBody.getData();
//        String assoId=associations.getContent().get(0).getId();

//        responseBody = service.patchAssociation(assoId,"block","!*+");
//        Assert.assertEquals(responseBody.getError(),"");
//        Assert.assertEquals(responseBody.getMessage(),"");
//        Association association = (Association) service.getAssociation(assoId).getData();
//        Assert.assertEquals(association.getState(),(Integer)0);
//    }

//    @Test
//    public void testHandleAssociation(){
//        MyObject myObject = new MyObject();
//        myObject.setDescription(UUID.randomUUID().toString());
//        myObject.setLogo(UUID.randomUUID().toString());
//        myObject.setName("qqwrv");
//        myObject.setTags(Arrays.asList("a","f"));

//        Application application= new Application();
//        application.setApplicantId("111");
//        application.setCreatedTime("111");
//        application.setHandledTime("1wq");
//        application.setId("qwewq");
//        application.setMaterialIds(Arrays.asList("aqwe","qwef"));
//        application.setState("pending");
//        application.setObject(myObject);
//        application.setType("createAssociation");

//        ResponseBody responseBody = service.handleAsoociationApplication("5d3134216372d61d78c1030f","accept",application);

//    }


}
