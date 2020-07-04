package ar.com.ada.empleados.empleados.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.empleados.empleados.entities.Empleado;
import ar.com.ada.empleados.empleados.repos.EmpleadoRepository;

@Service
public class EmpleadoServices {
    @Autowired
    private EmpleadoRepository repo;

    public void crearEmpleado(Empleado empleado) {
        repo.save(empleado);
    }

    public List<Empleado> obtenerEmpleado() {
        return (repo.findAll());
    }
}