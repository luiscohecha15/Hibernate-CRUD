import entidades.Carrera;
import entidades.Facultad;
import jakarta.persistence.EntityManager;
import utilidades.JpaUtil;

import java.util.Scanner;

public class HibernateEliminar {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();

        System.out.print("Seleccione el tipo de entidad a eliminar (1: Carrera, 2: Facultad): ");
        int tipo = s.nextInt();

        try {
            em.getTransaction().begin();

            if (tipo == 1) {
                // Eliminar Carrera
                System.out.printf("Digite el código de Carrera a eliminar: ");
                Long idCarrera = s.nextLong();
                Carrera carrera = em.find(Carrera.class, idCarrera);

                if (carrera != null) {
                    em.remove(carrera);
                    System.out.println("Carrera eliminada correctamente.");
                } else {
                    System.out.println("Carrera no encontrada.");
                }
            } else if (tipo == 2) {
                // Eliminar Facultad
                System.out.printf("Digite el código de Facultad a eliminar: ");
                Long idFacultad = s.nextLong();
                Facultad facultad = em.find(Facultad.class, idFacultad);

                if (facultad != null) {
                    em.remove(facultad);
                    System.out.println("Facultad eliminada correctamente.");
                } else {
                    System.out.println("Facultad no encontrada.");
                }
            } else {
                System.out.println("Opción no válida.");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
