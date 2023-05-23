package ec.com.books.library.supa.config;

import ec.com.books.library.supa.dto.BookDTO;
import ec.com.books.library.supa.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {
    Book toEntity(BookDTO bookDTO);

    @Mapping(target="id", expression = "java(book.getId())")
    BookDTO toDto(Book book);
}
