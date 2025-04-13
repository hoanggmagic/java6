// package com.example.java6.controllers.admin;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.java6.repositories.ProductRepository;
// import com.example.java6.repositories.Report;

// @Controller
// public class ReportController {
// @Autowired
// ProductRepository dao;

// @RequestMapping("/report/inventory-by-category")
// public String inventory(Model model) {
// List<Report> items = dao.getInventoryByCategory();
// model.addAttribute("items", items);
// return "report/inventory-by-category";
// }
// }
