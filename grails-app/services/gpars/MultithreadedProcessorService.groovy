package gpars

import groovyx.gpars.GParsPool
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class MultithreadedProcessorService {
    ProcessorService processorService
    def threadPoolSize = ConfigurationHolder.config.threadPool.size

    void processMultithreaded(batches) {
        GParsPool.withPool(threadPoolSize) {
            batches.eachParallel { batch ->
                processorService.process(batch)
            }
        }
    }
}
