package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;

/**
 * Reverts the {@code model}'s loan book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoLoanBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoLoanBook();
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
