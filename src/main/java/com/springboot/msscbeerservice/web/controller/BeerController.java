package com.springboot.msscbeerservice.web.controller;


import com.springboot.msscbeerservice.services.BeerService;
import com.springboot.msscbeerservice.web.model.BeerDto;
import com.springboot.msscbeerservice.web.model.BeerPagedList;
import com.springboot.msscbeerservice.web.model.BeerStyleNum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private final BeerService beerService;


    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleNum beerStyle) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID id,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        return new ResponseEntity<>(beerService.getById(id , showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@RequestBody @Validated BeerDto beerDto) {
        BeerDto savedNewBeer = beerService.saveNewBeer(beerDto);
        return new ResponseEntity<>(savedNewBeer, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID id, @RequestBody @Validated BeerDto beerDto) {
        BeerDto beer = beerService.updateBeer(id, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


