package notes.backend;

import java.util.ArrayList;

import notes.dao.Note;
import notes.main.NotesException;

/**
 * Defines backend operations for the application.
 * @author janik
 */
public interface NoteService {

	public void saveNote(Note note);
	
	public ArrayList<Note> getNotes() throws NotesException;
	
	public void deleteNote(Note note);
}
