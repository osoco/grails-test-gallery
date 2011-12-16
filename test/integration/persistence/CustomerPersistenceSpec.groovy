package persistence


class CustomerPersistenceSpec extends PersistenceSpec {
    def createPersistableDomainObject() {
        new Customer(address: 'Baker Street')
    }
}
