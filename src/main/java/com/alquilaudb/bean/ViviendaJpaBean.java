package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("viviendaJpaBean")
@RequestScoped
public class ViviendaJpaBean {

    private static final Logger LOGGER =
            Logger.getLogger(ViviendaJpaBean.class.getName());

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private List<Vivienda> viviendas;

    // OBTENER LISTADO
    public List<Vivienda> getViviendas() {

        if (viviendas == null) {
            viviendas = viviendaJpaDAO.listar();
        }

        return viviendas;
    }

    // ELIMINAR VIVIENDA
    public String eliminar(int id) {

        try {

            viviendaJpaDAO.eliminar(id);

            return "/viviendas-jpa.xhtml?faces-redirect=true";

        } catch (IllegalStateException e) {

            // LA VIVIENDA TIENE RESEÑAS
            mostrarError(e.getMessage());

            viviendas = null;
            return null;

        } catch (IllegalArgumentException e) {

            // LA VIVIENDA NO EXISTE
            mostrarError(e.getMessage());

            viviendas = null;
            return null;

        } catch (RuntimeException e) {

            // OTROS ERRORES DE PERSISTENCIA
            LOGGER.log(
                    Level.SEVERE,
                    "Error al eliminar la vivienda con ID: " + id,
                    e
            );

            mostrarError(
                    "No se pudo eliminar la vivienda. "
                    + "Comprueba las restricciones de la base de datos."
            );

            viviendas = null;
            return null;
        }
    }

    private void mostrarError(String mensaje) {

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        mensaje,
                        null
                )
        );
    }
}
