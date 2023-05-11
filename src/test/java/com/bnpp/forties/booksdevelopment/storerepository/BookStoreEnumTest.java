package com.bnpp.forties.booksdevelopment.storerepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookStoreEnumTest {
    private static final int TOTAL_NO_OF_DEVELOPMENT_BOOKS = 5;
    @Test
    public void bookDevelopmentEnumShouldContainFiveBooks(){

        Assertions.assertThat(TOTAL_NO_OF_DEVELOPMENT_BOOKS).isEqualTo(BookStoreEnum.values().length);

    }
}
