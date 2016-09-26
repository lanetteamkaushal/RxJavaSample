package lanet.bhavin.rxjavasample.models;

import java.util.HashMap;
import java.util.Map;

public class User {

    private Integer account_id;
    private Integer age;
    private Integer creation_date;
    private String location;
    private String profile_image;
    private String display_name;

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Integer creation_date) {
        this.creation_date = creation_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    @Override
    public String toString() {
        return "ID :" + account_id + " Name :" + display_name;
    }
}
