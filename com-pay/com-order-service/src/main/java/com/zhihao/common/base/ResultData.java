package com.zhihao.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: zhihao
 * @Date: 2020/4/2 18:47
 * @Description: 返回结果
 * @Versions 1.0
 **/
@ApiModel(description = "结果响应类")
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(value = JsonInclude.Include.NON_NULL) //为null,不返回前端
    @ApiModelProperty(value = "错误信息", dataType = "String")
    private String message;

    @ApiModelProperty(value = "请求路径", required = true, dataType = "String")
    private String urlPath;

    @JsonInclude(value = JsonInclude.Include.NON_NULL) //为null,不返回前端
    @ApiModelProperty(value = "响应数据")
    private T date;

    @ApiModelProperty(value = "响应状态码", required = true, dataType = "int")
    private Integer code;

    @JsonInclude(value = JsonInclude.Include.NON_NULL) //为null,不返回前端
    @ApiModelProperty(value = "分页属性")
    private PageAttribute pageAttribute;

    public ResultData() {
    }

    /**
     * 返回单个对象
     *
     * @param urlPath
     * @param date
     * @param code
     * @return
     * @author: zhihao
     * @date: 2020/4/3
     */
    public ResultData(String urlPath, T date, Integer code) {
        this.urlPath = urlPath;
        this.date = date;
        this.code = code;
    }

    /**
     * 带分页属性返回
     *
     * @param urlPath
     * @param date
     * @param code
     * @param pageAttribute
     * @return
     * @author: zhihao
     * @date: 2020/4/3
     */
    public ResultData(String urlPath, T date, Integer code, PageAttribute pageAttribute) {
        this.urlPath = urlPath;
        this.date = date;
        this.code = code;
        this.pageAttribute = pageAttribute;
    }

    /**
     * 错误返回
     *
     * @param message
     * @param urlPath
     * @param code
     * @return
     * @author: zhihao
     * @date: 2020/4/3
     */
    public ResultData(String message, String urlPath, Integer code) {
        this.message = message;
        this.urlPath = urlPath;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public PageAttribute getPageAttribute() {
        return pageAttribute;
    }

    public void setPageAttribute(PageAttribute pageAttribute) {
        this.pageAttribute = pageAttribute;
    }
}
