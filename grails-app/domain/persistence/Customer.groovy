package persistence

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.commons.lang.builder.ToStringBuilder


class Customer {
    String address

    boolean equals(o) {
        if (o instanceof Customer) {
            Customer that = (Customer) o
            that.canEqual(this) &&
                new EqualsBuilder().append(address, that.address).isEquals()
        }
    }

    boolean canEqual(o) { o instanceof Customer }

    int hashCode() {
        new HashCodeBuilder().append(address).toHashCode()
    }

    String toString() {
        new ToStringBuilder(this).append(address).toString()
    }

}
