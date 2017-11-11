package com.services.email;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.aeonbits.owner.ConfigFactory;

import com.amazonaws.regions.Regions;
import com.services.email.objects.Email;

@Path("/emails")
public class EmailRestService {

	public static void main(String[] args) {
		AWSAccessConfig config = ConfigFactory.create(AWSAccessConfig.class);
		new EmailService(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey()).sendEmail(
				new Email("seththeeke@gmail.com", Arrays.asList("seththeeke@gmail.com"), "testSubject", "testBody"));
	}

	private IAWSEmailService emailService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendEmail(Email email) {
		return Response.ok(getEmailService().sendEmail(email)).build();
	}

	private IAWSEmailService getEmailService() {
		if (this.emailService == null) {
			AWSAccessConfig config = ConfigFactory.create(AWSAccessConfig.class);
			this.emailService = new EmailService(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		}
		return this.emailService;
	}

}
