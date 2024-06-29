/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author lenovo
 */
@FacesValidator(value = "SKUValidator")
public class SKUValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage msj;
        if (value == null) {
            msj = new FacesMessage("El campo SKU es requerido.", "");
            msj.setSeverity(FacesMessage.SEVERITY_INFO);

            throw new ValidatorException(msj);
        } else {
            if (value instanceof String) {
                String str = (String) value;
                /*String valueSinEspacios = str.trim();
                if (valueSinEspacios.isEmpty()) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El campo SKU es requerido", ""));

                }
*/
                if (!str.matches("SKU-[A-Za-z]{3}\\d{8}") && !str.equals("")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El dato " + value.toString() + " no es correcto, valide el formato.", ""));

                }
            } else {
                if (!value.equals("")) {
                    msj = new FacesMessage("El tipo de dato no es el correcto, intente de nuevo.", "");
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msj);
                }
            }
        }
    }
}
