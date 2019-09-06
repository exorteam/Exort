package exort.api.http.finance.service;

import com.google.common.reflect.TypeToken;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.finance.entity.ApplyInfo;
import exort.api.http.finance.entity.Filters;
import exort.api.http.finance.entity.FinanceInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class FinanceImpl extends RestTemplate implements FinanceService {

    @Value("${exort.finance.protocol:http}")
    public void setProtocol(String protocol) {
        super.setProtocol(protocol);
    }

    @Value("${exort.finance.endpoint:localhost}")
    public void setEndpoint(String endpoint) {
        super.setEndpoint(endpoint);
    }


    @Override
    public ApiResponse<FinanceInfo> createFinance(ApplyInfo applyInfo) {
        return request(new TypeToken<FinanceInfo>() {
        }, applyInfo, HttpMethod.POST, "/finances");
    }

    @Override
    public ApiResponse<FinanceInfo> deleteFiance(String id) {
        return request(new TypeToken<FinanceInfo>() {
        }, HttpMethod.DELETE, "/finances/{financeId}", id);
    }

    @Override
    public ApiResponse<FinanceInfo> getOneFiance(String id) {
        return request(new TypeToken<FinanceInfo>() {
        }, HttpMethod.GET, "/finances/{financeId}", id);
    }

    @Override
    public ApiResponse<FinanceInfo> updateFinance(FinanceInfo finance, String id) {
        return request(new TypeToken<FinanceInfo>() {
        }, finance, HttpMethod.POST, "/finances/{financeId}", id);
    }

    @Override
    public ApiResponse<PagedData<FinanceInfo>> getAssociationFiances(Filters filters, PageQuery pageQuery) {
        return request(new TypeToken<PagedData<FinanceInfo>>() {
        }, filters, HttpMethod.POST, pageQuery, "/finances/filters");
    }

    @Override
    public ApiResponse<FinanceInfo> acceptFinanceApplication(String id) {
        return request(new TypeToken<FinanceInfo>() {
        }, HttpMethod.PUT, "/finances/{financeId}/accept",id);
    }

    @Override
    public ApiResponse<FinanceInfo> refuseFinanceApplication(String id) {
        return request(new TypeToken<FinanceInfo>() {
        }, HttpMethod.PUT, "/finances/{financeId}/refuse",id);
    }

    @Override
    public ApiResponse<Float> getAssociationBalance(String associationId) {
        return request(new TypeToken<Float>() {
        }, HttpMethod.GET, "associations/{associationId}/balance",associationId);
    }
}
