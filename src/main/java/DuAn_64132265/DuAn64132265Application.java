package DuAn_64132265;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DuAn64132265Application {

	public static void main(String[] args) {
		SpringApplication.run(DuAn64132265Application.class, args);
	}
    @Bean
    public CommandLineRunner testDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                String sql = "SELECT COUNT(*) FROM students";
                Integer studentCount = jdbcTemplate.queryForObject(sql, Integer.class);
                System.out.println("✅ Đã kết nối DB thành công. Số học viên: " + studentCount);
            } catch (Exception e) {
                System.out.println("❌ Kết nối DB không thành công: " + e.getMessage());
            }
        };
    }
}
