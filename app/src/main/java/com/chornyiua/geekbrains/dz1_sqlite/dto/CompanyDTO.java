package com.chornyiua.geekbrains.dz1_sqlite.dto;


public class CompanyDTO {
    private int id;
    private String name;

    public CompanyDTO(String name) {
        this.name = name;
    }

    public CompanyDTO() {
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
}
