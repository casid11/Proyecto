/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author casid
 */
public class Almacenes {
    SimpleStringProperty NombreAlmacen;
    SimpleStringProperty NombreOficina;
    public Almacenes(String NombreAlmacen, String NombreOficina) {
       this.NombreAlmacen = new SimpleStringProperty(NombreAlmacen);
       this.NombreOficina = new SimpleStringProperty(NombreOficina);
    }
    public String getNombreAlmacen(){
       return NombreAlmacen.get();
    }
    public void setNombreAlmacen(String NombreAlmacen){
       this.NombreAlmacen.set(NombreAlmacen);
    }
    
    public String getNombreOficina(){
       return NombreOficina.get();
    }
    public void setNombreOficina(String NombreOficina){
       this.NombreOficina.set(NombreOficina);
    }
}
