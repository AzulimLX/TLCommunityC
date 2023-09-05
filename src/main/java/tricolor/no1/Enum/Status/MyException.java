package tricolor.no1.Enum.Status;

import lombok.Data;


public class MyException extends RuntimeException {
    private String message;
    private Integer status;
    public MyException(String message){
        this.message = message;
    }
    public String getMessage(){return message;}
    public Integer getStatus(){return status;}

    public MyException(status status)
    {
        this.status = status.getCode();
        this.message = status.getMessage();
    }

}
