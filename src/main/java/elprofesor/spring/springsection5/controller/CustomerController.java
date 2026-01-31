package elprofesor.spring.springsection5.controller;

import elprofesor.spring.springsection5.model.CustomerDTO;
import elprofesor.spring.springsection5.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> listCustomers(){
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public CustomerDTO getCustomerById(@PathVariable("customerId")UUID customerId){
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.savedCustomer(customer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/customer/" + savedCustomer.getId());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customer){
        if(customerService.updateCustomerById(customerId, customer).isEmpty()){
            throw  new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity patchCustomer(@PathVariable UUID customerId, @RequestBody CustomerDTO customer){
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable UUID customerId){
        if(! customerService.deleteCustomerById(customerId)){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
