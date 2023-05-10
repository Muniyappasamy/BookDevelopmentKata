package com.bnpp.forties.booksdevelopment.storerepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookDevelopmentStackDetailsTest {
    private static final int TOTAL_NO_OF_DEVELOPMENT_BOOKS = 5;
    @Test
    public void bookDevelopmentEnumShouldContainFiveBooks(){

        Assertions.assertEquals(TOTAL_NO_OF_DEVELOPMENT_BOOKS,BookDevelopmentStackDetails.values().length);

    }
}
