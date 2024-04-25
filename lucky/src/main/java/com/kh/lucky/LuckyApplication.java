package com.kh.lucky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//Spring Security 기본 시스템 설정 해제
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LuckyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckyApplication.class, args);
	}

}
