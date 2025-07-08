/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

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
    LocalDate created_at;
    String status;

    public Feedback() {
    }

    public Feedback(int feedbackId, int customerId, int bouquetId, int rating, String comment, LocalDate created_at, String status) {
        this.feedbackId = feedbackId;
        this.customerId = customerId;
        this.bouquetId = bouquetId;
        this.rating = rating;
        this.comment = comment;
        this.created_at = created_at;
        this.status = status;
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

    @Override
    public String toString() {
        return "Feedback{" + "feedbackId=" + feedbackId + ", customerId=" + customerId + ", bouquetId=" + bouquetId + ", rating=" + rating + ", comment=" + comment + ", created_at=" + created_at + ", status=" + status + '}';
    }
    
    
}
