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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:206
 * @production ForStmt : {@link BranchTargetStmt} ::= <span class="component">InitStmt:{@link Stmt}*</span> <span class="component">[Condition:{@link Expr}]</span> <span class="component">UpdateStmt:{@link Stmt}*</span> <span class="component">{@link Stmt}</span>;

 */
public class ForStmt extends BranchTargetStmt implements Cloneable, VariableScope {
  /**
   * Manually implemented because it is too complex for a template.
   * @aspect PrettyPrintUtil
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PrettyPrintUtil.jrag:91
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("for (");
    if (getNumInitStmt() > 0) {
      if (getInitStmt(0) instanceof VarDeclStmt) {
        VarDeclStmt var = (VarDeclStmt) getInitStmt(0);
        int minDimension = Integer.MAX_VALUE;
        for (VariableDeclaration decl : var.getSingleDeclList()) {
          minDimension = Math.min(minDimension, decl.type().dimension());
        }

        // Print type.
        out.print(var.getModifiers());
        out.print(var.type().elementType().typeName());
        for (int i = minDimension; i > 0; i--) {
          out.print("[]");
        }

        // Print individual declarations.
        for (int i = 0; i < var.getNumSingleDecl(); ++i) {
          if (i != 0) {
            out.print(",");
          }
          VariableDeclaration decl = var.getSingleDecl(i);
          out.print(" " + decl.name());
          for (int j = decl.type().dimension() - minDimension; j > 0; j -= 1) {
            out.print("[]");
          }
          if (decl.hasInit()) {
            out.print(" = ");
            out.print(decl.getInit());
          }
        }
      } else if (getInitStmt(0) instanceof ExprStmt) {
        ExprStmt stmt = (ExprStmt) getInitStmt(0);
        out.print(stmt.getExpr());
        for (int i = 1; i < getNumInitStmt(); i++) {
          out.print(", ");
          stmt = (ExprStmt)getInitStmt(i);
          out.print(stmt.getExpr());
        }
      } else {
        throw new Error("Unexpected initializer in for loop: " + getInitStmt(0));
      }
    }

    out.print("; ");
    if (hasCondition()) {
      out.print(getCondition());
    }
    out.print("; ");

    // Print update statements.
    for (int i = 0; i < getNumUpdateStmt(); i++) {
      if (i > 0) {
        out.print(", ");
      }
      ExprStmt update = (ExprStmt) getUpdateStmt(i);
      out.print(update.getExpr());
    }

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
   * @aspect TypeCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:392
   */
  public void typeCheck() {
    if (hasCondition()) {
      TypeDecl cond = getCondition().type();
      if (!cond.isBoolean()) {
        errorf("the type of \"%s\" is %s which is not boolean",
            getCondition().prettyPrint(), cond.name());
      }
    }
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1599
   */
  public void createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    for (int i=0; i<getNumInitStmt(); i++) {
      getInitStmt(i).createBCode(gen);
    }
    gen.addLabel(cond_label());
    if (!getCondition().isConstant()) {
      getCondition().branchFalse(gen, end_label());
    }
    if (getCondition().canBeTrue()) {
      gen.addLabel(begin_label());
      getStmt().createBCode(gen);
      gen.addLabel(update_label());
      for (int i=0; i<getNumUpdateStmt(); i++) {
        getUpdateStmt(i).createBCode(gen);
      }
      gen.emitGoto(cond_label());
    }
    gen.addLabel(end_label());
    gen.addVariableScopeLabel(variableScopeEndLabel(gen));
  }
  /**
   * @declaredat ASTNode:1
   */
  public ForStmt() {
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
    children = new ASTNode[4];
    setChild(new List(), 0);
    setChild(new Opt(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:16
   */
  public ForStmt(List<Stmt> p0, Opt<Expr> p1, List<Stmt> p2, Stmt p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setChild(p3, 3);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:25
   */
  protected int numChildren() {
    return 4;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:31
   */
  public boolean mayHaveRewrite() {
    return true;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:37
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    isDUbeforeCondition_Variable_reset();
    localLookup_String_reset();
    localVariableDeclaration_String_reset();
    canCompleteNormally_reset();
    variableScopeEndLabel_CodeGeneration_reset();
    cond_label_reset();
    begin_label_reset();
    update_label_reset();
    end_label_reset();
    lookupVariable_String_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:55
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:61
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:67
   */
  public ForStmt clone() throws CloneNotSupportedException {
    ForStmt node = (ForStmt) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:74
   */
  public ForStmt copy() {
    try {
      ForStmt node = (ForStmt) clone();
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
   * @declaredat ASTNode:93
   */
  public ForStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:102
   */
  public ForStmt treeCopyNoTransform() {
    ForStmt tree = (ForStmt) copy();
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
   * @declaredat ASTNode:122
   */
  public ForStmt treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:129
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the InitStmt list.
   * @param list The new list node to be used as the InitStmt list.
   * @apilevel high-level
   */
  public void setInitStmtList(List<Stmt> list) {
    setChild(list, 0);
  }
  /**
   * Retrieves the number of children in the InitStmt list.
   * @return Number of children in the InitStmt list.
   * @apilevel high-level
   */
  public int getNumInitStmt() {
    return getInitStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the InitStmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the InitStmt list.
   * @apilevel low-level
   */
  public int getNumInitStmtNoTransform() {
    return getInitStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the InitStmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the InitStmt list.
   * @apilevel high-level
   */
  public Stmt getInitStmt(int i) {
    return (Stmt) getInitStmtList().getChild(i);
  }
  /**
   * Check whether the InitStmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasInitStmt() {
    return getInitStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the InitStmt list.
   * @param node The element to append to the InitStmt list.
   * @apilevel high-level
   */
  public void addInitStmt(Stmt node) {
    List<Stmt> list = (parent == null || state == null) ? getInitStmtListNoTransform() : getInitStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addInitStmtNoTransform(Stmt node) {
    List<Stmt> list = getInitStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the InitStmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setInitStmt(Stmt node, int i) {
    List<Stmt> list = getInitStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the InitStmt list.
   * @return The node representing the InitStmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="InitStmt")
  public List<Stmt> getInitStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the InitStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the InitStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getInitStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(0);
  }
  /**
   * Retrieves the InitStmt list.
   * @return The node representing the InitStmt list.
   * @apilevel high-level
   */
  public List<Stmt> getInitStmts() {
    return getInitStmtList();
  }
  /**
   * Retrieves the InitStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the InitStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getInitStmtsNoTransform() {
    return getInitStmtListNoTransform();
  }
  /**
   * Replaces the optional node for the Condition child. This is the <code>Opt</code>
   * node containing the child Condition, not the actual child!
   * @param opt The new node to be used as the optional node for the Condition child.
   * @apilevel low-level
   */
  public void setConditionOpt(Opt<Expr> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) Condition child.
   * @param node The new node to be used as the Condition child.
   * @apilevel high-level
   */
  public void setCondition(Expr node) {
    getConditionOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Condition child exists.
   * @return {@code true} if the optional Condition child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasCondition() {
    return getConditionOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Condition child.
   * @return The Condition child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getCondition() {
    return (Expr) getConditionOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Condition child. This is the <code>Opt</code> node containing the child Condition, not the actual child!
   * @return The optional node for child the Condition child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Condition")
  public Opt<Expr> getConditionOpt() {
    return (Opt<Expr>) getChild(1);
  }
  /**
   * Retrieves the optional node for child Condition. This is the <code>Opt</code> node containing the child Condition, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Condition.
   * @apilevel low-level
   */
  public Opt<Expr> getConditionOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(1);
  }
  /**
   * Replaces the UpdateStmt list.
   * @param list The new list node to be used as the UpdateStmt list.
   * @apilevel high-level
   */
  public void setUpdateStmtList(List<Stmt> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the UpdateStmt list.
   * @return Number of children in the UpdateStmt list.
   * @apilevel high-level
   */
  public int getNumUpdateStmt() {
    return getUpdateStmtList().getNumChild();
  }
  /**
   * Retrieves the number of children in the UpdateStmt list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the UpdateStmt list.
   * @apilevel low-level
   */
  public int getNumUpdateStmtNoTransform() {
    return getUpdateStmtListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the UpdateStmt list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the UpdateStmt list.
   * @apilevel high-level
   */
  public Stmt getUpdateStmt(int i) {
    return (Stmt) getUpdateStmtList().getChild(i);
  }
  /**
   * Check whether the UpdateStmt list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasUpdateStmt() {
    return getUpdateStmtList().getNumChild() != 0;
  }
  /**
   * Append an element to the UpdateStmt list.
   * @param node The element to append to the UpdateStmt list.
   * @apilevel high-level
   */
  public void addUpdateStmt(Stmt node) {
    List<Stmt> list = (parent == null || state == null) ? getUpdateStmtListNoTransform() : getUpdateStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addUpdateStmtNoTransform(Stmt node) {
    List<Stmt> list = getUpdateStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the UpdateStmt list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setUpdateStmt(Stmt node, int i) {
    List<Stmt> list = getUpdateStmtList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the UpdateStmt list.
   * @return The node representing the UpdateStmt list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="UpdateStmt")
  public List<Stmt> getUpdateStmtList() {
    List<Stmt> list = (List<Stmt>) getChild(2);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the UpdateStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the UpdateStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getUpdateStmtListNoTransform() {
    return (List<Stmt>) getChildNoTransform(2);
  }
  /**
   * Retrieves the UpdateStmt list.
   * @return The node representing the UpdateStmt list.
   * @apilevel high-level
   */
  public List<Stmt> getUpdateStmts() {
    return getUpdateStmtList();
  }
  /**
   * Retrieves the UpdateStmt list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the UpdateStmt list.
   * @apilevel low-level
   */
  public List<Stmt> getUpdateStmtsNoTransform() {
    return getUpdateStmtListNoTransform();
  }
  /**
   * Replaces the Stmt child.
   * @param node The new node to replace the Stmt child.
   * @apilevel high-level
   */
  public void setStmt(Stmt node) {
    setChild(node, 3);
  }
  /**
   * Retrieves the Stmt child.
   * @return The current node used as the Stmt child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Stmt")
  public Stmt getStmt() {
    return (Stmt) getChild(3);
  }
  /**
   * Retrieves the Stmt child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Stmt child.
   * @apilevel low-level
   */
  public Stmt getStmtNoTransform() {
    return (Stmt) getChildNoTransform(3);
  }
  @ASTNodeAnnotation.Attribute
  public boolean potentialTargetOf(Stmt branch) {
    ASTNode$State state = state();
    boolean potentialTargetOf_Stmt_value = branch.canBranchTo(this);

    return potentialTargetOf_Stmt_value;
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
      if (!(!hasCondition() || getCondition().isDAafterFalse(v))) {
        return false;
      }
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDAafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafterInitialization(Variable v) {
    ASTNode$State state = state();
    boolean isDAafterInitialization_Variable_value = getNumInitStmt() == 0 ? isDAbefore(v) : getInitStmt(getNumInitStmt()-1).isDAafter(v);

    return isDAafterInitialization_Variable_value;
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
      if (!isDUbeforeCondition(v)) {
        // start a circular evaluation here
        return false;
      }
      if (!(!hasCondition() || getCondition().isDUafterFalse(v))) {
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
  public boolean isDUafterInit(Variable v) {
    ASTNode$State state = state();
    boolean isDUafterInit_Variable_value = getNumInitStmt() == 0 ? isDUbefore(v) : getInitStmt(getNumInitStmt()-1).isDUafter(v);

    return isDUafterInit_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private void isDUbeforeCondition_Variable_reset() {
    isDUbeforeCondition_Variable_values = null;
  }
  protected java.util.Map isDUbeforeCondition_Variable_values;
  @ASTNodeAnnotation.Attribute
  public boolean isDUbeforeCondition(Variable v) {
    Object _parameters = v;
    if (isDUbeforeCondition_Variable_values == null) isDUbeforeCondition_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if(isDUbeforeCondition_Variable_values.containsKey(_parameters)) {
      Object _o = isDUbeforeCondition_Variable_values.get(_parameters);
      if(!(_o instanceof ASTNode$State.CircularValue)) {
        return ((Boolean)_o).booleanValue();
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      isDUbeforeCondition_Variable_values.put(_parameters, _value);
      _value.value = Boolean.valueOf(true);
    }
    ASTNode$State state = state();
    boolean new_isDUbeforeCondition_Variable_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = new Integer(state.CIRCLE_INDEX);
        state.CHANGE = false;
        new_isDUbeforeCondition_Variable_value = isDUbeforeCondition_compute(v);
        if (new_isDUbeforeCondition_Variable_value != ((Boolean)_value.value).booleanValue()) {
          state.CHANGE = true;
          _value.value = Boolean.valueOf(new_isDUbeforeCondition_Variable_value);
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        isDUbeforeCondition_Variable_values.put(_parameters, new_isDUbeforeCondition_Variable_value);
      } else {
        isDUbeforeCondition_Variable_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = isDUbeforeCondition_compute(v);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_isDUbeforeCondition_Variable_value;
    }
    if (!new Integer(state.CIRCLE_INDEX).equals(_value.visited)) {
      _value.visited = new Integer(state.CIRCLE_INDEX);
      new_isDUbeforeCondition_Variable_value = isDUbeforeCondition_compute(v);
      if (state.RESET_CYCLE) {
        isDUbeforeCondition_Variable_values.remove(_parameters);
      }
      else if (new_isDUbeforeCondition_Variable_value != ((Boolean)_value.value).booleanValue()) {
        state.CHANGE = true;
        _value.value = new_isDUbeforeCondition_Variable_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_isDUbeforeCondition_Variable_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return ((Boolean)_value.value).booleanValue();
  }
  /**
   * @apilevel internal
   */
  private boolean isDUbeforeCondition_compute(Variable v) {
      if (!isDUafterInit(v)) {
        return false;
      } else if (!isDUafterUpdate(v)) {
        return false;
      }
      return true;
    }
  /**
   * @attribute syn
   * @aspect DU
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1302
   */
  @ASTNodeAnnotation.Attribute
  public boolean isDUafterUpdate(Variable v) {
    ASTNode$State state = state();
    try {
        if (!isDUbeforeCondition(v)) // start a circular evaluation here
          return false;
        if (getNumUpdateStmt() > 0) {
          return getUpdateStmt(getNumUpdateStmt()-1).isDUafter(v);
        }
        if (!getStmt().isDUafter(v)) {
          return false;
        }
        for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
          ContinueStmt stmt = (ContinueStmt) iter.next();
          if (!stmt.isDUafterReachedFinallyBlocks(v)) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
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
    VariableDeclaration localVariableDeclaration_String_value = localVariableDeclaration_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      localVariableDeclaration_String_values.put(_parameters, localVariableDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return localVariableDeclaration_String_value;
  }
  /**
   * @apilevel internal
   */
  private VariableDeclaration localVariableDeclaration_compute(String name) {
      for (Stmt stmt: getInitStmtList()) {
        VariableDeclaration decl = stmt.variableDeclaration(name);
        if (decl != null) {
          return decl;
        }
      }
      return null;
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
    canCompleteNormally_value = reachable() && hasCondition()
          && (!getCondition().isConstant() || !getCondition().isTrue()) || reachableBreak();
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
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
  protected boolean begin_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int begin_label_value;
  /**
   * @apilevel internal
   */
  private void begin_label_reset() {
    begin_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int begin_label() {
    if(begin_label_computed) {
      return begin_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    begin_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      begin_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return begin_label_value;
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
  /**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PreciseRethrow.jrag:84
   */
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    try {
        for (Stmt stmt : getInitStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        for (Stmt stmt : getUpdateStmtList()) {
          if (stmt.modifiedInScope(var)) {
            return true;
          }
        }
        return getStmt().modifiedInScope(var);
      }
    finally {
    }
  }
  /**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupVariable.jrag:39
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BranchTarget.jrag:242
   * @apilevel internal
   */
  public Stmt Define_Stmt_branchTarget(ASTNode caller, ASTNode child, Stmt branch) {
     {
      int childIndex = this.getIndexOfChild(caller);
      return branch.canBranchTo(this) ? this : branchTarget(branch);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:716
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getUpdateStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      {
    if (!getStmt().isDAafter(v)) {
      return false;
    }
    for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
      ContinueStmt stmt = (ContinueStmt) iter.next();
      if (!stmt.isDAafterReachedFinallyBlocks(v)) {
        return false;
      }
    }
    return true;
  }
    }
    else if (caller == getStmtNoTransform()){
    if (hasCondition() && getCondition().isDAafterTrue(v)) {
      return true;
    }
    if (!hasCondition() && isDAafterInitialization(v)) {
      return true;
    }
    return false;
  }
    else if (caller == getConditionOptNoTransform()) {
      return isDAafterInitialization(v);
    }
    else if (caller == getInitStmtListNoTransform()) {
      int i = caller.getIndexOfChild(child);
      return i == 0 ? isDAbefore(v) : getInitStmt(i-1).isDAafter(v);
    }
    else {
      return getParent().Define_boolean_isDAbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1321
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getUpdateStmtListNoTransform()) {
      int i = caller.getIndexOfChild(child);
      {
    if (!isDUbeforeCondition(v)) // start a circular evaluation here
      return false;
    if (i == 0) {
      if (!getStmt().isDUafter(v)) {
        return false;
      }
      for (Iterator iter = targetContinues().iterator(); iter.hasNext(); ) {
        ContinueStmt stmt = (ContinueStmt) iter.next();
        if (!stmt.isDUafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    } else {
      return getUpdateStmt(i-1).isDUafter(v);
    }
  }
    }
    else if (caller == getStmtNoTransform()) {
      return isDUbeforeCondition(v) && (hasCondition() ?
    getCondition().isDUafterTrue(v) : isDUafterInit(v));
    }
    else if (caller == getConditionOptNoTransform()) {
      return isDUbeforeCondition(v);
    }
    else if (caller == getInitStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return childIndex == 0 ? isDUbefore(v) : getInitStmt(childIndex-1).isDUafter(v);
    }
    else {
      return getParent().Define_boolean_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupVariable.jrag:150
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getStmtNoTransform()) {
      return localLookup(name);
    }
    else if (caller == getUpdateStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return localLookup(name);
    }
    else if (caller == getConditionOptNoTransform()) {
      return localLookup(name);
    }
    else if (caller == getInitStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return localLookup(name);
    }
    else {
      return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:370
   * @apilevel internal
   */
  public VariableScope Define_VariableScope_outerScope(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return this;
    }
    else if (caller == getInitStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return this;
    }
    else {
      return getParent().Define_VariableScope_outerScope(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:445
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:158
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return reachable()
      && (!hasCondition() || (!getCondition().isConstant() || !getCondition().isFalse()));
    }
    else {
      return getParent().Define_boolean_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:213
   * @apilevel internal
   */
  public boolean Define_boolean_reportUnreachable(ASTNode caller, ASTNode child) {
    if (caller == getStmtNoTransform()) {
      return reachable();
    }
    else {
      return getParent().Define_boolean_reportUnreachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:71
   * @apilevel internal
   */
  public int Define_int_variableScopeEndLabel(ASTNode caller, ASTNode child, CodeGeneration gen) {
    if (caller == getInitStmtListNoTransform()) {
      int i = caller.getIndexOfChild(child);
      return variableScopeEndLabel(gen);
    }
    else {
      return getParent().Define_int_variableScopeEndLabel(this, caller, gen);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\LocalNum.jrag:175
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if (caller == getInitStmtListNoTransform()) {
      int index = caller.getIndexOfChild(child);
      {
    if (index == 0) {
      return localNum();
    } else {
      return getInitStmt(index-1).localNum() + getInitStmt(index-1).localSize();
    }
  }
    }
    else if (caller == getStmtNoTransform()){
    if (getNumInitStmt() == 0) {
      return localNum();
    } else {
      Stmt last = getInitStmt(getNumInitStmt()-1);
      return last.localNum() + last.localSize();
    }
  }
    else {
      return getParent().Define_int_localNum(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:58
   * @apilevel internal
   */
  public boolean Define_boolean_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getStmtNoTransform()) {
      return false;
    }
    else if (caller == getUpdateStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return modifiedInScope(var);
    }
    else if (caller == getInitStmtListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
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
    // Declared at @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1341
    if (!hasCondition()) {
      state().duringDU++;
      ASTNode result = rewriteRule0();
      state().duringDU--;
      return result;
    }
    return super.rewriteTo();
  }
  /**
   * @declaredat @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1341
   * @apilevel internal
   */
  private ForStmt rewriteRule0() {
{
      setCondition(new BooleanLiteral("true"));
      return this;
    }  }
}
