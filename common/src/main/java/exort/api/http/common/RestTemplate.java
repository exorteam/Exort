package exort.api.http.common;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

public class RestTemplate extends org.springframework.web.client.RestTemplate {

    private String protocol = "http";
    private String endpoint = "localhost";

    private void init() {
        setRequestFactory(new HttpComponentsClientRestfulHttpRequestFactory());
        setErrorHandler(new ServiceResponseErrorHandler());
    }
    public RestTemplate() {
        super();
        init();
    }
    public RestTemplate(String endpoint) {
        super();
        this.endpoint = endpoint;
        init();
    }
    public RestTemplate(String protocol, String endpoint) {
        super();
        this.protocol = protocol;
        this.endpoint = endpoint;
        init();
    }

    protected void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    protected void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String root() {
        return protocol + "://" + endpoint;
    }

    public String url(String path) {
        return root() + path;
    }

    public String url(String path, PageQuery pq) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url(path));
        if (pq.getPageNum() != null) {
            builder = builder.queryParam("pageNum", pq.getPageNum());
        }
        if (pq.getPageSize() != null) {
            builder = builder.queryParam("pageSize", pq.getPageSize());
        }
        if (pq.getSortBy() != null) {
            builder = builder.queryParam("sortBy", pq.getSortBy());
        }
        return builder.build().toString();
    }

    protected <RT, ST> ApiResponse<RT> request(ResponseType<RT> rt,
                                               ST body,
                                               HttpMethod method,
                                               String path,
                                               Object... pathVariables) {
        return exchange(url(path), method, new HttpEntity<>(body), rt, pathVariables).getBody();
    }

    protected <RT, ST> ApiResponse<RT> request(ResponseType<RT> rt,
                                               ST body,
                                               HttpMethod method,
                                               PageQuery pageQuery,
                                               String path,
                                               Object... pathVariables) {
        return exchange(url(path, pageQuery), method, new HttpEntity<>(body), rt, pathVariables).getBody();
    }

    protected <RT> ApiResponse<RT> request(ResponseType<RT> rt,
                                           HttpMethod method,
                                           String path,
                                           Object... pathVariables) {
        return exchange(url(path), method, null, rt, pathVariables).getBody();
    }

    protected <RT> ApiResponse<RT> request(ResponseType<RT> rt,
                                           HttpMethod method,
                                           PageQuery pageQuery,
                                           String path,
                                           Object... pathVariables) {
        return exchange(url(path, pageQuery), method, null, rt, pathVariables).getBody();
    }

    private static final class HttpComponentsClientRestfulHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (httpMethod == HttpMethod.GET) {
                return new HttpGetRequestWithEntity(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    // Prevent from ignoring request body for get method
    private static final class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
        public HttpGetRequestWithEntity(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }

    // Prevent from throwing exceptions when got non-4xx responses
    private static final class ServiceResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException { }
    }

}

