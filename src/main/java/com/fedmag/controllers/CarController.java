package carsharing.controllers;

import carsharing.data.car.Car;
import carsharing.data.company.Company;
import carsharing.menu.Controller;
import carsharing.menu.MenuElement;
import carsharing.menu.Request;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarController {

  private final Controller controller;

  public CarController(Controller controller) {
    this.controller = controller;
  }

  public MenuElement generateCarListElement(Company company) {
    MenuElement carList = new MenuElement(1, "Car list", null);

    carList.setOnSelect(request -> {
      carList.setChildren(request.getCurrentElements());

      List<Car> allCars = controller.carDao.getAllByCompanyId(company.getId());
      if (allCars.isEmpty()) {
        System.out.println("The car list is empty!\n");
        return;
      }
      System.out.println(company.getName() + " cars:");
      int i = 1;
      for (Car car : allCars) {
        System.out.println(i + ". " + car.getName());
        i++;
      }
      System.out.println();
    });
    return carList;
  }

  public MenuElement generateCreateCarElement(Company company) {
    MenuElement createCar = new MenuElement(2, "Create a car", null);

    createCar.setOnSelect((Request request) -> {
      System.out.println("Enter the car name:");
      Scanner sc = new Scanner(System.in);
      String name = sc.nextLine();
      System.out.println(formatInsertedValue(name));
      // SAVE TO DB
      controller.carDao.insert(new Car(name, company.getId()));
      createCar.setChildren(request.getCurrentElements());
    });
    return createCar;
  }

  public List<MenuElement> getAvailableCars(Company company) {
    List<Car> cars = controller.carDao.getAllByCompanyId(company.getId());
    return cars.stream().map(car -> new MenuElement(car.getId(), car.getName(), null)).collect(Collectors.toList());

  }

  private String formatInsertedValue(String str) {
    return String.format("> %s", str);
  }
}
