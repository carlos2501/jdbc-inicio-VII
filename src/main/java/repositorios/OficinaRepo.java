package repositorios;

import modelos.Oficina;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OficinaRepo {

    private static final String LISTAR_OFICINAS = "SELECT * FROM oficina";

    private Connection obtenerConexion() throws SQLException {
        return ConexionBD.creaConexion();
    }

    private Statement obtenerSentencia() throws SQLException {
        return obtenerConexion().createStatement();
    }

    public List<Oficina> listarOficinas() throws SQLException {
        // Creamos la variable que contendrá los datos de las oficinas
        List<Oficina> listaOficinas = new ArrayList<>();
        // ejecutamos la consulta dentro de un try con recursos para asegurarnos el cierre de estos
        try(Statement stmt = obtenerSentencia();
            ResultSet rs = stmt.executeQuery(LISTAR_OFICINAS)) {
            while (rs.next()) {
                Oficina oficina = new Oficina();
                oficina.setCodigoOficina(rs.getString("codigo_oficina"));
                oficina.setCiudad(rs.getString("ciudad"));
                oficina.setPais(rs.getString("pais"));
                oficina.setRegion(rs.getString("region"));
                oficina.setCodigoPostal(rs.getString("codigo_postal"));
                oficina.setTelefono(rs.getString("telefono"));
                oficina.setLineaDireccion1(rs.getString("linea_direccion1"));
                oficina.setLineaDireccion2(rs.getString("linea_direccion2"));
                listaOficinas.add(oficina);
            }
            return listaOficinas;
        }
    }

    public Optional<Oficina> leerOficinaxId(String id) throws SQLException {
        // Creo una oficina a null para devolver en caso de que no exista la solicitada
        Oficina oficina = null;
        // Consulta a ejecutar
        final String sqlQuery = "SELECT * FROM oficina WHERE codigo_oficina = ?";
        // El try conrecursos prepara la sentencia
        try (
                PreparedStatement stmt = obtenerConexion().prepareStatement(sqlQuery)
        ) {
            // asignamos el valor del parámetro de la query
            stmt.setString(1, id);
            // obtenemos el resultado
            ResultSet rs = stmt.executeQuery();
            // si no hay oficina, se devolverá un optional.empty, y si la hay se devolverá su valor
            if (rs.next()) {
                oficina = extractOficinaFrom(rs);
            }
        }
        return Optional.ofNullable(oficina);
    }

    public void CrearOficina(Oficina oficina) throws SQLException {
        String qry = "INSERT INTO oficina (codigo_oficina, ciudad) VALUES (?, ?)";

        try(PreparedStatement stmt = obtenerConexion().prepareStatement(qry)) {
            stmt.setString(1, oficina.getCodigoOficina());
            stmt.setString(2, oficina.getCiudad());
            stmt.executeUpdate();
        }

    }

    public boolean actualizarOficina(Oficina oficina) throws SQLException {
        String sql = "UPDATE oficina SET ciudad = ?, pais = ?, region = ?, codigo_postal = ?, telefono = ?, linea_direccion1 = ?, linea_direccion2 = ? WHERE codigo_oficina = ?";

        try (PreparedStatement stmt = obtenerConexion().prepareStatement(sql)) {
            stmt.setString(1, oficina.getCiudad());
            stmt.setString(2, oficina.getPais());
            stmt.setString(3, oficina.getRegion());
            stmt.setString(4, oficina.getCodigoPostal());
            stmt.setString(5, oficina.getTelefono());
            stmt.setString(6, oficina.getLineaDireccion1());
            stmt.setString(7, oficina.getLineaDireccion2());
            stmt.setString(8, oficina.getCodigoOficina());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public Oficina extractOficinaFrom(ResultSet rs) throws SQLException {
        Oficina oficina = new Oficina();
        oficina.setCodigoOficina(rs.getString("codigo_oficina"));
        oficina.setCiudad(rs.getString("ciudad"));
        oficina.setPais(rs.getString("pais"));
        oficina.setRegion(rs.getString("region"));
        oficina.setCodigoPostal(rs.getString("codigo_postal"));
        oficina.setTelefono(rs.getString("telefono"));
        oficina.setLineaDireccion1(rs.getString("linea_direccion1"));
        oficina.setLineaDireccion2(rs.getString("linea_direccion2"));
        return oficina;
    }

}
