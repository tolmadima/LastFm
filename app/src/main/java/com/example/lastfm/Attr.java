package com.example.lastfm;

import com.google.gson.annotations.SerializedName;

public class Attr{

	@SerializedName("total")
	private String total;

	@SerializedName("perPage")
	private String perPage;

	@SerializedName("totalPages")
	private String totalPages;

	@SerializedName("page")
	private String page;

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setPerPage(String perPage){
		this.perPage = perPage;
	}

	public String getPerPage(){
		return perPage;
	}

	public void setTotalPages(String totalPages){
		this.totalPages = totalPages;
	}

	public String getTotalPages(){
		return totalPages;
	}

	public void setPage(String page){
		this.page = page;
	}

	public String getPage(){
		return page;
	}

	@Override
 	public String toString(){
		return 
			"Attr{" + 
			"total = '" + total + '\'' + 
			",perPage = '" + perPage + '\'' + 
			",totalPages = '" + totalPages + '\'' + 
			",page = '" + page + '\'' + 
			"}";
		}
}