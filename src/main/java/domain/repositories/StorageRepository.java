package domain.repositories;

import application.helpers.MessageConstants;
import application.helpers.StorageConstants;
import domain.aggregates.tracker.Task;
import domain.exceptions.DukeFileException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StorageRepository implements IStorageRepository{
    public StorageRepository(){

    }

    public File init() throws DukeFileException {
        File file = new File(StorageConstants.FILE_PATH);
        file.setWritable(true);
        file.setReadable(true);
        if(!file.exists()) {
            try {
                file.createNewFile();
                write(file, StorageConstants.HEADER);
            } catch (IOException ex){
                throw new DukeFileException(MessageConstants.TASK_GET_ERROR);
            }
        }
        return file;
    }

    public void write(File file, String row) throws DukeFileException {
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(row + "\n");
            writer.close();
        } catch (IOException ex){
            throw new DukeFileException(MessageConstants.FILE_WRITE_ERROR);
        }
    }

    public void override(ArrayList<Task> tasks) throws DukeFileException {
        try {
            FileWriter writer = new FileWriter(StorageConstants.FILE_PATH);
            writer.append(StorageConstants.HEADER + "\n");
            for (Task task : tasks) {
                writer.append(task.toString() + "\n");
            }
            writer.flush();
        } catch (IOException ex){
            throw new DukeFileException(MessageConstants.FILE_OVERWRITE_ERROR);
        }
    }
}