package elprofesor.spring.springsection5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elprofesor.spring.springsection5.model.CustomerDTO;
import elprofesor.spring.springsection5.services.CustomerService;
import elprofesor.spring.springsection5.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testDeleteCustomer() throws Exception{
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
        given(customerService.deleteCustomerById(any(UUID.class))).willReturn(true);
        mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void getCustomerByIdNotFound() throws Exception{
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
