package notes.main;

import javax.swing.SwingUtilities;

import notes.backend.FileSystemNoteService;
import notes.gui.MainWindow;

public class Notes {

	private static String noteFolder = "C:\\temp";
			
	public Notes() {	
	}
	
	public static void main(String[] args) {
		
		if (args != null && args.length >0)
			noteFolder = args[0];
		
		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
            	MainWindow frame = new MainWindow(new FileSystemNoteService(noteFolder));
            	frame.setVisible(true);
            }
        });
		
		NoteLogger.log("Notes started");
	}

}
