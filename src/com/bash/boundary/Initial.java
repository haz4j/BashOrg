package com.bash.boundary;

import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.bash.control.MappingService;
import com.bash.entity.Approver;
import com.bash.entity.Quote;
import com.bash.entity.Status;

@ApplicationScoped
public class Initial {

	@Inject
	private MappingService mappingService;

	public void init() {
		
		Quote quote;
		Approver approver;
		Random random = new Random();
		

		for (int i = 0; i < 500; i++) {
			quote = new Quote();
			approver = new Approver();
			approver.getItems().add(quote);
			quote.setApprover(approver);
			quote.setAddedDate(new Date());
			quote.setRating(random.nextLong());
			quote.setText("text #" + i);
			
			int status = i % 3;
			
			switch (status) {
			case 0:
				quote.setStatus(Status.APPROVED);
				break;
				
			case 1:
				quote.setStatus(Status.UNAPPROVED);
				break;

			default:
				quote.setStatus(Status.UNEXAMINED);
				break;
			}
			
			mappingService.addQuote(quote);
			
		}

	}

}
