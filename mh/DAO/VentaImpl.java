/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mh.DAO;

import com.mh.biz.Venta;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author hulkiniano
 */
public class VentaImpl implements VentaInterfaz, AutoCloseable{

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ex) {
            System.exit(1);
        }
    }
    
    Connection con = null;
    public VentaImpl() throws Exception {
        this.con = DriverManager.getConnection("jdbc:sqlite:./helados.db");
    }
    
    @Override
    public ArrayList<Venta> historialVentas() throws Exception {
        ArrayList<Venta> l = new ArrayList<Venta>();
        String sql = "Select fecha_hora, posicion, nombre, precio, tipo, cantidad from ventas ";
        ResultSet rs = null;
        try(PreparedStatement pstm = con.prepareStatement(sql);){
            rs = pstm.executeQuery();
            while(rs.next()) {
                l.add(new Venta(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getInt(6)));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return l;
    }

    @Override
    public void insertarVenta(Venta v) throws Exception {
        String sql = "Insert into ventas(fecha_hora, posicion, nombre, precio, tipo, cantidad) values(datetime('now','localtime'),?,?,?,?,?)";
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, v.getPosicion());
            pstm.setString(2, v.getNombre());
            pstm.setDouble(3, v.getPrecio());
            pstm.setString(4, v.getTipo());
            pstm.setInt(5, v.getCantidad());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        con.close();
    }
    
}
