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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:13
 * @production Access : {@link Expr};

 */
public abstract class Access extends Expr implements Cloneable {
  /**
   * @aspect QualifiedNames
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ResolveAmbiguousNames.jrag:181
   */
  public Access addArrayDims(List list) {
    Access a = this;
    for (int i = 0; i < list.getNumChildNoTransform(); i++) {
      Dims dims = (Dims) list.getChildNoTransform(i);
      Opt opt = dims.getExprOpt();
      if (opt.getNumChildNoTransform() == 1) {
        a = new ArrayTypeWithSizeAccess(a, (Expr) opt.getChildNoTransform(0));
      } else {
        a = new ArrayTypeAccess(a);
      }
      a.setStart(dims.start());
      a.setEnd(dims.end());
    }
    return a;
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:562
   */
  public void emitLoadLocalInNestedClass(CodeGeneration gen, Variable v) {
    if (inExplicitConstructorInvocation() && enclosingBodyDecl() instanceof ConstructorDecl) {
      ConstructorDecl c = (ConstructorDecl) enclosingBodyDecl();
      v.type().emitLoadLocal(gen, c.localIndexOfEnclosingVariable(v));
    } else {
      String classname = hostType().constantPoolName();
      String      desc = v.type().typeDescriptor();
      String      name = "val$" + v.name();
      int index = gen.constantPool().addFieldref(classname, name, desc);
      gen.emit(Bytecode.ALOAD_0);
      gen.emit(Bytecode.GETFIELD, v.type().variableSize() - 1).add2(index);
    }
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:667
   */
  public void emitThis(CodeGeneration gen, TypeDecl targetDecl) {
    if (targetDecl == hostType()) {
      gen.emit(Bytecode.ALOAD_0);
    } else {
      TypeDecl enclosing = hostType();
      if (inExplicitConstructorInvocation()) {
        gen.emit(Bytecode.ALOAD_1);
        enclosing = enclosing.enclosing();
      } else {
        gen.emit(Bytecode.ALOAD_0);
      }
      while (enclosing != targetDecl) {
        String classname = enclosing.constantPoolName();
        enclosing = enclosing.enclosingType();
        String desc = enclosing.typeDescriptor();
        int index = gen.constantPool().addFieldref(classname, "this$0", desc);
        gen.emit(Bytecode.GETFIELD, 0).add2(index);
      }
    }
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:139
   */
  protected TypeDecl superConstructorQualifier(TypeDecl targetEnclosingType) {
    TypeDecl enclosing = hostType();
    while (!enclosing.instanceOf(targetEnclosingType)) {
      enclosing = enclosing.enclosingType();
    }
    return enclosing;
  }
  /**
   * @aspect FunctionalInterface
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\FunctionalInterface.jrag:174
   */
  public boolean sameType(Access a) {
    if (this instanceof ArrayTypeAccess && a instanceof ArrayTypeAccess) {
      ArrayTypeAccess at1 = (ArrayTypeAccess) this;
      ArrayTypeAccess at2 = (ArrayTypeAccess) a;
      return at1.sameType(at2);
    } else if (this instanceof AbstractWildcard && a instanceof AbstractWildcard) {
      AbstractWildcard w1 = (AbstractWildcard) this;
      AbstractWildcard w2 = (AbstractWildcard) a;
      return w1.sameType(w2);
    } else if (this instanceof TypeAccess && a instanceof TypeAccess) {
      TypeAccess t1 = (TypeAccess) this;
      TypeAccess t2 = (TypeAccess) a;
      return t1.sameType(t2);
    } else if (this instanceof ParTypeAccess && a instanceof ParTypeAccess) {
      ParTypeAccess pta1 = (ParTypeAccess) this;
      ParTypeAccess pta2 = (ParTypeAccess) a;
      return pta1.sameType(pta2);
    } else {
      return false;
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public Access() {
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
    prevExpr_reset();
    hasPrevExpr_reset();
    type_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:36
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:42
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:48
   */
  public Access clone() throws CloneNotSupportedException {
    Access node = (Access) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:59
   */
  public abstract Access fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:66
   */
  public abstract Access treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:74
   */
  public abstract Access treeCopy();
  @ASTNodeAnnotation.Attribute
  public Expr unqualifiedScope() {
    ASTNode$State state = state();
    Expr unqualifiedScope_value = isQualified() ? nestedScope() : this;

    return unqualifiedScope_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isQualified() {
    ASTNode$State state = state();
    boolean isQualified_value = hasPrevExpr();

    return isQualified_value;
  }
  @ASTNodeAnnotation.Attribute
  public Expr qualifier() {
    ASTNode$State state = state();
    Expr qualifier_value = prevExpr();

    return qualifier_value;
  }
  @ASTNodeAnnotation.Attribute
  public Access lastAccess() {
    ASTNode$State state = state();
    Access lastAccess_value = this;

    return lastAccess_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean prevExpr_computed = false;
  /**
   * @apilevel internal
   */
  protected Expr prevExpr_value;
  /**
   * @apilevel internal
   */
  private void prevExpr_reset() {
    prevExpr_computed = false;
    prevExpr_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Expr prevExpr() {
    if(prevExpr_computed) {
      return prevExpr_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    prevExpr_value = prevExpr_compute();
    if (isFinal && num == state().boundariesCrossed) {
      prevExpr_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return prevExpr_value;
  }
  /**
   * @apilevel internal
   */
  private Expr prevExpr_compute() {
      if (isLeftChildOfDot()) {
        if (parentDot().isRightChildOfDot()) {
          return parentDot().parentDot().leftSide();
        }
      } else if (isRightChildOfDot()) {
        return parentDot().leftSide();
      }
      throw new Error(this + " does not have a previous expression");
    }
  /**
   * @apilevel internal
   */
  protected boolean hasPrevExpr_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean hasPrevExpr_value;
  /**
   * @apilevel internal
   */
  private void hasPrevExpr_reset() {
    hasPrevExpr_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasPrevExpr() {
    if(hasPrevExpr_computed) {
      return hasPrevExpr_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    hasPrevExpr_value = hasPrevExpr_compute();
    if (isFinal && num == state().boundariesCrossed) {
      hasPrevExpr_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hasPrevExpr_value;
  }
  /**
   * @apilevel internal
   */
  private boolean hasPrevExpr_compute() {
      if (isLeftChildOfDot()) {
        if (parentDot().isRightChildOfDot()) {
          return true;
        }
      } else if (isRightChildOfDot()) {
        return true;
      }
      return false;
    }
  /**
   * Defines the expected kind of name for the left hand side in a qualified
   * expression.
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\SyntacticClassification.jrag:58
   */
  @ASTNodeAnnotation.Attribute
  public NameType predNameType() {
    ASTNode$State state = state();
    try {
        switch (nameType()) {
          case PACKAGE_NAME:
            return NameType.PACKAGE_NAME;
          case TYPE_NAME:
          case PACKAGE_OR_TYPE_NAME:
            return NameType.PACKAGE_OR_TYPE_NAME;
          case AMBIGUOUS_NAME:
          case EXPRESSION_NAME:
            return NameType.AMBIGUOUS_NAME;
          case NOT_CLASSIFIED:
          default:
            return NameType.NOT_CLASSIFIED;
        }
      }
    finally {
    }
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
    type_value = unknownType();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public int sourceLineNumber() {
    ASTNode$State state = state();
    int sourceLineNumber_value = findFirstSourceLineNumber();

    return sourceLineNumber_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isWildcard() {
    ASTNode$State state = state();
    boolean isWildcard_value = false;

    return isWildcard_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDiamond() {
    ASTNode$State state = state();
    boolean isDiamond_value = false;

    return isDiamond_value;
  }
  @ASTNodeAnnotation.Attribute
  public Access substituted(Collection<TypeVariable> original, List<TypeVariable> substitution) {
    ASTNode$State state = state();
    Access substituted_Collection_TypeVariable__List_TypeVariable__value = (Access) treeCopyNoTransform();

    return substituted_Collection_TypeVariable__List_TypeVariable__value;
  }
  /**
   * @attribute inh
   * @aspect LookupMethod
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:40
   */
  @ASTNodeAnnotation.Attribute
  public Expr nestedScope() {
    ASTNode$State state = state();
    Expr nestedScope_value = getParent().Define_Expr_nestedScope(this, null);

    return nestedScope_value;
  }
  /**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupType.jrag:289
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl unknownType() {
    ASTNode$State state = state();
    TypeDecl unknownType_value = getParent().Define_TypeDecl_unknownType(this, null);

    return unknownType_value;
  }
  /**
   * @attribute inh
   * @aspect VariableScopePropagation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupVariable.jrag:334
   */
  @ASTNodeAnnotation.Attribute
  public Variable unknownField() {
    ASTNode$State state = state();
    Variable unknownField_value = getParent().Define_Variable_unknownField(this, null);

    return unknownField_value;
  }
  /**
   * @attribute inh
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:561
   */
  @ASTNodeAnnotation.Attribute
  public boolean inExplicitConstructorInvocation() {
    ASTNode$State state = state();
    boolean inExplicitConstructorInvocation_value = getParent().Define_boolean_inExplicitConstructorInvocation(this, null);

    return inExplicitConstructorInvocation_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:340
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinSuppressWarnings(String annot) {
    ASTNode$State state = state();
    boolean withinSuppressWarnings_String_value = getParent().Define_boolean_withinSuppressWarnings(this, null, annot);

    return withinSuppressWarnings_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:448
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinDeprecatedAnnotation() {
    ASTNode$State state = state();
    boolean withinDeprecatedAnnotation_value = getParent().Define_boolean_withinDeprecatedAnnotation(this, null);

    return withinDeprecatedAnnotation_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
