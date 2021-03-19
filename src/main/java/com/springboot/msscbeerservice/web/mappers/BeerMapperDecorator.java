package com.springboot.msscbeerservice.web.mappers;

import com.springboot.msscbeerservice.domain.Beer;
import com.springboot.msscbeerservice.services.inventory.BeerInventoryService;
import com.springboot.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ESSADIKI_Youssef on 3/19/2021
 * @project mssc-beer-service
 */
public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerMapper beerMapper;
    private BeerInventoryService beerInventoryService;

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return beerMapper.beerToBeerDto(beer);
    }
    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        beerDto
                .setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return beerDto;
    }


    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }
}
