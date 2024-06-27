package com.fedmag.controllers;

import com.fedmag.data.company.Company;
import com.fedmag.menu.MenuElement;
import com.fedmag.menu.Request;
import java.util.ArrayList;
import java.util.List;

public class ManagerMenuController {

  private final Controller controller;

  public ManagerMenuController(Controller controller) {
    this.controller = controller;
  }

  MenuElement generateLoginAsManager(List<MenuElement> backMenu) {
    final MenuElement logInAsAManager = new MenuElement(1, "Log in as a manager", null);
    MenuElement companyListBtn = new MenuElement(1, "Company list", null);
    MenuElement createCompanyBtn = new MenuElement(2, "Create a company", null);
    MenuElement backBtn = new MenuElement(0, "Back", backMenu);

    // COMPANIES
    companyListBtn.setOnSelect(
        request -> {
          // retrieve all companies and print them
          List<Company> allCompanies = controller.companyDao.getAll();
          if (allCompanies.isEmpty()) {
            System.out.println("The company list is empty!\n");
            companyListBtn.setChildren(List.of(companyListBtn, createCompanyBtn, backBtn));
            return;
          }
          System.out.println("Choose the company:");
          List<MenuElement> children = generateCompanyChildren(allCompanies, request);
          children.add(new MenuElement(0, "Back", request.getCurrentElements()));
          companyListBtn.setChildren(children);
          System.out.println();
        });
    createCompanyBtn.setOnSelect(
        request -> {
          System.out.println("Enter the company name:");
          String name = Controller.sc.nextLine();
          System.out.println(name);
          controller.companyDao.insert(new Company(name));
          createCompanyBtn.setChildren(request.getCurrentElements());
        });
    logInAsAManager.setChildren(List.of(companyListBtn, createCompanyBtn, backBtn));
    return logInAsAManager;
  }

  private List<MenuElement> generateCompanyChildren(List<Company> allCompanies, Request request) {
    List<MenuElement> children = new ArrayList<>();
    for (Company company : allCompanies) {
      MenuElement child = new MenuElement(company.getName(), company.getId());
      MenuElement back = new MenuElement(0, "Back", request.getCurrentElements());
      MenuElement carList = controller.carController.generateCarListElement(company);
      MenuElement createCar = controller.carController.generateCreateCarElement(company);
      child.setChildren(List.of(carList, createCar, back));
      children.add(child);
    }
    return children;
  }
}
