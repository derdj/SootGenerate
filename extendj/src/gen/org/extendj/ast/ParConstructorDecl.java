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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\grammar\\GenericMethods.ast:10
 * @production ParConstructorDecl : {@link ConstructorDecl} ::= <span class="component">TypeArgument:{@link Access}*</span> <span class="component">&lt;GenericConstructorDecl:GenericConstructorDecl&gt;</span>;

 */
public class ParConstructorDecl extends ConstructorDecl implements Cloneable, Parameterization {
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:977
   */
  public boolean isRawType() {
    return false;
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:981
   */
  public TypeDecl substitute(TypeVariable typeVariable) {
    for (int i = 0; i < numTypeParameter(); i++) {
      if (typeParameter(i) == typeVariable) {
        return getTypeArgument(i).type();
      }
    }
    return genericConstructorDecl().hostType().substitute(typeVariable);
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:1013
   */
  public int numTypeParameter() {
    return genericConstructorDecl().original().getNumTypeParameter();
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:1021
   */
  public TypeVariable typeParameter(int index) {
    return genericConstructorDecl().original().getTypeParameter(index);
  }
  /**
   * @aspect GenericsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\GenericsCodegen.jrag:203
   */
  public void emitInvokeConstructor(CodeGeneration gen) {
    genericConstructorDecl().emitInvokeConstructor(gen);
  }
  /**
   * A raw type parameterization. Performs no substitutions.
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:240
   */
  public static final Parameterization RAW = new Parameterization() {
    @Override
    public boolean isRawType() {
      return true;
    }
    @Override
    public TypeDecl substitute(TypeVariable typeVariable) {
      return typeVariable;
    }
  };
  /**
   * @declaredat ASTNode:1
   */
  public ParConstructorDecl() {
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
    children = new ASTNode[7];
    setChild(new List(), 1);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
    setChild(new List(), 5);
  }
  /**
   * @declaredat ASTNode:17
   */
  public ParConstructorDecl(Modifiers p0, String p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5, List<Access> p6, GenericConstructorDecl p7) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
    setChild(p6, 5);
    setGenericConstructorDecl(p7);
  }
  /**
   * @declaredat ASTNode:27
   */
  public ParConstructorDecl(Modifiers p0, beaver.Symbol p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5, List<Access> p6, GenericConstructorDecl p7) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
    setChild(p6, 5);
    setGenericConstructorDecl(p7);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:40
   */
  protected int numChildren() {
    return 6;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:52
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    genericConstructorDecl_reset();
    sourceConstructorDecl_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:60
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:66
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:72
   */
  public ParConstructorDecl clone() throws CloneNotSupportedException {
    ParConstructorDecl node = (ParConstructorDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:79
   */
  public ParConstructorDecl copy() {
    try {
      ParConstructorDecl node = (ParConstructorDecl) clone();
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
   * @declaredat ASTNode:98
   */
  public ParConstructorDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:107
   */
  public ParConstructorDecl treeCopyNoTransform() {
    ParConstructorDecl tree = (ParConstructorDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 6:
          tree.children[i] = null;
          continue;
        }
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
   * @declaredat ASTNode:132
   */
  public ParConstructorDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:139
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ParConstructorDecl)node).tokenString_ID) && (tokenGenericConstructorDecl_GenericConstructorDecl == ((ParConstructorDecl)node).tokenGenericConstructorDecl_GenericConstructorDecl);    
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
   * Replaces the Parameter list.
   * @param list The new list node to be used as the Parameter list.
   * @apilevel high-level
   */
  public void setParameterList(List<ParameterDeclaration> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the Parameter list.
   * @return Number of children in the Parameter list.
   * @apilevel high-level
   */
  public int getNumParameter() {
    return getParameterList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Parameter list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Parameter list.
   * @apilevel low-level
   */
  public int getNumParameterNoTransform() {
    return getParameterListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Parameter list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Parameter list.
   * @apilevel high-level
   */
  public ParameterDeclaration getParameter(int i) {
    return (ParameterDeclaration) getParameterList().getChild(i);
  }
  /**
   * Check whether the Parameter list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasParameter() {
    return getParameterList().getNumChild() != 0;
  }
  /**
   * Append an element to the Parameter list.
   * @param node The element to append to the Parameter list.
   * @apilevel high-level
   */
  public void addParameter(ParameterDeclaration node) {
    List<ParameterDeclaration> list = (parent == null || state == null) ? getParameterListNoTransform() : getParameterList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addParameterNoTransform(ParameterDeclaration node) {
    List<ParameterDeclaration> list = getParameterListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Parameter list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setParameter(ParameterDeclaration node, int i) {
    List<ParameterDeclaration> list = getParameterList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Parameter list.
   * @return The node representing the Parameter list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Parameter")
  public List<ParameterDeclaration> getParameterList() {
    List<ParameterDeclaration> list = (List<ParameterDeclaration>) getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the Parameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Parameter list.
   * @apilevel low-level
   */
  public List<ParameterDeclaration> getParameterListNoTransform() {
    return (List<ParameterDeclaration>) getChildNoTransform(1);
  }
  /**
   * Retrieves the Parameter list.
   * @return The node representing the Parameter list.
   * @apilevel high-level
   */
  public List<ParameterDeclaration> getParameters() {
    return getParameterList();
  }
  /**
   * Retrieves the Parameter list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Parameter list.
   * @apilevel low-level
   */
  public List<ParameterDeclaration> getParametersNoTransform() {
    return getParameterListNoTransform();
  }
  /**
   * Replaces the Exception list.
   * @param list The new list node to be used as the Exception list.
   * @apilevel high-level
   */
  public void setExceptionList(List<Access> list) {
    setChild(list, 2);
  }
  /**
   * Retrieves the number of children in the Exception list.
   * @return Number of children in the Exception list.
   * @apilevel high-level
   */
  public int getNumException() {
    return getExceptionList().getNumChild();
  }
  /**
   * Retrieves the number of children in the Exception list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the Exception list.
   * @apilevel low-level
   */
  public int getNumExceptionNoTransform() {
    return getExceptionListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the Exception list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Exception list.
   * @apilevel high-level
   */
  public Access getException(int i) {
    return (Access) getExceptionList().getChild(i);
  }
  /**
   * Check whether the Exception list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasException() {
    return getExceptionList().getNumChild() != 0;
  }
  /**
   * Append an element to the Exception list.
   * @param node The element to append to the Exception list.
   * @apilevel high-level
   */
  public void addException(Access node) {
    List<Access> list = (parent == null || state == null) ? getExceptionListNoTransform() : getExceptionList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addExceptionNoTransform(Access node) {
    List<Access> list = getExceptionListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the Exception list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setException(Access node, int i) {
    List<Access> list = getExceptionList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the Exception list.
   * @return The node representing the Exception list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="Exception")
  public List<Access> getExceptionList() {
    List<Access> list = (List<Access>) getChild(2);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the Exception list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Exception list.
   * @apilevel low-level
   */
  public List<Access> getExceptionListNoTransform() {
    return (List<Access>) getChildNoTransform(2);
  }
  /**
   * Retrieves the Exception list.
   * @return The node representing the Exception list.
   * @apilevel high-level
   */
  public List<Access> getExceptions() {
    return getExceptionList();
  }
  /**
   * Retrieves the Exception list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Exception list.
   * @apilevel low-level
   */
  public List<Access> getExceptionsNoTransform() {
    return getExceptionListNoTransform();
  }
  /**
   * Replaces the optional node for the ParsedConstructorInvocation child. This is the <code>Opt</code>
   * node containing the child ParsedConstructorInvocation, not the actual child!
   * @param opt The new node to be used as the optional node for the ParsedConstructorInvocation child.
   * @apilevel low-level
   */
  public void setParsedConstructorInvocationOpt(Opt<Stmt> opt) {
    setChild(opt, 3);
  }
  /**
   * Replaces the (optional) ParsedConstructorInvocation child.
   * @param node The new node to be used as the ParsedConstructorInvocation child.
   * @apilevel high-level
   */
  public void setParsedConstructorInvocation(Stmt node) {
    getParsedConstructorInvocationOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional ParsedConstructorInvocation child exists.
   * @return {@code true} if the optional ParsedConstructorInvocation child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasParsedConstructorInvocation() {
    return getParsedConstructorInvocationOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) ParsedConstructorInvocation child.
   * @return The ParsedConstructorInvocation child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Stmt getParsedConstructorInvocation() {
    return (Stmt) getParsedConstructorInvocationOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the ParsedConstructorInvocation child. This is the <code>Opt</code> node containing the child ParsedConstructorInvocation, not the actual child!
   * @return The optional node for child the ParsedConstructorInvocation child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="ParsedConstructorInvocation")
  public Opt<Stmt> getParsedConstructorInvocationOpt() {
    return (Opt<Stmt>) getChild(3);
  }
  /**
   * Retrieves the optional node for child ParsedConstructorInvocation. This is the <code>Opt</code> node containing the child ParsedConstructorInvocation, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child ParsedConstructorInvocation.
   * @apilevel low-level
   */
  public Opt<Stmt> getParsedConstructorInvocationOptNoTransform() {
    return (Opt<Stmt>) getChildNoTransform(3);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 4);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(4);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(4);
  }
  /**
   * Replaces the TypeArgument list.
   * @param list The new list node to be used as the TypeArgument list.
   * @apilevel high-level
   */
  public void setTypeArgumentList(List<Access> list) {
    setChild(list, 5);
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
    List<Access> list = (List<Access>) getChild(5);
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
    return (List<Access>) getChildNoTransform(5);
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
   * Replaces the lexeme GenericConstructorDecl.
   * @param value The new value for the lexeme GenericConstructorDecl.
   * @apilevel high-level
   */
  public void setGenericConstructorDecl(GenericConstructorDecl value) {
    tokenGenericConstructorDecl_GenericConstructorDecl = value;
  }
  /**
   * @apilevel internal
   */
  protected GenericConstructorDecl tokenGenericConstructorDecl_GenericConstructorDecl;
  /**
   * Retrieves the value for the lexeme GenericConstructorDecl.
   * @return The value for the lexeme GenericConstructorDecl.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Token(name="GenericConstructorDecl")
  public GenericConstructorDecl getGenericConstructorDecl() {
    return tokenGenericConstructorDecl_GenericConstructorDecl;
  }
  /**
   * Retrieves the ImplicitConstructorInvocation child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ImplicitConstructorInvocation child.
   * @apilevel low-level
   */
  public Stmt getImplicitConstructorInvocationNoTransform() {
    return (Stmt) getChildNoTransform(6);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructorInvocation.
   * @return The the child position of the optional child ImplicitConstructorInvocation.
   * @apilevel low-level
   */
  protected int getImplicitConstructorInvocationChildPosition() {
    return 6;
  }
  /**
   * @apilevel internal
   */
  protected boolean genericConstructorDecl_computed = false;
  /**
   * @apilevel internal
   */
  protected GenericConstructorDecl genericConstructorDecl_value;
  /**
   * @apilevel internal
   */
  private void genericConstructorDecl_reset() {
    genericConstructorDecl_computed = false;
    genericConstructorDecl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public GenericConstructorDecl genericConstructorDecl() {
    if(genericConstructorDecl_computed) {
      return genericConstructorDecl_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    genericConstructorDecl_value = getGenericConstructorDecl();
    if (isFinal && num == state().boundariesCrossed) {
      genericConstructorDecl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return genericConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean sourceConstructorDecl_computed = false;
  /**
   * @apilevel internal
   */
  protected ConstructorDecl sourceConstructorDecl_value;
  /**
   * @apilevel internal
   */
  private void sourceConstructorDecl_reset() {
    sourceConstructorDecl_computed = false;
    sourceConstructorDecl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public ConstructorDecl sourceConstructorDecl() {
    if(sourceConstructorDecl_computed) {
      return sourceConstructorDecl_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    sourceConstructorDecl_value = genericConstructorDecl().original().sourceConstructorDecl();
    if (isFinal && num == state().boundariesCrossed) {
      sourceConstructorDecl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return sourceConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
