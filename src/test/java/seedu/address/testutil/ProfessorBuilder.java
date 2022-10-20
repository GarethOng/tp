package seedu.address.testutil;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.Professor;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Specialisation;

/**
 * A utility class to help with building Person objects.
 */
public class ProfessorBuilder extends PersonBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS1231S";
    public static final String DEFAULT_RATING = "5";
    public static final String DEFAULT_SPECIALISATION = "Computer Graphics";

    private ModuleCode moduleCode;
    private Rating rating;
    private Specialisation field;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public ProfessorBuilder() {
        super();
        this.moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        this.rating = new Rating(DEFAULT_RATING);
        this.field = new Specialisation(DEFAULT_SPECIALISATION, true);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public ProfessorBuilder(Professor personToCopy) {
        super(personToCopy);
        moduleCode = personToCopy.getModuleCode();
    }

    @Override
    public Professor build() {
        return new Professor(getName(), moduleCode, getPhone(), getEmail(), getGender(), getTags(), getLocation(),
                getGithubUsername(), rating, field, );
    }
    /**
     * Sets the {@code ModuleCode} of the {@code Professor} that we are building.
     */
    public PersonBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

}
