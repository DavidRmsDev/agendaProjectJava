/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.mysql.MySQLNotasDAO;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import modelo.Metodo;
import modelo.clases.Notas;
import modelo.conexion.Conexion;

/**
 * FXML Controller class
 *
 * @author David
 */
public class FXMLNotasController implements Initializable {

    @FXML
    private TableView<Notas> dataGridView1;
    @FXML
    private TableColumn<Notas, String> columnaID;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField campo_titulo;
    @FXML
    private TextField campo_fecha;
    @FXML
    private TextArea campo_nota;
    @FXML
    private TableColumn<Notas, String> columnaNotas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        campo_titulo.requestFocus();

        //TableColumn<Contacto, String> columnaID = new TableColumn<>("ID");
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Notas, String> titulo = new TableColumn<>("Título");
        titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        // TableColumn<Notas, String> notas = new TableColumn<>("Notas");
        columnaNotas.setCellValueFactory(new PropertyValueFactory<>("notas"));

        TableColumn<Notas, String> fecha = new TableColumn<>("Fecha");
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        dataGridView1.getColumns().addAll(titulo, fecha);

        actualizarGrid();
        vaciarCampos();

    }

    private void actualizarGrid() {
        if (new Conexion().comprobarConexion()) {
            ArrayList<Notas> lista = new MySQLNotasDAO().obtenerTodos();
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
        if (campo_titulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe introducir un título");
            vacio = false;
        } else if (!comprobarLongitud()) {
            vacio = false;
        }
        return vacio;
    }

    private void vaciarCampos() {
        campo_titulo.setText("");
        campo_fecha.setText("");
        campo_nota.setText("");
        textFieldID.setText("");
    }

    private Notas crearNotas() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM HH:mm:ss yyyy");
        String fecha =dateFormat.format(new Date());
        return new Notas(textFieldID.getText(), campo_titulo.getText(), campo_nota.getText(), fecha);
    }

    @FXML
    private void borrar(KeyEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (event.getCode() == KeyCode.DELETE && dataGridView1.getSelectionModel().getSelectedItem() != null) {
                Notas n = dataGridView1.getSelectionModel().getSelectedItem();
                if (new Metodo().borrar()) {
                    new MySQLNotasDAO().eliminar(n);
                    actualizarGrid();
                    vaciarCampos();
                    dataGridView1.getSelectionModel().clearSelection();
                }
            }
        }
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        vaciarCampos();
        if ((!dataGridView1.getItems().isEmpty()) && (dataGridView1.getSelectionModel().getSelectedItem() != null)) {

            Notas n = dataGridView1.getSelectionModel().getSelectedItem();
            campo_titulo.setText(n.gettitulo());
            campo_nota.setText(n.getnota());
            campo_fecha.setText(n.getfecha());
            textFieldID.setText(n.getid());
        }
    }

    @FXML
    private void insertar(ActionEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (comprobarRellenos()) {

                Notas n = crearNotas();
                new MySQLNotasDAO().insertar(n);
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

                    Notas n = crearNotas();
                    new MySQLNotasDAO().modificar(n);
                    actualizarGrid();
                    vaciarCampos();
                    dataGridView1.getSelectionModel().clearSelection();
                }
            }
        }
    }

    private boolean comprobarLongitud() {
        boolean valido = true;

        if (campo_titulo.getText().length() > 15) {
            JOptionPane.showMessageDialog(null, "Longitud del título no permitida. Max 15 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (campo_nota.getText().length() > 155) {
            JOptionPane.showMessageDialog(null, "Longitud de la nota no permitida. Max 155 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (campo_fecha.getText().length() > 150) {
            JOptionPane.showMessageDialog(null, "Longitud de la fecha no permitida. Max 150 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        }

        return valido;
    }

}
