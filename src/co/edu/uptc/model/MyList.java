package co.edu.uptc.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

public class MyList<T> implements List<T> {
	private Node<T> head;
	private Node<T> last;

	@Override
	public int size() {
		// El metodo no necesita cambios al implementar la lista doblemente enlazada
		Node<T> aux = head;
		int count = 0;
		while (aux != null) {
			count++;
			aux = aux.getNext();
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	// The method is same in the simple list and in the double list.
	public boolean contains(Object o) {
		Node<T> aux = head;
		while (aux != null) {
			if (aux.getData().equals(o)) {
				return true;
			}
			aux = aux.getNext();
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		// No necesita cambio
		return new Iterator<T>() {
			private Node<T> auxnode = head;

			@Override
			public boolean hasNext() {
				return auxnode != null;
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				T data = auxnode.getData();
				auxnode = auxnode.getNext();
				return data;
			}
		};
	}

	@Override
	public Object[] toArray() {
		// No requiere cambio
		Object[] array = new Object[size()];
		Node<T> aux = head;
		for (int i = 0; i < size(); i++) {
			array[i] = aux.getData();
			aux = aux.getNext();
		}
		return array;
	}

	@Override
	public <E> E[] toArray(E[] a) {
		if (a.length < this.size()) {
			return (E[]) toArray();
		} else {
			Node<T> aux = head;
			for (int i = 0; i < size(); i++) {
				a[i] = (E) aux.getData();
				aux = aux.getNext();
			}
			return a;
		}
	}

	@Override
	public boolean add(T e) {
		Node<T> newNode = new Node<>(e);
		if (head == null) {
			head = newNode;
			last = newNode;
		} else {
			last.setNext(newNode);
			newNode.setPrevious(last);
			last = newNode;
		}
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Node<T> temp = head;
		while (temp != null) {
			if (temp.getData().equals(o)) {
				if (temp.equals(head)) {
					head = temp.getNext();
				} else if (temp.equals(last)) {
					last = temp.getPrevious();
					if (last != null) {
						last.setNext(null);
					}
				} else {
					temp.getPrevious().setNext(temp.getNext());
					temp.getNext().setPrevious(temp.getPrevious());
				}
				return true;
			}
			temp = temp.getNext();
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object object : c) {
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// No necesita modificaciones
		boolean add = false;
		for (T t : c) {
			if (add(t)) {
				add = true;
			}
		}
		return add;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		Node<T> aux = head;
		int i = 0;
		while (aux != null && i < index + c.size()) {
			if (i == index) {
				for (T element : c) {
					this.add(i, element);
					i++;
				}
			}
			i++;
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean temp = false;
		for (Object o : c) {
			while (remove(o)) {
				temp = true;
			}
		}
		return temp;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Node<T> aux = head;
		Node<T> previous = null;
		boolean modified = false;
		while (aux != null) {
			if (!c.contains(aux.getData())) {
				if (aux.equals(head)) {
					head = aux.getNext();
					head.setPrevious(previous);
				} else {
					previous.setNext(aux.getNext());
					modified = true;
				}
			} else {
				previous = aux;
			}
			aux = aux.getNext();
			if (aux != null) {
				aux.setPrevious(previous);
			}
		}
		return modified;
	}

	@Override
	public void clear() {
		head = null;
		last = null;
	}

	@Override
	public T get(int index) {
		Node<T> aux = head;
		T data = null;
		for (int i = 0; i < size(); i++) {
			if (i == index) {
				data = aux.getData();
			} else {
				aux = aux.getNext();
			}
		}
		return data;
	}

	@Override
	public T set(int index, T element) {
		Node<T> nodeSet = new Node<T>(element);
		Node<T> auxNode = head;
		Node<T> deleted = null;
		int count = 0;
		while (auxNode != null && count < index - 1) {
			auxNode = auxNode.getNext();
			count++;
		}
		if (index > 0) {
			deleted = auxNode.getNext();
			nodeSet.setNext(auxNode.getNext().getNext());
			nodeSet.setPrevious(auxNode);
			auxNode.setNext(nodeSet);
		} else {
			deleted = auxNode;
			nodeSet.setNext(auxNode.getNext());
			this.head = nodeSet;
		}
		return deleted.getData();
	}

	@Override
	public void add(int index, T element) {
		if (index >= size()) {
			add(element);
		} else if (index == 0) {
			Node<T> temp = new Node<T>(element);
			temp.setNext(head);
			head = temp;
		} else {
			Node<T> aux = head;
			for (int i = 0; i < index - 1; i++) {
				aux = aux.getNext();
			}
			Node<T> temp = new Node<T>(element);
			temp.setNext(aux.getNext());
			aux.setNext(temp);
		}
	}

	@Override
	public T remove(int index) {
		Node<T> current = head;
		Node<T> previous = null;
		T recovered = null;
		if (index < 0 || index >= size()) {
			return null;
		}
		if (index == 0) {
			recovered = head.getData();
			head = head.getNext();
			if (head != null) {
				head.setPrevious(null);
			} else {
				last = null;
			}
			return recovered;
		}
		if (index == size() - 1) {
			while (current != null) {
				if (current.getNext() == null) {
					recovered = current.getData();
					previous.setNext(null);
					last = previous;
					return recovered;
				} else {
					previous = current;
					current = current.getNext();
				}
			}
		}
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		recovered = current.getData();
		previous = current.getPrevious();
		Node<T> next = current.getNext();
		if (previous != null) {
			previous.setNext(next);
		}
		if (next != null) {
			next.setPrevious(previous);
		}
		return recovered;
	}

	@Override
	public int indexOf(Object o) {
		// Modificado por que no encontré el que servia para la lista doble...
		Node<T> aux = head;
		int index = -1;
		if (aux != null) {
			index = 0;
		}
		while (aux != null) {
			if (o.equals(aux.getData())) {
				return index;
			}
			aux = aux.getNext();
			index++;
		}
		return index;
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int i = size() - 1; i > 0; i--) {
			if (get(i) == o) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		return new ListIterator<T>() {
			private Node<T> currentNode = head;
			private Node<T> previousNode = null;
			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentNode != null;
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				previousNode = currentNode;
				currentNode = currentNode.getNext();
				currentIndex++;
				return previousNode.getData();
			}

			@Override
			public boolean hasPrevious() {
				return previousNode != null;
			}

			@Override
			public T previous() {
				if (!hasPrevious()) {
					throw new NoSuchElementException();
				}
				currentNode = previousNode;
				previousNode = previousNode.getPrevious();
				currentIndex--;
				return currentNode.getData();
			}

			@Override
			public int nextIndex() {
				return currentIndex;
			}

			@Override
			public int previousIndex() {
				return currentIndex - 1;
			}

			@Override
			public void remove() {
				if (previousNode == null) {
					throw new IllegalStateException();
				}
				MyList.this.remove(previousNode.getData());
				previousNode = null;
				currentIndex--; // Se reduce el indice porque se elimino un elemento
			}

			@Override
			public void set(T e) {
				if (previousNode == null) {
					throw new IllegalStateException();
				}
				previousNode.setData(e);
			}

			@Override
			public void add(T e) {
				MyList.this.add(currentIndex, e);
				previousNode = null;
				currentIndex++;
			}
		};
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new ListIterator<T>() {
			private int currentIndex = index;
			private Node<T> currentNode = this.search(index);
			private Node<T> previousNode = currentNode.getPrevious();

			private Node<T> search(int index) {
				Node<T> aux = head;
				Node<T> data = null;
				for (int i = 0; i < size(); i++) {
					if (i == index) {
						data = aux;
						return data;
					} else {
						aux = aux.getNext();
					}
				}
				return data;
			}

			@Override
			public boolean hasNext() {
				return currentNode != null;
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				previousNode = currentNode;
				currentNode = currentNode.getNext();
				currentIndex++;
				return previousNode.getData();
			}

			@Override
			public boolean hasPrevious() {
				return previousNode != null;
			}

			@Override
			public T previous() {
				if (!hasPrevious()) {
					throw new NoSuchElementException();
				}
				currentNode = previousNode;
				previousNode = previousNode.getPrevious();
				currentIndex--;
				return currentNode.getData();
			}

			@Override
			public int nextIndex() {
				return currentIndex;
			}

			@Override
			public int previousIndex() {
				return currentIndex - 1;
			}

			@Override
			public void remove() {
				if (previousNode == null) {
					throw new IllegalStateException();
				}
				MyList.this.remove(previousNode.getData());
				previousNode = null;
				currentIndex--; // Se reduce el indice porque se elimino un elemento
			}

			@Override
			public void set(T e) {
				if (previousNode == null) {
					throw new IllegalStateException();
				}
				previousNode.setData(e);
			}

			@Override
			public void add(T e) {
				MyList.this.add(currentIndex, e);
				previousNode = null;
				currentIndex++;
			}
		};
	}

	public void replaceAll2(UnaryOperator<T> operator) {
		Node<T> aux = head;
		while (aux != null) {
			T newValue = operator.apply(aux.getData());
			aux.setData(newValue);
			aux = aux.getNext();
		}
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException("Índice fuera de rango");
		}
		MyList<T> subList = new MyList<>();
		Node<T> current = head;
		for (int i = 0; i < fromIndex; i++) {
			if (current == null) {
				throw new IndexOutOfBoundsException("Índice fuera de rango");
			}
			current = current.getNext();
		}
		for (int i = fromIndex; i < toIndex; i++) {
			if (current == null) {
				break;
			}
			subList.add(current.getData());
			current = current.getNext();
		}
		return subList;
	}

	public Iterator<T> descendingIterator() {
		return new Iterator<T>() {
			private Node<T> aux = last;

			@Override
			public boolean hasNext() {
				return aux != null;
			}

			@Override
			public T next() {
				T value = aux.getData();
				aux = aux.getPrevious();
				return value;
			}
		};
	}
}