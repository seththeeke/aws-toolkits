package com.services.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "file:/etc/aws/config.properties" })
public interface AWSConfig extends Config {

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

	/**
	 * Returns an AWS KeyPair Name
	 * 
	 * return String
	 */
	@Key("aws.key.pair")
	@DefaultValue("MyKeyPair")
	public String getKeyPair();

	/**
	 * Returns an AWS Security Group Id
	 * 
	 * return String
	 */
	@Key("aws.security.group")
	@DefaultValue("sg-54e40a27")
	public String getSecurityGroupId();
}
