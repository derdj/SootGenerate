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
 * Represents a Java boolean; either "true" or "false".
 * @ast node
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\grammar\\Literals.ast:23
 * @production BooleanLiteral : {@link Literal};

 */
public class BooleanLiteral extends Literal implements Cloneable {
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NodeConstructors.jrag:63
   */
  public BooleanLiteral(boolean b) {
    this(b ? "true" : "false");
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:165
   */
  public static void push(CodeGeneration gen, boolean value) {
    gen.emit(value ? Bytecode.ICONST_1 : Bytecode.ICONST_0);
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:209
   */
  public void emitPushConstant(CodeGeneration gen) {
    BooleanLiteral.push(gen, constant().booleanValue());
  }
  /**
   * @declaredat ASTNode:1
   */
  public BooleanLiteral() {
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
   * @declaredat ASTNode:12
   */
  public BooleanLiteral(String p0) {
    setLITERAL(p0);
  }
  /**
   * @declaredat ASTNode:15
   */
  public BooleanLiteral(beaver.Symbol p0) {
    setLITERAL(p0);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 0;
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
    constant_reset();
    type_reset();
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
  public BooleanLiteral clone() throws CloneNotSupportedException {
    BooleanLiteral node = (BooleanLiteral) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public BooleanLiteral copy() {
    try {
      BooleanLiteral node = (BooleanLiteral) clone();
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
  public BooleanLiteral fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:88
   */
  public BooleanLiteral treeCopyNoTransform() {
    BooleanLiteral tree = (BooleanLiteral) copy();
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
  public BooleanLiteral treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:115
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_LITERAL == ((BooleanLiteral)node).tokenString_LITERAL);    
  }
  /**
   * Replaces the lexeme LITERAL.
   * @param value The new value for the lexeme LITERAL.
   * @apilevel high-level
   */
  public void setLITERAL(String value) {
    tokenString_LITERAL = value;
  }
  /**
   * JastAdd-internal setter for lexeme LITERAL using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme LITERAL
   * @apilevel internal
   */
  public void setLITERAL(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setLITERAL is only valid for String lexemes");
    tokenString_LITERAL = (String)symbol.value;
    LITERALstart = symbol.getStart();
    LITERALend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme LITERAL.
   * @return The value for the lexeme LITERAL.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="LITERAL")
  public String getLITERAL() {
    return tokenString_LITERAL != null ? tokenString_LITERAL : "";
  }
  /**
   * @apilevel internal
   */
  protected boolean constant_computed = false;
  /**
   * @apilevel internal
   */
  protected Constant constant_value;
  /**
   * @apilevel internal
   */
  private void constant_reset() {
    constant_computed = false;
    constant_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Constant constant() {
    if(constant_computed) {
      return constant_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    constant_value = Constant.create(Boolean.valueOf(getLITERAL()).booleanValue());
    if (isFinal && num == state().boundariesCrossed) {
      constant_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return constant_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isTrue() {
    ASTNode$State state = state();
    boolean isTrue_value = constant().booleanValue();

    return isTrue_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFalse() {
    ASTNode$State state = state();
    boolean isFalse_value = !constant().booleanValue();

    return isFalse_value;
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
    type_value = typeBoolean();
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
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
