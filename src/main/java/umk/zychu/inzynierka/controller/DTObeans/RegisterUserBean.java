package umk.zychu.inzynierka.controller.DTObeans;

public class RegisterUserBean {

	String email;
	String password;
	String repeatedPassword;
	Boolean acceptRegulation;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	public Boolean getAcceptRegulation() {
		return acceptRegulation;
	}
	public void setAcceptRegulation(Boolean acceptRegulation) {
		this.acceptRegulation = acceptRegulation;
	}
	
}
