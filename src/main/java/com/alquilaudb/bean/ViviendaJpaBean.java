package com.alquilaudb.bean;

import com.alquilaudb.jpa.ViviendaJpaDAO;
import com.alquilaudb.model.Vivienda;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("viviendaJpaBean")
@RequestScoped
public class ViviendaJpaBean {

    @Inject
    private ViviendaJpaDAO viviendaJpaDAO;

    private List<Vivienda> viviendas;

    public List<Vivienda> getViviendas() {

        if (viviendas == null) {
            viviendas = viviendaJpaDAO.listar();
        }

        return viviendas;
    }
}

