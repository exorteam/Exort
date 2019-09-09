package exort.permission_manager.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Permission;
import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class PermController {

    @Autowired
    private PermService ps;

    @PostMapping("/permissions")
    public ApiResponse<Permission> createPermission(
            @RequestBody Permission permArg) {
        if (permArg.getName() == null || !Pattern.matches("[a-zA-Z][a-zA-Z0-9_]*", permArg.getName())) {
            throw new ApiError(400, "invalidName", "Invalid name for permission. A valid name should be /\\w[\\d\\w_]*/");
        }
        if (permArg.getCategory() == null) {
            permArg.setCategory("");
        }
        if (permArg.getDescription() == null) {
            permArg.setDescription("");
        }
        ExortPerm perm = ps.create(permArg.getName(), permArg.getCategory(), permArg.getDescription());

        if (perm == null) {
            throw new ApiError(409, "conflict", "Permission named \"" + permArg.getName() + "\" exists.");
        }
        return new ApiResponse<>(new Permission(perm.getId(), perm.getCategory(), perm.getDescription()));
    }

    @DeleteMapping("/permissions/{name}")
    public ApiResponse deletePermission(
            @PathVariable("name") String name){
        ps.delete(name);
        return ApiResponse.emptyResponse();
    }

    @PutMapping("/permissions/{name}")
    public ApiResponse<Permission> updatePermission(
            @PathVariable("name") String name,
            @RequestBody Permission permArg) {
        ExortPerm perm = ps.update(name, permArg.getCategory(), permArg.getDescription());
        if (perm == null) {
            throw new ApiError(404, "permissionNotFound", "Permission named \"" + name + "\" not exists.");
        }
        return new ApiResponse<>(new Permission(perm.getId(), perm.getCategory(), perm.getDescription()));
    }
    
    @GetMapping("/permissions/{name}")
    public ApiResponse<Permission> getPermission(
            @PathVariable("name") String name) {
        ExortPerm perm = ps.get(name);
        if (perm == null) {
            throw new ApiError(404, "permissionNotFound", "Permission named \"" + name + "\" not exists.");
        }
        return new ApiResponse<>(new Permission(perm.getId(), perm.getCategory(), perm.getDescription()));
    }

    @GetMapping("/permissions")
    public ApiResponse<List<Permission>> listPermissions(
            @RequestBody(required = false) Permission permArg) {
        List<Permission> res = new ArrayList<>();
        List<ExortPerm> perms;
        if (permArg == null || permArg.getCategory() == null) {
            perms = ps.list();
        } else {
            perms = ps.list(permArg.getCategory());
        }
        for (ExortPerm perm: perms) {
            res.add(new Permission(perm.getId(), perm.getCategory(), perm.getDescription()));
        }
        return new ApiResponse<>(res);
    }
}
