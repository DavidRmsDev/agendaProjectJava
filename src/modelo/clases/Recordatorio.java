package modelo.clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recordatorio {

//Clase usada para definir propiedades y con ella poder acceder a los datos contenidos.
//La propiedad es de tipo String.
//Modelo de datos para los recordatorios que se visualizarán en el TableView.
    
    private final SimpleStringProperty id;
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty descripcion;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty hora;

    public Recordatorio(String id, String titulo, String descripcion, String fecha, String hora) {
//*****************************************************
//SETTERS, usados para acceder a los atributos.
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.fecha = new SimpleStringProperty(fecha);
        this.hora = new SimpleStringProperty(hora);

    }
//*****************************************************
//GETTERS, usados para acceder a los atributos.

    public String getid() {
        return id.get();
    }

    public String gettitulo() {
        return titulo.get();
    }

    public String getdescripcion() {
        return descripcion.get();
    }

    public String getfecha() {
        return fecha.get();
    }

    public String gethora() {
        return hora.get();
    }

//*********************************************
//Devolver los datos de las propiedades
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public StringProperty horaProperty() {
        return hora;
    }

}
