/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mh.DAO;

import com.mh.biz.Helado;
import com.mh.exceptions.NotValidPositionException;
import com.mh.exceptions.QuantityExceededException;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author hulkiniano
 */
public class HeladoImpl implements HeladoInterfaz, AutoCloseable {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ex) {
            System.exit(1);
        }
    }

    Connection con = null;

    public HeladoImpl() throws Exception {
        this.con = DriverManager.getConnection("jdbc:sqlite:./helados.db");
    }

    @Override
    public void updateHelado(Helado h) throws Exception {
        String sql = "Update helados set posicion = ?, nombre = ?, precio = ?, tipo = ?, cantidad = ? where posicion = ?";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setString(1, h.getPosicion());
            pstm.setString(2, h.getNombre());
            pstm.setDouble(3, h.getPrecio());
            pstm.setString(4, h.getTipo());
            pstm.setInt(5, h.getCantidad());
            pstm.setString(6, h.getPosicion());
            pstm.executeUpdate(); 
        }
    }

    @Override
    public ArrayList<Helado> getListaHelados() throws Exception {     
        ArrayList<Helado> l = new ArrayList<Helado>();
        String sql = "Select posicion, nombre, precio, tipo, cantidad from helados";
        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                l.add(new Helado(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {
            throw e;
        }
        return l;
    }



    @Override
    public Helado getHeladoByPosicion(String posicion) throws Exception {
        String sql = "Select posicion, nombre, precio, tipo, cantidad from helados where posicion = ? ";
        Helado h = null;
        ResultSet rs = null;
        try (PreparedStatement pstm = con.prepareStatement(sql);){
            pstm.setString(1, posicion);
            rs = pstm.executeQuery();
            if (rs.next()){
                h = new Helado(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5));
            }
        } catch(Exception e){
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return h;
    }

    @Override
    public void close() throws Exception {
        con.close();
    }

}
