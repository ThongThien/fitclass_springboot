package DuAn_64132265.controller;

import DuAn_64132265.entity.ClassRegistration;
import DuAn_64132265.service.ClassRegistrationService;
import DuAn_64132265.service.StudentService;
import DuAn_64132265.service.ClassService;
import DuAn_64132265.entity.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/classregister")
public class ClassRegisController {

    @Autowired
    private ClassRegistrationService classRegistrationService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @GetMapping
    public String listRegistrations(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<ClassRegistration> registrations;
        if (keyword == null || keyword.isEmpty()) {
            registrations = classRegistrationService.getAllRegistrations();
        } else {
            registrations = classRegistrationService.searchRegistrationsByStudentName(keyword);
        }
        model.addAttribute("registrations", registrations);
        model.addAttribute("keyword", keyword);
        return "classregister/list_registrations";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("registration", new ClassRegistration());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("classes", classService.getAllClasses());
        return "classregister/add_registration";
    }

    @PostMapping("/add")
    public String saveRegistration(@ModelAttribute("registration") ClassRegistration registration,
                                   BindingResult result, Model model) {

        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("classes", classService.getAllClasses());
        
        if (result.hasErrors()) {
            return "classregister/add_registration";
        }

        // Kiểm tra trùng học viên + lớp
        Optional<ClassRegistration> existed = classRegistrationService.findByStudentAndClass(
            registration.getStudent(), registration.getaClass());
        
        if (existed.isPresent()) {
            result.rejectValue("student", "error.registration", "Học viên này đã đăng ký lớp này rồi.");
            return "classregister/add_registration";
        }

        // Lấy lại class từ DB để đảm bảo không bị thiếu dữ liệu
        Integer classId = registration.getaClass().getId();
        Class selectedClass = classService.getClassById(classId);

        int currentCount = classRegistrationService.countByClass(selectedClass);
        if (currentCount >= selectedClass.getMaxStudents()) {
            result.rejectValue("aClass", "error.registration", "Lớp đã đủ học viên.");
            return "classregister/add_registration";
        }

        // Tất cả đều hợp lệ → lưu
        registration.setaClass(selectedClass);
        classRegistrationService.saveRegistration(registration);
        return "redirect:/classregister";
    }
    
    @PostMapping("/extend/{regId}")
    public String extendRegistration(@PathVariable("regId") Integer regId,
                                     @RequestParam("extend_type") String extendType) {
        ClassRegistration reg = classRegistrationService.getClassRegistrationById(regId);
        if (reg == null) {
            return "redirect:/classregister";
        }

        int days = 0;
        switch (extendType) {
            case "15d": days = 15; break;
            case "1m": days = 30; break;
            case "2m": days = 60; break;
            default: days = 0;
        }

        Date now = new Date();
        Calendar cal = Calendar.getInstance();

        if (reg.getExpiredDate() == null) {
            cal.setTime(now);
        } else {
            cal.setTime(reg.getExpiredDate());
        }

        cal.add(Calendar.DAY_OF_MONTH, days);
        reg.setExpiredDate(cal.getTime());

        classRegistrationService.saveRegistration(reg);
        return "redirect:/classregister";
    }

    @GetMapping("/delete/{studentId}/{classId}")
    public String deleteRegistration(@PathVariable("studentId") Integer studentId,
                                     @PathVariable("classId") Integer classId) {
        classRegistrationService.deleteByStudentAndClass(studentId, classId);
        return "redirect:/classregister";
    }

}
