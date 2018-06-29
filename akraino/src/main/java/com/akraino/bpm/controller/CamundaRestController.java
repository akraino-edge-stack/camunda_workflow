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

package com.akraino.bpm.controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.model.Airship;
import com.akraino.bpm.model.Apache;
import com.akraino.bpm.model.Build;
import com.akraino.bpm.model.Deploy;
import com.akraino.bpm.model.Onap;
import com.akraino.bpm.model.Tempest;
import com.akraino.bpm.model.WorkflowResponse;

@RestController
public class CamundaRestController {
	
	  @Autowired
	  private ProcessEngine camunda;
	  
	  private static Logger logger = LoggerFactory.getLogger(CamundaRestController.class);
	  
	  @PostMapping("/build/")
	  public ResponseEntity<WorkflowResponse> build(@RequestBody Build build) {
		  logger.debug("Request received for Build ",build.toString());
		  
		  try {
			  executeBuildProcess(build);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing Build . exception : "+ex.getMessage()),HttpStatus.OK);
		  }
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200,"Build successfully completed."),HttpStatus.OK);
	}

	  private ProcessInstance executeBuildProcess(Build build) {
		
		String filepath=build.getFilepath()+"  "+(build.getFileparams()!=null?build.getFileparams().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("build",
				Variables.putValue("filepath", filepath).putValue("targetfolder", build.getTargetfolder()));
	}
	
	
	@PostMapping("/deploy/")
	  public ResponseEntity<WorkflowResponse> deploy(@RequestBody Deploy deploy) {
		  logger.debug("Request received for executing {} and targetDirectory {} ",deploy.toString());
		  try {
			  executeDeployProcess(deploy);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing genesis build. exception : "+ex.getMessage()),HttpStatus.OK);
		}
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200,"Genesis build completed ."),HttpStatus.OK);
	}

	private ProcessInstance executeDeployProcess(Deploy deploy) {
		
		String filepath1=deploy.getFilepath()+"  "+(deploy.getFileparams()!=null?deploy.getFileparams().replaceAll(",", "  "):" ");
		String filepath2=deploy.getWinscpfilepath()+"  "+(deploy.getWinscpfileparams()!=null?deploy.getWinscpfileparams().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("deploy",
				Variables.putValue("filepath", filepath1).putValue("ScpScriptFilepath", filepath2).putValue("remotserver", deploy.getRemotserver())
				.putValue("username", deploy.getUsername())
				.putValue("password", deploy.getPassword())
				.putValue("port", deploy.getPort()).putValue("destdir",deploy.getDestdir()).putValue("remotefilename", deploy.getRemotefilename())
				.putValue("fileparams", deploy.getFileparams()).
				putValue("verifier", deploy.getDeploymentverifier()).putValue("verifierfileparams", 
				deploy.getDeploymentverifierfileparams()).putValue("waittime", deploy.getWaittime()).
				putValue("iterations", deploy.getNoofiterations()).putValue("postverificationscript", deploy.getPostverificationscript()).putValue("postverificationScriptparams", deploy.getDeploymentverifierfileparams()));
		
	}
	
	
	@PostMapping("/airship/")
	  public ResponseEntity<WorkflowResponse> deploy(@RequestBody Airship airship) {
		  logger.debug("Request received for airship {} and targetDirectory {} ",airship.toString());
		  try {
			  executeAirshipProcess(airship);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing genesis airship. exception : "+ex.getMessage()),HttpStatus.OK);
		}
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200,"Airship is completed ."),HttpStatus.OK);
	}

	private ProcessInstance executeAirshipProcess(Airship airship) {
		
		String filepath1=airship.getFilepath()+"  "+(airship.getFileparams()!=null?airship.getFileparams().replaceAll(",", "  "):" ");
		String filepath2=airship.getWinscpfilepath()+"  "+(airship.getWinscpfileparams()!=null?airship.getWinscpfileparams().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("airship",
				Variables.putValue("filepath", filepath1).putValue("scpdir", airship.getWinscpdir()).putValue("ScpScriptFilepath", filepath2).putValue("remotserver", airship.getRemotserver())
				.putValue("username", airship.getUsername())
				.putValue("password", airship.getPassword())
				.putValue("port", airship.getPort()).putValue("destdir",airship.getDestdir()).putValue("remotefilename", airship.getRemotefilename())
				.putValue("remotefileparams", airship.getRemotefileparams()));
		
	}
	
	
	@PostMapping("/tempest/")
	  public ResponseEntity<WorkflowResponse> tempest(@RequestBody Tempest tempest) {
		  logger.debug("Request received for onap ",tempest.toString());
		  try {
			  executeTempestProcess(tempest);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing Tempset API. exception : "+ex.getMessage()),HttpStatus.OK);
		}
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200,"tempeset is completed successfully."),HttpStatus.OK);
	}
	
	private ProcessInstance executeTempestProcess(Tempest tempest) {
		
		String transferfile= tempest.getFiletrasferscript()+"  "+(tempest.getFiletransferparams()!=null?tempest.getFiletransferparams().replaceAll(",", "  "):" ");
		
		return camunda.getRuntimeService().startProcessInstanceByKey("tempest",
				Variables.putValue("filename", tempest.getFilename()).putValue("fileparams", tempest.getFileparams()).
				putValue("verifier", tempest.getDeploymentverifier()).putValue("verifierfileparams", tempest.getVerifierparams()).putValue("waittime", tempest.getWaittime()).
				putValue("iterations", tempest.getNoofiterations()).putValue("remotserver", tempest.getRemoteserver())
				.putValue("username", tempest.getUsername())
				.putValue("password", tempest.getPassword())
				.putValue("port", tempest.getPortnumber()).putValue("srcdir", tempest.getSrcdir()).putValue("destdir",tempest.getDestdir()).putValue("filepath", transferfile)
				);
		
	}
	
	
	@PostMapping("/apache/")
	  public ResponseEntity<WorkflowResponse> apache(@RequestBody Apache apache) {
		  logger.debug("Request received for onap ",apache.toString());
		  try {
			  executeapacheProcess(apache);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing Apache API. exception : "+ex.getMessage()),HttpStatus.OK);
		}
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200,"apache is completed successfully."),HttpStatus.OK);
	}
	
	private ProcessInstance executeapacheProcess(Apache apache) {
		
		String transferfile= apache.getFiletrasferscript()+"  "+(apache.getFiletransferparams()!=null?apache.getFiletransferparams().replaceAll(",", "  "):" ");
		
		return camunda.getRuntimeService().startProcessInstanceByKey("apache",
				Variables.putValue("filename", apache.getFilename()).putValue("fileparams", apache.getFileparams()).
				putValue("verifier", apache.getDeploymentverifier()).putValue("verifierfileparams", apache.getVerifierparams()).putValue("waittime", apache.getWaittime()).
				putValue("iterations", apache.getNoofiterations()).putValue("remotserver", apache.getRemoteserver())
				.putValue("username", apache.getUsername())
				.putValue("password", apache.getPassword())
				.putValue("port", apache.getPortnumber()).putValue("srcdir", apache.getSrcdir()).putValue("destdir",apache.getDestdir()).putValue("filepath", transferfile)
				);
		
	}
	
	
 @PostMapping("/onap/")
	  public ResponseEntity<WorkflowResponse> onap(@RequestBody Onap onap) {
		  logger.debug("Request received for onap ",onap.toString());
		  try {
			  executeOnapProcess(onap);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing onap build. exception : "+ex.getMessage()),HttpStatus.OK);
		}
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200,"Onap build completed ."),HttpStatus.OK);
	}
	
	private ProcessInstance executeOnapProcess(Onap onap) {
		
		String transferfile= onap.getFiletrasferscript()+"  "+(onap.getFiletransferparams()!=null?onap.getFiletransferparams().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("onap",
				Variables.putValue("filename", onap.getFilename()).putValue("fileparams", onap.getFileparams()).
				putValue("verifier", onap.getDeploymentverifier()).putValue("verifierfileparams", onap.getVerifierparams()).putValue("waittime", onap.getWaittime()).
				putValue("iterations", onap.getNoofiterations()).putValue("remotserver", onap.getRemoteserver())
				.putValue("username", onap.getUsername())
				.putValue("password", onap.getPassword())
				.putValue("port", onap.getPortnumber()).putValue("srcdir", onap.getSrcdir()).putValue("destdir",onap.getDestdir()).putValue("filepath", transferfile)
				);
		
	}


	
	
	/*@PostMapping("/singlestepdeploy/")
	  public ResponseEntity<WorkflowResponse> singelStepDeploy(@RequestBody Deploy deploy) {
		  logger.debug("Request received for onap ",deploy.toString());
		  try {
			  executeOnapProcess(deploy);
		  }catch(TaskExecutorException ex) {
			  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(500,"Problem while executing single step deploymewnt. exception : "+ex.getMessage()),HttpStatus.OK);
		}
		  return new ResponseEntity<WorkflowResponse>(new WorkflowResponse(200," single step deployment completed ."),HttpStatus.OK);
	}

	private ProcessInstance executeOnapProcess(Deploy deploy) {
		
		String filepath1="bash   " + deploy.getFilepath1()+"  "+(deploy.getFile1params()!=null?deploy.getFile1params().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("singlestepdeploy",Variables.putValue("filepath", filepath1));
		
	}*/
	  
}
