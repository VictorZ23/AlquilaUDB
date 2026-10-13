package com.alquilaudb.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("comentarioResenaValidator")
public class ComentarioResenaValidator implements Validator {

    @Override
    public void validate(
            FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {

        String comentario = value == null
                ? ""
                : value.toString().trim();

        if (comentario.length() < 10) {

            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "El comentario debe contener al menos 10 caracteres útiles.",
                            null
                    )
            );
        }

        if (comentario.length() > 1000) {

            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "El comentario no puede superar los 1000 caracteres.",
                            null
                    )
            );
        }
    }
}
