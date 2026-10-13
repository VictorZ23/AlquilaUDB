package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("detalleViviendaBean")
@ViewScoped
public class DetalleViviendaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private Integer id;
    private Vivienda vivienda;

    // CARGAR LA VIVIENDA SOLICITADA
    public void cargar() {

        if (id == null || id <= 0) {
            vivienda = null;
            return;
        }

        vivienda = viviendaJpaDAO.buscarPorId(id);
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
