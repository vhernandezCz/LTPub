/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Usuario;
import Facade.UsuarioFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author lenovo
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade = new UsuarioFacade();
    private Usuario usuario = new Usuario();
    
    private String correo = "";
    private String password = "";
    
    public String iniciarSesion(){
        List<Usuario> user = usuarioFacade.iniciarSesion(this.correo, this.password);
        
        if(user.isEmpty()){
            return "index";
         } else {
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("nombre", user.get(0).getNombre());
            FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().put("email", user.get(0).getEmail());
            return "menu";
        }
    }

    /**
      * @return the usuarioFacade
     */
    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    /**
     * @param usuarioFacade the usuarioFacade to set
     */
    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
