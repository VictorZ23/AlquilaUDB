
package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("registroViviendaBean")
@RequestScoped
public class RegistroViviendaBean {

    private static final Logger LOGGER =
            Logger.getLogger(RegistroViviendaBean.class.getName());

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private Vivienda vivienda;

    public RegistroViviendaBean() {

        vivienda = new Vivienda();
        vivienda.setDisponible(true);
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    public String guardar() {

        try {

            viviendaJpaDAO.guardar(vivienda);

            return "/viviendas-jpa.xhtml?faces-redirect=true";

        } catch (RuntimeException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al registrar la vivienda mediante JPA",
                    e
            );

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "No se pudo registrar la vivienda.",
                            "Revisa los datos e inténtalo nuevamente."
                    )
            );

            return null;
        }
    }
}
