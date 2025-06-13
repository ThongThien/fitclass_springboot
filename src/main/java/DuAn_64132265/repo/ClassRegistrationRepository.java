package DuAn_64132265.repo;

import DuAn_64132265.entity.ClassRegistration;
import DuAn_64132265.entity.Student;
import DuAn_64132265.entity.Class;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistration, Integer> {
    int countByAClass_Id(Integer classId);
    
    int countByAClass(Class aClass);
    
    Optional<ClassRegistration> findByStudentAndAClass(Student student, Class class1);
    
    List<ClassRegistration> findByStudentId(Integer studentId);
    
    void deleteByStudent_IdAndAClass_Id(Integer studentId, Integer classId);
    
    @Query("SELECT cr FROM ClassRegistration cr WHERE LOWER(cr.student.name) LIKE LOWER(CONCAT('%', :studentName, '%'))")
    List<ClassRegistration> findByStudentNameContainingIgnoreCase(@Param("studentName") String studentName);
}
