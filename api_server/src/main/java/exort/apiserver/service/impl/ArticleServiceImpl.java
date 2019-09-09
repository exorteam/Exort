package exort.apiserver.service.impl;

import java.util.List;

import com.google.common.reflect.TypeToken;

import exort.api.http.common.entity.PageQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PagedData;
import exort.apiserver.service.ArticleService;

@Service
public class ArticleServiceImpl extends RestTemplate implements ArticleService {

	@Value("${exort.article.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.article.endpoint:localhost}")
    public void setEndpoint(String endpoint) {super.setEndpoint(endpoint);}

	private final String urlBase = "/articles";

	public ApiResponse createArticle(Article e){
		return request(new TypeToken<Article>(){},
				e,
				HttpMethod.POST,
				urlBase);
	}

	public ApiResponse deleteArticle(int id){
		return request(new TypeToken<Boolean>(){},
				HttpMethod.DELETE,
				urlBase+"/{id}",
				id);
	}

	public ApiResponse updateArticle(int id,Article e){
		return request(new TypeToken<Boolean>(){},
				e,
				HttpMethod.PUT,
				urlBase+"/{id}",
				id);
	}

	public ApiResponse getArticle(int id){
		return request(new TypeToken<Article>(){},
				HttpMethod.GET,
				urlBase+"/{id}",
				id);
	}

	public ApiResponse publishArticle(int id,boolean publish){
		return request(new TypeToken<Boolean>(){},
				HttpMethod.PATCH,
				urlBase+"/{id}?publish="+String.valueOf(publish),
				id);
	}

	public ApiResponse listArticle(ArticleFilterParam param, PageQuery pageQuery){
		return request(new TypeToken<PagedData<Article>>(){},
				param,
				HttpMethod.GET,
				pageQuery,
				urlBase);
	}

	public ApiResponse listArticleWithAssociation(List<String> assoIds, PageQuery pageQuery){
		return request(new TypeToken<PagedData<Article>>(){},
				assoIds,
				HttpMethod.POST,
				pageQuery,
				urlBase+"/asso");
	}

}
