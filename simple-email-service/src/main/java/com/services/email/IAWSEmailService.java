package com.services.email;

import com.services.email.objects.Email;

/**
 * Sends an email using Amazon Simple Email Service
 *
 */
public interface IAWSEmailService {

	/**
	 * Send an email given the parameters set in the email object
	 * 
	 * @param email
	 * @return
	 */
	public boolean sendEmail(Email email);

}
