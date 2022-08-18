package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.AdminService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping() //+
    public String index(Model model) {
        model.addAttribute("allUsers", adminService.showUsers());
        return "admin/index";
    }

    @GetMapping("/new") //+
    public String createUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/new";
    }

    @PostMapping("/save") //+
    public String addUser(@ModelAttribute("user") User user) {
        adminService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}") //+
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userToShow", adminService.getUserById(id));
        return "admin/show";
    }

    @GetMapping("/{id}/update") //+
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userToShow", adminService.getUserById(id));
        return "admin/update";
    }

    @PatchMapping("/{id}/update") //+
    public String updateUser(@ModelAttribute("userToUpdate") User userToUpdate) {
        adminService.updateUser(userToUpdate);
        return "redirect:/admin/" + userToUpdate.getId();
    }

    @DeleteMapping("{id}/delete") //+
    public String deleteUser(@PathVariable("id") Long id) {
        adminService.deleteUserById(id);
        return "redirect:/admin";
    }


}