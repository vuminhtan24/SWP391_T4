/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class FeedbackImg {
    int feedbackId;
    String imageUrl;

    public FeedbackImg() {
    }

    public FeedbackImg(int feedbackId, String imageUrl) {
        this.feedbackId = feedbackId;
        this.imageUrl = imageUrl;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "FeedbackImg{" + "feedbackId=" + feedbackId + ", imageUrl=" + imageUrl + '}';
    }
    
    
}
