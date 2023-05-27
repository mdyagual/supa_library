package ec.com.students.library.supa.service;

import ec.com.students.library.supa.config.StudentMapper;
import ec.com.students.library.supa.dto.StudentDTO;
import ec.com.students.library.supa.repository.StudentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class StudentService {
   private final StudentRepository postgres;
   private final StudentMapper mapper;

   public List<StudentDTO> getAll(){
       return postgres.findAll().stream().map(mapper::toDto).toList();
   }

   public StudentDTO getById(String idStudent){
       return mapper.toDto(postgres.findById(idStudent));
   }

   public StudentDTO getByRegisNum(String regisNum){
       return mapper.toDto(postgres.findByRegisNum(regisNum));
   }
   @Transactional
   public StudentDTO save(StudentDTO studentDTO){
      return mapper.toDto(postgres.add(mapper.toEntity(studentDTO)));
   }

   @Transactional
   public StudentDTO update(StudentDTO studentDTO){
       return mapper.toDto(postgres.modify(mapper.toEntity(studentDTO)));
   }

   @Transactional
   public Integer delete(String idStudent){
       return postgres.remove(idStudent);
   }


}
