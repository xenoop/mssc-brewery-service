package com.springboot.msscbeerservice.services;

import com.springboot.msscbeerservice.web.model.BeerDto;
import com.springboot.msscbeerservice.web.model.BeerPagedList;
import com.springboot.msscbeerservice.web.model.BeerStyleNum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * @author ESSADIKI_Youssef on 3/18/2021
 * @project mssc-beer-service
 */
public interface BeerService {
    BeerDto updateBeer(UUID id, BeerDto beerDto);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto getById(UUID id ,  Boolean showInventoryOnHand);

    BeerPagedList listBeers(String beerName, BeerStyleNum beerStyle, PageRequest of, Boolean showInventoryOnHand);

    BeerDto getByUpc(String upc);
}
