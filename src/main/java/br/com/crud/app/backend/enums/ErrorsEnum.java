package br.com.crud.app.backend.enums;

public enum ErrorsEnum {

    ERROO1("Person not found, please verify!");

    private String description;

    private ErrorsEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
