package com.example.Hotel.exceptions;
public class EntityNotFound extends RuntimeException{
    public EntityNotFound(String message){
        super(message);
    }
}