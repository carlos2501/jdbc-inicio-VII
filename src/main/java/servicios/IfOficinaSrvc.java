package servicios;

import modelos.Oficina;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IfOficinaSrvc {
    List<Oficina> listaOficinas() throws SQLException;
    Optional<Oficina> leerOficinaxId(String id) throws SQLException;
    void CrearOficina(Oficina oficina) throws SQLException;
    boolean actualizarOficina(Oficina oficina) throws SQLException;
    void borrarOficina(String id) throws SQLException;
}
