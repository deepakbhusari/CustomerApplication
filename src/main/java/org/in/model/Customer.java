package org.in.model;


public class Customer {
    private  long id;
    private  String name;
    private int age;
    private  String country;

    public Customer(){
    	
    }
    
    public Customer(long id,String name) {
    	this.id=id;
    	this.name = name;
    }

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
