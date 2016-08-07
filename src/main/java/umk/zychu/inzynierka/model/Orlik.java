package umk.zychu.inzynierka.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name="orlik")
public class Orlik extends BaseEntity {
	
	@NotBlank
	@Column(name = "address")
	String address;
	
	@NotBlank
	@Column(name = "city")
	String city;
	
	@Column(name = "lights")
	Boolean lights;
	
	@Column(name = "water")	
	Boolean water;
	
	@Column(name = "shower")	
	Boolean shower;
	
	@Column(name = "required_shoes")	
	String shoes = "turf";
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "orlik", orphanRemoval = true)
	private List<Graphic> graphicCollection;
	
/*	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "orlik_manager", joinColumns = { @JoinColumn(name = "orlik_id", referencedColumnName = "id", nullable = false) }, 
	inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) })
	List<User> orlikManagers;*/

	/*	public List<User> getOrlikManagers() {
		return orlikManagers;
	}

	public void setOrlikManagers(List<User> orlikManagers) {
		this.orlikManagers = orlikManagers;
	}*/


	@OneToOne
	@JoinColumn(name = "animator_id", nullable = true, unique = true)
	User animator;

	public Orlik(String address, String city, Boolean lights, Boolean water, Boolean shower, String shoes, User animator, List<Graphic> graphicCollection) {
		this.address = address;
		this.city = city;
		this.lights = lights;
		this.water = water;
		this.shower = shower;
		this.shoes = shoes;
		this.animator = animator;
		this.graphicCollection = graphicCollection;
	}

	public User getAnimator() {
		return animator;
	}

	public void setAnimator(User animator) {
		this.animator = animator;
	}
	

	public Orlik(){
		super();
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;		
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;		
	}	
	
	public void setLights(Boolean lights){
		this.lights = lights;
	}
	
	public Boolean getLights(){
		return lights;		
	}
	
	public void setWater(Boolean water){
		this.water = water;
	}
	
	public Boolean getWater(){
		return water;		
	}

	public void setShower(Boolean shower){
		this.shower = shower;
	}
	
	public Boolean getShower(){
		return shower;		
	}
	
	public void setShoes(String shoes){
		this.shoes = shoes;
	}
	
	public String getShoes(){
		return shoes;		
	}

	/**
	 * @return the graphicCollection
	 */
	public List<Graphic> getGraphicCollection() {
		return graphicCollection;
	}

	/**
	 * @param graphicCollection the graphicCollection to set
	 */
	public void setGraphicCollection(List<Graphic> graphicCollection) {
		this.graphicCollection = graphicCollection;
	}

}