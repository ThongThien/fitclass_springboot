package DuAn_64132265.controller;

import DuAn_64132265.entity.Class;
import DuAn_64132265.entity.Trainer;
import DuAn_64132265.service.ClassRegistrationService;
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
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;
    
    @Autowired
    TrainerService trainerService;
    
    @Autowired
    ClassRegistrationService classRegistrationService;
    
    @GetMapping
    public String getAllClasses(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Class> classes = (keyword == null || keyword.isEmpty())
            ? classService.getAllClasses()
            : classService.searchClassesByName(keyword);
        model.addAttribute("classes", classes);
        model.addAttribute("keyword", keyword);
        return "class/list_class";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Class> classOptional = Optional.of(classService.getClassById(id));
        if (classOptional.isPresent()) {
            model.addAttribute("class", classOptional.get());
            return "class/class_form"; 
        } else {
            return "redirect:/classes"; 
        }
    }

    @PostMapping("/edit/{id}")
    public String updateClass(@PathVariable("id") Integer id, 
							@Validated @ModelAttribute Class cls, 
							BindingResult result) {
        if (result.hasErrors()) {
            return "class/class_form"; 
        }
        cls.setId(id);
        classService.saveClass(cls);
        return "redirect:/classes";
    }
    
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("class", new Class());
        model.addAttribute("trainers", trainerService.getAllTrainers()); 
        return "class/add_class"; 
    }

    @PostMapping("/new")
    public String saveNewClass(@Validated @ModelAttribute("class") Class cls,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trainers", trainerService.getAllTrainers());
            return "class/add_class";
        }

        Integer trainerId = (cls.getTrainer() != null) ? cls.getTrainer().getId() : null;
        if (trainerId != null) {
            Optional<Trainer> trainerOpt = Optional.of(trainerService.getTrainerById(trainerId));
            if (trainerOpt.isPresent()) {
                cls.setTrainer(trainerOpt.get());
            } else {
                result.rejectValue("trainer", "error.class", "Trainer không hợp lệ");
                model.addAttribute("trainers", trainerService.getAllTrainers());
                return "class/add_class";
            }
        } else {
            result.rejectValue("trainer", "error.class", "Trainer không được để trống");
            model.addAttribute("trainers", trainerService.getAllTrainers());
            return "class/add_class";
        }

        classService.saveClass(cls);
        return "redirect:/classes";
    }
    
    @GetMapping("/delete/{id}")
    public String showDeleteConfirm(@PathVariable("id") Integer id, Model model) {
        Optional<Class> classOpt = Optional.of(classService.getClassById(id));
        if (classOpt.isEmpty()) {
            return "redirect:/classes"; 
        }

        Class cls = classOpt.get();
        int registeredCount = classRegistrationService.countByClassId(id); 
        model.addAttribute("class", cls);
        model.addAttribute("registeredCount", registeredCount);

        return "class/confirm_delete"; 
    }
    
    @PostMapping("/delete/{id}")
    public String deleteClass(@PathVariable("id") Integer id) {
        classService.deleteClass(id);
        return "redirect:/classes";
    }
}
