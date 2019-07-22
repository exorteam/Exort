package exort.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.auth.entity.UserInfo;
import exort.auth.repository.InfoRepository;
import exort.auth.service.InfoService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoRepository infoRepository;

	public UserInfo getUserInfo(int id){
		if(!infoRepository.existsById(id)){
			return null;
		}else{
			return infoRepository.findById(id).get();
		}
	}

	public boolean updateUserInfo(UserInfo info){
		log.info(info.getNickname());
		infoRepository.save(info);
		return true;
	}

	public boolean disableUser(int id,boolean disabled){
		if(!infoRepository.existsById(id)){
			return false;
		}

		UserInfo info = infoRepository.findById(id).get();
		info.setEnabled(!disabled);
		infoRepository.save(info);

		return true;
	}
}
