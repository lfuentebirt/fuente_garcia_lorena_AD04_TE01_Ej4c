package ejercicio4c;

import entidades.Course;
import entidades.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ejercicio4cEliminar {

	public static void main(String[] args) {
		// creamos el Entity Manager Factory y el entity manager
		EntityManagerFactory factoria = Persistence.createEntityManagerFactory("demodb");
		
		EntityManager manager = factoria.createEntityManager();
		
		// comenzamos la transaccion
		manager.getTransaction().begin();
		
		try {	
			
			int idStudent = 13; // he insertado el registro n 13
			
			// obtenemos el estudiante
			Student estudiante = manager.find(Student.class, idStudent);
			
			// eliminamos el curso, pero no se elimina el estudinte ni la matricula
			manager.remove(estudiante);
			
			manager.getTransaction().commit();
			
		}catch(Exception e) {
			// si hay excepcion realizamos un rollback
			System.out.println("Rollback");
			manager.getTransaction().rollback();
			
			e.printStackTrace();
		}finally {
			manager.close();
			factoria.close();
		}

	}

}
