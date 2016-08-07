package umk.zychu.inzynierka.controller.DTObeans;

import java.util.Date;

public class EditAccountForm {

	public EditAccountForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditAccountForm(String name, String surname, Date dateOfBirth,
			String position, Integer weight, Integer height, String foot) {
		super();
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.position = position;
		this.weight = weight;
		this.height = height;
		this.foot = foot;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getFoot() {
		return foot;
	}
	public void setFoot(String foot) {
		this.foot = foot;
	}
	String name;
	String surname;
	Date dateOfBirth;
	String position;
	Integer weight;
	Integer height;
	String foot;
	
}
