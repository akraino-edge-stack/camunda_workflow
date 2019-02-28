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
import com.akraino.bpm.model.BuildResponse;
import com.akraino.bpm.service.DeployResponseSenderService;
import com.akraino.bpm.service.RemoteScriptExecutionService;

@Component
public class DeployPostVerficationRemoteScriptExecutor implements JavaDelegate {

        private static Logger logger = LoggerFactory.getLogger(DeployPostVerficationRemoteScriptExecutor.class);

        @Autowired
        RemoteScriptExecutionService remoteScriptExecutionService;

        @Autowired
        DeployResponseSenderService deployResponseSenderService;

        public void execute(DelegateExecution ctx) throws Exception {
        
                try {
                        String remoteserver =(String) ctx.getVariable("remotserver");
                        int    portnumber   =(Integer)ctx.getVariable("port");
                        String username     =(String) ctx.getVariable("username");
                        String password     =(String) ctx.getVariable("password");
                        String filename     =(String) ctx.getVariable("postverificationscript");
                        String fileparams   =(String) ctx.getVariable("postverificationScriptparams");
                        String srcdir       =(String) ctx.getVariable("srcdir");
                        String destdir      =(String) ctx.getVariable("destdir");
                        String sitename     =(String) ctx.getVariable("sitename");
                        String blueprint    =(String) ctx.getVariable("blueprint");

                        deployResponseSenderService.sendResponse(new BuildResponse("completed", "completed", "completed", "completed","inprogress",sitename,null,null,null));
                        logger.debug("task execution started blueprint {}, remoteserver {}, portnumber {}, username {}, password {}, filename {}, fileparams {}, src dir={}, dest dir={}",
                                                             blueprint,    remoteserver,    portnumber,    username,    password,    filename,    fileparams,    srcdir,     destdir);

                        String command = String.format("/bin/bash %s/%s %s", destdir, filename, (fileparams!=null?fileparams.replaceAll(",", "  "):""));
                        if ( filename != null && !filename.equals("null") && !filename.isEmpty() ) {
                                logger.debug("Execution command {}",command);
                                remoteScriptExecutionService.executeRemoteScript(remoteserver,username,password,portnumber,filename,fileparams,srcdir,destdir,command);
                        } else {
                                logger.debug("Skipping invalid verification command: {}", command);
                        }
                } catch(TaskExecutorException ex) {
                        throw ex;
                }
        }
}

