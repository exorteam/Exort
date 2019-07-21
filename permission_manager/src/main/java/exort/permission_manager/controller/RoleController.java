package exort.permission_manager.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.OperationBatch;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.entity.Role;
import exort.permission_manager.entity.ExortPerm;
import exort.permission_manager.entity.ExortRole;
import exort.permission_manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class RoleController {

    @Autowired
    private RoleService rs;

    @PostMapping("/roles")
    public ApiResponse<Role> createRole(
            @RequestBody Role roleArg) {
        if (!Pattern.matches("[a-zA-Z][a-zA-Z0-9_]*", roleArg.getName())) {
            throw new ApiError(400, "invalidName", "Invalid name for role. A valid name should be /\\w[\\d\\w_]*/");
        }
        if (roleArg.getDescription() == null) {
            roleArg.setDescription("");
        }
        ExortRole role = rs.create(roleArg.getName(), roleArg.getDescription());

        if (role == null) {
            throw new ApiError(409, "conflict", "Role named \"" + roleArg.getName() + "\" exists.");
        }
        return new ApiResponse<>(new Role(role.getId(), role.getDescription()));
    }

    @DeleteMapping("/roles/{name}")
    public ApiResponse deleteRole(
            @PathVariable("name") String name) {
        rs.delete(name);
        return ApiResponse.emptyResponse();
    }

    @PutMapping("/roles/{name}")
    public ApiResponse<Role> updateRole(
            @PathVariable("name") String name,
            @RequestBody Role roleArg) {
        ExortRole role = rs.update(name, roleArg.getDescription());
        if (role == null) {
            throw new ApiError(404, "roleNotFound", "Role named \"" + name + "\" not exists.");
        }
        return new ApiResponse<>(new Role(role.getId(), role.getDescription()));
    }

    @GetMapping("/roles/{name}")
    public ApiResponse<Role> getRole(
            @PathVariable("name") String name) {
        ExortRole role = rs.get(name);
        if (role == null) {
            throw new ApiError(404, "roleNotFound", "Role named \"" + name + "\" not exists.");
        }
        return new ApiResponse<>(new Role(role.getId(), role.getDescription()));
    }

    @PutMapping("/roles/{name}/permissions")
    public ApiResponse<List<Permission>> updatePermissions(
            @PathVariable("name") String name,
            @RequestBody OperationBatch<String> ops) {
        if (ops.getOperation() == null) {
            ops.setOperation("add");
        }
        if (ops.getArgs() == null) {
            ops.setArgs(new ArrayList<>());
        }
        List<ExortPerm> perms;
        if (ops.getOperation().toLowerCase().equals("add")) {
            perms = rs.grantPerms(name, ops.getArgs());
        } else if (ops.getOperation().toLowerCase().equals("remove")) {
            perms = rs.revokePerms(name, ops.getArgs());
        } else {
            throw new ApiError(400, "invalidOperation", "Invalid operation, should be \"add\" or \"remove\".");
        }
        List<Permission> res = new ArrayList<>();
        for (ExortPerm perm : perms) {
            res.add(new Permission(perm.getId(), perm.getCategory(), perm.getDescription()));
        }
        return new ApiResponse<>(res);
    }

    @GetMapping("/roles/{name}/permissions")
    public ApiResponse<List<Permission>> listPermissions(
            @PathVariable("name") String name) {
        List<ExortPerm> perms = rs.listPerms(name);
        List<Permission> res = new ArrayList<>();
        for (ExortPerm perm : perms) {
            res.add(new Permission(perm.getId(), perm.getCategory(), perm.getDescription()));
        }
        return new ApiResponse<>(res);
    }
}
