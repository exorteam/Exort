package exort.apiserver.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserAdminResponse {
    Boolean isSysAdmin;
    List<String> assoAdmins;
}
