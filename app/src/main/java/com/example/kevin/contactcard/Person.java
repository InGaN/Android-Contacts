package com.example.kevin.contactcard;

/**
 * Created by kevin on 2015/11/11.
 */
public class Person {
    private int id;
    private String gender;
    private String title;
    private String first;
    private String last;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;
    private String imageString;
    private String nationality;

    public Person(int id) {
        this.id = id;
    }

    public Person(int id, String gender, String title, String first, String last, String street, String city, String state, String zip, String email, String phone, String imageString, String nationality) {
        this.id = id;
        this.gender = gender;
        this.title = title;
        this.first = first;
        this.last = last;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
        this.imageString = imageString;
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return getTitle() + " " + getFirst() + " " + getLast() + " (" + getGender() + "), " + getStreet() + " " + getState() + ", " + getZip() + ", " + getNationality() + " " +getEmail() + " " + getPhone() + ", " + getImageString();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
