package br.com.crud.app.backend.utils;

import br.com.crud.app.backend.enums.PaginationDefaultValues;

public abstract class PaginationUtils {

    public static boolean validatePageNumber(Integer pageNumber) {
        return pageNumber != null && pageNumber != 0;
    }

    public static boolean validatePageSize(Integer pageSize) {
        return pageSize != null && pageSize != 0;
    }

    public static Integer setDefaultPageNumber() {
        return PaginationDefaultValues.DEFAULT_PAGE_NUMBER.getValue();
    }

    public static Integer setDefaultPageSize() {
        return PaginationDefaultValues.DEFAULT_PAGE_SIZE.getValue();
    }
}
