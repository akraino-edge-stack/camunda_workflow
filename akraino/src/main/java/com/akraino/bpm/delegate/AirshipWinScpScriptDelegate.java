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


import com.akraino.bpm.service.ScriptExecutionService;


@Component
public class AirshipWinScpScriptDelegate implements JavaDelegate {

	private static Logger logger = LoggerFactory.getLogger(AirshipWinScpScriptDelegate.class);
	
	
	@Autowired
	ScriptExecutionService scriptExecutionService;
	
	public void execute(DelegateExecution ctx) throws Exception {
		
		String filepath=(String)ctx.getVariable("ScpScriptFilepath");
		String dir=(String)ctx.getVariable("scpdir");
		logger.debug("Win SCP task execution started  :{} , Directory :{} ",filepath,dir);
		scriptExecutionService.executeCDScript(dir, filepath);
		
		//scriptExecutionService.executeScript(filepath);
		
		
	}

}
