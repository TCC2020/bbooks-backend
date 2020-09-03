package br.edu.ifsp.spo.bulls.usersApi.dto;


public class ProfileRegisterTO {

    private int id;
    private String country;
    private String city;
    private String state;
    private String birthDate;
    public ProfileRegisterTO(int id,
                             String country,
							 String city,
							 String state,
                             String birthDate) {
        super();
        this.id = id;
        this.country = country;
        this.city = city;
        this.state = state;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
