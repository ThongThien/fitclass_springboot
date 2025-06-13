package DuAn_64132265.service;

import DuAn_64132265.entity.Trainer;
import DuAn_64132265.repo.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }
    
    public List<Trainer> searchTrainersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllTrainers();
        }
        return trainerRepository.findByNameContainingIgnoreCase(name);
    }
    
    public Trainer getTrainerById(Integer id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public void deleteTrainer(Integer id) {
        trainerRepository.deleteById(id);
    }

    public Trainer getTrainerByEmail(String email) {
        return trainerRepository.findByEmail(email);
    }
}
