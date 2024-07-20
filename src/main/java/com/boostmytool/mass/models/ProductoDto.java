/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.boostmytool.mass.models;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PC
 */
public class ProductoDto {
    @NotEmpty(message = "El nombre es requerido")
    private String nombre;
    
    @NotEmpty(message = "La Marca es requerido")
    private String marca;
    
    @NotEmpty(message = "La Categoria es requerido")
    private String categoria;
    
    @Min(0)
    private double precio;
    
    @Size(min = 10, message = "La descripcion debe tener al menos 10 caracteres")
    @Size(max = 1000, message = "La descrippcion no puede exceder los 2000 caracteres")
    private String descripcion;
    
    private MultipartFile imagen;

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }
    
    
    
}
