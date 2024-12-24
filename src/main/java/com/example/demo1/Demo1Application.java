package com.example.demo1;

import com.example.demo1.entity.Role;
import com.example.demo1.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info=@Info(
		title = "Spring boot blog app rest api" ,
		description="Spring boot blog app rest api's documentation" ,
		version="v1.0",
		contact=@Contact(
				name="Ramesh",
				email="kanchanrawat8126@gmail.com",
				url="https://www.javaguides.net"
		),
		license=@License(
			name="Apache 2.0",
			url= "https://www.javaguides.net/license"
		)
),
externalDocs = @ExternalDocumentation(description="Spring boot api",url="https://github.com/123kanchan")
)
@SpringBootApplication
public class Demo1Application implements CommandLineRunner {
@Bean
	public ModelMapper getModel(){
		ModelMapper mp=new ModelMapper();
		return mp;
	}
	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);

	}
	@Autowired
private RoleRepository repo;
	@Override
	public void run(String... args) throws Exception {
		Role role=new Role();
		role.setName("ROLE_USER");
		repo.save(role);
		Role role1=new Role();
		role1.setName("ROLE_ADMIN");
		repo.save(role1);
	}
}
