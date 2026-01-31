package elprofesor.spring.springsection5.bootstrap;

import elprofesor.spring.springsection5.entities.Beer;
import elprofesor.spring.springsection5.entities.Customer;
import elprofesor.spring.springsection5.model.BeerDTO;
import elprofesor.spring.springsection5.model.CustomerDTO;
import elprofesor.spring.springsection5.repositories.BeerRepository;
import elprofesor.spring.springsection5.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception{
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Isenbeck")
                    .upc("561234")
                    .price(new BigDecimal(11.75))
                    .quantityOnHand(134)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Castel")
                    .upc("1896541436")
                    .price(new BigDecimal(10.33))
                    .quantityOnHand(101)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Grande Guiness")
                    .upc("47586513")
                    .quantityOnHand(75)
                    .price(new BigDecimal(12.45))
                    .createDate(LocalDateTime.now())
                    .createDate(LocalDateTime.now())
                    .build();
            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }

    private void loadCustomerData() {
        if(customerRepository.count() == 0){
            Customer customer1 = Customer.builder()
                    .customerName("El Profesor")
                    .version(3)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("La douceur")
                    .version(2)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Divina")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer3, customer2, customer1));
        }
        
    }
}
