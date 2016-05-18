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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:41
 * @production ReferenceType : {@link TypeDecl};

 */
public abstract class ReferenceType extends TypeDecl implements Cloneable {
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:246
   */
  public void emitReturn(CodeGeneration gen) { gen.emit(Bytecode.ARETURN);}
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:327
   */
  public void emitLoadLocal(CodeGeneration gen, int pos) {
    gen.emitLoadReference(pos);
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:470
   */
  public void emitStoreLocal(CodeGeneration gen, int pos) {
    gen.emitStoreReference(pos);
  }
  /**
   * @aspect CodeGenerationConversions
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:580
   */
  void refined_CodeGenerationConversions_ReferenceType_emitCastTo(CodeGeneration gen, TypeDecl type) { if (!instanceOf(type) && !type.isNull()) gen.emitCheckCast(type); }
  /**
   * @aspect CodeGenerationBranch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:793
   */
  public void branchEQ(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ACMPEQ, label); }
  /**
   * @aspect CodeGenerationBranch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:802
   */
  public void branchNE(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ACMPNE, label); }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:38
   */
  void byteToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeByte().boxed().byteToThis(gen);
    } else {
      unboxed().byteToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:49
   */
  void charToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeChar().boxed().charToThis(gen);
    } else {
      unboxed().charToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:60
   */
  void shortToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeShort().boxed().shortToThis(gen);
    } else {
      unboxed().shortToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:71
   */
  void intToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeInt().boxed().intToThis(gen);
    } else {
      unboxed().intToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:82
   */
  void longToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeLong().boxed().longToThis(gen);
    } else {
      unboxed().longToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:93
   */
  void floatToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeFloat().boxed().floatToThis(gen);
    } else {
      unboxed().floatToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:104
   */
  void doubleToThis(CodeGeneration gen) {
    if (isUnknown()) {
      throw new Error("Trying to cast to Unknown");
    }
    if (!isNumericType()) {
      typeDouble().boxed().doubleToThis(gen);
    } else {
      unboxed().doubleToThis(gen);
      emitBoxingOperation(gen);
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public ReferenceType() {
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
    setChild(new List(), 1);
  }
  /**
   * @declaredat ASTNode:14
   */
  public ReferenceType(Modifiers p0, String p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:19
   */
  public ReferenceType(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:27
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:39
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    narrowingConversionTo_TypeDecl_reset();
    bcFields_reset();
    jvmName_reset();
    unboxed_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:49
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:55
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:61
   */
  public ReferenceType clone() throws CloneNotSupportedException {
    ReferenceType node = (ReferenceType) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:72
   */
  public abstract ReferenceType fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:79
   */
  public abstract ReferenceType treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:87
   */
  public abstract ReferenceType treeCopy();
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
   * Replaces the BodyDecl list.
   * @param list The new list node to be used as the BodyDecl list.
   * @apilevel high-level
   */
  public void setBodyDeclList(List<BodyDecl> list) {
    setChild(list, 1);
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
    List<BodyDecl> list = (List<BodyDecl>) getChild(1);
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
    return (List<BodyDecl>) getChildNoTransform(1);
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
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:125
   */
    public void emitCastTo(CodeGeneration gen, TypeDecl type) {
    if (type instanceof PrimitiveType) {
      emitUnboxingOperation(gen);
      unboxed().emitCastTo(gen, type);
    } else {
      refined_CodeGenerationConversions_ReferenceType_emitCastTo(gen, type);
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean wideningConversionTo(TypeDecl type) {
    ASTNode$State state = state();
    boolean wideningConversionTo_TypeDecl_value = instanceOf(type);

    return wideningConversionTo_TypeDecl_value;
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
    boolean narrowingConversionTo_TypeDecl_value = narrowingConversionTo_compute(type);
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
  private boolean narrowingConversionTo_compute(TypeDecl type) {
      if (type.instanceOf(this)) {
        return true;
      }
      if (isClassDecl() && !getModifiers().isFinal() && type.isInterfaceDecl()) {
        return true;
      }
      if (isInterfaceDecl() && type.isClassDecl() && !type.getModifiers().isFinal()) {
        return true;
      }
      if (isInterfaceDecl() && type.instanceOf(this)) {
        return true;
      }
      if (fullName().equals("java.lang.Object") && type.isInterfaceDecl()) {
        return true;
      }
      // Dragons
      // TODO: Check if both are interfaces with compatible methods
      if (isArrayDecl() && type.isArrayDecl() && elementType().instanceOf(type.elementType())) {
        return true;
      }
      return false;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isReferenceType() {
    ASTNode$State state = state();
    boolean isReferenceType_value = true;

    return isReferenceType_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSupertypeOfNullType(NullType type) {
    ASTNode$State state = state();
    boolean isSupertypeOfNullType_NullType_value = true;

    return isSupertypeOfNullType_NullType_value;
  }
  @ASTNodeAnnotation.Attribute
  public byte arrayLoad() {
    ASTNode$State state = state();
    byte arrayLoad_value = Bytecode.AALOAD;

    return arrayLoad_value;
  }
  @ASTNodeAnnotation.Attribute
  public byte arrayStore() {
    ASTNode$State state = state();
    byte arrayStore_value = Bytecode.AASTORE;

    return arrayStore_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean bcFields_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection bcFields_value;
  /**
   * @apilevel internal
   */
  private void bcFields_reset() {
    bcFields_computed = false;
    bcFields_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection bcFields() {
    if(bcFields_computed) {
      return bcFields_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    bcFields_value = bcFields_compute();
    if (isFinal && num == state().boundariesCrossed) {
      bcFields_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return bcFields_value;
  }
  /**
   * @apilevel internal
   */
  private Collection bcFields_compute() {
      ArrayList l = new ArrayList();
      for (int i = 0; i < getNumBodyDecl(); i++) {
        if (getBodyDecl(i).isBytecodeField()) {
          l.add(getBodyDecl(i));
        }
      }
      return l;
    }
  /**
   * @attribute syn
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:328
   */
  @ASTNodeAnnotation.Attribute
  public Collection bcMethods() {
    ASTNode$State state = state();
    try {
        ArrayList l = new ArrayList();
        for (int i = 0; i < getNumBodyDecl(); i++) {
          if (getBodyDecl(i).isBytecodeMethod()) {
            l.add(getBodyDecl(i));
          }
        }
        return l;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl stringPromotion() {
    ASTNode$State state = state();
    TypeDecl stringPromotion_value = typeObject();

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
    jvmName_value = jvmName_compute();
    if (isFinal && num == state().boundariesCrossed) {
      jvmName_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return jvmName_value;
  }
  /**
   * @apilevel internal
   */
  private String jvmName_compute() {
      if (!isNestedType()) {
        return fullName();
      } else if (isAnonymous() || isLocalClass()) {
        return enclosingType().jvmName() + "$" + uniqueIndex() + name();
      } else {
        return enclosingType().jvmName() + "$" + name();
      }
    }
  @ASTNodeAnnotation.Attribute
  public String referenceClassFieldName() {
    ASTNode$State state = state();
    String referenceClassFieldName_value = "class$" + jvmName().replace('[', '$').replace('.', '$').replace(';', ' ').trim();

    return referenceClassFieldName_value;
  }
  @ASTNodeAnnotation.Attribute
  public int variableSize() {
    ASTNode$State state = state();
    int variableSize_value = 1;

    return variableSize_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:151
   */
  @ASTNodeAnnotation.Attribute
  public boolean isValidAnnotationMethodReturnType() {
    ASTNode$State state = state();
    try {
        if (isString()) {
          return true;
        }
        if (fullName().equals("java.lang.Class")) {
          return true;
        }
        // include generic versions of Class
        if (erasure().fullName().equals("java.lang.Class")) {
          return true;
        }
        return false;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean unboxingConversionTo(TypeDecl typeDecl) {
    ASTNode$State state = state();
    boolean unboxingConversionTo_TypeDecl_value = unboxed() == typeDecl;

    return unboxingConversionTo_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean unboxed_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl unboxed_value;
  /**
   * @apilevel internal
   */
  private void unboxed_reset() {
    unboxed_computed = false;
    unboxed_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl unboxed() {
    if(unboxed_computed) {
      return unboxed_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    unboxed_value = unboxed_compute();
    if (isFinal && num == state().boundariesCrossed) {
      unboxed_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return unboxed_value;
  }
  /**
   * @apilevel internal
   */
  private TypeDecl unboxed_compute() {
      if (packageName().equals("java.lang") && isTopLevelType()) {
        String n = name();
        if (n.equals("Boolean")) {
          return typeBoolean();
        }
        if (n.equals("Byte")) {
          return typeByte();
        }
        if (n.equals("Character")) {
          return typeChar();
        }
        if (n.equals("Short")) {
          return typeShort();
        }
        if (n.equals("Integer")) {
          return typeInt();
        }
        if (n.equals("Long")) {
          return typeLong();
        }
        if (n.equals("Float")) {
          return typeFloat();
        }
        if (n.equals("Double")) {
          return typeDouble();
        }
      }
      return unknownType();
    }
  @ASTNodeAnnotation.Attribute
  public TypeDecl unaryNumericPromotion() {
    ASTNode$State state = state();
    TypeDecl unaryNumericPromotion_value = isNumericType() && !isUnknown() ? unboxed().unaryNumericPromotion() : this;

    return unaryNumericPromotion_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl binaryNumericPromotion(TypeDecl type) {
    ASTNode$State state = state();
    TypeDecl binaryNumericPromotion_TypeDecl_value = unboxed().binaryNumericPromotion(type);

    return binaryNumericPromotion_TypeDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isNumericType() {
    ASTNode$State state = state();
    boolean isNumericType_value = !unboxed().isUnknown() && unboxed().isNumericType();

    return isNumericType_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isIntegralType() {
    ASTNode$State state = state();
    boolean isIntegralType_value = !unboxed().isUnknown() && unboxed().isIntegralType();

    return isIntegralType_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPrimitive() {
    ASTNode$State state = state();
    boolean isPrimitive_value = !unboxed().isUnknown() && unboxed().isPrimitive();

    return isPrimitive_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBoolean() {
    ASTNode$State state = state();
    boolean isBoolean_value = fullName().equals("java.lang.Boolean") && unboxed().isBoolean();

    return isBoolean_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean supertypeNullType(NullType type) {
    ASTNode$State state = state();
    boolean supertypeNullType_NullType_value = true;

    return supertypeNullType_NullType_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean strictSupertypeNullType(NullType type) {
    ASTNode$State state = state();
    boolean strictSupertypeNullType_NullType_value = true;

    return strictSupertypeNullType_NullType_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:103
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeBoolean() {
    ASTNode$State state = state();
    TypeDecl typeBoolean_value = getParent().Define_TypeDecl_typeBoolean(this, null);

    return typeBoolean_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:104
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeByte() {
    ASTNode$State state = state();
    TypeDecl typeByte_value = getParent().Define_TypeDecl_typeByte(this, null);

    return typeByte_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:105
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeChar() {
    ASTNode$State state = state();
    TypeDecl typeChar_value = getParent().Define_TypeDecl_typeChar(this, null);

    return typeChar_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:106
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeShort() {
    ASTNode$State state = state();
    TypeDecl typeShort_value = getParent().Define_TypeDecl_typeShort(this, null);

    return typeShort_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:107
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeInt() {
    ASTNode$State state = state();
    TypeDecl typeInt_value = getParent().Define_TypeDecl_typeInt(this, null);

    return typeInt_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:108
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeLong() {
    ASTNode$State state = state();
    TypeDecl typeLong_value = getParent().Define_TypeDecl_typeLong(this, null);

    return typeLong_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:109
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeFloat() {
    ASTNode$State state = state();
    TypeDecl typeFloat_value = getParent().Define_TypeDecl_typeFloat(this, null);

    return typeFloat_value;
  }
  /**
   * @attribute inh
   * @aspect AutoBoxing
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\AutoBoxing.jrag:110
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeDouble() {
    ASTNode$State state = state();
    TypeDecl typeDouble_value = getParent().Define_TypeDecl_typeDouble(this, null);

    return typeDouble_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
