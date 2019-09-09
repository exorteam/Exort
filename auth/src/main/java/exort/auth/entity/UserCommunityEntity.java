package exort.auth.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserCommunityEntity {

	@Id
	private int id;

	private List<String> subscribed;
	private List<CommunityMessage> messages;

}
