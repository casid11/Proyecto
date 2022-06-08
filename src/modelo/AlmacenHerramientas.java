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
public class AlmacenHerramientas {
     SimpleStringProperty Almacen;
     SimpleStringProperty Cantidades;
   public AlmacenHerramientas(String NombreUsuario, String Cantidades) {
      this.Almacen = new SimpleStringProperty(NombreUsuario);
      this.Cantidades = new SimpleStringProperty(Cantidades);
   }
   
   public String getAlmacen(){
      return Almacen.get();
   }
   public void setAlmacen(String Almacen){
      this.Almacen.set(Almacen);
   }
   
   public String getCantidades(){
      return Cantidades.get();
   }
   public void setCantidades(String Cantidades){
      this.Cantidades.set(Cantidades);
   }
}

