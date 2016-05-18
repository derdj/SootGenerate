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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:113
 * @production AssignPlusExpr : {@link AssignAdditiveExpr};

 */
public class AssignPlusExpr extends AssignAdditiveExpr implements Cloneable {
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:98
   */
  public void typeCheck() {
    if (!getDest().isVariable()) {
      error("left hand side is not a variable");
    } else if (getSource().type().isUnknown() || getDest().type().isUnknown()) {
      return;
    } else if (getDest().type().isString() && !(getSource().type().isVoid())) {
      return;
    } else if (getSource().type().isBoolean() || getDest().type().isBoolean()) {
      error("Operator + does not operate on boolean types");
    } else if (getSource().type().isPrimitive() && getDest().type().isPrimitive()) {
      return;
    } else
      errorf("can not assign %s of type %s a value of type %s",
          getDest().prettyPrint(), getDest().type().typeName(), getSource().type().typeName());
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:359
   */
  public void createBCode(CodeGeneration gen) {
    TypeDecl dest = getDest().type();
    TypeDecl source = getSource().type();
    if (dest.isString()) {
      getDest().createAssignLoadDest(gen);

      // new StringBuffer()
      TypeDecl stringBuffer = lookupType("java.lang", "StringBuffer");
      String classname = stringBuffer.constantPoolName();
      String desc;
      int index;
      TypeDecl argumentType;
      stringBuffer.emitNew(gen); // new StringBuffer
      gen.emitDup();             // dup
      desc = "()V";
      index = gen.constantPool().addMethodref(classname, "<init>", desc);
      gen.emit(Bytecode.INVOKESPECIAL, -1).add2(index); // invokespecial StringBuffer()

      gen.emitSwap();

      // append
      argumentType = dest.stringPromotion();
      desc = "(" + argumentType.typeDescriptor() + ")" + stringBuffer.typeDescriptor();
      index = gen.constantPool().addMethodref(classname, "append", desc);
      gen.emit(Bytecode.INVOKEVIRTUAL, -argumentType.variableSize()).add2(index); // StringBuffer.append

      getSource().createBCode(gen);

      // typed append
      argumentType = source.stringPromotion();
      desc = "(" + argumentType.typeDescriptor() + ")" + stringBuffer.typeDescriptor();
      index = gen.constantPool().addMethodref(classname, "append", desc);
      gen.emit(Bytecode.INVOKEVIRTUAL, -argumentType.variableSize()).add2(index); // StringBuffer.append

      // toString
      desc = "()" + type().typeDescriptor();
      index = gen.constantPool().addMethodref(classname, "toString", desc);
      gen.emit(Bytecode.INVOKEVIRTUAL, 0).add2(index); // StringBuffer.toString

      if (needsPush()) {
        getDest().createPushAssignmentResult(gen);
      }
      getDest().emitStore(gen);
    } else {
      super.createBCode(gen);
    }
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:505
   */
  public void createAssignOp(CodeGeneration gen, TypeDecl type)    { type.add(gen); }
  /**
   * @declaredat ASTNode:1
   */
  public AssignPlusExpr() {
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
  public AssignPlusExpr(Expr p0, Expr p1) {
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
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:38
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:44
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:50
   */
  public AssignPlusExpr clone() throws CloneNotSupportedException {
    AssignPlusExpr node = (AssignPlusExpr) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public AssignPlusExpr copy() {
    try {
      AssignPlusExpr node = (AssignPlusExpr) clone();
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
   * @declaredat ASTNode:76
   */
  public AssignPlusExpr fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:85
   */
  public AssignPlusExpr treeCopyNoTransform() {
    AssignPlusExpr tree = (AssignPlusExpr) copy();
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
   * @declaredat ASTNode:105
   */
  public AssignPlusExpr treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:112
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Dest child.
   * @param node The new node to replace the Dest child.
   * @apilevel high-level
   */
  public void setDest(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Dest child.
   * @return The current node used as the Dest child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Dest")
  public Expr getDest() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Dest child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Dest child.
   * @apilevel low-level
   */
  public Expr getDestNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the Source child.
   * @param node The new node to replace the Source child.
   * @apilevel high-level
   */
  public void setSource(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Source child.
   * @return The current node used as the Source child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Source")
  public Expr getSource() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the Source child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Source child.
   * @apilevel low-level
   */
  public Expr getSourceNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  @ASTNodeAnnotation.Attribute
  public String printOp() {
    ASTNode$State state = state();
    String printOp_value = "+=";

    return printOp_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
