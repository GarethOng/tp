package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFESSOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Professor;
import seedu.address.model.person.Student;
import seedu.address.model.person.TeachingAssistant;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void executeEditStudent_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    //    @Test
    //    public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
    //        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
    //        PersonBuilder personInList;
    //
    //        if (lastPerson instanceof Student) {
    //            personInList = new StudentBuilder((Student) lastPerson);
    //        } else if (lastPerson instanceof Professor) {
    //            personInList = new ProfessorBuilder((Professor) lastPerson);
    //        } else {
    //            personInList =
    //                    new TeachingAssistantBuilder((TeachingAssistant) lastPerson);
    //        }
    //
    //        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //                .withTags(VALID_TAG_HUSBAND).build();
    //
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
    //        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setPerson(lastPerson, editedPerson);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }



    @Test //check
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //    @Test
    //    public void execute_filteredList_success() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        Person editedPerson;
    //
    //        if (personInFilteredList instanceof Student) {
    //            editedPerson = new StudentBuilder((Student) personInFilteredList)
    //                    .withName(VALID_NAME_BOB).build();
    //        } else if (personInFilteredList instanceof Professor) {
    //            editedPerson = new ProfessorBuilder((Professor) personInFilteredList)
    //                    .withName(VALID_NAME_BOB).build();
    //        } else {
    //            editedPerson = new TeachingAssistantBuilder((TeachingAssistant) personInFilteredList)
    //                    .withName(VALID_NAME_BOB).build();
    //        }
    //
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }


    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor;
        if (firstPerson instanceof Student) {
            descriptor = new EditPersonDescriptorBuilder((Student) firstPerson).build();
        } else if (firstPerson instanceof Professor) {
            descriptor = new EditPersonDescriptorBuilder((Professor) firstPerson).build();
        } else {
            descriptor = new EditPersonDescriptorBuilder((TeachingAssistant) firstPerson).build();
        }

        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand;
        if (personInList instanceof Student) {
            editCommand = new EditCommand(INDEX_FIRST_PERSON,
                    new EditPersonDescriptorBuilder((Student) personInList).build());
        } else if (personInList instanceof Professor) {
            editCommand = new EditCommand(INDEX_FIRST_PERSON,
                    new EditPersonDescriptorBuilder((Professor) personInList).build());
        } else {
            editCommand = new EditCommand(INDEX_FIRST_PERSON,
                    new EditPersonDescriptorBuilder((TeachingAssistant) personInList).build());
        }

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, STUDENT_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(STUDENT_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, STUDENT_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, PROFESSOR_BOB)));
    }
}
