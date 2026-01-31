package elprofesor.spring.springsection5.services;

import elprofesor.spring.springsection5.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO savedCustomer(CustomerDTO customer);

    Boolean deleteCustomerById(UUID customerId);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
