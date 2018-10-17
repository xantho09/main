package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBikes.BIKE1;
import static seedu.address.testutil.TypicalLoans.ALICE;
import static seedu.address.testutil.TypicalLoans.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.exceptions.DuplicateLoanException;
import seedu.address.testutil.LoanBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getLoanList());
        assertEquals(Collections.emptyList(), addressBook.getBikeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateLoans_throwsDuplicateLoanException() {
        // Two loans with the same identity fields
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bike> newBikes = Arrays.asList();
        List<Loan> newLoans = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newBikes, newLoans);

        thrown.expect(DuplicateLoanException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasBike_nullBike_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasBike(null);
    }

    @Test
    public void hasLoan_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasLoan(null);
    }

    @Test
    public void hasBike_bikeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasLoan(ALICE));
    }

    @Test
    public void hasBike_bikeInAddressBook_returnsTrue() {
        addressBook.addBike(BIKE1);
        assertTrue(addressBook.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanInAddressBook_returnsTrue() {
        addressBook.addLoan(ALICE);
        assertTrue(addressBook.hasLoan(ALICE));
    }

    @Test
    public void hasLoan_loanWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addLoan(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasLoan(editedAlice));
    }

    @Test
    public void getBikeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getBikeList().remove(0);
    }

    @Test
    public void getLoanList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getLoanList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose loans list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Bike> bikes = FXCollections.observableArrayList();
        private final ObservableList<Loan> loans = FXCollections.observableArrayList();

        AddressBookStub(Collection<Bike> bikes, Collection<Loan> loans) {
            this.bikes.setAll(bikes);
            this.loans.setAll(loans);
        }

        @Override
        public ObservableList<Bike> getBikeList() {
            return bikes;
        }

        @Override
        public ObservableList<Loan> getLoanList() {
            return loans;
        }
    }

}
