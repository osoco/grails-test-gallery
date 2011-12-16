package es.osoco

class BlogEntrySpec extends ContentSpec {
    def createDomainObjectToCompare() {
        new BlogEntry(contentProperties << [url: new URL('http://google.com')])
    }

    @Override
    def modifiedPropertiesIncludedInEqualsAndHashCode() {
        super.modifiedPropertiesIncludedInEqualsAndHashCode() << [url: new URL('http://localhost')]
    }

    def 'equals relation must be symmetric with inheritance'()
    {
        given:
        def content = new Content()

        and:
        def blogEntry = new BlogEntry()

        expect:
        (content == blogEntry) == (blogEntry == content)
    }
}
