package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("editarViviendaBean")
@ViewScoped
public class EditarViviendaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER =
            Logger.getLogger(EditarViviendaBean.class.getName());

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private Integer id;

    private Vivienda vivienda;

    public void cargar() {

        if (id != null && vivienda == null) {
            vivienda = viviendaJpaDAO.buscarPorId(id);
        }
    }

    public String actualizar() {

        if (vivienda == null) {
            return null;
        }

        try {

            viviendaJpaDAO.actualizar(vivienda);

            return "/viviendas-jpa.xhtml?faces-redirect=true";

        } catch (RuntimeException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al actualizar la vivienda mediante JPA",
                    e
            );

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "No se pudo actualizar la vivienda.",
                            "Comprueba los datos y vuelve a intentarlo."
                    )
            );

            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }
}