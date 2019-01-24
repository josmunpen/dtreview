
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	//Repository
	@Autowired
	public ApplicationRepository	applicationRepository;

	//Services
	@Autowired
	public CustomerService			customerService;

	@Autowired
	public MessageService			messageService;
	@Autowired
	public HandyWorkerService		handyWorkerService;

	@Autowired
	public FixUpTaskService			fixUpTaskService;

	@Autowired
	public CreditCardService		ccService;


	//Constructor
	public ApplicationService() {
		super();
	}

	//Simple CRUD
	public Application create() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final FixUpTask fixUpTask = new FixUpTask();

		final Application res = new Application();

		res.setFixUpTask(fixUpTask);
		res.setMoment(new Date());
		res.setStatus("pending");
		res.setOfferedPrice(0);
		res.setComment("");
		res.setRejectedCause("");

		final CreditCard cc = new CreditCard();

		res.setCreditCard(cc);

		return res;
	}
	//Complex methods
	//10.2
	public Collection<Application> findByCustomer() {
		Collection<Application> res;

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(customer.getId());

		res = this.applicationRepository.findByCustomerId(customer.getId());
		return res;
	}

	public Application saveByCustomer(final Application application) {
		Assert.notNull(application);

		Application res;

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged customer is application's fix up task owner
		final Customer logCustomer;
		logCustomer = this.customerService.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.notNull(logCustomer.getId());
		final List<Application> l1 = new ArrayList<Application>();
		final Collection<FixUpTask> col1 = logCustomer.getFixUpTasks();
		for (final FixUpTask f : col1)
			l1.addAll(f.getApplications());
		Assert.isTrue(l1.contains(application));

		//Atributes that cant be changed
		final Application oldApplication = this.applicationRepository.findById(application.getId());
		Assert.isTrue(application.getMoment() == oldApplication.getMoment());
		Assert.isTrue(application.getOfferedPrice() == oldApplication.getOfferedPrice());
		Assert.notNull(application.getStatus());
		Assert.isTrue(application.getStatus().equals("pending") || application.getStatus().equals("accepted") || application.getStatus().equals("rejected"));

		if (application.getStatus().equals("accepted"))
			Assert.notNull(application.getCreditCard());
		else if (application.getStatus().equals("rejected"))
			Assert.notNull(application.getRejectedCause());

		res = this.applicationRepository.save(application);
		return res;
	}

	public Application saveByHandyWorker(final Application application) {
		Assert.notNull(application);

		Application res;

		//Logged user must be a handy worker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged handy worker is application's owner
		final HandyWorker logHandyWorker;
		logHandyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(logHandyWorker);
		Assert.notNull(logHandyWorker.getId());
		final List<Application> apps = new ArrayList<Application>();
		apps.addAll(logHandyWorker.getApplications());
		Assert.isTrue(apps.contains(application));

		Assert.isTrue(application.getOfferedPrice() > 0);

		application.setMoment(Calendar.getInstance().getTime());

		res = this.applicationRepository.save(application);
		return res;
	}

	//11.3
	public Collection<Application> findByHandyWorker() {
		Collection<Application> res;

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged handyWorker
		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		Assert.notNull(handyWorker.getId());

		res = this.applicationRepository.findByHandyWorkerId(handyWorker.getId());
		return res;
	}

	//12.5
	public double pendingApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.pendingApplications();
	}

	public double acceptedApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.acceptedApplications();
	}

	public double rejectedApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.rejectedApplications();
	}

	public double elapsedApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.elapsedApplications();
	}

	public String offeredPriceStatisctics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.offeredPriceStatistics();
	}

	public Application save(final Application application) {

		final Application res = application;

		if (application.getId() != 0 && application.getCreditCard() != null) {
			final CreditCard cc = res.getCreditCard();
			final CreditCard savedCC = this.ccService.save(cc);
			res.setCreditCard(savedCC);
		}
		final Application result = this.applicationRepository.save(res);
		if (application.getId() != 0) {
			final Application old;
			old = this.applicationRepository.findOne(application.getId());

			if (old.getStatus() == "pending") {
				this.messageService.applicationCustomerMessage(result);
				this.messageService.applicationHwMessage(result);
			}
		}

		if (application.getId() == 0) {
			final HandyWorker log = this.handyWorkerService.findByPrincipal();
			final Collection<Application> apps = log.getApplications();
			apps.add(result);
			log.setApplications(apps);
		}
		return result;
	}
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	//support method for saveByCustomer in EndorsementService
	public Collection<Application> findByHandyWorkerForEndorsementService(final HandyWorker hw) {
		Collection<Application> res;

		res = this.applicationRepository.findByHandyWorkerId(hw.getId());
		return res;
	}

	public Application findOne(final int applicationId) {
		Assert.isTrue(applicationId != 0);

		Application res;

		res = this.applicationRepository.findOne(applicationId);
		Assert.notNull(res);

		return res;
	}

	public HandyWorker findHwByApplicationId(final int id) {
		return this.applicationRepository.findHwByApplicationId(id);
	}
}
