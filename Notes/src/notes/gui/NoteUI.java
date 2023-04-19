package notes.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import notes.dao.Note;

/**
 * Represents GUI part of single note.
 */
public class NoteUI extends JPanel {

	private static final long serialVersionUID = 1L;
	
	Note noteData;
	JTextField header = new JTextField();
	JEditorPane editField = new JEditorPane();
	JCheckBox isDone = new JCheckBox("Done");
	JPanel buttons = new JPanel();
	
	public NoteUI(Note noteData, ActionListener listener){
		super();
		this.noteData = noteData;
		
		setLayout(new BorderLayout());

		addHeader();
		
		addEditField();
		
		setupButtonContainer();
		
		if (noteData.getCreated() == null) {
			addCreateButton(listener);
		}
		else {			
			addNote(listener);	
		}
		
		add(buttons, BorderLayout.EAST);
	}
	
	public Note getData() {
		noteData.setHeader(header.getText());
		noteData.setDescription(editField.getText());
		noteData.setDone(isDone.isSelected());
		return noteData;
	}

	public Note addNote(ActionListener listener) {
		noteData.setHeader(header.getText());
		noteData.setDescription(editField.getText());
		
		addDoneCheckbox(listener);
	
		addDeleteButton(listener);
		
		addSaveButton(listener);
		
		return noteData;
	}
	
	public Note createNote(ActionListener listener) {
		noteData.setCreated(new Date());
		buttons.removeAll();
		addNote(listener);
		return noteData;
	}
	
	private void addHeader() {
		Font boldFont = new Font(null, Font.BOLD, 12);
		header.setSize(10,30);
		header.setFont(boldFont);
		header.setText(noteData.getHeader());
		add(header,BorderLayout.NORTH);
	}
	
	private void addEditField() {
		editField.setMaximumSize(new Dimension(200, 50));
		editField.setText(noteData.getDescription());
		add(editField,BorderLayout.CENTER);
	}
	
	private void setupButtonContainer() {
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setBackground(Constants.BACKGROUND);
		buttons.add(Box.createRigidArea(new Dimension(0,10)));
	}
	
	private void addSaveButton (ActionListener listener) {
		addButton(listener,Constants.SAVE_TEXT, Constants.SAVE_COMMAND);
	}
	
	private void addDeleteButton (ActionListener listener) {
		addButton(listener,Constants.DELETE_TEXT, Constants.DELETE_COMMAND);
	}
		
	private void addCreateButton (ActionListener listener) {
		Date tempDate = new Date();
		tempDate.setTime(0);
		noteData.setCreated(tempDate);
		addButton(listener,Constants.CREATE_TEXT, Constants.CREATE_COMMAND);
	}
	
	private void addButton (ActionListener listener, String text, String command) {
		JButton button = new JButton(text);
		button.setActionCommand(command + noteData.getCreated().getTime());
		button.addActionListener(listener);
		buttons.add(button);
		buttons.add(Box.createRigidArea(new Dimension(0,10)));
	}
	
	private void addDoneCheckbox(ActionListener listener) {
		isDone.setActionCommand("Done");
		isDone.setSelected(noteData.isDone());
		isDone.addActionListener(listener);
		isDone.setBackground(Constants.BACKGROUND);
		buttons.add(isDone);
		buttons.add(Box.createRigidArea(new Dimension(0,10)));
	}
}
