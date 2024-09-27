import entidades.Carrera;
import entidades.Facultad;
import jakarta.persistence.EntityManager;
import utilidades.JpaUtil;

import javax.swing.*;

public class HibernateCrear {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        try {

            String[] options = {"Crear Carrera", "Crear Facultad"};
            int opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione el tipo de entidad a crear:",
                    "Hibernate Crear",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);


            em.getTransaction().begin();

            if (opcion == 0) {
                // Crear Carrera
                String nombre = JOptionPane.showInputDialog("Ingrese nombre de carrera:");
                int tipo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese tipo de carrera:"));
                int idFacultad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID de facultad:"));

                // Buscar la Facultad correspondiente al ID ingresado
                Facultad facultad = em.find(Facultad.class, idFacultad);

                // Verificar si se encontró la Facultad
                if (facultad == null) {
                    JOptionPane.showMessageDialog(null, "Facultad con ID " + idFacultad + " no encontrada.");
                    em.getTransaction().rollback();
                    return;
                }

                // Crear y configurar la nueva Carrera
                Carrera ca = new Carrera();
                ca.setNombre(nombre);
                ca.setTipo(tipo);
                ca.setFacultad(facultad); // Asignar la instancia de Facultad


                em.persist(ca);


                em.getTransaction().commit();

                // Mostrar el nuevo ID de la Carrera
                System.out.println("El nuevo código de carrera es: " + ca.getId());

                // Consultar y mostrar la Carrera recién creada
                ca = em.find(Carrera.class, ca.getId());
                System.out.println(ca);

            } else if (opcion == 1) {
                // Crear Facultad
                String nombreFacultad = JOptionPane.showInputDialog("Ingrese nombre de facultad:");
                String abreviatura = JOptionPane.showInputDialog("Ingrese abreviatura de facultad:");


                Facultad facultad = new Facultad();
                facultad.setNombre(nombreFacultad);
                facultad.setAbreviatura(abreviatura);


                em.persist(facultad);


                em.getTransaction().commit();

                // Mostrar el nuevo ID de la Facultad
                System.out.println("El nuevo código de facultad es: " + facultad.getId());

                // Consultar y mostrar la Facultad recién creada
                facultad = em.find(Facultad.class, facultad.getId());
                System.out.println(facultad);

            } else {
                System.out.println("Opción no válida.");
                em.getTransaction().rollback();
            }

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {

            em.close();
        }
    }
}
