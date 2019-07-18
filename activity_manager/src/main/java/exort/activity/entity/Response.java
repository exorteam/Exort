package exort.activity.entity;

public class Response<T> {

    private T data;
    private String error;
    private String message;

    public Response(T data, String error, String message){
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
