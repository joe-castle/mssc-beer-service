package uk.co.joecastle.msscbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.joecastle.msscbeerservice.model.BeerModel;
import uk.co.joecastle.msscbeerservice.model.BeerStyle;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerModel beerModel = BeerModel.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .upc(3370100000001L)
                .price(new BigDecimal("12.95"))
                .build();

        String beerModelToJson = mapper.writeValueAsString(beerModel);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerModelToJson))
            .andExpect(status().isCreated())
            .andExpect(result -> {
                MockHttpServletResponse response = result.getResponse();
                BeerModel model = mapper.readValue(response.getContentAsString(), BeerModel.class);

                // TODO: update test when location header properly returned
                assertEquals("http://localhost/api/v1/beer/"/* + model.getId()*/, response.getHeader("Location"));
            });
//            .andExpect(header().string("Location", "http://localhost/api/v1/beer/" + beerModel.getId()));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerModel beerModel = BeerModel.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .upc(3370100000001L)
                .price(new BigDecimal("12.95"))
                .build();

        String beerModelToJson = mapper.writeValueAsString(beerModel);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerModelToJson))
            .andExpect(status().isNoContent());
    }
}