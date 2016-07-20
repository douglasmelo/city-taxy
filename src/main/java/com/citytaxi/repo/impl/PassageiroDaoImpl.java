package com.citytaxi.repo.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citytaxi.controller.domain.Passageiro;
import com.citytaxi.repo.PassageiroDao;

@Repository
@Transactional
public class PassageiroDaoImpl implements PassageiroDao {
	
	@Autowired
	private EntityManager em;

	@Override
	public Passageiro findById(Integer id) {
		return em.find(Passageiro.class, id);
	}

	@Override
	public Passageiro create(Passageiro passageiro) {
		em.persist(passageiro);
		em.flush();
		return passageiro;
	}

	@Override
	public void update(Passageiro passageiro) {
		em.merge(passageiro);
		em.flush();
	}
	
	@Override
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Passageiro t");
		query.executeUpdate();
	}

}
