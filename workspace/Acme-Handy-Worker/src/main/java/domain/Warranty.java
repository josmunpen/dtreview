
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	private String	title;
	private String	terms;
	private String	applicableLaws;
	private boolean	finalMode;


	@Column(unique = true)
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getTerms() {
		return this.terms;
	}
	public void setTerms(final String terms) {
		this.terms = terms;
	}
	@NotBlank
	public String getApplicableLaws() {
		return this.applicableLaws;
	}
	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}
	public boolean isFinalMode() {
		return this.finalMode;
	}
	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

}
