package com.nemana.order.exceptions;



public class OrderNotFoundException extends  RuntimeException {
    private static final long serialVersionUID = 2256477558314496007L;

    public  OrderNotFoundException() {
        super();
    }
    public OrderNotFoundException(String msg) {
        super(msg);
    }

}
