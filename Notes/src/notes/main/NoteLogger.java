package notes.main;

public class NoteLogger {

	public static void log(String s) {
		System.out.println(s);
	}
	
	public static void log(Exception e) {
		e.printStackTrace();
	}
}
