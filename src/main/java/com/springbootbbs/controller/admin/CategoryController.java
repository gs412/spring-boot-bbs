package com.springbootbbs.controller.admin;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.exception.PageNotFoundException;
import com.springbootbbs.libs.Result;
import com.springbootbbs.repository.CategoryRepository;
import com.springbootbbs.repository.TopicRepository;
import com.springbootbbs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller("admin.CategoryController")
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/categories")
    public String categories(ModelMap m) {

        Iterable<Category> categories = categoryRepository.findAllByOrderBySortAscIdAsc();

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
    public Result add_post(String nameCn, String nameEn, String tab) {
        if (categoryRepository.existsByNameCn(nameCn)) {
            return Result.failure("中文名称已经存在");
        } else if (categoryRepository.existsByNameEn(nameEn)) {
            return Result.failure("英文名称已经存在");
        } else if (categoryRepository.existsByTab(tab)) {
            return Result.failure("标签已经存在");
        } else {
            Category lastCategory = categoryRepository.findTopByOrderBySortDesc();
            int sort;
            if (lastCategory != null) {
                sort = (lastCategory.getSort()/10)*10 + 10;
            } else {
                sort = 1;
            }

            Category category = new Category();
            category.setNameCn(nameCn);
            category.setNameEn(nameEn);
            category.setTab(tab);
            category.setSort(sort);
            categoryService.save(category);

            return Result.success("添加成功");
        }
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
    public Result edit_post(@PathVariable Long id, String nameCn, String nameEn, String tab, Integer sort, ModelMap m) {
        if (categoryRepository.existsByNameCnAndIdNot(nameCn, id)) {
            return Result.failure("中文名称已经存在");
        } else if (categoryRepository.existsByNameEnAndIdNot(nameEn, id)) {
            return Result.failure("英文名称已经存在");
        } else if (categoryRepository.existsByTabAndIdNot(tab, id)) {
            return Result.failure("标签已经存在");
        } else {
            Optional<Category> categoryOptional = categoryRepository.findById(id);

            if (categoryOptional.isEmpty()) {
                throw new PageNotFoundException("分类不存在");
            }

            Category category = categoryOptional.get();
            category.setNameCn(nameCn);
            category.setNameEn(nameEn);
            category.setTab(tab);
            category.setSort(sort);
            categoryRepository.save(category);

            return Result.success("编辑成功");
        }
    }

    @RequestMapping("/merge/{id}")
    public String merge(@PathVariable Long id, ModelMap m) {
        Category category = categoryRepository.findById(id).get();
        Iterable<Category> categories = categoryRepository.findAllByIdNotOrderBySortAscIdAsc(id);

        m.addAttribute("user", getUser());
        m.addAttribute("category", category);
        m.addAttribute("categories", categories);

        return "/admin/category/merge";
    }

    @RequestMapping(value = "/merge_post/{id}", method = RequestMethod.POST)
    public String merge_post(@PathVariable Long id, Long target_id) {
        Category sourceCategory = categoryRepository.findById(id).get();
        Category targetCategory = categoryRepository.findById(target_id).get();
        topicRepository.moveCategoryToCategory(sourceCategory, targetCategory);

        categoryService.delete(sourceCategory);

        return "redirect:/admin/category/categories";
    }

}
