package io.ossim.omar.scdf.notification.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.messaging.Message

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
     * Uses the SimpleMailMessage to create a new email message template
     */
    private SimpleMailMessage templateMessage

    /**
     * Pulls the from email from the properties file for use in the mail message
     */
    @Value('${send.from.email}')
    private final String fromEmail

    @Value('${mail.custom.signature}')
    private final String customSignature

    /**
     * Set the fromEmail and customSignature if we do not provide one from the
     * properties file
     *
     */
    OmarScdfNotificationEmailApplication() {
        if (null == fromEmail || fromEmail.equals("")) {
            fromEmail = "omar.dropbox.dg@gmail.com"
        }

        if (null == customSignature || customSignature.equals("")) {
            customSignature = "The OMAR Dropbox Team TEST"
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
     * Description: Uses Spring Boot's SimpleMailMessage to send an email
     *              to a user with a link to an image they have requested
     *              in and s3 bucket.
     *
     * @param
     * @return
     */
    @StreamListener(Sink.INPUT)
    @SuppressWarnings("unused")
    void emailSink(final Message<?> message) {

        if (logger.isDebugEnabled()) {
            logger.debug("Message received: ${message}")
        }

        /**
         * We need to parse the incoming payload
         * and grab the file location and email address
         */
        if (null != message) {

            /**
             * Uses the Groovy JsonSlurper to parse the incoming
             * payload message
             */
            def payloadJson = new JsonSlurper().parseText(message.payload)

            String zipFileUrl = payloadJson.zipFileUrl
            String toEmail = payloadJson.to

            logger.info('Starting to send email...')

            if (logger.isDebugEnabled()) {
                logger.debug("Zip File: " + zipFileUrl)
                logger.debug("toEmail: " + toEmail)
                logger.debug('fromEmail: ' + fromEmail)
            }

            this.templateMessage = new SimpleMailMessage()
            this.templateMessage.setSubject("Your image is now available for download")
            this.templateMessage.setFrom(fromEmail)
            this.templateMessage.setTo(toEmail)

            SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage)

            /**
             * Inserts the zip file url into the email message text/body
             */
            msg.setText("Hello,\n\n" +
                    "The following image is available for you to download:\n\n" +
                    "${zipFileUrl}\n\n" +
                    "Thank you,\n" +
                    "${customSignature}\n\n" +
                    "-------------------------------------------------------------------------------------------\n" +
                    "Note: This e-mail was auto-generated. Please do not reply.\n" +
                    "-------------------------------------------------------------------------------------------")

            try {
                this.mailSender.send(msg)
            }
            catch (MailException ex) {

                String mailError = ex.printStackTrace()

                logger.error("There was an error while sending the email: ${mailError}")
            }

            logger.info('Finished sending email...')
        }
    }
}
