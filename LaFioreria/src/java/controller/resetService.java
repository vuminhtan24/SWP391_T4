/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author VU MINH TAN
 */
public class resetService {
    private final int LIMIT_MINUS =10;
    
    public String generateToken(){
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expireDateTime(){
        return LocalDateTime.now().plusMinutes(10);
    }
    
    public boolean isExpireTime(LocalDateTime time){
        return LocalDateTime.now().isAfter(time);
    }
    
    public void sendEmail(String to, String link,String name){
        
    }
}
