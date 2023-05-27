package ec.com.students.library.supa.config;

import ec.com.students.library.supa.dto.StudentDTO;
import ec.com.students.library.supa.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {
    Student toEntity (StudentDTO studentDTO);

    @Mapping(target="id", expression = "java(student.getId())")
    StudentDTO toDto (Student student);
}
