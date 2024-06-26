package carsharing.data.customer;

import java.util.List;

public interface CustomerDao {
  void insert(Customer customer);
  Customer getById(int id);
  List<Customer> getAll();
  void updateRentedCar(Customer customer, Integer carId);
}
