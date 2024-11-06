package ec.com.books.library.supa.service;

import ec.com.books.library.supa.config.BookMapper;

import ec.com.books.library.supa.dto.BookDTO;
import ec.com.books.library.supa.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ApplicationScoped
public class BookService {
    private final BookMapper mapper;
    private final BookRepository repository;


    public List<BookDTO> getALl(){
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }


    public BookDTO getById(String idBook){
        return mapper.toDto(repository.findById(idBook));
    }

    @Transactional
    public BookDTO save(BookDTO bookDTO){
        repository.add(mapper.toEntity(bookDTO));
        return bookDTO;
    }

    @Transactional
    public BookDTO update(BookDTO bookDTO){
        return mapper.toDto(repository.modify(mapper.toEntity(bookDTO)));
    }

    @Transactional
    public String delete(String idBook){
        repository.remove(idBook);
        return idBook;
    }









}
