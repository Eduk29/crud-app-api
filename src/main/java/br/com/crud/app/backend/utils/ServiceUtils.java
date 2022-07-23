package br.com.crud.app.backend.utils;

import br.com.crud.app.backend.enums.ErrorsEnum;
import br.com.crud.app.backend.exception.InvalidPersonException;

public abstract class ServiceUtils {
    public static String getModeSearch(String filterQuery) throws Exception {
        if (filterQuery != null) {
            return filterQuery.substring(0, filterQuery.indexOf("="));
        }
        throw new Exception(ErrorsEnum.ERROO2.getDescription());
    }

    public static String getParameterSearch(String filterQuery) throws Exception {
        if (filterQuery != null) {
            return filterQuery.substring(filterQuery.indexOf("=") + 1, filterQuery.length());
        }
        throw new Exception(ErrorsEnum.ERROO2.getDescription());
    }

    public static String removeDoubleQuotes(String stringWithDoubleQuotes) {
        return stringWithDoubleQuotes.replace("\"","");
    }


}
