package myrest.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DAO {

	private static SessionFactory sessionFactory;

	/**
	 * Определяет просрочена ли работа.
	 * 
	 * @param job
	 * @return boolaen
	 */
	private static boolean prosrochena(Job job) {		
		// Конвертируем в формат для удобства расчета
		LocalDate nach = job.getNach().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalDate finish = nach.plusDays(Math.round(job.getDliteln())); // расчет в LocalDate		
		if (LocalDate.now().compareTo(finish) > 0) // просроченная работа
			return true;
		else
			return false;
	}

	/**
	 * Вычисление статуса проекта и средний процент выполнения по его
	 * работам
	 * 
	 * Приеоритет по убванию:
	 * - Проект завершен
	 * - с отклонением по срокам
	 * - с отклонением по бюджету
	 * - реализуется без отклонений
	 * 
	 * Метод работает только внутри транзакции
	 * 
	 * @param Project
	 * @return
	 */
	private static void updateState(Project p) {
		double mproc = 0;
		double mfb = 0;
		double mrb = 0;
		boolean otcl = false; // невыполненная работа с отклонением по срокам
		
		List<Job> jobs = p.getJobs();
		for (Job job : jobs) {
		
			mproc += job.getVipPr();
		
			mfb += job.getFb();
			mrb += job.getRb();
		
			if (job.getOkonch() == null || job.getVipPr() < 1) {
				if (prosrochena(job)) // просроченная работа
					otcl = true;
			}
		}
		
		Double mp = ((double) Math.round((mproc / jobs.size()) * 100) / 100);
		p.setMidPr(mp); // Средний процент выполнения
		
		if (p.getMidPr() >= 1) {
			p.setIdState(4); //Если работа завершена, то это самое главное
			return;
		} else if (otcl == true) {
			p.setIdState(1);
			return;
		} else if (mfb < p.getFb() || mrb < p.getRb()) {
			p.setIdState(2);
			return;
		} else {
			p.setIdState(3);
			return;
		}

	}

	public static boolean init() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

		try {
			sessionFactory = new MetadataSources(registry).addAnnotatedClass(Job.class).addAnnotatedClass(Project.class)
					.addAnnotatedClass(State.class).buildMetadata().buildSessionFactory();
			return true;
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble
			// building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);

			System.err.println(e.getMessage());
		}
		return false;

	}

	public static synchronized List<Project> getProjects() {
		List<Project> result = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			result = session.createQuery("from Project", Project.class).list();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return result;

	}

	public static List<Job> getProjectJobs(int id) {
		List<Job> result = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			Project p = session.byId(Project.class).load(id);
			result = p.getJobs();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}

		return result;
	}

	public static boolean createProject(Project p) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.save(p);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return false;
	}

	public static Project getProject(int id) {
		Project result = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			result = session.byId(Project.class).load(id);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}

		return result;
	}

	public static boolean updateProject(Project p) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			Project pReal = session.byId(Project.class).load(p.getId()); // get persistent object by id
			if (pReal == null)
				return false;

			if (p.getName() != null)
				pReal.setName(p.getName());
			if (p.getFb() != null)
				pReal.setFb(p.getFb());
			if (p.getRb() != null)
				pReal.setRb(p.getRb());
			updateState(pReal); // calculate state
			session.update(pReal); // save
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}

		return false;
	}

	public static boolean delProject(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.createQuery("delete from Project where id = ?1").setParameter(1, id).executeUpdate();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return false;
	}

	public static List<Project> getProjectsNerasp() {
		List<Project> result = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> re = session
					.createNativeQuery("SELECT project.id, SUM(job.fb) as a, SUM(job.rb) as b "
							+ "FROM project LEFT JOIN public.job ON project.id = job.id_project "
							+ "GROUP BY project.id " + "HAVING SUM(job.fb) != project.fb OR SUM(job.rb) != project.rb;")
					.list();
			if (re.size() > 0) {
				result = new ArrayList<>();
				for (Object[] r : re) {
					int id = (int) r[0];
					Project p = session.byId(Project.class).load(id);
					result.add(p);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		session.getTransaction().commit();
		session.close();

		return result;
	}

	public static boolean addJob(Job job) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.save(job);
			session.flush();
			Project p = session.byId(Project.class).load(job.getIdProject());
			updateState(p);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return false;
	}

	public static boolean updateJob(Job j) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			Job jReal = session.byId(Job.class).load(j.getId()); // get persistent object by id
			if (jReal == null)
				return false;

			//необязательность полей (два раза делается вызов, за-то выглядит компактнее)
			if (j.getName() != null)
				jReal.setName(j.getName());
			if (j.getFb() != null)
				jReal.setFb(j.getFb());
			if (j.getRb() != null)
				jReal.setRb(j.getRb());
			if (j.getNach() != null)
				jReal.setNach(j.getNach());
			if (j.getOkonch() != jReal.getOkonch())
				jReal.setOkonch(j.getOkonch());
			if (j.getDliteln() != null)
				jReal.setDliteln(j.getDliteln());
			if (j.getVipPr() != null)
				jReal.setVipPr(j.getVipPr());
			
			session.update(jReal); // save
			updateState(jReal.getProject()); // calculate state
			
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}

		return false;
	}

	public static boolean delJob(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			Job job = session.byId(Job.class).load(id);
			Project pr = job.getProject();
			session.delete(job);
			session.flush();
			updateState(pr);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return false;
	}

	public static List<Job> getProsrochJobs() {
		List<Job> result = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			result = session.createQuery("select j from Job j where j.okonch = null", Job.class).list();
			for (Iterator<Job> it = result.iterator(); it.hasNext();)
				if (!prosrochena(it.next())) // непросроченная работа
					it.remove();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		session.getTransaction().commit();
		session.close();

		return result;
	}
}
