package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
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

@Named("viviendaJpaBean")
@ViewScoped
public class ViviendaJpaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER =
            Logger.getLogger(ViviendaJpaBean.class.getName());

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private List<Vivienda> viviendas;

    private String textoBusqueda = "";
    private String tipoFiltro = "";
    private boolean soloDisponibles = false;

    // CONSULTAR VIVIENDAS CON FILTROS
    public List<Vivienda> getViviendas() {

        if (viviendas == null) {
            viviendas = viviendaJpaDAO.buscar(
                    textoBusqueda,
                    tipoFiltro,
                    soloDisponibles
            );
        }

        return viviendas;
    }

    // APLICAR BUSQUEDA
    public void buscar() {
        viviendas = null;
    }

    // RESTABLECER FILTROS
    public void limpiarFiltros() {

        textoBusqueda = "";
        tipoFiltro = "";
        soloDisponibles = false;
        viviendas = null;
    }

    // ELIMINAR VIVIENDA
    public String eliminar(int id) {

        try {

            viviendaJpaDAO.eliminar(id);

            return "/viviendas-jpa.xhtml?faces-redirect=true";

        } catch (IllegalStateException e) {

            mostrarError(e.getMessage());
            viviendas = null;
            return null;

        } catch (IllegalArgumentException e) {

            mostrarError(e.getMessage());
            viviendas = null;
            return null;

        } catch (RuntimeException e) {

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

    // MOSTRAR MENSAJES DE ERROR
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

    // PROPIEDAD: TEXTO DE BUSQUEDA
    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    // PROPIEDAD: TIPO DE VIVIENDA
    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    // PROPIEDAD: SOLO DISPONIBLES
    public boolean isSoloDisponibles() {
        return soloDisponibles;
    }

    public void setSoloDisponibles(boolean soloDisponibles) {
        this.soloDisponibles = soloDisponibles;
    }
}
