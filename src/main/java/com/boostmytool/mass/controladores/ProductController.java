/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.boostmytool.mass.controladores;

import org.springframework.web.multipart.MultipartFile;
import com.boostmytool.mass.models.ProductoDto;
import com.boostmytool.mass.models.Productos;
import com.boostmytool.mass.models.services.ProductsRepository;

import jakarta.validation.Valid;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductsRepository repo;
    
    @GetMapping("/index")
    public String showProductIndex(Model model) {
        List<Productos> productos = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("productos", productos);
        return "productos/index"; 
    }

    @GetMapping("/producto")
    public String showProductList(Model model) {
        List<Productos> productos = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("productos", productos);
        return "productos/producto"; 
    }

    @GetMapping("/crear")
    public String showCreatePage(Model model) {
        ProductoDto productoDto = new ProductoDto();
        model.addAttribute("productoDto", productoDto);
        return "productos/CrearProducto";
    }

    @PostMapping("/crear")
    public String createProdcut(
            @Valid @ModelAttribute ProductoDto productoDto,
            BindingResult result
    ) {

        if (productoDto.getImagen().isEmpty()) {
            result.addError(new FieldError("productoDto", "imagen", "La imagen es requerido"));
        }

        if (result.hasErrors()) {
            return "productos/CrearProducto";
        }

        //Guardar el documento de la imagen
        MultipartFile imagen = productoDto.getImagen();
        Date fecha = new Date();
        String guardado = fecha.getTime() + "_" + imagen.getOriginalFilename();

        try {
            String uploadDir = "public/Mimagen/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imagen.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + guardado),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Productos producto = new Productos();
        producto.setNombre(productoDto.getNombre());
        producto.setMarca(productoDto.getMarca());
        producto.setCategoria(productoDto.getCategoria());
        producto.setPrecio(productoDto.getPrecio());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setFecha(fecha);
        producto.setImagen(guardado);

        repo.save(producto);

        return "redirect:/productos";
    }

    @GetMapping("/editar")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {

        try {
            Productos producto = repo.findById(id).get();
            model.addAttribute("producto", producto);

            ProductoDto productoDto = new ProductoDto();
            productoDto.setNombre(producto.getNombre());
            productoDto.setMarca(producto.getMarca());
            productoDto.setCategoria(producto.getCategoria());
            productoDto.setPrecio(producto.getPrecio());
            productoDto.setDescripcion(producto.getDescripcion());

            model.addAttribute("productoDto", productoDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/productos";
        }

        return "productos/EditarProducto";
    }

    @PostMapping("/editar")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ProductoDto productoDto,
            BindingResult result
    ) {

        try {
            Productos producto = repo.findById(id).get();
            model.addAttribute("producto", producto);

            if (result.hasErrors()) {
                return "productos/EditarProducto";
            }

            //inicio
            if (!productoDto.getImagen().isEmpty()) {

                //Eliminar la imagen antigua
                String uploadDir = "public/Mimagen/";
                Path oldImagePath = Paths.get(uploadDir + producto.getImagen());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                //Guardar la nueva Imagen
                MultipartFile imagen = productoDto.getImagen();
                Date fecha = new Date();
                String guardado = fecha.getTime() + "_" + imagen.getOriginalFilename();

                try (InputStream inputStream = imagen.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + guardado),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                producto.setImagen(guardado);
            }
            //fin

            producto.setNombre(productoDto.getNombre());
            producto.setMarca(productoDto.getMarca());
            producto.setCategoria(productoDto.getCategoria());
            producto.setPrecio(productoDto.getPrecio());
            producto.setDescripcion(productoDto.getDescripcion());

            repo.save(producto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/productos";
    }

    @GetMapping("/eliminar")
    public String deleteProduct(
            @RequestParam int id
    ) {

        try {
            
            Productos producto = repo.findById(id).get();
            
            //Eliminar la imagen del producto
            Path imagePath = Paths.get("public/Mimagen/"+ producto.getImagen());
            try {
                Files.delete(imagePath);
            }
            catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
                }
            
            //Eliminar el producto
            repo.delete(producto);
            
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/productos";
    }

}
