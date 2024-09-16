package hu.bca.library.repositories;

import hu.bca.library.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findAll();

}
