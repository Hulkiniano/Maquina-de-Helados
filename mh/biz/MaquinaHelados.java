/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mh.biz;

import com.mh.DAO.HeladoImpl;
import com.mh.DAO.VentaImpl;
import com.mh.exceptions.*;
import com.mh.utils.Utils;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author enriquenogal
 */
public class MaquinaHelados {

    private double monedero = 0;
    private double ingresos = 0;

    public ArrayList<Venta> listaVentas() throws Exception {
        try (VentaImpl vi = new VentaImpl()) {
            return vi.historialVentas();
        } catch (Exception e) {
            throw e;
        }
    }

    public Helado pedirHelado(String posicion) throws NotValidPositionException, QuantityExceededException, NotEnoughMoneyException, Exception {
        Helado h;
        try (HeladoImpl hi = new HeladoImpl(); VentaImpl vi = new VentaImpl()) {
            h = hi.getHeladoByPosicion(posicion);
            if (h == null) {
                throw new NotValidPositionException("Posición no válida");
            } else if (h.getCantidad() <= 0) {
                throw new QuantityExceededException("No quedan mas helados de este tipo. Pide otro");
            } else if (this.getMonedero() < h.getPrecio()) {
                throw new NotEnoughMoneyException("No tienes el dinero suficiente para pagar");
            } else {
                this.setMonedero(this.getMonedero() - h.getPrecio());
                h.setCantidad(h.getCantidad() - 1);
                this.setIngresos(this.getIngresos() + h.getPrecio());
                this.setIngresos(Utils.redondeoDosDecimales(this.getIngresos()));
                hi.updateHelado(h);
                vi.insertarVenta(new Venta("", h.getPosicion(), h.getNombre(), h.getPrecio(), h.getTipo(), 1));
            }
        } catch (Exception e) {
            throw e;
        }
        return h;
    }

    public ArrayList<Helado> listaHelados() throws Exception {
        try (HeladoImpl hi = new HeladoImpl()) {
            return hi.getListaHelados();
        } catch (Exception e) {
            throw e;
        }
    }

    public double getMonedero() {
        return monedero;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setMonedero(double monedero) {
        this.monedero = Utils.redondeoDosDecimales(monedero);
    }

    public void setIngresos(double ingresos) {
        this.ingresos = Utils.redondeoDosDecimales(ingresos);
    }

}
