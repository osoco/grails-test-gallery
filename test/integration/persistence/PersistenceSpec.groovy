package persistence

import grails.plugin.spock.IntegrationSpec
import org.hibernate.SessionFactory

abstract class PersistenceSpec extends IntegrationSpec {
    SessionFactory sessionFactory

    protected abstract createPersistableDomainObject()

    def 'persistable domain object should be able to be saved and retrieved'() {
        given:
        def persistableDomainObject = createPersistableDomainObject()

        when:
        def savedDomainObject = persistableDomainObject.save()

        then:
        savedDomainObject.id != null

        when:
        clearFirstLevelCache()

        def retrievedDomainObject = persistableDomainObject.class.get(savedDomainObject.id)

        then:
        savedDomainObject == retrievedDomainObject
        !savedDomainObject.is(retrievedDomainObject)
    }

    private clearFirstLevelCache() {
        sessionFactory.currentSession.flush()
        sessionFactory.currentSession.clear()
    }
}
