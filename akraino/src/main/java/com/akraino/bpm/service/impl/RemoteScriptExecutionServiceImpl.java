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
import java.io.InputStream;
import java.io.InputStreamReader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.service.RemoteScriptExecutionService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service("remoteScriptExecutionService")
public class RemoteScriptExecutionServiceImpl implements RemoteScriptExecutionService{

	private static Logger logger = LoggerFactory.getLogger(RemoteScriptExecutionServiceImpl.class);
	
	public void executeRemoteScript(String remoteserver,String username,String password,int portnumber,String filename,
			String fileparams,String srcdir,String destdir,String command)  {
		
		//String filepath=destdir+"/"+filename+"  "+(fileparams!=null?fileparams.replaceAll(",", "  "):" ");
		ChannelExec channelExec=null;
		Session session=null;
		BufferedReader reader =null;
		try {
			//tranferFile(remoteserver,portnumber,username,password,filename,srcdir,destdir);
			logger.debug("executing the script  "+command);
			JSch jsch = new JSch();
			session = jsch.getSession(username, remoteserver, portnumber);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect();
			session.setTimeout(36000000);
			channelExec = (ChannelExec)session.openChannel("exec");
			InputStream in = channelExec.getInputStream();
			channelExec.setCommand(command);
			channelExec.setPty(true);
			
			channelExec.connect(36000000);
			reader = new BufferedReader(new InputStreamReader(in));
			String line;
			logger.debug("Script output......................");
			while ((line = reader.readLine()) != null){
				 logger.debug(line);
			}
			
			channelExec.disconnect();
	        while (!channelExec.isClosed()) {

	        }

			int exitStatus = channelExec.getExitStatus();
			
            logger.debug("Script exit code :"+exitStatus);
            if(exitStatus!=0) {
            	throw new TaskExecutorException("problem while executing the script . exist code :"+exitStatus);
            }
            
			
		} catch (IOException e) {
			throw new TaskExecutorException(filename + " not found.");
		} catch (Exception e) {
			throw new TaskExecutorException("Problem while executing script"+e.getMessage());
		}finally{
			if(reader!=null) {
				try {
					reader.close();
				}catch(Exception e) {
					throw new TaskExecutorException("onap build failed");
				}
			}
			if(session!=null) {
				session.disconnect();
			}
		}
		
		
		
	}
	
	
	

}
	 
	 
