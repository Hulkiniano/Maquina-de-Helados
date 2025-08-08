/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mh.DAO;

import com.mh.biz.Helado;
import com.mh.exceptions.NotValidPositionException;
import com.mh.exceptions.QuantityExceededException;
import java.util.*;

/**
 *
 * @author hulkiniano
 */
public interface HeladoInterfaz {
    public abstract void updateHelado(Helado h) throws Exception; // Hecho
    public abstract ArrayList<Helado> getListaHelados() throws Exception; //Hecho
    public abstract Helado getHeladoByPosicion(String posicion) throws Exception; // Hecho
}
