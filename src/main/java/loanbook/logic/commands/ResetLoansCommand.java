package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;

/**
 * Clears the loans and resets the loan ID in the loan book.
 */
public class ResetLoansCommand extends Command {

    public static final String COMMAND_WORD = "resetloans";
    public static final String MESSAGE_SUCCESS = "Loans have been cleared from the loan book!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetLoans();
        model.resetId();
        model.commitLoanBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
