package com.fedmag.data.company;

public class Company {

  Integer id;
  String name;

  public Company() {}

  public Company(String name) {
    this.name = name;
  }

  public Company(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return id + ". " + name;
  }
}
