import controladores.MenuPpal;

import java.sql.SQLException;

public class mvc {

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        MenuPpal menuPrincipal = new MenuPpal();

        menuPrincipal.presentarMenuPpal();
    }
}
