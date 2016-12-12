package com.yusong.baseproject.http;

/**
 * Created by vzhihao on 2016/11/1.
 */

/**
 * 标准数据格式
 *
 * @param <T>
 */
public class Response<T> {


    /**
     * data : null
     * status : 1
     * msg : 修改密码成功
     */

    private T data;
    private int status;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
