package elprofesor.spring.springsection5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elprofesor.spring.springsection5.model.BeerDTO;
import elprofesor.spring.springsection5.services.BeerService;
import elprofesor.spring.springsection5.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

//import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {
    //@Autowired
    //BeerController beerController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;  //cet objet va permettre la conversion du JSON en objet Java et vice versa

    @MockitoBean    //créé un faux bean qui va être utilisé pour les tests
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> argumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));//vérifie que la méthode saveNewBeer
        //a bie nété appelé peut importe l'argument qu'il a utilisé et retourne le 2ème de la liste de Beer

        mockMvc.perform(post("/api/v1/beer")//permet de simuler une requête http
                .accept(MediaType.APPLICATION_JSON) //le contrôleur renverra une réponse en format JSON
                .contentType(MediaType.APPLICATION_JSON) //On indique que les données qui seront envoyées au serveur seront au format JSON
                .content(objectMapper.writeValueAsString(beer))) //transforme l'objet java en données JSON
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));//le serveur doit renvoyer un en-tête nommée Location qui est l'url de la ressource qui a été créée
        //System.out.println(objectMapper.writeValueAsString(beer));
    }

    @Test
    void getBeerById() throws Exception {

        BeerDTO testBeer = beerServiceImpl.listBeers().get(0); // retourne le 1er élément de la liste de Beer.
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(testBeer));
        //given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        //System.out.println(beerController.getBeerById(UUID.randomUUID()));
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void testUpdateBeer() throws Exception{
        BeerDTO beer = beerServiceImpl.listBeers().get(1);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));//vérifie que la méthode update... a été appelé

    }

    @Test
    void testDeleteBeer() throws Exception{
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        given(beerService.deleteBeerById(beer.getId())).willReturn(true);
        mockMvc.perform(delete("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeerById(argumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());
    }
    @Test
    void testPatchBeer() throws Exception{
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        Map<String, Object>  beerMap = new HashMap<>();
        beerMap.put("beerName", "NewName");

        mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(argumentCaptor.capture(), beerArgumentCaptor.capture());//vérifie que patchBeerById a bien été appelé
        //et capture les paramètres qui lui ont été envoyés lors de l'appel.
        assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());//vérifie que l'ID envoyé au service est bien celui de l'objet qu'on voulait modifier
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());//vérifie que le nom de Beer reçu par le service est
        //bien NewName
    }

    @Test
    void getBeerByIdNotFound() throws Exception{
        //given(beerService.getBeerById(any(UUID.randomUUID().getClass()))).willReturn(NotFoundException.class);
        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception{
        BeerDTO beerDTO = BeerDTO.builder().build();

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.length()", is(6)))
                        .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateBlankBeer() throws Exception{
        BeerDTO beer = beerServiceImpl.listBeers().get(1);
        beer.setBeerName("");
        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put("/api/v1/beer/" + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest());

        //verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));//vérifie que la méthode update... a été appelé

    }
}
