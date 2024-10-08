package hu.bca.library.services.impl;

import hu.bca.library.config.OpenLibraryClient;
import hu.bca.library.models.Author;
import hu.bca.library.models.Book;
import hu.bca.library.models.BookByAuthorNationDTO;
import hu.bca.library.models.PublishedYearDTO;
import hu.bca.library.repositories.AuthorRepository;
import hu.bca.library.repositories.BookRepository;
import hu.bca.library.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private OpenLibraryClient openLibraryClient;

  public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                         OpenLibraryClient openLibraryClient) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.openLibraryClient = openLibraryClient;
  }

  @Override
  public Book addAuthor(Long bookId, Long authorId) {
    Optional<Book> book = this.bookRepository.findById(bookId);
    if (book.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", bookId));
    }
    Optional<Author> author = this.authorRepository.findById(authorId);
    if (author.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Author with id %s not found", authorId));
    }

    List<Author> authors = book.get().getAuthors();
    authors.add(author.get());

    book.get().setAuthors(authors);
    return this.bookRepository.save(book.get());
  }

  @Override
  public List<PublishedYearDTO> updateAllBooksWithPublicationYear() {
    List<Book> books = bookRepository.findAll();
    for (Book book : books) {
      Integer publicationYear = openLibraryClient.getPublicationYear(book.getWorkId());
      if (publicationYear != null) {
        book.setYear(publicationYear);
        bookRepository.save(book);
      }
    }
    return books.stream().map(book -> new PublishedYearDTO(book.getTitle(), book.getYear())).collect(Collectors.toList());
  }

  @Override
  public List<BookByAuthorNationDTO> getAllBooksByCountry(String country, Integer from) {
    return bookRepository.findAllByConditions(country, from)
            .stream()
            .map(book -> new BookByAuthorNationDTO(book.getId(), book.getTitle(), book.getYear()))
            .collect(Collectors.toList());
  }

}
