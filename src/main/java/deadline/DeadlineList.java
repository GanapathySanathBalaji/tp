package deadline;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import ui.Ui;

/**
 * Handles all functions related to the list of deadlines.
 */
public class DeadlineList {


    /** Stores the deadline information. */
    public ArrayList<Deadline> deadlines;

    /**
     * Constructor for the DeadlineList class.
     */
    public DeadlineList() {
        deadlines = new ArrayList<Deadline>();
    }

    public DeadlineList(ArrayList<Deadline> deadlines) {
        this.deadlines = deadlines;
    }

    /**
     * Adds a new deadline to the list.
     *
     * @param newDeadline Deadline represents the new deadline tot be added.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void addDeadline(Deadline newDeadline, Ui ui) {
        ui.printLine();
        deadlines.add(newDeadline);
        ui.printMessage("A new deadline with the following information has been added.");
        ui.printMessage(newDeadline.getDeadlineInformation());
        ui.printLine();
    }

    /**
     * Displays the current list of deadlines.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void listDeadlines(Ui ui) {
        ui.printLine();
        System.out.println("Here is the list of deadlines added so far:");
        int deadlineNumber = 1;
        for (Deadline deadline: deadlines) {
            ui.printMessage(deadlineNumber + ") " + deadline.getDeadlineInformation());
            deadlineNumber++;
        }
        ui.printLine();
    }

    /**
     * Deletes the deadline at the specified index.
     *
     * @param index The index (1-based) of the deadline to be deleted.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void deleteDeadline(int index, Ui ui) {
        try {
            deadlines.remove(index - 1);
            ui.printLine();
            ui.printMessage("The deadline at the mentioned index has been deleted");
            ui.printLine();
        } catch (IndexOutOfBoundsException e) {
            ui.printLine();
            ui.printMessage("Enter a valid index");
            ui.printLine();
        }

    }

    /**
     * Clears all the deadlines currently stored.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void clearDeadlines(Ui ui) {
        deadlines.clear();
        ui.printLine();
        ui.printMessage("The list of deadlines is cleared.");
        ui.printLine();
    }

    /**
     * Lists all the tasks sorted by their priority.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void priorityView(Ui ui) {
        ArrayList<Deadline> deadlinesSortedByPriority = deadlines;
        deadlinesSortedByPriority.sort(Comparator.comparingInt(Deadline::getPriority));
        Collections.reverse(deadlinesSortedByPriority);
        ui.printLine();
        int deadlineNumber = 1;
        for (Deadline deadline:deadlinesSortedByPriority) {
            ui.printMessage(deadlineNumber + ") " + deadline.getDeadlineInformation());
            deadlineNumber++;
        }
        if (deadlineNumber == 1) {
            ui.printMessage("The list is empty.");
        }
        ui.printLine();
    }

    /**
     * Lists all the tasks sorted by date along with the days remaining.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void countdownView(Ui ui) {
        ArrayList<Deadline> deadlinesSortedByDate = deadlines;
        deadlinesSortedByDate.sort(Comparator.comparing(Deadline::getDate));
        ui.printLine();
        int deadlineNumber = 1;
        for (Deadline deadline:deadlinesSortedByDate) {
            ui.printMessage(deadlineNumber + ") " + deadline.getDeadlineInformation());
            deadlineNumber++;
        }
        if (deadlineNumber == 1) {
            ui.printMessage("The list is empty.");
        }
        ui.printLine();
    }

    /**
     * Displays the list of deadlines containing the keyword.
     *
     * @param keyword The keyword to be searched for.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void searchDeadlines(String keyword, Ui ui) {
        ui.printLine();
        int deadlineNumber = 1;
        for (Deadline deadline:deadlines) {
            try {
                if (deadline.hasKeyword(keyword)) {
                    ui.printMessage(deadlineNumber + ") " + deadline.getDeadlineInformation());
                    deadlineNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (deadlineNumber == 1) {
            ui.printMessage("The list is empty.");
        }
        ui.printLine();
    }

    /**
     * Adds a new deadline to the list by parsing information from the user given string
     * and calling {@link #addDeadline(Deadline, Ui)} if the information is given in the correct format
     * to add the deadline.
     *
     * @param deadlineDetails Contains all the information related to the deadline as provided by the user.
     * @param ui This allows for the deadline class to interact with Users.
     */
    public void add(String deadlineDetails, Ui ui) {
        try {
            String[] details = deadlineDetails.split(" ",2)[1].split("/");
            String description = details[0];
            String date = details[1].substring(2);
            String dueTime = details[2].substring(2);
            String priority = details[3].substring(2);
            Deadline newDeadline =  new Deadline(description,date,dueTime, priority);
            addDeadline(newDeadline, ui);
        } catch (IndexOutOfBoundsException | DateTimeParseException | NullPointerException e) {
            ui.printLine();
            ui.printMessage("Wrong format to add deadlines");
            ui.printLine();
        } catch (Exception e) {
            ui.printLine();
            ui.printMessage(e.getMessage());
            ui.printLine();
        }
    }
}