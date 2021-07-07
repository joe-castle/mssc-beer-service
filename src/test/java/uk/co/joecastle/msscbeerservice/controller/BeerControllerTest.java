package uk.co.joecastle.msscbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import uk.co.joecastle.msscbeerservice.entity.Beer;
import uk.co.joecastle.msscbeerservice.model.BeerModel;
import uk.co.joecastle.msscbeerservice.model.BeerStyle;
import uk.co.joecastle.msscbeerservice.repository.BeerRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BeerRepository beerRepository;

    ConstraintFields fields = new ConstraintFields(BeerModel.class);

    @Test
    void getBeerById() throws Exception {
        given(beerRepository.findById(any(UUID.class)))
                .willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer").type(UUID.class),
                                fieldWithPath("version").description("Version number").type(Integer.class),
                                fieldWithPath("beerName").description("Beer name").type(String.class),
                                fieldWithPath("beerStyle").description("Beer style").type(String.class),
                                fieldWithPath("upc").description("UPC of Beer").type(Long.class),
                                fieldWithPath("price").description("Price of Beer").type(BigDecimal.class),
                                fieldWithPath("quantityOnHand").description("Quantity on hand").type(Integer.class),
                                fieldWithPath("createdDate").description("Date created").type(OffsetDateTime.class),
                                fieldWithPath("lastModifiedDate").description("Date updated").type(OffsetDateTime.class)
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerModel beerModel = getValidBeerModel();

        String beerModelToJson = mapper.writeValueAsString(beerModel);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerModelToJson))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    BeerModel model = mapper.readValue(response.getContentAsString(), BeerModel.class);

                    // TODO: update test when location header properly returned
                    assertThat(response.getHeader("Location"), endsWith("/api/v1/beer/"/* + model.getId()*/));
                })
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("beerName").description("Beer name"),
                                fields.withPath("beerStyle").description("Beer style"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price of Beer"),
                                fields.withPath("quantityOnHand").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored()
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer").type(UUID.class),
                                fieldWithPath("version").description("Version number").type(Integer.class),
                                fieldWithPath("beerName").description("Beer name").type(String.class),
                                fieldWithPath("beerStyle").description("Beer style").type(String.class),
                                fieldWithPath("upc").description("UPC of Beer").type(Long.class),
                                fieldWithPath("price").description("Price of Beer").type(BigDecimal.class),
                                fieldWithPath("quantityOnHand").description("Quantity on hand").type(Integer.class),
                                fieldWithPath("createdDate").description("Date created").type(OffsetDateTime.class),
                                fieldWithPath("lastModifiedDate").description("Date updated").type(OffsetDateTime.class)
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerModel beerModel = getValidBeerModel();

        String beerModelToJson = mapper.writeValueAsString(beerModel);

        mockMvc.perform(put("/api/v1/beer/{beerId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerModelToJson))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("beerName").description("Beer name"),
                                fields.withPath("beerStyle").description("Beer style"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price of Beer"),
                                fields.withPath("quantityOnHand").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored()
                        )));
    }

    private BeerModel getValidBeerModel() {
        return BeerModel.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .upc(3370100000001L)
                .price(new BigDecimal("12.95"))
                .build();
    }

    private static class ConstraintFields {

        private final ConstraintDescriptions constraintDescriptions;

        public ConstraintFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(constraintDescriptions.descriptionsForProperty(path), ". ")));
        }

    }

}