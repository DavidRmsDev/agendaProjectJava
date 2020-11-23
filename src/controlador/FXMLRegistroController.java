/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.mysql.MySQLUsuarioDAO;
import java.net.URL;
import java.sql.SQLException;
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
public class FXMLRegistroController implements Initializable {

    @FXML
    private TextField usuRegTextField;
    @FXML
    private TextField passRegTextField;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vaciarCampos();

    }

    private boolean comprobarRellenos() {
        boolean vacio = true;
        if (usuRegTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir un usuario");
            vacio = false;
        } else if (passRegTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir una contraseña");
            vacio = false;
        } else if (!comprobarLongitud()) {
            vacio = false;
        }
        return vacio;
    }

    private void vaciarCampos() {
        usuRegTextField.setText("");
        passRegTextField.setText("");
    }

    private Usuario crearUsuario() {
        return new Usuario(usuRegTextField.getText(), passRegTextField.getText());
    }

    @FXML
    private void registro(ActionEvent event) {
        
            if (new Conexion().comprobarConexion()) {
                if (comprobarRellenos()) {
                    Usuario u = crearUsuario();
                    if (!comprobarUsuario()) {
                        new MySQLUsuarioDAO().insertar(u);
                        vaciarCampos();
                        JOptionPane.showMessageDialog(null, "Inicie sesión");
                        new Metodo().formularioFXML("FXMLInicio.fxml", "INICIAR_SESIÓN");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible, ya está registrado");
                    }
                }
            }
        
    }

    private boolean comprobarLongitud() {
        boolean valido = true;

        if (usuRegTextField.getText().length() > 25) {
            JOptionPane.showMessageDialog(null, "Longitud del nickname no permitida. Max 25 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (passRegTextField.getText().length() > 25) {
            JOptionPane.showMessageDialog(null, "Longitud de la contraseña no permitida. Max 25 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        }

        return valido;
    }

    private boolean comprobarUsuario() {
        boolean existe = new MySQLUsuarioDAO().comprobarNombreUsuario(usuRegTextField.getText());
        return existe;
    }
}
