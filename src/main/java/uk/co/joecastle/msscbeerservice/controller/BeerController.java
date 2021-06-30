package uk.co.joecastle.msscbeerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.co.joecastle.msscbeerservice.model.BeerModel;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerModel> getBeerById(@PathVariable String beerId) {
        // TODO: Impl
        return ResponseEntity
                .ok(BeerModel.builder()
                        .build());
    }

    @PostMapping
    public ResponseEntity<BeerModel> saveNewBeer(@RequestBody BeerModel beerModel) {
        // TODO: Impl

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(beerModel.getId());

        return ResponseEntity
                .created(uri)
                .body(beerModel);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerModel> updateBeerById(@PathVariable String beerId, @RequestBody BeerModel beerModel) {
        // TODO: Impl
        return ResponseEntity
                .noContent()
                .build();
    }

}
