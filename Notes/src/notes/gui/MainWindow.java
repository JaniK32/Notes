package notes.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import notes.backend.NoteService;
import notes.dao.Note;
import notes.main.NoteLogger;
import notes.main.NotesException;

/**
 * Forms a GUI for the application and takes care of actions.
 */
public class MainWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	NoteService noteService;
	Map<String,NoteUI> noteComponents = new HashMap<String,NoteUI>();
	Map<String,Component> bottomSeparators = new HashMap<String,Component>();
	List<NotesException> errors = new ArrayList<NotesException>();
	
	public MainWindow(NoteService noteService) {
		this.noteService = noteService;

		setupMainWindow();
		
		addExistingNotes();

		addEmptyNote();
		
		showErrorMessages();
	}
	
	private void setupMainWindow() {
		setBounds(100, 100, 450, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().setBackground(Constants.BACKGROUND);
	}
	
	private void addExistingNotes() {
		try {
			for (Note note : noteService.getNotes()) {
				NoteUI noteComponent = new NoteUI(note,this);
				String createTimeAsString = Long.toString(note.getCreated().getTime());
				noteComponents.put(createTimeAsString, noteComponent);
				add(noteComponent);
				addBottomSeparator(createTimeAsString);
			}
		} catch (NotesException e) {
			errors.add(e);
		}
	}
	
	private void addBottomSeparator(String createTimeAsString) {
		Component bottomSeparator = Box.createRigidArea(new Dimension(0,Constants.BOTTOM_SEPARATOR_HEIGHT_PX));
		add(bottomSeparator);
		bottomSeparators.put(createTimeAsString, bottomSeparator);
	}
	
	private void removeBottomSeparator(String createTimeAsString) {
		Component bottomSeparator = bottomSeparators.remove(createTimeAsString);
		remove(bottomSeparator);
	}
	
	private void addEmptyNote() {
		Note note = new Note();
		NoteUI noteComponent = new NoteUI(note, this);
		add(noteComponent);
		noteComponents.put(Constants.CREATE_COMMAND, noteComponent);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		NoteLogger.log(command);
		
		if (command.startsWith(Constants.CREATE_COMMAND)) {
			NoteUI noteComponent = noteComponents.remove(Constants.CREATE_COMMAND);
			Note note = noteComponent.createNote(this);
			String createTimeAsString = Long.toString(note.getCreated().getTime());
			noteComponents.put(createTimeAsString , noteComponent);
			noteService.saveNote(note);
			addBottomSeparator(createTimeAsString);
			addEmptyNote();
			refreshScreen();
		}
		else if (command.startsWith(Constants.SAVE_COMMAND)) {
			NoteUI updated = noteComponents.get(command.substring(Constants.SAVE_COMMAND.length()));
			noteService.saveNote(updated.getData());
		}
		else if (command.startsWith(Constants.DELETE_COMMAND)) {
			String createTimeAsString = command.substring(Constants.DELETE_COMMAND.length());
			NoteUI deleted = noteComponents.remove(createTimeAsString);
			noteService.deleteNote(deleted.getData());
			removeBottomSeparator(createTimeAsString);
			remove(deleted);
			refreshScreen();
		}
	}
	
	private void refreshScreen() {
		validate();
		repaint();
	}

	private void showErrorMessages() {
		if (errors.size()>0) {
			JOptionPane.showMessageDialog(this, errors.get(0).getMessage() + "\nSee log for details.");
		}	
	}
}
