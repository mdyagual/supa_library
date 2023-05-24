package ec.com.books.library.supa.repository;

import ec.com.books.library.supa.entity.Book;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface BookRepository {
   List<Book> findAll();
   Book findById(String idBook);
   void add(Book book);
   Book modify(Book book);
   void remove(String idBook);
}
