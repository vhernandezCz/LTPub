/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author lenovo
 */
@Entity
@Table(schema = "LT260324")
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBitacora", nullable = false)
    private Integer idBitacora;

    @Column(name = "SKU", length = 50, nullable = false)
    private String sku;

    @Lob
    @Column(name = "imagen", nullable = true)
    private byte[] imagen;
    
    @Column(name = "categoria", length = 50, nullable = false)
    private String categoria;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "precio", length = 50, nullable = false)
    private String precio;

    @Column(name = "enStock", length = 50, nullable = false)
    private String enStock;

    @Column(name = "orden", length = 50, nullable = false)
    private String orden;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getIdBitacora() != null ? getIdBitacora().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.getIdBitacora() == null && other.getIdBitacora() != null) || (this.getIdBitacora() != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Bitacora[ id=" + getIdBitacora() + " ]";
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getEnStock() {
        return enStock;
    }

    public void setEnStock(String enStock) {
        this.enStock = enStock;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    //Constructor
    public Bitacora() {

    }

    public Bitacora(String orden, String sku, String nombre) {
        this.sku = sku;
        this.orden = orden;
        this.nombre = nombre;
    }

    public Bitacora(Integer idBitacora, String sku,
            String categoria, String nombre, String precio,
            String enStock, String orden, String descripcion) {
        this.idBitacora = idBitacora;
        this.sku = sku;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.enStock = enStock;
        this.orden = orden;
        this.descripcion = descripcion;
    }
}
