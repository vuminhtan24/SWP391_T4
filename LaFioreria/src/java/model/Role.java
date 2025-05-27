/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LAPTOP
 */
public class Role {
    
    private int role_id;
    private String role_Name;

    public Role() {
    }

    public Role(int role_id, String role_Name) {
        this.role_id = role_id;
        this.role_Name = role_Name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_Name() {
        return role_Name;
    }

    public void setRole_Name(String role_Name) {
        this.role_Name = role_Name;
    }

    @Override
    public String toString() {
        return "Role{" + "role_id=" + role_id + ", role_Name=" + role_Name + '}';
    }
    
    
}
