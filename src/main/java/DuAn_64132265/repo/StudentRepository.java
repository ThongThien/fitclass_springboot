package DuAn_64132265.repo;

import DuAn_64132265.entity.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByEmail(String email);
    
    List<Student> findByNameContainingIgnoreCase(String name);
}
