package com.services.ec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aeonbits.owner.ConfigFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.ResourceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TagSpecification;
import com.services.AWSService;
import com.services.config.AWSConfig;
import com.services.interfaces.IEC2Service;
import com.services.objects.ImageId;

public class EC2Service extends AWSService implements IEC2Service {

	public EC2Service(final Regions region, final String accessKey, final String secretKey) {
		super(region, accessKey, secretKey);
	}

	public EC2Service(Regions region) {
		super(region);
	}

	@Override
	public RunInstancesResult createEC2Instance() {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);

		AmazonEC2 client = buildEC2Client();
		Tag nameTag = new Tag("Name", "TestInstance" + String.valueOf((Math.random() * 100)));
		TagSpecification tagSpecification = new TagSpecification().withResourceType(ResourceType.Instance)
				.withTags(Arrays.asList(nameTag));
		RunInstancesRequest request = new RunInstancesRequest().withImageId(ImageId.AMAZON_LINUX.getValue())
				.withInstanceType(InstanceType.T2Micro).withMaxCount(1).withMinCount(1)
				.withTagSpecifications(tagSpecification).withKeyName(config.getKeyPair())
				.withSecurityGroupIds(config.getSecurityGroupId());

		RunInstancesResult response = client.runInstances(request);
		return response;
	}

	@Override
	public RunInstancesResult createEC2Instance(final ImageId imageId, final InstanceType instanceType,
			final String keyName, final String instanceName) {
		AmazonEC2 client = buildEC2Client();
		Tag nameTag = new Tag("Name", instanceName);

		TagSpecification tagSpecification = new TagSpecification().withResourceType(ResourceType.Instance)
				.withTags(Arrays.asList(nameTag));
		RunInstancesRequest request = new RunInstancesRequest().withImageId(imageId.getValue())
				.withInstanceType(instanceType).withMaxCount(1).withMinCount(1).withKeyName(keyName)
				.withTagSpecifications(tagSpecification);

		RunInstancesResult response = client.runInstances(request);
		return response;
	}

	@Override
	public RunInstancesResult createEC2Instance(final ImageId imageId, final InstanceType instanceType,
			final String keyName, final List<String> securityGroupIds, final List<Tag> instanceTags) {
		AmazonEC2 client = buildEC2Client();
		Optional<Tag> nameTag = instanceTags.stream()
				.filter(instanceTag -> instanceTag.getKey().equalsIgnoreCase("Name")).findFirst();
		if (nameTag.isPresent() == false) {
			// Assigning a name tag if one is not already present
			Tag defaultNameTag = new Tag("Name", "EC2Instance" + String.valueOf((Math.random() * 100)));
			instanceTags.add(defaultNameTag);
		}

		TagSpecification tagSpecification = new TagSpecification().withResourceType(ResourceType.Instance)
				.withTags(instanceTags);
		RunInstancesRequest request = new RunInstancesRequest().withImageId(imageId.getValue())
				.withInstanceType(instanceType).withMaxCount(1).withMinCount(1).withTagSpecifications(tagSpecification)
				.withKeyName(keyName).withSecurityGroupIds(securityGroupIds);

		RunInstancesResult response = client.runInstances(request);
		return response;
	}

	@Override
	public RunInstancesResult createEC2Instance(final ImageId imageId, final InstanceType instanceType,
			final int maxCount, final int minCount, final String keyName, final List<String> securityGroupIds,
			final List<Tag> instanceTags) {
		AmazonEC2 client = buildEC2Client();
		Optional<Tag> nameTag = instanceTags.stream()
				.filter(instanceTag -> instanceTag.getKey().equalsIgnoreCase("Name")).findFirst();
		if (nameTag.isPresent() == false) {
			// Assigning a name tag if one is not already present
			Tag defaultNameTag = new Tag("Name", "EC2Instance" + String.valueOf((Math.random() * 100)));
			instanceTags.add(defaultNameTag);
		}

		TagSpecification tagSpecification = new TagSpecification().withResourceType(ResourceType.Instance)
				.withTags(instanceTags);
		RunInstancesRequest request = new RunInstancesRequest().withImageId(imageId.getValue())
				.withInstanceType(instanceType).withMaxCount(maxCount).withMinCount(minCount)
				.withTagSpecifications(tagSpecification).withKeyName(keyName).withSecurityGroupIds(securityGroupIds);

		RunInstancesResult response = client.runInstances(request);
		return response;
	}

	@Override
	public List<Instance> describeEC2Instances() {
		AmazonEC2 client = buildEC2Client();
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		DescribeInstancesResult describeResult = client.describeInstances(request);
		List<Reservation> results = describeResult.getReservations();
		List<Instance> instances = new ArrayList<>();
		results.stream().map(result -> instances.addAll(result.getInstances())).collect(Collectors.toList());
		return instances;
	}

	/**
	 * Builds the ec2 client for interacting with ec2. If credentials are not
	 * provided to the service, this will return a client with no credentials
	 * 
	 * @return AmazonEC2
	 */
	private AmazonEC2 buildEC2Client() {
		if (getAccessKey() != null && getSecretKey() != null) {
			return AmazonEC2ClientBuilder.standard().withRegion(getRegion())
					.withCredentials(
							new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey())))
					.build();
		} else {
			return AmazonEC2ClientBuilder.standard().withRegion(getRegion()).build();
		}
	}
}
