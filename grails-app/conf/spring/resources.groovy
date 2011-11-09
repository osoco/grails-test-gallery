import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

beans = {
    jmsConnectionFactory(org.apache.activemq.spring.ActiveMQConnectionFactory) {
        brokerURL = CH.config.activemq.brokerURL
        redeliveryPolicy = ref('jmsRedeliveryPolicy')
    }

    jmsRedeliveryPolicy(org.apache.activemq.RedeliveryPolicy) {
        maximumRedeliveries = 3
        initialRedeliveryDelay = 30000L
    }
}
