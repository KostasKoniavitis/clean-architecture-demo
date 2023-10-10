package com.finartix.cleanarchitecture.demo.shared.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiValidationErrorModel {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationErrorModel(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
