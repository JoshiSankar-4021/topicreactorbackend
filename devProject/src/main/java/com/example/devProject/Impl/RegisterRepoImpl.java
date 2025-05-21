package com.example.devProject.Impl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.devProject.entities.Registration;
import com.example.devProject.repositories.RegistrationRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class RegisterRepoImpl implements RegistrationRepo {

	@PersistenceContext
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Integer isExist(String email, String password) {
		String hql="select r.id from Registration r where email=:email and password=:password";
		return (Integer)entityManager.createQuery(hql).setParameter("email",email)
				.setParameter("password",password).getSingleResult();
	}

	@Override
	public Page<Registration> findByKeyword(String keyword, Pageable pageable) {
		String hql = "From Registration r where r.firstName LIKE:keyword";
		Query query=entityManager.createQuery(hql,Registration.class).setParameter("keyword","%"+keyword+"%").
					setFirstResult((int)pageable.getOffset()).setMaxResults(pageable.getPageSize());
		List<Registration> list = query.getResultList();
	    String countHql = "SELECT COUNT(r) FROM Registration r WHERE r.firstName LIKE :keyword";
	    Long total = (Long) entityManager.createQuery(countHql)
	                                      .setParameter("keyword", "%" + keyword + "%")
	                                      .getSingleResult();

	    return new PageImpl<>(list, pageable, total); 
	}

	@Override
	public Page<Registration> finduserwithoutkeyword(Pageable pageable) {
		 String hql = "FROM Registration r"; 
		    Query query = entityManager.createQuery(hql, Registration.class);
		    query.setFirstResult((int) pageable.getOffset());
		    query.setMaxResults(pageable.getPageSize());
		    List<Registration> registrations = query.getResultList();
		    String countHql = "SELECT COUNT(r) FROM Registration r";
		    Long total = (Long) entityManager.createQuery(countHql).getSingleResult();
		    return new PageImpl<>(registrations, pageable, total);
	}

	@Override
	public void saveuser(Registration registration) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
	    
	    try {
	        session.persist(registration);	        
	        transaction.commit();
	    } catch (Exception e) {
	       if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}

	@Override
	public Integer getIdByEmail(String email) {
		String hql = "select r.id from Registration r where r.email:email";
		return (Integer) entityManager.createQuery(hql).setParameter("email",email).getSingleResult();
	}

//	@Override
//	public void updateUser(Integer id, String firstName, String lastName, String phnum, String address, String gender,
//			String password, boolean physicallyHandicaped, boolean legalIssues, LocalDate dob, Integer age,
//			Integer education, String email, String profileImagePath) {
//		// TODO Auto-generated method stub
//		
//	}
	
	@Override
	public void updateUser(Registration registration) {
		String hql ="update Registration r SET r.firstName=:firstName,"
				+ "r.lastName=:lastName,r.phnum=:phnum,r.address=:address,r.password=:password,"
				+ "r.gender=:gender,r.physicallyHandicaped=:physicallyHandicaped,r.legalIssues=:legalIssues,"
				+"r.education=:education,r.profileImagePath=:profileImagePath where r.id=:id";
				entityManager.createQuery(hql).
				setParameter("firstName", registration.getFirstName()).
				setParameter("lastName", registration.getLastName()).
				setParameter("phnum", registration.getPhnum()).
				setParameter("address", registration.getAddress()).
				setParameter("password", registration.getPassword()).
				setParameter("gender", registration.getGender()).
				setParameter("physicallyHandicaped",registration.getphysicallyHandicaped()).
				setParameter("legalIssues",registration.getphysicallyHandicaped()).
				setParameter("education", registration.getEducation()).setParameter("profileImagePath", registration.getProfileImagePath()).
				setParameter("id", registration.getId()).executeUpdate();
	}

	@Override
	public String getImagePathByUserId(Integer id) {
		String hql ="select r.profileImagePath from Registration r where r.id=:id";
		return (String)entityManager.createQuery(hql).setParameter("id",id).getSingleResult();
	}

	@Override
	public List<Registration> getAllUsers() {
		String hql="From Registration r";
		return entityManager.createQuery(hql,Registration.class).getResultList();
	}

	@Override
	public void deleteUserById(Integer id) {
		String hql = "Delete From Registration r where r.id=:id";
		entityManager.createQuery(hql).setParameter("id", id).executeUpdate();
	}

	@Override
	public String findProfilePathById(Integer id) {
		String hql = "Select r.profileImagePath from Registration r where r.id=:id";
		return (String) entityManager.createQuery(hql).setParameter("id",id).getSingleResult();
	}

	@Override
	public Registration findUserById(Integer id) {	
		String hql = "select r from Registration r where id=:id";
		
		return entityManager.createQuery(hql,Registration.class).setParameter("id",id).getSingleResult();
	}

	@Override
	public int getCountEmail(String email) {
		String hql="select count(*) from Registration r where email=:email";
		return ((Number) entityManager.createQuery(hql).setParameter("email",email).getSingleResult()).intValue();
	}

	@Override
	public void saveAll(List<Registration> excelRecords) {
		for(Registration record: excelRecords){
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
		    try {
		        session.persist(record);	        
		        transaction.commit();
		    } catch (Exception e) {
		       if (transaction != null) {
		            transaction.rollback();
		        }
		        e.printStackTrace();
		    } finally {
		        session.close();
		    }
		}
	}

	@Override
	public void save(Registration registration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveuser(String firstName, String lastName, String phnum, String address, String gender,
			String password, boolean physicallyHandicaped, boolean legalIssues, LocalDate dob, Integer age,
			Integer education, String email, String profileImagePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(Integer id, String firstName, String lastName, String phnum, String address, String gender,
			String password, boolean physicallyHandicaped, boolean legalIssues, LocalDate dob, Integer age,
			Integer education, String email, String profileImagePath) {
		// TODO Auto-generated method stub
		
	}
}
