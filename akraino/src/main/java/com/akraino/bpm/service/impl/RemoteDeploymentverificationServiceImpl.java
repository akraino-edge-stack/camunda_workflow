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
import com.akraino.bpm.service.RemoteDeploymentVerificationService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


@Service("remoteDeploymentVerificationService")
public class RemoteDeploymentverificationServiceImpl implements RemoteDeploymentVerificationService{

        private static Logger logger = LoggerFactory.getLogger(RemoteDeploymentverificationServiceImpl.class);

        public void executeScript(String remoteserver,String username,String password,int port,
                String filename,String fileparams,String srcdir,String destdir,int waitttime,int iterations,String command)  {

                boolean issuccess=true;
                int exitStatus = 0;
                //String filepath=destdir+"/"+filename+"  "+(fileparams!=null?fileparams.replaceAll(",", "  "):" ");
                ChannelExec channelExec=null;
                BufferedReader reader =null;
                Session session=null;
        
                for( int i=0;i<iterations;i++) {
                        try {
                                logger.debug("Executing the deployment verification script: {}  iteration: {}", command, i);
                                Thread.sleep(waitttime*1000);
                                JSch jsch = new JSch();
                                session = jsch.getSession(username, remoteserver, port);
                                session.setConfig("StrictHostKeyChecking", "no");
                                session.setPassword(password);
                                session.connect();
                                channelExec = (ChannelExec)session.openChannel("exec");
                                InputStream in = channelExec.getInputStream();
                                channelExec.setPty(true);
                                channelExec.setCommand(command);
                                channelExec.connect();
                                reader = new BufferedReader(new InputStreamReader(in));
                                String line;
                                logger.debug("Script output......................");
                                while ((line = reader.readLine()) != null) {
                                         logger.debug(line);
                                }
                                channelExec.disconnect();
                                while (!channelExec.isClosed()) {

                                }
                                exitStatus = channelExec.getExitStatus();
                                logger.debug("Script exit code: "+exitStatus);
                                issuccess = (exitStatus==0);
                        }
                        catch (IOException e) {
                                throw new TaskExecutorException("script: " + command + " not found.");
                        } catch (Exception e) {
                                throw new TaskExecutorException("problem while executing the script " + command);
                        } finally {
                                if(reader!=null) {
                                        try {
                                            reader.close();
                                        } catch(Exception e) {
                                            throw new TaskExecutorException("verification script failed");
                                        }
                                }
                                if(session!=null) {
                                        session.disconnect();
                                }
                        }

                        if(issuccess) {
                                break;
                        }
                }

                if(!issuccess) {
                        logger.debug("verification script failed. script: {}  last exit code: {}", command, exitStatus);
                        throw new TaskExecutorException(" verification script exit code : 1");
                }
        }
}
