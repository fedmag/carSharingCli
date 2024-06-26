package carsharing.data.car;

public class Car {
  Integer id;
  String name;
  Integer companyId;

  public Car() {}

  public Car(String name, Integer companyId) {
    this.name = name;
    this.companyId = companyId;
  }

  public Integer getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Integer companyId) {
    this.companyId = companyId;
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

  @Override
  public String toString() {
    return this.id + ". " + this.name;
  }
}
