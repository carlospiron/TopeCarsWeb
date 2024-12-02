package com.pinguela.topecars.web.util;

public class PaginationUtils {
	
	  public static int calculateFromPage(int currentPage, int browsablePageCount) {
	        int fromPage = currentPage - browsablePageCount / 2;
	        return Math.max(fromPage, 1);
	    }

	    public static int calculateToPage(int currentPage, int browsablePageCount, int lastPage) {
	        int toPage = currentPage + browsablePageCount / 2;
	        return Math.min(toPage, lastPage);
	    }

	    public static int calculateLastPage(int totalItems, int pageSize) {
	        return (int) Math.ceil((double) totalItems / pageSize);
	    }

}
