import entidades.Carrera;
import entidades.Facultad;
import jakarta.persistence.EntityManager;
import utilidades.JpaUtil;

import javax.swing.*;

public class HibernateEditar {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            String[] options = {"Editar Carrera", "Editar Facultad"};
            int opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opción:",
                    "Hibernate Editar",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (opcion == 0) {
                // Editar Carrera
                Long id = Long.valueOf(JOptionPane.showInputDialog("Ingrese un código de carrera a modificar:"));
                Carrera ca = em.find(Carrera.class, id);

                if (ca != null) {
                    String nombre = JOptionPane.showInputDialog("Ingrese nuevo nombre de carrera:", ca.getNombre());
                    int tipo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese tipo de carrera:", String.valueOf(ca.getTipo())));
                    Long idFacultad = Long.valueOf(JOptionPane.showInputDialog("Ingrese nuevo ID de facultad:", ca.getFacultad() != null ? ca.getFacultad().getId() : ""));


                    em.getTransaction().begin();
                    // Actualizar los valores
                    ca.setNombre(nombre);
                    ca.setTipo(tipo);

                    // Busca la nueva facultad y la actualiza en la carrera
                    Facultad facultad = em.find(Facultad.class, idFacultad);
                    if (facultad != null) {
                        ca.setFacultad(facultad);
                    } else {
                        JOptionPane.showMessageDialog(null, "Facultad con ID " + idFacultad + " no encontrada.");
                    }

                    em.merge(ca);
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, "Carrera actualizada: " + ca);
                } else {
                    JOptionPane.showMessageDialog(null, "Carrera no encontrada.");
                }

            } else if (opcion == 1) {
                // Editar Facultad
                Long id = Long.valueOf(JOptionPane.showInputDialog("Ingrese un código de facultad a modificar:"));
                Facultad fa = em.find(Facultad.class, id);

                if (fa != null) {
                    String nombre = JOptionPane.showInputDialog("Ingrese nuevo nombre de facultad:", fa.getNombre());
                    String abreviatura = JOptionPane.showInputDialog("Ingrese abreviatura de facultad:", fa.getAbreviatura());


                    em.getTransaction().begin();
                    // Actualizar los valores
                    fa.setNombre(nombre);
                    fa.setAbreviatura(abreviatura);

                    em.merge(fa);
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, "Facultad actualizada: " + fa);
                } else {
                    JOptionPane.showMessageDialog(null, "Facultad no encontrada.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Opción no válida.");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(null, "Se ha generado el error: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}

