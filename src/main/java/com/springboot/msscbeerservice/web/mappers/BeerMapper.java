package com.springboot.msscbeerservice.web.mappers;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.web.model.BeerDto;

public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDto beerDto);
}
