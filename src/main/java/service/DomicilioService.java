package service;

import dao.iDao;
import model.Domicilio;
import java.util.List;

public class DomicilioService implements iService<Domicilio> {

    private iDao<Domicilio> domicilioDao;

    public DomicilioService(iDao<Domicilio> domicilioDao) {
        this.domicilioDao = domicilioDao;
    }

    @Override
    public Domicilio guardar(Domicilio entidad) {
        return domicilioDao.guardar(entidad);
    }

    @Override
    public Domicilio buscar(Integer id) {
        return domicilioDao.buscar(id);
    }

    @Override
    public void eliminar(Integer id) {
        domicilioDao.eliminar(id);
    }

    @Override
    public void actualizar(Domicilio entidad) {
        domicilioDao.actualizar(entidad);
    }

    @Override
    public List<Domicilio> buscarTodos() {
        return domicilioDao.buscarTodos();
    }
}
