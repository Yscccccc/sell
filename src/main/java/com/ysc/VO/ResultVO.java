package com.ysc.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @param <T>
 */
@Data
public class ResultVO<T> {

    //错误码
    private Integer code;

    //提示信息
    //如果字段为必须的,要返回的结果不是null,则需赋初始值 private String msg = ""
    private String msg;

    //返回的具体内容
    private T data;
}
