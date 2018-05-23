package com.cerner.ccl.j4ccl.record.factory;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.cerner.ccl.j4ccl.record.DataType;
import com.cerner.ccl.j4ccl.record.Field;
import com.cerner.ccl.j4ccl.record.Record;
import com.cerner.ccl.j4ccl.record.Structure;

/**
 * Unit tests for {@link RecordFactory}
 *
 * @author Joshua Hyde
 *
 */

public class RecordFactoryTest {
    /**
     * A {@link Rule} used to test for thrown exceptions.
     */
    @Rule
    public ExpectedException expected = ExpectedException.none();

    /**
     * Test the creation of a record with a null name. It should fail.
     */
    @Test
    public void testCreateNullName() {
        expected.expect(NullPointerException.class);
        expected.expectMessage("Name must not be null");
        RecordFactory.create(null, mock(Structure.class));
    }

    /**
     * Verify that creating a record structure with a null structure fails.
     */
    @Test
    public void testCreateNullStructure() {
        expected.expect(NullPointerException.class);
        expected.expectMessage("Structure must not be null");
        RecordFactory.create("name", null);
    }

    /**
     * Verify the happy path.
     */
    @Test
    public void testHappyPath() {
        final Structure mockStructure = mock(Structure.class);
        final Field mockField1 = mock(Field.class);
        final Field mockField2 = mock(Field.class);
        when(mockField1.getName()).thenReturn("name");
        when(mockField1.getType()).thenReturn(DataType.VC);
        when(mockField2.getName()).thenReturn("id");
        when(mockField2.getType()).thenReturn(DataType.F8);
        when(mockStructure.getFields()).thenReturn(Arrays.asList(mockField1, mockField2));
        final Record record = RecordFactory.create("name", mockStructure);
        assertThat(record.getName()).isEqualTo("name");
        final Structure structure = record.getStructure();
        final List<Field> fields = structure.getFields();
        assertThat(fields.size()).isEqualTo(2);
        assertThat(fields.contains(mockField1));
        assertThat(fields.contains(mockField2));
    }
}
