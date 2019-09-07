package exort.apiserver.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.finance.entity.ApplyInfo;
import exort.api.http.finance.entity.Filters;
import exort.api.http.finance.entity.FinanceInfo;
import exort.api.http.finance.service.FinanceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FinanceController {
    @Autowired
    private FinanceImpl fsi;

    @PostMapping(value = "/finances")
    public ApiResponse<FinanceInfo> createFinance(@RequestBody ApplyInfo applyInfo) {
        return fsi.createFinance(applyInfo);
    }

    @DeleteMapping(value = "/finances/{financeId}")
    public ApiResponse<FinanceInfo> deleteFinance(@PathVariable(value = "financeId") String id) {
        return fsi.deleteFiance(id);
    }

    @PutMapping(value = "/finances/{financeId}")
    public ApiResponse<FinanceInfo> updateFinance(@RequestBody FinanceInfo finance, @PathVariable(value = "financeId") String id) {
        return fsi.updateFinance(finance, id);
    }

    @GetMapping(value = "/finances/{financeId}")
    public ApiResponse<FinanceInfo> getOneFinance(@PathVariable(value = "financeId") String id) {
        return fsi.getOneFiance(id);
    }

    @PostMapping(value = "/finances/filters")
    public ApiResponse<PagedData<FinanceInfo>> getAssociationFiances(@RequestBody Filters filters, @RequestParam int pagesize,
                                                                     @RequestParam int pagenum, @RequestParam String sortby) {
        return fsi.getAssociationFiances(filters, new PageQuery(pagenum, pagesize, sortby));
    }

    @GetMapping(value = "associations/{associationId}/balance")
    public ApiResponse<Float> getAssociationBalance(@PathVariable(value = "associationId") String associationId) {
        return fsi.getAssociationBalance(associationId);
    }

    @PutMapping(value = "/finances/{financeId}/accept")
    public ApiResponse<FinanceInfo> acceptFinanceApplication(@PathVariable(value = "financeId") String id) {
        return fsi.acceptFinanceApplication(id);
    }

    @PutMapping(value = "/finances/{financeId}/refuse")
    public ApiResponse<FinanceInfo> refuseFinanceApplication(@PathVariable(value = "financeId") String id) {
        return fsi.refuseFinanceApplication(id);
    }
}
