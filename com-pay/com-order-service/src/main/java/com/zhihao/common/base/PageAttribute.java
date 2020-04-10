package com.zhihao.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "分页对象")
public class PageAttribute implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "当前页")
	private int pageNum;

	@ApiModelProperty(value = "每页的数量")
	private int pageSize;

	@ApiModelProperty(value = "总记录数")
	private long total;

	@ApiModelProperty(value = "总页数")
	private int pages;

	@ApiModelProperty(value = "是否是第一页")
	private boolean isFirstPage;

	@ApiModelProperty(value = "是否是最后一页")
	private boolean isLastPage;


	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	
}
