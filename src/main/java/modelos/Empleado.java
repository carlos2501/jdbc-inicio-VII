package modelos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
        private int codigoEmpleado;
        private String nombre;
        private String apellido1;
        private String apellido2;
        private String extension;
        private String email;
        private String codigoOficina;
        private Integer codigoJefe; // Puede ser null
        private String puesto;

        public String toString() {
            return "Empleado [codigoEmpleado=" + codigoEmpleado + ", nombre=" + nombre + ", apellido1=" + apellido1 +
                    ", apellido2=" + apellido2 + "]";
        }


}
