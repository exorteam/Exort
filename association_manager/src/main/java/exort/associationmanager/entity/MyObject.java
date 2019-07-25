package exort.associationmanager.entity;

import lombok.Data;

import java.util.List;

@Data
public class MyObject {
    private String name;
    private String description;
    private List<String> tags;
    private  String logo;
    private  Integer assoId;
    private  String reason;
}

