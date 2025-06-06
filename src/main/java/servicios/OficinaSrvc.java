package servicios;

import modelos.Oficina;
import repositorios.OficinaRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OficinaSrvc implements IfOficinaSrvc{

    OficinaRepo ofiRepo = new OficinaRepo();

    @Override
    public List<Oficina> listaOficinas() throws SQLException {
        List<Oficina> oficinas = new ArrayList<>();
        return ofiRepo.listarOficinas();
    }

    @Override
    public Optional<Oficina> leerOficinaxId(String id) throws SQLException {
        Optional<Oficina> ofi = ofiRepo.leerOficinaxId(id);
        Oficina oficina = ofi.get();
        if(oficina.getRegion().equals("Centro")){
            oficina.setRegion(oficina.getRegion()+"DC");
        }
        return Optional.of(oficina);
    }

    @Override
    public void CrearOficina(Oficina oficina) throws SQLException {
        ofiRepo.CrearOficina(oficina);
    }

    @Override
    public boolean actualizarOficina(Oficina oficina) throws SQLException {
        return ofiRepo.actualizarOficina(oficina);
    }

    @Override
    public void borrarOficina(String id) throws SQLException {
        ofiRepo.borrarOficina(id);
    }
}
