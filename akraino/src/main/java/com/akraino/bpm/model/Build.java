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

public class Build {
	
	private String filepath;
	private String fileparams;
	private String targetfolder;
	
	
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
	public String getTargetfolder() {
		return targetfolder;
	}
	public void setTargetfolder(String targetfolder) {
		this.targetfolder = targetfolder;
	}
	@Override
	public String toString() {
		return "Build [filepath=" + filepath + ", fileparams=" + fileparams + ", targetfolder=" + targetfolder + "]";
	}
	
	
	
	
}
