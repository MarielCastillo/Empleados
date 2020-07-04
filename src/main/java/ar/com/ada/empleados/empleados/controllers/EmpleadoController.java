package ar.com.ada.empleados.empleados.controllers;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.empleados.empleados.entities.Categoria;
import ar.com.ada.empleados.empleados.entities.Empleado;
import ar.com.ada.empleados.empleados.models.request.InfoEmpleadoRequest;
import ar.com.ada.empleados.empleados.models.response.GenericResponse;
import ar.com.ada.empleados.empleados.services.*;

@RestController
public class EmpleadoController {
    @Autowired
    EmpleadoServices empleadoServices;
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadoRequest info) {
        Empleado empleado = new Empleado();
        empleado.setNombre(info.nombre);
        empleado.setEdad(info.edad);
        empleado.setSueldo(info.sueldo);
        empleado.setFechaAlta(new Date());
        Categoria categoria = categoriaService.obtenerPorId(info.categoriaId);
        empleado.setCategoria(categoria);
        empleado.setEstadoId(1);

        empleadoServices.crearEmpleado(empleado);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleado.getEmpleadoId();
        gR.message = "Empleado creada con exito";
        return ResponseEntity.ok(gR);
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarEmpleado() {
        return ResponseEntity.ok(empleadoServices.obtenerEmpleado());
    }
}