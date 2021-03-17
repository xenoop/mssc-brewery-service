package com.springboot.msscbeerservice.web.mappers;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto BeerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDto beerDto);
}
