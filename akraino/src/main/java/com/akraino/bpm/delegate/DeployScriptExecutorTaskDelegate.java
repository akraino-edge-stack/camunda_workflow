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
import com.akraino.bpm.service.ScriptExecutionService;


@Component
public class DeployScriptExecutorTaskDelegate implements JavaDelegate {

	 private static Logger logger = LoggerFactory.getLogger(DeployScriptExecutorTaskDelegate.class);
	
	@Autowired
	ScriptExecutionService scriptExecutionService;
	
	@Autowired 
	DeployResponseSenderService deployResponseSenderService;
	
	public void execute(DelegateExecution ctx) throws Exception {
			deployResponseSenderService.sendResponse(new BuildResponse("completed", "inprogress", "not started", "not started","not started","Boston"));
			String  filepath=(String)ctx.getVariable("filepath");
			logger.debug("task execution started  :"+filepath);
			scriptExecutionService.executeScript(filepath);
		
	}

}
