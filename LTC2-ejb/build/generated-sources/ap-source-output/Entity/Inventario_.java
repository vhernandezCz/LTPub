package Entity;

import Entity.OrdenAccesorio;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-06-28T21:14:57")
@StaticMetamodel(Inventario.class)
public class Inventario_ { 

    public static volatile SingularAttribute<Inventario, Integer> idInventario;
    public static volatile SingularAttribute<Inventario, OrdenAccesorio> ordenAccesorio;
    public static volatile SingularAttribute<Inventario, Integer> enStock;

}