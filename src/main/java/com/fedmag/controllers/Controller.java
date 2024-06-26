package com.fedmag.controllers;

import com.fedmag.data.car.CarDao;
import com.fedmag.data.car.CarDaoImpl;
import com.fedmag.data.company.CompanyDao;
import com.fedmag.data.company.CompanyDaoImpl;
import com.fedmag.data.customer.CustomerDao;
import com.fedmag.data.customer.CustomerDaoImpl;
import com.fedmag.menu.MenuElement;
import com.fedmag.menu.Request;
import java.util.List;
import java.util.Scanner;

public class Controller {

  final Scanner sc = new Scanner(System.in);
  public final CompanyDao companyDao = new CompanyDaoImpl();
  public final CarDao carDao = new CarDaoImpl();
  public final CustomerDao customerDao = new CustomerDaoImpl();

  public final MainMenuController mainMenuController;
  public final CarController carController;
  public final RentController rentController;

  public Controller() throws ClassNotFoundException {
    mainMenuController = new MainMenuController(this);
    carController = new CarController(this);
    rentController = new RentController(this);
  }

  public void showMenuAndListen(List<MenuElement> elements) throws IllegalAccessException {
    elements.forEach(System.out::println);
    int selected = sc.nextInt();
    System.out.println(formatInsertedValue(selected + "\n"));
    MenuElement selectedElement = elements
        .stream()
        .filter(e -> e.getDigit() == selected)
        .findFirst()
        .orElseThrow(() -> new IllegalAccessException("Invalid command"));
    if (selectedElement.getOnSelect() != null) {
      selectedElement.getOnSelect().execute(new Request(elements, selectedElement));
    }
    if (selectedElement.getChildren() != null && !selectedElement.getChildren().isEmpty()) {
      showMenuAndListen(selectedElement.getChildren());
    }
  }

  public void init() throws IllegalAccessException {
    showMenuAndListen(mainMenuController.initMainMenu());
  }

  private String formatInsertedValue(String str) {
    return String.format("> %s", str);
  }
}
