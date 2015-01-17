package com.bash.control;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import com.bash.boundary.SortType;
import com.bash.entity.Quote;
import com.bash.entity.Status;

@Stateless
public class MappingService {

	@PersistenceContext(unitName = "myPU")
	EntityManager em;

	@Inject
	private Logger logger;
	
	public Quote getQuote(Long id) {
		Quote quote = em.find(Quote.class, id);
		return quote;
	}

	public Long addQuote(Quote quote) {
		em.persist(quote);
		return quote.getItemId();
	}

	public Quote updateQuote(Quote quote) {
		em.merge(quote);
		return quote;
	}

	public void deleteQuote(Long id) {
		Quote quote = em.find(Quote.class, id);
		em.remove(quote);
	}

	public void voteUp(Long id) {
		Quote quote = em.find(Quote.class, id);
		quote.setRating(quote.getRating() + 1);
		em.merge(quote);
	}

	public void voteDown(Long id) {
		Quote quote = em.find(Quote.class, id);
		quote.setRating(quote.getRating() - 1);
		em.merge(quote);
	}

	public List<Quote> getQuotes(int start, int limit, SortType sortBy) {
		if (sortBy == SortType.DATE) {
			return getQuotes(start, limit);
		} else return getQuotesByRating(start, limit);
	}
	
	public List<Quote> getQuotes(int start, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Quote> cq = cb.createQuery(Quote.class);
		Root<Quote> rootEntry = cq.from(Quote.class);
		cq.where(cb.equal(rootEntry.get("status"), Status.APPROVED));
		TypedQuery<Quote> allQuery = em.createQuery(cq).setFirstResult(start)
				.setMaxResults(limit);
		return allQuery.getResultList();
	}

	public List<Quote> getQuotesByRating(int start, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Quote> cq = cb.createQuery(Quote.class);
		Root<Quote> rootEntry = cq.from(Quote.class);
		cq.where(cb.equal(rootEntry.get("status"), Status.APPROVED));
		cq.orderBy(cb.desc(rootEntry.get("rating")));
		TypedQuery<Quote> allQuery = em.createQuery(cq).setFirstResult(start)
				.setMaxResults(limit);
		return allQuery.getResultList();
	}
	
	public List<Quote> getQuotesOnApprove() {
		return getQuotesOnApprove(0, 50);
	}

	public List<Quote> getQuotesOnApprove(int start, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Quote> cq = cb.createQuery(Quote.class);
		Root<Quote> rootEntry = cq.from(Quote.class);
		cq.where(cb.equal(rootEntry.get("status"), Status.UNEXAMINED));
		TypedQuery<Quote> allQuery = em.createQuery(cq).setFirstResult(start)
				.setMaxResults(limit);
		return allQuery.getResultList();
	}

	//@RolesAllowed("Admin")
	public Quote approve(Long id) {
		Quote quote = em.find(Quote.class, id);
		quote.setStatus(Status.APPROVED);
		em.merge(quote);
		return quote;
	}

	//@RolesAllowed("Admin")
	public Quote unapprove(Long id) {
		Quote quote = em.find(Quote.class, id);
		quote.setStatus(Status.UNAPPROVED);
		em.merge(quote);
		return quote;
	}

	@AroundInvoke
	private Object logMethod(InvocationContext ic) throws Exception {
		logger.entering(ic.getTarget().toString(), ic.getMethod().getName());
		try {
			return ic.proceed();
		} finally {
			logger.exiting(ic.getTarget().toString(), ic.getMethod().getName());
		}
	}

	public Long getTotalPages() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Quote> root = cq.from(Quote.class);
		EntityType <Quote> Quote_ = root.getModel();
		cq.select((cb.countDistinct(root)));
		cq.where( cb.equal( root.get(Quote_.getSingularAttribute("status")), Status.APPROVED) );
		return em.createQuery(cq).getSingleResult();
	}
	
}
