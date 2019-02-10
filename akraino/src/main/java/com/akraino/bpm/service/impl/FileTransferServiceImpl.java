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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.service.FileTransferService;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service("fileTransferService")
public class FileTransferServiceImpl implements FileTransferService {

	private static Logger logger = LoggerFactory.getLogger(FileTransferServiceImpl.class);

	public void transferFile(String srcdir, String destdir, String filename,String servername,String username,String password,int port) {

		logger.debug("file transfor filename={},srcdir={},destdir={}",filename,srcdir,destdir);
		ChannelSftp sftpChannel=null;
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, servername, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect();
			sftpChannel = (ChannelSftp)session.openChannel("sftp");
			sftpChannel.setPty(true);
			sftpChannel.connect();
			sftpChannel.cd(destdir);
			sftpChannel.put(new FileInputStream(new File (srcdir+"/"+filename)), filename);
			sftpChannel.chmod(Integer.parseInt("777", 8),filename);
		} catch (Exception e) {
			logger.error("Exception occurred while FTP : "+e);
			throw new TaskExecutorException("problem while transferring the file to remote machine :"+e.getMessage());
		} finally {
			if (sftpChannel != null)
				sftpChannel.disconnect();
		}
	}

	public void transferFile(String srcdir, String destdir,String servername,String username,String password,int port) {

		List<File> files=getAllfiles(new File(srcdir));
		for(File file: files) {
			transferFile(srcdir,destdir,file.getName(),servername,username,password,port);
		}
	}

	private List<File> getAllfiles(File rootDirectory){
	    List<File> results = new ArrayList<File>();

	    if(rootDirectory==null) {
	    	throw new TaskExecutorException("problem while transferring the file to remote machine : src directory Not found");
	    }

		for(File currentItem : rootDirectory.listFiles()){
			if(currentItem.isDirectory()){
				results.addAll(getAllfiles(currentItem));
			}
			else{
				results.add(currentItem);
			}
		}
	    return results;
	}
}
