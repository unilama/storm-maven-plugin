package org.webik.mojo.storm;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.utils.Utils;

/**
 * Goal which touches a timestamp file.
 *
 * @goal jar
 * 
 * @phase deploy
 * @requiresDependencyResolution test
 */
public class JarDeployMojo
    extends AbstractMojo
{
    
    /**
     * Topology jar
     * 
     * @parameter expression="${storm.localJar}"
     */
	private String localJar;
	private String stormConfFile = null;

    public void execute() throws MojoExecutionException
    {
    	System.setProperty( "storm.conf.file", stormConfFile  );
    	System.setProperty( "storm.options", getStormOptions()  );
        
    	@SuppressWarnings("rawtypes")
		Map conf = Utils.readStormConfig();
        
        Set<Object> args = new HashSet<Object>();
        args.add( "java" );
        args.add( "-client" );
        args.add( conf.get( Config.NIMBUS_HOST ) );
        args.add( conf.get( Config.NIMBUS_THRIFT_PORT ) );
		args.add(  StormSubmitter.submitJar( conf , localJar ) );
		
		try {
			Runtime.getRuntime().exec( (String[]) args.toArray() );
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private String getStormOptions() {
		// TODO Auto-generated method stub
		return null;
	}
}
