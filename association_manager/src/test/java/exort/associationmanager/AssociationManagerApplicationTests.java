package exort.associationmanager;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.AssociationList;
import exort.associationmanager.entity.ResponseBody;
import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociationManagerApplicationTests {

	@Autowired
	private AssociationRepository repository;
	@Autowired
	private AssociationService service;

	@Test
	public void contextLoads() {
	}
	@Test
	public void testCreateAssociation(){
		Association association = new Association();
		association.setName("qqwrv");
		association.setDescription(UUID.randomUUID().toString());
		association.setLogo(UUID.randomUUID().toString());
		association.setTags( Arrays.asList("a","f"));
		ResponseBody responseBody = service.createAssociation(association.getName(),association.getDescription(),association.getTags(),association.getLogo());
		Assert.assertEquals(responseBody.getError(),"");
		Assert.assertEquals(responseBody.getMessage(),"");
//		Assert.assertFalse(repository.existsByName(association.getName()));
	}

	@Test
	public void testGetAssociation(){
		Association association = new Association();
		ResponseBody responseBody = service.getAssociation("5d3133846372d60e482d12c2");
		association = (Association) responseBody.getData();
		Assert.assertEquals(association.getName(),"qqwrv");

	}

	@Test
	public void testListAssociation(){
		AssociationList associations =new AssociationList();
		AssociationFilterParams params = new AssociationFilterParams();
		params.setState(1);
		params.setTags(Arrays.asList("a","g"));
		params.setKeyword("qqwrv");
		ResponseBody responseBody = service.listAssociations(params,0,6);
		associations = (AssociationList) responseBody.getData();
		Assert.assertEquals(associations.getContent().size(),5);
		Assert.assertEquals(associations.getTotalSize(),(Integer) 5);
		Assert.assertEquals(associations.getPageNumber(),(Integer) 0);
		Assert.assertEquals(associations.getPageSize(),(Integer) 6);


	}
	@Test
	public void testDeleteAssociation(){
		ResponseBody responseBody = service.deleteAssociation("5d3133846372d60e482d12c2");
		Assert.assertEquals(responseBody.getError(),"");
		Assert.assertEquals(responseBody.getMessage(),"");

//		Assert.assertEquals(repository.findById("5d3133846372d60e482d12c2"),null);


	}

}
