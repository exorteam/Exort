package exort.associationmanager.service;

import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.ResponseBody;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * AssociationServiceImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>���� 15, 2019</pre>
 * @version 1.0
 */
public class AssociationServiceTest {

    ResponseBody responseBody = new ResponseBody();
    AssociationService service;

    /**
     *
     * Method: getAssociation(Integer assoId)
     *
     */
    @Test
    public void testGetAssociation() throws Exception {

        responseBody.setMessage("");
        responseBody.setError("");
        responseBody.setData("");
        assertEquals(responseBody,service.getAssociation(111));
    }

    /**
     *
     * Method: listAssociations(AssociationFilterParams params, Integer pageNum, Integer pageSize)
     *
     */
    @Test
    public void testListAssociations() throws Exception {
//        assertEquals(responseBody,service.listAssociations(111);

    }

    /**
     *
     * Method: createAssociation(String name, String description, List<String> tags, String logo)
     *
     */
    @Test
    public void testCreateAssociation() throws Exception {
        assertEquals(responseBody,service.getAssociation(111));
    }

    /**
     *
     * Method: deleteAssociation(Integer assoId)
     *
     */
    @Test
    public void testDeleteAssociation() throws Exception {
        assertEquals(responseBody,service.getAssociation(111));
    }

    /**
     *
     * Method: editAssociation(Integer assoId, String name, String description, List<String> tags, String logo)
     *
     */
    @Test
    public void testEditAssociation() throws Exception {
        assertEquals(responseBody,service.getAssociation(111));
    }

    /**
     *
     * Method: patchAssociation(Integer assoId, String type, String reason)
     *
     */
    @Test
    public void testPatchAssociation() throws Exception {
        assertEquals(responseBody,service.getAssociation(111));
    }

    /**
     *
     * Method: handleAsoociationApplication(Integer user_id, String type, Application app)
     *
     */
    @Test
    public void testHandleAsoociationApplication() throws Exception {
        assertEquals(responseBody,service.getAssociation(111));
    }
    public void testCreateTestData() throws Exception{
        assertEquals(responseBody,service.getAssociation(111));
    }


    /**
     *
     * Method: noAssoMessage(ResponseBody responseBody)
     *
     */
    @Test
    public void testNoAssoMessage() throws Exception {
        assertEquals(responseBody,service.getAssociation(111));
/*
try {
   Method method = AssociationServiceImpl.getClass().getMethod("noAssoMessage", ResponseBody.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

}
