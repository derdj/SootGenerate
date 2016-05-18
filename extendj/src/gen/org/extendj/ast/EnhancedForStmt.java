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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\grammar\\EnhancedFor.ast:1
 * @production EnhancedForStmt : {@link BranchTargetStmt} ::= <span class="component">{@link VariableDeclaration}</span> <span class="component">{@link Expr}</span> <span class="component">{@link Stmt}</span>;

 */
public class EnhancedForStmt extends BranchTargetStmt implements Cloneable, VariableScope {
  /**
   * @aspect EnhancedFor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:41
   */
  public void typeCheck() {
    if (!getExpr().type().isArrayDecl() && !getExpr().type().isIterable()) {
      errorf("type %s of expression in foreach is neither array type nor java.lang.Iterable",
          getExpr().type().name());
    } else if (getExpr().type().isArrayDecl() && !getExpr().type().componentType().assignConversionTo(getVariableDeclaration().type(), null)) {
      errorf("parameter of type %s can not be assigned an element of type %s",
          getVariableDeclaration().type().typeName(), getExpr().type().componentType().typeName());
    } else if (getExpr().type().isIterable() && !getExpr().type().isUnknown()) {
      MethodDecl iterator = (MethodDecl) getExpr().type().memberMethods("iterator").iterator().next();
      MethodDecl next = (MethodDecl) iterator.type().memberMethods("next").iterator().next();
      TypeDecl componentType = next.type();
      if (!componentType.assignConversionTo(getVariableDeclaration().type(), null)) {
        errorf("parameter of type %s can not be assigned an element of type %s",
            getVariableDeclaration().type().typeName(), componentType.typeName());
      }
    }
  }
  /**
   * @aspect EnhancedFor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:86
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("for (");
    out.print(getVariableDeclaration().getModifiers());
    out.print(getVariableDeclaration().getTypeAccess());
    out.print(" " + getVariableDeclaration().name());
    out.print(" : ");
    out.print(getExpr());
    out.print(") ");
    if (getStmt() instanceof Block) {
      out.print(getStmt());
    } else {
      out.print("{");
      out.println();
      out.indent(1);
      out.print(getStmt());
      out.println();
      out.print("}");
    }
  }
  /**
   * @aspect EnhancedForToBytecode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\EnhancedForCodegen.jrag:48
   */
  public void createBCode(CodeGeneration gen) {
    VariableDeclaration decl = getVariableDeclaration();
    gen.addLocalVariableEntryAtCurrentPC(decl.name(), decl.type().typeDescriptor(), extraLocalIndex(), variableScopeEndLabel(gen));
    if (getExpr().type().isArrayDecl()) {
      getExpr().createBCode(gen);
      gen.emitStoreReference(extraLocalIndex());
      IntegerLiteral.push(gen, 0);
      gen.emit(Bytecode.ISTORE).add(extraLocalIndex()+1);
      gen.addLabel(cond_label());
      gen.emit(Bytecode.ILOAD).add(extraLocalIndex()+1);
      gen.emitLoadReference(extraLocalIndex());
      gen.emit(Bytecode.ARRAYLENGTH);
      gen.emitCompare(Bytecode.IF_ICMPGE, end_label());
      gen.emitLoadReference(extraLocalIndex());
      gen.emit(Bytecode.ILOAD).add(extraLocalIndex()+1);
      gen.emit(getExpr().type().componentType().arrayLoad());
      getExpr().type().componentType().emitCastTo(gen, getVariableDeclaration().type());
      getVariableDeclaration().type().emitStoreLocal(gen, getVariableDeclaration().localNum());
      getStmt().createBCode(gen);
      gen.addLabel(update_label());
      gen.emit(Bytecode.IINC).add(extraLocalIndex()+1).add(1);
      gen.emitGoto(cond_label());
      gen.addLabel(end_label());
    } else {
      TypeDecl typeIterable = lookupType("java.lang", "Iterable");
      TypeDecl typeIterator = lookupType("java.util", "Iterator");
      MethodDecl iteratorMethod = getMethod(typeIterable, "iterator");
      MethodDecl hasNextMethod = getMethod(typeIterator, "hasNext");
      MethodDecl nextMethod = getMethod(typeIterator, "next");
      getExpr().createBCode(gen);
      iteratorMethod.emitInvokeMethod(gen, typeIterable);
      gen.emitStoreReference(extraLocalIndex());
      gen.addLabel(cond_label());
      gen.emitLoadReference(extraLocalIndex());
      hasNextMethod.emitInvokeMethod(gen, typeIterator);
      gen.emitCompare(Bytecode.IFEQ, end_label());
      gen.emitLoadReference(extraLocalIndex());
      nextMethod.emitInvokeMethod(gen, typeIterator);
      VariableDeclaration obj = getVariableDeclaration();
      if (!obj.type().boxed().isUnknown()) {
        gen.emitCheckCast(obj.type().boxed());
        obj.type().boxed().emitCastTo(gen, obj.type());
        obj.type().emitStoreLocal(gen, obj.localNum());
      } else {
        gen.emitCheckCast(obj.type());
        gen.emitStoreReference(obj.localNum());
      }
      getStmt().createBCode(gen);
      gen.addLabel(update_label());
      gen.emitGoto(cond_label());
      gen.addLabel(end_label());
    }
    gen.addVariableScopeLabel(variableScopeEndLabel(gen));
  }
  /**
   * @declaredat ASTNode:1
   */
  public EnhancedForStmt() {
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
    children = new ASTNode[3];
  }
  /**
   * @declaredat ASTNode:13
   */
  public EnhancedForStmt(VariableDeclaration p0, Expr p1, Stmt p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:27
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    canCompleteNormally_reset();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    cond_label_reset();
    update_label_reset();
    end_label_reset();
    extraLocalIndex_reset();
    variableScopeEndLabel_CodeGeneration_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:47
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:53
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:59
   */
  public EnhancedForStmt clone() throws CloneNotSupportedException {
    EnhancedForStmt node = (EnhancedForStmt) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:66
   */
  public EnhancedForStmt copy() {
    try {
      EnhancedForStmt node = (EnhancedForStmt) clone();
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
   * @declaredat ASTNode:85
   */
  public EnhancedForStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:94
   */
  public EnhancedForStmt treeCopyNoTransform() {
    EnhancedForStmt tree = (EnhancedForStmt) copy();
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
   * @declaredat ASTNode:114
   */
  public EnhancedForStmt treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:121
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the VariableDeclaration child.
   * @param node The new node to replace the VariableDeclaration child.
   * @apilevel high-level
   */
  public void setVariableDeclaration(VariableDeclaration node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the VariableDeclaration child.
   * @return The current node used as the VariableDeclaration child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="VariableDeclaration")
  public VariableDeclaration getVariableDeclaration() {
    return (VariableDeclaration) getChild(0);
  }
  /**
   * Retrieves the VariableDeclaration child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the VariableDeclaration child.
   * @apilevel low-level
   */
  public VariableDeclaration getVariableDeclarationNoTransform() {
    return (VariableDeclaration) getChildNoTransform(0);
  }
  /**
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  /**
   * Replaces the Stmt child.
   * @param node The new node to replace the Stmt child.
   * @apilevel high-level
   */
  public void setStmt(Stmt node) {
    setChild(node, 2);
  }
  /**
   * Retrieves the Stmt child.
   * @return The current node used as the Stmt child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Stmt")
  public Stmt getStmt() {
    return (Stmt) getChild(2);
  }
  /**
   * Retrieves the Stmt child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Stmt child.
   * @apilevel low-level
   */
  public Stmt getStmtNoTransform() {
    return (Stmt) getChildNoTransform(2);
  }
  /**
   * @attribute syn
   * @aspect EnhancedFor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:78
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet localLookupVariable(String name) {
    ASTNode$State state = state();
    try {
        if (getVariableDeclaration().name().equals(name)) {
          return SimpleSet.emptySet.add(getVariableDeclaration());
        }
        return lookupVariable(name);
      }
    finally {
    }
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
    canCompleteNormally_value = reachable();
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
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
    boolean isDAafter_Variable_value = isDAafter_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafter_compute(Variable v) {
      if (!getExpr().isDAafter(v)) {
        return false;
      }
      /*
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDAafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      */
      return true;
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
      if (!getExpr().isDUafter(v)) {
        return false;
      }
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDUafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  @ASTNodeAnnotation.Attribute
  public boolean continueLabel() {
    ASTNode$State state = state();
    boolean continueLabel_value = true;

    return continueLabel_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean cond_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int cond_label_value;
  /**
   * @apilevel internal
   */
  private void cond_label_reset() {
    cond_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int cond_label() {
    if(cond_label_computed) {
      return cond_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    cond_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      cond_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return cond_label_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean update_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int update_label_value;
  /**
   * @apilevel internal
   */
  private void update_label_reset() {
    update_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int update_label() {
    if(update_label_computed) {
      return update_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    update_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      update_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return update_label_value;
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
  protected boolean extraLocalIndex_computed = false;
  /**
   * @apilevel internal
   */
  protected int extraLocalIndex_value;
  /**
   * @apilevel internal
   */
  private void extraLocalIndex_reset() {
    extraLocalIndex_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int extraLocalIndex() {
    if(extraLocalIndex_computed) {
      return extraLocalIndex_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    extraLocalIndex_value = localNum();
    if (isFinal && num == state().boundariesCrossed) {
      extraLocalIndex_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return extraLocalIndex_value;
  }
  @ASTNodeAnnotation.Attribute
  public int break_label() {
    ASTNode$State state = state();
    int break_label_value = end_label();

    return break_label_value;
  }
  @ASTNodeAnnotation.Attribute
  public int continue_label() {
    ASTNode$State state = state();
    int continue_label_value = update_label();

    return continue_label_value;
  }
  protected java.util.Map variableScopeEndLabel_CodeGeneration_values;
  /**
   * @apilevel internal
   */
  private void variableScopeEndLabel_CodeGeneration_reset() {
    variableScopeEndLabel_CodeGeneration_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public int variableScopeEndLabel(CodeGeneration gen) {
    Object _parameters = gen;
    if (variableScopeEndLabel_CodeGeneration_values == null) variableScopeEndLabel_CodeGeneration_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(variableScopeEndLabel_CodeGeneration_values.containsKey(_parameters)) {
      return ((Integer)variableScopeEndLabel_CodeGeneration_values.get(_parameters)).intValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    int variableScopeEndLabel_CodeGeneration_value = gen.variableScopeLabel();
    if (isFinal && num == state().boundariesCrossed) {
      variableScopeEndLabel_CodeGeneration_values.put(_parameters, Integer.valueOf(variableScopeEndLabel_CodeGeneration_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return variableScopeEndLabel_CodeGeneration_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    boolean modifiedInScope_Variable_value = getStmt().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute inh
   * @aspect EnhancedFor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:66
   */
  @ASTNodeAnnotation.Attribute
  public SimpleSet lookupVariable(String name) {
    ASTNode$State state = state();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);

    return lookupVariable_String_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:37
   * @apilevel internal
   */
  public Stmt Define_Stmt_branchTarget(ASTNode caller, ASTNode child, Stmt branch) {
     {
      int childIndex = this.getIndexOfChild(caller);
      return branch.canBranchTo(this) ? this : branchTarget(branch);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:69
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getStmtNoTransform()) {
      return localLookupVariable(name);
    }
    else if (caller == getExprNoTransform()) {
      return localLookupVariable(name);
    }
    else if (caller == getVariableDeclarationNoTransform()) {
      return localLookupVariable(name);
    }
    else {
      return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:71
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getVariableDeclarationNoTransform()) {
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_NameType_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:76
   * @apilevel internal
   */
  public VariableScope Define_VariableScope_outerScope(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return this;
    }
    else if (caller == getExprNoTransform()) {
      return this;
    }
    else if (caller == getVariableDeclarationNoTransform()) {
      return this;
    }
    else {
      return getParent().Define_VariableScope_outerScope(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:107
   * @apilevel internal
   */
  public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
    if (caller == getVariableDeclarationNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_isMethodParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:108
   * @apilevel internal
   */
  public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
    if (caller == getVariableDeclarationNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_isConstructorParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:109
   * @apilevel internal
   */
  public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    if (caller == getVariableDeclarationNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:113
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return reachable();
    }
    else {
      return getParent().Define_boolean_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:132
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getStmtNoTransform()) {
      return getExpr().isDAafter(v);
    }
    else if (caller == getExprNoTransform()) {
      return v == getVariableDeclaration() || isDAbefore(v);
    }
    else {
      return getParent().Define_boolean_isDAbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:148
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getStmtNoTransform()) {
      return getExpr().isDUafter(v);
    }
    else if (caller == getExprNoTransform()) {
      return v != getVariableDeclaration() && isDUbefore(v);
    }
    else {
      return getParent().Define_boolean_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\EnhancedFor.jrag:150
   * @apilevel internal
   */
  public boolean Define_boolean_insideLoop(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_insideLoop(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\EnhancedForCodegen.jrag:39
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return getVariableDeclaration().localNum() + getVariableDeclaration().type().size();
    }
    else if (caller == getVariableDeclarationNoTransform()) {
      return localNum() + (getExpr().type().isArrayDecl() ? 2 : 1);
    }
    else {
      return getParent().Define_int_localNum(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:50
   * @apilevel internal
   */
  public boolean Define_boolean_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getStmtNoTransform()) {
      return false;
    }
    else if (caller == getVariableDeclarationNoTransform()) {
      return modifiedInScope(var);
    }
    else {
      return getParent().Define_boolean_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
