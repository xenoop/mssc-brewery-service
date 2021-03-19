package com.springboot.msscbeerservice.services.inventory;

import java.util.UUID;

/**
 * @author ESSADIKI_Youssef on 3/19/2021
 * @project mssc-beer-service
 */
public interface BeerInventoryService {
    Integer getOnHandInventory(UUID id);
}
