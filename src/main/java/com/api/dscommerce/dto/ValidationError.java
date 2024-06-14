package com.api.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{

    private List<FieldMessage> fieldErrors = new ArrayList<>();

    public ValidationError(Instant timestamp, String status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String fieldName, String message) {

        fieldErrors.removeIf(x -> x.getFieldName().equals(message));

        fieldErrors.add(new FieldMessage(fieldName, message));
    }
}
