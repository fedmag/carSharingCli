package com.fedmag.controllers;

import com.fedmag.data.customer.Customer;
import com.fedmag.menu.MenuElement;
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
      String name = Controller.sc.nextLine();
      System.out.println(name);
      controller.customerDao.insert(new Customer(name));
      createCustomer.setChildren(request.getCurrentElements());
    });
    return createCustomer;
  }
}
