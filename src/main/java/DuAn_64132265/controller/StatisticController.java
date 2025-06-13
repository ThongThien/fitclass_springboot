package DuAn_64132265.controller;

import DuAn_64132265.service.ClassRegistrationService;
import DuAn_64132265.service.ClassService;
import DuAn_64132265.service.StudentService;
import DuAn_64132265.service.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassRegistrationService classRegistrationService;

    @Autowired
    private ClassService classService;

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        int totalStudents = studentService.getAllStudents().size();
        int totalRegisteredStudents = classRegistrationService.getAllRegistrations().stream()
                .map(reg -> reg.getStudent().getId())
                .distinct()
                .toArray().length;
        int totalClasses = classService.getAllClasses().size();
        int totalTrainers = trainerService.getAllTrainers().size();

        double registrationRate = totalStudents == 0 ? 0 :
                (double) totalRegisteredStudents / totalStudents * 100;

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("registrationRate", String.format("%.2f", registrationRate));
        model.addAttribute("totalClasses", totalClasses);
        model.addAttribute("totalTrainers", totalTrainers);

        return "statistics/statistics";
    }
}
