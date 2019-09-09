package exort.articlemanager;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import exort.articlemanager.component.AutoIncIdGenerator;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class AutoIdGeneratorTest {

	@Autowired
	private AutoIncIdGenerator idGenerator;

	@Test
	public void testGenerator() {
		final String ID_NAME = UUID.randomUUID().toString().substring(0,4);
		for(int i=1;i<10;i++){
			Assert.assertEquals(idGenerator.getNextId(ID_NAME).intValue(),i);
		}

		idGenerator.removeName(ID_NAME);
		for(int i=1;i<10;i++){
			Assert.assertEquals(idGenerator.getNextId(ID_NAME).intValue(),i);
		}

		idGenerator.removeName(ID_NAME);
	}
}

