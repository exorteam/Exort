package exort.api.http.finance.service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.finance.entity.ApplyInfo;
import exort.api.http.finance.entity.Filters;
import exort.api.http.finance.entity.FinanceInfo;

public interface FinanceService {
    public ApiResponse<FinanceInfo> createFinance(ApplyInfo applyInfo);

    public ApiResponse<FinanceInfo> deleteFiance(String id);

    public ApiResponse<FinanceInfo> getOneFiance(String id);

    public ApiResponse<FinanceInfo> updateFinance(FinanceInfo finance, String id);

    public ApiResponse<PagedData<FinanceInfo>> getAssociationFiances(Filters filters, PageQuery pageQuery);

}
