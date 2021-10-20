package com.ttps.laboratorio.exception;

public class ProductListEmptyException extends RuntimeException{
    public ProductListEmptyException(String msj){
        super(msj);
    }
}
