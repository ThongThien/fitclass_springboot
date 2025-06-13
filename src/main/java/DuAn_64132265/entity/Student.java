package DuAn_64132265.entity;


import java.util.Date;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    public enum Gender {
        Male, Female
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
        return "Student{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", dob=" + dob +
        ", address='" + address + '\'' +
        ", gender=" + gender +
        '}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
