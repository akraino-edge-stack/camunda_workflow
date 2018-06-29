#!/bin/bash
#
# Copyright (c) 2018 AT&T Intellectual Property.  All other rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#Pass version number as argument to run script:
#example : ./build.sh 1.0.0-SNAPSHOT
CON_NAME="akraino-camunda-workflow-engine"
version=$1
build=`curl -s  http://nexus3.att-akraino.org/repository/maven-snapshots/org/akraino/camunda_workflow/$version/maven-metadata.xml | grep '<value>' | head -1 | sed "s/.*<value>\([^<]*\)<\/value>.*/\1/"`
jar=camunda_workflow-$build.jar 
path="http://nexus3.att-akraino.org/repository/maven-snapshots/org/akraino/camunda_workflow/$version"
JARURL="$path/$jar"
wget $JARURL

VERSION=$build
sudo docker build -f Dockerfile -t "nexus3.att-akraino.org:10001/${CON_NAME}:${VERSION}" .
sudo docker tag  "nexus3.att-akraino.org:10001/${CON_NAME}:${VERSION}"  "nexus3.att-akraino.org:10001/${CON_NAME}:latest"
sudo docker login --username jenkins --password ZFXsSJjs nexus3.att-akraino.org:10001
sudo docker push "nexus3.att-akraino.org:10001/${CON_NAME}:${VERSION}"
sudo docker push "nexus3.att-akraino.org:10001/${CON_NAME}:latest"
