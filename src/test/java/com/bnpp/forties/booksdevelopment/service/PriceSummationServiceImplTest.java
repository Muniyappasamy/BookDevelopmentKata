package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.exception.InvalidBookException;
import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.CartSummaryReportDto;
import com.bnpp.forties.booksdevelopment.service.impl.PriceSummationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PriceSummationServiceImplTest {

    private static final String BOOK_NAME = "Clean Code";
    private static final double BOOK_PRICE = 50.00;

    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final int FIVE = 5;
    private static final double TWO_DIFF_BOOK_EXPECTED_PRICE_WITH_2_PER_DISCOUNT = 95.00;
    private static final double THREE_DIFF_BOOK_EXPECTED_PRICE_WITH_10_PER_DISCOUNT = 135.00;
    private static final double FOUR_DIFF_BOOK_EXPECTED_PRICE_WITH_20_PER_DISCOUNT = 160.00;
    private static final double FIVE_DIFF_BOOK_EXPECTED_PRICE_WITH_25_PER_DISCOUNT = 187.50;

    private static final double TWO_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT = 145.00;
    private static final double THREE_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT = 185.00;

    private static final double FOUR_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT = 210.00;
    private static final double FIVE_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT = 237.50;

    private static final double SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_DISCOUNT = 370.0;

    private static final double SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_ACTUAL_PRICE = 450.0;
    private static final double SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_DISCOUNT_AMOUNT = 80.0;

    List<BookDto> listOfBooks;


    @Autowired
    private PriceSummationServiceImpl priceSummationServiceImpl;

    @BeforeEach
    void setup() {
        listOfBooks = new ArrayList<BookDto>();
    }

    @Test
    @DisplayName("50 should return for a single book")
    void singleBookPriceShouldReturnFifty() {

        BookDto bookDto = new BookDto("Clean Code", ONE);

        listOfBooks.add(bookDto);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(BOOK_PRICE, actualPrice);
    }

    @Test
    @DisplayName("100 should return for two listOfBooks")
    void twoBooksShouldReturnPriceHundred() {
        BookDto bookDto = new BookDto("Clean Code", TWO);

        listOfBooks.add(bookDto);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();
        assertEquals(2 * BOOK_PRICE, actualPrice);
    }

    @Test
    @DisplayName("250 should return for a Five book")
    void fiveBooksShouldReturnPriceTwoHundredAndFifty() {
        BookDto bookDto = new BookDto("Clean Code", FIVE);

        listOfBooks.add(bookDto);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(5 * BOOK_PRICE, actualPrice);
    }

    @Test
    @DisplayName("Two different listOfBooks should get 5% discount")
    void twoDifferentBooksShouldReturnPriceNinetyFive() {


        BookDto firstBook = new BookDto("Clean Code", ONE);
        BookDto secondBook = new BookDto("The Clean Coder", ONE);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(TWO_DIFF_BOOK_EXPECTED_PRICE_WITH_2_PER_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("Three different listOfBooks should get 10% discount")
    void threeDifferentBooksShouldReturnOneHundredAndThirtyFive() {


        BookDto firstBook = new BookDto("Clean Code", ONE);
        BookDto secondBook = new BookDto("The Clean Coder", ONE);
        BookDto thirdBook = new BookDto("Clean Architecture", ONE);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(THREE_DIFF_BOOK_EXPECTED_PRICE_WITH_10_PER_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("Four different listOfBooks should get 10% discount")
    void fourDifferentBooksShouldReturnOneHundredAndSixty() {


        BookDto firstBook = new BookDto("Clean Code", 1);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 1);
        BookDto fourthBook = new BookDto("Test-Driven Development By Example", 1);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);
        listOfBooks.add(fourthBook);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();
        ;

        assertEquals(FOUR_DIFF_BOOK_EXPECTED_PRICE_WITH_20_PER_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("Five different listOfBooks should get 10% discount")
    void fiveDifferentBooksShouldReturnPriceOneHundredAndEightySeven() {


        BookDto firstBook = new BookDto("Clean Code", 1);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 1);
        BookDto fourthBook = new BookDto("Test-Driven Development By Example", 1);
        BookDto fifthBook = new BookDto("Working Effectively With Legacy Code", 1);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);
        listOfBooks.add(fourthBook);
        listOfBooks.add(fifthBook);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(FIVE_DIFF_BOOK_EXPECTED_PRICE_WITH_25_PER_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("two distinct listOfBooks should only get 5% discount")
    void fivePercentDiscountOnlyForTwoDistinctBooksShouldApply() {


        BookDto firstBook = new BookDto("Clean Code", 2);
        BookDto secondBook = new BookDto("The Clean Coder", 1);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();
        ;

        assertEquals(TWO_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("three distinct listOfBooks should only get 10% discount")
    void tenPercentDiscountOnlyForThreeDistinctBooks() {

        BookDto firstBook = new BookDto("Clean Code", 2);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 1);


        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(THREE_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("Four distinct listOfBooks should only get 20% discount")
    void twentyPercentDiscountOnlyForFourDistinctBooks() {


        BookDto firstBook = new BookDto("Clean Code", 2);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 1);
        BookDto fourthBook = new BookDto("Test-Driven Development By Example", 1);


        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);
        listOfBooks.add(fourthBook);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();
        ;

        assertEquals(FOUR_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT, actualPrice);
    }


    @Test
    @DisplayName("Five distinct listOfBooks should only get 25% discount")
    void twentyFivePercentDiscountOnlyForFiveDistinctBooks() {

        BookDto firstBook = new BookDto("Clean Code", 2);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 1);
        BookDto fourthBook = new BookDto("Test-Driven Development By Example", 1);
        BookDto fifthBook = new BookDto("Working Effectively With Legacy Code", 1);


        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);
        listOfBooks.add(fourthBook);
        listOfBooks.add(fifthBook);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();
        ;

        assertEquals(FIVE_DISTINCT_AND_ONE_SEPARATE_BOOK_WITH_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("apply discount to all distinct listOfBooks only ")
    void ApplyDiscountToAllDistinctBooks() {


        BookDto firstBook = new BookDto("Clean Code", 2);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 3);
        BookDto fourthBook = new BookDto("Test-Driven Development By Example", 2);
        BookDto fifthBook = new BookDto("Working Effectively With Legacy Code", 1);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);
        listOfBooks.add(fourthBook);
        listOfBooks.add(fifthBook);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(listOfBooks).getCostEffectivePrice();

        assertEquals(SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_DISCOUNT, actualPrice);
    }

    @Test
    @DisplayName("calculate price should return detailed price summary")
    void cartSummaryReportTesting() {


        BookDto firstBook = new BookDto("Clean Code", 2);
        BookDto secondBook = new BookDto("The Clean Coder", 1);
        BookDto thirdBook = new BookDto("Clean Architecture", 3);
        BookDto fourthBook = new BookDto("Test-Driven Development By Example", 2);
        BookDto fifthBook = new BookDto("Working Effectively With Legacy Code", 1);

        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);
        listOfBooks.add(thirdBook);
        listOfBooks.add(fourthBook);
        listOfBooks.add(fifthBook);


        CartSummaryReportDto cartSummaryReportDto = priceSummationServiceImpl.getcartSummaryReport(listOfBooks);

        assertEquals(SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_ACTUAL_PRICE, cartSummaryReportDto.getActualPrice());
        assertEquals(SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_DISCOUNT_AMOUNT, cartSummaryReportDto.getTotalDiscount());
        assertEquals(SIX_BOOKS_WITH_FIVE_DISTINCT_BOOKS_DISCOUNT, cartSummaryReportDto.getCostEffectivePrice());
    }

    @Test
    @DisplayName("calculate price summary should throw book not found exception for the invalid listOfBooks")
    void getCartSummaryshouldThrowInvalidExceptionOnInvalidBooks() {

        BookDto firstBook = new BookDto("Davince Code", 2);
        BookDto secondBook = new BookDto("Pirates", 1);


        listOfBooks.add(firstBook);
        listOfBooks.add(secondBook);


        assertThrows(InvalidBookException.class, () -> priceSummationServiceImpl.getcartSummaryReport(listOfBooks));
    }

}
