/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jbull.simplecore.events;

import java.util.*;

public class ListenerList implements List<Listener> {
    private final List<Listener> listenerList = new ArrayList<>();

    @Override
    public int size() {
        return listenerList.size();
    }

    @Override
    public boolean isEmpty() {
        return listenerList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return listenerList.contains(o);
    }

    @Override
    public Iterator<Listener> iterator() {
        return listenerList.iterator();
    }

    @Override
    public Object[] toArray() {
        return listenerList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return listenerList.toArray(a);
    }

    @Override
    public boolean add(Listener listener) {
        return listenerList.add(listener);
    }

    @Override
    public boolean remove(Object o) {
        return listenerList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return listenerList.contains(c);
    }

    @Override
    public boolean addAll(Collection<? extends Listener> c) {
        return listenerList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Listener> c) {
        return listenerList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return listenerList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return listenerList.retainAll(c);
    }

    @Override
    public void clear() {
        listenerList.clear();
    }

    @Override
    public Listener get(int index) {
        return listenerList.get(index);
    }

    @Override
    public Listener set(int index, Listener element) {
        return listenerList.set(index, element);
    }

    @Override
    public void add(int index, Listener element) {
        listenerList.add(index, element);
    }

    @Override
    public Listener remove(int index) {
        return listenerList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return listenerList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return listenerList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Listener> listIterator() {
        return listenerList.listIterator();
    }

    @Override
    public ListIterator<Listener> listIterator(int index) {
        return listenerList.listIterator(index);
    }

    @Override
    public List<Listener> subList(int fromIndex, int toIndex) {
        return listenerList.subList(fromIndex, toIndex);
    }
}
