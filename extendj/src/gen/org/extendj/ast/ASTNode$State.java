package org.extendj.ast;

import java.util.HashSet;
import java.io.File;
import java.util.Set;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import beaver.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jastadd.util.*;
import java.util.zip.*;
import java.io.*;
import org.jastadd.util.PrettyPrintable;
import org.jastadd.util.PrettyPrinter;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
/**
 * @ast class
 * @declaredat ASTNode$State:4
 */
public class ASTNode$State extends java.lang.Object {
  

  /**
   * @apilevel internal
   */
  public boolean INTERMEDIATE_VALUE = false;

  

  /**
   * @apilevel internal
   */
  public boolean IN_CIRCLE = false;

  

  /**
   * @apilevel internal
   */
  public int CIRCLE_INDEX = 1;

  

  /**
   * @apilevel internal
   */
  public boolean CHANGE = false;

  

  /**
   * @apilevel internal
   */
  public boolean RESET_CYCLE = false;

  

  /**
   * @apilevel internal
   */
  static public class CircularValue {
    Object value;
    int visited = -1;
  }

  
  /**
   * @apilevel internal
   */
  public static final int REWRITE_CHANGE = 1;

  

  /**
   * @apilevel internal
   */
  public static final int REWRITE_NOCHANGE = 2;

  

  /**
   * @apilevel internal
   */
  public static final int REWRITE_INTERRUPT = 3;

  

  public int boundariesCrossed = 0;

  

  // state code
  private int[] stack;

  

  private int pos;

  

  public ASTNode$State() {
    stack = new int[64];
    pos = 0;
  }

  

  private void ensureSize(int size) {
    if(size < stack.length)
      return;
    int[] newStack = new int[stack.length * 2];
    System.arraycopy(stack, 0, newStack, 0, stack.length);
    stack = newStack;
  }

  

  public void push(int i) {
    ensureSize(pos+1);
    stack[pos++] = i;
  }

  

  public int pop() {
    return stack[--pos];
  }

  

  public int peek() {
    return stack[pos-1];
  }

  protected int duringBoundNames = 0;

  protected int duringNameResolution = 0;

  protected int duringVariableDeclarationTransformation = 0;

  protected int duringJava7Literals = 0;

  protected int duringDU = 0;

  protected int duringAnnotations = 0;

  protected int duringEnums = 0;

  protected int duringGenericTypeVariables = 0;

  protected int duringMethodReference = 0;

  public void reset() {
    IN_CIRCLE = false;
    CIRCLE_INDEX = 1;
    CHANGE = false;
    boundariesCrossed = 0;
    if(duringBoundNames != 0) {
      System.out.println("Warning: resetting duringBoundNames");
      duringBoundNames = 0;
    }
    if(duringNameResolution != 0) {
      System.out.println("Warning: resetting duringNameResolution");
      duringNameResolution = 0;
    }
    if(duringVariableDeclarationTransformation != 0) {
      System.out.println("Warning: resetting duringVariableDeclarationTransformation");
      duringVariableDeclarationTransformation = 0;
    }
    if(duringJava7Literals != 0) {
      System.out.println("Warning: resetting duringJava7Literals");
      duringJava7Literals = 0;
    }
    if(duringDU != 0) {
      System.out.println("Warning: resetting duringDU");
      duringDU = 0;
    }
    if(duringAnnotations != 0) {
      System.out.println("Warning: resetting duringAnnotations");
      duringAnnotations = 0;
    }
    if(duringEnums != 0) {
      System.out.println("Warning: resetting duringEnums");
      duringEnums = 0;
    }
    if(duringGenericTypeVariables != 0) {
      System.out.println("Warning: resetting duringGenericTypeVariables");
      duringGenericTypeVariables = 0;
    }
    if(duringMethodReference != 0) {
      System.out.println("Warning: resetting duringMethodReference");
      duringMethodReference = 0;
    }

  }


}
