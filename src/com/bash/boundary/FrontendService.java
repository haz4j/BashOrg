package com.bash.boundary;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.bash.control.MappingService;
import com.bash.entity.Quote;
import com.bash.entity.Status;

@Named
@SessionScoped
public class FrontendService implements Serializable {

	private static final int QUOTES_BY_PAGE = 10;

	@Inject
	private MappingService mappingService;

	private Quote quote = new Quote();

	private int page = 0;

	private Long totalPage = 0L;

	private SortType sortBy = SortType.DATE;

	@Inject
	private Initial initial;

	@PostConstruct
	public void updateTotalPages() {
		// initial.init();
		totalPage = mappingService.getTotalPages() / QUOTES_BY_PAGE;
	}

	public List<Quote> getQuotes() {

		return getQuotes(page);
	}

	public List<Quote> getQuotes(int pageId) {
		return getQuotes(pageId * QUOTES_BY_PAGE, QUOTES_BY_PAGE);
	}

	public List<Quote> getQuotes(int from, int limit) {
		return mappingService.getQuotes(from, limit, sortBy);
	}

	public List<Quote> getQuotesOnApprove() {
		return mappingService.getQuotesOnApprove();
	}

	public void voteUp(long id) {
		mappingService.voteUp(id);
	}

	public void voteDown(long id) {
		mappingService.voteDown(id);
	}

	public void approve(long id) {
		mappingService.approve(id);
		updateTotalPages();
	}

	public void unapprove(long id) {
		mappingService.unapprove(id);
	}

	public void addQuote() {
		quote.setAddedDate(new Date());
		quote.setStatus(Status.UNEXAMINED);
		mappingService.addQuote(quote);
	}

	public void sortByRating() {
		sortBy = SortType.RATING;
	}

	public void sortByDate() {
		sortBy = SortType.DATE;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
}
