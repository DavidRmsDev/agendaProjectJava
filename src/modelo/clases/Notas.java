package modelo.clases;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notas {
    
//Clase usada para definir propiedades y con ella poder acceder a los datos contenidos.
//La propiedad es de tipo String.
//Modelo de datos para las notas que se visualizarán en el TableView.

    private final SimpleStringProperty id;
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty nota;
    

    public Notas(String id, String titulo,String nota, String fecha ) {
//*****************************************************
//SETTERS, usados para acceder a los atributos.
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.fecha = new SimpleStringProperty(fecha);
        this.nota = new SimpleStringProperty(nota);
    }
//*****************************************************
//GETTERS, usados para acceder a los atributos.

    public String getid() {
        return id.get();
    }

    public String gettitulo() {
        return titulo.get();
    }

    public String getfecha() {
        return fecha.get();
    }

    public String getnota() {
        return nota.get();
    }

   
//*********************************************
//Devolver los datos de las propiedades

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public StringProperty notaProperty() {
        return nota;
    }
}
