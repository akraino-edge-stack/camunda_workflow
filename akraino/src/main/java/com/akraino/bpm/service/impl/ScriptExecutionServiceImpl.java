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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.service.ScriptExecutionService;

@Service("scriptExecutionService")
public class ScriptExecutionServiceImpl implements ScriptExecutionService{

	private static Logger logger = LoggerFactory.getLogger(ScriptExecutionServiceImpl.class);

	public void executeScript(String filepatch)  {

		try {
			logger.debug("Executing the script.............");
			Process p = Runtime.getRuntime().exec(filepatch);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
            	logger.debug(line);
            }
            p.waitFor();
            logger.debug("Script exit code :"+p.exitValue());
            if(p.exitValue()!=0) {
            	throw new TaskExecutorException("problem while executing the script . exit code :"+p.exitValue());
            }

		} catch (IOException e) {
			throw new TaskExecutorException(filepatch + " not found.");
		} catch (InterruptedException e) {
			throw new TaskExecutorException("problem while executing the script "+filepatch);
		}

	}
		public void executeCDScript(String dir,String cmd)  {

			try {
				logger.debug("Executing the script.............dir:{},command:{}",dir,cmd);

				String[] command = { "/bin/bash", "-c", "bash  "+cmd };
				Process p = Runtime.getRuntime().exec(command, null, new File(dir));
				p.waitFor();
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line = "";
	            while ((line = input.readLine()) != null) {
	            	logger.debug(line);
	            }
	            logger.debug("Script exit code :"+p.exitValue());
	            if(p.exitValue()!=0) {
	            	throw new TaskExecutorException("problem while executing the script . exist code :"+p.exitValue());
	            }


			} catch (IOException e) {
				throw new TaskExecutorException(cmd + " not found.");
			} catch (InterruptedException e) {
				throw new TaskExecutorException("problem while executing the script "+cmd);
			}

	}


		public void executeCDBashScript(String dir,String cmd)  {

			try {
				logger.debug("Executing the script.............dir:{},command:{}",dir,cmd);

				String[] command = { "/bin/bash", "-c", "bash  "+cmd };
				Process p = Runtime.getRuntime().exec(command, null, new File(dir));
				p.waitFor();
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line = "";
	            while ((line = input.readLine()) != null) {
	            	logger.debug(line);
	            }
	            logger.debug("Script exit code :"+p.exitValue());
	            if(p.exitValue()!=0) {
	            	throw new TaskExecutorException("problem while executing the script . exit code :"+p.exitValue());
	            }


			} catch (IOException e) {
				throw new TaskExecutorException(cmd + " not found.");
			} catch (InterruptedException e) {
				throw new TaskExecutorException("problem while executing the script "+cmd);
			}

	}




		/*public void executeAirshipScript(String cmd)  {

			try {
				logger.debug("Executing the script.............{}",cmd);
				ProcessBuilder pb = new ProcessBuilder(cmd);
				Process shellProcess = pb.start();

				shellProcess.waitFor();
				BufferedReader input = new BufferedReader(new InputStreamReader(shellProcess.getInputStream()));
	            String line = "";
	            while ((line = input.readLine()) != null) {
	            	logger.debug(line);
	            }
	            logger.debug("Script exit code :"+shellProcess.exitValue());
	            if(shellProcess.exitValue()!=0) {
	            	throw new TaskExecutorException("problem while executing the script . exist code :"+shellProcess.exitValue());
	            }


			} catch (IOException e) {
				throw new TaskExecutorException(cmd + " not found.");
			} catch (InterruptedException e) {
				throw new TaskExecutorException("problem while executing the script "+cmd);
			}

	}*/
}
