package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.CategoryController")
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/categories")
    public String categories(ModelMap m) {

        Iterable<Category> categories = categoryRepository.findAll();

        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "/admin/category/categories";
    }

}
