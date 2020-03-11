package command;

import event.EventList;
import ui.Ui;

/**
 * Object represents a command and contains all the required information and implementation to execute it.
 */
public abstract class Command {

    /**
     * Executes the command based on the information provided by the user.
     *
     * @param eventList Contains the list of tasks on which the commands are executed on.
     * @param ui This handles the interaction with Users.
     * @throws Exception If issues are found during execution of command.
     */
    public abstract void executeCommand(EventList eventList, Ui ui) throws Exception;

}