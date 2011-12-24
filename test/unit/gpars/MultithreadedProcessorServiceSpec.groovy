package gpars

import grails.plugin.spock.UnitSpec

class MultithreadedProcessorServiceSpec extends UnitSpec {
    private MultithreadedProcessorService multithreadedProcessorService
    // Using Spock mocks because they are thread-safe
    private ProcessorService processorService = Mock(ProcessorService)

    def setup() {
        mockConfig('threadPool.size=2')
        multithreadedProcessorService = new MultithreadedProcessorService(
            processorService: processorService
        )
    }

    def 'processes batches using multiple threads'() {
        given:
        def batches = [[0, 1], [2, 3, 4], [5]]
        def processedBatches = markedAsNotProcessed(batches)

        when:
        multithreadedProcessorService.processMultithreaded(batches)

        then:
        batches.size() * processorService.process({ batch ->
            processedBatches[batch] = true
            batch in batches
        })
        assertAllProcessed(processedBatches)

    }

    private markedAsNotProcessed(batches) {
        batches.inject([:]) { processed, batch ->
            processed[batch] = false
            processed
        }
    }

    private void assertAllProcessed(batches) {
        assert batches*.value == [true] * batches.size()
    }
}
