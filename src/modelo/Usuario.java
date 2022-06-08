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
public class Usuario {
   SimpleStringProperty NombreUsuario;
   SimpleStringProperty Tipo;
   public Usuario(String NombreUsuario, String Tipo) {
      this.NombreUsuario = new SimpleStringProperty(NombreUsuario);
      this.Tipo = new SimpleStringProperty(Tipo);
   }
   public String getNombreUsuario(){
      return NombreUsuario.get();
   }
   public void setDNI(String NombreUsuario){
      this.NombreUsuario.set(NombreUsuario);
   }
   public String getTipo(){
      return Tipo.get();
   }
   public void setTipo(String Tipo){
      this.Tipo.set(Tipo);
   }
}