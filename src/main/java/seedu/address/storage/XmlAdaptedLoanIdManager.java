package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.loan.LoanIdManager;

public class XmlAdaptedLoanIdManager {

    @XmlElement
    private XmlAdaptedLoanId lastUsedLoanId;

    /**
     * Constructs an XmlAdaptedLoanIdManager.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLoanIdManager() {}

    /**
     * Constructs an {@code XmlAdaptedLoanIdManager} with the given last used Loan ID.
     */
    public XmlAdaptedLoanIdManager(XmlAdaptedLoanId lastUsedLoanId) {
        this.lastUsedLoanId = lastUsedLoanId;
    }

    /**
     * Constructs an {@code XmlAdaptedLoanIdManager} from the given Loan ID Manager for JAXB use.
     * Future changes to the specified LoanIdManager will not affect this instance.
     */
    public XmlAdaptedLoanIdManager(LoanIdManager source) {
        this.lastUsedLoanId = new XmlAdaptedLoanId(source.getLastUsedLoanId());
    }

    /**
     * Converts this JAXB-friendly adapted Loan ID Manager object into the model's LoanIdManager object.
     *
     * @throws IllegalValueException if there were any data constraints violated during the conversion
     */
    public LoanIdManager toModelType() throws IllegalValueException {
        return new LoanIdManager(lastUsedLoanId.toModelType());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLoanIdManager)) {
            return false;
        }

        return lastUsedLoanId.equals(((XmlAdaptedLoanIdManager) other).lastUsedLoanId);
    }
}
