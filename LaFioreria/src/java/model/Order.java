/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Legion
 */
public class Order {

    private int orderId;
    private String orderDate;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String totalSell;
    private String totalImport;
    private int statusId;
    private String statusName;
    private Integer shipperId;
    private String shipperName;
    private String deliveryProofImage;
    private String rejectImage;
    private String rejectReason;
    private String paymentMethod;
    private java.sql.Timestamp createdAt;

    // Constructors
    public Order() {
    }

    public Order(int orderId, String orderDate, Integer customerId, String customerName,
            String customerPhone, String customerAddress, String totalSell, String totalImport, int statusId, String statusName,
            Integer shipperId, String shipperName, String paymentMethod) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.totalSell = totalSell;
        this.totalImport = totalImport;
        this.statusId = statusId;
        this.statusName = statusName;
        this.shipperId = shipperId;
        this.shipperName = shipperName;
        this.paymentMethod = paymentMethod;
    }

    public Order(int orderId, String orderDate, Integer customerId, String customerName,
            String totalSell, String totalImport, int statusId, String statusName,
            Integer shipperId, String shipperName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalSell = totalSell;
        this.totalImport = totalImport;
        this.statusId = statusId;
        this.statusName = statusName;
        this.shipperId = shipperId;
        this.shipperName = shipperName;
    }

    public Order(int orderId, String orderDate, Integer customerId, String totalSell, int statusId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalSell = totalSell;
        this.statusId = statusId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getDeliveryProofImage() {
        return deliveryProofImage;
    }

    public void setDeliveryProofImage(String deliveryProofImage) {
        this.deliveryProofImage = deliveryProofImage;
    }

    public String getRejectImage() {
        return rejectImage;
    }

    public void setRejectImage(String rejectImage) {
        this.rejectImage = rejectImage;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getShipperId() { // Use Integer to allow null
        return shipperId;
    }

    public void setShipperId(Integer shipperId) {
        this.shipperId = shipperId;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(String totalSell) {
        this.totalSell = totalSell;
    }

    public String getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(String totalImport) {
        this.totalImport = totalImport;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", orderDate=" + orderDate + ", customerId=" + customerId + ", customerName=" + customerName + ", customerPhone=" + customerPhone + ", customerAddress=" + customerAddress + ", totalSell=" + totalSell + ", totalImport=" + totalImport + ", statusId=" + statusId + ", statusName=" + statusName + ", shipperId=" + shipperId + ", shipperName=" + shipperName + ", deliveryProofImage=" + deliveryProofImage + ", rejectImage=" + rejectImage + ", rejectReason=" + rejectReason + '}';
    }

}
