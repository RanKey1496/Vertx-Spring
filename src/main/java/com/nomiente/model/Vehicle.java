package com.nomiente.model;

public class Vehicle {

	private String name;
	private String color;
	private Integer model;
	
	public Vehicle(String name, String color, Integer model) {
		super();
		this.name = name;
		this.color = color;
		this.model = model;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	
}
