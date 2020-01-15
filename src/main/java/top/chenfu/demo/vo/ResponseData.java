package top.chenfu.demo.vo;

import top.chenfu.demo.util.Constant;

public class ResponseData<T> {

    private Boolean state;

    private String desc;

    private String msg;

    private T data;

    public ResponseData() {
    }

    public ResponseData(T data) {
        this.data = data;
    }

    public ResponseData(boolean state, String desc, String msg, T data) {
        this.state = state;
        this.desc = desc;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData(boolean state, String desc, String msg) {
        this.state = state;
        this.desc = desc;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseData<Object> success(boolean state, String desc, String msg, Object data) {
        return new ResponseData<>(state, desc, msg, data);
    }

    public static ResponseData defaultSuccess(Object data) {
        return success(true, Constant.OK, Constant.OK, data);
    }

}
