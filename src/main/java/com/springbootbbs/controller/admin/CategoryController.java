package com.springbootbbs.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootbbs.entiry.Category;
import com.springbootbbs.repository.CategoryRepository;
import freemarker.template.ObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller("admin.CategoryController")
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/categories")
    public String categories(ModelMap m) {

        Iterable<Category> categories = categoryRepository.findByOrderBySortAscIdAsc();

        m.addAttribute("user", getUser());
        m.addAttribute("categories", categories);

        return "/admin/category/categories";
    }

    @RequestMapping("/add")
    public String add(ModelMap m) {

        m.addAttribute("user", getUser());

        return "/admin/category/add";
    }

    @RequestMapping(value = "/add_post", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String add_post(String name, String tab) throws JsonProcessingException {
        HashMap<String, String> map = new HashMap<>();

        if (categoryRepository.existsByName(name)) {
            map.put("success", "0");
            map.put("message", "分来名称已经存在");
        } else if (categoryRepository.existsByTab(tab)) {
            map.put("success", "0");
            map.put("message", "标签已经存在");
        } else {
            Category lastCategory = categoryRepository.findTopByOrderBySortDesc();
            Integer sort;
            if (lastCategory != null) {
                sort = lastCategory.getSort() + 1;
            } else {
                sort = 1;
            }

            Category category = new Category();
            category.setName(name);
            category.setTab(tab);
            category.setSort(sort);
            categoryRepository.save(category);

            map.put("success", "1");
            map.put("message", "添加成功");
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap m) {
        Category category = categoryRepository.findById(id).get();

        m.addAttribute("user", getUser());
        m.addAttribute("category", category);

        return "/admin/category/edit";
    }

    @RequestMapping(value = "/edit_post/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String edit_post(@PathVariable Long id, String name, String tab, Integer sort, ModelMap m) throws JsonProcessingException {
        HashMap<String, String> map = new HashMap<>();

        if (categoryRepository.existsByNameAndIdNot(name, id)) {
            map.put("success", "0");
            map.put("message", "分来名称已经存在");
        } else if (categoryRepository.existsByTabAndIdNot(tab, id)) {
            map.put("success", "0");
            map.put("message", "标签已经存在");
        } else {
            Category category = categoryRepository.findById(id).get();
            category.setName(name);
            category.setTab(tab);
            category.setSort(sort);
            categoryRepository.save(category);

            map.put("success", "1");
            map.put("message", "编辑成功");
        }

        String json = new ObjectMapper().writeValueAsString(map);
        return json;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "已删除");
        return "redirect:/admin/category/categories";
    }

}
