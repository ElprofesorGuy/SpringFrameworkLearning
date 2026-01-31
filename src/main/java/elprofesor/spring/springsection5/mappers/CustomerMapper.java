package elprofesor.spring.springsection5.mappers;

import elprofesor.spring.springsection5.entities.Customer;
import elprofesor.spring.springsection5.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
