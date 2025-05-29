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

    public void modificarOficina() throws SQLException, IllegalAccessException {
        Oficina oficina;
        // preguntamos el código de la oficina
        System.out.println("Ingrese el código de la oficina: ");
        Optional<Oficina> ofi = oficinaSrvc.leerOficinaxId(scanner.nextLine());
        if (ofi.isPresent()) {
            // Si existe ofi (clase Optional) cargo su contenido en la variable oficina
            oficina = ofi.get();
            // Obtengo la lista de campos -propiedades- de <oficina> mediante reflexión <oficina.getClass().getDeclaredFields()>
            Field[] listaCampos = oficina.getClass().getDeclaredFields();
            // Recorremos la lista de los campos
            for (int i = 0; i < listaCampos.length; i++) {
                // para cada campo, presento su nombre y su valor.
                Field field = listaCampos[i];
                field.setAccessible(true);
                System.out.println("Campo: " + field.getName() + " - valor: " + field.get(oficina) + " - nuevo valor: ");
                // pregunto por el nuevo valor...
                String valor = scanner.nextLine();
                if(! valor.isEmpty()) {     // ... si el usuario introduce algo...
                    field.set(oficina, valor);      // ... actualizo el valor del campo
                }
            }
            // grabo los cambios
            oficinaSrvc.actualizarOficina(oficina);
            System.out.println("La oficina ha sido modificada correctamente");
        }
    }

    public void borrarOficina() throws SQLException{
        System.out.println("Ingrese el código de la oficina: ");
        String codigoOficina = scanner.nextLine();

        if(codigoOficina != null) {
            oficinaSrvc.borrarOficina(codigoOficina);
            System.out.println("La oficina ha sido borrada correctamente");
        }else{
            System.out.println("No ha introducido ningún código de oficina");
        }
    }
}
