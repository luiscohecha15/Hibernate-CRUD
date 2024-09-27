import entidades.Carrera;
import entidades.Facultad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import utilidades.JpaUtil;

import java.util.Scanner;

public class HibernateListarWhere {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);


        EntityManager em = JpaUtil.getEntityManager();


        System.out.print("Ingrese el tipo de entidad (1 para Carrera, 2 para Facultad): ");
        int tipoEntidad = s.nextInt();
        s.nextLine();

        Query consulta = null;
        String idTipo = null;


        switch (tipoEntidad) {
            case 1:
                consulta = em.createQuery("select c from Carrera c where c.id = ?1", Carrera.class);
                System.out.print("Ingrese un código de carrera: ");
                idTipo = "Carrera";
                break;
            case 2:
                consulta = em.createQuery("select f from Facultad f where f.id = ?1", Facultad.class);
                System.out.print("Ingrese un código de facultad: ");
                idTipo = "Facultad";
                break;
            default:
                System.out.println("Tipo de entidad no válido.");
                em.close();
                return;
        }

        String id = s.nextLine();
        consulta.setParameter(1, id);

        try {

            Object resultado = consulta.getSingleResult();
            System.out.println("Resultado (" + idTipo + "): " + resultado);
        } catch (Exception e) {
            System.out.println("No se encontró el resultado para el ID proporcionado.");
        } finally {

            em.close();
        }
    }
}
