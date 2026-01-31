package elprofesor.spring.springsection5.services;

import elprofesor.spring.springsection5.controller.NotFoundException;
import elprofesor.spring.springsection5.mappers.CustomerMapper;
import elprofesor.spring.springsection5.model.CustomerDTO;
import elprofesor.spring.springsection5.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id).orElseThrow(NotFoundException::new)));
    }

    @Override
    public CustomerDTO savedCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }

        return false;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO dto = CustomerDTO.builder().build();
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer ->{
            foundCustomer.setCustomerName(customer.getCustomerName());
            customerRepository.save(foundCustomer);
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () ->{
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
