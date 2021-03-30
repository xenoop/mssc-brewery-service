package com.springboot.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.msscbeerservice.bootstrap.BeerLoader;
import com.springboot.msscbeerservice.services.BeerService;
import com.springboot.msscbeerservice.web.model.BeerDto;
import com.springboot.msscbeerservice.web.model.BeerStyleNum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriHost = "dev.springboot.msscbeerservice" , uriScheme = "https",uriPort = 80)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.springboot.msscbeerservice.services")
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {

        given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewBeer() throws Exception {

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleNum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

//    @Test
//    void getBeerById() throws Exception {
//
//        given(beerService.getById(any(),false)).willReturn(getValidBeerDto());
//
//        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID()
//                .toString())
//                .param("isCold", "yes")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("v1/beer-get", pathParameters(
//                        parameterWithName("beerId").description("UUID of the desired beer to get ")
//                        ), requestParameters(
//                        parameterWithName("isCold").description("is beer cold query param")
//                        ),
//                        responseFields(
//                                fieldWithPath("id").description("Id of Beer"),
//                                fieldWithPath("version").description("Version number"),
//                                fieldWithPath("createdDate").description("Date Created"),
//                                fieldWithPath("lastModifiedDate").description("Date Updated"),
//                                fieldWithPath("beerName").description("Beer Name"),
//                                fieldWithPath("beerStyle").description("Beer Style"),
//                                fieldWithPath("upc").description("UPC of Beer"),
//                                fieldWithPath("price").description("Price"),
//                                fieldWithPath("quantityOnHand").description("Quantity On hand"))));
//    }
//
//    @Test
//    void saveNewBeer() throws Exception {
//
//        BeerDto beerDto = getValidBeerDto();
//        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
//
//        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());
//
//        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
//
//        mockMvc.perform(post("/api/v1/beer/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(beerDtoJson))
//                .andExpect(status().isCreated())
//                .andDo(document("v1/beer-new",
//                        requestFields(
//                                fields.withPath("id").ignored(),
//                                fields.withPath("version").ignored(),
//                                fields.withPath("createdDate").ignored(),
//                                fields.withPath("lastModifiedDate").ignored(),
//                                fields.withPath("beerName").description("Name of the beer"),
//                                fields.withPath("beerStyle").description("Style of Beer"),
//                                fields.withPath("upc").description("Beer UPC").attributes(),
//                                fields.withPath("price").description("Beer Price"),
//                                fields.withPath("quantityOnHand").ignored()
//                        )));;
//    }
//
//    @Test
//    void updateBeerById() throws Exception {
//        BeerDto beerDto = getValidBeerDto();
//        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
//
//        given(beerService.updateBeer(any(),any())).willReturn(getValidBeerDto());
//
//        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID()
//                .toString())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(beerDtoJson))
//                .andExpect(status().isNoContent());
//    }
//
//
//    BeerDto getValidBeerDto() {
//        return BeerDto.builder()
//                .beerName("My Beer")
//                .beerStyle(BeerStyleNum.ALE)
//                .price(new BigDecimal("2.99"))
//                .upc("0083783375213")
//                .build();
//    }
//
//
//    private static class ConstrainedFields {
//
//        private final ConstraintDescriptions constraintDescriptions;
//
//        ConstrainedFields(Class<?> input) {
//            this.constraintDescriptions = new ConstraintDescriptions(input);
//        }
//
//        private FieldDescriptor withPath(String path) {
//            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
//                    .collectionToDelimitedString(this.constraintDescriptions
//                            .descriptionsForProperty(path), ". ")));
//        }
//    }
}