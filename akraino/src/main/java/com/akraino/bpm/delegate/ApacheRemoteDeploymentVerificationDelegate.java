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

package com.akraino.bpm.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.service.RemoteDeploymentVerificationService;



@Component
public class ApacheRemoteDeploymentVerificationDelegate implements JavaDelegate {

	private static Logger logger = LoggerFactory.getLogger(ApacheRemoteDeploymentVerificationDelegate.class);
	
	@Autowired
	RemoteDeploymentVerificationService remotedeploymentVerificationService;
	

 public void execute(DelegateExecution ctx) throws Exception {
		
		try {
			String  verifierFilename=(String)ctx.getVariable("verifier");
			int waittime=(Integer)ctx.getVariable("waittime");
			int iterations=(Integer)ctx.getVariable("iterations");
			String  remoteserver=(String)ctx.getVariable("remoteserver");
			int  port=(Integer)ctx.getVariable("port");
			String  username=(String)ctx.getVariable("username");
			String  password=(String)ctx.getVariable("password");
			String  srcdir=(String)ctx.getVariable("srcdir");
			String  destdir=(String)ctx.getVariable("destdir");
			String  filepparams=(String)ctx.getVariable("verifierfileparams");
			
			logger.debug("task execution started remoteserver {} , port {},username {}, password {},filename : {} , waittime : {},No of iterations :{}",
					remoteserver,port,username,password,verifierFilename,srcdir,destdir,waittime,iterations);
			
			String command= "cd  "+destdir+ ";" + (filepparams!=null?filepparams:" ") + " ./"+verifierFilename;
			logger.debug("Execution command {}",command);
			remotedeploymentVerificationService.executeScript(remoteserver,username,password,port,verifierFilename,filepparams,srcdir,destdir,waittime,iterations,command);
		}catch(TaskExecutorException ex) {
			throw ex;
		}
		
		
	}

}

