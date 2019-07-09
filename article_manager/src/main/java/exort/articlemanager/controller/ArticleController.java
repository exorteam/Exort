package exort.articlemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.articlemanager.entity.Article;
import exort.articlemanager.entity.ArticleFilterParams;
import exort.articlemanager.service.ArticleService;

@RestController
@RequestMapping(value = {"/association-member-management"})
public class ArticleController {
	@Autowired
	private ArticleService service;

	@GetMapping("/create")
	public Article createArticle(@RequestBody Article article){
		return service.createArticle(article);
	}

}

