/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Feedback {
    int feedbackId;
    int customerId;
    int bouquetId;
    int rating;
    String comment;
    LocalDateTime created_at;
    String status;
    String bouquetName;

    public Feedback() {
    }

    public Feedback(int feedbackId, int customerId, int bouquetId, int rating, String comment, LocalDateTime created_at, String status, String bouquetName) {
        this.feedbackId = feedbackId;
        this.customerId = customerId;
        this.bouquetId = bouquetId;
        this.rating = rating;
        this.comment = comment;
        this.created_at = created_at;
        this.status = status;
        this.bouquetName = bouquetName;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public void setBouquetId(int bouquetId) {
        this.bouquetId = bouquetId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    // Thêm getter chuyển đổi sang Date
    public java.util.Date getCreatedAtAsDate() {
        return created_at != null ? java.util.Date.from(created_at.atZone(java.time.ZoneId.systemDefault()).toInstant()) : null;
    }

    public String getBouquetName() {
        return bouquetName;
    }

    public void setBouquetName(String bouquetName) {
        this.bouquetName = bouquetName;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedbackId=" + feedbackId + ", customerId=" + customerId + ", bouquetId=" + bouquetId + ", rating=" + rating + ", comment=" + comment + ", created_at=" + created_at + ", status=" + status + ", bouquetName=" + bouquetName + '}';
    }
        
    
}
