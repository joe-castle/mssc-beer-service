package uk.co.joecastle.msscbeerservice.mapper;

import org.mapstruct.Mapper;
import uk.co.joecastle.msscbeerservice.entity.Beer;
import uk.co.joecastle.msscbeerservice.model.BeerModel;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {

    BeerModel beerModelToBeer(Beer beerModel);

    Beer beerToBeerModel(BeerModel beer);

}
