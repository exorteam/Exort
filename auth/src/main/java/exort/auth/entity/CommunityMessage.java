package exort.auth.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class CommunityMessage {

	@Id
	private int id;

	private Integer senderId;
	private String content;
	private Date timestamp;
	private boolean read;

}
