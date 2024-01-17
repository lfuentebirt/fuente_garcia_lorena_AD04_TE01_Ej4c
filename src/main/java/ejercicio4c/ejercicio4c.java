package ejercicio4c;

import java.time.LocalDate;

import entidades.Address;
import entidades.Course;
import entidades.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ejercicio4c {
	public static void main(String[] args) {
		/* Crea un nuevo objeto Student y otro objeto Course y guárdalo en la BD. 
		 * La asociación entre ambas entidades será: @ManyToMany bidireccional. 
		 * Esto será útil si necesitas acceder a los estudiantes inscritos en un curso 
		 * (por ejemplo, para generar listas de clase o para administración) y también 
		 * necesitas conocer los cursos en los que está inscrito un estudiante. 
		 * 
		 */
		// creamos el Entity Manager Factory y el entity manager
		EntityManagerFactory factoria = Persistence.createEntityManagerFactory("demodb");
		
		EntityManager manager = factoria.createEntityManager();
		
		// comenzamos la transaccion
		manager.getTransaction().begin();
		try {		
			// creamos un objeto de la clase Student
			Student estudiante = crearEstudiante();
			
			// creamos el curso
			Course curso = crearCurso();
			
			//relacion bidireccional ManyToMany
			estudiante.getCourses().add(curso);
			curso.getStudents().add(estudiante);
	
			// guardamos el estudiante
			manager.persist(estudiante);
			
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
		
		private static Course crearCurso() {
			Course curso = new Course();
			
			curso.setName("Acceso a datos");
			curso.setCredits(6);
			
			return curso;	
		}


		private static Student crearEstudiante() {
			Student estu = new Student();
			
			estu.setFirstName("Lorena");
			estu.setLastName("Fuente");
			estu.setEmail("lfuente@birt.eus");
			estu.getPhones().add("666666666");
			estu.setBirthdate(LocalDate.parse("1990-01-01"));
			
			Address direccion = new Address();
			direccion.setAddressLine1("C/Alava");
			direccion.setAddressLine2("1, 4A");
			direccion.setCity("Vitoria");
			direccion.setZipCode("1000");
			
			estu.setAddress(direccion);
			
			return estu;
		}
}
