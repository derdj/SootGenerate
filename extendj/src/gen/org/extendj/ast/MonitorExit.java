/* This file was generated with JastAdd2 (http://jastadd.org) version 2.1.10-34-g8379457 */
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
 * Used in code generation to represent the implicit monitor exit
 * call at the end of a synchronized block.
 * @ast node
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:219
 * @production MonitorExit : {@link Block};

 */
public class MonitorExit extends Block implements Cloneable {
  /**
   * @aspect MonitorExit
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\MonitorExit.jrag:34
   */
  protected SynchronizedStmt monitor = null;
  /**
   * @aspect MonitorExit
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\MonitorExit.jrag:36
   */
  public MonitorExit(SynchronizedStmt sync) {
    monitor = sync;
  }
  /**
   * Generate bytecode for the monitor exit call.
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1904
   */
  public void createBCode(CodeGeneration gen) {
    gen.monitorRangeEnd(monitor.monitorId,
        hostType().constantPool().newLabel());
  }
  /**
   * Generate exception handler for monitor closing.
   * @param gen
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1913
   */
  public void emitMonitorExitHandler(CodeGeneration gen) {
    int handler_lbl = handler_label();
    int end_lbl = handler_end_label();

    gen.changeStackDepth(1);
    int num = localNum() + 1;

    // handler start
    gen.addLabel(handler_lbl);

    gen.emitStoreReference(num);

    gen.emitLoadReference(monitor.localNum());
    gen.emit(Bytecode.MONITOREXIT);

    // handler end
    gen.addLabel(end_lbl);

    gen.emitLoadReference(num);
    gen.emit(Bytecode.ATHROW);

    // add exception handler for the monitor closing
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4414101
    gen.addException(handler_lbl, end_lbl, handler_lbl,
        CodeGeneration.ExceptionEntry.CATCH_ALL);

  }
  /**
   * @declaredat ASTNode:1
   */
  public MonitorExit() {
    super();
  }
  /**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @declaredat ASTNode:10
   */
  public void init$Children() {
    children = new ASTNode[1];
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public MonitorExit(List<Stmt> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:20
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:26
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:32
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    handler_label_reset();
    handler_end_label_reset();
    end_label_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:47
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:53
   */
  public MonitorExit clone() throws CloneNotSupportedException {
    MonitorExit node = (MonitorExit) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public MonitorExit copy() {
    try {
      MonitorExit node = (MonitorExit) clone();
      node.parent = null;
      if(children != null) {
        node.children = (ASTNode[]) children.clone();
      }
      return node;
    } catch (CloneNotSupportedException e) {
      throw new Error("Error: clone not supported for " + getClass().getName());
    }
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:79
   */
  public MonitorExit fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public MonitorExit treeCopyNoTransform() {
    MonitorExit tree = (MonitorExit) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) children[i];
        if(child != null) {
          child = child.treeCopyNoTransform();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:108
   */
  public MonitorExit treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:115
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Stmt list.
   * @param list The new list node to be used as the Stmt list.
   * @apilevel high-level
   */
  public void setStmtList(List<Stmt> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the Stmt list.
   * @return Number of children in the Stmt list.
   * @apilevel high-level
   */
  public int getNumStmt() {
    return getStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Stmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Stmt list.
   * @apilevel low-level
   */
  public int getNumStmtNoTransform() {
    return getStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Stmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Stmt list.
   * @apilevel high-level
   */
  public Stmt getStmt(int i) {
    return (Stmt) getStmtList().getChild(i);
  }
  /**
   * Check whether the Stmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasStmt() {
    return getStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the Stmt list.
   * @param node The element to append to the Stmt list.
   * @apilevel high-level
   */
  public void addStmt(Stmt node) {
    List<Stmt> list = (parent == null || state == null) ? getStmtListNoTransform() : getStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addStmtNoTransform(Stmt node) {
    List<Stmt> list = getStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Stmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setStmt(Stmt node, int i) {
    List<Stmt> list = getStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Stmt list.
   * @return The node representing the Stmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Stmt")
  public List<Stmt> getStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the Stmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Stmt list.
   * @apilevel low-level
   */
  public List<Stmt> getStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(0);
  }
  /**
   * Retrieves the Stmt list.
   * @return The node representing the Stmt list.
   * @apilevel high-level
   */
  public List<Stmt> getStmts() {
    return getStmtList();
  }
  /**
   * Retrieves the Stmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Stmt list.
   * @apilevel low-level
   */
  public List<Stmt> getStmtsNoTransform() {
    return getStmtListNoTransform();
  }
  /**
   * @apilevel internal
   */
  protected boolean handler_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int handler_label_value;
  /**
   * @apilevel internal
   */
  private void handler_label_reset() {
    handler_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int handler_label() {
    if(handler_label_computed) {
      return handler_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    handler_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      handler_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return handler_label_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean handler_end_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int handler_end_label_value;
  /**
   * @apilevel internal
   */
  private void handler_end_label_reset() {
    handler_end_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int handler_end_label() {
    if(handler_end_label_computed) {
      return handler_end_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    handler_end_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      handler_end_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return handler_end_label_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean end_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int end_label_value;
  /**
   * @apilevel internal
   */
  private void end_label_reset() {
    end_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int end_label() {
    if(end_label_computed) {
      return end_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    end_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      end_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return end_label_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
