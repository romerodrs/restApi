package com.api.ws.envers;

import java.util.Date;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Created by geci on 29/06/2016.
 */
@Component
public class CustomRevisionListener implements RevisionListener {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newRevision(final Object revisionEntity) {
		final Revision revision = (Revision) revisionEntity;
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// save the user name
			revision.setUserName(auth.getName());
			Object details = auth.getDetails();
			if (details != null && details instanceof WebAuthenticationDetails) {
				// save the user IP
				revision.setRemoteIp(((WebAuthenticationDetails) details).getRemoteAddress());
				revision.setDate(new Date());
			}
		}
	}
}