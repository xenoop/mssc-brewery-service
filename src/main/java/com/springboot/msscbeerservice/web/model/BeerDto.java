package com.springboot.msscbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {
    private UUID id;
    private Integer version;
    private OffsetDateTime createdTime;
    private OffsetDateTime lastModifiedTime;
    private String beerName;
    private BeerStyleNum beerStyleNum;
    private Long upc;
    private BigDecimal price;
    private Integer quantityOnHand;
}