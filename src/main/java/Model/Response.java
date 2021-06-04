package Model;

public class Response {
    public String message;
    public Integer status;

    public Response(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
