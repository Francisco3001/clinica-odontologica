package service;

import java.util.List;

public interface iService<T> {
    T guardar(T entidad);
    T buscar(Integer id);
    void eliminar(Integer id);
    void actualizar(T entidad);
    List<T> buscarTodos();
}
