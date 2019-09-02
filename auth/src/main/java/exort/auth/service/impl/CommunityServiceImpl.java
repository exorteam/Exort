package exort.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.auth.component.AutoIncIdGenerator;
import exort.auth.entity.CommunityMessage;
import exort.auth.entity.UserCommunityEntity;
import exort.auth.repository.CommunityRepository;
import exort.auth.service.CommunityService;

@Service
public class CommunityServiceImpl implements CommunityService {

	@Autowired
	private CommunityRepository repo;
	@Autowired
	private AutoIncIdGenerator autoId;

	// Operations on root entity
	// Creation and updation
	// No deletion according to principles on account
	// Init both lists when creating
	public UserCommunityEntity createUser(int uid){
		UserCommunityEntity e = new UserCommunityEntity();
		e.setId(uid);
		e.setSubscribed(new ArrayList<String>());
		e.setMessages(new ArrayList<CommunityMessage>());
		return repo.save(e);
	}

	public UserCommunityEntity updateUser(UserCommunityEntity e){
		if(!repo.existsById(e.getId())){
			return null;
		}

		return repo.save(e);
	}

	// Operations on messages
	// treat message box as list
	// post(update), marking read(update), dropping messages(update)
	public Integer postMessage(int uid,CommunityMessage msg){
		if(!repo.existsById(uid)){
			return null;
		}

		msg.setId(autoId.getNextId("User["+String.valueOf(uid)+"]Msg"));
		msg.setTimestamp(new Date());
		msg.setRead(false);

		UserCommunityEntity e = repo.findById(uid).get();
		List<CommunityMessage> msgs = e.getMessages();
		msgs.add(msg);
		e.setMessages(msgs);
		repo.save(e);

		return msgs.size();

	}

	public Integer markMessageAsReadById(int uid,int msgId){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();
		List<CommunityMessage> msgs = e.getMessages();
		
		int numOfUnread = 0;
		int pos = -1;
		for(int i=0;i<msgs.size();i++){
			CommunityMessage msg = msgs.get(i);
			if(!msg.isRead()){
				++numOfUnread;
			}
			if(msg.getId() == msgId){
				pos = i;
			}
		}

		if(pos != -1){
			CommunityMessage mm = msgs.get(pos);
			mm.setRead(true);

			msgs.set(pos,mm);
			e.setMessages(msgs);
			repo.save(e);

			return numOfUnread;
		}

		return null;
	}

	public Integer markAllMessageAsRead(int uid){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();
		final List<CommunityMessage> msgs = e.getMessages();
		List<CommunityMessage> mms = new ArrayList<CommunityMessage>();

		for(int i=0;i<msgs.size();i++){
			CommunityMessage msg = msgs.get(i);
			msg.setRead(true);
			mms.add(msg);
		}
		e.setMessages(mms);
		repo.save(e);

		return mms.size();
	}

	public Integer dropMessageById(int uid,int msgId){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();
		List<CommunityMessage> msgs = e.getMessages();

		for(int i=0;i<msgs.size();i++){
			CommunityMessage msg = msgs.get(i);
			if(msg.getId() == msgId){
				msgs.remove(i);
				e.setMessages(msgs);
				repo.save(e);
				return msgs.size();
			}
		}

		return null;
	}

	public Integer dropAllMessage(int uid){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();
		List<CommunityMessage> msgs = e.getMessages();

		final int num = msgs.size();
		msgs.clear();
		e.setMessages(msgs);
		repo.save(e);

		return num;
	}

	public List<CommunityMessage> getMessagesForUser(int uid){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();

		return e.getMessages();
	}

	// Operations on subscribe list
	// add and remove entries(update)
	// and query
	public List<String> addToSubscribed(int uid,List<String> assoIds){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();
		List<String> ss = e.getSubscribed();
		for(int i=0;i<assoIds.size();i++){
			final String assoId = assoIds.get(i);
			if(!ss.contains(assoId)){
				ss.add(assoId);
			}
		}
		e.setSubscribed(ss);
		repo.save(e);

		return ss;
	}

	public List<String> removeFromSubscribed(int uid,List<String> assoIds){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();
		List<String> ss = e.getSubscribed();
		for(int i=0;i<assoIds.size();i++){
			final String assoId = assoIds.get(i);
			if(ss.contains(assoId)){
				ss.remove(assoId);
			}
		}
		e.setSubscribed(ss);
		repo.save(e);

		return ss;
	}

	public List<String> listSubscribed(int uid){
		if(!repo.existsById(uid)){
			return null;
		}

		UserCommunityEntity e = repo.findById(uid).get();

		return e.getSubscribed();
	}
}
