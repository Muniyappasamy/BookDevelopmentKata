package com.bnpp.forties.booksdevelopment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiException {

    String functionalErrorCode;
    String functionalErrorMessage;
}
