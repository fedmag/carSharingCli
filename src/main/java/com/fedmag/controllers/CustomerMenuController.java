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

//  private class CustomerItemProcessor implements ItemProcessor {
//
//    @Override
//    public MenuElement processItem(Object item) {
//      Customer customer = (Customer) item;
//      return new MenuElement(customer.getName(), customer.getId());
//    }
//
//    @Override
//    public List<MenuElement> getChildren(Object item, MenuElement back) {
//      Customer customer = (Customer) item;
//      // in this case for each customer i need to generate:
//      /*
//      Rent a car:
//      1. children must be company list
//      2. each element of the company list when selected must show all that cars
//      3. on selection the car must be rented
//      */
//
//      MenuElement rentACar = new MenuElement(1, "Rent a car", null);
//      rentACar.setCommand((request -> {
//        List<Company> allCompanies = controller.companyDao.getAll();
//        if (allCompanies.isEmpty()) {
//          System.out.println("The company list is empty!\n");
//          rentACar.setChildren(request.getCurrentElements());
//          return;
//        }
//        System.out.println("Choose the company:");
//        List<MenuElement> children = generateChildren(allCompanies, request,
//            new CompanyItemProcessor());
//        children.add(new MenuElement(0, "Back", request.getCurrentElements()));
//        rentACar.setChildren(children);
//        System.out.println();
//      }));
//
//      // 2. Return rented car
//      MenuElement returnRentedCar = new MenuElement(2, "Return rented car", null);
//      // 3. My rented car
//      MenuElement myRentedCar = new MenuElement(3, "My rented car", null);
//      // 0. back
//      return List.of(rentACar, returnRentedCar, myRentedCar, back);
//    }
//  }
//
//
//  private class CompanyItemProcessor implements ItemProcessor {
//
//    @Override
//    public MenuElement processItem(Object item) {
//      Company company = (Company) item;
//      return new MenuElement(company.getName(), company.getId());
//    }
//
//    @Override
//    public List<MenuElement> getChildren(Object item, MenuElement back) {
//      Company company = (Company) item;
//      List<MenuElement> carList = controller.carController.getAvailableCars(company);
//      if (carList.isEmpty()) {
//        return back.getChildren();
//      }
//      carList = carList.stream().peek(
//          menuElement -> menuElement.setCommand(
//              request -> {
//                System.out.println("You rented '" + request.getInvoker().getMessage() + "'");
//              }
//          )
//      ).toList();
//      return new ArrayList<>(carList);
//    }
//  }

}
