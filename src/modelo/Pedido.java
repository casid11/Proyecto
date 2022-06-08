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
public class Pedido {
    SimpleStringProperty Herramienta;
    SimpleStringProperty Cantidad;
   public Pedido(String Herramienta, String Cantidad) {
      this.Herramienta = new SimpleStringProperty(Herramienta);
      this.Cantidad = new SimpleStringProperty(Cantidad);
   }
   
   public String getHerramienta(){
      return Herramienta.get();
   }
   public void setHerramienta(String Herramienta){
      this.Herramienta.set(Herramienta);
   }
   
   public String getCantidad(){
      return Cantidad.get();
   }
   public void setCantidad(String Cantidad){
      this.Cantidad.set(Cantidad);
   }
}
