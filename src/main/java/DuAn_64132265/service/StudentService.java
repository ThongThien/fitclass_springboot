package DuAn_64132265.service;

import DuAn_64132265.entity.Student;
import DuAn_64132265.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public List<Student> searchStudentsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public Optional<Student> getStudentById(Integer id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
}
