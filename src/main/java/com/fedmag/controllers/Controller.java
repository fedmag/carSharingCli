package com.fedmag.controllers;

import com.fedmag.data.DbClient;
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

  public static final Scanner sc = new Scanner(System.in);
  public final MainMenuController mainMenuController;
  public final CarController carController;
  public final RentController rentController;
  private final DbClient dbClient = new DbClient();
  public final CompanyDao companyDao = new CompanyDaoImpl(dbClient);
  public final CarDao carDao = new CarDaoImpl(dbClient);
  public final CustomerDao customerDao = new CustomerDaoImpl(dbClient);

  public Controller() throws ClassNotFoundException {
    mainMenuController = new MainMenuController(this);
    carController = new CarController(this);
    rentController = new RentController(this);
  }

  public void showMenuAndListen(List<MenuElement> elements) throws IllegalAccessException {
    elements.forEach(System.out::println);
    int selected = sc.nextInt();
    System.out.println(selected + "\n");
    MenuElement selectedElement =
        elements.stream()
            .filter(e -> e.getDigit() == selected)
            .findFirst()
            .orElseThrow(() -> new IllegalAccessException("Invalid command"));
    sc
        .nextLine(); // in order to share the scanner instance there is the need of clearing the
                     // buffer
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
}
