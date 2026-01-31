package elprofesor.spring.springsection5.services;

import elprofesor.spring.springsection5.model.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<Book> getListBook();


    Book getBookById(UUID bookId);

    Book createBook(Book newBook);

    Book updateBookById(UUID bookId, Book book);

    void deleteBookById(UUID bookId);


    void patchBookById(UUID bookId, Book book);
}
