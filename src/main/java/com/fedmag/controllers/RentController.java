package carsharing.controllers;

import carsharing.data.car.Car;
import carsharing.data.company.Company;
import carsharing.data.customer.Customer;
import carsharing.menu.Controller;
import carsharing.menu.MenuElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentController {

  private final Controller controller;

  public RentController(Controller controller) {
    this.controller = controller;
  }

  public List<MenuElement> generateRentChildren(Customer customer) {
    // rent car
    MenuElement rentACar = getRentCarMenuElement(customer);
    // 2. Return rented car
    MenuElement returnRentedCar = getReturnRentedCar(customer);
    // 3. My rented car
    MenuElement myRentedCar = new MenuElement(3, "My rented car", null);
    myRentedCar.setOnSelect(request -> {
      getRentedCarsChildren(customer);
      myRentedCar.setChildren(request.getCurrentElements());
    });

    return new ArrayList<>(List.of(rentACar, returnRentedCar, myRentedCar));
  }

  private MenuElement getRentCarMenuElement(Customer customer) {
    MenuElement rentACar = new MenuElement(1, "Rent a car", null);
    rentACar.setOnSelect(request -> {
      // if this customer has already a rent we should not allow him to get a new one
      if (customer.getRentedCarId() != null) {
        System.out.println("You've already rented a car!\n");
        rentACar.setChildren(request.getCurrentElements());
        return;
      }
      List<Company> allCompanies = controller.companyDao.getAll();
      if (allCompanies.isEmpty()) {
        System.out.println("The company list is empty!\n");
        rentACar.setChildren(request.getCurrentElements());
        return;
      }
      System.out.println("Choose the company:");
      List<MenuElement> children = generateCompanyElements(allCompanies, customer);
      children.add(new MenuElement(0, "Back", request.getCurrentElements()));
      rentACar.setChildren(children);
      System.out.println();
    });
    return rentACar;
  }

  private void getRentedCarsChildren(Customer customer) {
    Integer rentedCarId = controller.customerDao.getById(customer.getId()).getRentedCarId();
    if (rentedCarId != null) {
      Car car = controller.carDao.getById(rentedCarId);
      Company company = controller.companyDao.getById(car.getCompanyId());
      System.out.println("You rented '" + car.getName() + "'");
      System.out.println("company name is '" + company.getName() + "'\n");
      return;
    }
    System.out.println("You didn't rent a car!\n");
  }

  private List<MenuElement> generateCompanyElements(List<Company> allCompanies, Customer customer) {
    return allCompanies.stream().map(company -> {
      // children of this must be the car list with the custom command
      MenuElement companyElement = new MenuElement(company.getId(), company.getName(), null);
      companyElement.setOnSelect(request -> {
        List<Car> cars = controller.carDao.getAllByCompanyId(company.getId());
        List<MenuElement> companyChildren = generateCarElements(cars, customer);
        companyChildren.add(new MenuElement(0, "Back", request.getCurrentElements()));
        companyElement.setChildren(companyChildren);

        if (companyElement.getChildren().isEmpty()) {
          System.out.println("No cars available at the moment.");
          companyElement.setChildren(request.getCurrentElements());
        }
      });
      return companyElement;
    }).collect(Collectors.toList());
  }

  private List<MenuElement> generateCarElements(List<Car> cars, Customer customer) {
    return cars.stream()
        .map(car -> {
          MenuElement carElement = new MenuElement(car.getId(), car.getName(), null);
          carElement.setOnSelect(request -> {
            controller.customerDao.updateRentedCar(customer, car.getId());
            customer.setRentedCarId(car.getId());
            System.out.println("You rented '" + carElement.getMessage() + "'\n");
            MenuElement backToSelection = new MenuElement(0, "Back", request.getCurrentElements());
            List<MenuElement> childrenElements = generateRentChildren(customer);
            childrenElements.add(backToSelection);
            carElement.setChildren(childrenElements);
          });
          return carElement;
        })
        .collect(Collectors.toList());
  }
  private MenuElement getReturnRentedCar(Customer customer) {
    MenuElement returnRentedCar = new MenuElement(2, "Return a rented car", null);
    returnRentedCar.setOnSelect(request -> {
      // get car id for this customer, if null: you did not get any car
      if (customer.getRentedCarId() == null) {
        System.out.println("You didn't rent a car!");
      } else {
        // set null in the rented_car_id
        System.out.println("You've returned a rented car!");
        controller.customerDao.updateRentedCar(customer, null);
      }
      returnRentedCar.setChildren(request.getCurrentElements());
    });
    return returnRentedCar;
  }

}
