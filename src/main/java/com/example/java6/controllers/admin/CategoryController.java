package com.example.java6.controllers.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.java6.entities.Category;
import com.example.java6.repositories.CategoryDAO;

@Controller
public class CategoryController {
    @Autowired
    CategoryDAO dao;

    @RequestMapping("/category/index")
    public String index(Model model) {
        model.addAttribute("item", new Category());
        model.addAttribute("items", dao.findAll());
        return "category/index";
    }

    @RequestMapping("/category/create")
    public String create(Category item, RedirectAttributes redirectAttributes) {
        // Kiểm tra trùng tên
        Optional<Category> existingCategory = dao.findByName(item.getName());
        if (existingCategory.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Tên danh mục đã tồn tại!");
            return "redirect:/category/index";
        }
        dao.save(item);
        redirectAttributes.addFlashAttribute("success", "Thêm danh mục thành công!");
        return "redirect:/category/index";
    }

    @RequestMapping("/category/update")
    public String update(Category item, RedirectAttributes redirectAttributes) {
        // Kiểm tra trùng tên nhưng không phải chính nó
        Optional<Category> existingCategory = dao.findByName(item.getName());
        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(item.getId())) {
            redirectAttributes.addFlashAttribute("error", "Tên danh mục đã tồn tại!");
            return "redirect:/category/edit/" + item.getId();
        }
        dao.save(item);
        redirectAttributes.addFlashAttribute("success", "Cập nhật danh mục thành công!");
        return "redirect:/category/index";
    }
}
