package io.ossim.omar.scdf.notification.email

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
	public void emailSink(Object payload){

		if(logger.isDebugEnabled()){
			logger.info("Payload received: ${payload}")
		}

	}

	;

}