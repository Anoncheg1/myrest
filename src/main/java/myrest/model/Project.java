package myrest.model;

import java	.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.ForeignKey;

@Entity
@Table(name = "project") //не требуется, достаточно интеллектуальный, чтобы это не указывать
public class Project{
	@Id	
	@GenericGenerator(name = "XyzIdGenerator",
	        strategy = "myrest.IdGenerator")
	@GeneratedValue(generator = "XyzIdGenerator")
	private int id;
	
	@OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
	private List<Job> jobs = new ArrayList<>();
	@Column(name = "`name`")
	private String name;
	private Double fb;
	private Double rb;
	@Column(name = "mid_pr")
	private double midPr;
	
	@Column(name = "id_state") //FK
	private int idState;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_state",
			foreignKey = @ForeignKey(name = "state_fk"), 
			insertable = false, updatable = false)
	private State state;
	
	
	//constructor
	public Project(){		
	}
	
			
	/**
	 * REST Создание проекта
	 * 
	 * @param name
	 * @param fb
	 * @param rb
	 * @param midPr
	 * @param state
	 * @param idState
	 */
	public Project(String name, double fb, double rb) {		
		this.name = name;
		this.fb = fb;
		this.rb = rb;
		this.midPr = 0;		
		this.idState = 3; //Реализуется без отклонений
	}
	
	/**
	 * for DAO
	 * 
	 * @param id
	 * @param name
	 * @param fb
	 * @param rb
	 * @param midPr
	 * @param idState
	 */
	/*Project(int id, String name, double fb, double rb, int idState, double midPr) {		
		this.id = id;
		this.name = name;
		this.fb = fb;
		this.rb = rb;
		this.midPr = midPr;
		this.idState = idState;
	}*/


	public int getId() {
		return id;
	}	
	public String getName() {
		return name;
	}

	public Double getFb() {
		return fb;
	}

	public Double getRb() {
		return rb;
	}

	public double getMidPr() {
		return midPr;
	}

	public State getState() {
		return state;
	}

	List<Job> getJobs() {
		return this.jobs;
	}

	void setMidPr(double midPr) {
		this.midPr = midPr;
	}

	void setIdState(int idState) {
		this.idState = idState;
	}

	void setName(String name) {
		this.name = name;
	}

	void setFb(double fb) {
		this.fb = fb;
	}

	public void setRb(double rb) {
		this.rb = rb;
	}
}
