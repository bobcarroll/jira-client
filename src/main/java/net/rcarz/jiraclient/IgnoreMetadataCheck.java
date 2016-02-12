package net.rcarz.jiraclient;

/**
 * This interface represents the capabilities that any payload for a custom field should possess and wherein
 * the schema validations associated with a custom field are to be skipped.
 *
 * A good example for this would be when attempting to set/edit a custom field whose type is a cascading select.
 *
 */
public interface IgnoreMetadataCheck {
    /**
     *
     * @return - <code>true</code> if meta data validation is to be skipped.
     */
    boolean skipMetadataValidation();
}
