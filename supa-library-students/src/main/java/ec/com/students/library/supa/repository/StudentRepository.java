package ec.com.students.library.supa.repository;

import ec.com.students.library.supa.entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StudentRepository implements PanacheRepositoryBase<Student, String> {

    public Student findByRegisNum(String regisNum){

        return find("regis_num",regisNum).firstResult();
    }

    public List<Student> findByFaculty(String faculty){
        return new ArrayList<>(find("faculty", faculty).list());
    }

    public Student add(Student student){
        persist(student);
        return findByRegisNum(student.getRegis_num());

    }

    public Student modify(Student student){
        remove(student.getId());
        add(student);
        return findByRegisNum(student.getRegis_num());
    }

    public Integer remove(String idStudent){
        delete("id", idStudent);
        return Math.toIntExact(find("id", idStudent).count());
    }
}
