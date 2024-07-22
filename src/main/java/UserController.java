
import com.boostmytool.mass.controladores.UserService;
import com.boostmytool.mass.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
            // Redirigir al usuario a la página de inicio después del registro
            return new ModelAndView("redirect:/inicio.xhtml");
        } else {
            // Redirigir de vuelta al formulario de registro con un mensaje de error
            return new ModelAndView("register").addObject("error", "Error al registrar el usuario");
        }
    }
}