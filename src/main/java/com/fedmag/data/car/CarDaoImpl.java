package com.fedmag.data.car;

import com.fedmag.data.DbClient;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {

  private final static String CREATE_DB = "CREATE TABLE IF NOT EXISTS CAR "
      + "(id INTEGER primary key AUTO_INCREMENT, "
      + " NAME VARCHAR(255) UNIQUE NOT NULL,"
      + " COMPANY_ID INTEGER NOT NULL,"
      + " FOREIGN KEY (company_id) REFERENCES company(id))";
  private final static String SELECT_BY_COMPANY_ID = "SELECT * FROM car WHERE company_id = ? ORDER BY id ASC";
  private final static String SELECT_BY_ID = "SELECT * FROM car WHERE id = %d";
  private final static String INSERT_CAR = "INSERT INTO car (name, company_id) VALUES ('%s', %d)";

  private final DbClient dbClient;

  public CarDaoImpl(DbClient dbClient) throws ClassNotFoundException {
    this.dbClient = dbClient;
    init();
  }

  void init() {
//    dbClient.run("DROP TABLE CAR; DROP TABLE COMPANY;");
    dbClient.run(CREATE_DB);
  }


  @Override
  public void insert(Car car) {
    dbClient.run(String.format(INSERT_CAR, car.name, car.companyId));
    System.out.println("The car was created!\n");
  }

  @Override
  public Car getById(int id) {
    List<Car> cars = new ArrayList<>();
    List<Object[]> objects = dbClient.runForResult(String.format(SELECT_BY_ID, id));
    for (Object[] obj : objects) {
      Car c = new Car();
      c.id = (Integer) obj[0];
      c.name = (String) obj[1];
      c.companyId = (Integer) obj[2];
      cars.add(c);
    }
    return cars.get(0);
  }

  @Override
  public List<Car> getAllByCompanyId(int companyId) {
    List<Car> cars = new ArrayList<>();
    List<Object[]> objects = null;
    try {
      objects = dbClient.runForResultWithParams(SELECT_BY_COMPANY_ID, List.of(companyId));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    for (Object[] obj : objects) {
      Car c = new Car();
      c.id = (Integer) obj[0];
      c.name = (String) obj[1];
      cars.add(c);
    }
    return cars;
  }
}

