package br.com.crud.app.backend.exception;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class InvalidPersonException extends Exception implements Serializable {

    private static final long serialVersionUID = 3323247773894616156L;

    @Autowired
    public InvalidPersonException(String message){
        super(message);
    }
}
