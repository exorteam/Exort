package exort.comment.service.serviceImpl;

import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.finance.entity.Filters;
import exort.comment.entity.AssociationBalance;
import exort.comment.entity.Finance;
import exort.comment.repository.AssociationBalanceRepository;
import exort.comment.repository.FinanceRepository;
import exort.comment.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.min;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private AssociationBalanceRepository associationBalanceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Finance createFinance(String projectName, String associationId, String associationName, String content, String supervisor, Float transactionAmount, String direction, Integer operatorId) {

        Finance finance = new Finance(projectName, associationId, associationName, content, supervisor, transactionAmount, direction, operatorId);
        finance.setBalance(getAssociationBalance(associationId));
        Finance f=financeRepository.save(finance);
        Finance ret= acceptFinanceApplication(f.getId());

        return ret;
    }

    @Override
    public Finance deleteFiance(String id) {

        if (financeRepository.findById(id).isPresent()) {
            Finance finance = financeRepository.findById(id).get();
            financeRepository.delete(finance);
            return finance;
        }

        return null;
    }

    @Override
    public Finance getOneFiance(String id) {

        if (financeRepository.findById(id).isPresent()) {

            Finance finance = financeRepository.findById(id).get();

            return finance;
        }

        return null;
    }

    @Override
    public Finance updateFinance(Finance finance, String id) {
        finance.setId(id);
        Finance ret = financeRepository.save(finance);
        return ret;
    }

    @Override
    public PagedData<Finance> getAssociationFiances(Filters filters, PageQuery pageQuery) {
        Query query = new Query();

        query.with(Sort.by(Sort.Direction.DESC, "time"));

        if (filters.getAssociationId() != null) {
            query.addCriteria(Criteria.where("associationId").is(filters.getAssociationId()));
        }

        if (filters.getKeyword() != null) {
            query.addCriteria(Criteria.where("projectName").regex(filters.getKeyword()));
        }

        if (filters.getTimeRange() != null) {
            query.addCriteria(
                    Criteria.where("time")
                            .gte(filters.getTimeRange().getStart())
                            .lte(filters.getTimeRange().getEnd())
            );
        }

        List<Finance> finances = mongoTemplate.find(query, Finance.class, "finance");
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        if (finances.size() == 0) {
            return new PagedData<>(pageNum, pageSize, 0L, null);
        }
        int totalSize = finances.size();
        List<Finance> result = finances.subList(pageNum * pageSize, min((pageNum + 1) * pageSize, totalSize));

        return new PagedData<Finance>(pageNum, pageSize, (long) totalSize, result);
    }

    @Override
    public Finance acceptFinanceApplication(String id) {
        Finance finance = getOneFiance(id);

        if (finance == null) {
            return null;
        }

        finance.setState(2);
        Finance ret = financeRepository.save(finance);

        AssociationBalance balance = associationBalanceRepository.findByAssociationId(ret.getAssociationId());
        if (finance.getDirection().equals("汇入")) {
            balance.setBalance(balance.getBalance() + ret.getTransactionAmount());
        } else {
            balance.setBalance(balance.getBalance() - ret.getTransactionAmount());
        }
        associationBalanceRepository.save(balance);

        return ret;
    }

    @Override
    public Finance refuseFinanceApplication(String id) {
        Finance finance = getOneFiance(id);

        if (finance == null) {
            return null;
        }

        finance.setState(3);
        Finance ret = financeRepository.save(finance);

        return ret;
    }

    @Override
    public Float getAssociationBalance(String associationId) {

        if (associationBalanceRepository.existsByAssociationId(associationId)) {
            return associationBalanceRepository.findByAssociationId(associationId).getBalance();
        } else {
            AssociationBalance associationBalance = new AssociationBalance(associationId, Float.valueOf("0"));
            AssociationBalance ret= associationBalanceRepository.save(associationBalance);
            return ret.getBalance();
        }
    }
}
