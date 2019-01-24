
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.HandyWorker;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//Query 10.2
	//Tenemos que sacar las fixuptasks de las aplicaciones de un customer
	@Query("select f.applications from Customer c join c.fixUpTasks f where c.id=?1")
	Collection<Application> findByCustomerId(int customerId);

	//Query 11.3
	@Query("select h.applications from HandyWorker h where h.id=?1")
	Collection<Application> findByHandyWorkerId(int handyWorkerId);

	//10.2
	//Sacamos la Appplication cuyo id pertenece a la application que queremos modificar
	@Query("select a from Application a where a.id=?1")
	Application findById(int applicationId);

	//12.5
	@Query("select count(a1)*1.0 / (select count(a2)*1.0 from Application a2) from Application a1 where a1.status = 'pending'")
	double pendingApplications();

	@Query("select count(a1)*1.0 / (select count(a2)*1.0 from Application a2) from Application a1 where a1.status = 'accepted'")
	double acceptedApplications();

	@Query("select count(a1)*1.0 / (select count(a2)*1.0 from Application a2) from Application a1 where a1.status = 'rejected'")
	double rejectedApplications();

	@Query("select count(a1)*1.0/(select count(a2)*1.0 from Application a2) from Application a1 where a1.status = 'pending' and a1.fixUpTask.endDate < current_timestamp()")
	double elapsedApplications();

	@Query("select avg(a.offeredPrice), min(a.offeredPrice), max(a.offeredPrice),stddev(a.offeredPrice) from Application a")
	String offeredPriceStatistics();

	@Query("select h from HandyWorker h join h.applications a where a.id=?1")
	HandyWorker findHwByApplicationId(int id);
}
