package DuAn_64132265.service;

import DuAn_64132265.entity.Class;
import DuAn_64132265.repo.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<Class> getAllClasses() {
        return classRepository.findAll();  
    }
    public List<Class> searchClassesByName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return getAllClasses();
        }
        return classRepository.findByClassNameContainingIgnoreCase(className);
    }
    public Class getClassById(Integer id) {
        return classRepository.findById(id).orElse(null);
    }

    public Class saveClass(Class aClass) {
        return classRepository.save(aClass);
    }

    public void deleteClass(Integer id) {
        classRepository.deleteById(id);
    }

    public Class getClassByClassName(String className) {
        return classRepository.findByClassName(className);
    }
    public void updateClass(Class cls) {
    	
    }

    public List<Class> getClassesByTrainerId(Integer trainerId) {
        return classRepository.findByTrainerId(trainerId);
    }
}
