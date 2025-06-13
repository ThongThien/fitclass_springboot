package DuAn_64132265.repo;

import DuAn_64132265.entity.Class;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    Class findByClassName(String className);
    
    List<Class> findByTrainerId(Integer trainerId);
    
    List<Class> findByClassNameContainingIgnoreCase(String className);
}
