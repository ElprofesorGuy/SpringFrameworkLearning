package elprofesor.spring.springsection5.controller;

import elprofesor.spring.springsection5.model.Book;
import elprofesor.spring.springsection5.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/v1/Book/")
    public List<Book> listBooks(){
        return bookService.getListBook();
    }

    @GetMapping("/api/v1/Book/" + "{bookId}")
    public Book getBookById(@PathVariable("bookId") UUID bookId){
        return bookService.getBookById(bookId);
    }

    @PostMapping("/api/v1/Book/")
    public ResponseEntity postBook(@RequestBody Book newBook){
        Book savedBook = bookService.createBook(newBook);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/Book/" + newBook.getId());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/Book/" + "{bookId}")
    public ResponseEntity putBook(@PathVariable("bookId") UUID bookId, @RequestBody Book book){
        Book updatedBook = bookService.updateBookById(bookId, book);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/Book/{bookId}")
    public ResponseEntity deleteBook (@PathVariable("bookId")UUID bookId){
        bookService.deleteBookById(bookId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/api/v1/Book/{bookId}")
    public ResponseEntity patchBook(@PathVariable("bookId")UUID bookId, @RequestBody Book book){
        bookService.patchBookById(bookId, book);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
