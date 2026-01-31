package elprofesor.spring.springsection5.services;

import elprofesor.spring.springsection5.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl(){
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(2)
                .beerName("Isenbeck")
                .upc("561234")
                .price(new BigDecimal(11.75))
                .quantityOnHand(134)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Castel")
                .upc("1896541436")
                .price(new BigDecimal(10.33))
                .quantityOnHand(101)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Grande Guiness")
                .upc("47586513")
                .quantityOnHand(75)
                .price(new BigDecimal(12.45))
                .createDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<BeerDTO> listBeers(){
        return new ArrayList<>(beerMap.values());
    }


    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .version(beer.getVersion())
                .price(beer.getPrice())
                .beerName(beer.getBeerName())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(beerId);

        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setPrice(beer.getPrice());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        existingBeer.setUpc(beer.getUpc());
        beerMap.put(existingBeer.getId(), existingBeer);

        return Optional.of(existingBeer);
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existingBeer = beerMap.get(beerId);

        if(StringUtils.hasText(beer.getBeerName())){
            existingBeer.setBeerName(beer.getBeerName());
        }

        if(beer.getPrice() != null){
            existingBeer.setPrice(beer.getPrice());
        }

        if(beer.getQuantityOnHand() != null){
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if(StringUtils.hasText(beer.getUpc())){
            existingBeer.setUpc(beer.getUpc());
        }

        beerMap.put(existingBeer.getId(), existingBeer);
    }

    /*@Override
    public void updateBeerById(UUID beerId, Beer beer) {
        Beer existingBeer = beerMap.get(beerId);

        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setPrice(beer.getPrice());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        existingBeer.setUpc(beer.getUpc());
    }*/


}
