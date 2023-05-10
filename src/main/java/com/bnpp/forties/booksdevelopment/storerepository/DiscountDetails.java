package com.bnpp.forties.booksdevelopment.storerepository;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountDetails {
    TWO_DISTINCT_BOOKS(2, 5), THREE_DISTINCT_BOOKS(3, 10);

    private int numberOfDistinctItems;
    private int discountPercentage;
}