package com.buccodev.contact_book;

import com.buccodev.contact_book.entities.Users;
import com.buccodev.contact_book.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ContactBookApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Faker faker;


	@Test
	void contextLoads() {

		long initialCount = userRepository.count();


		for (int i = 0; i < 10000; i++) {
			Users user = new Users(null, faker.name().name(), faker.number().digits(11), faker.internet().emailAddress()+ new Random().hashCode());

			userRepository.save(user);


		}

		long finalCount = userRepository.count();

		long insertedCount = finalCount - initialCount;
		System.out.println("Número de registros inseridos: " + insertedCount);

		List<Users> users = userRepository.findAll();
		users.stream().forEach(x -> System.out.println(x.getName()));

		assertThat(insertedCount).isEqualTo(10000);





	}

}
