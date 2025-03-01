package com.example.devProject.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.devProject.entities.Education;
import com.example.devProject.repositories.EducationRepo;
import org.hibernate.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transaction;

import org.hibernate.Session;

@Repository
public class EduRepoImpl implements EducationRepo{
	
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int gerMaxEducation() {
		return 0;
	}

	@Override
	public int getIdByQualification(String qualification) {
		 String hql ="select e.id from Edcation e where qualifiation:=qualification";
		 return (int) entityManager.createQuery(hql).setParameter("qualification", qualification).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getEducation() {
		String hql="From Education e";
		return  entityManager.createQuery(hql).getResultList();
	}

	@Override
	public void save(Education education) {
		 Session session=sessionFactory.openSession();
		 session.persist(education);
		 session.close();
}
}