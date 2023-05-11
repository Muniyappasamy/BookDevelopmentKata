package com.bnpp.forties.booksdevelopment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookGroupClassification {
    private int discountPercentage;
    private double actualPrice;
    private double discount;
    private int numberOfBooks;
}
