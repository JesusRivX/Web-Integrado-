/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.boostmytool.mass.controladores;

import com.boostmytool.mass.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            // Almacenar la información del usuario en la sesión
            session.setAttribute("loggedUser", user);
            // Redirigir a la página de inicio
            return new ModelAndView("redirect:/index.html");
        } else {
            // Redirigir de vuelta al formulario de login con un mensaje de error
            return new ModelAndView("login").addObject("error", "Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String nombres, @RequestParam String email,
            @RequestParam String direccion, @RequestParam String dni,
            @RequestParam String password) {
        User newUser = new User();
        newUser.setNombres(nombres);
        newUser.setEmail(email);
        newUser.setDireccion(direccion);
        newUser.setDni(dni);
        newUser.setPassword(password);

        boolean success = userService.insertarCliente(newUser);
        if (success) {
            // Redirigir al usuario a la página de inicio de sesión después del registro
            return new ModelAndView("redirect:/inicioSesion.xhtml");
        } else {
            // Redirigir de vuelta al formulario de registro con un mensaje de error
            return new ModelAndView("register").addObject("error", "Error al registrar el usuario");
        }
    }

    @GetMapping("/logout") 
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/inicioSesion.xhtml";
    }
}
