package seedu.duke;

import event.EventList;
import resourceloader.EventLoader;
import ui.Ui;

import java.io.File;
import java.util.Scanner;

public class Duke {

    private static final String FILE_PATH = "library" + File.separator + "eventList.txt";
    public static Ui ui = new Ui();
    public static EventLoader eventLoader;
    public static final String ADD_COMMAND = "add";
    public static final String VIEW_COMMAND = "view";
    public static final String PRIORITY_VIEW_COMMAND = "priority_view";
    public static final String COUNTDOWN_VIEW_COMMAND = "countdown";
    public static final String CLEAR_COMMAND = "clear";
    public static final String SEARCH_COMMAND = "search";
    public static final String DELETE_COMMAND = "delete";
    public static final String INVALID_INDEX_MESSAGE = "Enter a valid index";
    public static final String INVALID_COMMAND_MESSAGE = "Enter a valid command";
    public static final String BYE_COMMAND = "bye";
    public static final String BYE_MESSAGE = "Bye!!!!!!";
    static EventList eventList;

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) throws Exception {
        ui.printWelcomeMessage();

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
        eventLoader = new EventLoader(FILE_PATH);
        eventList = new EventList(eventLoader.loadFile());
        runCommands();
        eventLoader.saveEvents(eventList.events);
    }

    private static void runCommands() {
        Scanner in = new Scanner(System.in);
        String command;
        command = in.nextLine();
        while (!command.equals(BYE_COMMAND)) {
            try {
                String commandType = command.split(" ")[0];
                switch (commandType) {
                case ADD_COMMAND:
                    eventList.add(command);
                    break;
                case VIEW_COMMAND:
                    eventList.listEvents();
                    break;
                case PRIORITY_VIEW_COMMAND:
                    eventList.priorityView();
                    break;
                case COUNTDOWN_VIEW_COMMAND:
                    eventList.countdownView();
                    break;
                case CLEAR_COMMAND:
                    eventList.clearEvents();
                    break;
                case SEARCH_COMMAND:
                    eventList.searchEvents(command.split(" ", 2)[1]);
                    break;
                case DELETE_COMMAND:
                    eventList.deleteEvent(Integer.parseInt(command.split(" ", 2)[1]));
                    break;
                default:
                    ui.printLine();
                    System.out.println(INVALID_COMMAND_MESSAGE);
                    ui.printLine();
                    break;
                }
            } catch (NumberFormatException e) {
                ui.printLine();
                System.out.println(INVALID_INDEX_MESSAGE);
                ui.printLine();
            } catch (IndexOutOfBoundsException e) {
                ui.printLine();
                System.out.println(INVALID_COMMAND_MESSAGE);
                ui.printLine();
            }
            command = in.nextLine();
        }
        System.out.println(BYE_MESSAGE);
    }
}
