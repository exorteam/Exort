package exort.api.http.common;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
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

    protected <RT, ST> ApiResponse<RT> request(TypeToken<RT> token,
                                               ST body,
                                               HttpMethod method,
                                               String path,
                                               Object... pathVariables) {
        return exchange(url(path), method, new HttpEntity<>(body), getReference(token), pathVariables).getBody();
    }

    protected <RT, ST> ApiResponse<RT> request(TypeToken<RT> token,
                                               ST body,
                                               HttpMethod method,
                                               PageQuery pageQuery,
                                               String path,
                                               Object... pathVariables) {
        return exchange(url(path, pageQuery), method, new HttpEntity<>(body), getReference(token), pathVariables).getBody();
    }

    protected <RT> ApiResponse<RT> request(TypeToken<RT> token,
                                           HttpMethod method,
                                           String path,
                                           Object... pathVariables) {
        return exchange(url(path), method, null, getReference(token), pathVariables).getBody();
    }

    protected <RT> ApiResponse<RT> request(TypeToken<RT> token,
                                           HttpMethod method,
                                           PageQuery pageQuery,
                                           String path,
                                           Object... pathVariables) {
        return exchange(url(path, pageQuery), method, null, getReference(token), pathVariables).getBody();
    }


    /*
     *
     * Tricks
     *
     */

    private static <T> ParameterizedTypeReference<ApiResponse<T>> getReference(TypeToken<T> typeToken) {
        return ParameterizedTypeReferenceBuilder.fromTypeToken(
                new TypeToken<ApiResponse<T>>() { }.where(
                        new TypeParameter<T>() { }, typeToken
                )
        );
    }

    private static class ParameterizedTypeReferenceBuilder {

        public static <T> ParameterizedTypeReference<T> fromTypeToken(TypeToken<T> typeToken) {
            return new TypeTokenParameterizedTypeReference<>(typeToken);
        }

        private static class TypeTokenParameterizedTypeReference<T> extends ParameterizedTypeReference<T> {

            private final Type type;

            private TypeTokenParameterizedTypeReference(TypeToken<T> typeToken) {
                this.type = typeToken.getType();
            }

            @Override
            public Type getType() {
                return type;
            }

            @Override
            public boolean equals(Object obj) {
                return (this == obj || (obj instanceof ParameterizedTypeReference &&
                        this.type.equals(((ParameterizedTypeReference<?>) obj).getType())));
            }

            @Override
            public int hashCode() {
                return this.type.hashCode();
            }

            @Override
            public String toString() {
                return "ParameterizedTypeReference<" + this.type + ">";
            }

        }

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
