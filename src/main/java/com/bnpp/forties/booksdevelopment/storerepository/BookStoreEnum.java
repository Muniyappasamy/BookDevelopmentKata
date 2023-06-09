package com.bnpp.forties.booksdevelopment.storerepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStoreEnum {

    CLEAN_CODE("Clean Code", "Robert Martin", 50.00),
    THE_CLEAN_CODER("The Clean Coder", "Robert Martin", 50.00),
    CLEAN_ARCHITECTURE("Clean Architecture", "Robert Martin", 50.00),
    TEST_DRIVEN_DEVELOPMENT_BY_EXAMPLE("Test-Driven Development By Example", "Kent Beck", 50.00),
    WORKING_EFFECTIVELY_WITH_LEGACY_CODE("Working Effectively With Legacy Code", "Michael C. Feathers", 50.00);

    private String bookTitle;
    private String author;
    private double price;

}