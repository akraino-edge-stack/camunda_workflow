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

package com.akraino.bpm.model;

public class Airship {
	
	private String filepath;
	private String fileparams;
	private String winscpdir;
	private String winscpfilepath;
	private String winscpfileparams;
	private String remotserver;
	private int port;
	private String username;
	private String password;
	private String destdir;
	private String remotefilename;
	private String remotefileparams;
	
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFileparams() {
		return fileparams;
	}
	public void setFileparams(String fileparams) {
		this.fileparams = fileparams;
	}
	public String getWinscpfilepath() {
		return winscpfilepath;
	}
	public void setWinscpfilepath(String winscpfilepath) {
		this.winscpfilepath = winscpfilepath;
	}
	public String getWinscpfileparams() {
		return winscpfileparams;
	}
	public void setWinscpfileparams(String winscpfileparams) {
		this.winscpfileparams = winscpfileparams;
	}
	public String getDestdir() {
		return destdir;
	}
	public void setDestdir(String destdir) {
		this.destdir = destdir;
	}
	
	public String getRemotserver() {
		return remotserver;
	}
	public void setRemotserver(String remotserver) {
		this.remotserver = remotserver;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemotefilename() {
		return remotefilename;
	}
	public void setRemotefilename(String remotefilename) {
		this.remotefilename = remotefilename;
	}
	public String getRemotefileparams() {
		return remotefileparams;
	}
	public void setRemotefileparams(String remotefileparams) {
		this.remotefileparams = remotefileparams;
	}
	public String getWinscpdir() {
		return winscpdir;
	}
	public void setWinscpdir(String winscpdir) {
		this.winscpdir = winscpdir;
	}
	@Override
	public String toString() {
		return "Airship [filepath=" + filepath + ", fileparams=" + fileparams + ", winscpdir=" + winscpdir
				+ ", winscpfilepath=" + winscpfilepath + ", winscpfileparams=" + winscpfileparams + ", remotserver="
				+ remotserver + ", port=" + port + ", username=" + username + ", password=" + password + ", destdir="
				+ destdir + ", remotefilename=" + remotefilename + ", remotefileparams=" + remotefileparams + "]";
	}
	
	
	
	
	
 }
