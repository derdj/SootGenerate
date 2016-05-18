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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:155
 * @production AddExpr : {@link AdditiveExpr};

 */
public class AddExpr extends AdditiveExpr implements Cloneable {
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:204
   */
  public void typeCheck() {
    TypeDecl left = getLeftOperand().type();
    TypeDecl right = getRightOperand().type();
    if (!left.isString() && !right.isString()) {
      super.typeCheck();
    } else if (left.isVoid()) {
      error("The type void of the left hand side is not numeric");
    } else if (right.isVoid()) {
      error("The type void of the right hand side is not numeric");
    }
  }
  /**
   * @aspect CodeGenerationBinaryOperations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:662
   */
  void emitOperation(CodeGeneration gen) { type().add(gen); }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:984
   */
  public void createBCode(CodeGeneration gen) {
    if (!type().isString()) {
      super.createBCode(gen);
    } else if (isConstant()) {
      StringLiteral.push(gen, constant().stringValue());
    } else {
      TypeDecl stringBuffer = lookupType("java.lang", "StringBuffer");
      String classname = stringBuffer.constantPoolName();
      String desc;
      int index;
      TypeDecl argumentType;
      if (firstStringAddPart()) {
        stringBuffer.emitNew(gen); // new StringBuffer
        gen.emitDup();             // dup
        desc = "()V";
        index = gen.constantPool().addMethodref(classname, "<init>", desc);
        gen.emit(Bytecode.INVOKESPECIAL, -1).add2(index); // invokespecial StringBuffer()
        getLeftOperand().createBCode(gen); // left
        argumentType = getLeftOperand().type().stringPromotion();
        desc = "(" + argumentType.typeDescriptor() + ")" + stringBuffer.typeDescriptor();
        index = gen.constantPool().addMethodref(classname, "append", desc);
        gen.emit(Bytecode.INVOKEVIRTUAL, -argumentType.variableSize()).add2(index); // StringBuffer.append
      } else {
        getLeftOperand().createBCode(gen);
      }
      getRightOperand().createBCode(gen); // right
      argumentType = getRightOperand().type().stringPromotion();
      desc = "(" + argumentType.typeDescriptor() + ")" + stringBuffer.typeDescriptor();
      index = gen.constantPool().addMethodref(classname, "append", desc);
      gen.emit(Bytecode.INVOKEVIRTUAL, -argumentType.variableSize()).add2(index); // StringBuffer.append
      if (lastStringAddPart()) {
        desc = "()" + type().typeDescriptor();
        index = gen.constantPool().addMethodref(classname, "toString", desc);
        gen.emit(Bytecode.INVOKEVIRTUAL, 0).add2(index); // StringBuffer.toString
      }
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public AddExpr() {
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
  public AddExpr(Expr p0, Expr p1) {
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
    type_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:39
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:45
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:51
   */
  public AddExpr clone() throws CloneNotSupportedException {
    AddExpr node = (AddExpr) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public AddExpr copy() {
    try {
      AddExpr node = (AddExpr) clone();
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
   * @declaredat ASTNode:77
   */
  public AddExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:86
   */
  public AddExpr treeCopyNoTransform() {
    AddExpr tree = (AddExpr) copy();
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
   * @declaredat ASTNode:106
   */
  public AddExpr treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:113
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the LeftOperand child.
   * @param node The new node to replace the LeftOperand child.
   * @apilevel high-level
   */
  public void setLeftOperand(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the LeftOperand child.
   * @return The current node used as the LeftOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="LeftOperand")
  public Expr getLeftOperand() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the LeftOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the LeftOperand child.
   * @apilevel low-level
   */
  public Expr getLeftOperandNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the RightOperand child.
   * @param node The new node to replace the RightOperand child.
   * @apilevel high-level
   */
  public void setRightOperand(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the RightOperand child.
   * @return The current node used as the RightOperand child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="RightOperand")
  public Expr getRightOperand() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the RightOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the RightOperand child.
   * @apilevel low-level
   */
  public Expr getRightOperandNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  @ASTNodeAnnotation.Attribute
  public Constant constant() {
    ASTNode$State state = state();
    Constant constant_value = type().add(getLeftOperand().constant(), getRightOperand().constant());

    return constant_value;
  }
  @ASTNodeAnnotation.Attribute
  public String printOp() {
    ASTNode$State state = state();
    String printOp_value = "+";

    return printOp_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean type_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl type_value;
  /**
   * @apilevel internal
   */
  private void type_reset() {
    type_computed = false;
    type_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    if(type_computed) {
      return type_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    type_value = type_compute();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl type_compute() {
      TypeDecl left = getLeftOperand().type();
      TypeDecl right = getRightOperand().type();
      if (!left.isString() && !right.isString()) {
        return super.type();
      } else {
        if (left.isVoid() || right.isVoid()) {
          return unknownType();
        }
        // pick the string type
        return left.isString() ? left : right;
      }
    }
  @ASTNodeAnnotation.Attribute
  public boolean isStringAdd() {
    ASTNode$State state = state();
    boolean isStringAdd_value = type().isString() && !isConstant();

    return isStringAdd_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean firstStringAddPart() {
    ASTNode$State state = state();
    boolean firstStringAddPart_value = type().isString() && !getLeftOperand().isStringAdd();

    return firstStringAddPart_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean lastStringAddPart() {
    ASTNode$State state = state();
    boolean lastStringAddPart_value = !getParent().isStringAdd();

    return lastStringAddPart_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:53
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_targetType(ASTNode caller, ASTNode child) {
    if (caller == getRightOperandNoTransform()){
    if (getRightOperand().stringContext()) {
      return getLeftOperand().type();
    } else if (!getLeftOperand().isPolyExpression() && !getRightOperand().isPolyExpression()) {
      return type();
    } else {
      return targetType();
    }
  }
    else if (caller == getLeftOperandNoTransform()){
    if (getLeftOperand().stringContext()) {
      return getRightOperand().type();
    } else if (!getLeftOperand().isPolyExpression() && !getRightOperand().isPolyExpression()) {
      return type();
    } else {
      return targetType();
    }
  }
    else {
      return super.Define_TypeDecl_targetType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:234
   * @apilevel internal
   */
  public boolean Define_boolean_stringContext(ASTNode caller, ASTNode child) {
    if (caller == getRightOperandNoTransform()){
    if (!getRightOperand().isPolyExpression() && !getLeftOperand().isPolyExpression()) {
      if (getLeftOperand().type().isString() && !getRightOperand().type().isString()) {
        return true;
      }
    }
    return false;
  }
    else if (caller == getLeftOperandNoTransform()){
    if (!getRightOperand().isPolyExpression() && !getLeftOperand().isPolyExpression()) {
      if (getRightOperand().type().isString() && !getLeftOperand().type().isString()) {
        return true;
      }
    }
    return false;
  }
    else {
      return super.Define_boolean_stringContext(caller, child);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
