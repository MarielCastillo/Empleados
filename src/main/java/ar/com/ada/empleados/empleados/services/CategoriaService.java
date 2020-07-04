package ar.com.ada.empleados.empleados.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.empleados.empleados.entities.Categoria;
import ar.com.ada.empleados.empleados.repos.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repo;

    public void crearCategoria(Categoria categoria) {
        repo.save(categoria);
    }

    public List<Categoria> obtenerCategorias() {
        return (repo.findAll());
    }

    public Categoria obtenerPorId(int categoriId){
        Optional<Categoria> c = repo.findById(categoriId);

        if(c.isPresent()){
            return c.get();
        }
        return null;
    }
}