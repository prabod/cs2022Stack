/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Student
 */
public class Lab3_140520G  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CS2022LinkedStack<Integer> stack = new CS2022LinkedStack<Integer>();
        stack.push(1);
        stack.push(3);
        System.out.println(stack.pop());
        stack.push(2);
        System.out.println(stack.pop());
        CS2022PostfixCalculator cal = new CS2022PostfixCalculator();
        System.out.println(cal.calculate("1 2 + 3 * 6 + 2 3 + /"));
    }
    
}

class CS2022PostfixCalculator{
    private String input;
    private CS2022LinkedStack<Float> stack = new CS2022LinkedStack<Float>();
    public float calculate(String s){
        
        String[] elements = s.split(" ");
        
        for (int i = 0 ; i < elements.length ; i++){
            if (isInteger(elements[i])){
                stack.push(Float.parseFloat(elements[i]));
            }
            else{
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("JavaScript");
                float no1 = stack.pop();
                float no2 = stack.pop();
                double newNumber = 0.0;
                try {
                    newNumber =  (double) engine.eval(no1 + elements[i] + no2);
                    System.out.println(newNumber);
                } catch (ScriptException ex) {
                    Logger.getLogger(CS2022PostfixCalculator.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Invalid operator");
                }
                
                stack.push((float)newNumber);
                
            }
        }
        return stack.pop();
    }
    
    public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException | NullPointerException e) { 
        return false; 
    }
    return true;
}
}

class CS2022LinkedStack<E> {
    private CS2022LinkedList<E> stack;
    //Add any additional things you need here.
    
    public CS2022LinkedStack(){
        init_stack();
    }
    
    private void init_stack(){
        stack = new CS2022LinkedList<E>();
    }
    
    public boolean is_empty(){
        return stack.is_empty();
    }
    
    public boolean push(E element){
        return stack.insert(element);
    }
    
    public E pop(){
        return stack.deleteNodeAt(stack.getLength());
    }
}


class CS2022LinkedList<E> {

    private Node<E> head;
    private int length;

    CS2022LinkedList() {
        init_list();
    }

    private void init_list() {
        length = 0;                     //initial length set
        setHead(new Node<E>(null));
    }

    /**
     * This function will check and see if the list is empty. It will return
     * true if the list is empty and false otherwise.
     *
     * @return
     */
    public boolean is_empty() {
        return length == 0;
    }

    /**
     *This function will return the first node object which contains the search 
     * item if it is present in the list. If the item is not present 
     * it will return NULL.
     * @param element
     * @return
     */
    public Node<E> search(E element) {
        Node<E> headNode = getHead();
        Node<E> currentNode = headNode;
        E currentNodeElement = currentNode.getElement();

        while (currentNodeElement != element) {
            currentNode = currentNode.getNext();
            if (currentNode != null) {
                currentNodeElement = currentNode.getElement();
            } else {
                break;
            }
        }

        return currentNode != null ? currentNode : null;
    }

    /**
     *This function will remove the first instance of the item from the list 
     * if the item is present in the list at least once, and return a the  
     * item that is removed from the list. If the item is not present 
     * in the list, it should return NULL.
     * @param element
     * @return
     */
    public E delete(E element) {
        Node<E> prevNode = getHead();
        Node<E> currentNode = prevNode;
        E currentNodeElement = currentNode.getElement();

        while (currentNodeElement != element) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
            if (currentNode != null) {
                currentNodeElement = currentNode.getElement();
            } else {
                break;
            }
        }
        boolean deleted;
        if (currentNodeElement == element) {
            prevNode.setNext(currentNode.getNext());
            deleted = true;
        } else {
            deleted = false;
        }
        decrementLength();
        return deleted ? element : null;
    }

    /**
     *This function will remove the node in the ith location of the list and 
     * return the item that is removed from the list. If the specified position
     * is higher than the actual amount of items in the list, it should
     * return NULL. The location indices start from 1
     * @param i
     * @return
     */
    public E deleteNodeAt(int i) {
        Node<E> prevNode = getHead();
        Node<E> currentNode = prevNode;

        if (i > getLength()) {
            return null;
        }
        int index = 1;
        while(index != i){
            prevNode = currentNode;
            currentNode = currentNode.getNext();
            index++;
        }
        prevNode.setNext(currentNode.getNext());
        decrementLength();

        return currentNode.getElement();
    }

    /**
     *This function will place element at the end of the list and return whether
     * insert was successful or not. If the insert is successful, it will return
     * true, else false.
     * @param element
     * @return
     */
    public boolean insert(E element) {
        Node<E> tempNode = new Node<>(element);
        Node<E> currentNode = getHead();

        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
        }
        if (currentNode.getElement() != null) {
            currentNode.setNext(tempNode);
        } else {
            setHead(tempNode);
        }
        incrementLength();
        return true;
    }

    /**
     *This function will place element in the ith location of the list and 
     * return whether insert was successful or not. If the insert is successful,
     * it will return true, else false. If the specified location i is higher 
     * than actual number of elements in the list, the item will be added to the
     * end of the list.
     * @param element
     * @param i
     * @return
     */
    public boolean insertAt(E element, int i) {
        Node<E> tempNode = new Node<>(element);
        Node<E> currentNode = getHead();

        if (i > getLength() + 1) {
            return false;
        }
        int index = 1;

        while (index != i - 1 && index < i) {
            currentNode = currentNode.getNext();
            index++;
        }

        if (i == 1) {
            tempNode.setNext(getHead());
            setHead(tempNode);
        } else {
            if (currentNode.getNext() != null) {
                tempNode.setNext(currentNode.getNext());
            }
            currentNode.setNext(tempNode);
        }
        incrementLength();
        return true;
    }

    /**
     *This function set the Head of the list
     * @param head
     */
    public void setHead(Node<E> head) {
        this.head = head;
    }

    /**
     *This function return the head of the node
     * @return
     */
    public Node<E> getHead() {
        return head;
    }

    /**
     *This Function increment the count of elements
     */
    public void incrementLength() {
        this.length++;
    }

    /**
     *This Function decrement the count of elements
     */
    public void decrementLength() {
        this.length--;
    }

    /**
     *This Function return the count of elements
     * @return
     */
    public int getLength() {
        return length;
    }

}

class Node <E>{
	
	private E  element;
	private Node<E> next;
	
        public Node(E element){
            setElement(element);
            setNext(null);
        }
        
        public Node(E element,Node<E> next){
            setElement(element);
            setNext(next);
        }
        
        
	public void setElement(E element) {
		this.element = element;
	}
	public E getElement() {
		return element;
	}
	public void setNext(Node<E> next) {
		this.next = next;
	}
	public Node<E> getNext() {
		return next;
	}
}

