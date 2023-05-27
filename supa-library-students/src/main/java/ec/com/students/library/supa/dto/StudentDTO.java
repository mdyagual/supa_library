package ec.com.students.library.supa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String id;
    private String regis_num;
    private String first_name;
    private String surname;
    private String faculty;
    private Boolean blocked;
}
