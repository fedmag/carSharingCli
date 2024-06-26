package com.fedmag.data.company;

import java.util.List;

public interface CompanyDao {
  void insert(Company company);
  Company getById(int id);
  List<Company> getAll();
  void update(Company company);
  void delete(Company company);
}
