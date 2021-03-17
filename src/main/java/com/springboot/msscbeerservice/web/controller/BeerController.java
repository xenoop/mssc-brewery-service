package com.springboot.msscbeerservice.web.controller;


import com.springboot.msscbeerservice.web.model.BeerDto;
import com.springboot.msscbeerservice.web.model.BeerStyleNum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/beer")
public class BeerController {


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID id) {
        //todo implement

        return new ResponseEntity<>(BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleNum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(123123123123L)
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beerDto) {
        //todo implement
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID id, @RequestBody @Validated BeerDto beerDto) {
        //todo implement
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}


