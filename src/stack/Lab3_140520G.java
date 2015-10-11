/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 *
 * @author Student
 */
public class Lab3_140520G  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CS2022LinkedStack<Object> stack = new CS2022LinkedStack<>();
        File input = new File(args[0]); 											//array to hold the numbers
		ArrayList<Object> arguments = new ArrayList<>();
		try{
                    Scanner inputFile = new Scanner(input);					//get the input
                    while(inputFile.hasNextLine()){
                        arguments.add(inputFile.next().split(" "));
                    
                    }
                    for (int i = 0 ;i < arguments.size() ; i++){
                        String[] funcArg = (String[]) arguments.get(i);
                        if (funcArg[0] == "push"){
                            stack.push(funcArg[1]);
                        }
                        else if (funcArg[0] == "pop"){
                            System.out.println(stack.pop());
                        }
                        else if (funcArg[0] == "calculate"){
                            stack.push(funcArg[1]);
                        }
                    }
		}
		catch (FileNotFoundException e) {
                    System.out.println("No Such File");						//exception for no input file
		}
    }
    
}

class CS2022PostfixCalculator{
    private String input;
    private CS2022LinkedStack<Float> stack = new CS2022LinkedStack<Float>();
    
    public float calculate(String s){
        
        String[] elements = s.split(" ");
        System.out.println(Arrays.toString(elements));
        for (int i = 0 ; i < elements.length ; i++){
            boolean isInt = isInteger(elements[i]);
            if (isInt){
                stack.push(Float.parseFloat(elements[i]));
            }
            else{
                float no2 = stack.pop();
                float no1 = stack.pop();
                float newNumber = 0;
                if ("+".equals(elements[i])){
                    newNumber = no1 + no2;
                }
                else if ("-".equals(elements[i])){
                    newNumber = no1 - no2;
                }
                else if ("*".equals(elements[i])){
                    newNumber = no1 * no2;
                }
                else if ("/".equals(elements[i])){
                    newNumber = no1 / no2;
                }
                stack.push(newNumber);
                System.out.println(newNumber+" = "+no1+ elements[i]+no2);
                
                
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
        if (index != 1){
            prevNode.setNext(currentNode.getNext());
            decrementLength();
        }
        else{
            if(1 < getLength()){
                setHead(currentNode.getNext());
                decrementLength();
            }
            else{
                setHead(new Node<E>(null));
                decrementLength();
            }
        }
        
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

