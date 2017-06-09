package io.ossim.omar.scdf.notification.email

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by adrake on 5/31/2017
 */

@SpringBootApplication
@EnableBinding(Processor.class)
class OmarScdfNotificationEmailApplication {

	/**
	 * The application logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass())

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
	@StreamListener(Processor.INPUT) @SendTo(Processor.OUTPUT)
	final String sendEmail(final Message<?> message){

		if(logger.isDebugEnabled()){
			logger.debug("Message received: ${message}")
		}

		println "Inside the sendEmail"
		return "sending email..."

	}

}