package controladores;

import modelos.Oficina;
import servicios.IfOficinaSrvc;
import servicios.OficinaSrvc;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class OficinaCtrl {

    IfOficinaSrvc oficinaSrvc = new OficinaSrvc();
    Scanner scanner = new Scanner(System.in);

    public void listarOficinas() throws SQLException {
        oficinaSrvc.listaOficinas().forEach(System.out::println);
    }

    public void leerOficinaxId() throws SQLException {
        System.out.println("Indique el id de la oficina: ");
        // preguntamos el id de la oficina y vamos a buscar sus datos
        Optional<Oficina> ofi = oficinaSrvc.leerOficinaxId(scanner.nextLine());
        // Aprovecho las capacidades de Optional para simplificar el código y utilizar conceptos de programación funcional
        ofi.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("El id de la oficina no existe")
        );
    }

    public void crearOficina() throws IllegalAccessException, SQLException {
        Oficina ofi = new Oficina();
        System.out.println("Introduzca los datos de la oficina: ");
        // Obtenemos la lista de los campos mediante reflexión
        Field[] listaCampos = ofi.getClass().getDeclaredFields();
        // Generamos una lista con el nombre del campo y pregunto su valor al usuario
        for(int i = 0; i < listaCampos.length; i++) {  // en producción, utilizar el for "mejorado" que propone el IDE
            // obtengo el campo
            Field field = listaCampos[i];
            // hago que sus propiedades "private" sean accesibles (las otras ya lo son)
            field.setAccessible(true);
            // pregunto al usuario
            System.out.println("Campo: " + field.getName() + " - valor: ");
            String valor = scanner.nextLine();
            field.set(ofi,valor);
        }
        // Envio al servicio la petición de crear la oficina
        oficinaSrvc.CrearOficina(ofi);

        // Comprobamos sio se ha creado la oficina
        Optional<Oficina> ofiGrabada = oficinaSrvc.leerOficinaxId(ofi.getCodigoOficina());

        /** La siguiente comprobación es funcionalmente idéntica a la realizada en las últimas líneas del método
         * leerOficinaxId(). Aquella tiene un enfoque de programación funcional (gracias a Optional) y esta un enfoque
         * de programación "procedimental" tradicional
         */
        if (ofiGrabada.isPresent()) {
            System.out.println("la oficina se ha grabado correctamente.");
        } else {
            System.out.println("la oficina no se ha grabado correctamente.");
        }
    }

    public void modifiarOficina() {
        System.out.println("Modificar oficina:");
        System.out.print("Introduce el código de la oficina que quieres modificar: ");
        String codigoModificar = scanner.nextLine();
        Oficina oficina = new Oficina();
        try {
            Optional<Oficina> oficinaExistenteOpt = repoOfi.leerOficinaxId(codigoModificar);

            leerYModificarCampos(scanner, oficinaExistenteOpt.get(), repoOfi);

            // Utilizamos las posibilidades de la clase Optional para programación funcional
                        /*oficinaExistenteOpt.ifPresentOrElse(
                                ofi -> {
                                    try {
                                        System.out.println(repoOfi.actualizarOficina(preguntarCampos(ofi))  //utilizamos un if ternario
                                                ? "Oficina actualizada correctamente."
                                                : "No se pudo actualizar la oficina :"
                                        );
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                },
                                () -> System.out.println("No se encontró ninguna oficina con ese código.")
                        );*/
        } catch (SQLException e) {
            System.out.println("Error al modificar la oficina: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
