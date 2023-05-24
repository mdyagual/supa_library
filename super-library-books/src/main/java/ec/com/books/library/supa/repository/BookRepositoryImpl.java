package ec.com.books.library.supa.repository;

import ec.com.books.library.supa.dto.BookDTO;
import ec.com.books.library.supa.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository{
    private final EntityManager postgres;

    @Override
    public List<Book> findAll() {
        return postgres.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }

    @Override
    public Book findById(String idBook) {
        return postgres.find(Book.class, idBook);
    }

    @Override
    public void add(Book book) {
        postgres.persist(book);
    }

    @Override
    public Book modify(Book book) {
        return Objects.isNull(findById(book.getId())) ? new Book() : postgres.merge(book);
    }

    @Override
    public void remove(String idBook) {
        postgres.remove(findById(idBook));
    }
}
