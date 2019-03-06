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

public class MultiNodeDeploy {
	
	private String sitename;
	private String file1;
	private String file1params;
	private String winscpfilepath;
	private String winscpfileparams;
	private String remoteserver;
	private int port;
	private String username;
	private String password;
	private String destdir1;
	private String remotefile1;
	private String remotefile1params;
	private String destdir2;
	private String remotefile2;
	private String remotefile2params;
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getFile1() {
		return file1;
	}
	public void setFile1(String file1) {
		this.file1 = file1;
	}
	public String getFile1params() {
		return file1params;
	}
	public void setFile1params(String file1params) {
		this.file1params = file1params;
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
	public String getRemoteserver() {
		return remoteserver;
	}
	public void setRemoteserver(String remoteserver) {
		this.remoteserver = remoteserver;
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
	
	public String getRemotefile1() {
		return remotefile1;
	}
	public void setRemotefile1(String remotefile1) {
		this.remotefile1 = remotefile1;
	}
	public String getRemotefile1params() {
		return remotefile1params;
	}
	public void setRemotefile1params(String remotefile1params) {
		this.remotefile1params = remotefile1params;
	}
	public String getRemotefile2() {
		return remotefile2;
	}
	public void setRemotefile2(String remotefile2) {
		this.remotefile2 = remotefile2;
	}
	public String getRemotefile2params() {
		return remotefile2params;
	}
	public void setRemotefile2params(String remotefile2params) {
		this.remotefile2params = remotefile2params;
	}
	public String getDestdir1() {
		return destdir1;
	}
	public void setDestdir1(String destdir1) {
		this.destdir1 = destdir1;
	}
	public String getDestdir2() {
		return destdir2;
	}
	public void setDestdir2(String destdir2) {
		this.destdir2 = destdir2;
	}
	
	
	@Override
	public String toString() {
		return "MultiNodeDeploy [sitename=" + sitename + ", file1=" + file1 + ", file1params=" + file1params
				+ ", winscpfilepath=" + winscpfilepath + ", winscpfileparams=" + winscpfileparams + ", remoteserver="
				+ remoteserver + ", port=" + port + ", username=" + username + ", password=" + password + ", destdir1="
				+ destdir1 + ", remotefile1=" + remotefile1 + ", remotefile1params=" + remotefile1params + ", destdir2="
				+ destdir2 + ", remotefile2=" + remotefile2 + ", remotefile2params=" + remotefile2params + "]";
	}
	
	
	
 }
