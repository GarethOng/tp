package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Adds a Student to the address book.
 */
public class StudentCommand extends Command {
    public static final String COMMAND_WORD = "student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_MODULE_CODE + "MODULE_CODE]... "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_MODULE_CODE + "CS4226 "
            + PREFIX_MODULE_CODE + "CS5242 "
            + PREFIX_MODULE_CODE + "CS1101S  "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "JohnD@example.com "
            + PREFIX_GENDER + "M "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney"
            + PREFIX_LOCATION + "UTown Residences";

    public static final String MESSAGE_SUCCESS = "New Student added: %1$s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This Student already exists in the address book";

    private final Person toAdd;

    /**
     * Creates a ProfCommand to add the specified {@code Student}
     */
    public StudentCommand(Student toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentCommand // instanceof handles nulls
                && toAdd.equals(((StudentCommand) other).toAdd));
    }
}
