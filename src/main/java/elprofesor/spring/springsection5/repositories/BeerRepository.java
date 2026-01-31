package elprofesor.spring.springsection5.repositories;

import elprofesor.spring.springsection5.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID>{
    UUID id(UUID id);
}
