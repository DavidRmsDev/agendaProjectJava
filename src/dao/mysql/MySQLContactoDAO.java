/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.mysql;

import static controlador.Inicio.user;
import dao.ContactoDAO;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.clases.Contacto;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.conexion.Conexion;
import modelo.utilidades.Logcat;

/**
 *
 * @author David
 */
public class MySQLContactoDAO implements ContactoDAO {

    private Connection conexion;

    public MySQLContactoDAO() {
        try {
            conexion = new Conexion().obtenerConexion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor", "Error de conexión", JOptionPane.ERROR_MESSAGE);
            new Logcat(ex.toString() + "\n" + ex.getStackTrace());
        }
    }

    @Override
    public void insertar(Contacto dato) {
        CallableStatement cst = null;
        try {
            cst = conexion.prepareCall("{call insertaContacto(?,?,?,?,?,?)}");
            cst.setInt(1, user);
            cst.setString(2, dato.getnombre());
            cst.setString(3, dato.getapellidos());
            cst.setInt(4, Integer.parseInt(dato.gettelefono()));
            cst.setString(5, dato.getdireccion());
            cst.setString(6, dato.getemail());
            if (cst.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido insertar el contacto, telefono duplicado", "El telefono ya existe", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente", "Operación", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido insertar el contacto, telefono duplicado", "El telefono ya existe", JOptionPane.ERROR_MESSAGE);
            new Logcat(e.toString() + "\n" + e.getStackTrace());
        } finally {

            if (cst != null) {
                try {
                    cst.close();
                    this.conexion.close();
                } catch (SQLException e) {
                    new Logcat(e.toString() + "\n" + e.getStackTrace());
                }
            }
        }
    }

    @Override
    public void modificar(Contacto dato) {
        CallableStatement cst = null;
        try {
            cst = conexion.prepareCall("{call modificaContacto(?,?,?,?,?,?)}");
            cst.setInt(1, Integer.parseInt(dato.getid()));
            cst.setString(2, dato.getnombre());
            cst.setString(3, dato.getapellidos());
            cst.setInt(4, Integer.parseInt(dato.gettelefono()));
            cst.setString(5, dato.getdireccion());
            cst.setString(6, dato.getemail());

            if (cst.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente", "Operación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el registro", "Operación", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido modificar el registro", "El telefono ya existe", JOptionPane.ERROR_MESSAGE);
            new Logcat(e.toString() + "\n" + e.getStackTrace());
        } finally {

            if (cst != null) {
                try {
                    cst.close();
                    this.conexion.close();
                } catch (SQLException e) {
                    System.out.format("%s\n", "Error en SQL");
                    new Logcat(e.toString() + "\n" + e.getStackTrace());
                }
            }
        }
    }

    @Override
    public void eliminar(Contacto dato) {
        CallableStatement cst = null;
        try {
            cst = conexion.prepareCall("{call borrarContacto(?)}");
            cst.setInt(1, Integer.parseInt(dato.getid()));

            if (cst.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Registro borrado correctamente", "Operación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido borrar el registro", "Operación", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido borrar el registro", "Error de conexión", JOptionPane.ERROR_MESSAGE);
            new Logcat(e.toString() + "\n" + e.getStackTrace());
        } finally {

            if (cst != null) {
                try {
                    cst.close();
                    this.conexion.close();
                } catch (SQLException e) {
                    System.out.format("%s\n", "Error en SQL");
                    new Logcat(e.toString() + "\n" + e.getStackTrace());
                }
            }
        }
    }

    @Override
    public ArrayList<Contacto> obtenerTodos() {
        ArrayList<Contacto> lista = new ArrayList<>();
        CallableStatement cst = null;
        ResultSet rs;

        try {
            cst = conexion.prepareCall("{call seleccionaContacto(?)}");
            cst.setInt(1, user);
            rs = cst.executeQuery();
            while (rs.next()) {
                lista.add(new Contacto(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), Integer.toString(rs.getInt(4)), rs.getString(5), rs.getString(6)));
            }

        } catch (SQLException e) {

            new Logcat(e.toString() + "\n" + e.getStackTrace());
        } finally {

            if (cst != null) {
                try {
                    cst.close();
                    this.conexion.close();
                } catch (SQLException e) {
                    new Logcat(e.toString() + "\n" + e.getStackTrace());
                }
            }
        }

        return lista;
    }

}
