package br.com.crud.app.backend.model;

import br.com.crud.app.backend.utils.ServiceUtils;

import java.io.Serializable;
import java.util.Objects;

public class SearchFilter implements Serializable {

    private static final long serialVersionUID = -1973091650779681540L;
    private String searchMode;
    private String searchParameter;
    private String rawSearchFilter;

    public SearchFilter(String searchFilter) throws Exception {
        this.rawSearchFilter = ServiceUtils.removeDoubleQuotes(searchFilter);
        this.searchMode = ServiceUtils.getModeSearch(rawSearchFilter);
        this.searchParameter = ServiceUtils.getParameterSearch(rawSearchFilter);
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }

    public String getRawSearchFilter() {
        return rawSearchFilter;
    }

    public void setRawSearchFilter(String rawSearchFilter) {
        this.rawSearchFilter = rawSearchFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchFilter that = (SearchFilter) o;
        return Objects.equals(searchMode, that.searchMode) && Objects.equals(searchParameter, that.searchParameter) && Objects.equals(rawSearchFilter, that.rawSearchFilter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchMode, searchParameter, rawSearchFilter);
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "searchMode='" + searchMode + '\'' +
                ", searchParameter='" + searchParameter + '\'' +
                ", rawSearchFilter='" + rawSearchFilter + '\'' +
                '}';
    }
}
