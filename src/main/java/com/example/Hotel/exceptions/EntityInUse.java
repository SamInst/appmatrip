package com.example.Hotel.exceptions;

public class EntityInUse extends RuntimeException{
    private static final Long serialVersionUID = 1L;
    public EntityInUse(String message){
        super(message);

    }
}
