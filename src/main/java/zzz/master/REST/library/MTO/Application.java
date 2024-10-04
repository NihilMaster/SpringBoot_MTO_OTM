package zzz.master.REST.library.MTO;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().load();

		String dbUrl = dotenv.get("DB_URL");
		String dbUser = dotenv.get("DB_USERNAME");
		String dbPassword = dotenv.get("DB_PASSWORD");

		System.setProperty("DB_URL", dbUrl);
		System.setProperty("DB_USERNAME", dbUser);
		System.setProperty("DB_PASSWORD", dbPassword);

		SpringApplication.run(Application.class, args);
	}

}
