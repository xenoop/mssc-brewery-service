package com.springboot.msscbeerservice.services;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.repository.BeerRepository;
import com.springboot.msscbeerservice.web.controller.NotFoundException;
import com.springboot.msscbeerservice.web.mappers.BeerMapper;
import com.springboot.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ESSADIKI_Youssef on 3/18/2021
 * @project mssc-beer-service
 */

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    @Override
    public BeerDto getById(UUID id) {
        return beerMapper.beerToBeerDto(beerRepository.findById(id)
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
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));

    }
}
