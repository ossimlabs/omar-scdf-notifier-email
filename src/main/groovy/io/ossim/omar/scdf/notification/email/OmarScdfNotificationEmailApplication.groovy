package io.ossim.omar.scdf.notification.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage

/**
 * Created by adrake on 6/09/2017
 */

@SpringBootApplication
@EnableBinding(Sink.class)
class OmarScdfNotificationEmailApplication {

	/**
	 * The application logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass())

	/**
	 * Adds the spring boot starter MailSender to simplify
	 * the mail creation process
	 */
	@Autowired
	private final MailSender mailSender

	/**
	 * Used the SimpleMailMessage to create a new email message template
	 */
	private SimpleMailMessage templateMessage

	/**
	 * Pulls the from email from the properties file for use in the mail message
	 */
	@Value('${send.from.email}')
	private final String fromEmail

	/**
	 * Set the fromEmail if we do not provide one from the
	 * properties file
	 */
	OmarScdfNotificationEmailApplication(){
		if(null == fromEmail || fromEmail.equals("")){
			fromEmail = "omar.dropbox.dg@gmail.com"
		}
	}


	/**
	 * The main entry point of the SCDF Notifier Email application.
	 * @param args
	 */
	static final void main(String[] args) {
		SpringApplication.run OmarScdfNotificationEmailApplication, args
	}

	/**
	 * TODO: Application description
	 *
	 * @param
	 * @return
	 */
	@ServiceActivator(inputChannel=Sink.INPUT)
	public void emailSink(final Object payload){

		if(logger.isDebugEnabled()){
			logger.info("Payload received: ${payload}")
		}

		String response = "OK"

		/**
		 *We need to parse the incoming payload
		 * and grab the name and email address
 		 */
		if(null != payload){

			if(logger.isDebugEnabled()){
				logger.info('Starting to send email...')
			}

			//String response = "OK"
			this.templateMessage = new SimpleMailMessage()

			// TODO: Add parsed filename here into the subject line
			this.templateMessage.setSubject("Image Available for download")
			//this.templateMessage.setFrom(fromEmail)
			this.templateMessage.setFrom("omarftw@gmail.com")
			this.templateMessage.setTo("melbsurfer@gmail.com")

			SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage)

			// TODO: add s3 link for the image to download
			msg.setText("A new image is available for you to download")

			try{
				this.mailSender.send(msg)
			}
			catch(MailException ex){
				//response = "NO_OK"
				//String mailError = ex.getMessage()
				String mailError = ex.printStackTrace()

				if(logger.isDebugEnabled()){
					logger.error("There was an error while sending the email: ${'mailError'}")
				}

			}
			if(logger.isDebugEnabled()){
				logger.info('Finished sending email...')
			}


		}

	}


}