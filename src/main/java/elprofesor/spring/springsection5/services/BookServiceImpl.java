package elprofesor.spring.springsection5.services;

import ch.qos.logback.core.util.StringUtil;
import elprofesor.spring.springsection5.model.Book;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private Map<UUID, Book> bookMap;

    public BookServiceImpl(){
        this.bookMap = new HashMap<UUID, Book>();

        Book book1 = Book.builder()
                .id(UUID.randomUUID())
                .bookTitle("Au Bonheur des Dames")
                .quantityOnHand(50)
                .version("1")
                .price(new BigDecimal(12.50))
                .editedDate(LocalDateTime.now())
                .build();

        Book book2 = Book.builder()
                .id(UUID.randomUUID())
                .bookTitle("Le Misanthrope de Molière")
                .price(new BigDecimal(15.2))
                .version("1.0")
                .editedDate(LocalDateTime.now())
                .quantityOnHand(45)
                .build();

        Book book3 = Book.builder()
                .id(UUID.randomUUID())
                .bookTitle("Cendrillon")
                .quantityOnHand(75)
                .price(new BigDecimal(32.41))
                .editedDate(LocalDateTime.now())
                .version("1.0")
                .build();

        Book book4 = Book.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal(15.78))
                .version("1.2")
                .quantityOnHand(50)
                .editedDate(LocalDateTime.now())
                .bookTitle("Les Tribus de Capitoline")
                .build();

        Book book5 = Book.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal(39.99))
                .version("1.3")
                .quantityOnHand(80)
                .bookTitle("Une saison blanche et sèche")
                .editedDate(LocalDateTime.now())
                .build();

        bookMap.put(book1.getId(), book1);
        bookMap.put(book2.getId(), book2);
        bookMap.put(book3.getId(), book3);
        bookMap.put(book4.getId(), book4);
        bookMap.put(book5.getId(), book5);
    }

    @Override
    public List<Book> getListBook() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public Book getBookById(UUID bookId) {
        return bookMap.get(bookId);
    }

    @Override
    public Book createBook(Book newBook) {
        Book createdBook = Book.builder()
                .id(UUID.randomUUID())
                .price(newBook.getPrice())
                .version(newBook.getVersion())
                .quantityOnHand(newBook.getQuantityOnHand())
                .editedDate(LocalDateTime.now())
                .bookTitle(newBook.getBookTitle())
                .build();

        bookMap.put(createdBook.getId(), createdBook);
        return createdBook;
    }

    @Override
    public Book updateBookById(UUID bookId, Book book) {
        Book existingBook = bookMap.get(bookId);

        existingBook.setBookTitle(book.getBookTitle());
        existingBook.setPrice(book.getPrice());
        existingBook.setVersion(book.getVersion());
        existingBook.setEditedDate(LocalDateTime.now());

        bookMap.put(existingBook.getId(), existingBook);

        return existingBook;
    }

    @Override
    public void deleteBookById(UUID bookId) {
        bookMap.remove(bookId);
    }

    @Override
    public void patchBookById(UUID bookId, Book book) {
        Book existingBook = bookMap.get(bookId);

        if(StringUtils.hasText(existingBook.getBookTitle())){
            existingBook.setBookTitle(book.getBookTitle());
        }
        if(StringUtils.hasText(existingBook.getVersion())){
            existingBook.setVersion(book.getVersion());
        }
        if(book.getPrice() != null){
            existingBook.setPrice(book.getPrice());
        }
        if(book.getQuantityOnHand() != null){
            existingBook.setQuantityOnHand(book.getQuantityOnHand());
        }

        bookMap.put(existingBook.getId(), existingBook);
    }


}
