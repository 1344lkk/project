package com.hzcwtech.mybatis;

/**
 * 分页参数类
 * 
 */
public class Pager {

    public static final int DEFAULT_PAGE_SIZE = 20;

    private int pageSize;
    private int currentPage;
    private int prevPage;
    private int nextPage;
    private int totalPage;
    private int totalCount;

    private String url;
  
    public String getUrl() {
    	return url;
    }
    
    public void setUrl(String url) {
    	this.url = url;
    }
    
    public String getHtml() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<nav>");
    	sb.append(String.format("<span class='total-count'>共 %d 条</span>", totalCount));
    	
    	
    	if (totalPage > 1) {
    		sb.append("<ul class='pagination'>");
    		
	    	if (prevPage != currentPage) {
	    		sb.append(String.format("<li><a href='#%d'>&laquo;</a></li>", prevPage));
	    	} else {
	    		sb.append("<li class='disabled'><span>&laquo;</span></li>");
	    	}
	    	
	    	int startPage = currentPage - 4;
	    	if (startPage < 1) startPage = 1;
	    	int endPage = startPage + 8;
	    	if (endPage > totalPage) endPage = totalPage;
	    	
	    	if (startPage > 5) {
	    		sb.append(String.format("<li><a href='#%d'>%d</a></li>", 1, 1));
	    		sb.append("<li>...</li>");
	    	}
	    	
	    	for(int i = startPage; i <= endPage; ++i) {
	    		if (i != currentPage) {
	    			sb.append(String.format("<li><a href='#%d'>%d</a></li>", i, i));
	    		} else {
	    			sb.append(String.format("<li class='active'><span>%d <span class='sr-only'>(current)</span></span></li>", i));
	    		}
	    	}
	    	
	    	if (totalPage - endPage > 4) {
	    		sb.append("<li>...</li>");
	    		sb.append(String.format("<li><a href='#%d'>%d</a></li>", totalPage, totalPage));
	    		
	    	}
	    	
	    	
	    	if (nextPage != currentPage) {
	    		sb.append(String.format("<li><a href='#%d'>&raquo;</a></li>", nextPage));
	    	} else {
	    		sb.append("<li class='disabled'><span>&raquo;</span></li>");
	    	}
	    	
	    	sb.append("</ul>");
    	}
    	
    	
    	sb.append("</nav>");
    	return sb.toString();
    }
    
    
    public Pager() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pager(Integer currentPage, Integer pageSize) {
    	if (currentPage == null || currentPage < 1) currentPage = 1;
        this.currentPage = currentPage;
        if (pageSize == null) pageSize = DEFAULT_PAGE_SIZE;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount; 
        this.totalPage = totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1);
        
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        
        prevPage = currentPage - 1;
        if (prevPage < 1) prevPage = 1;
        nextPage = currentPage + 1;
        if (nextPage > totalPage) nextPage = currentPage;
    }


}
