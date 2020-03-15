package resourceloader;

import studyarea.Dictionary;
import studyarea.IllegalStudyAreaException;
import studyarea.StudyArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import static ui.Constants.FILE_PATH_DICTIONARY;
import static ui.Constants.FILE_PATH_STUDY_AREAS;
import static ui.Constants.INCONSISTENT_DATA_STORAGE;
import static ui.Constants.MISSING_STUDY_AREA_DATA;

/**
 * This class loads all the required information of Study Areas that is stored in location.txt.
 */

public class StudyAreaLoader {
    private static final String DIVIDER = "~";
    private String url;
    private File file;

    public StudyAreaLoader(String url) throws IllegalStudyAreaException {
        this.url = url;
        loadFile();
    }

    /**
     * Loads content from location.txt and dictionary.txt.
     * @throws IllegalStudyAreaException if file is not found.
     */
    public void loadFile() throws IllegalStudyAreaException {
        try {
            this.file = new File(this.url);
            Dictionary.loadDictionary();
        }  catch (NullPointerException e) {
            throw new IllegalStudyAreaException(MISSING_STUDY_AREA_DATA);
        } catch (FileNotFoundException e) {
            throw new IllegalStudyAreaException(e.getMessage());
        }
    }

    /**
     * Return the imported study area file as an ArrayList for the main controller
     * to add it to its database.
     *
     * @return buffer ArrayList of Location from study area file
     * @throws FileNotFoundException if location.txt does not exist.
     * @throws IllegalStudyAreaException if data stored is inconsistent
     */

    public ArrayList<StudyArea> pushToDatabase() throws IllegalStudyAreaException, FileNotFoundException {
        ArrayList<StudyArea> buffer = new ArrayList<>();
        Scanner input = new Scanner(this.file);
        while (input.hasNextLine()) {
            String detailsOfLocation = input.nextLine();
            String[] detailsBuffer = detailsOfLocation.split(DIVIDER);
            if (detailsBuffer.length != 6) {
                String name = detailsBuffer[0];
                throw new IllegalStudyAreaException(INCONSISTENT_DATA_STORAGE + "at " + name);
            }
            StudyArea studyArea = new StudyArea(detailsBuffer[0], detailsBuffer[1], detailsBuffer[2],
                    Boolean.parseBoolean(detailsBuffer[3]), Boolean.parseBoolean(detailsBuffer[4]),
                    Integer.parseInt(detailsBuffer[5]));
            buffer.add(studyArea);
        }
        input.close();
        return buffer;
    }

    /**
     * This method creates a new data file for locations.txt and dictionary.txt.
     * @throws IOException if cannot create file.
     */
    public static void createNewStudyAreaData() throws IOException {
        Files.createFile(Paths.get(FILE_PATH_STUDY_AREAS));
        Files.createFile(Paths.get(FILE_PATH_DICTIONARY));
        PrintWriter dataBuffer = new PrintWriter(new File(FILE_PATH_STUDY_AREAS));
        dataBuffer.println(BackUpData.BACKUP_LOCATIONS);
        dataBuffer.close();
        dataBuffer = new PrintWriter(new File(FILE_PATH_DICTIONARY));
        dataBuffer.println(BackUpData.BACKUP_DICTIONARY);
        dataBuffer.close();
    }
}

