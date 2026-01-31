package elprofesor.spring.springsection5.controller;

import elprofesor.spring.springsection5.entities.Customer;
import elprofesor.spring.springsection5.mappers.CustomerMapper;
import elprofesor.spring.springsection5.model.CustomerDTO;
import elprofesor.spring.springsection5.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testCustomerNotFoundById(){
        assertThrows(NotFoundException.class, ()->{
            customerController.getCustomerById(UUID.randomUUID());
        });
    }


    @Test
    void testDeleteCustomerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomer(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdatedExistingCustomer(){
        Customer customer = customerRepository.findAll().get(1);
        CustomerDTO dto = customerMapper.customerToCustomerDto(customer);
        dto.setId(null);
        dto.setVersion(null);
        final String updateName = "UPDATED CUSTOMER NAME";
        dto.setCustomerName(updateName);

        ResponseEntity responseEntity = customerController.updateCustomer(customer.getId(), dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Customer updateCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updateCustomer.getCustomerName()).isEqualTo(updateName);
    }

    @Test
    void testUpdatedCustomerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testCustomerFindById(){
        Customer customer = customerRepository.findAll().get(1);
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();

    }

    @Test
    void testListCustomers(){
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(0);

    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer(){
        CustomerDTO dto = CustomerDTO.builder()
                .customerName("Deo Gracias")
                .build();
        ResponseEntity responseEntity = customerController.handlePost(dto);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String [] location = responseEntity.getHeaders().getLocation().getPath().split("/");

        System.out.println("Location 1 : " + location[1]);
        System.out.println("Location 2 : " + location[2]);
        System.out.println("Location 3 : " + location[3]);
        System.out.println("Location 4 : " + location[4]);
        UUID savedUUID = UUID.fromString(location[4]);

        assertThat(customerRepository.findById(savedUUID).get()).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomer(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

}