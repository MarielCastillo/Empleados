package ar.com.ada.empleados.empleados.controllers;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.empleados.empleados.entities.Categoria;
import ar.com.ada.empleados.empleados.entities.Empleado;
import ar.com.ada.empleados.empleados.models.request.InfoEmpleadoRequest;
import ar.com.ada.empleados.empleados.models.request.SueldoModifRequest;
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

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable int id) {
        Empleado empleado = empleadoServices.obtenerPorId(id);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
            // return ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(empleado);
    }

    @GetMapping("/empleados/categorias/{categoriaId}")
    public ResponseEntity<List<Empleado>> listarPorCategoria(@PathVariable int categoriaId) {
        List<Empleado> listaEmpleados = categoriaService.obtenerPorId(categoriaId).getEmpleados();
        return ResponseEntity.ok(listaEmpleados);
    }

    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable int id,
            @RequestBody SueldoModifRequest sueldoRequest) {
        Empleado empleado = empleadoServices.obtenerPorId(id);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }
        empleado.setSueldo(sueldoRequest.sueldoNuevo);
        empleadoServices.grabar(empleado);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleado.getEmpleadoId();
        gR.message = "Sueldo actualizado con exito";
        return ResponseEntity.ok(gR);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleado(@PathVariable int id) {
        Empleado empleado = empleadoServices.obtenerPorId(id);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        empleado.setFechaBaja(new Date());
        empleado.setEstadoId(0);
        empleadoServices.grabar(empleado);

        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleado.getEmpleadoId();
        gR.message = "Empleado dado de baja con exito";
        return ResponseEntity.ok(gR);
    }
}