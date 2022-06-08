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
public class Herramientas {
     SimpleStringProperty Herramienta;
     SimpleStringProperty Cantidades;
   public Herramientas(String NombreUsuario, String Cantidades) {
      this.Herramienta = new SimpleStringProperty(NombreUsuario);
      this.Cantidades = new SimpleStringProperty(Cantidades);
   }
   
   public String getHerramienta(){
      return Herramienta.get();
   }
   public void setHerramienta(String Herramienta){
      this.Herramienta.set(Herramienta);
   }
   
   public String getCantidades(){
      return Cantidades.get();
   }
   public void setCantidades(String Cantidades){
      this.Cantidades.set(Cantidades);
   }
}
