package test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;
import entity.Chancellor;
import entity.Faculty;
import entity.Professor;
import entity.University;

public class Test {

	public void saveData(Session session) {
		Transaction t = session.beginTransaction();

		Chancellor chSTU = new Chancellor();
		chSTU.setName("Robert Redhammer");
		Chancellor chUK = new Chancellor();
		chUK.setName("Karol Mièieta");

		Professor p1 = new Professor();
		p1.setName("Jano");
		p1.setSalary(3000);
		Professor p2 = new Professor();
		p2.setName("Jakub");
		p2.setSalary(2000);
		Professor p3 = new Professor();
		p3.setName("Jana");
		p3.setSalary(1500);
		Professor p4 = new Professor();
		p4.setName("Hana");
		p4.setSalary(4000);

		List<Professor> fiitProf = new ArrayList<>();
		fiitProf.add(p1);
		fiitProf.add(p4);
		List<Professor> feiProf = new ArrayList<>();
		feiProf.add(p1);
		feiProf.add(p2);
		List<Professor> fmfiProf = new ArrayList<>();
		fmfiProf.add(p3);
		List<Professor> ftvsProf = new ArrayList<>();
		ftvsProf.add(p4);

		University stu = new University();
		stu.setName("STU");
		stu.setChancellor(chSTU);
		University uk = new University();
		uk.setName("UK");
		uk.setChancellor(chUK);

		Faculty fiit = new Faculty();
		fiit.setName("FIIT");
		fiit.setProfessors(fiitProf);
		fiit.setUniversity(stu);
		Faculty fei = new Faculty();
		fei.setName("FEI");
		fei.setProfessors(feiProf);
		fei.setUniversity(stu);
		Faculty fmfi = new Faculty();
		fmfi.setName("FMFI");
		fmfi.setProfessors(fmfiProf);
		fmfi.setUniversity(uk);
		Faculty ftvs = new Faculty();
		ftvs.setName("FTVŠ");
		ftvs.setProfessors(ftvsProf);
		ftvs.setUniversity(uk);
		
		List<Faculty> p1Fac = new ArrayList<>();
		p1Fac.add(fiit);
		p1Fac.add(fei);

		List<Faculty> p2Fac = new ArrayList<>();
		p2Fac.add(fei);

		List<Faculty> p3Fac = new ArrayList<>();
		p3Fac.add(fmfi);

		List<Faculty> p4Fac = new ArrayList<>();
		p4Fac.add(fiit);
		p4Fac.add(ftvs);
		
		p1.setFaculties(p1Fac);
		p2.setFaculties(p2Fac);
		p3.setFaculties(p3Fac);
		p4.setFaculties(p4Fac);
		
		List<Faculty> stuFac = new ArrayList<>();
		stuFac.add(fiit);
		stuFac.add(fei);
		List<Faculty> ukFac = new ArrayList<>();
		ukFac.add(fmfi);
		ukFac.add(ftvs);
		stu.setFaculties(stuFac);
		uk.setFaculties(ukFac);

		session.persist(chSTU);
		session.persist(chUK);
		
		session.persist(stu);
		session.persist(uk);
		
		session.persist(fiit);
		session.persist(fei);
		session.persist(fmfi);
		session.persist(ftvs);

		session.persist(p1);
		session.persist(p2);
		session.persist(p3);
		session.persist(p4);
		
		t.commit();
	}

	/*
	 * select all fields from professors and write their names, salaries and
	 * names of faculties they work at
	 */
	public void HQLselectData(Session session) {
		String hql = "FROM Professor P";
		Query query = session.createQuery(hql);
		List results = query.list();

		for (Object i : results) {
			Professor p = (Professor)i;
			System.out.println(p.getId() + " " + p.getName() + " " + p.getSalary() + " " + p.getFaculties());
		}
	}
	
	public void nativeSQLselectData(Session session) {
		String sql = "SELECT * FROM UNIVERSITY";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(University.class);
		List<University> results = query.list();
		
		for (University i : results) {
			System.out.println(i.getId() + " " + i.getName() + " " + " " + i.getChancellor() + " " + i.getFaculties());
			for(Faculty j : i.getFaculties()) {
				System.out.println(" " + j.getName());
			}
		}
	}
	
	public void namedQuerySelectData(Session session) {
		Query query = session.getNamedQuery("University.findAll");
		List<University> results = query.list();
		
		for (University i : results) {
			System.out.println(i.getId() + " " + i.getName() + " " + " " + i.getChancellor() + " " + i.getFaculties());
		}
	}
	
	public void criteriaQuery(Session session) {
		Criteria cr = session.createCriteria(Professor.class);
		cr.add(Restrictions.gt("salary", 1500));
		cr.add(Restrictions.like("name", "J%"));
		cr.addOrder(Order.asc("salary"));
		
		List<Professor> results = cr.list();
		
		for (Professor i : results) {
			System.out.println(i.getId() + " " + i.getName() + " " + i.getSalary());
		}
	}

	/*
	 * update chancellor in stu
	 */
	public void updateData(Session session) {
		Transaction t = session.beginTransaction();
		
		Chancellor newchstu = new Chancellor();
		newchstu.setName("Janko Hraško");
		University stu = (University) session.get(University.class, 3);
		stu.setChancellor(newchstu);
		session.persist(newchstu);
		session.persist(stu);
		
		t.commit();
	}

	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Test test = new Test();
		test.saveData(session);
		//test.HQLselectData(session);
		//test.nativeSQLselectData(session);
		//test.namedQuerySelectData(session);
		//test.criteriaQuery(session);
		//test.updateData(session);
		
		session.close();
	}

}
