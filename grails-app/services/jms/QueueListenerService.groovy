package jms

import grails.plugin.jms.Queue

class QueueListenerService {
    static final QUEUE_NAME = 'aQueue'
    static exposes = ['jms']

    MessageProcessorService messageProcessorService

    @Queue(name = QueueListenerService.QUEUE_NAME)
    void onMessage(msg) {
        messageProcessorService.process(msg)
    }
}
