package edu.nyu.foodvoid.pojo;

public class RestaurantModel {

    private String rName,rAddr,rPhoneNumber;

    public String getrAddr() {
        return rAddr;
    }

    public String getrName() {
        return rName;
    }

    public String getrPhoneNumber() {
        return rPhoneNumber;
    }

    public void setrAddr(String rAddr) {
        this.rAddr = rAddr;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public void setrPhoneNumber(String rPhoneNumber) {
        this.rPhoneNumber = rPhoneNumber;
    }
}


