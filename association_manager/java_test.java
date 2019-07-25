public class ResponseBody<T> {
    private T data;
    private String error;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
