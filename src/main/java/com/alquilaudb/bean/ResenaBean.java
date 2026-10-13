package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Resena;
import com.alquilaudb.model.Vivienda;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("resenaBean")
@ViewScoped
public class ResenaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER =
            Logger.getLogger(ResenaBean.class.getName());

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private Integer viviendaId;

    private Vivienda vivienda;

    private List<Resena> resenas;

    private Resena nuevaResena = new Resena();

    // CARGAR VIVIENDA Y SUS RESEÑAS
    public void cargar() {

        if (viviendaId == null || viviendaId <= 0) {
            return;
        }

        vivienda = viviendaJpaDAO.buscarPorId(viviendaId);

        if (vivienda != null) {
            resenas = viviendaJpaDAO.listarResenas(viviendaId);
        }
    }

    // REGISTRAR NUEVA RESEÑA
    public String registrar() {

        if (vivienda == null || viviendaId == null) {
            return null;
        }

        try {

            viviendaJpaDAO.guardarResena(
                    viviendaId,
                    nuevaResena
            );

            return "/resenas.xhtml?faces-redirect=true&id="
                    + viviendaId;

        } catch (RuntimeException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error al registrar la reseña",
                    e
            );

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "No se pudo registrar la reseña.",
                            "Revisa los datos introducidos."
                    )
            );

            return null;
        }
    }

    public Integer getViviendaId() {
        return viviendaId;
    }

    public void setViviendaId(Integer viviendaId) {
        this.viviendaId = viviendaId;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public List<Resena> getResenas() {
        return resenas;
    }

    public Resena getNuevaResena() {
        return nuevaResena;
    }

    public void setNuevaResena(Resena nuevaResena) {
        this.nuevaResena = nuevaResena;
    }
}

