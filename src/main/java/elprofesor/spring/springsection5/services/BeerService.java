package elprofesor.spring.springsection5.services;

import elprofesor.spring.springsection5.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BeerService {
    List<BeerDTO> listBeers();//GET

    Optional<BeerDTO> getBeerById(UUID id);//GET/id

    BeerDTO saveNewBeer(BeerDTO beer);//POST

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);//PUT

    Boolean deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);

    //void updateBeerById(UUID beerId, Beer beer);
}
