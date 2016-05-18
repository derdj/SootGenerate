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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:77
 * @production FieldDeclaration : {@link MemberDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">TypeAccess:{@link Access}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[Init:{@link Expr}]</span>;

 */
public class FieldDeclaration extends MemberDecl implements Cloneable, SimpleSet, Iterator, Variable {
  /**
   * @aspect BoundNames
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BoundNames.jrag:32
   */
  public Access createQualifiedBoundAccess() {
    if (isStatic()) {
      return hostType().createQualifiedAccess().qualifiesAccess(new BoundFieldAccess(this));
    } else
      return new ThisAccess("this").qualifiesAccess(
        new BoundFieldAccess(this));
  }
  /**
   * @aspect BoundNames
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BoundNames.jrag:106
   */
  public Access createBoundFieldAccess() {
    return createQualifiedBoundAccess();
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:105
   */
  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:109
   */
  public boolean isSingleton() { return true; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:110
   */
  public boolean isSingleton(Object o) { return contains(o); }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:113
   */
  private FieldDeclaration iterElem;
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:114
   */
  public Iterator iterator() { iterElem = this; return this; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:115
   */
  public boolean hasNext() { return iterElem != null; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:116
   */
  public Object next() { Object o = iterElem; iterElem = null; return o; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:117
   */
  public void remove() { throw new UnsupportedOperationException(); }
  /**
   * @aspect DefiniteAssignment
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:194
   */
  public void definiteAssignment() {
    super.definiteAssignment();
    if (isBlank() && isFinal() && isClassVariable()) {
      boolean found = false;
      TypeDecl typeDecl = hostType();
      for (int i = 0; i < typeDecl.getNumBodyDecl(); i++) {
        if (typeDecl.getBodyDecl(i) instanceof StaticInitializer) {
          StaticInitializer s = (StaticInitializer) typeDecl.getBodyDecl(i);
          if (s.isDAafter(this)) {
            found = true;
          }
        }

        else if (typeDecl.getBodyDecl(i) instanceof FieldDeclaration) {
          FieldDeclaration f = (FieldDeclaration) typeDecl.getBodyDecl(i);
          if (f.isStatic() && f.isDAafter(this)) {
            found = true;
          }
        }

      }
      if (!found) {
        errorf("blank final class variable %s in %s is not definitely assigned in static initializer",
            name(), hostType().typeName());
      }

    }
    if (isBlank() && isFinal() && isInstanceVariable()) {
      TypeDecl typeDecl = hostType();
      boolean found = false;
      for (int i = 0; !found && i < typeDecl.getNumBodyDecl(); i++) {
        if (typeDecl.getBodyDecl(i) instanceof FieldDeclaration) {
          FieldDeclaration f = (FieldDeclaration) typeDecl.getBodyDecl(i);
          if (!f.isStatic() && f.isDAafter(this)) {
            found = true;
          }
        } else if (typeDecl.getBodyDecl(i) instanceof InstanceInitializer) {
          InstanceInitializer ii = (InstanceInitializer) typeDecl.getBodyDecl(i);
          if (ii.getBlock().isDAafter(this)) {
            found = true;
          }
        }
      }
      for (Iterator iter = typeDecl.constructors().iterator(); !found && iter.hasNext(); ) {
        ConstructorDecl c = (ConstructorDecl) iter.next();
        if (!c.isDAafter(this)) {
          errorf("blank final instance variable %s in %s is not definitely assigned after %s",
              name(), hostType().typeName(), c.signature());
        }
      }
    }
    if (isBlank() && hostType().isInterfaceDecl()) {
      errorf("variable  %s in %s which is an interface must have an initializer",
          name(), hostType().typeName());
    }
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:140
   */
  public void checkModifiers() {
    super.checkModifiers();
    if (hostType().isInterfaceDecl()) {
      if (isProtected()) {
        error("an interface field may not be protected");
      }
      if (isPrivate()) {
        error("an interface field may not be private");
      }
      if (isTransient()) {
        error("an interface field may not be transient");
      }
      if (isVolatile()) {
        error("an interface field may not be volatile");
      }
    }
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:352
   */
  public void nameCheck() {
    super.nameCheck();
    // 8.3
    for (Iterator iter = hostType().memberFields(name()).iterator(); iter.hasNext(); ) {
      Variable v = (Variable) iter.next();
      if (v != this && v.hostType() == hostType()) {
        errorf("field named %s is multiply declared in type %s", name(), hostType().typeName());
      }
    }

  }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NodeConstructors.jrag:97
   */
  public FieldDeclaration(Modifiers m, Access type, String name) {
    this(m, type, name, new Opt());
  }
  /**
   * @aspect NodeConstructors
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NodeConstructors.jrag:101
   */
  public FieldDeclaration(Modifiers m, Access type, String name, Expr init) {
    this(m, type, name, new Opt(init));
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PrettyPrint.jadd:41
   */
  public void prettyPrint(PrettyPrinter out) {
    if (hasDocComment()) {
      out.print(docComment());
    }
    if (!out.isNewLine()) {
      out.println();
    }
    out.print(getModifiers());
    out.print(getTypeAccess());
    out.print(" ");
    out.print(getID());
    if (hasInit()) {
      out.print(" = ");
      out.print(getInit());
    }
    out.print(";");
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:55
   */
  public void typeCheck() {
    if (hasInit()) {
      TypeDecl source = getInit().type();
      TypeDecl dest = type();
      if (!source.assignConversionTo(dest, getInit())) {
        errorf("can not assign field %s of type %s a value of type %s",
            name(), dest.typeName(), source.typeName());
      }
    }
  }
  /**
   * @aspect VariableDeclarationTransformation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:112
   */
  private FieldDecl fieldDecl = null;
  /**
   * @aspect VariableDeclarationTransformation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:113
   */
  public FieldDecl getFieldDecl() {
    return fieldDecl;
  }
  /**
   * @aspect VariableDeclarationTransformation
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:116
   */
  public void setFieldDecl(FieldDecl fieldDecl) {
    this.fieldDecl = fieldDecl;
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:334
   */
  public void emitLoadField(CodeGeneration gen, TypeDecl typeDecl) {
    if (hostType().isArrayDecl() && name().equals("length")) {
      gen.emit(Bytecode.ARRAYLENGTH);
      return;
    }
    String classname = typeDecl.constantPoolName();
    String      desc = type().typeDescriptor();
    String      name = name();
    int index = gen.constantPool().addFieldref(classname, name, desc);
    if (isStatic()) {
      gen.emit(Bytecode.GETSTATIC, type().variableSize()).add2(index);
    } else {
      gen.emit(Bytecode.GETFIELD, type().variableSize() - 1).add2(index);
    }
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:393
   */
  public void emitStoreField(CodeGeneration gen, TypeDecl typeDecl) {
    String classname = typeDecl.constantPoolName();
    String      desc = type().typeDescriptor();
    String      name = name();
    int index = gen.constantPool().addFieldref(classname, name, desc);
    if (isStatic()) {
      gen.emit(Bytecode.PUTSTATIC, -type().variableSize()).add2(index);
    } else {
      gen.emit(Bytecode.PUTFIELD, -type().variableSize() - 1).add2(index);
    }
  }
  /**
   * Generate static initialization code for the field.
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:295
   */
  public void emitStaticInitializer(CodeGeneration gen, TypeDecl hostType) {
    if (isStatic() && hasInit()) {
      emitInitializerBCode(gen);
      emitStoreField(gen, hostType);
    }
  }
  /**
   * Generate instance initialization code for the field.
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:305
   */
  public void emitInstanceInitializer(CodeGeneration gen, TypeDecl hostType) {
    if (!isStatic() && hasInit()) {
      gen.emit(Bytecode.ALOAD_0);
      emitInitializerBCode(gen);
      emitStoreField(gen, hostType);
    }
  }
  /**
   * Generate field initialization bytecode.
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:316
   */
  protected void emitInitializerBCode(CodeGeneration gen) {
    getInit().createBCode(gen);
    getInit().type().emitAssignConvTo(gen, type());
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:429
   */
  public boolean clear() {
    return false;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:341
   */
  public MethodDecl createAccessor(TypeDecl fieldQualifier) {
    MethodDecl m = (MethodDecl) fieldQualifier.getAccessor(this, "field_read");
    if (m != null) {
      return m;
    }

    int accessorIndex = fieldQualifier.accessorCounter++;
    Modifiers modifiers = new Modifiers(new List());
    modifiers.addModifier(new Modifier("static"));
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));

    List parameters = new List();
    if (!isStatic()) {
      parameters.add(new ParameterDeclaration(fieldQualifier.createQualifiedAccess(), "that"));
    }

    m = new MethodDecl(
      modifiers,
      type().createQualifiedAccess(),
      "get$" + name() + "$access$" + accessorIndex,
      parameters,
      new List(),
      new Opt(
        new Block(
          new List().add(
            new ReturnStmt(createAccess())
          )
        )
      )
    );
    m = fieldQualifier.addMemberMethod(m);
    fieldQualifier.addAccessor(this, "field_read", m);
    return m;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:377
   */
  public MethodDecl createAccessorWrite(TypeDecl fieldQualifier) {
    MethodDecl m = (MethodDecl) fieldQualifier.getAccessor(this, "field_write");
    if (m != null) {
      return m;
    }

    int accessorIndex = fieldQualifier.accessorCounter++;
    Modifiers modifiers = new Modifiers(new List());
    modifiers.addModifier(new Modifier("static"));
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));

    List parameters = new List();
    if (!isStatic()) {
      parameters.add(new ParameterDeclaration(fieldQualifier.createQualifiedAccess(), "that"));
    }
    parameters.add(new ParameterDeclaration(type().createQualifiedAccess(), "value"));

    m = new MethodDecl(
      modifiers,
      type().createQualifiedAccess(),
      "set$" + name() + "$access$" + accessorIndex,
      parameters,
      new List(),
      new Opt(
        new Block(
          new List().add(
            new ExprStmt(
              new AssignSimpleExpr(
                createAccess(),
                new VarAccess("value")
              )
            )
          ).add(
            new ReturnStmt(
              new Opt(
                new VarAccess("value")
              )
            )
          )
        )
      )
    );
    m = fieldQualifier.addMemberMethod(m);
    fieldQualifier.addAccessor(this, "field_write", m);
    return m;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:425
   */
  private Access createAccess() {
    Access fieldAccess = new BoundFieldAccess(this);
    return isStatic() ? fieldAccess : new VarAccess("that").qualifiesAccess(fieldAccess);
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:1400
   */
  public BodyDecl substitutedBodyDecl(Parameterization parTypeDecl) {
    return new FieldDeclarationSubstituted(
      (Modifiers) getModifiers().treeCopyNoTransform(),
      getTypeAccess().type().substituteReturnType(parTypeDecl),
      getID(),
      new Opt(),
      this
    );
  }
  /**
   * @aspect UncheckedConversion
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\UncheckedConversion.jrag:47
   */
  public void checkWarnings() {
    if (hasInit() && !suppressWarnings("unchecked")) {
      checkUncheckedConversion(getInit().type(), type());
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public FieldDeclaration() {
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
    setChild(new Opt(), 2);
  }
  /**
   * @declaredat ASTNode:14
   */
  public FieldDeclaration(Modifiers p0, Access p1, String p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
  }
  /**
   * @declaredat ASTNode:20
   */
  public FieldDeclaration(Modifiers p0, Access p1, beaver.Symbol p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:29
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:35
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    accessibleFrom_TypeDecl_reset();
    exceptions_reset();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    constant_reset();
    attributes_reset();
    flags_reset();
    usesTypeVariable_reset();
    sourceVariableDecl_reset();
    throwTypes_reset();
    isEffectivelyFinal_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:64
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:70
   */
  public FieldDeclaration clone() throws CloneNotSupportedException {
    FieldDeclaration node = (FieldDeclaration) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:77
   */
  public FieldDeclaration copy() {
    try {
      FieldDeclaration node = (FieldDeclaration) clone();
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
   * @declaredat ASTNode:96
   */
  public FieldDeclaration fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:105
   */
  public FieldDeclaration treeCopyNoTransform() {
    FieldDeclaration tree = (FieldDeclaration) copy();
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
   * @declaredat ASTNode:125
   */
  public FieldDeclaration treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:132
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((FieldDeclaration)node).tokenString_ID);    
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
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   */
  public void setTypeAccess(Access node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="TypeAccess")
  public Access getTypeAccess() {
    return (Access) getChild(1);
  }
  /**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   */
  public Access getTypeAccessNoTransform() {
    return (Access) getChildNoTransform(1);
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
   * @apilevel internal
   */
  protected String tokenString_ID;
  /**
   */
  public int IDstart;
  /**
   */
  public int IDend;
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
   * Replaces the optional node for the Init child. This is the <code>Opt</code>
   * node containing the child Init, not the actual child!
   * @param opt The new node to be used as the optional node for the Init child.
   * @apilevel low-level
   */
  public void setInitOpt(Opt<Expr> opt) {
    setChild(opt, 2);
  }
  /**
   * Replaces the (optional) Init child.
   * @param node The new node to be used as the Init child.
   * @apilevel high-level
   */
  public void setInit(Expr node) {
    getInitOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Init child exists.
   * @return {@code true} if the optional Init child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasInit() {
    return getInitOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Init child.
   * @return The Init child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Expr getInit() {
    return (Expr) getInitOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Init child. This is the <code>Opt</code> node containing the child Init, not the actual child!
   * @return The optional node for child the Init child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Init")
  public Opt<Expr> getInitOpt() {
    return (Opt<Expr>) getChild(2);
  }
  /**
   * Retrieves the optional node for child Init. This is the <code>Opt</code> node containing the child Init, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Init.
   * @apilevel low-level
   */
  public Opt<Expr> getInitOptNoTransform() {
    return (Opt<Expr>) getChildNoTransform(2);
  }
  /**
   * @aspect Attributes
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:213
   */
  private Collection refined_Attributes_FieldDeclaration_attributes()
{
    ArrayList l = new ArrayList();
    if (isFinal() && isConstant() && (type().isPrimitive() || type().isString())) {
      l.add(new ConstantValueAttribute(hostType().constantPool(), this));
    }
    return l;
  }
  /**
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:38
   */
  private Collection refined_AnnotationsCodegen_FieldDeclaration_attributes()
{
    Collection c = refined_Attributes_FieldDeclaration_attributes();
    getModifiers().addRuntimeVisibleAnnotationsAttribute(c);
    getModifiers().addRuntimeInvisibleAnnotationsAttribute(c);
    return c;
  }
  protected java.util.Map accessibleFrom_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void accessibleFrom_TypeDecl_reset() {
    accessibleFrom_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean accessibleFrom(TypeDecl type) {
    Object _parameters = type;
    if (accessibleFrom_TypeDecl_values == null) accessibleFrom_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(accessibleFrom_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)accessibleFrom_TypeDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean accessibleFrom_TypeDecl_value = accessibleFrom_compute(type);
    if (isFinal && num == state().boundariesCrossed) {
      accessibleFrom_TypeDecl_values.put(_parameters, Boolean.valueOf(accessibleFrom_TypeDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return accessibleFrom_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean accessibleFrom_compute(TypeDecl type) {
      if (isPublic()) {
        return true;
      } else if (isProtected()) {
        if (hostPackage().equals(type.hostPackage())) {
          return true;
        }
        if (type.withinBodyThatSubclasses(hostType()) != null) {
          return true;
        }
        return false;
      } else if (isPrivate()) {
        return hostType().topLevelType() == type.topLevelType();
      } else {
        return hostPackage().equals(type.hostPackage());
      }
    }
  /**
   * @apilevel internal
   */
  protected boolean exceptions_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection exceptions_value;
  /**
   * @apilevel internal
   */
  private void exceptions_reset() {
    exceptions_computed = false;
    exceptions_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection exceptions() {
    if(exceptions_computed) {
      return exceptions_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    exceptions_value = exceptions_compute();
    if (isFinal && num == state().boundariesCrossed) {
      exceptions_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return exceptions_value;
  }
  /**
   * @apilevel internal
   */
  private Collection exceptions_compute() {
      HashSet set = new HashSet();
      if (isInstanceVariable() && hasInit()) {
        collectExceptions(set, this);
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
          TypeDecl typeDecl = (TypeDecl) iter.next();
          if (!getInit().reachedException(typeDecl)) {
            iter.remove();
          }
        }
      }
      return set;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isConstant() {
    ASTNode$State state = state();
    boolean isConstant_value = isFinal() && hasInit() && getInit().isConstant() && (type() instanceof PrimitiveType || type().isString());

    return isConstant_value;
  }
  @ASTNodeAnnotation.Attribute
  public int size() {
    ASTNode$State state = state();
    int size_value = 1;

    return size_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEmpty() {
    ASTNode$State state = state();
    boolean isEmpty_value = false;

    return isEmpty_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean contains(Object o) {
    ASTNode$State state = state();
    boolean contains_Object_value = this == o;

    return contains_Object_value;
  }
  protected java.util.Map isDAafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDAafter_Variable_reset() {
    isDAafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDAafter(Variable v) {
    Object _parameters = v;
    if (isDAafter_Variable_values == null) isDAafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(isDAafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDAafter_Variable_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDAafter_Variable_value = isDAafter_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDAafter_compute(Variable v) {
      if (v == this) {
        return hasInit();
      }
      return hasInit() ? getInit().isDAafter(v) : isDAbefore(v);
    }
  protected java.util.Map isDUafter_Variable_values;
  /**
   * @apilevel internal
   */
  private void isDUafter_Variable_reset() {
    isDUafter_Variable_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDUafter(Variable v) {
    Object _parameters = v;
    if (isDUafter_Variable_values == null) isDUafter_Variable_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(isDUafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDUafter_Variable_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean isDUafter_Variable_value = isDUafter_compute(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUafter_Variable_value;
  }
  /**
   * @apilevel internal
   */
  private boolean isDUafter_compute(Variable v) {
      if (v == this) {
        return !hasInit();
      }
      return hasInit() ? getInit().isDUafter(v) : isDUbefore(v);
    }
  @ASTNodeAnnotation.Attribute
  public boolean isSynthetic() {
    ASTNode$State state = state();
    boolean isSynthetic_value = getModifiers().isSynthetic();

    return isSynthetic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPublic() {
    ASTNode$State state = state();
    boolean isPublic_value = getModifiers().isPublic() || hostType().isInterfaceDecl();

    return isPublic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isPrivate() {
    ASTNode$State state = state();
    boolean isPrivate_value = getModifiers().isPrivate();

    return isPrivate_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isProtected() {
    ASTNode$State state = state();
    boolean isProtected_value = getModifiers().isProtected();

    return isProtected_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStatic() {
    ASTNode$State state = state();
    boolean isStatic_value = getModifiers().isStatic() || hostType().isInterfaceDecl();

    return isStatic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFinal() {
    ASTNode$State state = state();
    boolean isFinal_value = getModifiers().isFinal() || hostType().isInterfaceDecl();

    return isFinal_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isTransient() {
    ASTNode$State state = state();
    boolean isTransient_value = getModifiers().isTransient();

    return isTransient_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVolatile() {
    ASTNode$State state = state();
    boolean isVolatile_value = getModifiers().isVolatile();

    return isVolatile_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasModifiers() {
    ASTNode$State state = state();
    boolean hasModifiers_value = getModifiers().getNumModifier() > 0;

    return hasModifiers_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    ASTNode$State state = state();
    TypeDecl type_value = getTypeAccess().type();

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVoid() {
    ASTNode$State state = state();
    boolean isVoid_value = type().isVoid();

    return isVoid_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isParameter() {
    ASTNode$State state = state();
    boolean isParameter_value = false;

    return isParameter_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isClassVariable() {
    ASTNode$State state = state();
    boolean isClassVariable_value = isStatic() || hostType().isInterfaceDecl();

    return isClassVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isInstanceVariable() {
    ASTNode$State state = state();
    boolean isInstanceVariable_value = (hostType().isClassDecl() || hostType().isAnonymous() )&& !isStatic();

    return isInstanceVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isMethodParameter() {
    ASTNode$State state = state();
    boolean isMethodParameter_value = false;

    return isMethodParameter_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isConstructorParameter() {
    ASTNode$State state = state();
    boolean isConstructorParameter_value = false;

    return isConstructorParameter_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isExceptionHandlerParameter() {
    ASTNode$State state = state();
    boolean isExceptionHandlerParameter_value = false;

    return isExceptionHandlerParameter_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isLocalVariable() {
    ASTNode$State state = state();
    boolean isLocalVariable_value = false;

    return isLocalVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isBlank() {
    ASTNode$State state = state();
    boolean isBlank_value = !hasInit();

    return isBlank_value;
  }
  @ASTNodeAnnotation.Attribute
  public String name() {
    ASTNode$State state = state();
    String name_value = getID();

    return name_value;
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
    constant_value = type().cast(getInit().constant());
    if (isFinal && num == state().boundariesCrossed) {
      constant_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return constant_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean attributes_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection attributes_value;
  /**
   * @apilevel internal
   */
  private void attributes_reset() {
    attributes_computed = false;
    attributes_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection attributes() {
    if(attributes_computed) {
      return attributes_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    attributes_value = attributes_compute();
    if (isFinal && num == state().boundariesCrossed) {
      attributes_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return attributes_value;
  }
  /**
   * @apilevel internal
   */
  private Collection attributes_compute() {
      Collection c = refined_AnnotationsCodegen_FieldDeclaration_attributes();
      if (needsSignatureAttribute()) {
        c.add(new SignatureAttribute(hostType().constantPool(), type().fieldTypeSignature()));
      }
      return c;
    }
  /**
   * @apilevel internal
   */
  protected boolean flags_computed = false;
  /**
   * @apilevel internal
   */
  protected int flags_value;
  /**
   * @apilevel internal
   */
  private void flags_reset() {
    flags_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int flags() {
    if(flags_computed) {
      return flags_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    flags_value = flags_compute();
    if (isFinal && num == state().boundariesCrossed) {
      flags_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return flags_value;
  }
  /**
   * @apilevel internal
   */
  private int flags_compute() {
      int res = 0;
      if (isPublic()) {
        res |= Modifiers.ACC_PUBLIC;
      }
      if (isPrivate()) {
        res |= Modifiers.ACC_PRIVATE;
      }
      if (isProtected()) {
        res |= Modifiers.ACC_PROTECTED;
      }
      if (isStatic()) {
        res |= Modifiers.ACC_STATIC;
      }
      if (isFinal()) {
        res |= Modifiers.ACC_FINAL;
      }
      if (isVolatile()) {
        res |= Modifiers.ACC_VOLATILE;
      }
      if (isTransient()) {
        res |= Modifiers.ACC_TRANSIENT;
      }
      return res;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isBytecodeField() {
    ASTNode$State state = state();
    boolean isBytecodeField_value = true;

    return isBytecodeField_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean flush() {
    ASTNode$State state = state();
    boolean flush_value = false;

    return flush_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSuppressWarnings(String annot) {
    ASTNode$State state = state();
    boolean hasAnnotationSuppressWarnings_String_value = getModifiers().hasAnnotationSuppressWarnings(annot);

    return hasAnnotationSuppressWarnings_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDeprecated() {
    ASTNode$State state = state();
    boolean isDeprecated_value = getModifiers().hasDeprecatedAnnotation();

    return isDeprecated_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean usesTypeVariable_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean usesTypeVariable_value;
  /**
   * @apilevel internal
   */
  private void usesTypeVariable_reset() {
    usesTypeVariable_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean usesTypeVariable() {
    if(usesTypeVariable_computed) {
      return usesTypeVariable_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    usesTypeVariable_value = getTypeAccess().usesTypeVariable();
    if (isFinal && num == state().boundariesCrossed) {
      usesTypeVariable_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return usesTypeVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public FieldDeclaration erasedField() {
    ASTNode$State state = state();
    FieldDeclaration erasedField_value = this;

    return erasedField_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean sourceVariableDecl_computed = false;
  /**
   * @apilevel internal
   */
  protected Variable sourceVariableDecl_value;
  /**
   * @apilevel internal
   */
  private void sourceVariableDecl_reset() {
    sourceVariableDecl_computed = false;
    sourceVariableDecl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Variable sourceVariableDecl() {
    if(sourceVariableDecl_computed) {
      return sourceVariableDecl_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    sourceVariableDecl_value = this;
    if (isFinal && num == state().boundariesCrossed) {
      sourceVariableDecl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return sourceVariableDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean visibleTypeParameters() {
    ASTNode$State state = state();
    boolean visibleTypeParameters_value = !isStatic();

    return visibleTypeParameters_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean needsSignatureAttribute() {
    ASTNode$State state = state();
    boolean needsSignatureAttribute_value = type().needsSignatureAttribute();

    return needsSignatureAttribute_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean throwTypes_computed = false;
  /**
   * @apilevel internal
   */
  protected Collection<TypeDecl> throwTypes_value;
  /**
   * @apilevel internal
   */
  private void throwTypes_reset() {
    throwTypes_computed = false;
    throwTypes_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public Collection<TypeDecl> throwTypes() {
    if(throwTypes_computed) {
      return throwTypes_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    throwTypes_value = throwTypes_compute();
    if (isFinal && num == state().boundariesCrossed) {
      throwTypes_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return throwTypes_value;
  }
  /**
   * @apilevel internal
   */
  private Collection<TypeDecl> throwTypes_compute() {
      Collection<TypeDecl> tts = new LinkedList<TypeDecl>();
      tts.add(type());
      return tts;
    }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSafeVarargs() {
    ASTNode$State state = state();
    boolean hasAnnotationSafeVarargs_value = getModifiers().hasAnnotationSafeVarargs();

    return hasAnnotationSafeVarargs_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean suppressWarnings(String type) {
    ASTNode$State state = state();
    boolean suppressWarnings_String_value = hasAnnotationSuppressWarnings(type) || withinSuppressWarnings(type);

    return suppressWarnings_String_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean isEffectivelyFinal_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isEffectivelyFinal_value;
  /**
   * @apilevel internal
   */
  private void isEffectivelyFinal_reset() {
    isEffectivelyFinal_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isEffectivelyFinal() {
    if(isEffectivelyFinal_computed) {
      return isEffectivelyFinal_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isEffectivelyFinal_value = isFinal();
    if (isFinal && num == state().boundariesCrossed) {
      isEffectivelyFinal_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isEffectivelyFinal_value;
  }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:81
   */
  @ASTNodeAnnotation.Attribute
  public boolean handlesException(TypeDecl exceptionType) {
    ASTNode$State state = state();
    boolean handlesException_TypeDecl_value = getParent().Define_boolean_handlesException(this, null, exceptionType);

    return handlesException_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect SuppressWarnings
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\SuppressWarnings.jrag:36
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinSuppressWarnings(String annot) {
    ASTNode$State state = state();
    boolean withinSuppressWarnings_String_value = getParent().Define_boolean_withinSuppressWarnings(this, null, annot);

    return withinSuppressWarnings_String_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:61
   * @apilevel internal
   */
  public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_isSource(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:361
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getInitOptNoTransform()){
    return isDAbefore(v);
  }
    else {
      return super.Define_boolean_isDAbefore(caller, child, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:209
   * @apilevel internal
   */
  public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if (caller == getInitOptNoTransform()){
    if (hostType().isAnonymous()) {
      return true;
    }
    for (Iterator iter = hostType().constructors().iterator(); iter.hasNext(); ) {
      ConstructorDecl decl = (ConstructorDecl) iter.next();
      if (!decl.throwsException(exceptionType)) {
        return false;
      }
    }
    return true;
  }
    else {
      return getParent().Define_boolean_handlesException(this, caller, exceptionType);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:303
   * @apilevel internal
   */
  public boolean Define_boolean_mayBePublic(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBePublic(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:304
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeProtected(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeProtected(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:305
   * @apilevel internal
   */
  public boolean Define_boolean_mayBePrivate(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBePrivate(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:306
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeStatic(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeStatic(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:307
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeFinal(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:308
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeTransient(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeTransient(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:309
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeVolatile(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeVolatile(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\SyntacticClassification.jrag:105
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getTypeAccessNoTransform()) {
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_NameType_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeAnalysis.jrag:286
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_declType(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return type();
    }
    else {
      return getParent().Define_TypeDecl_declType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:189
   * @apilevel internal
   */
  public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return isStatic();
    }
    else {
      return getParent().Define_boolean_inStaticContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:98
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_expectedType(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return type().componentType();
    }
    else {
      return getParent().Define_TypeDecl_expectedType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:107
   * @apilevel internal
   */
  public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if (caller == getModifiersNoTransform()) {
      return name.equals("FIELD");
    }
    else {
      return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\GenericMethodsInference.jrag:60
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_assignConvertedType(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return type();
    }
    else {
      return getParent().Define_TypeDecl_assignConvertedType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:40
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_targetType(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return getTypeAccess().type();
    }
    else {
      return getParent().Define_TypeDecl_targetType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:373
   * @apilevel internal
   */
  public boolean Define_boolean_assignmentContext(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_assignmentContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:374
   * @apilevel internal
   */
  public boolean Define_boolean_invocationContext(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_invocationContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:375
   * @apilevel internal
   */
  public boolean Define_boolean_castContext(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_castContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:376
   * @apilevel internal
   */
  public boolean Define_boolean_stringContext(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_stringContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\TargetType.jrag:377
   * @apilevel internal
   */
  public boolean Define_boolean_numericContext(ASTNode caller, ASTNode child) {
    if (caller == getInitOptNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_numericContext(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
