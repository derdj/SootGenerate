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
 * @ast node
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\grammar\\BasicTWR.ast:1
 * @production BasicTWR : {@link Stmt} ::= <span class="component">Resource:{@link ResourceDeclaration}</span> <span class="component">{@link Block}</span>;

 */
public class BasicTWR extends Stmt implements Cloneable, VariableScope {
  /**
   * The general structure of the basic try-with-resources:
   * 
   * <pre><code>
   * RESOURCE
   * BLOCK
   * 
   * Primary Exception Handler
   * Automatic Closing of Resource
   * Suppressed Exception Handler
   * re-throw primary exception
   * Automatic Closing of Resource
   * </pre></code>
   * 
   * Pseudocode for basic try-with-resources:
   * 
   * <pre><code>
   * 0  .resourceBegin
   * 1  emit RESOURCE
   * 0  store resource
   * 0  .resourceEnd
   * 
   * 0  .blockBegin
   * 0  emit BLOCK
   * 0  .blockEnd
   * 0  goto outerFinally
   * 
   * 1  .resourceException
   * 1  throw
   * 
   * #if BLOCK is not empty:
   * 
   * 1  .catchPrimary
   * 0  store primary
   * 
   * 0  .tryCloseBegin
   * 1  load resource
   * 0  ifnull innerFinally
   * 1  load resource
   * 0  invoke java.lang.AutoCloseable.close()
   * 0  .tryCloseEnd
   * 
   * 0  goto innerFinally
   * 
   * 1  .catchSuppressed
   * 0  store suppressed
   * 1  load primary
   * 2  load suppressed
   * 0  invoke java.lang.Throwable.addSuppressed(Throwable)
   * 
   * 0  .innerFinally
   * 1  load primary
   * 1  throw
   * 
   * #endif BLOCK is not empty
   * 
   * 0  .outerFinally
   * 1  load resource
   * 0  ifnull tryEnd
   * 1  load resource
   * 0  invoke java.lang.AutoCloseable.close()
   * 
   * 0  .tryEnd
   * 
   * Exception Table:
   * resourceBegin .. resourceEnd : resourceException
   * blockBegin .. blockEnd : catchPrimary
   * tryCloseBegin .. tryCloseEnd : catchSuppressed
   * </pre></code>
   * 
   * @aspect TryWithResources
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\TryWithResources.jrag:157
   */
  public void createBCode(CodeGeneration gen) {
    ResourceDeclaration resource = getResource();

    int resourceBeginLbl = gen.constantPool().newLabel();
    int resourceEndLbl = gen.constantPool().newLabel();
    int blockBeginLbl = gen.constantPool().newLabel();
    int blockEndLbl = gen.constantPool().newLabel();
    int tryCloseBeginLbl = gen.constantPool().newLabel();
    int tryCloseEndLbl = gen.constantPool().newLabel();

    int resourceExceptionLbl = gen.constantPool().newLabel();
    int catchPrimaryLbl = gen.constantPool().newLabel();
    int catchSuppressedLbl = gen.constantPool().newLabel();
    int innerFinallyLbl = gen.constantPool().newLabel();
    int outerFinallyLbl = gen.constantPool().newLabel();
    int tryEndLbl = gen.constantPool().newLabel();

    TypeDecl throwableType = lookupType("java.lang", "Throwable");
    TypeDecl resourceType = resource.type();
    TypeDecl autoCloseableType = lookupType("java.lang", "AutoCloseable");

    gen.changeStackDepth(3);
    int resourceIndex = resource.localNum();
    int primaryIndex = resourceIndex+resourceType.variableSize();
    int suppressedIndex = primaryIndex+throwableType.variableSize();

    // store the resource in local
    gen.addLabel(resourceBeginLbl);
    resource.createBCode(gen);
    gen.addLabel(resourceEndLbl);
    gen.emit(Bytecode.NOP);

    gen.addLabel(blockBeginLbl);
    getBlock().createBCode(gen);
    gen.addLabel(blockEndLbl);
    gen.emitGoto(outerFinallyLbl);

    // If there was an exception when initializing the resource
    // we need to directly rethrow the exception
    gen.addLabel(resourceExceptionLbl);
    gen.emitThrow();
    gen.addException(
      resourceBeginLbl,
      resourceEndLbl,
      resourceExceptionLbl,
      CodeGeneration.ExceptionEntry.CATCH_ALL);

    if (gen.addressOf(blockBeginLbl) != gen.addressOf(blockEndLbl)) {

      // catch primary exception
      // operand stack: .., #primary
      gen.addLabel(catchPrimaryLbl);
      throwableType.emitStoreLocal(gen, primaryIndex);

      // try-close resource
      gen.addLabel(tryCloseBeginLbl);
      {
        // if resource != null
        resourceType.emitLoadLocal(gen, resourceIndex);
        gen.emitCompare(Bytecode.IFNULL, innerFinallyLbl);
        resourceType.emitLoadLocal(gen, resourceIndex);
        closeMethod().emitInvokeMethod(gen, autoCloseableType);
      }
      gen.addLabel(tryCloseEndLbl);
      gen.emitGoto(innerFinallyLbl);

      // catch suppressed exception
      // operand stack: .., #primary, #suppressed
      gen.addLabel(catchSuppressedLbl);
      throwableType.emitStoreLocal(gen, suppressedIndex);
      throwableType.emitLoadLocal(gen, primaryIndex);
      throwableType.emitLoadLocal(gen, suppressedIndex);
      addSuppressedMethod().emitInvokeMethod(gen, throwableType);

      // inner finally
      // operand stack: .., #primary
      gen.addLabel(innerFinallyLbl);
      throwableType.emitLoadLocal(gen, primaryIndex);
      gen.emitThrow();

      // If there was an exception during the block of the try
      // statement, then we should try to close the resource
      gen.addException(
        blockBeginLbl,
        blockEndLbl,
        catchPrimaryLbl,
        CodeGeneration.ExceptionEntry.CATCH_ALL);

      // If an exception occurrs during the automatic closing
      // of a resource after an exception in the try block...
      gen.addException(
        tryCloseBeginLbl,
        tryCloseEndLbl,
        catchSuppressedLbl,
        CodeGeneration.ExceptionEntry.CATCH_ALL);
    }

    // outer finally
    gen.addLabel(outerFinallyLbl);
    {
      // if resource != null
      resourceType.emitLoadLocal(gen, resourceIndex);
      gen.emitCompare(Bytecode.IFNULL, tryEndLbl);
      resourceType.emitLoadLocal(gen, resourceIndex);
      closeMethod().emitInvokeMethod(gen, autoCloseableType);
    }

    gen.addLabel(tryEndLbl);
    gen.emit(Bytecode.NOP);
  }
  /**
   * Lookup the java.lang.Throwable.close() method.
   * @aspect TryWithResources
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\TryWithResources.jrag:271
   */
  private MethodDecl closeMethod() {
    TypeDecl autoCloseableType = lookupType("java.lang", "AutoCloseable");
    if (autoCloseableType.isUnknown()) {
      throw new Error("Could not find java.lang.AutoCloseable");
    }
    for (MethodDecl method : (Collection<MethodDecl>)
        autoCloseableType.memberMethods("close")) {
      if (method.getNumParameter() == 0) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.AutoCloseable.close()");
  }
  /**
   * Lookup the java.lang.Throwable.addSuppressed(Throwable) method.
   * @aspect TryWithResources
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\TryWithResources.jrag:288
   */
  private MethodDecl addSuppressedMethod() {
    TypeDecl throwableType = lookupType("java.lang", "Throwable");
    if (throwableType.isUnknown()) {
      throw new Error("Could not find java.lang.Throwable");
    }
    for (MethodDecl method : (Collection<MethodDecl>)
        throwableType.memberMethods("addSuppressed")) {
      if (method.getNumParameter() == 1 &&
          method.getParameter(0).getTypeAccess().type() == throwableType) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.Throwable.addSuppressed()");
  }
  /**
   * @declaredat ASTNode:1
   */
  public BasicTWR() {
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
  public BasicTWR(ResourceDeclaration p0, Block p1) {
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
    localLookup_String_reset();
    localVariableDeclaration_String_reset();
    lookupVariable_String_reset();
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
  public BasicTWR clone() throws CloneNotSupportedException {
    BasicTWR node = (BasicTWR) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public BasicTWR copy() {
    try {
      BasicTWR node = (BasicTWR) clone();
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
  public BasicTWR fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public BasicTWR treeCopyNoTransform() {
    BasicTWR tree = (BasicTWR) copy();
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
  public BasicTWR treeCopy() {
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
   * Replaces the Resource child.
   * @param node The new node to replace the Resource child.
   * @apilevel high-level
   */
  public void setResource(ResourceDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Resource child.
   * @return The current node used as the Resource child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Resource")
  public ResourceDeclaration getResource() {
    return (ResourceDeclaration) getChild(0);
  }
  /**
   * Retrieves the Resource child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Resource child.
   * @apilevel low-level
   */
  public ResourceDeclaration getResourceNoTransform() {
    return (ResourceDeclaration) getChildNoTransform(0);
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
  protected java.util.Map localLookup_String_values;
  /**
   * @apilevel internal
   */
  private void localLookup_String_reset() {
    localLookup_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet localLookup(String name) {
    Object _parameters = name;
    if (localLookup_String_values == null) localLookup_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(localLookup_String_values.containsKey(_parameters)) {
      return (SimpleSet)localLookup_String_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet localLookup_String_value = localLookup_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      localLookup_String_values.put(_parameters, localLookup_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localLookup_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet localLookup_compute(String name) {
      VariableDeclaration v = localVariableDeclaration(name);
      if (v != null) {
        return v;
      }
      return lookupVariable(name);
    }
  protected java.util.Map localVariableDeclaration_String_values;
  /**
   * @apilevel internal
   */
  private void localVariableDeclaration_String_reset() {
    localVariableDeclaration_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public VariableDeclaration localVariableDeclaration(String name) {
    Object _parameters = name;
    if (localVariableDeclaration_String_values == null) localVariableDeclaration_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(localVariableDeclaration_String_values.containsKey(_parameters)) {
      return (VariableDeclaration)localVariableDeclaration_String_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    VariableDeclaration localVariableDeclaration_String_value = getResource().declaresVariable(name) ? getResource() : null;
    if (isFinal && num == state().boundariesCrossed) {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localVariableDeclaration_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect MultiCatch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\MultiCatch.jrag:111
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    Object _parameters = name;
    if (lookupVariable_String_values == null) lookupVariable_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(lookupVariable_String_values.containsKey(_parameters)) {
      return (SimpleSet)lookupVariable_String_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);
    if (isFinal && num == state().boundariesCrossed) {
      lookupVariable_String_values.put(_parameters, lookupVariable_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lookupVariable_String_value;
  }
  protected java.util.Map lookupVariable_String_values;
  /**
   * @apilevel internal
   */
  private void lookupVariable_String_reset() {
    lookupVariable_String_values = null;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\MultiCatch.jrag:99
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getBlockNoTransform()) {
      return localLookup(name);
    }
    else {
      return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\TryWithResources.jrag:315
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return getResource().localNum()
      + getResource().type().variableSize()
      + 2 * lookupType("java.lang", "Throwable").variableSize();
    }
    else if (caller == getResourceNoTransform()) {
      return localNum();
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
