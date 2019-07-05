package com.exort.association_member_management.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Application {
    @Id
    @GeneratedValue
    int id;
    int user_id;
    int association;
    List<Integer> material_ids;
    Date create_at;
    int state;
}
