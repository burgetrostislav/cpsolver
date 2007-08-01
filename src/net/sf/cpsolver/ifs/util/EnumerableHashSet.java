package net.sf.cpsolver.ifs.util;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

/** 
 * An extension of {@link HashSet} that implements {@link EnumerableCollection} interface.
 *
 * @version
 * IFS 1.1 (Iterative Forward Search)<br>
 * Copyright (C) 2006 Tomas Muller<br>
 * <a href="mailto:muller@unitime.org">muller@unitime.org</a><br>
 * Lazenska 391, 76314 Zlin, Czech Republic<br>
 * <br>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <br><br>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <br><br>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class EnumerableHashSet extends HashSet implements EnumerableCollection {
    /** Iterate the set */
    public Enumeration elements() {
        return new Enumeration() {
            Iterator i=iterator();
            public boolean hasMoreElements() {
                return i.hasNext();
            }
            public Object nextElement() {
                return i.next();
            }
        };
        
    }
    /** Add an element into the set */
    public void addElement(Object o) {
        add(o);
    }
    /** Remove an element from the set */
    public boolean removeElement(Object o) {
        return remove(o);
    }
    /** First element in the set (first using {@link HashSet#iterator()}) */
    public Object firstElement() {
        return (isEmpty()?null:elements().nextElement());
    }
}