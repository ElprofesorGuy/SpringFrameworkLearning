package elprofesor.spring.springsection5.repositories;

import elprofesor.spring.springsection5.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
