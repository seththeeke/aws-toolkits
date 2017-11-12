package com.services.interfaces;

import java.util.List;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.services.objects.ImageId;

public interface IEC2Service {

	/**
	 * Create an EC2 instance of type T2 micro with Amazon Linux, a default
	 * instance name, and the key pair and security group located in AWS Config
	 * file
	 * 
	 * @return
	 */
	RunInstancesResult createEC2Instance();

	/**
	 * Create an EC2 instance of a particular imageId, instanceType, key name,
	 * and instance name
	 * 
	 * @param imageId
	 * @param instanceType
	 * @param keyName
	 * @param instanceName
	 * @return
	 */
	RunInstancesResult createEC2Instance(ImageId imageId, InstanceType instanceType, String keyName,
			String instanceName);

	/**
	 * Create an EC2 instance of a particular imageId, instanceType, key name,
	 * tags, and with a security group ids. Will generate a default name is not
	 * given in the tags
	 * 
	 * @param imageId
	 * @param instanceType
	 * @param keyName
	 * @param securityGroupIds
	 * @param instanceTags
	 * @return
	 */
	RunInstancesResult createEC2Instance(ImageId imageId, InstanceType instanceType, String keyName,
			List<String> securityGroupIds, List<Tag> instanceTags);

	/**
	 * Create an EC2 instance of a particular imageId, instanceType, key name,
	 * max instance count, min instance count, tags, and with a security group
	 * ids. Will generate a default name is not given in the tags.
	 * 
	 * @param imageId
	 * @param instanceType
	 * @param maxCount
	 * @param minCount
	 * @param keyName
	 * @param securityGroupIds
	 * @param instanceTags
	 * @return
	 */
	RunInstancesResult createEC2Instance(ImageId imageId, InstanceType instanceType, int maxCount, int minCount,
			String keyName, List<String> securityGroupIds, List<Tag> instanceTags);

	/**
	 * Describes all EC2 instances
	 * 
	 * @return
	 */
	List<Instance> describeEC2Instances();

}