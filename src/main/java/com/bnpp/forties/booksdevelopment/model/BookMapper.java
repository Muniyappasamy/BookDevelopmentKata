package com.bnpp.forties.booksdevelopment.model;

import com.bnpp.forties.booksdevelopment.storerepository.BookStoreEnum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {


    Book mapper(BookStoreEnum bookStoreEnum);

}

