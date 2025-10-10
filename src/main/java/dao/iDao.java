package dao;

import java.util.List;

public interface iDao<T> {
    T guardar(T entidad);
    T buscar(Integer id);
    void eliminar(Integer id);
    void actualizar(T entidad);
    List<T> buscarTodos();
}
