/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class represents an object that is contructed as a result
 * of a reply from Gadu-Gadu's public directory.
 * <p>
 * It holds the collection of entries, each entry represents a single matched
 * Gadu-Gadu user. These entries can be viewed through a public iterator.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PublicDirSearchReply.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public class PublicDirSearchReply {

	private Integer m_nextStart = null;
	
	private Collection m_publicDirSearchEntries = new ArrayList();

	/**
	 * Method that creates a new entry
	 * and adds it to entries collection.
	 * 
	 * @return <code>PublicDirSearchReply.Entry</code> object
	 */
	public PublicDirSearchReply.Entry createSearchEntry() {
		Entry entry = new Entry();
		m_publicDirSearchEntries.add(entry);
		return entry;
	}
	
	/**
	 * Returns an unmodifiable iterator over search results entries.
	 * 
	 * @return <code>Iterator</code> over search results entries.
	 */
	public Iterator listResults() {
		return new UnmodifiableIterator(m_publicDirSearchEntries.iterator());
	}
	
	/**
	 * Sets nextStart.
	 * 
	 * @param nextStart
	 */
	public void setNextStart(Integer nextStart) {
		m_nextStart = nextStart;
	}
	
	/**
	 * Returns nextStart.
	 * 
	 * @return <code>Integer</code> value that holds nextStart.
	 */
	public Integer getNextStart() {
		return m_nextStart;
	}

	public static class Entry extends PublicDirSearch {

		private IStatus m_status = null;

		private Entry() {
			//private contructor
		}
		
		public void setStatus(IStatus status) {
			m_status = status;
		}

		public IStatus getStatus() {
			return m_status;
		}

	}
	
	private static class UnmodifiableIterator implements Iterator {

		private Iterator m_iterator;
		
		private UnmodifiableIterator(Iterator it) {
			m_iterator = it;
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return m_iterator.hasNext();
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		public Object next() {
			return m_iterator.next();
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException("this is read-only iterator");
		}
		
	}
	
}