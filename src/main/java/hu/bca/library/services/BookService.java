package hu.bca.library.services;

import hu.bca.library.models.Book;
import hu.bca.library.models.PublishedYearDTO;

import java.util.List;

public interface BookService {
  Book addAuthor(Long bookId, Long authorId);

  List<PublishedYearDTO> updateAllBooksWithPublicationYear();

}
