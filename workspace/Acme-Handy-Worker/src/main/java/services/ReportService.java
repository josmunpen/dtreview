
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;
import domain.Complaint;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	public ReportRepository		reportRepository;

	@Autowired
	public AdministratorService	administratorService;


	//35.2
	public Report reportByComplaint(final Complaint complaint) {
		final Report res;
		//Comprobamos que el logged user es un customer
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		res = this.reportRepository.getReportByComplaintId(complaint.getId());
		return res;
	}

	//36.1
	public Report create() {
		//Comprobamos que el logged user es un referee
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final Report res = new Report();
		res.setAttachments("");
		res.setComplaint(new Complaint());
		res.setDescription("");
		res.setMoment(new Date());
		res.setNotes(new ArrayList<Note>());

		return res;

	}
	public Report AssignComplaint(final Report report, final Complaint complaint) {
		//Comprobamos que el logged user es un referee
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final Report res = report;
		res.setComplaint(complaint);
		return this.reportRepository.save(res);

	}

	//38.5
	public ArrayList<Object> notesStatistics() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.reportRepository.notesStatistics();
	}

	public Report save(final Report report) {
		return this.reportRepository.save(report);
	}

	public Collection<Report> findAll() {
		return this.reportRepository.findAll();
	}

}
