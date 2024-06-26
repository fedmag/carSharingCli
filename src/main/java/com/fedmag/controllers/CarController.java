package com.fedmag.controllers;

import com.fedmag.data.car.Car;
import com.fedmag.data.company.Company;
import com.fedmag.menu.MenuElement;
import com.fedmag.menu.Request;
import java.util.List;

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
      String name = Controller.sc.nextLine();
      System.out.println(name);
      controller.carDao.insert(new Car(name, company.getId()));
      createCar.setChildren(request.getCurrentElements());
    });
    return createCar;
  }
}
