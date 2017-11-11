package com.services.email;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "file:/etc/aws/config.properties" })
public interface AWSAccessConfig extends Config {

	/**
	 * Returns an AWS users access key
	 * 
	 * @return String
	 */
	@Key("aws.access.key")
	public String getAccessKey();

	/**
	 * Returns an AWS users access key
	 * 
	 * @return String
	 */
	@Key("aws.secret.key")
	public String getSecretKey();
}
