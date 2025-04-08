package com.example.java6.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.java6.entities.Product;
import com.example.java6.repositories.ProductRepository;
import com.example.java6.repositories.SessionService;

@Controller
public class ProductControllerAdmin1 {
    @Autowired
    ProductRepository dao;

    @Autowired
    SessionService session;

    @RequestMapping("/product/sort")
    public String sort(Model model, @RequestParam("field") Optional<String> field) {
        String sortField = field.orElse("price"); // Mặc định sắp xếp theo price
        Sort sort = Sort.by(Direction.DESC, sortField);

        model.addAttribute("field", sortField.toUpperCase());
        List<Product> items = dao.findAll(sort);
        model.addAttribute("items", items);

        return "product/sort";
    }

    @RequestMapping("/product/page")
    public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
        int pageNumber = p.orElse(0); // Mặc định trang 0 nếu không có tham số
        Pageable pageable = PageRequest.of(pageNumber, 5); // 5 sản phẩm mỗi trang

        Page<Product> page = dao.findAll(pageable);
        model.addAttribute("page", page);

        return "product/page";
    }

    // bai1 lab7
    // @GetMapping("product/search")
    // public String search(Model model,
    // @RequestParam("min") Optional<Double> min,
    // @RequestParam("max") Optional<Double> max) {
    // double minPrice = min.orElse(0.0);
    // double maxPrice = max.orElse(Double.MAX_VALUE);
    // List<Product> items = dao.findByPrice(minPrice, maxPrice);
    // model.addAttribute("items", items);
    // return "product/search";
    // }

    // bai4 lab7
    @GetMapping("product/search")
    public String search(Model model,
            @RequestParam("min") Optional<Double> min,
            @RequestParam("max") Optional<Double> max) {
        double minPrice = min.orElse(0.0);
        double maxPrice = max.orElse(Double.MAX_VALUE);
        List<Product> items = dao.findByPriceBetween(minPrice, maxPrice);
        model.addAttribute("items", items);
        return "product/search";
    }
    // bai2 lab7
    // @RequestMapping("/product/search-and-page")
  // public String searchAndPage(Model model,
    // @RequestParam("keywords") Optional<String> kw,
    // @RequestParam("p") Optional<Integer> p) {
    // String keywords = kw.orElse(session.get("keywords", ""));
    // session.set("keywords", keywords);
    // Pageable pageable = PageRequest.of(p.orElse(0), 5);
    // Page<Product> page = dao.findByKeywords("%" + keywords + "%", pageable);
    // model.addAttribute("page", page);
    // model.addAttribute("keywords", keywords);
    // return "product/search-and-page";
    // }
    // bai5 lab7
    @RequestMapping("/product/search-and-page")
    public String searchAndPage(Model model,
            @RequestParam("keywords") Optional<String> kw,
            @RequestParam("p") Optional<Integer> p) {
        String keywords = kw.orElse(session.get("keywords", ""));
        session.set("keywords", keywords);
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Product> page = dao.findAllByNameLike("%" + keywords + "%", pageable);
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "product/search-and-page";
    }

}
