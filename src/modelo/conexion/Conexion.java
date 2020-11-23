package modelo.conexion;

import dao.conexion.XMLDAOConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.utilidades.Logcat;

public class Conexion {

    public Connection conexion;

    public Connection obtenerConexion() throws SQLException {
        ConexionXML con = new XMLDAOConexion().datosConexion();
        final String SSL = "?useSSL=false";
        String basedatos = con.getDir() + SSL;
        String usuario = con.getUser();
        String password = con.getPassword();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conexion = DriverManager.getConnection(basedatos, usuario, password);

        } catch (ClassNotFoundException e) {
            new Logcat("Conexion.obtenerConexion()" + "\n" + e.toString() + "\n" + e.getStackTrace());
        }
        return conexion;
    }

    public void desconectar(Connection conexion) {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            new Logcat("Conexion.desconectar()" + "\n" + e.toString() + "\n" + e.getStackTrace());
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean comprobarConexion() {
        boolean valido = false;
        try {
            Connection c = obtenerConexion();
            if (c != null) {
                valido = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }

        return valido;
    }

}
