package com.chornyiua.geekbrains.dz1_sqlite.dto;


public class WorkerDTO {
    private int id;
    private String name;
    private int companyID;
    private int salary;

    public WorkerDTO(int id, String name, int companyID, int salary) {
        this.id = id;
        this.name = name;
        this.companyID = companyID;
        this.salary = salary;
    }

    public WorkerDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public WorkerDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
