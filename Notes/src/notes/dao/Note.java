package notes.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * Data object class representing a single note.
 * 
 * @author janik
 */
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean done = false;
	
	private Date created = null;
	
	private String header = "";
	
	private String description = "";
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
