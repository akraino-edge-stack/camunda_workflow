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

import com.akraino.bpm.model.BuildResponse;
import com.akraino.bpm.service.DeployResponseSenderService;
import com.akraino.bpm.service.RemoteScriptExecutionService;



@Component
public class MultiNodeDeployRemoteScript2ExecutorTaskDelegate implements JavaDelegate {

	
	private static Logger logger = LoggerFactory.getLogger(MultiNodeDeployRemoteScript2ExecutorTaskDelegate.class);
	
	@Autowired
	RemoteScriptExecutionService remoteScriptExecutionService;
	
	@Autowired 
	DeployResponseSenderService deployResponseSenderService;
	
	
	public void execute(DelegateExecution ctx) throws Exception {
		String  remoteserver=(String)ctx.getVariable("remoteserver");
		int  port=(Integer)ctx.getVariable("port");
		String  username=(String)ctx.getVariable("username");
		String  password=(String)ctx.getVariable("password");
		String  filename=(String)ctx.getVariable("remotefile2");
		String fileparams=(String)ctx.getVariable("remotefile2params");
		String destdir=(String)ctx.getVariable("destdir2");
		String sitename=(String)ctx.getVariable("sitename");
		
		deployResponseSenderService.sendResponse(new BuildResponse("completed", "completed", "completed", "inprogress","not started",sitename,null,null,null));
		
		logger.debug("task execution started remoteserver {} , port {},username {}, password {},filename : {} ,fileparams={},dest dir={}",
				remoteserver,port,username,password,filename,fileparams,destdir);
		
		String command="cd   "+destdir+ ";" +" bash  "+filename+"  "+ (fileparams!=null?fileparams:" ") ;
		logger.debug("Execution command {}",command);
		remoteScriptExecutionService.executeRemoteScript(remoteserver,username,password,port,filename,fileparams,null,destdir,command);
		
		deployResponseSenderService.sendResponse(new BuildResponse("completed", "completed", "completed", "completed","completed",sitename,null,null,null));
	}

}
