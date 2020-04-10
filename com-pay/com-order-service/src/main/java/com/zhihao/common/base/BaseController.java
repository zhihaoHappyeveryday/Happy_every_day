package com.zhihao.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: zhihao
 * @Date: 2020/4/2 17:19
 * @Description: 基础返回控制器
 * @Versions 1.0
 **/
public class BaseController {

    /**
     * 处理失败-返回错误信息
     *
     * @param code    状态码
     * @param message 错误信息
     * @param request 请求对象
     * @return  ResultData
     * @author: zhihao
     * @date: 2020/4/2
     */
    public  ResultData<?> retResultErrorCode(Integer code, String message, HttpServletRequest request) {
        return new ResultData(message, request.getRequestURI(), code);
    }

    /**
     * 处理成功 - 返回一个单个对象
     *
     * @param data    数据
     * @param request 请求对象
     * @return  ResultData
     * @author: zhihao
     * @date: 2020/4/2
     */
    public <T>  ResultData<T> retResultMessageData(T data, HttpServletRequest request) {
        return new ResultData(request.getRequestURI(), data, HttpStatus.OK.value());
    }

    /**
     * 处理成功 - 返回带有分页数据集
     *
     * @param req
     * @param pageAttribute
     * @param resultData
     * @return  ResultData<T>
     * @author: zhihao
     * @date: 2020/4/3
     */
    public <T>  ResultData<T> retMessagePaginationData(HttpServletRequest req, PageAttribute pageAttribute, T resultData) {
         ResultData<T> objectResultData = retResultMessageData(resultData, req);
        //设置分页属性
        objectResultData.setPageAttribute(pageAttribute);
        return objectResultData;
    }

    /**
     * 设置响应的分页属性
     *
     * @param page 分页对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author: zhihao
     * @date: 2020/4/2
     */
    public PageAttribute setPageAttribute(Page<?> page) {
        PageAttribute attribute = new PageAttribute();
        attribute.setPageNum((int) page.getCurrent());
        attribute.setPageSize((int) page.getSize());
        attribute.setPages((int) page.getPages());
        attribute.setTotal(page.getTotal());
        attribute.setFirstPage(page.hasPrevious());
        attribute.setLastPage(page.hasNext());
        return attribute;
    }

}
