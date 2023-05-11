package com.bnpp.forties.booksdevelopment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookGroupClassification {
    private List<String> listOfbooks;
    private int appliedDiscountPercentage;
    private double actualPrice;
    private double discountAmount;
    private int numberOfDistinctBooks;
}

