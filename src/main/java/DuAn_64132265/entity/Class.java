package DuAn_64132265.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "max_students", nullable = false)
    private Integer maxStudents;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Integer getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(Integer maxStudents) {
		this.maxStudents = maxStudents;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	@Override
	public String toString() {
	    return "Class{id=" + id + ", className='" + className + "', description='" + description + "', schedule=" + schedule + ", maxStudents=" + maxStudents + ", trainer=" + trainer + "}";
	}
}
