package jms

import grails.plugin.jms.JmsService
import grails.plugin.spock.IntegrationSpec
import java.util.concurrent.CountDownLatch
import static java.util.concurrent.TimeUnit.SECONDS

class QueueListenerServiceSpec extends IntegrationSpec {
    private static final QUEUE_RECEPTION_TIMEOUT_SEC = 5
    private static final QUEUE_POLL_TIMEOUT_MILLIS = 5000
    private static final QUEUE_POLL_INTERVAL_MILLIS = 500

    private static class Timeout
    {
        private endTime

        Timeout(duration)
        {
            endTime = System.currentTimeMillis() + duration
        }

        def hasTimedOut()
        {
            timeRemaining() <= 0
        }

        def  timeRemaining()
        {
            endTime - System.currentTimeMillis()
        }
    }

    QueueListenerService queueListenerService
    JmsService jmsService

    private messageReceived = new CountDownLatch(1)
    private messageProcessor = Mock(MessageProcessorService)

    def setup() {
        queueListenerService.messageProcessorService = messageProcessor
    }

    def 'receives a message and passes it to further processing'()
    {
        when:
        jmsService.send(QueueListenerService.QUEUE_NAME, message())
        messageReceived.await(QUEUE_RECEPTION_TIMEOUT_SEC, SECONDS)

        then:
        1 * messageProcessor.process({ Map msg ->
            messageReceived.countDown()
            msg == message()
        })

        assertQueue(empty())
    }

    def 'message is returned to the queue if an exception is thrown'()
    {
        given:
        messageProcessor.process(_) >> {
            messageReceived.countDown()
            throw new IllegalStateException('Processing error')
        }

        when:
        jmsService.send(QueueListenerService.QUEUE_NAME, message())
        messageReceived.await(QUEUE_RECEPTION_TIMEOUT_SEC, SECONDS)

        then:
        assertQueue(notEmpty())
    }

    private message()
    {
        [key: 'a value']
    }

    private void assertQueue(condition)
    {
        def timeout = new Timeout(QUEUE_POLL_TIMEOUT_MILLIS)
        while (!condition.fulfilled())
        {
            if (timeout.hasTimedOut())
            {
                throw new AssertionError(condition.describeFailure())
            }

            sleep(QUEUE_POLL_INTERVAL_MILLIS)
        }
    }

    private empty()
    {
        [
            fulfilled: { jmsService.browse(QueueListenerService.QUEUE_NAME) == []},
            describeFailure: 'Expected queue to be empty'
        ]
    }

    private notEmpty()
    {
        [
            fulfilled: { jmsService.browse(QueueListenerService.QUEUE_NAME) != []},
            describeFailure: 'Expected queue to be NOT empty'
        ]
    }
}
