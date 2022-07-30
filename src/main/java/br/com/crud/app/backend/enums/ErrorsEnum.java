package br.com.crud.app.backend.enums;

public enum ErrorsEnum {

    ERROO1("Person not found, please verify!"),
    ERROO2("Invalid parameters, please verify!"),
    ERR003("Person is not valid, please verify!"),
    ERR004("User not found, please verify!"),
    ERR005("User is not valid, please verify!"),
    ERR006("User must have person owner, please verify!"),
    ERR007("Username already exist, please verify!");


    private String description;

    private ErrorsEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
