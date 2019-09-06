package exort.comment.service.serviceImpl;

import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.comment.entity.Finance;
import exort.comment.repository.FinanceRepository;
import exort.comment.service.FinanceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FinanceServiceImplTest {

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private FinanceService financeService;

    String fid;

    @Before
    public void before() {
        Float amount = Float.valueOf("300.1");
        Integer userId = 1;
        Finance finance = new Finance("name", "assoID", "assoName", "content", "supervisor", amount, "汇入", userId);
        Finance ret = financeRepository.save(finance);
        fid = ret.getId();
    }

    @After
    public void after() {
        financeRepository.deleteAll();
    }

    @Test
    public void createFinance() {
        Float amount = Float.valueOf("300.1");
        Integer userId = 1;

        Finance finance = financeService.createFinance("hello", "assoID", "assoName", "content", "supervisor", amount, "汇入", userId);

        assertEquals("hello", finance.getProjectName());
    }

    @Test
    public void deleteFiance() {
        financeService.deleteFiance(fid);
        assertEquals(0, financeRepository.findAll().size());
    }

    @Test
    public void getOneFiance() {
        Finance finance = financeService.getOneFiance(fid);
        assertEquals("name", finance.getProjectName());
    }

    @Test
    public void updateFinance() {
        Finance finance = financeRepository.findById(fid).get();
        finance.setAssociationName("asfasd");
        Finance ret = financeService.updateFinance(finance, fid);

        assertEquals("asfasd", ret.getAssociationName());
    }

    @Test
    public void getAssociationFiances() {
        PageQuery pageQuery = new PageQuery(0, 5);
        Filters filters = new Filters();
        filters.setAssociationId("assoID");
        PagedData<Finance> finances = financeService.getAssociationFiances(filters, pageQuery);
        assertEquals(1, finances.getContent().size());

        Date date = financeService.getOneFiance(fid).getTime();

        Calendar cal1=Calendar.getInstance();
        cal1.setTime(date);


        filters.setKeyword("na");
        PagedData<Finance> finances2 = financeService.getAssociationFiances(filters, pageQuery);
        assertEquals(1, finances2.getContent().size());
    }
}