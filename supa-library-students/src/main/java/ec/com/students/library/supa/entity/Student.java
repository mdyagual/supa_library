package ec.com.students.library.supa.entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@Entity
public class Student{
    @Id
    @GeneratedValue(generator = "custom_id_generator")
    @GenericGenerator(
            name = "custom_id_generator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "uuid_gen_strategy_class", value = "ec.com.students.library.supa.config.IdGenerator")
            }
    )
    private String id;
    private String regis_num;
    private String first_name;
    private String surname;
    private String faculty;
    private Boolean blocked;
}
