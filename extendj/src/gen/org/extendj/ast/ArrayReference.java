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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\grammar\\ConstructorReference.ast:4
 * @production ArrayReference : {@link ConstructorReference};

 */
public class ArrayReference extends ConstructorReference implements Cloneable {
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\NameCheck.jrag:539
   */
  public void nameCheck() {
    Access typeAccess = getTypeAccess();
    while (typeAccess instanceof ArrayTypeAccess) {
      typeAccess = ((ArrayTypeAccess) typeAccess).getAccess();
    }
    if (typeAccess instanceof ParTypeAccess) {
      error("Cannot create array of generic type");
    }
  }
  /**
   * @aspect Java8PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\PrettyPrint.jadd:84
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("::new");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ArrayReference() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  public ArrayReference(Access p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:19
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:25
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:31
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    congruentTo_FunctionDescriptor_reset();
    isExact_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    toBlock_reset();
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
  public ArrayReference clone() throws CloneNotSupportedException {
    ArrayReference node = (ArrayReference) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public ArrayReference copy() {
    try {
      ArrayReference node = (ArrayReference) clone();
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
  public ArrayReference fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public ArrayReference treeCopyNoTransform() {
    ArrayReference tree = (ArrayReference) copy();
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
  public ArrayReference treeCopy() {
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
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  protected java.util.Map congruentTo_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void congruentTo_FunctionDescriptor_reset() {
    congruentTo_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean congruentTo(FunctionDescriptor f) {
    Object _parameters = f;
    if (congruentTo_FunctionDescriptor_values == null) congruentTo_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(congruentTo_FunctionDescriptor_values.containsKey(_parameters)) {
      return ((Boolean)congruentTo_FunctionDescriptor_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean congruentTo_FunctionDescriptor_value = congruentTo_compute(f);
    if (isFinal && num == state().boundariesCrossed) {
      congruentTo_FunctionDescriptor_values.put(_parameters, Boolean.valueOf(congruentTo_FunctionDescriptor_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return congruentTo_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private boolean congruentTo_compute(FunctionDescriptor f) {
      if (f.method.getNumParameter() != 1) {
        return false;
      }
      if (!f.method.getParameter(0).type().assignConversionTo(f.fromInterface().typeInt(), null)) {
        return false;
      }
      if (!f.method.type().isVoid()) {
        if (!getTypeAccess().type().assignConversionTo(f.method.type(), null)) {
          return false;
        }
      }
      return true;
    }
  /**
   * @apilevel internal
   */
  protected boolean isExact_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isExact_value;
  /**
   * @apilevel internal
   */
  private void isExact_reset() {
    isExact_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isExact() {
    if(isExact_computed) {
      return isExact_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isExact_value = true;
    if (isFinal && num == state().boundariesCrossed) {
      isExact_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isExact_value;
  }
  protected java.util.Map potentiallyCompatible_TypeDecl_BodyDecl_values;
  /**
   * @apilevel internal
   */
  private void potentiallyCompatible_TypeDecl_BodyDecl_reset() {
    potentiallyCompatible_TypeDecl_BodyDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean potentiallyCompatible(TypeDecl type, BodyDecl candidateDecl) {
    java.util.List _parameters = new java.util.ArrayList(2);
    _parameters.add(type);
    _parameters.add(candidateDecl);
    if (potentiallyCompatible_TypeDecl_BodyDecl_values == null) potentiallyCompatible_TypeDecl_BodyDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(potentiallyCompatible_TypeDecl_BodyDecl_values.containsKey(_parameters)) {
      return ((Boolean)potentiallyCompatible_TypeDecl_BodyDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean potentiallyCompatible_TypeDecl_BodyDecl_value = potentiallyCompatible_compute(type, candidateDecl);
    if (isFinal && num == state().boundariesCrossed) {
      potentiallyCompatible_TypeDecl_BodyDecl_values.put(_parameters, Boolean.valueOf(potentiallyCompatible_TypeDecl_BodyDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return potentiallyCompatible_TypeDecl_BodyDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean potentiallyCompatible_compute(TypeDecl type, BodyDecl candidateDecl) {
      if (super.potentiallyCompatible(type, candidateDecl) && type.isTypeVariable()) {
        return true;
      } else if (!super.potentiallyCompatible(type, candidateDecl)) {
        return false;
      }
      InterfaceDecl iDecl = (InterfaceDecl) type;
      FunctionDescriptor f = iDecl.functionDescriptor();
      return f.method.arity() == 1;
    }
  /**
   * @attribute syn
   * @aspect ConstructorReferenceToClass
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\ConstructorReferenceToClass.jrag:34
   */
  @ASTNodeAnnotation.Attribute
  public ArrayTypeAccess getArrayTypeWithSizeAccess(Expr expr) {
    ASTNode$State state = state();
    try {
        ArrayTypeAccess startAccess = (ArrayTypeAccess) getTypeAccess().treeCopyNoTransform();
        return recursiveArrayTypeWithSizeAccess(startAccess, (Expr) expr.treeCopyNoTransform());
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect ConstructorReferenceToClass
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\ConstructorReferenceToClass.jrag:39
   */
  @ASTNodeAnnotation.Attribute
  public ArrayTypeAccess recursiveArrayTypeWithSizeAccess(ArrayTypeAccess access, Expr expr) {
    ASTNode$State state = state();
    try {
        if (!(access.getAccess() instanceof ArrayTypeAccess)) {
          return new ArrayTypeWithSizeAccess(access.getAccess(), expr);
        } else {
          return new ArrayTypeAccess(recursiveArrayTypeWithSizeAccess((ArrayTypeAccess) access.getAccess(), expr));
        }
      }
    finally {
    }
  }
  /**
   * @apilevel internal
   */
  protected boolean toBlock_computed = false;
  /**
   * @apilevel internal
   */
  protected Block toBlock_value;
  /**
   * @apilevel internal
   */
  private void toBlock_reset() {
    toBlock_computed = false;
    toBlock_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Block toBlock() {
    if(toBlock_computed) {
      return toBlock_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    toBlock_value = toBlock_compute();
    if (isFinal && num == state().boundariesCrossed) {
      toBlock_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return toBlock_value;
  }
  /**
   * @apilevel internal
   */
  private Block toBlock_compute() {
      // First, build an ArrayCreationExpr used in the block
      String paramName = targetInterface().functionDescriptor().method.getParameter(0).name();
      VarAccess paramAccess = new VarAccess(paramName);
      ArrayCreationExpr arrayExpr = new ArrayCreationExpr(getArrayTypeWithSizeAccess(paramAccess), new Opt());
  
      // Next build actual block
      Stmt blockStmt = null;
      if (targetInterface().functionDescriptor().method.type().isVoid()) {
        blockStmt = new ExprStmt(arrayExpr);
      } else {
        blockStmt = new ReturnStmt(arrayExpr);
      }
      List<Stmt> stmtList = new List<Stmt>();
      stmtList.add(blockStmt);
      return new Block(stmtList);
    }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
