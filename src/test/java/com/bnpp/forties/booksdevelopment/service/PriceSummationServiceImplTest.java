package com.bnpp.forties.booksdevelopment.service;

import com.bnpp.forties.booksdevelopment.exception.InvalidBookException;
import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.CartSummaryReportDto;
import com.bnpp.forties.booksdevelopment.service.impl.PriceSummationServiceImpl;
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



    @Autowired
    private PriceSummationServiceImpl priceSummationServiceImpl;

    @Test
    @DisplayName("50 should return for a single book")
    void priceForABook_shouldReturnFifty() {
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto = new BookDto("Clean Code",1);
        books.add(bookDto);
        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(BOOK_PRICE, actualPrice);
    }

    @Test
    @DisplayName("100 should return for a two book")
    void priceForTwoBooks_shouldReturnHundrad() {
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto = new BookDto("Clean Code",2);
        books.add(bookDto);
        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(2*BOOK_PRICE, actualPrice);
    }
    @Test
    @DisplayName("250 should return for a Five book")
    void priceForFiveBooks_shouldReturnTwoHundradAndFivty() {
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto = new BookDto("Clean Code",5);
        books.add(bookDto);
        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(5*BOOK_PRICE, actualPrice);
    }

    @Test
    @DisplayName("Two different books should get 5% discount")
    void priceForTwoDifferentBooks_shouldReturnNintyFive() {
        double expectedPrice = 95.00;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",1);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        books.add(bookDto1);
        books.add(bookDto2);
        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    @DisplayName("Three different books should get 10% discount")
    void priceForThreeDifferentBooks_shouldReturnOneHundradAndThirtyFive() {
        double expectedPrice = 135.00;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",1);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",1);
        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    @DisplayName("Four different books should get 10% discount")
    void priceForFourDifferentBooks_shouldReturnOneHundradAndSixty() {
        double expectedPrice = 160.00;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",1);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",1);
        BookDto bookDto4 = new BookDto("Test-Driven Development By Example",1);
        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        books.add(bookDto4);

        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    @DisplayName("Five different books should get 10% discount")
    void priceForFiveDifferentBooks_shouldReturnOneHundradAndEightySeven() {
        double expectedPrice = 187.50;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",1);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",1);
        BookDto bookDto4 = new BookDto("Test-Driven Development By Example",1);
        BookDto bookDto5 = new BookDto("Working Effectively With Legacy Code",1);

        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        books.add(bookDto4);
        books.add(bookDto5);
        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    @DisplayName("two distinct books should only get 5% discount")
    void calculatePrice_shouldApplyFivePercentDiscountOnlyForTwoDistinctBooks() {
        Double expectedResultThreeBooksWithTwoDistinctBooks = 145.00;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",2);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);

        books.add(bookDto1);
        books.add(bookDto2);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedResultThreeBooksWithTwoDistinctBooks, actualPrice);
    }

    @Test
    @DisplayName("three distinct books should only get 10% discount")
    void calculatePrice_shouldApplyTenPercentDiscountOnlyForThreeDistinctBooks() {
        Double expectedResultFourBooksWithThreeDistinctBooks = 185.00;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",2);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",1);


        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedResultFourBooksWithThreeDistinctBooks, actualPrice);
    }

    @Test
    @DisplayName("Four distinct books should only get 20% discount")
    void calculatePrice_shouldApplyTwentyPercentDiscountOnlyForFourDistinctBooks() {
        Double expectedResultFiveBooksWithFourDistinctBooks = 210.00;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",2);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",1);
        BookDto bookDto4 = new BookDto("Test-Driven Development By Example",1);



        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        books.add(bookDto4);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedResultFiveBooksWithFourDistinctBooks, actualPrice);
    }


    @Test
    @DisplayName("Five distinct books should only get 25% discount")
    void calculatePrice_shouldApplyTwentyFivePercentDiscountOnlyForFiveDistinctBooks() {
        Double expectedResultSixBooksWithFiveDistinctBooks = 237.50;
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Clean Code",2);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",1);
        BookDto bookDto4 = new BookDto("Test-Driven Development By Example",1);
        BookDto bookDto5 = new BookDto("Working Effectively With Legacy Code",1);




        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        books.add(bookDto4);
        books.add(bookDto5);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();
;

        assertEquals(expectedResultSixBooksWithFiveDistinctBooks, actualPrice);
    }

    @Test
    @DisplayName("apply discount to all distinct books only ")
    void calculatePrice_shouldApplyDiscountToAllDistinctBooks() {
        List<BookDto> books = new ArrayList<BookDto>();
        Double expectedResultSixBooksWithFiveDistinctBooks = 372.5;

        BookDto bookDto1 = new BookDto("Clean Code",2);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",3);
        BookDto bookDto4 = new BookDto("Test-Driven Development By Example",2);
        BookDto bookDto5 = new BookDto("Working Effectively With Legacy Code",1);

        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        books.add(bookDto4);
        books.add(bookDto5);


        Double actualPrice = priceSummationServiceImpl.getcartSummaryReport(books).getBestPrice();

        assertEquals(expectedResultSixBooksWithFiveDistinctBooks, actualPrice);
    }

    @Test
    @DisplayName("calculate price should return detailed price summary")
    void calculatePrice_shouldReturnCartSummaryReport() {
        List<BookDto> books = new ArrayList<BookDto>();
        Double expectedActualPrice = 450.0;
        Double expectedDiscountPrice = 77.5;
        Double expectedResultSixBooksWithFiveDistinctBooks = 372.5;

        BookDto bookDto1 = new BookDto("Clean Code",2);
        BookDto bookDto2 = new BookDto("The Clean Coder",1);
        BookDto bookDto3 = new BookDto("Clean Architecture",3);
        BookDto bookDto4 = new BookDto("Test-Driven Development By Example",2);
        BookDto bookDto5 = new BookDto("Working Effectively With Legacy Code",1);

        books.add(bookDto1);
        books.add(bookDto2);
        books.add(bookDto3);
        books.add(bookDto4);
        books.add(bookDto5);


        CartSummaryReportDto cartSummaryReportDto = priceSummationServiceImpl.getcartSummaryReport(books);

        assertEquals(expectedActualPrice, cartSummaryReportDto.getActualPrice());
        assertEquals(expectedDiscountPrice, cartSummaryReportDto.getTotalDiscount());
        assertEquals(expectedResultSixBooksWithFiveDistinctBooks, cartSummaryReportDto.getBestPrice());
    }
    @Test
    @DisplayName("calculate price summary should throw book not found exception for the invalid books")
    void getCartSummary_shouldThrowInvalidExceptionOnInvalidBooks() {
        List<BookDto> books = new ArrayList<BookDto>();
        BookDto bookDto1 = new BookDto("Davince Code", 2);
        BookDto bookDto2 = new BookDto("Pirates", 1);


        books.add(bookDto1);
        books.add(bookDto2);


        assertThrows(InvalidBookException.class, () -> priceSummationServiceImpl.getcartSummaryReport(books));
    }
}
