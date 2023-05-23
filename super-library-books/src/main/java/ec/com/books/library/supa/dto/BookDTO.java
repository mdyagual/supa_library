package ec.com.books.library.supa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String isbn;
    private String title;
    private List<String> authors;
    private String year;
    private List<String> categories;
    private Boolean available;
}
