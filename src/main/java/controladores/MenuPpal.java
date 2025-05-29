package controladores;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuPpal {

    public void presentarMenuPpal() throws SQLException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        boolean seguir = true;
        OficinaCtrl oficinaCtrl = new OficinaCtrl();

        // Presentamos el menú en la consola
        while (seguir) {
            // Presentamos el menú en la consola
            System.out.print("""
                    -------------- Selection Menu --------------
                    1.Listar oficinas
                    2.Leer oficina por su ID
                    3.Nueva oficina
                    4.Modificar oficina
                    5.Borrar oficina
                    6.Salir
                    Indique su opción: \s""");
            // esperamos la opción del cliente
            String opc = scanner.nextLine();
            switch (opc) {
                case "1": {
                    oficinaCtrl.listarOficinas();
                    break;
                }
                case "2": {
                    oficinaCtrl.leerOficinaxId();
                    break;
                }
                case "3": {
                    oficinaCtrl.crearOficina();
                    break;
                }
                case "4": {
                    oficinaCtrl.modifiarOficina();
                    break;
                }
                /*case "5" -> .delete();*/
                case "6": {
                    seguir = false;
                    break;
                }
                default: {
                    System.out.println("Opción no válida");
                }
            }
        }
    }
}
