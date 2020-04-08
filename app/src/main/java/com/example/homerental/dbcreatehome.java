package com.example.homerental;

public class dbcreatehome {
    private String community_name, address_flat, street, city, state, pincode, no_of_bedrooms, square_ft, rent, advance, description, image1, image2, image3, image4,uniqueid;


    public dbcreatehome() {
        this.image1 = null;
        this.image2 = null;
        this.image3 = null;
        this.image4 = null;
    }

    public dbcreatehome(String community_name, String address_flat, String street, String city, String state, String pincode, String no_of_bedrooms, String square_ft, String rent, String advance, String description, String image1, String image2, String image3, String image4) {
        this.community_name = community_name;
        this.address_flat = address_flat;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.no_of_bedrooms = no_of_bedrooms;
        this.square_ft = square_ft;
        this.rent = rent;
        this.advance = advance;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public dbcreatehome(String community_name, String address_flat, String street, String city, String state, String pincode, String no_of_bedrooms, String square_ft, String rent, String advance, String description, String image1, String image2, String image3, String image4, String uniqueid) {
        this.community_name = community_name;
        this.address_flat = address_flat;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.no_of_bedrooms = no_of_bedrooms;
        this.square_ft = square_ft;
        this.rent = rent;
        this.advance = advance;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.uniqueid = uniqueid;
    }



    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getAddress_flat() {
        return address_flat;
    }

    public void setAddress_flat(String address_flat) {
        this.address_flat = address_flat;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNo_of_bedrooms() {
        return no_of_bedrooms;
    }

    public void setNo_of_bedrooms(String no_of_bedrooms) {
        this.no_of_bedrooms = no_of_bedrooms;
    }

    public String getSquare_ft() {
        return square_ft;
    }

    public void setSquare_ft(String square_ft) {
        this.square_ft = square_ft;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
}