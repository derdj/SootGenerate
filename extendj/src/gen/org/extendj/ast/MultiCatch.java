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
 * A catch clause that can catch a multiple exception types.
 * @ast node
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\grammar\\MultiCatch.ast:14
 * @production MultiCatch : {@link CatchClause} ::= <span class="component">Parameter:{@link CatchParameterDeclaration}</span> <span class="component">{@link Block}</span>;

 */
public class MultiCatch extends CatchClause implements Cloneable {
  /**
   * @aspect PreciseRethrow
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PreciseRethrow.jrag:293
   */
  void checkUnreachableStmt() {
    if (!getBlock().reachable() && reportUnreachable()) {
      errorf("the exception %s is not thrown in the body of the try statement",
          getParameter().type().fullName());
    }
  }
  /**
   * @aspect Java7PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PrettyPrint.jadd:75
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("catch (");
    out.print(getParameter());
    out.print(") ");
    out.print(getBlock());
  }
  /**
   * The Multi-Catch clause has two or more exception table entries
   * which all point to the same exception handler.
   * @aspect MultiCatch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\MultiCatch.jrag:37
   */
  public void exceptionTableEntries(CodeGeneration gen, int begin_lbl, int end_lbl) {
    for (int i = 0; i < getParameter().getNumTypeAccess(); ++i) {
      TypeDecl type = getParameter().getTypeAccess(i).type();

      gen.addException(
        begin_lbl,
        end_lbl,
        label(),
        gen.constantPool().addClass(type.constantPoolName())
        );
    }
  }
  /**
   * Code gen.
   * @aspect MultiCatch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\MultiCatch.jrag:53
   */
  public void createBCode(CodeGeneration gen) {
    gen.addLabel(label());
    // add 1 to stack depth
    gen.changeStackDepth(1);
    getParameter().type().emitStoreLocal(gen, localNum());
    getBlock().createBCode(gen);
  }
  /**
   * @declaredat ASTNode:1
   */
  public MultiCatch() {
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
    children = new ASTNode[2];
  }
  /**
   * @declaredat ASTNode:13
   */
  public MultiCatch(CatchParameterDeclaration p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:20
   */
  protected int numChildren() {
    return 2;
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
    parameterDeclaration_String_reset();
    localNum_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:40
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:46
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:52
   */
  public MultiCatch clone() throws CloneNotSupportedException {
    MultiCatch node = (MultiCatch) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:59
   */
  public MultiCatch copy() {
    try {
      MultiCatch node = (MultiCatch) clone();
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
   * @declaredat ASTNode:78
   */
  public MultiCatch fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:87
   */
  public MultiCatch treeCopyNoTransform() {
    MultiCatch tree = (MultiCatch) copy();
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
   * @declaredat ASTNode:107
   */
  public MultiCatch treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:114
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Parameter child.
   * @param node The new node to replace the Parameter child.
   * @apilevel high-level
   */
  public void setParameter(CatchParameterDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Parameter child.
   * @return The current node used as the Parameter child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Parameter")
  public CatchParameterDeclaration getParameter() {
    return (CatchParameterDeclaration) getChild(0);
  }
  /**
   * Retrieves the Parameter child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Parameter child.
   * @apilevel low-level
   */
  public CatchParameterDeclaration getParameterNoTransform() {
    return (CatchParameterDeclaration) getChildNoTransform(0);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(1);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(1);
  }
  protected java.util.Map parameterDeclaration_String_values;
  /**
   * @apilevel internal
   */
  private void parameterDeclaration_String_reset() {
    parameterDeclaration_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet parameterDeclaration(String name) {
    Object _parameters = name;
    if (parameterDeclaration_String_values == null) parameterDeclaration_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(parameterDeclaration_String_values.containsKey(_parameters)) {
      return (SimpleSet)parameterDeclaration_String_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet parameterDeclaration_String_value = getParameter().name().equals(name) ? getParameter() : SimpleSet.emptySet;
    if (isFinal && num == state().boundariesCrossed) {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return parameterDeclaration_String_value;
  }
  /**
   * @attribute syn
   * @aspect ExceptionHandling
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:259
   */
  @ASTNodeAnnotation.Attribute
  public boolean handles(TypeDecl exceptionType) {
    ASTNode$State state = state();
    try {
        CatchParameterDeclaration param = getParameter();
        for (int i = 0; i < param.getNumTypeAccess(); ++i) {
          TypeDecl type = param.getTypeAccess(i).type();
          if (!type.isUnknown() && exceptionType.instanceOf(type)) {
            return true;
          }
        }
        return false;
      }
    finally {
    }
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\MultiCatch.jrag:65
   */
  @ASTNodeAnnotation.Attribute
  public int localNum() {
    if(localNum_computed) {
      return localNum_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    localNum_value = getParent().Define_int_localNum(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      localNum_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localNum_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean localNum_computed = false;
  /**
   * @apilevel internal
   */
  protected int localNum_value;
  /**
   * @apilevel internal
   */
  private void localNum_reset() {
    localNum_computed = false;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\MultiCatch.jrag:49
   * @apilevel internal
   */
  public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_isMethodParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\MultiCatch.jrag:50
   * @apilevel internal
   */
  public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_isConstructorParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\MultiCatch.jrag:51
   * @apilevel internal
   */
  public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\MultiCatch.jrag:102
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getParameterNoTransform()) {
      return parameterDeclaration(name);
    }
    else {
      return super.Define_SimpleSet_lookupVariable(caller, child, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\MultiCatch.jrag:152
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()){
    boolean anyReachable = false;
    CatchParameterDeclaration param = getParameter();
    for (int i = 0; i < param.getNumTypeAccess(); ++i) {
      TypeDecl type = param.getTypeAccess(i).type();
      if (!reachableCatchClause(type)) {
        errorf("The exception type %s can not be caught by this multi-catch clause",
            type.fullName());
      } else {
        anyReachable = true;
      }
    }
    return anyReachable;
  }
    else {
      return getParent().Define_boolean_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\MultiCatch.jrag:68
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if (caller == getParameterNoTransform()) {
      return localNum();
    }
    else if (caller == getBlockNoTransform()) {
      return localNum() + getParameter().type().variableSize();
    }
    else {
      return getParent().Define_int_localNum(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
