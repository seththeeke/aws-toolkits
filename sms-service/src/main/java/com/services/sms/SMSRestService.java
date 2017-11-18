package com.services.sms;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.aeonbits.owner.ConfigFactory;

import com.amazonaws.regions.Regions;
import com.services.config.AWSConfig;
import com.services.interfaces.ISMSService;
import com.services.objects.TextMessage;

@Path("/text-messages")
public class SMSRestService {

	public static void main(String[] args) {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		SMSService smsService = new SMSService(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		TextMessage message = new TextMessage("+13043769298",
				"Hey, It's Seth, I can text you from a computer now completely anonymously... :)");
		smsService.sendTextMessage(message);
	}

	private ISMSService smsService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendTextMessage(TextMessage message) {
		return Response.ok(getSMSService().sendTextMessage(message)).build();
	}

	private ISMSService getSMSService() {
		if (this.smsService == null) {
			AWSConfig config = ConfigFactory.create(AWSConfig.class);
			this.smsService = new SMSService(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		}
		return this.smsService;
	}
}
