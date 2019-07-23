package exort.auth.entity;

import lombok.Data;

@Data
public class RestResponse<T> {

	private T	   data;
	private String error;
	private String message;

	public RestResponse(T data,String error,String message){
		this.data = data;
		this.error = error;
		this.message = message;
	}
}
