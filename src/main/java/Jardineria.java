import modelos.Oficina;
import repositorios.OficinaRepo;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Jardineria {

    public static void main(String[] args) throws SQLException {

        OficinaRepo repoOfi = new OficinaRepo();
        boolean run = true;
        Scanner scanner = new Scanner(System.in);

        while (run) {
            // Presentamos el menú en la consola
            System.out.print("""
                    -------------- Selection Menu --------------
                    1.Listar oficinas
                    2.findById
                    3.create
                    4.update
                    5.delete
                    6.exit
                    Insert your option: \s""");
            // esperamos la opción del cliente
            String opc = scanner.nextLine();
            switch (opc) {
                case "1": {
                    repoOfi.listarOficinas();
                    break;
                }
                case "2": {
                    System.out.println("Indique el id de la oficina: ");
                    // preguntamos el id de la oficina y vamos a buscar sus datos
                    Optional<Oficina> ofi = repoOfi.leerOficinaxId(scanner.nextLine());
                    ofi.ifPresent(System.out::println);
                    if (ofi.isEmpty()) {
                        System.out.println("El id de la oficina no existe");
                    }
                    break;
                }
                case "3": {
                    Oficina ofi = new Oficina();
                    System.out.println("Introduzca los datos de la oficina: ");
                    System.out.println("Código de oficina: ");
                    ofi.setCodigoOficina(scanner.nextLine());
                    System.out.println("Ciudad:");
                    ofi.setCiudad(scanner.nextLine());
                    repoOfi.CrearOficina(ofi);
                    // Comprobamos sio se ha creado la oficina
                    Optional<Oficina> ofiGrabada = repoOfi.leerOficinaxId(ofi.getCodigoOficina());
                    if (ofiGrabada.isPresent()) {
                        System.out.println("la oficina se ha grabado correctamente.");
                    } else {
                        System.out.println("la oficina no se ha grabado correctamente.");
                    }
                    break;
                }
                case "4": {
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
                    break;
                }
                /*case "5" -> .delete();*/
                case "6": {
                    run = false;
                    break;
                }
                default: {
                    System.out.println("Invalid command");
                }
            }
        }
    }

    // Utilizamos el criterio de "responsabilidad única" del código y creamos este método que se ocupa de preguntar
    //  los campos al usuario devolver un objeto Oficina con los nuevos valores que se hayan modificado
    private static Oficina preguntarCampos(Oficina oficinaExistente) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Datos actuales:");
        System.out.println(oficinaExistente);

        System.out.print("Nueva ciudad: ");
        String ciudadNueva = scanner.nextLine();
        oficinaExistente.setCiudad(ciudadNueva.isEmpty() ? oficinaExistente.getCiudad() : ciudadNueva);

        System.out.print("Nuevo país: ");
        String paisNuevo = scanner.nextLine();
        oficinaExistente.setPais(paisNuevo.isEmpty() ? oficinaExistente.getPais() : paisNuevo);

        System.out.print("Nueva región: ");
        String regionNueva = scanner.nextLine();
        oficinaExistente.setRegion(regionNueva.isEmpty() ? oficinaExistente.getRegion() : regionNueva);

        System.out.print("Nuevo código postal: ");
        String postalNuevo = scanner.nextLine();
        oficinaExistente.setCodigoPostal(postalNuevo.isEmpty() ? oficinaExistente.getCodigoPostal() : postalNuevo);

        System.out.print("Nuevo teléfono: ");
        String telefonoNuevo = scanner.nextLine();
        oficinaExistente.setTelefono(telefonoNuevo.isEmpty() ? oficinaExistente.getTelefono() : telefonoNuevo);

        System.out.print("Nueva dirección línea 1: ");
        String direccion1Nueva = scanner.nextLine();
        oficinaExistente.setLineaDireccion1(direccion1Nueva.isEmpty() ? oficinaExistente.getLineaDireccion1() : direccion1Nueva);

        System.out.print("Nueva dirección línea 2: ");
        String direccion2Nueva = scanner.nextLine();
        oficinaExistente.setLineaDireccion2(direccion2Nueva.isEmpty() ? oficinaExistente.getLineaDireccion2() : direccion2Nueva);
        return oficinaExistente;
    }

    private static void leerYModificarCampos(Scanner scanner, Oficina oficina, OficinaRepo repo) throws IllegalAccessException, SQLException {
        boolean seguir = true;
        boolean otravez = false;
        // Obtenemos los campos de la oficina
        Field[] listaCampos = oficina.getClass().getDeclaredFields();
        // Generamos una lista con el nombre del campo y su valor mediante reflexión
        for(int i = 0; i<listaCampos.length; i++) {
            Field field = listaCampos[i];
            field.setAccessible(true);
            System.out.println("Campo " + (i+1) + ": " + field.getName() + " - valor: " + field.get(oficina));
        }
        while (seguir) {
            // Preguntamos el campo que se desea modificar
            System.out.println("Indique el numero del campo que desea modificar (si desea salir, indique cualquier otro número):");
            int campo = scanner.nextInt();
            // Si el campo es mayor que la última opción, salimos del método
            if(campo > listaCampos.length) {
                seguir = false;
            }
            // Preguntamos el nuevo valor
            scanner.nextLine();
            System.out.println("Que valor le desea dar al campo?");
            String valor = scanner.nextLine();
            switch (campo) {
                case 1:
                    System.out.println("El código de la oficina no se puede modificar");
                    break;
                case 2:
                    oficina.setCiudad(valor.isEmpty() ? oficina.getCiudad() : valor);
                    break;
                case 3:
                    oficina.setPais(valor.isEmpty() ? oficina.getPais() : valor);
                    break;
                case 4:
                    oficina.setRegion(valor.isEmpty() ? oficina.getRegion() : valor);
                    break;
                case 5:
                    oficina.setCodigoPostal(valor.isEmpty() ? oficina.getCodigoPostal() : valor);
                    break;
                case 6:
                    oficina.setTelefono(valor.isEmpty() ? oficina.getTelefono() : valor);
                    break;
                case 7:
                    oficina.setLineaDireccion1(valor.isEmpty() ? oficina.getLineaDireccion1() : valor);
                    break;
                case 8:
                    oficina.setLineaDireccion2(valor.isEmpty() ? oficina.getLineaDireccion2() : valor);
                default:
                    break;
            }
        }
        repo.actualizarOficina(oficina);
    }
}
