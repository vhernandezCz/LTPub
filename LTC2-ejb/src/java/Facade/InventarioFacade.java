/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Inventario;
import Entity.OrdenAccesorio;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author lenovo
 */
@Stateless
@LocalBean
public class InventarioFacade {

    @PersistenceContext(unitName = "LTC2-ejbPU")
    private EntityManager em;

    public List<Inventario> findAll() {
        TypedQuery<Inventario> query = em.createQuery(
                "SELECT i FROM Inventario i "
                + "JOIN FETCH i.ordenAccesorio oa "
                + "JOIN FETCH oa.datosAccesorio da "
                + "JOIN FETCH da.categoria c "
                + "JOIN FETCH da.clasificacion cl "
                + "ORDER BY c.nombre, cl.nombre",
                Inventario.class
        );
        return query.getResultList();
    }

    public Inventario consultarProductoPorOrdenAccesorio(int idOrdenAccesorio) {
        TypedQuery<Inventario> query;
        String queryString = "SELECT i FROM Inventario i WHERE i.ordenAccesorio.idOrdenAccesorio = :idOrdenAccesorio";
        query = em.createQuery(queryString, Inventario.class);
        query.setParameter("idOrdenAccesorio", idOrdenAccesorio);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public Inventario consultarPorDatosAccesorioProveedorSugerido(int idDatosAccesorio) {
        TypedQuery<Inventario> query;
        String queryString = "SELECT i FROM Inventario i WHERE "
                + "i.ordenAccesorio.datosAccesorio.idDatosAccesorio = :idDatosAccesorio "
                + "AND i.ordenAccesorio.precioInicial = (SELECT MIN(p.ordenAccesorio.precioInicial) "
                + "FROM Inventario p WHERE p.ordenAccesorio.datosAccesorio.idDatosAccesorio = :idDatosAccesorio)";
        query = em.createQuery(queryString, Inventario.class);
        query.setParameter("idDatosAccesorio", idDatosAccesorio);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public void actualizarStock(int idInventario, int enStock) {
        String queryString = "UPDATE Inventario i SET i.enStock = :enStock WHERE i.idInventario = :idInventario";
        Query query = em.createQuery(queryString);
        query.setParameter("enStock", enStock);
        query.setParameter("idInventario", idInventario);
        query.executeUpdate();
    }

    public void editarOrdenAccesorio(int idInventario, int idOrdenAccesorio) {
        Query query = em.createQuery("UPDATE Inventario i SET i.ordenAccesorio = :ordenAccesorio WHERE i.idInventario = :idInventario");
        OrdenAccesorio ordenAccesorio = em.find(OrdenAccesorio.class, idOrdenAccesorio);
        query.setParameter("ordenAccesorio", ordenAccesorio);
        query.setParameter("idInventario", idInventario);
        int actualizaciones = query.executeUpdate();
        if (actualizaciones == 0) {
            // Manejar el caso en el que no se actualizó ningún registro
        }
    }

    public int insert(Inventario inventario) {
        FacesMessage msj;
        try {
            if (inventario != null) {
                em.persist(inventario);
                em.flush();
                return inventario.getIdInventario();
            } else {
                msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El objeto Inventario es nulo.", "");
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
            msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al insertar el objeto Inventario: " + e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
        }
        return -1;
    }

    public int guardarImagen(int idInventario, byte[] imagenBytes) {
        FacesMessage msj;
        try {
            if (imagenBytes != null && imagenBytes.length > 0) {
                Inventario inventario = em.find(Inventario.class, idInventario);
                inventario.getOrdenAccesorio().getDatosAccesorio().setImagen(imagenBytes);
                em.merge(inventario);
                return idInventario;
            } else {
                msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: El arreglo de bytes de la imagen es nulo o vacío.", "");
                FacesContext.getCurrentInstance().addMessage(null, msj);
            }
        } catch (Exception e) {
            msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar la imagen en el objeto Inventario: " + e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
        }
        return -1;
    }

    public List<Inventario> consultaPorIdsInventario(List<Integer> idsInventarioSeleccionados) {
        List<Inventario> inventarios = new ArrayList<>();
        for (Integer id : idsInventarioSeleccionados) {
            Inventario inventario = em.find(Inventario.class, id);
            if (inventario != null) {
                inventarios.add(inventario);
            }
        }
        return inventarios;
    }

    public Inventario findInventarioById(Integer id) {
        return em.find(Inventario.class, id);
    }

    public void update(Inventario inventario) {
        em.merge(inventario);
    }

    public void delete(Inventario inventario) {
        em.remove(em.merge(inventario));
    }
}
