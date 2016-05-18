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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:191
 * @production Stmt : {@link ASTNode};

 */
public abstract class Stmt extends ASTNode<ASTNode> implements Cloneable {
  /**
   * @return An Opt node containing the finally and monitor exit blocks
   * from the list of enclosing try-statements and synchronized blocks.
   * @aspect NTAFinally
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NTAFinally.jrag:82
   */
  public Opt<Block> branchFinallyOpt() {
    FinallyHost enclosing = enclosingFinally(this);
    if (enclosing != null) {
      return new Opt<Block>(ntaFinallyBlock(enclosing, this, enclosing.getFinallyBlock()));
    } else {
      return new Opt<Block>();
    }
  }
  /**
   * @aspect UnreachableStatements
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:35
   */
  void checkUnreachableStmt() {
    if (!reachable() && reportUnreachable()) {
      error("statement is unreachable");
    }
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1359
   */
  public void createBCode(CodeGeneration gen) {
    gen.addLineNumberEntryAtCurrentPC(this);
  }
  /**
   * @declaredat ASTNode:1
   */
  public Stmt() {
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
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:15
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:21
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    finallyIterator_reset();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    canCompleteNormally_reset();
    localSize_reset();
    enclosingFinally_Stmt_reset();
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
  public Stmt clone() throws CloneNotSupportedException {
    Stmt node = (Stmt) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:63
   */
  public abstract Stmt fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:70
   */
  public abstract Stmt treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:78
   */
  public abstract Stmt treeCopy();
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PreciseRethrow.jrag:84
   */
  @ASTNodeAnnotation.Attribute
  public abstract boolean modifiedInScope(Variable var);
  /**
   * @apilevel internal
   */
  protected boolean finallyIterator_computed = false;
  /**
   * @apilevel internal
   */
  protected Iterator<FinallyHost> finallyIterator_value;
  /**
   * @apilevel internal
   */
  private void finallyIterator_reset() {
    finallyIterator_computed = false;
    finallyIterator_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Iterator<FinallyHost> finallyIterator() {
    if(finallyIterator_computed) {
      return finallyIterator_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    finallyIterator_value = finallyIterator_compute();
    if (isFinal && num == state().boundariesCrossed) {
      finallyIterator_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return finallyIterator_value;
  }
  /**
   * @apilevel internal
   */
  private Iterator<FinallyHost> finallyIterator_compute() {
      return new LazyFinallyIterator(this);
    }
  @ASTNodeAnnotation.Attribute
  public boolean canBranchTo(BranchTargetStmt target) {
    ASTNode$State state = state();
    boolean canBranchTo_BranchTargetStmt_value = false;

    return canBranchTo_BranchTargetStmt_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean canBranchTo(LabeledStmt target) {
    ASTNode$State state = state();
    boolean canBranchTo_LabeledStmt_value = false;

    return canBranchTo_LabeledStmt_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean canBranchTo(SwitchStmt target) {
    ASTNode$State state = state();
    boolean canBranchTo_SwitchStmt_value = false;

    return canBranchTo_SwitchStmt_value;
  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafter_Variable_reset() {
    isDAafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    Object _parameters = v;
    if (isDAafter_Variable_values == null) isDAafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(isDAafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDAafter_Variable_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafter_Variable_value = isDAbefore(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
  }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDUafter_Variable_reset() {
    isDUafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    Object _parameters = v;
    if (isDUafter_Variable_values == null) isDUafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(isDUafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDUafter_Variable_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDUafter_Variable_value = isDUafter_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafter_compute(Variable v) {
      throw new Error("isDUafter in " + getClass().getName());
    }
  @ASTNodeAnnotation.Attribute
  public VariableDeclaration variableDeclaration(String name) {
    ASTNode$State state = state();
    VariableDeclaration variableDeclaration_String_value = null;

    return variableDeclaration_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean continueLabel() {
    ASTNode$State state = state();
    boolean continueLabel_value = false;

    return continueLabel_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_value;
  /**
   * @apilevel internal
   */
  private void canCompleteNormally_reset() {
    canCompleteNormally_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean canCompleteNormally() {
    if(canCompleteNormally_computed) {
      return canCompleteNormally_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    canCompleteNormally_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1621
   */
  @ASTNodeAnnotation.Attribute
  public int break_label() {
    ASTNode$State state = state();
    try {
        throw new UnsupportedOperationException("Can not break at this statement of type " + getClass().getName());
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1645
   */
  @ASTNodeAnnotation.Attribute
  public int continue_label() {
    ASTNode$State state = state();
    try {
        throw new UnsupportedOperationException("Can not continue at this statement");
      }
    finally {
    }
  }
  /**
   * @apilevel internal
   */
  protected boolean localSize_computed = false;
  /**
   * @apilevel internal
   */
  protected int localSize_value;
  /**
   * @apilevel internal
   */
  private void localSize_reset() {
    localSize_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int localSize() {
    if(localSize_computed) {
      return localSize_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    localSize_value = 0;
    if (isFinal && num == state().boundariesCrossed) {
      localSize_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localSize_value;
  }
  /**
   * @return the target statement for a break or continue
   * @attribute inh
   * @aspect BranchTarget
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BranchTarget.jrag:227
   */
  @ASTNodeAnnotation.Attribute
  public Stmt branchTarget(Stmt branch) {
    ASTNode$State state = state();
    Stmt branchTarget_Stmt_value = getParent().Define_Stmt_branchTarget(this, null, branch);

    return branchTarget_Stmt_value;
  }
  /**
   * Find finally block of enclosing try-statement, or monitor exit
   * block of enclosing synchronized block.
   * @param branch the source branch
   * @return a finally block, or <code>null</code> if there is no
   * enclosing try-statement
   * @attribute inh
   * @aspect BranchTarget
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BranchTarget.jrag:262
   */
  @ASTNodeAnnotation.Attribute
  public FinallyHost enclosingFinally(Stmt branch) {
    Object _parameters = branch;
    if (enclosingFinally_Stmt_values == null) enclosingFinally_Stmt_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(enclosingFinally_Stmt_values.containsKey(_parameters)) {
      return (FinallyHost)enclosingFinally_Stmt_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    FinallyHost enclosingFinally_Stmt_value = getParent().Define_FinallyHost_enclosingFinally(this, null, branch);
    if (isFinal && num == state().boundariesCrossed) {
      enclosingFinally_Stmt_values.put(_parameters, enclosingFinally_Stmt_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return enclosingFinally_Stmt_value;
  }
  protected java.util.Map enclosingFinally_Stmt_values;
  /**
   * @apilevel internal
   */
  private void enclosingFinally_Stmt_reset() {
    enclosingFinally_Stmt_values = null;
  }
  /**
   * @attribute inh
   * @aspect DA
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:255
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDAbefore(Variable v) {
    ASTNode$State state = state();
    boolean isDAbefore_Variable_value = getParent().Define_boolean_isDAbefore(this, null, v);

    return isDAbefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect DU
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:779
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDUbefore(Variable v) {
    ASTNode$State state = state();
    boolean isDUbefore_Variable_value = getParent().Define_boolean_isDUbefore(this, null, v);

    return isDUbefore_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:46
   */
  @ASTNodeAnnotation.Attribute
  public Collection lookupMethod(String name) {
    ASTNode$State state = state();
    Collection lookupMethod_String_value = getParent().Define_Collection_lookupMethod(this, null, name);

    return lookupMethod_String_value;
  }
  /**
   * @attribute inh
   * @aspect LookupFullyQualifiedTypes
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupType.jrag:130
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupType(String packageName, String typeName) {
    ASTNode$State state = state();
    TypeDecl lookupType_String_String_value = getParent().Define_TypeDecl_lookupType(this, null, packageName, typeName);

    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupType.jrag:339
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupType(String name) {
    ASTNode$State state = state();
    SimpleSet lookupType_String_value = getParent().Define_SimpleSet_lookupType(this, null, name);

    return lookupType_String_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupVariable.jrag:37
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    ASTNode$State state = state();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);

    return lookupVariable_String_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeAnalysis.jrag:561
   */
  @ASTNodeAnnotation.Attribute
  public BodyDecl enclosingBodyDecl() {
    ASTNode$State state = state();
    BodyDecl enclosingBodyDecl_value = getParent().Define_BodyDecl_enclosingBodyDecl(this, null);

    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeAnalysis.jrag:639
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl hostType() {
    ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);

    return hostType_value;
  }
  /**
   * @attribute inh
   * @aspect UnreachableStatements
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:51
   */
  @ASTNodeAnnotation.Attribute
  public boolean reachable() {
    ASTNode$State state = state();
    boolean reachable_value = getParent().Define_boolean_reachable(this, null);

    return reachable_value;
  }
  /**
   * @attribute inh
   * @aspect UnreachableStatements
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:209
   */
  @ASTNodeAnnotation.Attribute
  public boolean reportUnreachable() {
    ASTNode$State state = state();
    boolean reportUnreachable_value = getParent().Define_boolean_reportUnreachable(this, null);

    return reportUnreachable_value;
  }
  /**
   * Checks if the branch statement leaves the monitor.
   * @return <code>true</code> if the branch leaves the monitor
   * @attribute inh
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1836
   */
  @ASTNodeAnnotation.Attribute
  public boolean leavesMonitor(Stmt branch, SynchronizedStmt monitor) {
    ASTNode$State state = state();
    boolean leavesMonitor_Stmt_SynchronizedStmt_value = getParent().Define_boolean_leavesMonitor(this, null, branch, monitor);

    return leavesMonitor_Stmt_SynchronizedStmt_value;
  }
  /**
   * @return The next available local variable index.
   * @attribute inh
   * @aspect LocalNum
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\LocalNum.jrag:63
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
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
