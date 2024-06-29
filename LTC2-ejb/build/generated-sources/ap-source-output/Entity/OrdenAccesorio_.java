package Entity;

import Entity.DatosAccesorio;
import Entity.Orden;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-06-28T21:14:57")
@StaticMetamodel(OrdenAccesorio.class)
public class OrdenAccesorio_ { 

    public static volatile SingularAttribute<OrdenAccesorio, DatosAccesorio> datosAccesorio;
    public static volatile SingularAttribute<OrdenAccesorio, Integer> idOrdenAccesorio;
    public static volatile SingularAttribute<OrdenAccesorio, Integer> precioInicial;
    public static volatile SingularAttribute<OrdenAccesorio, Orden> orden;
    public static volatile SingularAttribute<OrdenAccesorio, Integer> precioSugerido;

}