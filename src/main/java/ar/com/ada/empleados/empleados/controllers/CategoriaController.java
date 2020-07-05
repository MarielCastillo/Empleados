package ar.com.ada.empleados.empleados.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.empleados.empleados.entities.Categoria;
import ar.com.ada.empleados.empleados.models.response.GenericResponse;
import ar.com.ada.empleados.empleados.services.CategoriaService;

@RestController
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        categoriaService.crearCategoria(categoria);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = categoria.getCategoriaId();
        gR.message = "Categoria creada con exito";
        return ResponseEntity.ok(gR);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategoria() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());
    }

    
}