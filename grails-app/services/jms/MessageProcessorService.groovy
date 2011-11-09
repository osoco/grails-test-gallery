package jms

class MessageProcessorService {
    void process(msg) {
        log.info("Processing message ${msg}")
    }
}
