package exort.associationmanager.entity;


import lombok.Data;

@Data
public class ResponseBody<T> {
    private T data;
    private String error;
    private String message;

    public ResponseBody setAndGetResponsebody (T newData ,String newError , String newMessage){
        ResponseBody responseBody = new ResponseBody();
        responseBody.setData(newData);
        responseBody.setError(newError);
        responseBody.setMessage(newMessage);
        return responseBody;
    };

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
