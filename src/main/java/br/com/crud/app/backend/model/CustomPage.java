package br.com.crud.app.backend.model;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import org.springframework.data.domain.Page;

public class CustomPage<T> {
    private List<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageSize;
    private Integer pageNumber;
    private String timestamp;

    public CustomPage(Page<T> page) {
        this.content = (List<T>) page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
        this.timestamp = new Date().toInstant().toString();
    }

    public CustomPage(T input) {
        List<T> inputList = new ArrayList<T>();
        inputList.add(input);

        this.content = (List<T>) inputList;
        this.totalElements = (long) 1;
        this.totalPages = 1;
        this.pageSize = 1;
        this.pageNumber = 1;
        this.timestamp = new Date().toInstant().toString();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
