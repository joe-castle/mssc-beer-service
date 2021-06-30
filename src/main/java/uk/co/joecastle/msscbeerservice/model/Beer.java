package uk.co.joecastle.msscbeerservice.model;

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
public class Beer {

    private UUID id;
    private Integer version;

    private String beerName;
    private BeerStyle beerStyle;

    private Long upc;

    private BigDecimal price;

    private Integer quantityOnHand;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;

}
