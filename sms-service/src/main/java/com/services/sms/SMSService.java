package com.services.sms;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.services.AWSService;
import com.services.interfaces.ISMSService;
import com.services.objects.TextMessage;

public class SMSService extends AWSService implements ISMSService {

	public SMSService(Regions region) {
		super(region);
	}

	public SMSService(final Regions region, final String accessKey, final String secretKey) {
		super(region, accessKey, secretKey);
	}

	@Override
	public PublishResult sendTextMessage(TextMessage message) {
		AmazonSNS snsClient = buildSMSClient();
		return sendMessage(snsClient, message);
	}

	/**
	 * Publishes a message given an sns client and message objects
	 * 
	 * @param snsClient
	 * @param message
	 * @return
	 */
	private PublishResult sendMessage(AmazonSNS snsClient, TextMessage message) {
		Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
		return snsClient.publish(new PublishRequest().withMessage(message.getMessage())
				.withPhoneNumber(message.getReceiverPhoneNumber()).withMessageAttributes(smsAttributes));
	}

	/**
	 * Builds the sms client for sending an notification. If credentials are not
	 * provided to the service, this will return a client with no credentials
	 * 
	 * @return AmazonSNS
	 */
	private AmazonSNS buildSMSClient() {
		if (getAccessKey() != null && getSecretKey() != null) {
			return AmazonSNSClientBuilder.standard().withRegion(getRegion())
					.withCredentials(
							new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey())))
					.build();
		} else {
			return AmazonSNSClientBuilder.standard().withRegion(getRegion()).build();
		}
	}

}
