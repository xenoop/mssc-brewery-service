package com.springboot.msscbeerservice.web.mappers;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
    BeerDto beerToBeerDtoWithInventory(Beer beer);
}
