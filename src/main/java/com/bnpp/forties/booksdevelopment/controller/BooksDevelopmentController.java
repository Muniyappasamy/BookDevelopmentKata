package com.bnpp.forties.booksdevelopment.controller;


import com.bnpp.forties.booksdevelopment.model.Book;
import com.bnpp.forties.booksdevelopment.model.BookDto;
import com.bnpp.forties.booksdevelopment.model.CartSummaryReportDto;
import com.bnpp.forties.booksdevelopment.service.BooksDevelopmentService;
import com.bnpp.forties.booksdevelopment.service.PriceSummationService;
import com.bnpp.forties.booksdevelopment.service.impl.BooksDevelopmentServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = BooksDevelopmentController.APIRoutes.BooksController.ROOT_API)
@RequiredArgsConstructor
public class BooksDevelopmentController {

    private final BooksDevelopmentService booksDevelopmentService;

    private final PriceSummationService priceSummationService;

    @ApiOperation(value = "Get list of Books in the Store ", response = Iterable.class, tags = "getAllBooks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @RequestMapping(method = RequestMethod.GET, value = APIRoutes.BooksController.GET_ALL_BOOKS)
    public List<Book> getAllBooks() {
        return booksDevelopmentService.getAllBooks();
    }


    @ApiOperation(value = "API Produces Calculated Book Summary Report With Best Price Of Discounts ", response = CartSummaryReportDto.class, tags = "calculateDiscountPrice")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 400, message = "Bad Request, Given Input is not Matching the Store Boooks!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    @RequestMapping(method = RequestMethod.POST, value = APIRoutes.BooksController.CALCULATE_DISCOUNT_PRICE)
    public CartSummaryReportDto calculateDiscountPrice(@RequestBody List<BookDto> listOfBooks) {
        return priceSummationService.getcartSummaryReport(listOfBooks);
    }
    public class APIRoutes {

        public class BooksController {
            public static final String ROOT_API = "/api/booksdevelopment";
            public static final String GET_ALL_BOOKS = "/getallbooks";
            public static final String CALCULATE_DISCOUNT_PRICE = "/calculatediscountprice";
        }
    }

}

