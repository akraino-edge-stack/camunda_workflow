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

package com.akraino.bpm.service;

import com.akraino.bpm.model.Airship;
import com.akraino.bpm.model.Apache;
import com.akraino.bpm.model.Build;
import com.akraino.bpm.model.Deploy;
import com.akraino.bpm.model.Onap;
import com.akraino.bpm.model.Tempest;

public interface AsyncProcessExecutorService {
	
	public void executeAirshipProcess(Airship airship);
	
	public void executeBuildProcess(Build build);
	
	public void executeDeployProcess(Deploy deploy);
	
	public void executeOnapProcess(Onap onap);
	
	public void executeTempestProcess(Tempest tempest);
	
	public void executeApacheProcess(Apache apache);
}
