package com.fedmag.data.company;

import com.fedmag.data.DbClient;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

  private final static String CREATE_DB = "CREATE TABLE IF NOT EXISTS COMPANY "
      + "(id INTEGER primary key AUTO_INCREMENT, "
      + " NAME VARCHAR(255) UNIQUE NOT NULL)";
  private final static String SELECT_ALL = "SELECT * FROM company";
  private final static String SELECT_BY_ID = "SELECT * FROM company WHERE id = %d";
  private final static String INSERT_COMPANY = "INSERT INTO company (name) VALUES ('%s')";

  private final DbClient dbClient;

  public CompanyDaoImpl(DbClient dbClient) {
    this.dbClient = dbClient;
    init();
  }

  void init() {
//    dbClient.run("DROP TABLE company;");
    dbClient.run(CREATE_DB);
  }


  @Override
  public void insert(Company company) {
    dbClient.run(String.format(INSERT_COMPANY, company.name));
    System.out.println("The company was created!\n");
  }

  @Override
  public Company getById(int id) {
    List<Company> companies = new ArrayList<>();
    List<Object[]>  objects = dbClient.runForResult(String.format(SELECT_BY_ID, id));
    for (Object[] obj : objects) {
      Company c = new Company();
      c.id = (Integer) obj[0];
      c.name = (String) obj[1];
      companies.add(c);
    }
    return companies.get(0);
  }

  @Override
  public List<Company> getAll() {
    List<Company> companies = new ArrayList<>();
    List<Object[]> objects = dbClient.runForResult(SELECT_ALL);
      for (Object[] obj : objects){
        Company c = new Company();
        c.id = (Integer) obj[0];
        c.name = (String) obj[1];
        companies.add(c);
      }
    return companies;
  }

  @Override
  public void update(Company company) {

  }

  @Override
  public void delete(Company company) {
  }
}
