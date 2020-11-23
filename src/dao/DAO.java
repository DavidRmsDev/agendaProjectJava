/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author David
 * @param <T>
 * @param <K>
 */
public interface DAO<T, K> {

    //Inserta el objeto pasado por parametros a la BBDD
    public void insertar(T dato);
    //Modifica el objeto pasado por parametros a la BBDD

    public void modificar(T dato);
    //Elimina el objeto pasado por parametros a la BBDD

    public void eliminar(T dato);
    //Muestra todos los objetos T de la BBDD

    public ArrayList<T> obtenerTodos();
    //Muestra el objeto T de la BBDD cuya id sea la pasada por par�metros

}
