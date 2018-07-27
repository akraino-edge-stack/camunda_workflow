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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import com.akraino.bpm.Exception.TaskExecutorException;
import com.akraino.bpm.service.YamlVerificationService;

@Service("yamlVerificationService")
public class YamlVerificationServiceImpl implements YamlVerificationService{

	private static Logger logger = LoggerFactory.getLogger(YamlVerificationServiceImpl.class);
	
	@Autowired
	YamlFileFilter yamlFileFilter;
	
	public void verify(String taget)  {
		
		logger.debug("Verifying the YAML files .............");
		List<File> yamlFileList =searchForFile(new File(taget),yamlFileFilter);
		for(File file:yamlFileList)	{
			try {
				Object obj =check(file);
				if(obj==null) {
					throw new TaskExecutorException(file + "parsing failed.");
				}
			}catch(Exception ex ) {
				throw new TaskExecutorException(file + " not found.");
			}
			
			
			
		}
	}
	
	private List<File> searchForFile(File rootDirectory, FileFilter filter){
	    List<File> results = new ArrayList<File>();
	    for(File currentItem : rootDirectory.listFiles(filter)){
	      if(currentItem.isDirectory()){
	          results.addAll(searchForFile(currentItem, filter));
	      }
	      else{
	          results.add(currentItem);
	      }
	    }
	    return results;
	}
	
	

   private Object check(File file) throws IOException {
    		Yaml yaml = new Yaml();
            Object obj = yaml.load(new FileInputStream(file));
            return obj;
    }
    

}
