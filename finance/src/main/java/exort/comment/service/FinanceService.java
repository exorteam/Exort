package exort.comment.service;

import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.finance.entity.Filters;
import exort.comment.entity.Finance;


public interface FinanceService {
    public Finance createFinance(String projectName, String associationId, String associationName, String content, String supervisor, Float transactionAmount, String direction, Integer operatorId);

    public Finance deleteFiance(String id);

    public Finance getOneFiance(String id);

    public Finance updateFinance(Finance finance, String id);

    public PagedData<Finance> getAssociationFiances(Filters filters, PageQuery pageQuery);

    public Finance acceptFinanceApplication(String id);

    public Finance refuseFinanceApplication(String id);

    public Float getAssociationBalance(String associationId);

}
