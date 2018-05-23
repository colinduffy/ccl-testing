package com.cerner.ftp.data.factory;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.cerner.ftp.data.FileRequest;

/**
 * Automated unit test for {@link FileRequestFactory}.
 *
 * @author Joshua Hyde
 *
 */

public class FileRequestFactoryTest {
    /**
     * Test that the factory fails when given a null source file.
     */
    @Test(expected = NullPointerException.class)
    public void testNullSourceFile() {
        FileRequestFactory.create(null, URI.create("http://www.google.com"));
    }

    /**
     * Test that the factory fails when given a null target file.
     */
    @Test(expected = NullPointerException.class)
    public void testNullTargetFile() {
        FileRequestFactory.create(URI.create("http://www.google.com"), null);
    }

    /**
     * Test the creation of a file request data object.
     */
    @Test
    public void testCreate() {
        final URI source = URI.create("file:/c:/temp/test.txt");
        final URI target = URI.create("/temp/target/target.txt");

        final FileRequest request = FileRequestFactory.create(source, target);

        assertEquals("Source URI does not match.", source.toString(), request.getSourceFile().toString());
        assertEquals("Target URI does not match.", target.toString(), request.getTargetFile().toString());
    }

    /**
     * Test that the {@link Object#equals(Object)} implementation of the returned {@link FileRequest} object has
     * properly overridden the method.
     */
    @Test
    public void testFileRequestEquals() {
        final URI source = URI.create("file:/c:/temp/test.txt");
        final URI target = URI.create("/temp/target/target.txt");

        final FileRequest request = FileRequestFactory.create(source, target);
        final FileRequest otherRequest = FileRequestFactory.create(source, target);

        // Verify that there's no caching issue
        assertThat(request).isNotSameAs(otherRequest);
        assertThat(request).isEqualTo(otherRequest);
    }

    /**
     * Verify that the overridden {@link Object#equals(Object)} method within the {@link FileRequest} implementation
     * returns {@code false} when compared to {@code null}.
     */
    @Test
    public void testFileRequestNotEqualNull() {
        final URI source = URI.create("file:/c:/temp/test.txt");
        final URI target = URI.create("/temp/target/target.txt");

        final FileRequest request = FileRequestFactory.create(source, target);
        assertThat(request).isNotEqualTo(null);
    }

    /**
     * Verify that the overriden {@link Object#equals(Object)} method within the {@link FileRequest} implementation
     * returns {@code false} when compared to a non-{@code FileRequest} object.
     */
    @Test
    public void testFileRequestNotEqualsObject() {
        final URI source = URI.create("file:/c:/temp/test.txt");
        final URI target = URI.create("/temp/target/target.txt");

        final FileRequest request = FileRequestFactory.create(source, target);
        assertThat(request).isNotEqualTo(new Object());
    }

    /**
     * Verify that the overridden {@link Object#equals(Object)} method within the {@link FileRequest} implementation
     * returns {@code true} when compared to itself.
     */
    @Test
    public void testFileRequestEqualsItself() {
        final URI source = URI.create("file:/c:/temp/test.txt");
        final URI target = URI.create("/temp/target/target.txt");

        final FileRequest request = FileRequestFactory.create(source, target);
        assertThat(request).isEqualTo(request);
    }

    /**
     * Verify that the hash code generated by the {@link FileRequest} implementation is reasonably unique.
     */
    @Test
    public void testFileRequestHashCode() {
        final URI sourcePrime = URI.create("http://www.google.com");
        final URI targetPrime = URI.create("http://www.cnn.com");
        final URI sourceSecondary = URI.create("http://www.slickdeals.net");

        final FileRequest first = FileRequestFactory.create(sourcePrime, targetPrime);
        final FileRequest second = FileRequestFactory.create(sourceSecondary, targetPrime);

        assertThat(first.hashCode()).isNotEqualTo(second.hashCode());

        // Verify that a FileRequest with the same values has a matching hash
        // code
        final FileRequest firstDup = FileRequestFactory.create(first.getSourceFile(), first.getTargetFile());
        assertThat(firstDup.hashCode()).isEqualTo(first.hashCode());
    }
}
