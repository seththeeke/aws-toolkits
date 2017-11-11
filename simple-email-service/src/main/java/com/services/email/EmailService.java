package com.services.email;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.services.AWSService;
import com.services.interfaces.IAWSEmailService;
import com.services.objects.Email;

public class EmailService extends AWSService implements IAWSEmailService {

	public EmailService(final Regions region, final String accessKey, final String secretKey) {
		super(region, accessKey, secretKey);
	}

	public EmailService(final Regions region) {
		super(region);
	}

	@Override
	public boolean sendEmail(Email email) {
		AmazonSimpleEmailService client = buildEmailClient();
		SendEmailRequest request = buildEmailRequest(email);
		client.sendEmail(request);
		return true;
	}

	/**
	 * Builds the email client for sending an email. If credentials are not
	 * provided to the service, this will return a client with no credentials
	 * 
	 * @return AmazonSimpleEmailService
	 */
	private AmazonSimpleEmailService buildEmailClient() {
		if (getAccessKey() != null && getSecretKey() != null) {
			return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(getRegion())
					.withCredentials(
							new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey())))
					.build();
		} else {
			return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(getRegion()).build();
		}
	}

	/**
	 * Builds an AWS SendEmailRequest with the configuration given by the email
	 * object
	 * 
	 * @param Email
	 *            email determine email parameter
	 * @return SendEmailRequest
	 */
	private SendEmailRequest buildEmailRequest(Email email) {
		return new SendEmailRequest().withDestination(new Destination().withToAddresses(email.getToAddresses()))
				.withMessage(new Message().withBody(new Body().withText(new Content().withData(email.getBody())))
						.withSubject(new Content().withData(email.getSubject())))
				.withSource(email.getFromAddress());
	}

}
