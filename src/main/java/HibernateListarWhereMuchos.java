import entidades.Carrera;
import entidades.Facultad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import utilidades.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateListarWhereMuchos {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();

        System.out.print("Seleccione el tipo de entidad (1: Carrera, 2: Facultad): ");
        int tipo = s.nextInt();

        if (tipo == 1) {

            Query consultaCarrera = em.createQuery("SELECT c FROM Carrera c WHERE c.id > ?1", Carrera.class);
            System.out.print("Ingrese un código de carrera: ");
            String idCarrera = s.next();
            consultaCarrera.setParameter(1, idCarrera);
            List<Carrera> carreras = consultaCarrera.getResultList();
            System.out.println("Carreras encontradas: " + carreras);
        } else if (tipo == 2) {
            // Consulta para Facultad
            Query consultaFacultad = em.createQuery("SELECT f FROM Facultad f WHERE f.id > ?1", Facultad.class);
            System.out.print("Ingrese un código de facultad: ");
            String idFacultad = s.next();
            consultaFacultad.setParameter(1, idFacultad);
            List<Facultad> facultades = consultaFacultad.getResultList();
            System.out.println("Facultades encontradas: " + facultades);
        } else {
            System.out.println("Opción no válida.");
        }

        em.close();
    }
}

