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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:95
 * @production MemberTypeDecl : {@link MemberDecl};

 */
public abstract class MemberTypeDecl extends MemberDecl implements Cloneable {
  /**
   * @declaredat ASTNode:1
   */
  public MemberTypeDecl() {
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
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:39
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:45
   */
  public MemberTypeDecl clone() throws CloneNotSupportedException {
    MemberTypeDecl node = (MemberTypeDecl) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:56
   */
  public abstract MemberTypeDecl fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:63
   */
  public abstract MemberTypeDecl treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:71
   */
  public abstract MemberTypeDecl treeCopy();
  /**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupType.jrag:604
   */
  @ASTNodeAnnotation.Attribute
  public abstract TypeDecl typeDecl();
  @ASTNodeAnnotation.Attribute
  public boolean declaresType(String name) {
    ASTNode$State state = state();
    boolean declaresType_String_value = typeDecl().name().equals(name);

    return declaresType_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type(String name) {
    ASTNode$State state = state();
    TypeDecl type_String_value = declaresType(name) ? typeDecl() : null;

    return type_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStatic() {
    ASTNode$State state = state();
    boolean isStatic_value = typeDecl().isStatic();

    return isStatic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSuppressWarnings(String annot) {
    ASTNode$State state = state();
    boolean hasAnnotationSuppressWarnings_String_value = typeDecl().hasAnnotationSuppressWarnings(annot);

    return hasAnnotationSuppressWarnings_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDeprecated() {
    ASTNode$State state = state();
    boolean isDeprecated_value = typeDecl().isDeprecated();

    return isDeprecated_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean visibleTypeParameters() {
    ASTNode$State state = state();
    boolean visibleTypeParameters_value = !isStatic();

    return visibleTypeParameters_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSafeVarargs() {
    ASTNode$State state = state();
    boolean hasAnnotationSafeVarargs_value = typeDecl().hasAnnotationSafeVarargs();

    return hasAnnotationSafeVarargs_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
