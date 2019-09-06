package exort.comment.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.finance.entity.ApplyInfo;
import exort.api.http.finance.entity.Filters;
import exort.comment.entity.Finance;
import exort.comment.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FinanceController {
    @Autowired
    private FinanceService financeService;

    @PostMapping(value = "/finances")
    public ApiResponse<Finance> createFinance(@RequestBody ApplyInfo applyInfo) {

        Finance finance = financeService.createFinance(
                applyInfo.getProjectName(),
                applyInfo.getAssociationId(),
                applyInfo.getAssociationName(),
                applyInfo.getContent(),
                applyInfo.getSupervisor(),
                applyInfo.getTransactionAmount(),
                applyInfo.getDirection(),
                applyInfo.getOperatorId());

        return new ApiResponse<>(finance);
    }

    @DeleteMapping(value = "/finances/{financeId}")
    public ApiResponse<Finance> deleteFinance(@PathVariable(value = "financeId") String id) {
        Finance ret = financeService.deleteFiance(id);

        if (ret == null) {
            throw new ApiError(404, "Finance not found!", "财务记录不存在");
        }
        return new ApiResponse<>(ret);
    }

    @PutMapping(value = "/finances/{financeId}")
    public ApiResponse<Finance> updateFinance(@RequestBody Finance finance, @PathVariable(value = "financeId") String id) {
        Finance ret = financeService.updateFinance(finance, id);
        return new ApiResponse<>(ret);
    }

    @GetMapping(value = "/finances/{financeId}")
    public ApiResponse<Finance> getOneFinance(@PathVariable(value = "financeId") String id) {
        Finance ret = financeService.getOneFiance(id);
        if (ret == null) {
            throw new ApiError(404, "Finance not found!", "财务记录不存在");
        }
        return new ApiResponse<>(ret);
    }

    @PostMapping(value = "/finances/filters")
    public ApiResponse<PagedData<Finance>> getAssociationFiances(@RequestBody Filters filters, PageQuery pageQuery) {
        return new ApiResponse<>(financeService.getAssociationFiances(filters, pageQuery));
    }

    @GetMapping(value = "associations/{associationId}/balance")
    public ApiResponse<Float> getAssociationBalance(@PathVariable(value = "associationId") String associationId) {
        return new ApiResponse<>(financeService.getAssociationBalance(associationId));
    }
}
