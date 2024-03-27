package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String showUsers (ModelMap modelMap) {
        modelMap.addAttribute("ListOfUsers", userService.findAll());
        return "allUsers";
    }
    @GetMapping ("/createUser")
    public String createFormUser (ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("role", roleService.getAll());
        return "createUser";
    }

    @PostMapping("/createUser")
    public String addUser (@ModelAttribute ("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping ("/editUser")
    public String editFormUser (@RequestParam(value = "id", required = false) Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        modelMap.addAttribute("role", roleService.getAll());
        return "editUser";
    }

    @PostMapping("/editUser")
    public String edit (@ModelAttribute ("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/allUsers")
    public String delete (@RequestParam(value = "id", required = false) Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
