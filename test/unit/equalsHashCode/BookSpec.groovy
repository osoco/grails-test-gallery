package equalsHashCode

class BookSpec extends ContentSpec {
    def createDomainObjectToCompare() {
        new Book(contentProperties << [ISBN: '123-456'])
    }

    @Override
    def modifiedPropertiesIncludedInEqualsAndHashCode() {
        super.modifiedPropertiesIncludedInEqualsAndHashCode() << [ISBN: '456-789']
    }

    def 'equals relation must be symmetric with inheritance'()
    {
        given:
        def content = new Content()

        and:
        def book = new Book()

        expect:
        (content == book) == (book == content)
    }
}