package net.rcarz.jiraclient;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldTest {
    @Test
    public void testToJson() throws JiraException {
        String input = "Dummy Payload";
        IgnoreMetadataCheck payload = new FakePayload(input);
        String output = (String) Field.toJson("test", payload, Utils.getTestIssue());
        assertEquals("should return input payload without any transformation", input, output);
    }

    public static class FakePayload implements IgnoreMetadataCheck {
        private String payload;

        public FakePayload(String payload) {
            this.payload = payload;
        }

        public boolean skipMetadataValidation() {
            return true;
        }

        @Override
        public String toString() {
            return payload;
        }
    }
}
