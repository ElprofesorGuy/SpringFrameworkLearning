package elprofesor.spring.springsection5.repositories;

import elprofesor.spring.springsection5.entities.Beer;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSavedBeer(){
        Beer beer = beerRepository.save(Beer.builder()
                        .beerName("Doppel Munich")
                        .upc("154814")
                        .price(new BigDecimal(12.45))
                        .quantityOnHand(150)
                        .build());
        beerRepository.flush();
        assertThat(beer.getBeerName()).isNotNull();
        assertThat(beer.getId()).isNotNull();

    }

    @Test
    void testSavedBeerTooLongName(){
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = beerRepository.save(Beer.builder()
                    .beerName("Doppel Munich abcdefghij abcdefghij abcdefghij abcdefghij")
                    .upc("154814")
                    .price(new BigDecimal(12.45))
                    .quantityOnHand(150)
                    .build());
            beerRepository.flush();
        });
        //assertThat(beer.getBeerName()).isNotNull();
        //assertThat(beer.getId()).isNotNull();

    }

}