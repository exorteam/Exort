package exort.apiserver.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/test")
public class TestController {
	
	@GetMapping("/auth")
	public Map testAuthFilter(@RequestAttribute(name="id", required=false) Integer id){
		HashMap<String,Integer> res = new HashMap<>();
		if(id != null){
			res.put("id",id);
		}
		return res;
	}

}

