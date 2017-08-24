#  omar-scdf-notifier-e-mail
The File parser is a Spring Cloud Data Flow (SCDF) Processor.
This means it:
1. Receives a message on a Spring Cloud input stream using Kafka.
2. Performs an operation on the data.
3. Sends the result on a Spring Cloud output stream using Kafka to a listening SCDF Processor or SCDF Sink.

## Purpose
The omar-scdf-notifier-e-mail receives a JSON message from the file parser containing an e-mail address and the zip file URL 
The omar-scdf-notifier-e-mail sends an e-mail from omar.dropbox.dg@gmail.com to the recipient that the image is ready at the URL given


## JSON input Example (from the omar-scdf-file-parser)
```json
{
    "to": "asdf@asdf.com",
    "zipFileUrl": "https://s3.amazonaws.com/o2-test-data/examplezip.zip"
}
```
