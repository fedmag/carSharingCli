package com.fedmag.data.car;

import java.util.List;

public interface CarDao {
  void insert(Car car);
  Car getById(int id);
  List<Car> getAllByCompanyId(int companyId);
}
