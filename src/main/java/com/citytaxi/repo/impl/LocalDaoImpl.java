package com.citytaxi.repo.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citytaxi.controller.domain.Local;
import com.citytaxi.repo.LocalDao;

@Repository
@Transactional
public class LocalDaoImpl implements LocalDao{
	
	@Autowired
	private EntityManager em;

	@Override
	public Local findById(Integer id) {
		return em.find(Local.class, id);
	}

	@Override
	public List<Local> findByTime(Integer time) {
		TypedQuery<Local> query = em.createQuery("SELECT l FROM Local l WHERE l.tempo = :tempo", Local.class);
		query.setParameter("l.tempo", time);
		List<Local> listLocais = query.getResultList();
		return listLocais;
	}

	@Override
	public Local create(Local local) {
		em.persist(local);
		em.flush();
		return local;
	}

	@Override
	public void update(Local local) {
		em.merge(local);
		em.flush();
	}
	
	@Override
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Local t");
		query.executeUpdate();
	}

}
