/******************************************************************************
 *  Copyright (c) 2011 GitHub Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *    Kevin Sawicki (GitHub Inc.) - initial API and implementation
 *****************************************************************************/
package org.eclipse.egit.ui.internal.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.internal.repository.tree.FileNode;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

/**
 * Drag assistant for {@link FileNode} selections in the repositories view
 */
public class RepositoryDragAssistant extends CommonDragAdapterAssistant {

	public Transfer[] getSupportedTransferTypes() {
		return new Transfer[] { LocalSelectionTransfer.getTransfer(),
				FileTransfer.getInstance() };
	}

	public boolean setDragData(final DragSourceEvent event,
			final IStructuredSelection selection) {
		if (selection == null || selection.isEmpty())
			return false;

		if (LocalSelectionTransfer.getTransfer()
				.isSupportedType(event.dataType)) {
			LocalSelectionTransfer.getTransfer().setSelection(selection);
			return true;
		}

		if (FileTransfer.getInstance().isSupportedType(event.dataType)) {
			final List<String> files = new ArrayList<String>();
			for (Object selected : selection.toList())
				if (selected instanceof FileNode) {
					File file = ((FileNode) selected).getObject();
					if (file != null && file.exists())
						files.add(file.getAbsolutePath());
				}
			event.data = files.toArray(new String[files.size()]);
			return !files.isEmpty();
		}

		return false;
	}

	public void dragFinished(final DragSourceEvent event,
			final IStructuredSelection selection) {
		if (LocalSelectionTransfer.getTransfer()
				.isSupportedType(event.dataType))
			LocalSelectionTransfer.getTransfer().setSelection(selection);
	}
}
