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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\grammar\\MethodReference.ast:3
 * @production ExprMethodReference : {@link MethodReference} ::= <span class="component">{@link Expr}</span>;

 */
public class ExprMethodReference extends MethodReference implements Cloneable {
  /**
   * @aspect Java8NameCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\NameCheck.jrag:521
   */
  public void nameCheck() {
    super.nameCheck();
    if (!getExpr().isSuperAccess()) {
      if (!getExpr().type().isReferenceType()) {
        error("Expression in a method reference must have reference type");
      }
    }
  }
  /**
   * @aspect Java8PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\PrettyPrint.jadd:88
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print(getExpr());
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
    out.print(name());
  }
  /**
   * @aspect Java8CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\CreateBCode.jrag:33
   */
  public void createBCode(CodeGeneration gen) {
    toClass().createBCode(gen);
  }
  /**
   * @aspect MethodReferenceToClass
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\MethodReferenceToClass.jrag:30
   */
  private String syntheticFieldName() {
    return "#1";
  }
  /**
   * @aspect Transformations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\Transformations.jrag:33
   */
  public void transformation() {
    toClass().transformation();
  }
  /**
   * @declaredat ASTNode:1
   */
  public ExprMethodReference() {
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
    setChild(new List(), 0);
  }
  /**
   * @declaredat ASTNode:14
   */
  public ExprMethodReference(List<Access> p0, String p1, Expr p2) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:19
   */
  public ExprMethodReference(List<Access> p0, beaver.Symbol p1, Expr p2) {
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
    targetMethod_FunctionDescriptor_reset();
    syntheticAccess_FunctionDescriptor_reset();
    syntheticMethodAccess_FunctionDescriptor_reset();
    congruentTo_FunctionDescriptor_reset();
    potentiallyApplicableMethods_FunctionDescriptor_reset();
    exactCompileTimeDeclaration_reset();
    potentiallyCompatible_TypeDecl_BodyDecl_reset();
    toClass_reset();
    toBlock_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:54
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:60
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:66
   */
  public ExprMethodReference clone() throws CloneNotSupportedException {
    ExprMethodReference node = (ExprMethodReference) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:73
   */
  public ExprMethodReference copy() {
    try {
      ExprMethodReference node = (ExprMethodReference) clone();
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
   * @declaredat ASTNode:92
   */
  public ExprMethodReference fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:101
   */
  public ExprMethodReference treeCopyNoTransform() {
    ExprMethodReference tree = (ExprMethodReference) copy();
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
   * @declaredat ASTNode:121
   */
  public ExprMethodReference treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:128
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ExprMethodReference)node).tokenString_ID);    
  }
  /**
   * Replaces the TypeArgument list.
   * @param list The new list node to be used as the TypeArgument list.
   * @apilevel high-level
   */
  public void setTypeArgumentList(List<Access> list) {
    setChild(list, 0);
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
    List<Access> list = (List<Access>) getChild(0);
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
    return (List<Access>) getChildNoTransform(0);
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
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(1);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(1);
  }
  protected java.util.Map targetMethod_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void targetMethod_FunctionDescriptor_reset() {
    targetMethod_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public MethodDecl targetMethod(FunctionDescriptor f) {
    Object _parameters = f;
    if (targetMethod_FunctionDescriptor_values == null) targetMethod_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(targetMethod_FunctionDescriptor_values.containsKey(_parameters)) {
      return (MethodDecl)targetMethod_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    MethodDecl targetMethod_FunctionDescriptor_value = targetMethod_compute(f);
    if (isFinal && num == state().boundariesCrossed) {
      targetMethod_FunctionDescriptor_values.put(_parameters, targetMethod_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return targetMethod_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private MethodDecl targetMethod_compute(FunctionDescriptor f) {
      return syntheticMethodAccess(f).decl();
    }
  /**
   * @apilevel internal
   */
  protected java.util.Map syntheticAccess_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  protected List syntheticAccess_FunctionDescriptor_list;
  /**
   * @apilevel internal
   */
  private void syntheticAccess_FunctionDescriptor_reset() {
    syntheticAccess_FunctionDescriptor_values = null;
    syntheticAccess_FunctionDescriptor_list = null;
  }
  @ASTNodeAnnotation.Attribute
  public Access syntheticAccess(FunctionDescriptor f) {
    Object _parameters = f;
    if (syntheticAccess_FunctionDescriptor_values == null) syntheticAccess_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(syntheticAccess_FunctionDescriptor_values.containsKey(_parameters)) {
      return (Access)syntheticAccess_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    Access syntheticAccess_FunctionDescriptor_value = syntheticAccess_compute(f);
    if(syntheticAccess_FunctionDescriptor_list == null) {
      syntheticAccess_FunctionDescriptor_list = new List();
      syntheticAccess_FunctionDescriptor_list.is$Final = true;
      syntheticAccess_FunctionDescriptor_list.setParent(this);
    }
    syntheticAccess_FunctionDescriptor_list.add(syntheticAccess_FunctionDescriptor_value);
    if(syntheticAccess_FunctionDescriptor_value != null) {
      syntheticAccess_FunctionDescriptor_value = (Access) syntheticAccess_FunctionDescriptor_list.getChild(syntheticAccess_FunctionDescriptor_list.numChildren-1);
      syntheticAccess_FunctionDescriptor_value.is$Final = true;
    }
    if (true) {
      syntheticAccess_FunctionDescriptor_values.put(_parameters, syntheticAccess_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return syntheticAccess_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private Access syntheticAccess_compute(FunctionDescriptor f) {
      List<Expr> arguments = new List<Expr>();
      for (int i = 0; i < f.method.getNumParameter(); i++) {
        TypeDecl argumentType = f.method.getParameter(i).type();
        arguments.add(new SyntheticTypeAccess(argumentType));
      }
  
      if (!hasTypeArgument()) {
        MethodReferenceAccess mAccess = new MethodReferenceAccess(name(), arguments, f);
        return ((Expr) getExpr().treeCopyNoTransform()).qualifiesAccess(mAccess);
      } else {
        ParMethodReferenceAccess pmAccess = new ParMethodReferenceAccess(name(), arguments,
            (List<Access>) getTypeArgumentList().treeCopyNoTransform(), f);
        return ((Expr) getExpr().treeCopyNoTransform()).qualifiesAccess(pmAccess);
      }
    }
  protected java.util.Map syntheticMethodAccess_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void syntheticMethodAccess_FunctionDescriptor_reset() {
    syntheticMethodAccess_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public MethodAccess syntheticMethodAccess(FunctionDescriptor f) {
    Object _parameters = f;
    if (syntheticMethodAccess_FunctionDescriptor_values == null) syntheticMethodAccess_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(syntheticMethodAccess_FunctionDescriptor_values.containsKey(_parameters)) {
      return (MethodAccess)syntheticMethodAccess_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    MethodAccess syntheticMethodAccess_FunctionDescriptor_value = syntheticMethodAccess_compute(f);
    if (isFinal && num == state().boundariesCrossed) {
      syntheticMethodAccess_FunctionDescriptor_values.put(_parameters, syntheticMethodAccess_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return syntheticMethodAccess_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private MethodAccess syntheticMethodAccess_compute(FunctionDescriptor f) {
      Access synAccess = syntheticAccess(f);
      return (MethodAccess) synAccess.lastAccess();
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
      MethodDecl decl = targetMethod(f);
      if (unknownMethod() == decl) {
        return false;
      }
      if (f.method.type().isVoid()) {
        return true;
      }
      if (decl.type().isVoid()) {
        return false;
      }
      return decl.type().assignConversionTo(f.method.type(), null);
    }
  protected java.util.Map potentiallyApplicableMethods_FunctionDescriptor_values;
  /**
   * @apilevel internal
   */
  private void potentiallyApplicableMethods_FunctionDescriptor_reset() {
    potentiallyApplicableMethods_FunctionDescriptor_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public ArrayList<MethodDecl> potentiallyApplicableMethods(FunctionDescriptor f) {
    Object _parameters = f;
    if (potentiallyApplicableMethods_FunctionDescriptor_values == null) potentiallyApplicableMethods_FunctionDescriptor_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(potentiallyApplicableMethods_FunctionDescriptor_values.containsKey(_parameters)) {
      return (ArrayList<MethodDecl>)potentiallyApplicableMethods_FunctionDescriptor_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    ArrayList<MethodDecl> potentiallyApplicableMethods_FunctionDescriptor_value = potentiallyApplicableMethods_compute(f);
    if (isFinal && num == state().boundariesCrossed) {
      potentiallyApplicableMethods_FunctionDescriptor_values.put(_parameters, potentiallyApplicableMethods_FunctionDescriptor_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return potentiallyApplicableMethods_FunctionDescriptor_value;
  }
  /**
   * @apilevel internal
   */
  private ArrayList<MethodDecl> potentiallyApplicableMethods_compute(FunctionDescriptor f) {
      Collection<MethodDecl> col = getExpr().type().memberMethods(name());
      ArrayList<MethodDecl> applicable = new ArrayList<MethodDecl>();
      for (MethodDecl decl : col) {
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
          GenericMethodDecl genDecl = decl.genericDecl();
          if (!(getNumTypeArgument() == genDecl.getNumTypeParameter())) {
          }
            continue;
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
  protected MethodDecl exactCompileTimeDeclaration_value;
  /**
   * @apilevel internal
   */
  private void exactCompileTimeDeclaration_reset() {
    exactCompileTimeDeclaration_computed = false;
    exactCompileTimeDeclaration_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public MethodDecl exactCompileTimeDeclaration() {
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
  private MethodDecl exactCompileTimeDeclaration_compute() {
      Collection<MethodDecl> col = getExpr().type().memberMethods(name());
      int foundCompatible = 0;
      MethodDecl latestDecl = null;
      for (MethodDecl decl  : col) {
        if (decl.accessibleFrom(hostType())) {
          foundCompatible++;
          latestDecl = decl;
        }
      }
      if (foundCompatible != 1) {
        return unknownMethod();
      }
      if (latestDecl.isVariableArity()) {
        return unknownMethod();
      }
      if (latestDecl.isGeneric()) {
        GenericMethodDecl genericDecl = latestDecl.genericDecl();
        if (getNumTypeArgument() == genericDecl.getNumTypeParameter()) {
          return latestDecl;
        } else {
          return unknownMethod();
        }
      }
      return latestDecl;
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
      for (MethodDecl decl : potentiallyApplicableMethods(f)) {
        if (!decl.isStatic() && f.method.arity() == decl.arity()) {
          foundMethod = true;
          break;
        }
      }
      return foundMethod;
    }
  /**
   * @apilevel internal
   */
  protected boolean toClass_computed = false;
  /**
   * @apilevel internal
   */
  protected ClassInstanceExpr toClass_value;
  /**
   * @apilevel internal
   */
  private void toClass_reset() {
    toClass_computed = false;
    toClass_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public ClassInstanceExpr toClass() {
    if(toClass_computed) {
      return toClass_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    toClass_value = toClass_compute();
    toClass_value.setParent(this);
    toClass_value.is$Final = true;
    if (true) {
      toClass_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return toClass_value;
  }
  /**
   * @apilevel internal
   */
  private ClassInstanceExpr toClass_compute() {
      List<Access> implementsList = new List<Access>();
      InterfaceDecl iDecl = targetInterface();
  
      // First compute the interface implemented by the anonymous class
      Access implementsInterface = iDecl.createQualifiedAccess();
      implementsList.add(implementsInterface);
  
      // Next compute the BodyDecls for the anonymous class
      List<BodyDecl> bodyDecls = new List<BodyDecl>();
  
      FieldDeclaration fieldDecl = null;
      // If this reference uses a primary or expression name, must evaluate that part first
      if (!(getExpr() instanceof Access) || !(((Access) getExpr()).lastAccess() instanceof SuperAccess)) {
        fieldDecl = new FieldDeclaration(new Modifiers(),
            getExpr().type().createQualifiedAccess(), syntheticFieldName(), (Expr) getExpr().treeCopyNoTransform());
        bodyDecls.add(fieldDecl);
      }
  
      // Then we must build the method overriding the abstract methods
  
      Modifiers methodModifiers = new Modifiers(new List<Modifier>().add(new Modifier("public")));
      Access returnType = new SyntheticTypeAccess(iDecl.functionDescriptor().method.type());
      List<ParameterDeclaration> methodParams = toParameterList();
      List<Access> methodThrows = new List<Access>();
      for (TypeDecl throwsType : iDecl.functionDescriptor().throwsList) {
        methodThrows.add(new SyntheticTypeAccess(throwsType));
      }
      Opt<Block> methodBlock = new Opt<Block>(toBlock());
      MethodDecl method = new MethodDecl(methodModifiers, returnType, iDecl.functionDescriptor().method.name(),
                        methodParams, methodThrows, methodBlock);
  
      bodyDecls.add(method);
  
      /* Now the anonymous class can be built. Must use the type LambdaAnonymousDecl instead
      of a normal AnonymousDecl in order for this and super keywords to get the type of the outer
      scope. */
      LambdaAnonymousDecl anonymousDecl = new LambdaAnonymousDecl(new Modifiers(), "MethodReference", bodyDecls);
      for (Access impl: implementsList) {
        anonymousDecl.addImplements(impl);
      }
  
      return new ClassInstanceExpr((Access) implementsInterface.treeCopyNoTransform(), new List<Expr>(), new Opt<TypeDecl>(anonymousDecl));
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
      Expr qualifier = null;
      if (getExpr() instanceof Access && ((Access) getExpr()).lastAccess() instanceof SuperAccess) {
        qualifier = (Expr) getExpr().treeCopyNoTransform();
      } else {
        qualifier = new VarAccess(syntheticFieldName());
      }
  
      List<Expr> arguments = new List<Expr>();
      for (int i = 0; i < targetInterface().functionDescriptor().method.getNumParameter(); i++) {
        String paramName = targetInterface().functionDescriptor().method.getParameter(i).name();
        arguments.add(new VarAccess(paramName));
      }
  
      MethodAccess m = null;
      if (!hasTypeArgument()) {
        m = new MethodAccess(name(), arguments);
      } else {
        m = new ParMethodAccess(name(), arguments, (List<Access>)getTypeArgumentList().treeCopyNoTransform());
      }
      Access qualifiedMethod = qualifier.qualifiesAccess(m);
      Stmt blockStmt = null;
      if (targetInterface().functionDescriptor().method.type().isVoid()) {
        blockStmt = new ExprStmt(qualifiedMethod);
      } else {
        blockStmt = new ReturnStmt(qualifiedMethod);
      }
      List<Stmt> stmtList = new List<Stmt>();
      stmtList.add(blockStmt);
      return new Block(stmtList);
    }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:385
   * @apilevel internal
   */
  public boolean Define_boolean_assignmentContext(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_assignmentContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:386
   * @apilevel internal
   */
  public boolean Define_boolean_invocationContext(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_invocationContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:387
   * @apilevel internal
   */
  public boolean Define_boolean_castContext(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_castContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:388
   * @apilevel internal
   */
  public boolean Define_boolean_stringContext(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_stringContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:389
   * @apilevel internal
   */
  public boolean Define_boolean_numericContext(ASTNode caller, ASTNode child) {
    if (caller == getExprNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_numericContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\backend\\ToClassInherited.jrag:36
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_assignConvertedType(ASTNode caller, ASTNode child) {
    if (caller == toClass_value) {
{
    return targetInterface().functionDescriptor().method.type();
  }
    }
    else {
      return getParent().Define_TypeDecl_assignConvertedType(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
