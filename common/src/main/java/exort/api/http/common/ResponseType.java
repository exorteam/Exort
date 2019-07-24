package exort.api.http.common;

import exort.api.http.common.entity.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;

public class ResponseType<T> extends ParameterizedTypeReference<ApiResponse<T>> { }
