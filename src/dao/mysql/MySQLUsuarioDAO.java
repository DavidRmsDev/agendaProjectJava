/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.mysql;

import dao.UsuarioDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.clases.Usuario;
import modelo.conexion.Conexion;
import modelo.utilidades.Logcat;

/**
 *
 * @author David
 */
public class MySQLUsuarioDAO implements UsuarioDAO {

    private Connection conexion;

    public MySQLUsuarioDAO() {
        try {
            conexion = new Conexion().obtenerConexion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor MySQLUsuarioDAO", "Error de conexión", JOptionPane.ERROR_MESSAGE);
            new Logcat(ex.toString() + "\n" + ex.getStackTrace());
        }
    }

    @Override
    public void insertar(Usuario dato) {
        CallableStatement cst = null;

        try {
            cst = conexion.prepareCall("{call insertaUsuario(?,?)}");
            cst.setString(1, dato.getNickname());
            cst.setString(2, dato.getPasssword());

            if (cst.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido dar de alta al usuario", "Nickname ya registrado", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "Operación", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido dar de alta al usuario", "Nickname ya registrado", JOptionPane.ERROR_MESSAGE);
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

    public boolean comprobarNombreUsuario(String nick) {
        boolean existe = false;
        CallableStatement cst = null;
        ResultSet rs;

        try {
            cst = conexion.prepareCall("{call comprobarNombreUsuario(?)}");
            cst.setString(1, nick);
            rs = cst.executeQuery();
            while (rs.next()) {
                existe = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Usuario incorrecto", "Error de login", JOptionPane.ERROR_MESSAGE);
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
        return existe;
    }

    public int devolverUsuario(Usuario u) {
        int user = 0;
        CallableStatement cst = null;
        ResultSet rs;

        try {
            cst = conexion.prepareCall("{call seleccionaUsuario(?,?)}");
            cst.setString(1, u.getNickname());
            cst.setString(2, u.getPasssword());
            rs = cst.executeQuery();
            while (rs.next()) {
                user = rs.getInt(1);
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
        return user;
    }

    @Override
    public void modificar(Usuario dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Usuario dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> obtenerTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
