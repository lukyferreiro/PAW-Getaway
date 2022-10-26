package ar.edu.itba.getaway.models.pagination;

import java.util.List;

public class Page<T> {

    private final List<T> content;
    private final Integer currentPage, totalPages;
    private final Long totalResults;

    public Page(List<T> content, Integer currentPage, Integer totalPages, Long totalResults) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getMinPage() {
        int minPage = 1;
        if (currentPage - 2 >= 1) {
            minPage = currentPage - 2;
        } else if (currentPage - 1 >= 1) {
            minPage = currentPage - 1;
        }
        return minPage;
    }

    public Integer getMaxPage() {
        int maxPage = currentPage;
        if (currentPage + 2 <= totalPages) {
            maxPage = currentPage + 2;
        } else if (currentPage + 1 <= totalPages) {
            maxPage = currentPage + 1;
        }
        return maxPage;
    }

    public Long getTotalResults() {
        return totalResults;
    }
}
