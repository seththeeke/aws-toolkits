package com.services.interfaces;

import com.amazonaws.services.sns.model.PublishResult;
import com.services.objects.TextMessage;

public interface ISMSService {

	/**
	 * Sends a text message given the parameters of the message object
	 * 
	 * @param message
	 * @return PublicResult
	 */
	public PublishResult sendTextMessage(TextMessage message);

}
