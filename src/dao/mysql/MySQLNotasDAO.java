/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.mysql;

import static controlador.Inicio.user;
import dao.NotasDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.clases.Notas;
import modelo.conexion.Conexion;
import modelo.utilidades.Logcat;

/**
 *
 * @author David
 */
public class MySQLNotasDAO implements NotasDAO {

    private Connection conexion;

    public MySQLNotasDAO() {
        try {
            conexion = new Conexion().obtenerConexion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor", "Error de conexión", JOptionPane.ERROR_MESSAGE);
            new Logcat(ex.toString() + "\n" + ex.getStackTrace());
        }
    }

    @Override
    public void insertar(Notas dato) {
        CallableStatement cst = null;
        try {
            cst = conexion.prepareCall("{call insertaNota(?,?,?,?)}");
            cst.setInt(1, user);
            cst.setString(2, dato.gettitulo());
            cst.setString(3, dato.getnota());
            cst.setString(4, dato.getfecha());

            if (cst.executeUpdate() == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido insertar la nota", "Error al insertar", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente", "Operación", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido insertar la nota", "Error al insertar", JOptionPane.ERROR_MESSAGE);
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
    public void modificar(Notas dato) {
        CallableStatement cst = null;
        try {
            cst = conexion.prepareCall("{call modificaNota(?,?,?,?)}");
            cst.setInt(1, Integer.parseInt(dato.getid()));
            cst.setString(2, dato.gettitulo());
            cst.setString(3, dato.getnota());
            cst.setString(4, dato.getfecha());

            if (cst.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Registro modificado correctamente", "Operación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el registro", "Operación", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido modificar el registro", "Error de conexión", JOptionPane.ERROR_MESSAGE);
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
    public void eliminar(Notas dato) {
        CallableStatement cst = null;
        try {
            cst = conexion.prepareCall("{call borrarNota(?)}");
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
    public ArrayList<Notas> obtenerTodos() {
        ArrayList<Notas> lista = new ArrayList<>();
        CallableStatement cst = null;
        ResultSet rs;

        try {
            cst = conexion.prepareCall("{call seleccionaNota(?)}");
            cst.setInt(1, user);
            rs = cst.executeQuery();
            while (rs.next()) {
                lista.add(new Notas(Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4)));
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
