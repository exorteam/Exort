package exort.articlemanager.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AutoIncIdGenerator {

	@Autowired
	private MongoTemplate 	mt;

	private final String 	TABLE_NAME = "auto_id";
	private final int 	 	START_NUM  = 1;

	@Data
	@AllArgsConstructor
	private class AutoIdEntity {
		private String 	name;
		private Integer	count;
	}

	public Integer getNextId(String name){
		if(!checkIfExistByName(name)){
			if(!createNewAutoId(name))return null;
		}

		final Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		final Update update = new Update();
		update.inc("count");
		final Integer id = mt.findAndModify(query,update,AutoIdEntity.class,TABLE_NAME).getCount();
		return id;
	}

	public void removeName(String name){
		final Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		mt.findAndRemove(query,AutoIdEntity.class,TABLE_NAME);
	}

	private boolean checkIfExistByName(String name){
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return (mt.findOne(query,AutoIdEntity.class,TABLE_NAME) != null);
	}

	private boolean createNewAutoId(String name){
		log.info("Create new mongo AutoIncId for name: "+name);
		AutoIdEntity e = new AutoIdEntity(name,START_NUM);
		return (mt.save(e,TABLE_NAME) != null);
	}
}

