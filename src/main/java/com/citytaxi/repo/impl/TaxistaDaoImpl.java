package com.citytaxi.repo.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citytaxi.controller.domain.TaxiStatus;
import com.citytaxi.controller.domain.Taxista;
import com.citytaxi.repo.TaxistaDao;

@Repository
@Transactional
public class TaxistaDaoImpl implements TaxistaDao{
	
	@Autowired
    private EntityManager em;

	@Override
	public Taxista findById(Integer id) {
		return em.find(Taxista.class, id);
	}

	@Override
	public Taxista save(Taxista taxista) {
		em.persist(taxista);
		em.flush();
		return taxista;
	}

	@Override
	public void update(Taxista taxista) {
		em.merge(taxista);
		em.flush();
	}

	@Override
	public List<Taxista> findAllByStatus(TaxiStatus taxiStatus) {
		TypedQuery<Taxista> query = em.createQuery("SELECT t FROM Taxista t WHERE t.status = :status", Taxista.class);
		query.setParameter("status", taxiStatus);
		return query.getResultList();
	}
	
	@Override
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Taxista t");
		query.executeUpdate();
	}

}
