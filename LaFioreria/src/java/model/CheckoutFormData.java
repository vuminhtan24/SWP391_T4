/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

// Lớp này dùng để đóng gói dữ liệu từ form checkout
public class CheckoutFormData implements Serializable {

    private String email;
    private String fullName;
    private String addressLine;
    private String provinceCode; // Lưu mã để chọn lại dropdown
    private String districtCode;
    private String wardCode;
    private String phoneNumber;
    private String notes;
    private String paymentMethod;
    private boolean shipToBilling;

    public CheckoutFormData() {
    }

    public CheckoutFormData(String email, String fullName, String addressLine, String provinceCode, String districtCode, String wardCode, String phoneNumber, String notes, String paymentMethod, boolean shipToBilling) {
        this.email = email;
        this.fullName = fullName;
        this.addressLine = addressLine;
        this.provinceCode = provinceCode;
        this.districtCode = districtCode;
        this.wardCode = wardCode;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.paymentMethod = paymentMethod;
        this.shipToBilling = shipToBilling;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isShipToBilling() {
        return shipToBilling;
    }

    public void setShipToBilling(boolean shipToBilling) {
        this.shipToBilling = shipToBilling;
    }
}
