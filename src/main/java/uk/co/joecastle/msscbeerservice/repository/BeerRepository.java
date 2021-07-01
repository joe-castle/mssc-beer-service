package uk.co.joecastle.msscbeerservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import uk.co.joecastle.msscbeerservice.entity.Beer;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
