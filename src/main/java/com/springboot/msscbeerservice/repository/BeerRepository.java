package com.springboot.msscbeerservice.repository;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.web.model.BeerStyleNum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyleNum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleNum beerStyle, Pageable pageable);

    Optional<Beer> findByUpc(String upc);
}
