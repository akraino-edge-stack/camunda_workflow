/*
 * Copyright (c) 2018 AT&T Intellectual Property. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.akraino.bpm;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class AkrainoSpringBootApplication {
	public static void main(String... args) {
		System.setProperty("server.connection-timeout","36000000");
		SpringApplication.run(AkrainoSpringBootApplication.class, args);
	}
	
	  @Bean
	  public Docket api() {
	    Set<String> mediaTypes = new HashSet<String>();
	    mediaTypes.add(MediaType.APPLICATION_JSON_VALUE);

	    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
	        .paths(PathSelectors.any()).build().pathMapping("/").consumes(mediaTypes).produces(mediaTypes)
	        .apiInfo(apiInfo()).useDefaultResponseMessages(false);
	  }

	  @Bean
	  public ApiInfo apiInfo() {
	    final ApiInfoBuilder builder = new ApiInfoBuilder();
	    builder.title("Akraino Camunda  Workflow REST Api").version("1.0").license("(C) Copyright AT&T Inc.")
	        .description("The API provides a Workflow Engine for Akraino");
	    return builder.build();
	  }

	
	
}
