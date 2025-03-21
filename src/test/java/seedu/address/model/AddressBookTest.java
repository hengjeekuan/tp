package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.person.ExternalParty;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ExternalPartyBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StaffBuilder;
import seedu.address.testutil.StudentBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasStaff_staffInAddressBook_returnsTrue() {
        Staff staff = new StaffBuilder().build();
        addressBook.addStaff(staff);
        assertTrue(addressBook.hasStaff(staff));
    }

    @Test
    public void hasExternalParty_externalPartyInAddressBook_returnsTrue() {
        ExternalParty externalParty = new ExternalPartyBuilder().build();
        addressBook.addExternalParty(externalParty);
        assertTrue(addressBook.hasExternalParty(externalParty));
    }

    @Test
    public void hasStudent_staffInAddressBook_returnsTrue() {
        Student student = new StudentBuilder().build();
        addressBook.addStudent(student);
        assertTrue(addressBook.hasStudent(student));
    }

    @Test
    public void setStaff_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setStaff(null, new StaffBuilder().build()));
    }

    @Test
    public void setStaff_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setStaff(new StaffBuilder().build(), null));
    }

    @Test
    public void setStaff_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.setStaff(
                new StaffBuilder().build(), new StaffBuilder().build()));
    }

    @Test
    public void setStaff_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        Staff staff = new StaffBuilder().build();
        Staff staff1 = new StaffBuilder().withName("Haikel").build();
        addressBook.addStaff(staff);
        addressBook.addStaff(staff1);
        assertThrows(DuplicatePersonException.class, () -> addressBook.setStaff(staff, staff1));
    }

    @Test
    public void setStudent_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setStudent(null,
                new StudentBuilder().build()));
    }

    @Test
    public void setStudent_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setStudent(new StudentBuilder().build(),
                null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.setStudent(
                new StudentBuilder().build(), new StudentBuilder().build()));
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicatePersonException() {
        Student student = new StudentBuilder().build();
        Student student1 = new StudentBuilder().withName("Haikel").build();
        addressBook.addStudent(student);
        addressBook.addStudent(student1);
        assertThrows(DuplicatePersonException.class, () -> addressBook.setStudent(student, student1));
    }

    @Test
    public void remove_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeStaff(null));
    }

    @Test
    public void remove_staffDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removeStaff(new StaffBuilder().build()));
    }

    @Test
    public void remove_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeStudent(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> addressBook.removeStudent(new StudentBuilder().build()));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();
        private final ObservableList<ExternalParty> externalParties = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Staff> getStaffList() {
            return staffs;
        }

        @Override
        public ObservableList<ExternalParty> getExternalPartyList() {
            return externalParties;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
