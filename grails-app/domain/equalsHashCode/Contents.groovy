package equalsHashCode

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.commons.lang.builder.ToStringBuilder

class Content {
    String author

    boolean equals(o) {
        if (o instanceof Content) {
            Content that = (Content) o
            that.canEqual(this) && 
                new EqualsBuilder().append(author, that.author).isEquals()
        }
    }

    boolean canEqual(o) { o instanceof Content }

    int hashCode() {
        new HashCodeBuilder().append(author).toHashCode()
    }

    String toString() {
        new ToStringBuilder(this).append(author).toString()
    }
}

class BlogEntry extends Content {
    URL url

    boolean equals(o) {
        if (o instanceof BlogEntry) {
            BlogEntry that = (BlogEntry) o
            that.canEqual(this) && 
                new EqualsBuilder().appendSuper(super.equals(o)).append(url, that.url).isEquals()
        }
    }

    boolean canEqual(o) { o instanceof BlogEntry }

    int hashCode() {
        new HashCodeBuilder().appendSuper(super.hashCode()).append(url).toHashCode()
    }

    String toString() {
        new ToStringBuilder(this).appendSuper(super.toString()).append(url).toString()
    }
}

class Book extends Content {
    String ISBN

    boolean equals(o) {
        if (o instanceof Book) {
            Book that = (Book) o
            that.canEqual(this) &&
                new EqualsBuilder().appendSuper(super.equals(o)).append(ISBN, that.ISBN).isEquals()
        }
    }

    boolean canEqual(o) { o instanceof Book }

    int hashCode() {
        new HashCodeBuilder().appendSuper(super.hashCode()).append(ISBN).toHashCode()
    }

    String toString() {
        new ToStringBuilder(this).appendSuper(super.toString()).append(ISBN).toString()
    }
}
