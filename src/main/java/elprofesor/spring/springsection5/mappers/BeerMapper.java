package elprofesor.spring.springsection5.mappers;

import elprofesor.spring.springsection5.entities.Beer;
import elprofesor.spring.springsection5.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
