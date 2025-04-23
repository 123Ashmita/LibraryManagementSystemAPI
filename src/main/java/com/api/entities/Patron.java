package com.api.entities;


import jakarta.persistence.*;

@Entity
public class Patron {

    @Id
    private int pid;
    private String name;

    public Patron() {}

    public Patron(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}