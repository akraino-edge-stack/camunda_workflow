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

package com.akraino.bpm.config;


import java.io.IOException;


import javax.sql.DataSource;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.jobexecutor.CallerRunsRejectedJobsHandler;
import org.camunda.bpm.engine.impl.jobexecutor.JobExecutor;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



@Configuration

	public class CamundaCorsFilter {
	
	
	@Value("${camunda.bpm.buildresponseurl}")	
	private String buildresponseurl;
	
	@Value("${camunda.bpm.tokenId}")	
	private String tokenId;
	
	@Autowired 
	private DataSource dataSource;
	
	@Autowired
	private PlatformTransactionManager  transactionManager;
	
	@Autowired 
	private JobExecutor jobExecutor;
	 	
	 	public String getBuildresponseurl() {
			return buildresponseurl;
		}

	 	public void setBuildresponseurl(String buildresponseurl) {
			this.buildresponseurl = buildresponseurl;
		}
	 	
	 	public String getTokenId() {
			return tokenId;
		}

		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}

		@Bean
	 	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	 	   // Do any additional configuration here
	 	   return builder.build();
	 	}
	 	
	 	
	 	
	 	
	 	/*@Bean
	 	public  JobExecutor jobExecutor() {
	 	    final JobExecutor jobExecutor = new RuntimeContainerJobExecutor();
	 	    jobExecutor.setWaitTimeInMillis(5000);
	 	    jobExecutor.setLockTimeInMillis(18000000);
	 	    return jobExecutor;
	 	  } 
	 	@Bean 
	 	
	 	public ProcessEngineConfiguration SpringProcessEngineConfiguration () {
	 		
	 		ProcessEngineConfiguration ProcessEngineConfiguration=new SpringProcessEngineConfiguration().setJobExecutor(jobexecutor).setProcessEngineName("default");
	 		return ProcessEngineConfiguration;
	 	}*/
	 	
	 @Bean
	 	@Primary
	    public static JobExecutor jobExecutor(@Qualifier("camundaTaskExecutor") final TaskExecutor taskExecutor) {
	      final SpringJobExecutor springJobExecutor = new SpringJobExecutor();
	      springJobExecutor.setTaskExecutor(taskExecutor);
	      springJobExecutor.setRejectedJobsHandler(new CallerRunsRejectedJobsHandler());
	      springJobExecutor.setWaitTimeInMillis(5000);
	      springJobExecutor.setLockTimeInMillis(36000000);
	 	  return springJobExecutor;
	    }
	 	
	 	@Bean
	 	@Primary
	 	public ProcessEngineConfigurationImpl processEngineConfiguration() throws IOException {
	 	    SpringProcessEngineConfiguration config = (SpringProcessEngineConfiguration) new SpringProcessEngineConfiguration().setJobExecutor(jobExecutor);
	 	    config.setDataSource(dataSource);
	 	    config.setTransactionManager(transactionManager);
	 	    config.setDatabaseSchemaUpdate("true");
	 	    config.setJobExecutorActivate(true);
	 	    config.setAuthorizationEnabled(true);
	 	    config.setDefaultSerializationFormat("application/json");
	 	    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	 	    config.setDeploymentResources(resolver.getResources("classpath:*.bpmn"));
	 	    //config.setProcessEnginePlugins();
	 	    return config;
	 	}
	 
	 	@Bean
	 	public PlatformTransactionManager getTransactionManager() {
	 		PlatformTransactionManager ptm=new DataSourceTransactionManager(dataSource);
	 		return ptm;
	 	  } 
	 	
	 	
	 	@Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("OPTIONS");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("PATCH");
	        config.addAllowedMethod("PUT");
	        config.addAllowedMethod("DELETE");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
	 	
	 	@Bean(name="akrainoprocessExecutor")
	    public TaskExecutor workExecutor() {
	        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
	        threadPoolTaskExecutor.setThreadNamePrefix("Async-");
	        threadPoolTaskExecutor.setCorePoolSize(10);
	        threadPoolTaskExecutor.setMaxPoolSize(50);
	        threadPoolTaskExecutor.setQueueCapacity(600);
	        threadPoolTaskExecutor.afterPropertiesSet();
	        return threadPoolTaskExecutor;
	    }	
	}


