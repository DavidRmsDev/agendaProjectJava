package modelo.clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacto {

//Clase usada para definir propiedades y con ella poder acceder a los datos contenidos.
//La propiedad es de tipo String.
//Modelo de datos para los Contactos que se visualizarán en el TableView.
    private final SimpleStringProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty apellidos;
    private final SimpleStringProperty telefono;
    private final SimpleStringProperty direccion;
    private final SimpleStringProperty email;

    public Contacto(String id, String nombre, String apellidos, String telefono, String direccion, String email) {
//*****************************************************
//SETTERS, usados para acceder a los atributos.
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.telefono = new SimpleStringProperty(telefono);
        this.direccion = new SimpleStringProperty(direccion);
        this.email = new SimpleStringProperty(email);
    }
//*****************************************************
//GETTERS, usados para acceder a los atributos.

    public String getid() {
        return id.get();
    }

    public String getnombre() {
        return nombre.get();
    }

    public String getapellidos() {
        return apellidos.get();
    }

    public String gettelefono() {
        return telefono.get();
    }

    public String getdireccion() {
        return direccion.get();
    }

    public String getemail() {
        return email.get();
    }

//*********************************************
//Devolver los datos de las propiedades
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty apellidosProperty() {
        return apellidos;
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public StringProperty emailProperty() {
        return email;
    }

}
