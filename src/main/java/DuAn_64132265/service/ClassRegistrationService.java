package DuAn_64132265.service;

import DuAn_64132265.entity.ClassRegistration;
import DuAn_64132265.entity.Student;
import DuAn_64132265.repo.ClassRegistrationRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import DuAn_64132265.entity.Class;
@Service
public class ClassRegistrationService {

	@Autowired
    private ClassRegistrationRepository classRegistrationRepository;

    public List<ClassRegistration> getAllRegistrations() {
        return classRegistrationRepository.findAll();
    }

    public ClassRegistration getClassRegistrationById(Integer id) {
        return classRegistrationRepository.findById(id).orElse(null);
    }

    public ClassRegistration saveRegistration(ClassRegistration registration) {
        return classRegistrationRepository.save(registration);
    }

    public void deleteClassRegistration(Integer id) {
        classRegistrationRepository.deleteById(id);
    }

    public List<ClassRegistration> findByStudentId(Integer studentId) {
        return classRegistrationRepository.findByStudentId(studentId);
    }

    public Optional<ClassRegistration> findByStudentAndClass(Student student, Class aClass) {
        return classRegistrationRepository.findByStudentAndAClass(student, aClass);
    }

    public int countByClassId(Integer classId) {
        return classRegistrationRepository.countByAClass_Id(classId);
    }
    public int countByClass(Class cls) {
        return classRegistrationRepository.countByAClass(cls);
    }

    @Transactional
    public void deleteByStudentAndClass(Integer studentId, Integer classId) {
        classRegistrationRepository.deleteByStudent_IdAndAClass_Id(studentId, classId);
    }
    public List<ClassRegistration> searchRegistrationsByStudentName(String keyword) {
        return classRegistrationRepository.findByStudentNameContainingIgnoreCase(keyword);
    }

}
