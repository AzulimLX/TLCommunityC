package tricolor.no1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tricolor.no1.Enum.Status.status;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>  implements Serializable {
    private Integer status;
    private String msg;
    private T data;

    public static Result success(Object data) {
        return new Result(200,"操作成功",data);
    }

    public static Result fail(Object data){
        return new Result(400,"操作失败",data);
    }
    public static Result success(Object data,String msg){
        Result result = new Result();
        result.setMsg(msg);
        return new Result(200,msg,data);
    }


    public static Result Tfail(Object data,String msg){
        Result result = new Result();
        result.setMsg(msg);
        return new Result(400,msg,data);
    }

    public static Result faill(status status,Object data)
    {
        return new Result(status.getCode(),status.getMessage(),data);
    }

}
