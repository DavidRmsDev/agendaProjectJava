/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.conexion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import modelo.conexion.ConexionXML;
import modelo.utilidades.Logcat;

/**
 *
 * @author David
 */
public class XMLDAOConexion implements DAOConexion<ConexionXML> {

    @Override
    public ConexionXML datosConexion() {
        ConexionXML con = new ConexionXML();

        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader xmlsr = null;
        try {
            xmlsr = xmlif.createXMLStreamReader(new FileReader("conexion.xml"));

            String tag = null;
            int eventType;
            while (xmlsr.hasNext()) {
                eventType = xmlsr.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        tag = xmlsr.getLocalName();
                        if (tag.equals("dir")) {
                            con.setDir(xmlsr.getElementText());
                        }
                        if (tag.equals("user")) {
                            con.setUser(xmlsr.getElementText());
                        }
                        if (tag.equals("password")) {
                            con.setPassword(xmlsr.getElementText());
                        }
                        break;
                    case XMLEvent.END_DOCUMENT:

                        break;
                }
            }
        } catch (XMLStreamException e) {
            System.out.format("%s\n", "Error en el formato de los datos del XML");
            new Logcat(e.toString() + "\n" + e.getStackTrace());
        } catch (FileNotFoundException e) {
            System.out.format("%s\n", "No se ha encontrado XML con datos de conexión");
            new Logcat(e.toString() + "\n" + e.getStackTrace());
        }
        return con;
    }
}
