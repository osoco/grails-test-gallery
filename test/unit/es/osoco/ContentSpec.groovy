package es.osoco

import es.osoco.grails.plugins.EqualsHashCodeSpec

abstract class ContentSpec extends EqualsHashCodeSpec {

    protected getContentProperties() {
        [author:  'an author']
    }
    
    def modifiedPropertiesIncludedInEqualsAndHashCode() {
        [author: 'modified author']
    }

    def modifiedPropertiesIgnoredInEqualsAndHashCode() {
        [id: 1L]
    }
}
