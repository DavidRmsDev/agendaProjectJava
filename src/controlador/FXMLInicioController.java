/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.mysql.MySQLUsuarioDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import modelo.Metodo;
import modelo.clases.Usuario;
import modelo.conexion.Conexion;

/**
 * FXML Controller class
 *
 * @author David
 */
public class FXMLInicioController implements Initializable {

    @FXML
    private TextField usuIniTextField;
    @FXML
    private TextField passIniTextField;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void iniciar(ActionEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (comprobarRellenos()) {
                if (comprobarUsuario()) {
                    Usuario u = crearUsuario();
                    if ((Inicio.user = new MySQLUsuarioDAO().devolverUsuario(u)) != 0) {
                        JOptionPane.showMessageDialog(null, "Usuario logeado con éxito");
                        Metodo.loader.activarMenus();
                        new Metodo().cerrarVentanas();
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario incorrecto", "Error de login", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean comprobarRellenos() {
        boolean vacio = true;
        if (usuIniTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir un usuario");
            vacio = false;
        } else if (passIniTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir una contraseña");
            vacio = false;
        } else if (!comprobarLongitud()) {
            vacio = false;
        }
        return vacio;
    }

    private boolean comprobarUsuario() {
        boolean existe = new MySQLUsuarioDAO().comprobarNombreUsuario(usuIniTextField.getText());
        return existe;
    }

    private boolean comprobarLongitud() {
        boolean valido = true;

        if (usuIniTextField.getText().length() > 25) {
            JOptionPane.showMessageDialog(null, "Longitud del nickname no permitida. Max 25 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (passIniTextField.getText().length() > 25) {
            JOptionPane.showMessageDialog(null, "Longitud de la contraseña no permitida. Max 25 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        }

        return valido;
    }

    private Usuario crearUsuario() {
        return new Usuario(usuIniTextField.getText(), passIniTextField.getText());
    }

}
