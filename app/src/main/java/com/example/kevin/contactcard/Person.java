package com.example.kevin.contactcard;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kevin on 2015/11/11.
 */
public class Person implements Parcelable {
    private int id;
    private String title;
    private String first;
    private String last;
    private String gender;
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

    public Person(int id, String title, String first, String last, String gender, String street, String city, String state, String zip, String email, String phone, String imageString, String nationality) {
        this.id = id;
        this.title = title;
        this.first = first;
        this.last = last;
        this.gender = gender;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
        this.imageString = imageString;
        this.nationality = nationality;
    }

    public Person(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.first = in.readString();
        this.last = in.readString();
        this.gender = in.readString();
        this.street = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.zip = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.imageString = in.readString();
        this.nationality = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(first);
        dest.writeString(last);
        dest.writeString(gender);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zip);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(imageString);
        dest.writeString(nationality);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

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
