/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import br.com.supremeforever.suprememdiwindow.MDIWindow;
import controlador.Inicio;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Metodo {

    public static Inicio loader;

    public void formularioFXML(String formulariofxml, String nombre) {
        try {

            String url = "http://laweb.com/imagen1.jpg";//"/img/agendaPortada.jpg";
            cerrarVentanas();

            AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/" + formulariofxml));

            MDIWindow mDIWindow = new MDIWindow("Ventana", new ImageView(url), nombre, root);
            mDIWindow.setMaxSize(740, 850);
            loader.getCanvas().addMDIWindow(mDIWindow);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cerrarVentanas() {
        try {

            loader.getCanvas().removeMDIWindow("Ventana");

        } catch (Exception e) {
        }
    }

    public void cargarLoader(Inicio loader) {
        this.loader = loader;
    }

    public boolean borrar() {
        boolean borrar = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Borrar Registro");
        alert.setHeaderText("Borrar Registro");
        alert.setContentText("¿Seguro de eliminar el registro?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            borrar = true;
        }
        return borrar;
    }

}
