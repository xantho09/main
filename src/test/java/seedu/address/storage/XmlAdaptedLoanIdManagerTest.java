package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanIdManager;

public class XmlAdaptedLoanIdManagerTest {

    @Test
    public void defaultConstructorTest() {
        new XmlAdaptedLoanIdManager(); // No exception should be thrown.
    }

    @Test
    public void toModelTypeValidTest() throws IllegalValueException {
        // Construct from model's LoanIdManager.
        LoanIdManager loanIdManager1 = new LoanIdManager(LoanId.fromInt(500));
        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager1 = new XmlAdaptedLoanIdManager(loanIdManager1);

        // Construct from XmlAdaptedLoanId, exactly the same as above.
        XmlAdaptedLoanId xmlAdaptedLoanId = new XmlAdaptedLoanId(500);
        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager2 = new XmlAdaptedLoanIdManager(xmlAdaptedLoanId);

        assertEquals(loanIdManager1, xmlAdaptedLoanIdManager1.toModelType()); // Converting back into the original
        assertEquals(loanIdManager1, xmlAdaptedLoanIdManager2.toModelType()); // Converting from XML object to model

        // Construct a model LoanIdManager with no last used Loan ID (i.e. it starts from the initial Loan ID)
        LoanIdManager loanIdManager2 = new LoanIdManager();
        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager3 = new XmlAdaptedLoanIdManager(loanIdManager2);

        // Construct with null as the lastUsedLoanId. When converted to the model type, the output should be a
        // LoanIdManager that starts from the initial Loan ID.
        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager4 = new XmlAdaptedLoanIdManager((XmlAdaptedLoanId) null);

        assertEquals(loanIdManager2, xmlAdaptedLoanIdManager3.toModelType()); // Converting back into the original
        assertEquals(loanIdManager2, xmlAdaptedLoanIdManager4.toModelType()); // Converting from XML object to model

    }

    @Test
    public void toModelTypeInvalidTest() {
        XmlAdaptedLoanId invalidLoanId = new XmlAdaptedLoanId(-1); // Negative value
        XmlAdaptedLoanIdManager invalidLoanIdManager = new XmlAdaptedLoanIdManager(invalidLoanId);

        assertThrows(IllegalValueException.class, invalidLoanIdManager::toModelType);
    }

    @Test
    public void equalityTest() {
        LoanIdManager loanIdManager1 = new LoanIdManager(LoanId.fromInt(2103));
        XmlAdaptedLoanId xmlAdaptedLoanId1 = new XmlAdaptedLoanId(2103);
        XmlAdaptedLoanId xmlAdaptedLoanId2 = new XmlAdaptedLoanId(9001);

        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager1 = new XmlAdaptedLoanIdManager(loanIdManager1);
        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager2 = new XmlAdaptedLoanIdManager(xmlAdaptedLoanId1);
        XmlAdaptedLoanIdManager xmlAdaptedLoanIdManager3 = new XmlAdaptedLoanIdManager(xmlAdaptedLoanId2);

        assertEquals(xmlAdaptedLoanIdManager1, xmlAdaptedLoanIdManager1); // Same instance
        assertEquals(xmlAdaptedLoanIdManager1, xmlAdaptedLoanIdManager2); // Same value
        assertNotEquals(xmlAdaptedLoanIdManager2, xmlAdaptedLoanIdManager3); // Different last used Loan ID
        assertNotEquals(xmlAdaptedLoanIdManager2, "Different type");
    }

}
