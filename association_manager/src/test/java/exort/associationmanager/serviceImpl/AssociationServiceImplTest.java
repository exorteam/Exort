//package exort.associationmanager.serviceImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import exort.api.http.perm.entity.Role;
//import exort.api.http.perm.service.PermService;
//import exort.associationmanager.entity.*;
//import exort.associationmanager.service.AssociationService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AssociationServiceImplTest {
//
//   @Autowired
//   private AssociationService service;
//    @Autowired
//    private PermService permService;
//
//    @Test
//    public void testCreateAssociation1(){
//        service.createAssociation("qqwrv"+"create",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString());
//        String assoId = service.listAssociations(new AssociationFilterParams("qqwrvcreate",(List<String>)Arrays.asList("a","f"),1),0,1).getContent().get(0).getId();
//        Association association = service.getAssociation(assoId);
//        Assert.assertEquals(association.getName(),"qqwrvcreate");
//        Assert.assertEquals(association.getTags(),(List<String>)Arrays.asList("a","f"));
//    }
//    // @Test
//    // public void testCreateAssociation2(){
//    //     service.createAssociation("qqwrv"+"create",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString());
//    //     String assoId = service.listAssociations(new AssociationFilterParams("qqwrvcreate",(List<String>)Arrays.asList("a","f"),1),0,1).getContent().get(0).getId();
//    //     Association association = service.getAssociation(assoId);
//    //     Assert.assertEquals(association.getName(),"qqwrvcreate");
//    //     Assert.assertEquals(association.getTags(),(List<String>)Arrays.asList("a","f"));
//    // }
//
////    @Test
////    public void testGetAssociation(){
////        service.createAssociation("qqwrv"+"get",UUID.randomUUID().toString(), (List<String>)Arrays.asList("a","f"),UUID.randomUUID().toString());
////
////
////        AssociationList associations = new AssociationList();
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
//
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
////       AssociationList associations =new AssociationList();
////       AssociationFilterParams params = new AssociationFilterParams();
////       params.setState(1);
////       params.setTags(Arrays.asList("a","g"));
////       params.setKeyword("qqwrv"+"list");
////       associations= service.listAssociations(params,0,6);
////       Assert.assertEquals(associations.getContent().size(),6);
////       Assert.assertEquals(associations.getPageNumber(),(Integer) 0);
////       Assert.assertEquals(associations.getPageSize(),(Integer) 6);
////  }
//////
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
////       AssociationList associations =new AssociationList();
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
////       AssociationList associations = new AssociationList();
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
////        AssociationList associations =new AssociationList();
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
//
////       Application application= new Application();
////       application.setApplicantId("111");
////       application.setCreatedTime("111");
////       application.setHandledTime("1wq");
////       application.setId("qwewq");
////       application.setMaterialIds(Arrays.asList("aqwe","qwef"));
////       application.setState("pending");
////       application.setObject(myObject);
////       application.setType("createAssociation");
//
////       permService.createRole(new Role("SysManager","the admin of exort system"));
////       permService.grantRoles(Long.valueOf(12345),"System",Arrays.asList("SysManager"));
//
//
//
//
////       if (service.handleAsoociationApplication( Long.valueOf( 12345),"accept",application)){
////           String assoId = service.listAssociations(new AssociationFilterParams("qqwrvhandle",(List<String>)Arrays.asList("a","f"),1),0,1).getContent().get(0).getId();
////           Association association = service.getAssociation(assoId);
////           Assert.assertEquals(association.getName(),"qqwrvhandle");
////           Assert.assertEquals(association.getTags(),(List<String>)Arrays.asList("a","f"));
////       }
////       else {
////           Assert.assertEquals(true,false);
////       }
//
////   }
//
//
//}
