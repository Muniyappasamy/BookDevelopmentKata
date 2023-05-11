package com.bnpp.forties.booksdevelopment.utils;

import java.math.BigDecimal;

public class Constants {
    public static final double NO_DISCOUNT = BigDecimal.ZERO.doubleValue();
    public static final int HUNDRED = 100;
    public static final int ZERO_PERCENT = BigDecimal.ZERO.intValue();
    public static final int ONE_QUANTITY = BigDecimal.ONE.intValue();

    public static final String ROOT_API = "/api/booksdevelopment";
    public static final String GET_ALL_BOOKS = "/getallbooks";
    public static final String CALCULATE_DISCOUNT_PRICE = "/calculatediscountprice";

}
