package hu.bca.library.controllers;

import hu.bca.library.models.Book;
import hu.bca.library.models.BookByAuthorNationDTO;
import hu.bca.library.models.PublishedYearDTO;
import hu.bca.library.services.BookService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController("books")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping("/{bookId}/add_author/{authorId}")
  @ResponseBody
  Book addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
    return this.bookService.addAuthor(bookId, authorId);
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("/update-all-with-year")
  @ResponseBody
  List<PublishedYearDTO> updateAllBooksWithTheirPublishedYear() {
    return bookService.updateAllBooksWithPublicationYear();
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("/query/{country}")
  @ResponseBody
  List<BookByAuthorNationDTO> getAllBooksFromGivenCountry(@PathVariable String country,
                                                          @RequestParam(required = false) Integer from) {
    return bookService.getAllBooksByCountry(country, from);
  }

}
