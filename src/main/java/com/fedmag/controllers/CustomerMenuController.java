package com.fedmag.controllers;

import com.fedmag.data.customer.Customer;
import com.fedmag.menu.MenuElement;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenuController {

  private final Controller controller;

  public CustomerMenuController(Controller controller) {
    this.controller = controller;
  }

  public MenuElement generateLoginAsCustomer(List<MenuElement> currentMenu) {
    final MenuElement logInAsCustomer = new MenuElement(2, "Log in as a customer", null);
    logInAsCustomer.setOnSelect(request -> {
      // retrieve all companies and print them
      List<Customer> allCustomers = controller.customerDao.getAll();
      if (allCustomers.isEmpty()) {
        System.out.println("The customer list is empty!\n");
        logInAsCustomer.setChildren(request.getCurrentElements());
        return;
      }
      System.out.println("Choose the customer:");
      List<MenuElement> children = generateChildren(allCustomers, currentMenu);
      children.add(new MenuElement(0, "Back", request.getCurrentElements()));
      logInAsCustomer.setChildren(children);
      System.out.println();
    });
    return logInAsCustomer;
  }

  private List<MenuElement> generateChildren(List<Customer> allCustomers,
      List<MenuElement> currentMenu) {
    return allCustomers.stream()
        .map(customer -> {
          MenuElement customerElement = new MenuElement(customer.getId(), customer.getName(), null);
          List<MenuElement> children = controller.rentController.generateRentChildren(customer);
          children.add(new MenuElement(0, "Back", currentMenu));
          customerElement.setChildren(children);
          return customerElement;})
        .collect(Collectors.toList());
  }
}
