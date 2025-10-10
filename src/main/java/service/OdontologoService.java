package service;

import dao.iDao;
import model.Odontologo;
import java.util.List;

public class OdontologoService implements iService<Odontologo> {

    private iDao<Odontologo> odontologoDao;

    public OdontologoService(iDao<Odontologo> odontologoDao) {
        this.odontologoDao = odontologoDao;
    }

    @Override
    public Odontologo guardar(Odontologo entidad) {
        return odontologoDao.guardar(entidad);
    }

    @Override
    public Odontologo buscar(Integer id) {
        return odontologoDao.buscar(id);
    }

    @Override
    public void eliminar(Integer id) {
        odontologoDao.eliminar(id);
    }

    @Override
    public void actualizar(Odontologo entidad) {
        odontologoDao.actualizar(entidad);
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return odontologoDao.buscarTodos();
    }
}