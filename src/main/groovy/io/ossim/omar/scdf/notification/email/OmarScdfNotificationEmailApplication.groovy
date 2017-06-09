package io.ossim.omar.scdf.notification.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.beans.factory.annotation.Autowired
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
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

        JsonBuilder filesToDownload

        if(null != message.payload) {

            // Parse the message
            final def parsedJson = new JsonSlurper().parseText(message.payload)
            final String emailAddress = parsedJson.file.bucket

            if (logger.isDebugEnabled()) {
                //logger.debug("\n-- Parsed Message --\nfileName: ${fileNameFromMessage} \nfileExtension: ${fileExtensionFromMessage}\nbucketName: ${bucketName}\n")
            }

            // TODO:
            // This assumes we will always be looking for two files with the aggregator.  Should
            // we make it so that we can also look for one, or maybe three???
            if (fileExtension1 == fileExtensionFromMessage) {

                if (logger.isDebugEnabled()) {
                    //logger.debug("fileExtension1 matches file extension from message")
                }

                if (s3FileResource.exists()) {

                    // The other file exists! Put both files in a JSON array to send to next processor


                } else {
//                    logger.warn("""
//					Received notification for file that does not exist:
//					${s3FileResource.filename}
//					""")
                }
            }

            if (logger.isDebugEnabled()) {
                //logger.debug("filesToDownload: ${filesToDownload}")
            }
        }
		//return filesToDownload.toString()
	}


}