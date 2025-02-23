package com.back.backend;

import com.back.backend.entities.Product;
import com.back.backend.entities.User;
import com.back.backend.enums.InventoryStatus;
import com.back.backend.repository.ProductRepository;
import com.back.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

		System.out.println("Hello World!");
	}

	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			if (productRepository.count() == 0) { // Ensures data is only added if empty
				List<Product> products = List.of(
						new Product("P001", "Laptop", "High-end gaming laptop", "laptop.jpg",
								"Electronics", 1500.0, 10, "LTP-001", 1, 10.0, LocalDateTime.now(), LocalDateTime.now(), InventoryStatus.INSTOCK),

						new Product("P002", "Smartphone", "Latest smartphone with 5G", "smartphone.jpg",
								"Electronics", 999.0, 20, "SMP-002", 2, 10.0, LocalDateTime.now(), LocalDateTime.now(), InventoryStatus.INSTOCK),

						new Product("P003", "Headphones", "Noise-cancelling headphones", "headphones.jpg",
								"Accessories", 299.0, 15, "HPH-003", 3, 10.0, LocalDateTime.now(), LocalDateTime.now(), InventoryStatus.INSTOCK)
				);

				productRepository.saveAll(products);
			}

			productRepository.findAll().forEach(System.out::println);
		};
	}

	@Bean
	CommandLineRunner initData(UserRepository userRepository,
							   PasswordEncoder passwordEncoder) {
		return args -> {
			User admin = userRepository.findUserByEmail("admin@admin.com");
			if (admin == null) {
				admin = new User();
				admin.setUsername("admin");
				admin.setEmail("admin@admin.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setAuthorities("ADMIN");
				userRepository.save(admin);
			}
		};
	}


}
