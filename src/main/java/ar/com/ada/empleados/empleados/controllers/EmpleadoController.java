package ar.com.ada.empleados.empleados.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.empleados.empleados.entities.Empleado;
import ar.com.ada.empleados.empleados.models.response.GenericResponse;
import ar.com.ada.empleados.empleados.services.EmpleadoServices;

@RestController
public class EmpleadoController {
    @Autowired
    EmpleadoServices empleadoServices;

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) {
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