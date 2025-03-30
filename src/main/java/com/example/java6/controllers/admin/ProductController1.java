package com.example.java6.controllers.admin;

import com.example.java6.entities.Product;
import com.example.java6.services.ProductService;
import com.example.java6.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductController1 {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = "D:\\HocKy5\\Java6\\java6\\src\\main\\resources\\static\\images";

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile file) throws IOException {
        // Nếu có file ảnh, lưu vào thư mục và cập nhật đường dẫn
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File saveFile = new File(UPLOAD_DIR + fileName);
            file.transferTo(saveFile);
            product.setImage("/images/" + fileName);
        }

        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Product> productOpt = Optional.ofNullable(productService.findById(id));
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            model.addAttribute("categories", categoryService.findAll());
            return "product/form";
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        if (product != null && (product.getOrderDetails() == null || product.getOrderDetails().isEmpty())) {
            productService.deleteById(id);
        } else {
            model.addAttribute("error", "Không thể xóa sản phẩm vì đã có đơn hàng liên quan.");
        }
        return "redirect:/admin/product";
    }

}
