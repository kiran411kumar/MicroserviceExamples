package com.nemana.order.exceptions;

import com.nemana.order.dao.OrderEntity;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<OrderEntity> handleOrderNotFoundException(OrderNotFoundException onfe) {
        OrderEntity entity = new OrderEntity();
        entity.setComments(onfe.getMessage());
        return new ResponseEntity<OrderEntity>(entity, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<OrderEntity> handleOrderNotFoundException(InvalidDataAccessResourceUsageException idar) {
        OrderEntity entity = new OrderEntity();
        entity.setComments(idar.getMessage());
        return new ResponseEntity<OrderEntity>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
