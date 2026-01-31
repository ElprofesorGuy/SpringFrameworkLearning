package elprofesor.spring.springsection5.controller;

import elprofesor.spring.springsection5.entities.Beer;
import elprofesor.spring.springsection5.mappers.BeerMapper;
import elprofesor.spring.springsection5.model.BeerDTO;
import elprofesor.spring.springsection5.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testNotFoundBeerById(){
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeer(){
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListBeer(){
        beerRepository.deleteAll();
        //System.out.println("Nombre d'éléments dans la BD : " + beerRepository.count());
        //beerRepository.flush();
        List<BeerDTO> dtos = beerController.listBeers();
        //System.out.println("Nombre d'éléments dans la BD : " + beerRepository.count());
        //System.out.println("Taille de la liste : " + dtos.size());
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewBeer(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Sminorff")
                .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String [] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(location[3]);
        System.out.println("Location : " + savedUUID);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingBeer(){
        Beer beer = beerRepository.findAll().get(1);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String updatedName = "UPDATED BEER NAME";
        beerDTO.setBeerName(updatedName);
        System.out.println("Nom de la Beer : " + beerDTO.getBeerName());
        ResponseEntity responseEntity = beerController.updateBeer(beer.getId(), beerDTO);
        beerRepository.flush();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        System.out.println("Display beer name : " + updatedBeer.getBeerName());
        assertThat(updatedBeer.getBeerName()).isEqualTo(updatedName);

    }

    @Test
    void testsUpdateNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBeer(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeerById(){
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteBeer(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void testDeleteBeerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteBeer(UUID.randomUUID());
        });
    }
}