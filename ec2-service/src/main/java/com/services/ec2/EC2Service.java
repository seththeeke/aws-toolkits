package com.services.ec2;

import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.services.AWSService;

public class EC2Service extends AWSService {

	public EC2Service(final Regions region, final String accessKey, final String secretKey) {
		super(region, accessKey, secretKey);
	}

	public EC2Service(Regions region) {
		super(region);
	}

	public void createEC2Instance() {
		AmazonEC2 client = AmazonEC2ClientBuilder.standard().withRegion(getRegion())
				.withCredentials(
						new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey())))
				.build();
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		DescribeInstancesResult result = client.describeInstances(request);
		List<Reservation> results = result.getReservations();
	}
}
