import entidades.Carrera;
import entidades.Facultad;
import jakarta.persistence.EntityManager;
import utilidades.JpaUtil;

import java.util.List;

public class HibernateListar {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Facultad> facultades = em.createQuery("select c from Facultad c").getResultList();
        facultades.forEach(System.out::println);
        List<Carrera> carreras = em.createQuery("select c from Carrera c").getResultList();
        carreras.forEach(System.out::println);
    }
}