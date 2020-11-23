/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import dao.mysql.MySQLRecordatorioDAO;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import modelo.clases.Recordatorio;
import modelo.conexion.Conexion;

/**
 * FXML Controller class
 *
 * @author David
 */
public class FXMLRecordatorioController implements Initializable {

    @FXML
    private TableView<Recordatorio> dataGridView1;
    @FXML
    private TableColumn<Recordatorio, String> columnaID;
    @FXML
    private TextField campo_titulo;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextArea campo_descripcion;
    @FXML
    private TableColumn<Recordatorio, String> columnaDescripcion;
    @FXML
    private JFXDatePicker fecha;
    @FXML
    private JFXTimePicker hora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        campo_titulo.requestFocus();

        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Recordatorio, String> titulo = new TableColumn<>("Título");
        titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<Recordatorio, String>("descripcion"));

        TableColumn<Recordatorio, String> fecha = new TableColumn<>("Fecha");
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Recordatorio, String> hora = new TableColumn<>("Hora");
        hora.setCellValueFactory(new PropertyValueFactory<>("hora"));

        dataGridView1.getColumns().addAll(titulo, fecha, hora);

        actualizarGrid();
        vaciarCampos();
    }

    private void actualizarGrid() {
        if (new Conexion().comprobarConexion()) {
            ArrayList<Recordatorio> lista = new MySQLRecordatorioDAO().obtenerTodos();
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
        } else if (fecha.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Debe introducir una fecha");
            vacio = false;
        } else if (hora.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Debe introducir un horario");
            vacio = false;
        } else if (!comprobarLongitud()) {
            vacio = false;
        }
        return vacio;
    }

    private void vaciarCampos() {
        campo_titulo.setText("");
        fecha.setValue(null);
        hora.setValue(null);
        campo_descripcion.setText("");
        textFieldID.setText("");
    }

    private void formatearFecha(String datosFecha) {

        String[] dfecha = datosFecha.split("-");
        fecha.setValue(LocalDate.of(Integer.parseInt(dfecha[0]), Integer.parseInt(dfecha[1]), Integer.parseInt(dfecha[2])));
    }

    private void formatearHora(String datosHora) {

        String[] dhora = datosHora.split(":");
        hora.setValue(LocalTime.of(Integer.parseInt(dhora[0]), Integer.parseInt(dhora[1])));
    }

    private Recordatorio crearRecordatorio() {
        return new Recordatorio(textFieldID.getText(), campo_titulo.getText(), campo_descripcion.getText(), fecha.getValue().toString(), hora.getValue().toString());
    }

    @FXML
    private void borrar(KeyEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (event.getCode() == KeyCode.DELETE && dataGridView1.getSelectionModel().getSelectedItem() != null) {
                Recordatorio n = dataGridView1.getSelectionModel().getSelectedItem();
                if (new Metodo().borrar()) {
                    new MySQLRecordatorioDAO().eliminar(n);
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

            Recordatorio n = dataGridView1.getSelectionModel().getSelectedItem();
            campo_titulo.setText(n.gettitulo());
            campo_descripcion.setText(n.getdescripcion());
            formatearFecha(n.getfecha());
            formatearHora(n.gethora());
            textFieldID.setText(n.getid());
        }
    }

    @FXML
    private void insertar(ActionEvent event) {
        if (new Conexion().comprobarConexion()) {
            if (comprobarRellenos()) {
                Recordatorio r = new Recordatorio(null, campo_titulo.getText(), campo_descripcion.getText(), fecha.getValue().toString(), hora.getValue().toString());
                new MySQLRecordatorioDAO().insertar(r);
                vaciarCampos();
                actualizarGrid();
            }
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        if (new Conexion().comprobarConexion()) {

            if (!comprobarRellenos()) {
            } else if (dataGridView1.getSelectionModel().getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.", "Error.", JOptionPane.ERROR_MESSAGE);
            } else {

                Recordatorio n = crearRecordatorio();
                new MySQLRecordatorioDAO().modificar(n);
                actualizarGrid();
                vaciarCampos();
                dataGridView1.getSelectionModel().clearSelection();
            }
        }
    }

    private boolean comprobarLongitud() {
        boolean valido = true;

        if (campo_titulo.getText().length() > 40) {
            JOptionPane.showMessageDialog(null, "Longitud del título no permitida. Max 40 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        } else if (campo_descripcion.getText().length() > 255) {
            JOptionPane.showMessageDialog(null, "Longitud de la descripción no permitida. Max 255 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            valido = false;
        }

        return valido;
    }

}
