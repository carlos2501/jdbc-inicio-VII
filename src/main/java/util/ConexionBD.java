package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Definimos los parámetros de conexión a la BBDD
    private static final String url = "jdbc:postgresql://localhost:5432/jardineria";
    private static final String usuario = "paisajista";
    private static final String clave = "paisajista";

    // el método debe devolver una variable de tipo Connection que será utilizada por el código que llama
    private static Connection conex;

    public static Connection creaConexion() throws SQLException {
        if(conex == null) {
            conex = DriverManager.getConnection(url, usuario, clave);
        }
        return conex;
    }

    public static void close() throws SQLException {
        if(conex != null) {
            conex.close();
        }
    }

}
