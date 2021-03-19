package com.springboot.msscbeerservice.services;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.repository.BeerRepository;
import com.springboot.msscbeerservice.web.controller.NotFoundException;
import com.springboot.msscbeerservice.web.mappers.BeerMapper;
import com.springboot.msscbeerservice.web.model.BeerDto;
import com.springboot.msscbeerservice.web.model.BeerPagedList;
import com.springboot.msscbeerservice.web.model.BeerStyleNum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author ESSADIKI_Youssef on 3/18/2021
 * @project mssc-beer-service
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    @Override
    @Cacheable(cacheNames = "beerCache", key = "#id", condition = "#showInventoryOnHand==false")
    public BeerDto getById(UUID id, Boolean showInventoryOnHand) {
        System.out.println("i was called");
        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(id)
                    .orElseThrow(NotFoundException::new));
        }
        return beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElseThrow(NotFoundException::new));
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand==false")
    public BeerPagedList listBeers(String beerName, BeerStyleNum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        System.out.println("i was called");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;
        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }
        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable()
                                            .getPageNumber(),
                                    beerPage.getPageable()
                                            .getPageSize()),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable()
                                            .getPageNumber(),
                                    beerPage.getPageable()
                                            .getPageSize()),
                    beerPage.getTotalElements());

        }
        return beerPagedList;
    }

    @Override
    @Cacheable(cacheNames = "beerUpcCache", key = "#upc")
    public BeerDto getByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc)
                .orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {

        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID id, BeerDto beerDto) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle()
                .name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));

    }
}
