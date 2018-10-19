package myrest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "state")
public class State {
	@Id
	@GeneratedValue
	private int id;
	
	private String state;

	// constructor
	public State() {
	}

	public int getId() {
		return id;
	}

	public String getState() {
		return state;
	}

}
