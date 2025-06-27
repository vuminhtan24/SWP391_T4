package model;

import java.time.LocalDate;

public class EmployeeInfo {

    private int userId;
    private String employeeCode;
    private String contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String department;
    private String position;

    public EmployeeInfo() {
    }

    public EmployeeInfo(int userId, String employeeCode, String contractType,
            LocalDate startDate, LocalDate endDate, String department, String position) {
        this.userId = userId;
        this.employeeCode = employeeCode;
        this.contractType = contractType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.position = position;
    }

    // Getters & Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "EmployeeInfo{"
                + "userId=" + userId
                + ", employeeCode='" + employeeCode + '\''
                + ", contractType='" + contractType + '\''
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", department='" + department + '\''
                + ", position='" + position + '\''
                + '}';
    }
}
