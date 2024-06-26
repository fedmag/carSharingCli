package carsharing.controllers;

import carsharing.data.car.Car;
import carsharing.data.customer.Customer;
import carsharing.menu.Controller;
import carsharing.menu.MenuElement;
import carsharing.menu.Request;
import java.util.List;
import java.util.Scanner;

public class CreateCustomerController {

  private final Controller controller;

  public CreateCustomerController(Controller controller) {
    this.controller = controller;
  }

  public MenuElement generateCreateCustomer(List<MenuElement> currentMenu) {
    MenuElement createCustomer = new MenuElement(3, "Create a customer", null);

    createCustomer.setOnSelect(request -> {
      System.out.println("Enter the customer name:");
      Scanner sc = new Scanner(System.in);
      String name = sc.nextLine();
      System.out.println(formatInsertedValue(name));
      // SAVE TO DB
      controller.customerDao.insert(new Customer(name));
      createCustomer.setChildren(request.getCurrentElements());
    });
    return createCustomer;
  }

  private String formatInsertedValue(String str) {
    return String.format("> %s", str);
  }
}
