/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mh.DAO;

import com.mh.biz.*;
import java.util.ArrayList;

/**
 *
 * @author hulkiniano
 */
public interface VentaInterfaz {
    public ArrayList<Venta> historialVentas() throws Exception; //Hecho
    public void insertarVenta(Venta v) throws Exception; //Hecho
}
