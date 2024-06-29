/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Entity.Bitacora;
import Entity.Usuario;
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;

/**
 *
 * @author lenovo
 */
@Stateless
@LocalBean
public class UsuarioFacade {

    @PersistenceContext(unitName = "LTC2-ejbPU")
    private EntityManager em;

    public List<Usuario> iniciarSesion(String email, String password) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByEmailAndPassword", Usuario.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getResultList();
    }

}
