package elprofesor.spring.springsection5.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Book {
    private UUID id;
    private String bookTitle;
    private String version;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime editedDate;
}
