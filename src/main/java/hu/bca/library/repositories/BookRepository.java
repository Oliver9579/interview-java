package hu.bca.library.repositories;

import hu.bca.library.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findAll();

  @Query(value = "SELECT B.*, A.name FROM Book B, Author A, book_author BA WHERE A.country = ?1 " +
          "AND (IFNULL(?2, 0) <= B.year) AND B.id = BA.book_id AND A.id = BA.author_id ORDER BY B.year ASC;", nativeQuery = true)
  List<Book> findAllByConditions(String country, Integer from);

}
