package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

public class LoanIdManagerTest {

    private static final int EXPECTED_INITIAL_ID_VALUE = 0;
    private static final int EXPECTED_MAXIMUM_ID_VALUE = 999999999;

    private static final LoanId EXPECTED_INITIAL_LOAN_ID = LoanId.fromInt(EXPECTED_INITIAL_ID_VALUE);
    private static final LoanId EXPECTED_MAXIMUM_LOAN_ID = LoanId.fromInt(EXPECTED_MAXIMUM_ID_VALUE);

    @Test
    public void defaultIdManagerConstructorTest() {
        // Create new Loan ID Manager starting from the initial ID value.
        LoanIdManager idManager = new LoanIdManager();
        assertTrue(idManager.hasNextAvailableLoanId()); // There should be a next Loan ID

        LoanId nextAvailableLoanId = idManager.getNextAvailableLoanId();
        assertEquals(EXPECTED_INITIAL_LOAN_ID, nextAvailableLoanId); // The first Loan ID created should be 0.
    }

    @Test
    public void runningIdFromDefaultConstructorTest() {
        LoanIdManager idManager = new LoanIdManager();

        // The IDs returned by the idManager should be {0, 1, 2, ... 9}
        for (int id = EXPECTED_INITIAL_ID_VALUE; id < 10; ++id) {
            LoanId expectedLoanId = LoanId.fromInt(id);
            LoanId actualLoanId = idManager.getNextAvailableLoanId();

            assertEquals(expectedLoanId, actualLoanId);
            assertEquals(id, (int) actualLoanId.value);
        }
    }

    @Test
    public void constructorWithSuppliedLastUsedLoanId() {
        LoanId lastUsedLoanId = LoanId.fromInt(500);

        LoanIdManager idManager = new LoanIdManager(lastUsedLoanId);
        assertTrue(idManager.hasNextAvailableLoanId()); // 500 is not the maximum; there should be a next Loan ID

        // The next ID should be 501.
        LoanId nextAvailableLoanId = idManager.getNextAvailableLoanId();
        LoanId expectedLoanId = LoanId.fromInt(501);

        assertEquals(expectedLoanId, nextAvailableLoanId);
        assertEquals(501, (int) nextAvailableLoanId.value);
    }

    @Test
    public void runningIdFromLastUsedLoanIdTest() {
        int lastUsedIdValue = 2100;
        LoanId lastUsedLoanId = LoanId.fromInt(lastUsedIdValue);

        LoanIdManager idManager = new LoanIdManager(lastUsedLoanId);

        // The IDs returned should be {2101, 2102, ... , 2109}
        for (int id = lastUsedIdValue + 1; id < 2110; ++id) {
            LoanId expectedLoanId = LoanId.fromInt(id);
            LoanId actualLoanId = idManager.getNextAvailableLoanId();

            assertEquals(expectedLoanId, actualLoanId);
            assertEquals(id, (int) actualLoanId.value);
        }
    }

    @Test
    public void maximumLoanIdTest() {
        // Create an ID manager where the last used ID is the maximum ID.
        LoanIdManager idManager = new LoanIdManager(EXPECTED_MAXIMUM_LOAN_ID);

        assertFalse(idManager.hasNextAvailableLoanId());
        assertThrows(IllegalStateException.class, idManager::getNextAvailableLoanId);
    }

    @Test
    public void incrementToMaximumTest() {
        int offset = 10;
        int initialValue = EXPECTED_MAXIMUM_ID_VALUE - offset;

        LoanIdManager idManager = new LoanIdManager(LoanId.fromInt(initialValue));

        // With an offset of 10, the ID manager should
        // reach the maximum ID after 10 calls
        for (int i = 1; i <= offset; ++i) {
            int expectedIdValue = initialValue + i;
            LoanId expectedLoanId = LoanId.fromInt(expectedIdValue);
            LoanId actualLoanId = idManager.getNextAvailableLoanId();

            // Value check
            assertEquals(expectedLoanId, actualLoanId);
            assertEquals(expectedIdValue, (int) actualLoanId.value);

            // Maximum check
            if (i < offset) {
                // Not yet the maximum. There should be more.
                assertTrue(idManager.hasNextAvailableLoanId());
                assertFalse(actualLoanId.isMaximumId());
            } else {
                // At the last iteration, the maximum loan ID should have been used.
                assertFalse(idManager.hasNextAvailableLoanId());
                assertTrue(actualLoanId.isMaximumId());
            }
        }
    }
}
