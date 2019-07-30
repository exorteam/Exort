package exort.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import exort.auth.entity.UserInfo;
import exort.auth.repository.InfoRepository;
import exort.auth.service.InfoService;

@Service
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
		if(!infoRepository.existsById(info.getId())){
			return false;
		}

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

	public List<UserInfo> getUserInfoInBatch(Iterable<Integer> ids){
		ArrayList<UserInfo> res = new ArrayList<>();
		infoRepository.findAllById(ids).forEach((UserInfo info) -> res.add(info));
		return res;
	}

	public Page<UserInfo> getUserInfoByPage(int pageNum,int pageSize,String sortBy){
		return infoRepository.findAll(PageRequest.of(pageNum,pageSize,Sort.by(sortBy)));
	}
}
