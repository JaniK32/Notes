package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Date;

import org.junit.jupiter.api.Test;

import notes.backend.FileSystemNoteService;
import notes.dao.Note;

/**
 * Just one quickly made stest case at the moment.
 * There should be more of these...
 * 
 * @author janik
 */
class SaveNoteTest {

	@Test
	void testSaveNote() {
		FileSystemNoteService noteService = new FileSystemNoteService("C:\\temp");
		Note testNote = getTestNote();
		noteService.saveNote(testNote);
		File[] files = getTestFile("C:\\temp\\", Long.toString(testNote.getCreated().getTime()));
		assertEquals(1, files.length);
	}

	private Note getTestNote() {
		Note note = new Note();
		note.setCreated(new Date());
		return note;
	}
	
	private File[] getTestFile(String path, String name) {
		File folder = new File(path);
		File[] files = folder.listFiles((file) -> {
            return file.getName().equals(name);
        	}
		);
		return files;
	}
}
