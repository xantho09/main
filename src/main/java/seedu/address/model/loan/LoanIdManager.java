package seedu.address.model.loan;

/**
 * The LoanIdManager class keeps track of the running Loan ID and
 * provides the next available ID when a new Loan is created.
 */
public class LoanIdManager {

    /** Represents the lack of a last used ID value. */
    private static final int NO_LAST_USED_ID_VALUE = -1;
    /** The first Loan ID to create. */
    private static final int INITIAL_ID_VALUE = 0;
    /** Represents when an ID Manager has no next available Loan ID. */
    private static final int MAXIMUM_ID_VALUE_REACHED = 999999999;

    private int lastUsedIdValue;
    private boolean isMaximumReached;

    /**
     * Constructs a {@code LoanIdManager} that provides running Loan IDs starting from 0.
     */
    public LoanIdManager() {
        lastUsedIdValue = NO_LAST_USED_ID_VALUE;
        isMaximumReached = false;
    }

    /**
     * Constructs a {@code LoanIdManager} that provides running Loan IDs starting after
     * the specified last used Loan ID.
     *
     * @param lastUsedLoanId The last used Loan ID.
     */
    public LoanIdManager(LoanId lastUsedLoanId) {
        if (lastUsedLoanId.isMaximumId()) {
            isMaximumReached = true;
            lastUsedIdValue = MAXIMUM_ID_VALUE_REACHED;

            return;
        }

        isMaximumReached = false;
        lastUsedIdValue = lastUsedLoanId.value;
    }

    /**
     * Checks if this Loan ID Manager has a next available Loan ID.
     *
     * @return true if this Loan ID Manager has a next available Loan ID.
     */
    public boolean hasNextAvailableLoanId() {
        return !isMaximumReached;
    }

    /**
     * Returns the next available Loan ID.
     *
     * @return The next available Loan ID.
     */
    public LoanId getNextAvailableLoanId() {
        if (isMaximumReached) {
            throw new IllegalStateException("No more available Loan IDs");
        }

        incrementLastUsedIdValue();
        LoanId output = LoanId.fromInt(lastUsedIdValue);

        if (output.isMaximumId()) {
            isMaximumReached = true;
            lastUsedIdValue = MAXIMUM_ID_VALUE_REACHED;
        }

        return output;
    }

    /**
     * Increment the last used ID value.
     */
    private void incrementLastUsedIdValue() {
        switch (lastUsedIdValue) {
            case NO_LAST_USED_ID_VALUE:
                // Set the value to the first possible Loan ID value.
                lastUsedIdValue = INITIAL_ID_VALUE;
                return;
            case MAXIMUM_ID_VALUE_REACHED:
                // No further action to be taken.
                return;
            default:
                // Increment the integer value.
                ++lastUsedIdValue;
        }
    }
}