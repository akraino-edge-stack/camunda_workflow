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

package com.akraino.bpm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.akraino.bpm.model.Airship;
import com.akraino.bpm.model.Apache;
import com.akraino.bpm.model.Build;
import com.akraino.bpm.model.BuildResponse;
import com.akraino.bpm.model.Deploy;
import com.akraino.bpm.model.Onap;
import com.akraino.bpm.model.Tempest;
import com.akraino.bpm.model.MultiNodeDeploy;
import com.akraino.bpm.service.AsyncProcessExecutorService;

import io.swagger.annotations.Api;

@Api
@RestController
public class CamundaRestController {

	@Autowired
	private AsyncProcessExecutorService asyncProcessExecutorService;

	private static Logger logger = LoggerFactory.getLogger(CamundaRestController.class);

	@PostMapping("/build/")
	public ResponseEntity<BuildResponse> build(@RequestBody Build build) {
		logger.debug("Request received for Build {}",build.toString());
		asyncProcessExecutorService.executeBuildProcess(build);
		return new ResponseEntity<BuildResponse>(new BuildResponse("in progress",null,null,null,null,build.getSitename(),null,null,null),HttpStatus.OK);
	}

	@PostMapping("/deploy/")
	public ResponseEntity<BuildResponse> deploy(@RequestBody Deploy deploy) {
		logger.debug("Request received for deploy {}  ",deploy.toString());
		asyncProcessExecutorService.executeDeployProcess(deploy);
		return new ResponseEntity<BuildResponse>(new BuildResponse(null,"in progress","not started","not started","not started",deploy.getSitename(),null,null,null),HttpStatus.OK);
	}

	@PostMapping("/multinodedeploy/")
	public ResponseEntity<BuildResponse> multiNodeDeploy(@RequestBody MultiNodeDeploy multiNodeDeploy) {
		logger.debug("Request received for multi node deploy {}  ",multiNodeDeploy.toString());
		asyncProcessExecutorService.executeMultiNodeDeployProcess(multiNodeDeploy);
		return new ResponseEntity<BuildResponse>(new BuildResponse(null,"in progress","not started","not started","not started",multiNodeDeploy.getSitename(),null,null,null),HttpStatus.OK);
	}

	@PostMapping("/airship/")
	public ResponseEntity<BuildResponse> airship(@RequestBody Airship airship) {
		logger.debug("Request received for airship {} ",airship.toString());
		asyncProcessExecutorService.executeAirshipProcess(airship);
		return new ResponseEntity<BuildResponse>(new BuildResponse(null,null,null,null,"in progress",airship.getSitename(),null,null,null),HttpStatus.OK);
	}

	@PostMapping("/tempest/")
	public ResponseEntity<BuildResponse> tempest(@RequestBody Tempest tempest) {
		logger.debug("Request received for tempest {}",tempest.toString());
		asyncProcessExecutorService.executeTempestProcess(tempest);
		return new ResponseEntity<BuildResponse>(new BuildResponse(null,null,null,null,null,tempest.getSitename(),null,null,"in progress"),HttpStatus.OK);
	}

	@PostMapping("/apache/")
	public ResponseEntity<BuildResponse> apache(@RequestBody Apache apache) {
		logger.debug("Request received for apache{} ",apache.toString());
		asyncProcessExecutorService.executeApacheProcess(apache);
		return new ResponseEntity<BuildResponse>(new BuildResponse(null,null,null,null,null,apache.getSitename(),null,"in progress",null),HttpStatus.OK);
	}

	@PostMapping("/onap/")
	public ResponseEntity<BuildResponse> onap(@RequestBody Onap onap) {
		logger.debug("Request received for onap ",onap.toString());
		asyncProcessExecutorService.executeOnapProcess(onap);
		return new ResponseEntity<BuildResponse>(new BuildResponse(null,null,null,null,null,onap.getSitename(),"in progress",null,null),HttpStatus.OK);
	}
}
