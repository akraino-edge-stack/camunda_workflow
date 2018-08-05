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

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.akraino.bpm.service.ScriptExecutionService;


@Component
public class ApacheScriptExecutorTaskDelegate implements JavaDelegate {

	@Autowired
	RuntimeService runtimeService;
	
	 private static Logger logger = LoggerFactory.getLogger(ApacheScriptExecutorTaskDelegate.class);
	
	@Autowired
	ScriptExecutionService scriptExecutionService;
	
	public void execute(DelegateExecution ctx) throws Exception {
		String  filename=(String)ctx.getVariable("scpfilename");
		String scpdir=(String)ctx.getVariable("scpdir");
		logger.debug("task execution started  filename:{}, directory:{}",filename,scpdir);
		scriptExecutionService.executeCDScript(scpdir, filename);
	}

}
