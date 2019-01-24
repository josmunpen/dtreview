
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Referee extends Actor {

	private Collection<Note>	notes;
	private Collection<Report>	reports;
	private Collection<Complaint> complaints;


	@OneToMany
	public Collection<Note> getNotes() {
		return this.notes;
	}
	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@OneToMany
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}
	@OneToMany
	public Collection<Complaint> getComplaints(){
		return this.complaints;
	}
	public void setComplaints(final Collection<Complaint> complaints){
		this.complaints=complaints;
	}
}
