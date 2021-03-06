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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\grammar\\Annotations.ast:11
 * @production ElementConstantValue : {@link ElementValue} ::= <span class="component">{@link Expr}</span>;

 */
public class ElementConstantValue extends ElementValue implements Cloneable {
  /**
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:214
   */
  public void nameCheck() {
    if (enclosingAnnotationDecl().fullName().equals("java.lang.annotation.Target")) {
      Variable v = getExpr().varDecl();
      if (v != null && v.hostType().fullName().equals("java.lang.annotation.ElementType")) {
        if (lookupElementTypeValue(v.name()) != this) {
          error("repeated annotation target");
        }
      }
    }
  }
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\PrettyPrint.jadd:325
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getExpr());
  }
  /**
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:256
   */
  public void appendAsAttributeTo(Attribute buf) {
    if (getExpr().isConstant() && !getExpr().type().isEnumDecl()) {
      TypeDecl targetType = declType();
      char tag = targetType.isString() ? 's' : targetType.typeDescriptor().charAt(0);
      int const_value_index = targetType.addAnnotConstant(hostType().constantPool(), getExpr().constant());
      buf.u1(tag);
      buf.u2(const_value_index);
    } else if (getExpr().isClassAccess()) {
      int const_class_index = hostType().constantPool().addUtf8(getExpr().classAccess().typeDescriptor());
      buf.u1('c');
      buf.u2(const_class_index);
    } else {
      Variable v = getExpr().varDecl();
      if (v == null) {
        throw new Error("Expected Enumeration constant");
      }

      int type_name_index = hostType().constantPool().addUtf8(v.type().typeDescriptor());
      int const_name_index = hostType().constantPool().addUtf8(v.name());
      buf.u1('e');
      buf.u2(type_name_index);
      buf.u2(const_name_index);
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ElementConstantValue() {
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
  public ElementConstantValue(Expr p0) {
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
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:37
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:43
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:49
   */
  public ElementConstantValue clone() throws CloneNotSupportedException {
    ElementConstantValue node = (ElementConstantValue) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:56
   */
  public ElementConstantValue copy() {
    try {
      ElementConstantValue node = (ElementConstantValue) clone();
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
   * @declaredat ASTNode:75
   */
  public ElementConstantValue fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:84
   */
  public ElementConstantValue treeCopyNoTransform() {
    ElementConstantValue tree = (ElementConstantValue) copy();
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
   * @declaredat ASTNode:104
   */
  public ElementConstantValue treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:111
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:80
   */
  @ASTNodeAnnotation.Attribute
  public boolean validTarget(Annotation a) {
    ASTNode$State state = state();
    try {
        Variable v = getExpr().varDecl();
        if (v == null) {
          return true;
        }
        return v.hostType().fullName().equals("java.lang.annotation.ElementType") && a.mayUseAnnotationTarget(v.name());
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:228
   */
  @ASTNodeAnnotation.Attribute
  public ElementValue definesElementTypeValue(String name) {
    ASTNode$State state = state();
    try {
        Variable v = getExpr().varDecl();
        if (v != null && v.hostType().fullName().equals("java.lang.annotation.ElementType") && v.name().equals(name)) {
          return this;
        }
        return null;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasValue(String annot) {
    ASTNode$State state = state();
    boolean hasValue_String_value = getExpr().type().isString() && getExpr().isConstant()
          && getExpr().constant().stringValue().equals(annot);

    return hasValue_String_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:558
   */
  @ASTNodeAnnotation.Attribute
  public boolean commensurateWithTypeDecl(TypeDecl type) {
    ASTNode$State state = state();
    try {
        Expr v = getExpr();
        if (!v.type().assignConversionTo(type, v)) {
          return false;
        }
        if ((type.isPrimitive() || type.isString()) && !v.isConstant()) {
          return false;
        }
        if (v.type().isNull()) {
          return false;
        }
        if (type.fullName().equals("java.lang.Class") && !v.isClassAccess()) {
          return false;
        }
        if (type.isEnumDecl() && (v.varDecl() == null || !(v.varDecl() instanceof EnumConstant))) {
          return false;
        }
        return true;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    ASTNode$State state = state();
    TypeDecl type_value = getExpr().type();

    return type_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:224
   */
  @ASTNodeAnnotation.Attribute
  public ElementValue lookupElementTypeValue(String name) {
    ASTNode$State state = state();
    ElementValue lookupElementTypeValue_String_value = getParent().Define_ElementValue_lookupElementTypeValue(this, null, name);

    return lookupElementTypeValue_String_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:647
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return NameType.EXPRESSION_NAME;
    }
    else {
      return getParent().Define_NameType_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:652
   * @apilevel internal
   */
  public String Define_String_methodHost(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return enclosingAnnotationDecl().typeName();
    }
    else {
      return getParent().Define_String_methodHost(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
