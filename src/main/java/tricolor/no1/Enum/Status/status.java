package tricolor.no1.Enum.Status;

public enum status implements UseStatus{
    Token_Error(401,"判断token过期，需要进行刷新");


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return status;
    }

    private Integer status;
    private String message;

    status(Integer status,String message)
    {
        this.status=status;
        this.message=message;
    }

}
