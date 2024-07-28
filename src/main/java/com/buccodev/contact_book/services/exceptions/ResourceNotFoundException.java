package com.buccodev.contact_book.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private static final long sereialVersionUId = 1L;

    public ResourceNotFoundException(Object id){
        super("Resource "+id+" not found!");

    }
}
