package exort.associationmanager.entity;

import java.time.format.DateTimeFormatter;

import java.util.Date;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Application {
    @Id
    Integer Id;
    Integer applicantId;
    Integer type;
    Association asso;
    String materials[];
    Date createTime;
    Date handleTime;
    Integer state;
}
