package com.services.ec2;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.aeonbits.owner.ConfigFactory;

import com.amazonaws.regions.Regions;
import com.services.config.AWSConfig;
import com.services.interfaces.IEC2Service;

@Path("/ec2-instances")
public class EC2RestService {

	public static void main(String[] args) {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		IEC2Service ec2Service = new EC2Service(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		ec2Service.createEC2Instance();
	}

	private IEC2Service ec2Service;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEC2Instance() {
		return Response.ok(getEC2Service().createEC2Instance()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response describeEC2Instances() {
		return Response.ok(getEC2Service().describeEC2Instances()).build();
	}

	private IEC2Service getEC2Service() {
		if (this.ec2Service == null) {
			AWSConfig config = ConfigFactory.create(AWSConfig.class);
			this.ec2Service = new EC2Service(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		}
		return this.ec2Service;
	}

}
