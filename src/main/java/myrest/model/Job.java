package myrest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "job")
public class Job {
	@Id
	@GenericGenerator(name = "XyzIdGenerator", strategy = "myrest.IdGenerator")
	@GeneratedValue(generator = "XyzIdGenerator")
	private int id;

	@Column(name = "id_project") // FK
	private int idProject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_project", foreignKey = @ForeignKey(name = "project_fk"), insertable = false, updatable = false)
	private Project project;
	@Column(name = "`name`")
	private String name;
	private Double fb;
	private Double rb;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date nach; // приходится использовать старый формат из за Jackson
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date okonch;
	private Double dliteln;
	@Column(name = "vip_pr")
	private Double vipPr;

	/**
	 * REST Создание работы
	 * 
	 * @param id
	 * @param name
	 * @param fb
	 * @param rb
	 * @param nach
	 * @param okonch
	 * @param dliteln
	 * @param vipPr
	 */
	public Job(int idProject, String name, double fb, double rb, Date nach, double dliteln) {
		this.idProject = idProject;
		this.name = name;
		this.fb = fb;
		this.rb = rb;
		this.nach = nach;
		this.okonch = null;
		this.dliteln = dliteln;
		this.vipPr = 0d;
	}

	// constructor
	public Job() {
	}

	public Double getRb() {
		return rb;
	}

	public void setRb(double rb) {
		this.rb = rb;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getFb() {
		return fb;
	}

	public Date getNach() {
		return nach;
	}

	public Date getOkonch() {
		return okonch;
	}

	public Double getDliteln() {
		if(dliteln != null)
			return dliteln;
		else 
			return null;
	}

	public Double getVipPr() {
		return vipPr;
	}

	Project getProject() {
		return project;
	}

	public int getIdProject() {
		return idProject;
	}

	void setName(String name) {
		this.name = name;
	}

	void setFb(double fb) {
		this.fb = fb;
	}

	void setNach(Date nach) {
		this.nach = nach;
	}

	void setOkonch(Date okonch) {
		this.okonch = okonch;
	}

	void setDliteln(Double dliteln) {
		this.dliteln = dliteln;
	}

	void setVipPr(Double vipPr) {
		this.vipPr = vipPr;
	}
}
