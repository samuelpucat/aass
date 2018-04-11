package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CHANCELLOR")
public class Chancellor {
	private Integer id;
	private String name;
	private University university;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	@OneToOne(mappedBy="chancellor")
	public University getUniversity() {
		return university;
	}
	
	public void setUniversity(University university) {
		this.university = university;
	}
	
}
