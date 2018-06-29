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

package com.akraino.bpm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.config.CamundaCorsFilter;
import com.akraino.bpm.model.BuildResponse;
import com.akraino.bpm.service.DeployResponseSenderService;

@Service("deployResponseSenderService")
public class DeployResponseSenderServiceImpl implements DeployResponseSenderService {
	
	private static Logger logger = LoggerFactory.getLogger(DeployResponseSenderServiceImpl.class);

	@Autowired
	private RestTemplate  restTemplate; 
	
	@Autowired 
	private CamundaCorsFilter camundaCorsFilter;
	
	public void sendResponse(BuildResponse response) {
		try {
			logger.debug(response.toString());
			HttpEntity<BuildResponse> request = new HttpEntity<BuildResponse>(response);	
			ResponseEntity<Void> httpresposne	=restTemplate
					.exchange(camundaCorsFilter.getBuildresponseurl(), HttpMethod.POST, request, Void.class);
		
			logger.debug("Build response HttpResponseStatus :"+httpresposne.getStatusCodeValue());
		}catch (Exception e ) {
			logger.error("problem while sending  deploy response :"+e.getMessage());
			
			throw new TaskExecutorException("problem while sending  deploy response :"+e.getMessage());
			
		}
	}
	
 }
