
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;
import domain.Administrator;
import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Phase;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	//Repositories
	@Autowired
	public FixUpTaskRepository	fixUpTaskRepository;

	//Services
	@Autowired
	public CustomerService		customerService;

	@Autowired
	public HandyWorkerService	handyWorkerService;

	@Autowired
	public AdministratorService	administratorService;

	@Autowired
	public FinderService		finderService;

	@Autowired
	public ComplaintService		complaintService;


	//Constructor
	public FixUpTaskService() {
		super();
	}

	//Simple CRUD

	//10.1

	public FixUpTask create() {
		//Logged user must be a customer
		Assert.isTrue(LoginService.getPrincipal().isAccountNonLocked());
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final FixUpTask res = new FixUpTask();

		res.setTicker(TickerGenerator.generateTicker());
		res.setDescription("");
		res.setAddress("");
		res.setMaximumPrice(0);
		res.setStartDate(new Date());
		res.setEndDate(new Date());
		res.setCategory(new Category());
		final ArrayList<Application> applications = new ArrayList<Application>();
		res.setApplications(applications);
		final ArrayList<Phase> phases = new ArrayList<Phase>();
		res.setPhases(phases);
		final ArrayList<Warranty> warranties = new ArrayList<Warranty>();
		res.setWarranty(warranties);
		res.setMoment(Calendar.getInstance().getTime());

		return res;
	}
	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//fixUpTask's owner ID must be the same as LoggedCustomerId
		if (fixUpTask.getId() != 0) {
			final Customer logCustomer;
			logCustomer = this.customerService.findByPrincipal();
			Assert.notNull(logCustomer);
			Assert.isTrue(logCustomer.getId() != 0);
			Assert.isTrue(logCustomer.getFixUpTasks().contains(fixUpTask));
		}

		//Business logic
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));
		if (fixUpTask.getWarranty() != null)
			for (final Warranty w : fixUpTask.getWarranty())
				Assert.isTrue(w.isFinalMode());
		final FixUpTask res;
		res = this.fixUpTaskRepository.save(fixUpTask);
		if (fixUpTask.getId() == 0) {
			final Customer logCustomer2 = this.customerService.findByPrincipal();
			final Collection<FixUpTask> fut = logCustomer2.getFixUpTasks();
			fut.add(res);
			logCustomer2.setFixUpTasks(fut);
		}
		//this.customerService.save(logCustomer2);

		return res;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//fixUpTask's owner ID must be the same as LoggedCustomerId
		final Customer logCustomer;
		logCustomer = this.customerService.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.isTrue(logCustomer.getId() != 0);
		Assert.isTrue(logCustomer.getFixUpTasks().contains(fixUpTask));

		//Business logic
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));

		//		if (fixUpTask.getComplaints() != null)
		//			for (final Complaint c : fixUpTask.getComplaints())
		//				this.complaintService.delete(c);

		final Collection<FixUpTask> futs = logCustomer.getFixUpTasks();
		futs.remove(fixUpTask);
		logCustomer.setFixUpTasks(futs);
		this.fixUpTaskRepository.delete(fixUpTask);
	}
	public Collection<FixUpTask> findByCustomer() {

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		res = this.fixUpTaskRepository.findByCustomerId(customer.getId());
		return res;
	}

	//11.1
	public Collection<FixUpTask> findAll() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.findAll();
		return res;
	}

	//11.2
	public Collection<FixUpTask> fixUpTaskFilterByKeyword(final String keyword) {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final ArrayList<FixUpTask> res = new ArrayList<>();
		res.addAll(this.fixUpTaskRepository.fixUpTaskFilterByKeyword(keyword));
		return res;

	}

	public Collection<FixUpTask> fixUpTaskFilterByCategory(final String category) {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.fixUpTaskFilterByCategory(category);
		return res;
	}

	public Collection<FixUpTask> fixUpTaskFilterByRangeOfPrices(final Double minPrice, final Double maxPrice) {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.fixUpTaskFilterByRangeOfPrices(minPrice, maxPrice);
		return res;
	}

	public Collection<FixUpTask> fixUpTaskFilterByRangeOfDates(final Date minDate, final Date maxDate) {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Collection<FixUpTask> res = null;
		//res = this.fixUpTaskRepository.fixUpTaskFilterByRangeOfDates(minDate, maxDate);
		return res;
	}

	public Collection<FixUpTask> fixUpTaskFilterByWarranty(final String title) {
		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.fixUpTaskFilterByWarranty(title);
		return res;
	}

	//12.5
	public String applicationsStatistics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.fixUpTaskRepository.applicationsStatistics();
	}

	public String maximunPriceStatistics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.fixUpTaskRepository.maximunPriceStatistics();
	}

	//37.2
	public Collection<FixUpTask> finderResults(final Integer finderId) {
		//Logged user must be a HandyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.fixUpTaskRepository.finderResults(finderId);
	}

	//38.5
	public ArrayList<Object> complaintsStatistics() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.fixUpTaskRepository.complaintsStatistics();
	}

	public double fixUpTasksWithComplaints() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.fixUpTaskRepository.fixUpTasksWithComplaints();
	}

	public FixUpTask findOne(final int id) {
		FixUpTask res;

		Assert.isTrue(id != 0);

		res = this.fixUpTaskRepository.findOne(id);
		Assert.notNull(res);

		return res;

	}

	//support methods
	public FixUpTask saveForAppTest(final FixUpTask fixuptask) {
		return this.fixUpTaskRepository.save(fixuptask);
	}

	public Collection<FixUpTask> findByCustomerForEndorsementService(final Customer customer) {
		return this.fixUpTaskRepository.findByCustomerId(customer.getId());
	}

	public FixUpTask saveForTest(final FixUpTask fut) {
		return this.fixUpTaskRepository.save(fut);
	}

	public Collection<FixUpTask> findByHandyWorkerId(final Integer hwId) {
		return this.fixUpTaskRepository.findByHandyWorkerId(hwId);
	}
	public Customer findCustomerByFixUpTask(final int id) {
		return this.fixUpTaskRepository.findCustomerByFixUpTask(id);
	}
}
