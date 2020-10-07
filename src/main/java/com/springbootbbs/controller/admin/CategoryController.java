package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.CategoryController")
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/categories")
    public String categories(ModelMap m) {

        Iterable<Category> categories = categoryRepository.findByOrderBySortAsc();

        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "/admin/category/categories";
    }

    @RequestMapping("/add")
    public String add(ModelMap m) {

        m.addAttribute("user", getUser());

        return "/admin/category/add";
    }

    @RequestMapping("/add_post")
    public String add_post(String name) {
        Category lastCategory = categoryRepository.findTopByOrderBySortDesc();
        Integer sort;
        if (lastCategory != null) {
            sort = lastCategory.getSort() + 1;
        } else {
            sort = 1;
        }

        Category category = new Category();
        category.setName(name);
        category.setSort(sort);
        categoryRepository.save(category);

        return "redirect:/admin/category/categories";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap m) {
        Category category = categoryRepository.findById(id).get();

        m.addAttribute("user", getUser());
        m.addAttribute("category", category);

        return "/admin/category/edit";
    }

}
