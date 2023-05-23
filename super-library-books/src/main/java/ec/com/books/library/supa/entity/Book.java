package ec.com.books.library.supa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Entity
public class Book {
    @Id
    @GeneratedValue(generator = "custom_id_generator")
    @GenericGenerator(
            name = "custom_id_generator",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(name = "uuid_gen_strategy_class", value = "ec.com.books.library.supa.config.IdGenerator")
            }
    )
    private String id;
    private String isbn;
    private String title;
    private List<String> authors;
    private String year;
    private List<String> categories;
    private Boolean available;

}
