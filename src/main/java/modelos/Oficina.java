package modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Oficina {
    private String codigoOficina;
    private String ciudad;
    private String pais;
    private String region;
    private String codigoPostal;
    private String telefono;
    private String lineaDireccion1;
    private String lineaDireccion2;

    public String toString(){
        return "Oficina [" + codigoOficina + ", " + ciudad + ", " + pais + ", " + region + ", " +
                codigoPostal + ", " + telefono + ", " + lineaDireccion1 + ", " + lineaDireccion2 + "]";
    }
}
