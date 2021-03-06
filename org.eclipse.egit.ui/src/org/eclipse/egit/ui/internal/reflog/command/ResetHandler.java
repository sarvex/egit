/*******************************************************************************
 * Copyright (c) 2014, Konrad Kügler <swamblumat-eclipsebugs@yahoo.de> and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.egit.ui.internal.reflog.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.actions.ResetMenu;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Handler to reset (soft/mixed/hard) to a reflog entry's commit
 */
public class ResetHandler extends AbstractReflogCommandHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		String resetMode = event.getParameter(ResetMenu.RESET_MODE);
		RevCommit commit = getSelectedCommit(event, repository);
		if (commit != null)
			ResetMenu.performReset(event, repository, commit, resetMode);
		return null;
	}

}
