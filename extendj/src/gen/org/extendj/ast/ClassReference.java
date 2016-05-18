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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\grammar\\ConstructorReference.ast:3
 * @production ClassReference : {@link ConstructorReference} ::= <span class="component">TypeArgument:{@link Access}*</span>;

 */
public class ClassReference extends ConstructorReference implements Cloneable {
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\NameCheck.jrag:530
   */
  public void nameCheck() {
    for (int i = 0; i < getNumTypeArgument(); i++) {
      if (getTypeArgument(i) instanceof AbstractWildcard) {
        error("Wildcard not allowed in constructor reference type argument lists");
        break;
      }
    }
  }
  /**
   * @aspect Java8PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\PrettyPrint.jadd:69
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getTypeAccess());
    out.print("::");
    if (hasTypeArgument()) {
      out.print("<");
      out.join(getTypeArguments(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(">");
    }
    out.print("new");
  }
  /**
   * @declaredat ASTNode:1
   */
  public ClassReference() {
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
  public ClassReference(Access p0, List<Access> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:21
   */
  protected int numChildren() {
    return 2;
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
    targetConstructor_FunctionDescriptor_reset();
    syntheticInstanceExpr_FunctionDescriptor_reset();
    congruentTo_FunctionDescriptor_reset();
    potentiallyApplicableConstructors_FunctionDescriptor_reset();
    exactCompileTimeDeclaration_reset();
    isExact_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    toBlock_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:47
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:53
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:59
   */
  public ClassReference clone() throws CloneNotSupportedException {
    ClassReference node = (ClassReference) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:66
   */
  public ClassReference copy() {
    try {
      ClassReference node = (ClassReference) clone();
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
   * @declaredat ASTNode:85
   */
  public ClassReference fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:94
   */
  public ClassReference treeCopyNoTransform() {
    ClassReference tree = (ClassReference) copy();
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
   * @declaredat ASTNode:114
   */
  public ClassReference treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:121
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
  /**
   * Replaces the TypeArgument list.
   * @param list The new list node to be used as the TypeArgument list.
   * @apilevel high-level
   */
  public void setTypeArgumentList(List<Access> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the TypeArgument list.
   * @return Number of children in the TypeArgument list.
   * @apilevel high-level
   */
  public int getNumTypeArgument() {
    return getTypeArgumentList().getNumChild();
  }
  /**
   * Retrieves the number of children in the TypeArgument list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the TypeArgument list.
   * @apilevel low-level
   */
  public int getNumTypeArgumentNoTransform() {
    return getTypeArgumentListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the TypeArgument list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeArgument list.
   * @apilevel high-level
   */
  public Access getTypeArgument(int i) {
    return (Access) getTypeArgumentList().getChild(i);
  }
  /**
   * Check whether the TypeArgument list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasTypeArgument() {
    return getTypeArgumentList().getNumChild() != 0;
  }
  /**
   * Append an element to the TypeArgument list.
   * @param node The element to append to the TypeArgument list.
   * @apilevel high-level
   */
  public void addTypeArgument(Access node) {
    List<Access> list = (parent == null || state == null) ? getTypeArgumentListNoTransform() : getTypeArgumentList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addTypeArgumentNoTransform(Access node) {
    List<Access> list = getTypeArgumentListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the TypeArgument list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setTypeArgument(Access node, int i) {
    List<Access> list = getTypeArgumentList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="TypeArgument")
  public List<Access> getTypeArgumentList() {
    List<Access> list = (List<Access>) getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   */
  public List<Access> getTypeArgumentListNoTransform() {
    return (List<Access>) getChildNoTransform(1);
  }
  /**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   */
  public List<Access> getTypeArguments() {
    return getTypeArgumentList();
  }
  /**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   */
  public List<Access> getTypeArgumentsNoTransform() {
    return getTypeArgumentListNoTransform();
  }
  protected java.util.Map targetConstructor_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void targetConstructor_FunctionDescriptor_reset() {
    targetConstructor_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public ConstructorDecl targetConstructor(FunctionDescriptor f) {
    Object _parameters = f;
    if (targetConstructor_FunctionDescriptor_values == null) targetConstructor_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(targetConstructor_FunctionDescriptor_values.containsKey(_parameters)) {
      return (ConstructorDecl)targetConstructor_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ConstructorDecl targetConstructor_FunctionDescriptor_value = syntheticInstanceExpr(f).decl();
    if (isFinal && num == state().boundariesCrossed) {
      targetConstructor_FunctionDescriptor_values.put(_parameters, targetConstructor_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return targetConstructor_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  protected java.util.Map syntheticInstanceExpr_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  protected List syntheticInstanceExpr_FunctionDescriptor_list;
  /**
   * @apilevel internal
   */
  private void syntheticInstanceExpr_FunctionDescriptor_reset() {
    syntheticInstanceExpr_FunctionDescriptor_values = null;
    syntheticInstanceExpr_FunctionDescriptor_list = null;
  }
  @ASTNodeAnnotation.Attribute
  public ClassInstanceExpr syntheticInstanceExpr(FunctionDescriptor f) {
    Object _parameters = f;
    if (syntheticInstanceExpr_FunctionDescriptor_values == null) syntheticInstanceExpr_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(syntheticInstanceExpr_FunctionDescriptor_values.containsKey(_parameters)) {
      return (ClassInstanceExpr)syntheticInstanceExpr_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ClassInstanceExpr syntheticInstanceExpr_FunctionDescriptor_value = syntheticInstanceExpr_compute(f);
    if(syntheticInstanceExpr_FunctionDescriptor_list == null) {
      syntheticInstanceExpr_FunctionDescriptor_list = new List();
      syntheticInstanceExpr_FunctionDescriptor_list.is$Final = true;
      syntheticInstanceExpr_FunctionDescriptor_list.setParent(this);
    }
    syntheticInstanceExpr_FunctionDescriptor_list.add(syntheticInstanceExpr_FunctionDescriptor_value);
    if(syntheticInstanceExpr_FunctionDescriptor_value != null) {
      syntheticInstanceExpr_FunctionDescriptor_value = (ClassInstanceExpr) syntheticInstanceExpr_FunctionDescriptor_list.getChild(syntheticInstanceExpr_FunctionDescriptor_list.numChildren-1);
      syntheticInstanceExpr_FunctionDescriptor_value.is$Final = true;
    }
    if (true) {
      syntheticInstanceExpr_FunctionDescriptor_values.put(_parameters, syntheticInstanceExpr_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return syntheticInstanceExpr_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private ClassInstanceExpr syntheticInstanceExpr_compute(FunctionDescriptor f) {
      List<Expr> arguments = new List<Expr>();
      for (int i = 0; i < f.method.getNumParameter(); i++) {
        TypeDecl argumentType = f.method.getParameter(i).type();
        arguments.add(new SyntheticTypeAccess(argumentType));
      }
  
      ClassInstanceExpr instanceExpr = null;
      if (hasTypeArgument()) {
        instanceExpr = new ParConstructorReferenceAccess(
            (Access) getTypeAccess().treeCopyNoTransform(), arguments,
            new Opt(), (List<Access>)getTypeArgumentList().treeCopyNoTransform(), f);
      } else {
        // Must check for raw reference type, and in that case infer using diamond (JLS 15.13.1)
        boolean checkDiamond = true;
        if (getTypeAccess().type().hostType() != null && !getTypeAccess().type().isStatic()
            && getTypeAccess().type().hostType().isRawType()) {
          checkDiamond = false;
        }
        if (getTypeAccess().type().isRawType() && checkDiamond) {
          DiamondAccess diamond = new DiamondAccess((Access)getTypeAccess().treeCopyNoTransform());
          instanceExpr = new ConstructorReferenceAccess(diamond, arguments, f);
        } else {
          instanceExpr = new ConstructorReferenceAccess(
              (Access) getTypeAccess().treeCopyNoTransform(), arguments, f);
        }
      }
      return instanceExpr;
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
      ConstructorDecl decl = targetConstructor(f);
      if (unknownConstructor() == decl) {
        return false;
      }
      if (!f.method.type().isVoid()) {
        TypeDecl returnType = syntheticInstanceExpr(f).type();
        if (!returnType.assignConversionTo(f.method.type(), null)) {
          return false;
        }
      }
      return true;
    }
  protected java.util.Map potentiallyApplicableConstructors_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void potentiallyApplicableConstructors_FunctionDescriptor_reset() {
    potentiallyApplicableConstructors_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public ArrayList<ConstructorDecl> potentiallyApplicableConstructors(FunctionDescriptor f) {
    Object _parameters = f;
    if (potentiallyApplicableConstructors_FunctionDescriptor_values == null) potentiallyApplicableConstructors_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(potentiallyApplicableConstructors_FunctionDescriptor_values.containsKey(_parameters)) {
      return (ArrayList<ConstructorDecl>)potentiallyApplicableConstructors_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ArrayList<ConstructorDecl> potentiallyApplicableConstructors_FunctionDescriptor_value = potentiallyApplicableConstructors_compute(f);
    if (isFinal && num == state().boundariesCrossed) {
      potentiallyApplicableConstructors_FunctionDescriptor_values.put(_parameters, potentiallyApplicableConstructors_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return potentiallyApplicableConstructors_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private ArrayList<ConstructorDecl> potentiallyApplicableConstructors_compute(FunctionDescriptor f) {
      TypeDecl classType = syntheticInstanceExpr(f).type();
      Collection<ConstructorDecl> col = classType.constructors();
      ArrayList<ConstructorDecl> applicable = new ArrayList<ConstructorDecl>();
      for (ConstructorDecl decl : col) {
        if (!decl.accessibleFrom(hostType())) {
          continue;
        }
        if (!(decl.arity() == f.method.arity())) {
          continue;
        }
        if (hasTypeArgument()) {
          if (!decl.isGeneric()) {
            continue;
          }
          GenericConstructorDecl genDecl = decl.genericDecl();
          if (!(getNumTypeArgument() == genDecl.getNumTypeParameter())) {
            continue;
          }
        }
        applicable.add(decl);
      }
      return applicable;
    }
  /**
   * @apilevel internal
   */
  protected boolean exactCompileTimeDeclaration_computed = false;
  /**
   * @apilevel internal
   */
  protected ConstructorDecl exactCompileTimeDeclaration_value;
  /**
   * @apilevel internal
   */
  private void exactCompileTimeDeclaration_reset() {
    exactCompileTimeDeclaration_computed = false;
    exactCompileTimeDeclaration_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public ConstructorDecl exactCompileTimeDeclaration() {
    if(exactCompileTimeDeclaration_computed) {
      return exactCompileTimeDeclaration_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    exactCompileTimeDeclaration_value = exactCompileTimeDeclaration_compute();
    if (isFinal && num == state().boundariesCrossed) {
      exactCompileTimeDeclaration_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return exactCompileTimeDeclaration_value;
  }
  /**
   * @apilevel internal
   */
  private ConstructorDecl exactCompileTimeDeclaration_compute() {
      if (getTypeAccess().type().isRawType()) {
        if (getTypeAccess().type().hostType() == null || getTypeAccess().type().isStatic()
            || getTypeAccess().type().hostType().isRawType()) {
          return unknownConstructor();
        }
      }
      TypeDecl classType = getTypeAccess().type();
      Collection<ConstructorDecl> col = classType.constructors();
      ArrayList<ConstructorDecl> applicable = new ArrayList<ConstructorDecl>();
      int foundCompatible = 0;
      ConstructorDecl latestDecl = null;
  
      for (ConstructorDecl decl : col) {
        if (decl.accessibleFrom(hostType())) {
          foundCompatible++;
          latestDecl = decl;
        }
      }
      if (foundCompatible != 1) {
        return unknownConstructor();
      }
      if (latestDecl.isVariableArity()) {
        return unknownConstructor();
      }
      if (latestDecl.isGeneric()) {
        GenericConstructorDecl genericDecl = latestDecl.genericDecl();
        if (getNumTypeArgument() == genericDecl.getNumTypeParameter()) {
          return latestDecl;
        } else {
          return unknownConstructor();
        }
      }
      return latestDecl;
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
    isExact_value = exactCompileTimeDeclaration() != unknownConstructor();
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
  
      boolean foundMethod = false;
      for (ConstructorDecl decl : potentiallyApplicableConstructors(f)) {
        if (f.method.arity() == decl.arity()) {
          foundMethod = true;
          break;
        }
      }
      return foundMethod;
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
      List<Expr> arguments = new List<Expr>();
      for (int i = 0; i < targetInterface().functionDescriptor().method.getNumParameter(); i++) {
        String paramName = targetInterface().functionDescriptor().method.getParameter(i).name();
        arguments.add(new VarAccess(paramName));
      }
  
      ClassInstanceExpr instanceExpr = null;
      if (hasTypeArgument()) {
        instanceExpr = new ParClassInstanceExpr((Access) getTypeAccess().treeCopyNoTransform(), arguments,
                            new Opt(), (List<Access>)getTypeArgumentList().treeCopyNoTransform());
      } else {
        if (getTypeAccess().type().isRawType()) {
          DiamondAccess diamond = new DiamondAccess((Access) getTypeAccess().treeCopyNoTransform());
          instanceExpr = new ClassInstanceExpr(diamond, arguments);
        } else {
          instanceExpr = new ClassInstanceExpr((Access) getTypeAccess().treeCopyNoTransform(), arguments);
        }
      }
      Stmt blockStmt = null;
      if (targetInterface().functionDescriptor().method.type().isVoid()) {
        blockStmt = new ExprStmt(instanceExpr);
      } else {
        blockStmt = new ReturnStmt(instanceExpr);
      }
      List<Stmt> stmtList = new List<Stmt>();
      stmtList.add(blockStmt);
      return new Block(stmtList);
  
    }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\ConstructorReference.jrag:65
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getTypeArgumentListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else {
      return super.Define_NameType_nameType(caller, child);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\ToClassInherited.jrag:44
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_targetType(ASTNode caller, ASTNode child) {
    if (caller == toClass_value) {
{
    return targetInterface().functionDescriptor().method.type();
  }
    }
    else {
      return getParent().Define_TypeDecl_targetType(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
