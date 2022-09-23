package ar.edu.itba.getaway.models.pagination;

import java.util.Collection;

public class PaginatedResult<T>{

    private final boolean isLast, isFirst;
    private int page;
    private int itemsPerPage;
    private int totalItems;
    private final int totalPages;


    private Collection<T> results;

    public PaginatedResult(boolean isLast, boolean isFirst, int page, int itemsPerPage, int totalItems, int totalPages) {
        this.isFirst = page == 0;;
        this.page = page;
        this.totalPages = (int) Math.ceil((float) totalItems / itemsPerPage);
        this.totalItems = totalItems;
        this.isLast = itemsPerPage * page + results.size() > itemsPerPage * (totalPages - 1) && itemsPerPage * page + results.size() <= itemsPerPage * (totalPages);
    }

    public Collection<T> getResults() {
        return results;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getPage() {
        return page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }


    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

}
