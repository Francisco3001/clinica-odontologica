package service;

import dao.iDao;
import model.Paciente;
import java.util.List;

public class PacienteService implements iService<Paciente> {

    private iDao<Paciente> pacienteDao;

    public PacienteService(iDao<Paciente> pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    @Override
    public Paciente guardar(Paciente entidad) {
        return pacienteDao.guardar(entidad);
    }

    @Override
    public Paciente buscar(Integer id) {
        return pacienteDao.buscar(id);
    }

    @Override
    public void eliminar(Integer id) {
        pacienteDao.eliminar(id);
    }

    @Override
    public void actualizar(Paciente entidad) {
        pacienteDao.actualizar(entidad);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteDao.buscarTodos();
    }
}