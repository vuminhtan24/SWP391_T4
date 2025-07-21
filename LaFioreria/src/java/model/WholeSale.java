/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class WholeSale {

    private int id;
    private int user_id;
    private int bouquet_id;
    private int requested_quantity;

    private String note;

    private Integer quoted_price;      // Có thể null → dùng Integer
    private Integer total_price;       // Có thể null → dùng Integer

    private LocalDate quoted_at;       // Có thể null → dùng LocalDate
    private LocalDate responded_at;    // Có thể null → dùng LocalDate
    private LocalDate created_at;      // Luôn có → vẫn dùng LocalDate

    private String status;
    private Integer expense;
    
    private String request_group_id;

    public WholeSale() {
    }

    public WholeSale(int id, int user_id, int bouquet_id, int requested_quantity, String note, Integer quoted_price, Integer total_price, LocalDate quoted_at, LocalDate responded_at, LocalDate created_at, String status, Integer expense, String request_group_id) {
        this.id = id;
        this.user_id = user_id;
        this.bouquet_id = bouquet_id;
        this.requested_quantity = requested_quantity;
        this.note = note;
        this.quoted_price = quoted_price;
        this.total_price = total_price;
        this.quoted_at = quoted_at;
        this.responded_at = responded_at;
        this.created_at = created_at;
        this.status = status;
        this.expense = expense;
        this.request_group_id = request_group_id;
    }
    
    public WholeSale(int user_id, int bouquet_id, int requested_quantity, String note, Integer quoted_price, Integer total_price, LocalDate quoted_at, LocalDate responded_at, LocalDate created_at, String status, Integer expense, String request_group_id) {
        this.user_id = user_id;
        this.bouquet_id = bouquet_id;
        this.requested_quantity = requested_quantity;
        this.note = note;
        this.quoted_price = quoted_price;
        this.total_price = total_price;
        this.quoted_at = quoted_at;
        this.responded_at = responded_at;
        this.created_at = created_at;
        this.status = status;
        this.expense = expense;
        this.request_group_id = request_group_id;
    }
    
    public WholeSale(int user_id, LocalDate created_at, LocalDate quoted_at, LocalDate responded_at, String status, String request_group_id) {
        this.user_id = user_id;
        this.created_at = created_at;
        this.quoted_at = quoted_at;
        this.responded_at = responded_at;
        this.status = status;
        this.request_group_id = request_group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBouquet_id() {
        return bouquet_id;
    }

    public void setBouquet_id(int bouquet_id) {
        this.bouquet_id = bouquet_id;
    }

    public int getRequested_quantity() {
        return requested_quantity;
    }

    public void setRequested_quantity(int requested_quantity) {
        this.requested_quantity = requested_quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getQuoted_price() {
        return quoted_price;
    }

    public void setQuoted_price(Integer quoted_price) {
        this.quoted_price = quoted_price;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    public LocalDate getQuoted_at() {
        return quoted_at;
    }

    public void setQuoted_at(LocalDate quoted_at) {
        this.quoted_at = quoted_at;
    }

    public LocalDate getResponded_at() {
        return responded_at;
    }

    public void setResponded_at(LocalDate responded_at) {
        this.responded_at = responded_at;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    public String getRequest_group_id() {
        return request_group_id;
    }

    public void setRequest_group_id(String request_group_id) {
        this.request_group_id = request_group_id;
    }

    @Override
    public String toString() {
        return "WholeSale{" + "id=" + id + ", user_id=" + user_id + ", bouquet_id=" + bouquet_id + ", requested_quantity=" + requested_quantity + ", note=" + note + ", quoted_price=" + quoted_price + ", total_price=" + total_price + ", quoted_at=" + quoted_at + ", responded_at=" + responded_at + ", created_at=" + created_at + ", status=" + status + ", expense=" + expense + ", request_group_id=" + request_group_id + '}';
    }
    
}
