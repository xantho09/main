package seedu.address.model.loan;

/**
 * The LoanIdManager class keeps track of the running Loan ID and
 * provides the next available ID when a new Loan is created.
 */
public class LoanIdManager {

    private static final int INITIAL_LOAN_ID = -1;
    private static final int COUNTER_MAXIMUM = 1000000000;

    private int runningIdCounter;
    private boolean isMaximumReached;

    public LoanIdManager() {
        runningIdCounter = INITIAL_LOAN_ID;
        isMaximumReached = false;

        // Ensure that the first generated Loan ID (INITIAL_LOAN_ID + 1) is valid.
        assert LoanId.isValidLoanId(Integer.toString(INITIAL_LOAN_ID + 1));
    }

    public LoanIdManager(LoanId lastUsedLoanId) {
        if (lastUsedLoanId.isMaximumId()) {
            isMaximumReached = true;
            runningIdCounter = COUNTER_MAXIMUM;

            return;
        }

        isMaximumReached = false;
        runningIdCounter = lastUsedLoanId.value;
    }

    public boolean hasNextAvailableLoanId() {
        return !isMaximumReached;
    }

    public LoanId getNextAvailableLoanId() {
        if (isMaximumReached) {
            throw new IllegalStateException("No more available Loan IDs");
        }

        incrementIdCounter();
        LoanId output = LoanId.fromInt(runningIdCounter);

        isMaximumReached = output.isMaximumId();
        return output;
    }

    private void incrementIdCounter() {
        ++runningIdCounter;
    }
}
