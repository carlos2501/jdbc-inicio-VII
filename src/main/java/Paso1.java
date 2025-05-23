import java.sql.*;

public class Paso1 {
    public static void main(String[] args) {
        // Definimos los parámetros de conexión a la BBDD
        String url = "jdbc:postgresql://localhost:5432/jardineria";
        String usuario = "jardinero";
        String clave = "jardinero";
        try {
            // Creamos la instancia de la conexión a la BBDD
            Connection conex = DriverManager.getConnection(url, usuario, clave);
            // Creamos una instancia de sentencia para poder enviar consultas al servidor de BBDD
            Statement st = conex.createStatement();
            // definimos la consulta, la enviamos la servidor (ejecutamos la sentencia) y obtenemos el resultado devuelto por el servidor
            ResultSet rs = st.executeQuery("SELECT * FROM cliente");
            // En el ResulSet tenemos todos los registros devueltos por el servidor dentro de un iterador
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }
            rs.close();
            st.close();
            conex.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
