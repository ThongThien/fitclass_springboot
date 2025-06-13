package DuAn_64132265.controller;

import DuAn_64132265.entity.ClassRegistration;
import DuAn_64132265.entity.Student;
import DuAn_64132265.service.ClassRegistrationService;
import DuAn_64132265.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ClassRegistrationService classRegistrationService;  
    
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students/list_students"; 
    }
    
    @GetMapping("/search")
    public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
        List<Student> students = studentService.searchStudentsByName(keyword);
        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);  
        return "students/list_students";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());  
        return "students/student_form";  
    }

    @PostMapping
    public String createStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student); 
        return "redirect:/students";  
    }

    @GetMapping("/details/{id}")
    public String getStudentDetails(@PathVariable("id") Integer id, Model model) {
        Optional<Student> studentOptional = studentService.getStudentById(id);
        if (studentOptional.isPresent()) {
            model.addAttribute("student", studentOptional.get());
            return "students/student_detail";  
        } else {
            return "redirect:/students";  
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Student> studentOptional = studentService.getStudentById(id);
        if (studentOptional.isPresent()) {
            model.addAttribute("student", studentOptional.get());
            return "students/student_form";  
        } else {
            return "redirect:/students";  
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") Integer id, 
    							@Validated @ModelAttribute Student student, 
    							BindingResult result) {
        if (result.hasErrors()) {
            return "students/student_form";  
        }
        student.setId(id);  
        studentService.saveStudent(student); 
        return "redirect:/students";  
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable("id") Integer id, Model model) {
        // Kiểm tra nếu sinh viên tham gia lớp học nào
        List<ClassRegistration> classRegistrations = classRegistrationService.findByStudentId(id);
        Optional<Student> studentOptional = studentService.getStudentById(id);
        
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();  
            
            // Nếu sinh viên tham gia lớp học, thêm thông tin vào model
            if (!classRegistrations.isEmpty()) {
                String classNames = classRegistrations.stream()
                        .map(classRegistration -> classRegistration.getaClass().getClassName())  
                        .collect(Collectors.joining(", "));
                model.addAttribute("message", "This student is enrolled in the following classes: " + classNames);
            }

            // Thêm thông tin sinh viên và ID vào model
            model.addAttribute("student", student);
            model.addAttribute("studentId", id);  
            
            return "students/confirm_delete"; 
        } else {
            return "redirect:/students?error=studentNotFound";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id) {
        studentService.deleteStudent(id);  
        return "redirect:/students"; 
    }
}

