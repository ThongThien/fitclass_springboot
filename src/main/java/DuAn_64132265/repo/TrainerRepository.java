package DuAn_64132265.repo;

import DuAn_64132265.entity.Trainer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Trainer findByEmail(String email);
    
    List<Trainer> findByNameContainingIgnoreCase(String name);
}
