package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.Orlik;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by emagdnim on 2015-10-12.
 */
public class OrlikForm {
    Integer id;
    String address;
    String city;
    Boolean lights;
    Boolean water;
    Boolean shower;
    String shoes;
    String animatorEmail;

    public Map<String, String> getChooser() {
        return chooser;
    }

    public void setChooser(Map<String, String> chooser) {
        this.chooser = chooser;
    }

    Map<String, String> chooser;

    public OrlikForm(){
        super();
        makeChooser();
        id = null;
        address = "";
        city = "";
        lights = false;
        water = false;
        shower = false;
        shoes = "turf";
        animatorEmail = "";
    }

    public OrlikForm(Orlik orlik) {
        super();
        makeChooser();
        id = orlik.getId();
        address = orlik.getAddress();
        city = orlik.getCity();
        lights = orlik.getLights();
        water = orlik.getWater();
        shower = orlik.getShower();
        shoes = orlik.getShoes();
        animatorEmail = orlik.getAnimator() != null ? orlik.getAnimator().getEmail() : "";
    }

    private void makeChooser(){
        this.chooser = new LinkedHashMap<>();
        chooser.put("false", "NIE");
        chooser.put("true", "TAK");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnimatorEmail() {
        return animatorEmail;
    }

    public void setAnimatorEmail(String animatorEmail) {
        this.animatorEmail = animatorEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getLights() {
        return lights;
    }

    public void setLights(Boolean lights) {
        this.lights = lights;
    }

    public Boolean getWater() {
        return water;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public Boolean getShower() {
        return shower;
    }

    public void setShower(Boolean shower) {
        this.shower = shower;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }
}
