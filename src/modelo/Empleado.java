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
public class Empleado {
   SimpleStringProperty DNI;
   SimpleStringProperty Apellidos;
   SimpleStringProperty Nombre;
   SimpleStringProperty Oficina;
   SimpleStringProperty Telefono;
   SimpleStringProperty Correo;
   SimpleStringProperty Sueldo;
   SimpleStringProperty FechaIngreso;
   SimpleStringProperty Direccion;
   SimpleStringProperty Estado;
   public Empleado(String DNI, String Apellidos, String Nombre, String Oficina, String Telefono, String Correo, String Sueldo, String FechaIngreso, String Direccion, String Estado) {
      this.DNI = new SimpleStringProperty(DNI);
      this.Apellidos = new SimpleStringProperty(Apellidos);
      this.Nombre = new SimpleStringProperty(Nombre);
      this.Oficina = new SimpleStringProperty(Oficina);
      this.Telefono = new SimpleStringProperty(Telefono);
      this.Correo = new SimpleStringProperty(Correo);
      this.Sueldo = new SimpleStringProperty(Sueldo);
      this.FechaIngreso = new SimpleStringProperty(FechaIngreso);
      this.Direccion = new SimpleStringProperty(Direccion);
      this.Estado = new SimpleStringProperty(Estado);
   }
   public String getDNI(){
      return DNI.get();
   }
   public void setDNI(String DNI){
      this.DNI.set(DNI);
   }
   public String getApellidos(){
      return Apellidos.get();
   }
   public void setApellidos(String Apellidos){
      this.Apellidos.set(Apellidos);
   }
   public String getNombre(){
      return Nombre.get();
   }
   public void setNombre(String Nombre){
      this.Nombre.set(Nombre);
   }
   public String getOficina(){
      return Oficina.get();
   }
   public void setOficina(String Oficina){
      this.Oficina.set(Oficina);
   }
   public String getTelefono(){
      return Telefono.get();
   }
   public void SetTelefono(String Tlf){
      this.Telefono.set(Tlf);
   }
   public String getCorreo(){
      return Correo.get();
   }
   public void setCorreo(String Correo){
      this.Correo.set(Correo);
   }
   public String getSueldo(){
      return Sueldo.get();
   }
   public void setModified(String Sueldo){
      this.Sueldo.set(Sueldo);
   }
   public String getFechaIngreso(){
      return FechaIngreso.get();
   }
   public void setFechaIngreso(String FechaIngreso){
      this.FechaIngreso.set(FechaIngreso);
   }
   public String getDireccion(){
      return Direccion.get();
   }
   public void setDireccion(String Direccion){
      this.Direccion.set(Direccion);
   }
   public String getEstado(){
      return Estado.get();
   }
   public void setEstado(String Estado){
      this.Estado.set(Estado);
   }
}