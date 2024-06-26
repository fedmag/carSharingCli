package com.fedmag.data.customer;

public class Customer {

  Integer id;
  String name;
  Integer rentedCarId;

  public Customer() {}

  public Customer(String name) {
    this.name = name;
  }

  public Integer getRentedCarId() {
    return rentedCarId;
  }

  public void setRentedCarId(Integer rentedCarId) {
    this.rentedCarId = rentedCarId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
