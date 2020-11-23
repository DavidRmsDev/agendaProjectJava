package controlador;

import br.com.supremeforever.suprememdiwindow.MDICanvas;
import br.com.supremeforever.suprememdiwindow.MDIWindow;
import static java.lang.System.exit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Metodo;
import modelo.conexion.Conexion;

public class Inicio extends Application {

    public static int user = 0;
    private MDIWindow mDIWindow;
    String url = "http://laweb.com/imagen1.jpg";//"/img/agendaPortada.jpg";
    AnchorPane root;
    private MDICanvas canvas;
    private Menu menu1, menu2, menu3, menu4, menu5;

    @Override
    public void start(Stage stage) throws Exception {
        canvas = new MDICanvas();
        canvas.setPrefSize(740, 830);
        
        MenuBar menuBar = new MenuBar();

        // Login/Register
        menu1 = new Menu("Login");
        MenuItem menuItem10 = new MenuItem("Registrarse...");
        menu1.getItems().add(menuItem10);
        MenuItem menuItem11 = new MenuItem("Iniciar sesión...");
        menu1.getItems().add(menuItem11);
        menuBar.getMenus().add(menu1);

        //Contactos
        menu2 = new Menu("Contactos");
        MenuItem menuItem20 = new MenuItem("Ver Contactos");
        menu2.getItems().add(menuItem20);
        menuBar.getMenus().add(menu2);
        menu2.setDisable(true);

        //Notas
        menu3 = new Menu("Notas");
        MenuItem menuItem30 = new MenuItem("Ver Notas");
        menu3.getItems().add(menuItem30);
        menuBar.getMenus().add(menu3);
        menu3.setDisable(true);

        //Recordatorios
        menu4 = new Menu("Recordatorios");
        MenuItem menuItem40 = new MenuItem("Ver Recordatorios");
        menu4.getItems().add(menuItem40);
        menuBar.getMenus().add(menu4);
        menu4.setDisable(true);

        //Desconectar
        menu5 = new Menu("Cerrar Sesión");
        MenuItem menuItem50 = new MenuItem("Desconectar...");
        menu5.getItems().add(menuItem50);
        menuBar.getMenus().add(menu5);
        menu5.setDisable(true);

        new Metodo().cargarLoader(this);

        //EVENTOS EN LAS OPCIONES DE MENÚ.
        //Opción 1.1 de Login
        menuItem11.setOnAction(e -> {
            new Metodo().formularioFXML("FXMLInicio.fxml", "INICIAR_SESIÓN");
        });

        //Opcion 1.2 de Register
        menuItem10.setOnAction(e -> {
            new Metodo().formularioFXML("FXMLRegistro.fxml", "REGISTRARSE");
        });

        //Opcion 2 de Contactos
        menuItem20.setOnAction(e -> {
            new Metodo().formularioFXML("FXMLContactos.fxml", "EVENTOS PROPIOS");
        });

        //Opcion 3 de Notas
        menuItem30.setOnAction(e -> {
            new Metodo().formularioFXML("FXMLNotas.fxml", "EVENTOS");
        });

        //Opcion 4 de Recordatorios
        menuItem40.setOnAction(e -> {
            new Metodo().formularioFXML("FXMLRecordatorio.fxml", "EVENTOS");
        });

        //Opcion 2 de desconectar
        menuItem50.setOnAction(e -> {
            desconectar();
        });

        ////***********************************////////
        BackgroundImage myBI,myBI2;
        myBI = new BackgroundImage(new Image("img/agendaPortada.jpg",760,880,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        myBI2 = new BackgroundImage(new Image("img/fondoPapel.jpg",760,Double.MAX_VALUE,true,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        
        canvas.centerMdiWindow(mDIWindow);

        VBox box = new VBox(menuBar, canvas);
        AnchorPane.setBottomAnchor(box, -15d);
        AnchorPane.setTopAnchor(box, 0d);
        AnchorPane.setLeftAnchor(box, 0d);
        AnchorPane.setRightAnchor(box, 0d);
        AnchorPane pane = new AnchorPane(box);
        
        pane.setBackground(new Background(myBI));
        
        Scene scene = new Scene(pane);
        stage.setTitle("AGENDA");
        stage.setResizable(false);
        stage.getIcons().add(new Image("img/agendaPortada.jpg")); 
        stage.setScene(scene);
        
        stage.show();

    }

    public void desconectar() {
        user = 0;
        resetearMenus();
        new Metodo().cerrarVentanas();
    }

    public MDICanvas getCanvas() {
        return canvas;
    }

    public void resetearMenus() {
        this.menu1.setDisable(false);
        this.menu2.setDisable(true);
        this.menu3.setDisable(true);
        this.menu4.setDisable(true);
        this.menu5.setDisable(true);

    }

    public void activarMenus() {
        this.menu1.setDisable(true);
        this.menu2.setDisable(false);
        this.menu3.setDisable(false);
        this.menu4.setDisable(false);
        this.menu5.setDisable(false);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (new Conexion().comprobarConexion()) {
            launch(args);
        } else {
            exit(0);
        }

    }

}
