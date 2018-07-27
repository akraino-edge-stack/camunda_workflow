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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.service.DeploymentVerificationService;


@Service("deploymentVerificationService")
public class DeploymentverificationServiceImpl implements DeploymentVerificationService{

	private static Logger logger = LoggerFactory.getLogger(DeploymentverificationServiceImpl.class);
	
	public void executeScript(String filepatch,int waitttime,int iterations)  {
		
		boolean issuccess=false;
		for( int i=0;i<=iterations;i++) {
			try {
				logger.debug("Executing the deployment verification script.............iteration : {}",i);
				Thread.sleep(waitttime*1000);
				Process p = Runtime.getRuntime().exec(filepatch);
				p.waitFor();
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String line = "";
		        while ((line = input.readLine()) != null) {
		            if(line.equals("0")) {
		            	issuccess=true;
		            }
		         }
		        
		        logger.debug("Script exit code :"+p.exitValue());
	            if(p.exitValue()!=0) {
	            	throw new TaskExecutorException("problem while executing the script . exist code :"+p.exitValue());
	            }
		    } catch (IOException e) {
					throw new TaskExecutorException(filepatch + " not found.");
			} catch (InterruptedException e) {
					throw new TaskExecutorException("problem while executing the script "+filepatch);
			}
			if(issuccess) {
				break;
			}
			
		}
		
		 if(!issuccess) {
         	logger.debug("verification script returned 1 ");
         	throw new TaskExecutorException("1");
         }
	}

}
