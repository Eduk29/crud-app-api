package br.com.crud.app.backend.enums;

public enum PaginationDefaultValues {

    DEFAULT_PAGE_NUMBER(0),
    DEFAULT_PAGE_SIZE(10);

    private Integer value;

    private PaginationDefaultValues(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

}
