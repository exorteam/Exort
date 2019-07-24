package exort.associationmanager.controller;


import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssociationManagerController{

    @Autowired
    private AssociationService service;

    @GetMapping("/associations")
    public ApiResponse listAssociations(@RequestBody AssociationFilterParams body, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize ){
//        System.out.println(body.toString());
        if(false){
            throw new ApiError(400,"ads","asd");
        }
        return service.listAssociations(body,pageNum,pageSize);
    }
//naive
//    @GetMapping("/associations")
//    public ApiResponse listAssociations(@RequestParam("keyword") String keyword,@RequestParam("tags") String tags,@RequestParam("state") Integer state,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize ){
//        AssociationFilterParams body=new AssociationFilterParams();
//        body.setKeyword(keyword);
//        List<String> laozizhenshicaole = new ArrayList<String>(Arrays.asList(tags.split(",")));
//        body.setTags(laozizhenshicaole);
//        body.setState(state);
//        return service.listAssociations(body,pageNum,pageSize);
//    }
//professional


    @GetMapping("/associations/{assoId}")
    public ApiResponse getAssociation(@PathVariable(value="assoId") String assoId){
        return service.getAssociation(assoId);
    }
//origin
    @PostMapping("/associations")
    public ApiResponse createAssociation(@RequestBody AssociationInfo body){
        return  service.createAssociation(body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }

//    @PostMapping("/associations")
//    public ApiResponse createAssociation(@RequestBody AssociationInfo body){
//        return  service.createAssociation(body.getData().getName(),body.getData().getDescription(),body.getData().getTags(),body.getData().getLogo());
//    }

    @DeleteMapping("/associations/{assoId}")
    public ApiResponse deleteAssociation(@PathVariable(value="assoId") String assoId ){
        return  service.deleteAssociation(assoId);
    }
// origin
    @PutMapping("/associations/{assoId}")
    public ApiResponse editAssociation(@RequestBody AssociationInfo body,@PathVariable(value="assoId") String assoId ){
        return service.editAssociation(assoId, body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }

//    @PutMapping("/associations/{assoId}")
//    public ApiResponse editAssociation(@RequestBody AssociationInfo body,@PathVariable(value="assoId") String assoId ){
//        return service.editAssociation(assoId, body.getData().getName(),body.getData().getDescription(),body.getData().getTags(),body.getData().getLogo());
//    }

    @PutMapping("/associations/{assoId}/state")
    public ApiResponse patchAssociation(@RequestBody PatchAssociationInfo body,@PathVariable(value="assoId") String assoId ){
        return service.patchAssociation(assoId,body.getType(),body.getDescription());
    }

    @PostMapping("/callback")
    public ApiResponse handleAssociationApplication(@RequestBody ApplicationAssociationInfo body){
        return service.handleAsoociationApplication(body.getUserId(),body.getEvent(),body.getApplication());
    }

//    @GetMapping("/test")
//    public boolean createData(){
//        return service.createTestData();
//    }

}
