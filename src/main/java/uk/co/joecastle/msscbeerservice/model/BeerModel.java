package uk.co.joecastle.msscbeerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerModel {

    @Null
    private UUID id;

    @Null
    private Integer version;

    @NotBlank
    @Size(min = 3, max = 100)
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotNull
    @Positive
    private Long upc;

    @NotNull
    @Positive
    private BigDecimal price;

    @Null
    private Integer quantityOnHand;

    @Null
    private OffsetDateTime createdDate;

    @Null
    private OffsetDateTime lastModifiedDate;

}
