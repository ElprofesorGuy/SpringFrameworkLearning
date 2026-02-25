package elprofesor.spring.springsection5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elprofesor.spring.springsection5.entities.Beer;
import elprofesor.spring.springsection5.mappers.BeerMapper;
import elprofesor.spring.springsection5.model.BeerDTO;
import elprofesor.spring.springsection5.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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
        List<BeerDTO> dtos = beerController.listBeers(null);
        assertThat(dtos.size()).isEqualTo(2413);
    }

    @Test
    void testListBeerByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListBeer(){
        beerRepository.deleteAll();
        //System.out.println("Nombre d'éléments dans la BD : " + beerRepository.count());
        //beerRepository.flush();
        List<BeerDTO> dtos = beerController.listBeers(null);
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

    @Test
    void testPatchBeerBadName() throws Exception{
        Beer beer = beerRepository.findAll().get(0);
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "NewName azertyuiopq NewName azertyuiopq NewName azertyuiopq NewName azertyuiopq");

        mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest());

        //verify(beerService).patchBeerById(argumentCaptor.capture(), beerArgumentCaptor.capture());//vérifie que patchBeerById a bien été appelé
        //et capture les paramètres qui lui ont été envoyés lors de l'appel.
        //assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());//vérifie que l'ID envoyé au service est bien celui de l'objet qu'on voulait modifier
        //assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());//vérifie que le nom de Beer reçu par le service est
        //bien NewName
    }
}