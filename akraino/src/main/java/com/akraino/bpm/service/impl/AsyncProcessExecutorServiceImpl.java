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

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.model.Airship;
import com.akraino.bpm.model.Apache;
import com.akraino.bpm.model.Build;
import com.akraino.bpm.model.BuildResponse;
import com.akraino.bpm.model.Deploy;
import com.akraino.bpm.model.MultiNodeDeploy;
import com.akraino.bpm.model.Onap;
import com.akraino.bpm.model.Tempest;
import com.akraino.bpm.service.AsyncProcessExecutorService;
import com.akraino.bpm.service.DeployResponseSenderService;

@Service("asyncProcessExecutorService")
public class AsyncProcessExecutorServiceImpl implements AsyncProcessExecutorService{

	  @Autowired
	  private ProcessEngine camunda;
	  
	  @Autowired 
	  private DeployResponseSenderService  deployResponseSenderService;
	  
	  private static Logger logger = LoggerFactory.getLogger(AsyncProcessExecutorServiceImpl.class);
	  
	@Async
	public void executeAirshipProcess(Airship airship) {
		
		 try {
			executeAirshipservice(airship);
		  }catch(TaskExecutorException ex) {
			  logger.error("Airship execution failed ",ex);
			  deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,"exception: "+ex.getMessage(),airship.getSitename(),null,null,null));
			  return;
		}
		 logger.debug("Airship execution success ");
		 deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,"success",airship.getSitename(),null,null,null));
		
		
		
	}

	private ProcessInstance executeAirshipservice(Airship airship) {
		
		String filepath1=airship.getFilepath()+"  "+(airship.getFileparams()!=null?airship.getFileparams().replaceAll(",", "  "):" ");
		String filepath2=airship.getWinscpfilepath()+"  "+(airship.getWinscpfileparams()!=null?airship.getWinscpfileparams().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("airship",
				Variables.putValue("filepath", filepath1).putValue("scpdir", airship.getWinscpdir()).putValue("ScpScriptFilepath", filepath2).putValue("remotserver", airship.getRemotserver())
				.putValue("username", airship.getUsername())
				.putValue("password", airship.getPassword())
				.putValue("port", airship.getPort()).putValue("destdir",airship.getDestdir()).putValue("remotefilename", airship.getRemotefilename())
				.putValue("remotefileparams", airship.getRemotefileparams()).putValue("sitename", airship.getSitename()));
		
	}

	@Async
	public void executeBuildProcess(Build build) {
		
		 try {
			executeBuildService(build);
		  }catch(TaskExecutorException ex) {
			  logger.error("Build execution failed ",ex);
			  deployResponseSenderService.sendResponse(new BuildResponse("exception: "+ex.getMessage(),null,null,null,null,build.getSitename(),null,null,null));
			  return;
		}
		 logger.debug("Build execution success ");
		 deployResponseSenderService.sendResponse(new BuildResponse("success",null,null,null,null,build.getSitename(),null,null,null));
	}

	
	private ProcessInstance executeBuildService(Build build) {
		
		String filepath=build.getFilepath()+"  "+(build.getFileparams()!=null?build.getFileparams().replaceAll(",", "  "):" ");
		return camunda.getRuntimeService().startProcessInstanceByKey("build",
				Variables.putValue("filepath", filepath).putValue("targetfolder", build.getTargetfolder()));
	}
	
	@Async
	public void executeDeployProcess(Deploy deploy) {
		
		 try {
			 executeDeployService(deploy);
		  }catch(TaskExecutorException ex) {
			  logger.error("deploy execution failed ",ex);
			  deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,"exception: "+ex.getMessage(),deploy.getSitename(),null,null,null));
			  return;
		}
		 logger.debug("deploy execution success ");
		 deployResponseSenderService.sendResponse(new BuildResponse("success","success","success","success","success",deploy.getSitename(),null,null,null));
	}
	
	
	private ProcessInstance executeDeployService(Deploy deploy) {
		
		String filepath1=deploy.getFilepath()+"  "+(deploy.getFileparams()!=null?deploy.getFileparams().replaceAll(",", "  "):" ");
		
		
		int lastindex=deploy.getWinscpfilepath().lastIndexOf("/");
		String scpSrcDir=deploy.getWinscpfilepath().substring(0,lastindex);
		String scpfilename=deploy.getWinscpfilepath().substring(lastindex+1,deploy.getWinscpfilepath().length());
		String transferfile= scpfilename+"  "+(deploy.getWinscpfileparams()!=null?deploy.getWinscpfileparams().replaceAll(",", "  "):" ");
		
		
		
		return camunda.getRuntimeService().startProcessInstanceByKey("deploy",
				Variables.putValue("filepath", filepath1).putValue("scpsrcdir", scpSrcDir).
				putValue("ScpScriptFilepath", transferfile).putValue("remotserver", deploy.getRemotserver())
				.putValue("username", deploy.getUsername())
				.putValue("password", deploy.getPassword())
				.putValue("port", deploy.getPort()).putValue("destdir",deploy.getDestdir()).putValue("remotefilename", deploy.getRemotefilename())
				.putValue("fileparams", deploy.getFileparams()).
				putValue("verifier", deploy.getDeploymentverifier()).putValue("verifierfileparams", 
				deploy.getDeploymentverifierfileparams()).putValue("waittime", deploy.getWaittime()).
				putValue("iterations", deploy.getNoofiterations()).putValue("postverificationscript", deploy.getPostverificationscript()).
				putValue("postverificationScriptparams", deploy.getDeploymentverifierfileparams()).putValue("sitename", deploy.getSitename()));
		
	}

	@Async
	public void executeOnapProcess(Onap onap) {
		
		try {
			executeOnapService(onap);
		  }catch(TaskExecutorException ex) {
			  logger.error("Onap execution failed ",ex);
			  deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,null,onap.getSitename(),"exception: "+ex.getMessage(),null,null));
			  return;
		}
		 logger.debug("Onap execution success ");
		 deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,null,onap.getSitename(),"succes",null,null));
		
		
	}
	
	
	private ProcessInstance executeOnapService(Onap onap) {
		
		int lastindex=onap.getFiletrasferscript().lastIndexOf("/");
		String scpSrcDir=onap.getFiletrasferscript().substring(0,lastindex);
		String scpfilename=onap.getFiletrasferscript().substring(lastindex+1,onap.getFiletrasferscript().length());
		String transferfile= scpfilename+"  "+(onap.getFiletransferparams()!=null?onap.getFiletransferparams().replaceAll(",", "  "):" ");
		
		
		return camunda.getRuntimeService().startProcessInstanceByKey("onap",
				Variables.putValue("filename", onap.getFilename()).putValue("fileparams", onap.getFileparams()).
				putValue("verifier", onap.getDeploymentverifier()).putValue("verifierfileparams", onap.getVerifierparams()).putValue("waittime", onap.getWaittime()).
				putValue("iterations", onap.getNoofiterations()).putValue("remotserver", onap.getRemoteserver())
				.putValue("username", onap.getUsername())
				.putValue("password", onap.getPassword())
				.putValue("port", onap.getPortnumber()).putValue("srcdir", onap.getSrcdir()).putValue("destdir",onap.getDestdir()).putValue("ScpScriptFilepath", transferfile).
				putValue("scpsrcdir", scpSrcDir)
				);
		
	}
	
	
	@Async
	public void executeTempestProcess(Tempest tempest) {
		 try {
			executeTempestService(tempest);
		  }catch(TaskExecutorException ex) {
			  logger.error("Tempest execution failed ",ex);
			  deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,null,tempest.getSitename(),null,null,"exception: "+ex.getMessage()));
			  return;
		}
		 logger.debug("Tempest execution success ");
		 deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,null,tempest.getSitename(),null,null,"success"));
		
	}
	
		private ProcessInstance executeTempestService(Tempest tempest) {
		
			int lastindex=tempest.getFiletrasferscript().lastIndexOf("/");
			String scpSrcDir=tempest.getFiletrasferscript().substring(0,lastindex);
			String scpfilename=tempest.getFiletrasferscript().substring(lastindex+1,tempest.getFiletrasferscript().length());
			String transferfile= scpfilename+"  "+(tempest.getFiletransferparams()!=null?tempest.getFiletransferparams().replaceAll(",", "  "):" ");
		
			return camunda.getRuntimeService().startProcessInstanceByKey("tempest",
				Variables.putValue("filename", tempest.getFilename()).putValue("fileparams", tempest.getFileparams()).
				putValue("verifier", tempest.getDeploymentverifier()).putValue("verifierfileparams", tempest.getVerifierparams()).putValue("waittime", tempest.getWaittime()).
				putValue("iterations", tempest.getNoofiterations()).putValue("remotserver", tempest.getRemoteserver())
				.putValue("username", tempest.getUsername())
				.putValue("password", tempest.getPassword())
				.putValue("port", tempest.getPortnumber()).putValue("srcdir", tempest.getSrcdir()).putValue("destdir",tempest.getDestdir()).putValue("ScpScriptFilepath", transferfile).
				putValue("scpsrcdir", scpSrcDir)
				);
		
		}
	
	
		@Async
		public void executeApacheProcess(Apache apache) {
			 try {
				executeApacheService(apache);
			  }catch(TaskExecutorException ex) {
				  logger.error("Apache execution failed ",ex);
				  deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,null,apache.getSitename(),null,"exception: "+ex.getMessage(),null));
				  return;
			}
			 logger.debug("Apache execution success ");
			 deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,null,apache.getSitename(),null,"success",null));
			
			
		}
		
		private ProcessInstance executeApacheService(Apache apache) {
			
			int lastindex=apache.getFiletrasferscript().lastIndexOf("/");
			String scpSrcDir=apache.getFiletrasferscript().substring(0,lastindex);
			String scpfilename=apache.getFiletrasferscript().substring(lastindex+1,apache.getFiletrasferscript().length());
			
			String transferfile= scpfilename+"  "+(apache.getFiletransferparams()!=null?apache.getFiletransferparams().replaceAll(",", "  "):" ");
			
			return camunda.getRuntimeService().startProcessInstanceByKey("apache",
					Variables.putValue("filename", apache.getFilename()).putValue("fileparams", apache.getFileparams()).
					putValue("verifier", apache.getDeploymentverifier()).putValue("verifierfileparams", apache.getVerifierparams()).putValue("waittime", apache.getWaittime()).
					putValue("iterations", apache.getNoofiterations()).putValue("remotserver", apache.getRemoteserver())
					.putValue("username", apache.getUsername())
					.putValue("password", apache.getPassword())
					.putValue("port", apache.getPortnumber()).
					putValue("srcdir", apache.getSrcdir()).putValue("destdir",apache.getDestdir()).
					putValue("scpfilename", transferfile).putValue("scpdir", scpSrcDir)
					);
			
		}
		
		@Async
		public void executeMultiNodeDeployProcess(MultiNodeDeploy multiNodeDeploy) {
			try {
				executeMultiNodeDeployService(multiNodeDeploy);
				}catch(TaskExecutorException ex) {
					  logger.error("MultiNodeDeploy execution failed ",ex);
					  deployResponseSenderService.sendResponse(new BuildResponse(null,null,null,null,"exception: "+ex.getMessage(),multiNodeDeploy.getSitename(),null,null,null));
					  return;
			}
			logger.debug("MultiNodeDeploy execution success ");
			deployResponseSenderService.sendResponse(new BuildResponse("success","success","success","success","success",multiNodeDeploy.getSitename(),null,null,null));
				
		}

		private ProcessInstance executeMultiNodeDeployService(MultiNodeDeploy multiNodeDeploy) {
			
			int lastindex=multiNodeDeploy.getWinscpfilepath().lastIndexOf("/");
			String scpSrcDir=multiNodeDeploy.getWinscpfilepath().substring(0,lastindex);
			String scpfilename=multiNodeDeploy.getWinscpfilepath().substring(lastindex+1,multiNodeDeploy.getWinscpfilepath().length());
			
			String transferfile= scpfilename+"  "+(multiNodeDeploy.getWinscpfileparams()!=null?multiNodeDeploy.getWinscpfileparams().replaceAll(",", "  "):" ");
			
			return camunda.getRuntimeService().startProcessInstanceByKey("multinodedeploy",
					Variables.putValue("file1", multiNodeDeploy.getFile1()).putValue("file1params", multiNodeDeploy.getFile1params())
					.putValue("winscpdir", scpSrcDir)
					.putValue("scpfilename", transferfile)
					.putValue("remotserver", multiNodeDeploy.getRemotserver())
					.putValue("username", multiNodeDeploy.getUsername())
					.putValue("password", multiNodeDeploy.getPassword())
					.putValue("port", multiNodeDeploy.getPort())
					.putValue("destdir1",multiNodeDeploy.getDestdir1()).putValue("destdir2", multiNodeDeploy.getDestdir2())
					.putValue("remotefile1", multiNodeDeploy.getRemotefile1()).putValue("remotefile1params", multiNodeDeploy.getRemotefile1params())
					.putValue("sitename", multiNodeDeploy.getSitename()).putValue("remotefile2", multiNodeDeploy.getRemotefile2()).putValue("remotefile2params", multiNodeDeploy.getRemotefile2params())
					);
				
		}

}
