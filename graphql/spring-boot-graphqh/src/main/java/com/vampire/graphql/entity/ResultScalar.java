package com.vampire.graphql.entity;

public class ResultScalar {

    private ResultScalar() {}

    private ResultCodeEnum result;
    private String msg;

    public static ResultScalar error(String msg) {
        ResultScalar rs = new ResultScalar();
        rs.result = ResultCodeEnum.error;
        rs.setMsg(msg);
        return rs;
    }

    public static ResultScalar error() {
        ResultScalar rs = new ResultScalar();
        rs.result = ResultCodeEnum.error;
        rs.setMsg("程序内部错误");
        return rs;
    }

    public static ResultScalar success(String msg) {
        ResultScalar rs = new ResultScalar();
        rs.result = ResultCodeEnum.error;
        rs.setMsg(msg);
        return rs;
    }

    public static ResultScalar fail(String msg) {
        ResultScalar rs = new ResultScalar();
        rs.result = ResultCodeEnum.error;
        rs.setMsg(msg);
        return rs;
    }

    public ResultCodeEnum getResult() {
        return result;
    }

    public void setResult(ResultCodeEnum result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
