package com.buccodev.contact_book.services.exceptions;

public class DataBaseExcepions extends RuntimeException{

    private static final long sereialVersionUId = 1L;

    public DataBaseExcepions(String msg){
        super(msg);
    }

}
