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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:53
 * @production ByteType : {@link IntegralType};

 */
public class ByteType extends IntegralType implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PrettyPrint.jadd:101
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("byte");
  }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:585
   */
  void emitCastTo(CodeGeneration gen, TypeDecl type)     { type.byteToThis(gen); }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:596
   */
  void intToThis(CodeGeneration gen)   { gen.emit(Bytecode.I2B); }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:602
   */
  void floatToThis(CodeGeneration gen)   { gen.emit(Bytecode.F2I).emit(Bytecode.I2B); }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:611
   */
  void doubleToThis(CodeGeneration gen)   { gen.emit(Bytecode.D2I).emit(Bytecode.I2B); }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:620
   */
  void longToThis(CodeGeneration gen)   { gen.emit(Bytecode.L2I).emit(Bytecode.I2B); }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:636
   */
  void charToThis(CodeGeneration gen)     { gen.emit(Bytecode.I2B); }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:644
   */
  void shortToThis(CodeGeneration gen)     { gen.emit(Bytecode.I2B); }
  /**
   * @declaredat ASTNode:1
   */
  public ByteType() {
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
    setChild(new Opt(), 1);
    setChild(new List(), 2);
  }
  /**
   * @declaredat ASTNode:15
   */
  public ByteType(Modifiers p0, String p1, Opt<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:21
   */
  public ByteType(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<BodyDecl> p3) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:30
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:36
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:42
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    narrowingConversionTo_TypeDecl_reset();
    unaryNumericPromotion_reset();
    typeDescriptor_reset();
    jvmName_reset();
    boxed_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:53
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:59
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:65
   */
  public ByteType clone() throws CloneNotSupportedException {
    ByteType node = (ByteType) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:72
   */
  public ByteType copy() {
    try {
      ByteType node = (ByteType) clone();
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
   * @declaredat ASTNode:91
   */
  public ByteType fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:100
   */
  public ByteType treeCopyNoTransform() {
    ByteType tree = (ByteType) copy();
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
   * @declaredat ASTNode:120
   */
  public ByteType treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:127
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ByteType)node).tokenString_ID);    
  }
  /**
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Modifiers")
  public Modifiers getModifiers() {
    return (Modifiers) getChild(0);
  }
  /**
   * Retrieves the Modifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Modifiers child.
   * @apilevel low-level
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers) getChildNoTransform(0);
  }
  /**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @param symbol Symbol containing the new value for the lexeme ID
   * @apilevel internal
   */
  public void setID(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String))
    throw new UnsupportedOperationException("setID is only valid for String lexemes");
    tokenString_ID = (String)symbol.value;
    IDstart = symbol.getStart();
    IDend = symbol.getEnd();
  }
  /**
   * Retrieves the value for the lexeme ID.
   * @return The value for the lexeme ID.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="ID")
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
  /**
   * Replaces the optional node for the SuperClass child. This is the <code>Opt</code>
   * node containing the child SuperClass, not the actual child!
   * @param opt The new node to be used as the optional node for the SuperClass child.
   * @apilevel low-level
   */
  public void setSuperClassOpt(Opt<Access> opt) {
    setChild(opt, 1);
  }
  /**
   * Replaces the (optional) SuperClass child.
   * @param node The new node to be used as the SuperClass child.
   * @apilevel high-level
   */
  public void setSuperClass(Access node) {
    getSuperClassOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional SuperClass child exists.
   * @return {@code true} if the optional SuperClass child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasSuperClass() {
    return getSuperClassOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) SuperClass child.
   * @return The SuperClass child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Access getSuperClass() {
    return (Access) getSuperClassOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the SuperClass child. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * @return The optional node for child the SuperClass child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="SuperClass")
  public Opt<Access> getSuperClassOpt() {
    return (Opt<Access>) getChild(1);
  }
  /**
   * Retrieves the optional node for child SuperClass. This is the <code>Opt</code> node containing the child SuperClass, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child SuperClass.
   * @apilevel low-level
   */
  public Opt<Access> getSuperClassOptNoTransform() {
    return (Opt<Access>) getChildNoTransform(1);
  }
  /**
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * @return Number of children in the BodyDecl list.
   * @apilevel high-level
   */
  public int getNumBodyDecl() {
    return getBodyDeclList().getNumChild();
  }
  /**
   * Retrieves the number of children in the BodyDecl list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the BodyDecl list.
   * @apilevel low-level
   */
  public int getNumBodyDeclNoTransform() {
    return getBodyDeclListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the BodyDecl list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the BodyDecl list.
   * @apilevel high-level
   */
  public BodyDecl getBodyDecl(int i) {
    return (BodyDecl) getBodyDeclList().getChild(i);
  }
  /**
   * Check whether the BodyDecl list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasBodyDecl() {
    return getBodyDeclList().getNumChild() != 0;
  }
  /**
   * Append an element to the BodyDecl list.
   * @param node The element to append to the BodyDecl list.
   * @apilevel high-level
   */
  public void addBodyDecl(BodyDecl node) {
    List<BodyDecl> list = (parent == null || state == null) ? getBodyDeclListNoTransform() : getBodyDeclList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addBodyDeclNoTransform(BodyDecl node) {
    List<BodyDecl> list = getBodyDeclListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the BodyDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setBodyDecl(BodyDecl node, int i) {
    List<BodyDecl> list = getBodyDeclList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="BodyDecl")
  public List<BodyDecl> getBodyDeclList() {
    List<BodyDecl> list = (List<BodyDecl>) getChild(2);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclListNoTransform() {
    return (List<BodyDecl>) getChildNoTransform(2);
  }
  /**
   * Retrieves the BodyDecl list.
   * @return The node representing the BodyDecl list.
   * @apilevel high-level
   */
  public List<BodyDecl> getBodyDecls() {
    return getBodyDeclList();
  }
  /**
   * Retrieves the BodyDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the BodyDecl list.
   * @apilevel low-level
   */
  public List<BodyDecl> getBodyDeclsNoTransform() {
    return getBodyDeclListNoTransform();
  }
  @ASTNodeAnnotation.Attribute
  public Constant cast(Constant c) {
    ASTNode$State state = state();
    Constant cast_Constant_value = Constant.create((byte) c.intValue());

    return cast_Constant_value;
  }
  protected java.util.Map narrowingConversionTo_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void narrowingConversionTo_TypeDecl_reset() {
    narrowingConversionTo_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean narrowingConversionTo(TypeDecl type) {
    Object _parameters = type;
    if (narrowingConversionTo_TypeDecl_values == null) narrowingConversionTo_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(narrowingConversionTo_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)narrowingConversionTo_TypeDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean narrowingConversionTo_TypeDecl_value = type.isChar();
    if (isFinal && num == state().boundariesCrossed) {
      narrowingConversionTo_TypeDecl_values.put(_parameters, Boolean.valueOf(narrowingConversionTo_TypeDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return narrowingConversionTo_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean unaryNumericPromotion_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl unaryNumericPromotion_value;
  /**
   * @apilevel internal
   */
  private void unaryNumericPromotion_reset() {
    unaryNumericPromotion_computed = false;
    unaryNumericPromotion_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl unaryNumericPromotion() {
    if(unaryNumericPromotion_computed) {
      return unaryNumericPromotion_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    unaryNumericPromotion_value = typeInt();
    if (isFinal && num == state().boundariesCrossed) {
      unaryNumericPromotion_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return unaryNumericPromotion_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isByte() {
    ASTNode$State state = state();
    boolean isByte_value = true;

    return isByte_value;
  }
  @ASTNodeAnnotation.Attribute
  public byte arrayLoad() {
    ASTNode$State state = state();
    byte arrayLoad_value = Bytecode.BALOAD;

    return arrayLoad_value;
  }
  @ASTNodeAnnotation.Attribute
  public byte arrayStore() {
    ASTNode$State state = state();
    byte arrayStore_value = Bytecode.BASTORE;

    return arrayStore_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeDescriptor_computed = false;
  /**
   * @apilevel internal
   */
  protected String typeDescriptor_value;
  /**
   * @apilevel internal
   */
  private void typeDescriptor_reset() {
    typeDescriptor_computed = false;
    typeDescriptor_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String typeDescriptor() {
    if(typeDescriptor_computed) {
      return typeDescriptor_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeDescriptor_value = "B";
    if (isFinal && num == state().boundariesCrossed) {
      typeDescriptor_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeDescriptor_value;
  }
  @ASTNodeAnnotation.Attribute
  public int arrayPrimitiveTypeDescriptor() {
    ASTNode$State state = state();
    int arrayPrimitiveTypeDescriptor_value = 8;

    return arrayPrimitiveTypeDescriptor_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl stringPromotion() {
    ASTNode$State state = state();
    TypeDecl stringPromotion_value = typeInt();

    return stringPromotion_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean jvmName_computed = false;
  /**
   * @apilevel internal
   */
  protected String jvmName_value;
  /**
   * @apilevel internal
   */
  private void jvmName_reset() {
    jvmName_computed = false;
    jvmName_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String jvmName() {
    if(jvmName_computed) {
      return jvmName_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    jvmName_value = "B";
    if (isFinal && num == state().boundariesCrossed) {
      jvmName_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return jvmName_value;
  }
  @ASTNodeAnnotation.Attribute
  public String primitiveClassName() {
    ASTNode$State state = state();
    String primitiveClassName_value = "Byte";

    return primitiveClassName_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean boxed_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl boxed_value;
  /**
   * @apilevel internal
   */
  private void boxed_reset() {
    boxed_computed = false;
    boxed_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl boxed() {
    if(boxed_computed) {
      return boxed_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boxed_value = lookupType("java.lang", "Byte");
    if (isFinal && num == state().boundariesCrossed) {
      boxed_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return boxed_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
