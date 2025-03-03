package edu.eci.cvds.ECIReserves;

import edu.eci.cvds.ECIReserves.service.LaboratoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EciReservesApplication {

	public static void main(String[] args) {
		// Inicia el contexto de Spring
		ApplicationContext context = SpringApplication.run(EciReservesApplication.class, args);

		// Obtiene el servicio de laboratorios
		LaboratoryService laboratoryService = context.getBean(LaboratoryService.class);

		// Verifica la conexión
		testMongoConnection(laboratoryService);
	}

	private static void testMongoConnection(LaboratoryService laboratoryService) {
		try {
			long count = laboratoryService.getRepository().count();
			System.out.println("✅ Conectado a MongoDB. Número de laboratorios en la base de datos: " + count);
		} catch (Exception e) {
			System.err.println("❌ Error conectando a MongoDB: " + e.getMessage());
		}
	}
}

