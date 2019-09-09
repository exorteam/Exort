package exort.apiserver.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.Application;
import exort.api.http.review.entity.Operation;
import exort.api.http.review.entity.details.ActivitySignUp;
import exort.api.http.review.entity.details.AssociationInfo;
import exort.api.http.review.entity.details.AssociationMemberSignUp;
import exort.api.http.review.entity.receipt.AssociationMemberReceipt;
import exort.api.http.review.entity.receipt.NormalReceipt;
import exort.api.http.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/applications")
public class ReviewController {

    @Autowired
    private ReviewService rs;

    @GetMapping("/list/user")
    public ApiResponse<PagedData<Application>> listUserApplication(
            @RequestAttribute("id") Long id,
            @RequestParam(name="state", required=false) String state,
            @RequestParam(name="type", required=false) String type,
            PageQuery pageQuery) {
        return rs.listApplication(id, type, state, pageQuery);
    }

    @GetMapping("/list/activity_sugnup/{aid}")
    public ApiResponse<PagedData<Application<ActivitySignUp, NormalReceipt>>> listActivitySignUp(@PathVariable("aid") String aid,
                                                                  @RequestParam(name="state", required=false) String state,
                                                                  PageQuery pageQuery) {
        return rs.listActivitySignUp(aid, state, pageQuery);
    }

    @GetMapping("/list/association_member_signup")
    public ApiResponse<PagedData<Application<AssociationMemberSignUp, AssociationMemberReceipt>>> listAssociationMemberSignUp(
            @RequestParam("aid") String aid, @RequestParam(name="state", required=false) String state,
            @RequestParam(name="departmentId", required=false) Integer did, PageQuery pageQuery) {
        return rs.listAssociationMemberSignUp(aid, did, state, pageQuery);
    }

    @GetMapping("/list/association_creation")
    public ApiResponse<PagedData<Application<AssociationInfo, NormalReceipt>>> listAssociationCreation(@RequestParam(name="state", required=false) String state, PageQuery pageQuery) {
        return rs.listAssociationInfo(state, pageQuery);
    }

    @PostMapping("/create_association")
    public ApiResponse<Application<AssociationInfo, NormalReceipt>> createAssociation(@RequestAttribute("id") Long id, @RequestBody AssociationInfo info) {
        return rs.createAssociation(id, info);
    }

    @PostMapping("/signup_activity")
    public ApiResponse<Application<ActivitySignUp, NormalReceipt>> signUpActivity(@RequestAttribute("id") Long id, @RequestBody ActivitySignUp signUp) {
        return rs.signUpActivity(id, signUp);
    }

    @PostMapping("/signup_association_member")
    public ApiResponse<Application<AssociationMemberSignUp, AssociationMemberReceipt>> signUpAssociationMember(@RequestAttribute("id") Long id, @RequestBody AssociationMemberSignUp signUp) {
        return rs.signUpAssociationMember(id, signUp);
    }

    @PostMapping("/accept/{aid}")
    public ApiResponse<Application> accept(@RequestAttribute("id") Long id, @PathVariable("aid") Long aid, @RequestBody Object receipt) {
        return rs.accept(aid, id, receipt);
    }
    @PostMapping("/cancel/{aid}")
    public ApiResponse<Application> cancel(@RequestAttribute("id") Long id, @PathVariable("aid") Long aid, @RequestBody Object receipt) {
        return rs.cancel(aid, id, receipt);
    }
    @PostMapping("/refuse/{aid}")
    public ApiResponse<Application> refuse(@RequestAttribute("id") Long id, @PathVariable("aid") Long aid, @RequestBody Object receipt) {
        return rs.refuse(aid, id, receipt);
    }

}
