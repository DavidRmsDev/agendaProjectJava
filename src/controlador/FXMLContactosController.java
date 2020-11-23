/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.mysql.MySQLContactoDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import modelo.Metodo;
import modelo.clases.Contacto;
import modelo.conexion.Conexion;

/**
 * FXML Controller class
 *
 * @author David
 */
public class FXMLContactosController implements Initializable {

    @FXML
    private TableView<Contacto> dataGridView1;
    @FXML
    private TextField campo_nombre;
    @FXML
    private TextField campo_apellidos;
    @FXML
    private TextField campo_telefono;
    @FXML
    private TextField campo_direccion;
    @FXML
    private TextField campo_email;
    @FXML
    private TableColumn<Contacto, String> columnaID;
    @FXML
    private TextField textFieldID;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        campo_nombre.requestFocus();

        //TableColumn<Contacto, String> columnaID = new TableColumn<>("ID");
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Contacto, String> nombre = new TableColumn<>("Nombre");
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Contacto, String> apellidos = new TableColumn<>("Apellidos");
        apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        TableColumn<Contacto, String> telefono = new TableColumn<>("Teléfono");
        telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Contacto, String> direccion = new TableColumn<>("Dirección");
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Contacto, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        dataGridView1.getColumns().addAll(nombre, apellidos, telefono, direccion, email);

        actualizarGrid();

    }

    private void actualizarGrid() {
        if (new Conexion().comprobarConexion()) {
            ArrayList<Contacto> lista = new MySQLContactoDAO().obtenerTodos();
            dataGridView1.getItems().clear();
            for (int i = 0; i < lista.size(); i++) {
                dataGridView1.getItems().addAll(lista.get(i));
            }
            //Visualizar la TableView.
            dataGridView1.setVisible(true);
            //Redimensiona cada columna al tamaño de sus datos.
            dataGridView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
    }

    private boolean comprobarRellenos() {
        boolean vacio = true;
        if (campo_nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir un nombre");
            vacio = false;
        } else if (campo_telefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir un teléfono");
            vacio = false;
        } else if (!patronTel(campo_telefono.getText())) {
            JOptionPane.showMessageDialog(null, "Formato de teléfono incorrecto, deben ser 9 dígitos sin espacios");
            vacio = false;
        } else if (!campo_email.getText().isEmpty()) {
            if (!patronEmail(campo_email.getText())) {
                JOptionPane.showMessageDialog(null, "Formato de email incorrecto");
                vacio = false;
            }
        } else if (!comprobarLongitud()) {
            vacio = false;
        }

        return vacio;
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        vaciarCampos();

        if ((!dataGridView1.getItems().isEmpty()) && (dataGridView1.getSelectionModel().getSelectedItem() != null)) {

            Contacto c = dataGridView1.getSelectionModel().getSelectedItem();
            campo_nombre.setText(c.getnombre());
            campo_apellidos.setText(c.getapellidos());
            campo_telefono.setText(c.gettelefono());
            campo_direccion.setText(c.getdireccion());
            campo_email.setText(c.getemail());
            textFieldID.setText(c.getid());
        }
    }

    private Contacto crearContacto() {
        return new Contacto(textFieldID.getText(), campo_nombre.getText(), campo_apellidos.getText(), campo_telefono.getText(), campo_direccion.getText(), campo_email.getText());
    }

    @FXML
    private void insertar(ActionEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (comprobarRellenos()) {
                Contacto c = crearContacto();
                new MySQLContactoDAO().insertar(c);
                vaciarCampos();
                actualizarGrid();
            }
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (comprobarRellenos()) {
                if (dataGridView1.getSelectionModel().getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.", "Error.", JOptionPane.ERROR_MESSAGE);
                } else {
                    Contacto c = crearContacto();
                    new MySQLContactoDAO().modificar(c);
                    actualizarGrid();
                    vaciarCampos();
                    dataGridView1.getSelectionModel().clearSelection();
                }
            }
        }
    }

    @FXML
    private void borrar(KeyEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (event.getCode() == KeyCode.DELETE && dataGridView1.getSelectionModel().getSelectedItem() != null) {
                Contacto c = dataGridView1.getSelectionModel().getSelectedItem();
                if (new Metodo().borrar()) {
                    new MySQLContactoDAO().eliminar(c);
                    actualizarGrid();
                    vaciarCampos();
                    dataGridView1.getSelectionModel().clearSelection();
                }
            }
        }
    }

    private boolean patronTel(String tel) {
        boolean valido = true;
        String patron = "[0-9]{9}";
        Pattern pattern = Pattern.compile(patron);
        try {
            Matcher matcher = pattern.matcher(tel);
            valido = matcher.matches();

        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }

    private boolean patronEmail(String email) {
        boolean valido = true;
        String patron = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(patron);
        try {
            Matcher matcher = pattern.matcher(email);
            valido = matcher.matches();

        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }

    private void vaciarCampos() {
        campo_nombre.setText("");
        campo_apellidos.setText("");
        campo_telefono.setText("");
        campo_direccion.setText("");
        campo_email.setText("");
        textFieldID.setText("");
    }

    private boolean comprobarLongitud() {
        boolean valido = true;

        if (campo_nombre.getText().length() > 20) {
            JOptionPane.showMessageDialog(null, "Longitud del nombre no permitida. Max 20 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (campo_apellidos.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Longitud del apellido no permitida. Max 50 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (campo_direccion.getText().length() > 80) {
            JOptionPane.showMessageDialog(null, "Longitud de la dirección no permitida. Max 80 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (campo_email.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Longitud de email no permitida. Max 50 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        }

        return valido;
    }

}
