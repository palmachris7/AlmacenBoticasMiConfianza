package entidades;


import javafx.scene.image.Image;

import java.sql.Blob;
import java.util.Date;

public class Producto {
    int id_producto;
    String nombre_producto;
    String tipo_producto;
    String presentacion_producto;
    int lote_producto;
    Date fecha_vencimiento;
    String info_producto;
    String estado_producto;
    Double precio_unid;
    Double precio_caja;
    Laboratorio laboratorio;
    Blob image;




    public Producto(int id_producto, String nombre_producto, String tipo_producto, String presentacion_producto, int lote_producto, Date fecha_vencimiento, String info_producto, String estado_producto, Double precio_unid, Double precio_caja, Laboratorio laboratorio) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.tipo_producto = tipo_producto;
        this.presentacion_producto = presentacion_producto;
        this.lote_producto = lote_producto;
        this.fecha_vencimiento = fecha_vencimiento;
        this.info_producto = info_producto;
        this.estado_producto = estado_producto;
        this.precio_unid = precio_unid;
        this.precio_caja = precio_caja;
        this.laboratorio = laboratorio;
    }

    public Producto(int id_producto, String nombre_producto, String tipo_producto, String presentacion_producto, int lote_producto, Date fecha_vencimiento, String info_producto, String estado_producto, Double precio_unid, Double precio_caja, Laboratorio laboratorio, Blob image) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.tipo_producto = tipo_producto;
        this.presentacion_producto = presentacion_producto;
        this.lote_producto = lote_producto;
        this.fecha_vencimiento = fecha_vencimiento;
        this.info_producto = info_producto;
        this.estado_producto = estado_producto;
        this.precio_unid = precio_unid;
        this.precio_caja = precio_caja;
        this.laboratorio = laboratorio;
        this.image = image;
    }

    public Producto() {
    }






    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
    public int getId_producto() {
        return this.id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return this.nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getTipo_producto() {
        return this.tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public String getPresentacion_producto() {
        return this.presentacion_producto;
    }

    public void setPresentacion_producto(String presentacion_producto) {
        this.presentacion_producto = presentacion_producto;
    }

    public int getLote_producto() {
        return this.lote_producto;
    }

    public void setLote_producto(int lote_producto) {
        this.lote_producto = lote_producto;
    }

    public Date getFecha_vencimiento() {
        return this.fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getInfo_producto() {
        return this.info_producto;
    }

    public void setInfo_producto(String info_producto) {
        this.info_producto = info_producto;
    }

    public String getEstado_producto() {
        return this.estado_producto;
    }

    public void setEstado_producto(String estado_producto) {
        this.estado_producto = estado_producto;
    }

    public Double getPrecio_unid() {
        return this.precio_unid;
    }

    public void setPrecio_unid(Double precio_unid) {
        this.precio_unid = precio_unid;
    }

    public Double getPrecio_caja() {
        return this.precio_caja;
    }

    public void setPrecio_caja(Double precio_caja) {
        this.precio_caja = precio_caja;
    }

    public Laboratorio getLaboratorio() {
        return this.laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }












                

            

    
}

