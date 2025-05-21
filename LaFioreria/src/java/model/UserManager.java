/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LAPTOP
 */
public class UserManager {

    private int userid;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private String role;

    public UserManager() {
    }

    public UserManager(int userid, String username, String password, String fullname, String email, String phone, String address, String role) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserManager{" + "userid=" + userid + ", username=" + username + ", password=" + password + ", fullname=" + fullname + ", email=" + email + ", phone=" + phone + ", address=" + address + ", role=" + role + '}';
    }
    
    
    
}
