package DuAn_64132265.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "class_registration")
public class ClassRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class aClass;

    @Column(name = "registration_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp registrationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_date")
    private Date expiredDate;

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	@PrePersist
    public void prePersist() {
        if (registrationDate == null) {
            registrationDate = new Timestamp(System.currentTimeMillis());
        }
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Class getaClass() {
		return aClass;
	}

	public void setaClass(Class aClass) {
		this.aClass = aClass;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String toString() {
		return super.toString();
	}  
}
