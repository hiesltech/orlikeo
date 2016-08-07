package umk.zychu.inzynierka.controller.DTObeans;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;


@SuppressWarnings("serial")
@JsonAutoDetect
public class JsonTestObject implements Serializable{

	
	public JsonTestObject(String first, String first2) {
		// TODO Auto-generated constructor stub
		this.name = first;
		this.surname = first2;
	}


	public JsonTestObject() {
		// TODO Auto-generated constructor stub
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


	String name;
	@Override
	public String toString() {
		return "JsonTestObject [name=" + name + ", surname=" + surname + "]";
	}


	String surname;
}
