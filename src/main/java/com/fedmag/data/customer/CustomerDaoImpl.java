package com.fedmag.data.customer;

import com.fedmag.data.DbClient;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

  private static final String CREATE_DB =
      "CREATE TABLE IF NOT EXISTS CUSTOMER "
          + "(id INTEGER primary key AUTO_INCREMENT, "
          + " NAME VARCHAR(255) UNIQUE NOT NULL,"
          + " RENTED_CAR_ID INTEGER DEFAULT NULL,"
          + " FOREIGN KEY (rented_car_id) REFERENCES car(id))";
  private static final String SELECT_BY_ID = "SELECT * FROM customer WHERE id = %d";
  private static final String SELECT_ALL = "SELECT * FROM customer";
  private static final String INSERT_CUSTOMER = "INSERT INTO customer (name) VALUES ('%s')";
  private static final String UPDATE_CUSTOMER =
      "UPDATE customer SET rented_car_id = %s WHERE id = %s";

  private final DbClient dbClient;

  public CustomerDaoImpl(DbClient dbClient) {
    this.dbClient = dbClient;
    init();
  }

  void init() {
    //    dbClient.run("DROP TABLE CAR; DROP TABLE COMPANY;");
    dbClient.run(CREATE_DB);
  }

  @Override
  public void insert(Customer customer) {
    dbClient.run(String.format(INSERT_CUSTOMER, customer.name));
    System.out.println("The customer was created!\n");
  }

  @Override
  public Customer getById(int id) {
    List<Object[]> objects = dbClient.runForResult(String.format(SELECT_BY_ID, id));
    Object[] obj = objects.get(0);
    Customer c = new Customer((String) obj[1]);
    if (obj[2] == null) {
      return c;
    }
    Integer rentedCar = (Integer) obj[2];
    c.setRentedCarId(rentedCar);
    return c;
  }

  @Override
  public List<Customer> getAll() {
    List<Customer> customers = new ArrayList<>();
    List<Object[]> objects = dbClient.runForResult(SELECT_ALL);
    for (Object[] obj : objects) {
      Customer c = new Customer();
      c.id = (Integer) obj[0];
      c.name = (String) obj[1];
      c.rentedCarId = (Integer) obj[2];
      customers.add(c);
    }
    return customers;
  }

  @Override
  public void updateRentedCar(Customer customer, Integer carId) {
    if (carId == null) {
      dbClient.run(String.format(UPDATE_CUSTOMER, "NULL", customer.getId()));
      return;
    }
    dbClient.run(String.format(UPDATE_CUSTOMER, carId, customer.getId()));
  }
}
