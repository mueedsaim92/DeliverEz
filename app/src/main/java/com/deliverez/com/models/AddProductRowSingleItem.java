package com.deliverez.com.models;

public class AddProductRowSingleItem {

    public String companyName;
    public String address;

   public AddProductRowSingleItem(String companyName, String address){
        this.companyName = companyName;
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "companyNameTV='" + companyName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
