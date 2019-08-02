package exort.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.auth.entity.UserInfo;
import exort.auth.repository.InfoRepository;
import exort.auth.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoRepository infoRepo;

	public ApiResponse<UserInfo> getUserInfo(int id){
		if(!infoRepo.existsById(id)){
			throw new ApiError(403,"QueryErr","No such user information when getting with id: "+String.valueOf(id));
		}

		return new ApiResponse<UserInfo>(infoRepo.findById(id).get());
	}

	public ApiResponse<UserInfo> updateUserInfo(int id,UserInfo info){
		if(id != info.getId()){
			throw new ApiError(403,"QueryErr","Id args differ when updating");
		}
		if(!infoRepo.existsById(id)){
			throw new ApiError(403,"QueryErr","No such user information when updating with id: "+String.valueOf(id));
		}

		infoRepo.save(info);
		return new ApiResponse(info);
	}

	public ApiResponse<Boolean> disableUser(int id,boolean disabled){
		if(!infoRepo.existsById(id)){
			throw new ApiError(403,"QueryErr","No such user information when diabling user with id: "+String.valueOf(id));
		}

		UserInfo info = infoRepo.findById(id).get();
		info.setEnabled(!disabled);
		infoRepo.save(info);

		return new ApiResponse(Boolean.TRUE);
	}

	public ApiResponse<List<UserInfo>> getUserInfoInBatch(Iterable<Integer> ids){
		ArrayList<UserInfo> res = new ArrayList<>();
		infoRepo.findAllById(ids).forEach((UserInfo info) -> res.add(info));
		return new ApiResponse<List<UserInfo>>(res);
	}

	public ApiResponse<List<UserInfo>> getUserInfoByPage(int pageNum,int pageSize,String sortBy){
		return new ApiResponse<List<UserInfo>>(infoRepo.findAll(PageRequest.of(pageNum,pageSize,Sort.by(sortBy))).getContent());
	}
}
