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
public class Pedidos {
    SimpleStringProperty Npedido;
    SimpleStringProperty Cliente;
    SimpleStringProperty FechaR;
    SimpleStringProperty FechaE;
    SimpleStringProperty Distribuidora;
   public Pedidos(String Npedido, String Cliente, String FechaR, String FechaE, String Distribuidora) {
      this.Npedido = new SimpleStringProperty(Npedido);
      this.Cliente = new SimpleStringProperty(Cliente);
      this.FechaR = new SimpleStringProperty(FechaR);
      this.FechaE = new SimpleStringProperty(FechaE);
      this.Distribuidora = new SimpleStringProperty(Distribuidora);
   }
   
   public String getNpedido(){
      return Npedido.get();
   }
   public String getCliente(){
      return Cliente.get();
   }
   public String getFechaR(){
      return FechaR.get();
   }
   public String getFechaE(){
      return FechaE.get();
   }
   public String getDistribuidora(){
      return Distribuidora.get();
   }
   
   public void setNpedido(String Npedido){
      this.Npedido.set(Npedido);
   }
   public void setCliente(String Cliente){
      this.Cliente.set(Cliente);
   }
   public void setFechaR(String FechaR){
      this.FechaR.set(FechaR);
   }
   public void setFechaE(String FechaE){
      this.FechaE.set(FechaE);
   }
   public void setDistribuidora(String Distribuidora){
      this.Distribuidora.set(Distribuidora);
   }
}
