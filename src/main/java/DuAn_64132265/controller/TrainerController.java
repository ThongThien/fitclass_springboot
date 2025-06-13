package DuAn_64132265.controller;

import DuAn_64132265.entity.Trainer;
import DuAn_64132265.entity.Class;
import DuAn_64132265.service.ClassService;
import DuAn_64132265.service.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;
    
    @Autowired
    ClassService classService;
    
    @GetMapping
    public String listTrainers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Trainer> trainers = (keyword == null || keyword.isEmpty())
            ? trainerService.getAllTrainers()
            : trainerService.searchTrainersByName(keyword);
        model.addAttribute("trainers", trainers);
        model.addAttribute("keyword", keyword);
        return "trainer/list_trainer";
    }


    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "trainer/trainer_form";
    }

    @PostMapping("/new")
    public String saveNewTrainer(@Validated @ModelAttribute("trainer") Trainer trainer,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "trainer/trainer_form";
        }
        trainerService.saveTrainer(trainer);
        return "redirect:/trainers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trainer> trainerOpt = Optional.of(trainerService.getTrainerById(id));
        if (trainerOpt.isPresent()) {
            model.addAttribute("trainer", trainerOpt.get());
            return "trainer/trainer_form";
        } else {
            return "redirect:/trainers";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTrainer(@PathVariable("id") Integer id,
                                @Validated @ModelAttribute("trainer") Trainer trainer,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "trainer/trainer_form";
        }
        trainer.setId(id);
        trainerService.saveTrainer(trainer);
        return "redirect:/trainers";
    }
    
    @GetMapping("/delete/{id}")
    public String showDeleteConfirm(@PathVariable("id") Integer id, Model model) {
        Optional<Trainer> trainerOpt = Optional.of(trainerService.getTrainerById(id));
        if (trainerOpt.isEmpty()) {
            return "redirect:/trainers";
        }
        Trainer trainer = trainerOpt.get();

        List<Class> classes = classService.getClassesByTrainerId(id);

        model.addAttribute("trainer", trainer);
        model.addAttribute("classes", classes);

        return "trainer/confirm_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable("id") Integer id, Model model) {
        trainerService.deleteTrainer(id);
        return "redirect:/trainers";
    }

}
