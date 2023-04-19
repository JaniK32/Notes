package notes.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import notes.dao.Note;
import notes.main.NoteLogger;
import notes.main.NotesException;

/**
 * Takes care of backend operations against a file system.
 * @author janik
 */
public class FileSystemNoteService implements NoteService{

	private String noteFolder;
	
	public FileSystemNoteService(String noteFolder) {
		this.noteFolder = noteFolder + "\\";
	}
	
	public void saveNote(Note note) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(noteFolder + note.getCreated().getTime()));
			oos.writeObject(note);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			NoteLogger.log(e);
		} catch (IOException e) {
			NoteLogger.log(e);
		}	
	}
	
	public ArrayList<Note> getNotes() throws NotesException {

		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			String [] fileNames = new File(noteFolder).list();
			for (String fileName : fileNames){
				 ObjectInputStream inStream  = new ObjectInputStream(new FileInputStream(noteFolder + fileName));
				 Note note = (Note) inStream.readObject();
				 inStream.close();
				 notes.add(note);
			}
		} catch (ClassNotFoundException e) {
			NoteLogger.log(e);
			throw new NotesException(e);
		} catch (IOException e) {
			NoteLogger.log(e);
			throw new NotesException(e);
		}
	    return notes;
	}
		
	public void deleteNote(Note note) {
		File file =  new File(noteFolder + note.getCreated().getTime());
		file.delete();
	}
}
