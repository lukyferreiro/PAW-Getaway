package ar.edu.itba.getaway.models.pagination;

import java.util.List;

public class Page<T> {

    private final List<T> content;
    private final int currentPage, totalPages;
    private final long totalResults;

    public Page(List<T> content, int currentPage, int totalPages, long totalResults) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public List<T> getContent() {
        return content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getMinPage() {
        int minPage = 1;
        if (currentPage - 2 >= 1) {
            minPage = currentPage - 2;
        } else if (currentPage - 1 >= 1) {
            minPage = currentPage - 1;
        }
        return minPage;
    }

    public int getMaxPage() {
        int maxPage = currentPage;
        if (currentPage + 2 <= totalPages) {
            maxPage = currentPage + 2;
        } else if (currentPage + 1 <= totalPages) {
            maxPage = currentPage + 1;
        }
        return maxPage;
    }

    public long getTotalResults() {
        return totalResults;
    }
}
