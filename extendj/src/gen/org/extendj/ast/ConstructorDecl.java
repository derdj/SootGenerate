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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:72
 * @production ConstructorDecl : {@link BodyDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">Parameter:{@link ParameterDeclaration}*</span> <span class="component">Exception:{@link Access}*</span> <span class="component">[ParsedConstructorInvocation:{@link Stmt}]</span> <span class="component">{@link Block}</span> <span class="component">ImplicitConstructorInvocation:{@link Stmt}</span>;

 */
public class ConstructorDecl extends BodyDecl implements Cloneable, ExceptionHolder {
  /**
   * @aspect ConstructorDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupConstructor.jrag:190
   */
  public boolean applicable(List<Expr> argList) {
    if (getNumParameter() != argList.getNumChild()) {
      return false;
    }
    for (int i = 0; i < getNumParameter(); i++) {
      TypeDecl arg = argList.getChild(i).type();
      TypeDecl parameter = getParameter(i).type();
      if (!arg.instanceOf(parameter)) {
        return false;
      }
    }
    return true;
  }
  /**
   * Flag to indicate if this constructor is an auto-generated
   * default constructor. Implicit constructors are not pretty
   * printed.
   * @aspect ImplicitConstructor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupConstructor.jrag:221
   */
  private boolean isImplicitConstructor = false;
  /**
   * Set the default constructor flag. Causes this constructor
   * to not be pretty printed.
   * @aspect ImplicitConstructor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupConstructor.jrag:227
   */
  public void setImplicitConstructor() {
    isImplicitConstructor = true;
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:136
   */
  public void checkModifiers() {
    super.checkModifiers();
  }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:97
   */
  public void nameCheck() {
    super.nameCheck();
    // 8.8
    if (!hostType().name().equals(name())) {
      errorf("constructor %s does not have the same name as the simple name of the host class %s",
          name(), hostType().name());
    }

    // 8.8.2
    if (hostType().lookupConstructor(this) != this) {
      errorf("constructor with signature %s is multiply declared in type %s", signature(),
          hostType().typeName());
    }

    if (circularThisInvocation(this)) {
      errorf("The constructor %s may not directly or indirectly invoke itself", signature());
    }
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PrettyPrint.jadd:508
   */
  public void prettyPrint(PrettyPrinter out) {
    if (!isImplicitConstructor()) {
      if (hasDocComment()) {
        out.print(docComment());
      }
      if (!out.isNewLine()) {
        out.println();
      }
      out.print(getModifiers());
      out.print(getID());
      out.print("(");
      out.join(getParameterList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(")");
      if (hasExceptions()) {
        out.print(" throws ");
        out.join(getExceptionList(), new PrettyPrinter.Joiner() {
          @Override
          public void printSeparator(PrettyPrinter out) {
            out.print(", ");
          }
        });
      }
      out.print(" {");
      out.println();
      out.indent(1);
      out.print(getParsedConstructorInvocationOpt());
      if (!out.isNewLine()) {
        out.println();
      }
      out.indent(1);
      out.join(blockStmts(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.println();
        }
      });
      if (!out.isNewLine()) {
        out.println();
      }
      out.print("}");
    }
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:498
   */
  public void typeCheck() {
    // 8.8.4 (8.4.4)
    TypeDecl exceptionType = typeThrowable();
    for (int i = 0; i < getNumException(); i++) {
      TypeDecl typeDecl = getException(i).type();
      if (!typeDecl.instanceOf(exceptionType)) {
        errorf("%s throws non throwable type %s", signature(), typeDecl.fullName());
      }
    }
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:547
   */
  public void emitInvokeConstructor(CodeGeneration gen) {
    int stackChange = -1;
    for (int i = 0; i < getNumParameter(); i++) {
      stackChange -= getParameter(i).type().variableSize();
    }
    if (hostType().needsEnclosing()) {
      stackChange -= 1;
    }
    if (hostType().needsSuperEnclosing()) {
      stackChange -= 1;
    }
    String classname = hostType().constantPoolName();
    String      desc = descName();
    String      name = "<init>";
    int index = gen.constantPool().addMethodref(classname, name, desc);
    gen.emit(Bytecode.INVOKESPECIAL, stackChange).add2(index);
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:142
   */
  private void generateBytecodes(CodeGeneration gen) {
    int label = gen.variableScopeLabel();
    int paramLength = 1;
    gen.addLocalVariableEntryAtCurrentPC("this", hostType().typeDescriptor(), 0, label);
    for (ParameterDeclaration p: getParameterList()) {
      paramLength += p.type().variableSize();
      if (paramLength > 255) {
        throw new Error("parameter list too large");
      }
      gen.addLocalVariableEntryAtCurrentPC(
        p.name(), p.type().typeDescriptor(), p.localNum(), label
      );
    }
    createBCode(gen);
    gen.emitReturn();
    gen.addVariableScopeLabel(label);
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:172
   */
  public void createBCode(CodeGeneration gen) {
    try {
    boolean needsInit = true;

    if (hasConstructorInvocation()) {
      getConstructorInvocation().createBCode(gen);
      Stmt stmt = getConstructorInvocation();
      if (stmt instanceof ExprStmt) {
        ExprStmt exprStmt = (ExprStmt) stmt;
        Expr expr = exprStmt.getExpr();
        if (!expr.isSuperConstructorAccess()) {
          needsInit = false;
        }

      }
    }

    if (needsEnclosing()) {
      gen.emitLoadReference(0);
      gen.emitLoadReference(1);
      String classname = hostType().constantPoolName();
      String desc = enclosing().typeDescriptor();
      String name = "this$0";
      int index = gen.constantPool().addFieldref(classname, name, desc);
      gen.emit(Bytecode.PUTFIELD, -2).add2(index);
    }

    int localIndex = offsetFirstEnclosingVariable();
    for (Iterator iter = hostType().enclosingVariables().iterator(); iter.hasNext(); ) {
        Variable v = (Variable) iter.next();
        gen.emitLoadReference(0);
        v.type().emitLoadLocal(gen, localIndex);
        String classname = hostType().constantPoolName();
        String desc = v.type().typeDescriptor();
        String name = "val$" + v.name();
        int index = gen.constantPool().addFieldref(classname, name, desc);
        gen.emit(Bytecode.PUTFIELD, -1 - v.type().variableSize()).add2(index);
        localIndex += v.type().variableSize();
    }

    if (needsInit) {
      TypeDecl typeDecl = hostType();
      for (int i = 0; i < typeDecl.getNumBodyDecl(); i++) {
        BodyDecl b = typeDecl.getBodyDecl(i);
        if (b instanceof FieldDeclaration && b.isBytecodeField()) {
          FieldDeclaration f = (FieldDeclaration) b;
          f.emitInstanceInitializer(gen, hostType());
        } else if (b instanceof InstanceInitializer) {
          b.createBCode(gen);
        }
      }
    }
    gen.maxLocals = Math.max(gen.maxLocals, getBlock().localNum());
    getBlock().createBCode(gen);
    } catch (Error e) {
      System.err.println(hostType().typeName() + ": " + this);
      throw e;
    }
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:292
   */
  public void generateMethod(DataOutputStream out, ConstantPool cp) throws IOException {
    out.writeChar(flags());
    out.writeChar(cp.addUtf8("<init>"));
    out.writeChar(cp.addUtf8(descName()));
    out.writeChar(attributes().size());
    for (Iterator itera = attributes().iterator(); itera.hasNext();) {
      ((Attribute) itera.next()).emit(out);
    }
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:311
   */
  public void touchMethod(ConstantPool cp) {
    cp.addUtf8("<init>");
    cp.addUtf8(descName());
    attributes();
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:433
   */
  public boolean clear() {
    getBlock().clear();
    setBlock(new Block(new List()));
    bytecodes_ConstantPool_values = null;
    return false;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:526
   */
  protected boolean addEnclosingVariables = true;
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:527
   */
  public void addEnclosingVariables() {
    if (!addEnclosingVariables) {
      return;
    }
    addEnclosingVariables = false;
    hostType().addEnclosingVariables();
    for (Iterator iter = hostType().enclosingVariables().iterator(); iter.hasNext(); ) {
      Variable v = (Variable) iter.next();
      getParameterList().add(new ParameterDeclaration(v.type(), "val$" + v.name()));
    }
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:567
   */
  public ConstructorDecl createAccessor() {
    ConstructorDecl c = (ConstructorDecl) hostType().getAccessor(this, "constructor");
    if (c != null) {
      return c;
    }

    // make sure enclosing varibles are added as parameters prior to building accessor
    addEnclosingVariables();

    Modifiers modifiers = new Modifiers(new List());
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));

    List parameters = createAccessorParameters();

    List exceptionList = new List();
    for (int i = 0; i < getNumException(); i++) {
      exceptionList.add(getException(i).type().createQualifiedAccess());
    }

    // add all parameters as arguments except for the dummy parameter
    List args = new List();
    for (int i = 0; i < parameters.getNumChildNoTransform() - 1; i++) {
      args.add(new VarAccess(((ParameterDeclaration) parameters.getChildNoTransform(i)).name()));
    }
    ConstructorAccess access = new ConstructorAccess("this", args);
    access.addEnclosingVariables = false;

    c = new ConstructorDecl(
      modifiers,
      name(),
      parameters,
      exceptionList,
      new Opt(
        new ExprStmt(
          access
        )
      ),
      new Block()
    );
    c = hostType().addConstructor(c);
    c.addEnclosingVariables = false;
    hostType().addAccessor(this, "constructor", c);
    return c;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:613
   */
  protected List createAccessorParameters() {
    List parameters = new List();
    for (int i=0; i<getNumParameter(); i++) {
      parameters.add(new ParameterDeclaration(getParameter(i).type(), getParameter(i).name()));
    }
    parameters.add(new ParameterDeclaration(createAnonymousJavaTypeDecl().createBoundAccess(), ("p" + getNumParameter())));
    return parameters;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:622
   */
  protected TypeDecl createAnonymousJavaTypeDecl() {
    ClassDecl classDecl =
        new ClassDecl(
            new Modifiers(new List().add(new Modifier("synthetic"))),
            "" + hostType().nextAnonymousIndex(),
            new Opt(),
            new List(),
            new List()
            );
    classDecl = hostType().addMemberClass(classDecl);
    hostType().addNestedType(classDecl);
    return classDecl;
  }
  /**
   * @aspect Transformations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Transformations.jrag:113
   */
  public void transformation() {
    // this$val as fields and constructor parameters
    addEnclosingVariables();
    super.transformation();
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Enums.jrag:197
   */
  protected void transformEnumConstructors() {
    super.transformEnumConstructors();
    getParameterList().insertChild(
      new ParameterDeclaration(new TypeAccess("java.lang", "String"), "@p0"),
      0
    );
    getParameterList().insertChild(
      new ParameterDeclaration(new TypeAccess("int"), "@p1"),
      1
    );
  }
  /**
   * Check if the enum constructor has an incorrect access modifier
   * @aspect Enums
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Enums.jrag:525
   */
  protected void checkEnum(EnumDecl enumDecl) {
    super.checkEnum(enumDecl);

    if (isPublic()) {
      error("enum constructors can not be declared public");
    } else if (isProtected()) {
      error("enum constructors can not be declared public");
    }

    if (hasParsedConstructorInvocation()) {
      ExprStmt invocation = (ExprStmt) getParsedConstructorInvocation();
      if (invocation.getExpr() instanceof SuperConstructorAccess) {
        error("can not call super() in enum constructor");
      }
    }
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:1388
   */
  public ConstructorDecl substitutedBodyDecl(Parameterization parTypeDecl) {
    return new ConstructorDeclSubstituted(
      (Modifiers) getModifiers().treeCopyNoTransform(),
      getID(),
      getParameterList().substitute(parTypeDecl),
      getExceptionList().substitute(parTypeDecl),
      new Opt(),
      new Block(),
      this
    );
  }
  /**
   * @declaredat ASTNode:1
   */
  public ConstructorDecl() {
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
    children = new ASTNode[6];
    setChild(new List(), 1);
    setChild(new List(), 2);
    setChild(new Opt(), 3);
  }
  /**
   * @declaredat ASTNode:16
   */
  public ConstructorDecl(Modifiers p0, String p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @declaredat ASTNode:24
   */
  public ConstructorDecl(Modifiers p0, beaver.Symbol p1, List<ParameterDeclaration> p2, List<Access> p3, Opt<Stmt> p4, Block p5) {
    setChild(p0, 0);
    setID(p1);
    setChild(p2, 1);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:35
   */
  protected int numChildren() {
    return 5;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:41
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:47
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    accessibleFrom_TypeDecl_reset();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    throwsException_TypeDecl_reset();
    name_reset();
    signature_reset();
    sameSignature_ConstructorDecl_reset();
    lessSpecificThan_ConstructorDecl_reset();
    parameterDeclaration_String_reset();
    circularThisInvocation_ConstructorDecl_reset();
    attributes_reset();
    descName_reset();
    bytecodes_ConstantPool_reset();
    flags_reset();
    offsetBeforeParameters_reset();
    offsetFirstEnclosingVariable_reset();
    offsetAfterParameters_reset();
    sourceConstructorDecl_reset();
    handlesException_TypeDecl_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:72
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:78
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:84
   */
  public ConstructorDecl clone() throws CloneNotSupportedException {
    ConstructorDecl node = (ConstructorDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:91
   */
  public ConstructorDecl copy() {
    try {
      ConstructorDecl node = (ConstructorDecl) clone();
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
   * @declaredat ASTNode:110
   */
  public ConstructorDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:119
   */
  public ConstructorDecl treeCopyNoTransform() {
    ConstructorDecl tree = (ConstructorDecl) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        switch (i) {
        case 5:
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
   * @declaredat ASTNode:144
   */
  public ConstructorDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:151
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((ConstructorDecl)node).tokenString_ID);    
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
   * Retrieves the ImplicitConstructorInvocation child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ImplicitConstructorInvocation child.
   * @apilevel low-level
   */
  public Stmt getImplicitConstructorInvocationNoTransform() {
    return (Stmt) getChildNoTransform(5);
  }
  /**
   * Retrieves the child position of the optional child ImplicitConstructorInvocation.
   * @return The the child position of the optional child ImplicitConstructorInvocation.
   * @apilevel low-level
   */
  protected int getImplicitConstructorInvocationChildPosition() {
    return 5;
  }
  /**
   * @aspect ImplicitConstructor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupConstructor.jrag:344
   */
  private Stmt refined_ImplicitConstructor_ConstructorDecl_getImplicitConstructorInvocation()
{ return new ExprStmt(new SuperConstructorAccess("super", new List())); }
  /**
   * @aspect ImplicitConstructor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupConstructor.jrag:355
   */
  private Stmt refined_ImplicitConstructor_ConstructorDecl_getConstructorInvocation()
{
    if (hasParsedConstructorInvocation()) {
      return getParsedConstructorInvocation();
    } else {
      return getImplicitConstructorInvocation();
    }
  }
  /**
   * @aspect Attributes
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:236
   */
  private Collection refined_Attributes_ConstructorDecl_attributes()
{
    ArrayList l = new ArrayList();
    CodeGeneration bytecodes = bytecodes(hostType().constantPool());
    l.add(new CodeAttribute(bytecodes, null));
    l.add(new ExceptionsAttribute(bytecodes, this));
    if (getModifiers().isSynthetic()) {
      l.add(new SyntheticAttribute(hostType().constantPool()));
    }
    return l;
  }
  /**
   * @aspect Flags
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Flags.jrag:94
   */
  private int refined_Flags_ConstructorDecl_flags()
{
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
    //if (isSynchronized()) res |= Modifiers.ACC_SYNCHRONIZED;
    //if (isStrictfp()) res |= Modifiers.ACC_STRICT;
    return res;
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
      if (!hostType().accessibleFrom(type)) {
        return false;
      } else if (isPublic()) {
        return true;
      } else if (isProtected()) {
        return true;
      } else if (isPrivate()) {
        return hostType().topLevelType() == type.topLevelType();
      } else {
        return hostPackage().equals(type.hostPackage());
      }
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
    boolean isDAafter_Variable_value = getBlock().isDAafter(v) && getBlock().checkReturnDA(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDAafter_Variable_value;
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
    boolean isDUafter_Variable_value = getBlock().isDUafter(v) && getBlock().checkReturnDU(v);
    if (isFinal && num == state().boundariesCrossed) {
      isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDUafter_Variable_value;
  }
  protected java.util.Map throwsException_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void throwsException_TypeDecl_reset() {
    throwsException_TypeDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean throwsException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (throwsException_TypeDecl_values == null) throwsException_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(throwsException_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)throwsException_TypeDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean throwsException_TypeDecl_value = throwsException_compute(exceptionType);
    if (isFinal && num == state().boundariesCrossed) {
      throwsException_TypeDecl_values.put(_parameters, Boolean.valueOf(throwsException_TypeDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return throwsException_TypeDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean throwsException_compute(TypeDecl exceptionType) {
      for (Access exception : getExceptionList()) {
        if (exceptionType.instanceOf(exception.type())) {
          return true;
        }
      }
      return false;
    }
  /**
   * @apilevel internal
   */
  protected boolean name_computed = false;
  /**
   * @apilevel internal
   */
  protected String name_value;
  /**
   * @apilevel internal
   */
  private void name_reset() {
    name_computed = false;
    name_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String name() {
    if(name_computed) {
      return name_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    name_value = getID();
    if (isFinal && num == state().boundariesCrossed) {
      name_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return name_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean signature_computed = false;
  /**
   * @apilevel internal
   */
  protected String signature_value;
  /**
   * @apilevel internal
   */
  private void signature_reset() {
    signature_computed = false;
    signature_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String signature() {
    if(signature_computed) {
      return signature_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    signature_value = signature_compute();
    if (isFinal && num == state().boundariesCrossed) {
      signature_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return signature_value;
  }
  /**
   * @apilevel internal
   */
  private String signature_compute() {
      StringBuffer s = new StringBuffer();
      s.append(name() + "(");
      for (int i = 0; i < getNumParameter(); i++) {
        s.append(getParameter(i).type().typeName());
        if (i != getNumParameter() - 1) {
          s.append(", ");
        }
      }
      s.append(")");
      return s.toString();
    }
  protected java.util.Map sameSignature_ConstructorDecl_values;
  /**
   * @apilevel internal
   */
  private void sameSignature_ConstructorDecl_reset() {
    sameSignature_ConstructorDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean sameSignature(ConstructorDecl c) {
    Object _parameters = c;
    if (sameSignature_ConstructorDecl_values == null) sameSignature_ConstructorDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(sameSignature_ConstructorDecl_values.containsKey(_parameters)) {
      return ((Boolean)sameSignature_ConstructorDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean sameSignature_ConstructorDecl_value = sameSignature_compute(c);
    if (isFinal && num == state().boundariesCrossed) {
      sameSignature_ConstructorDecl_values.put(_parameters, Boolean.valueOf(sameSignature_ConstructorDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return sameSignature_ConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean sameSignature_compute(ConstructorDecl c) {
      if (!name().equals(c.name())) {
        return false;
      }
      if (c.getNumParameter() != getNumParameter()) {
        return false;
      }
      for (int i = 0; i < getNumParameter(); i++) {
        if (!c.getParameter(i).type().equals(getParameter(i).type())) {
          return false;
        }
      }
      return true;
    }
  @ASTNodeAnnotation.Attribute
  public boolean moreSpecificThan(ConstructorDecl m) {
    ASTNode$State state = state();
    boolean moreSpecificThan_ConstructorDecl_value = m.lessSpecificThan(this) && !this.lessSpecificThan(m);

    return moreSpecificThan_ConstructorDecl_value;
  }
  protected java.util.Map lessSpecificThan_ConstructorDecl_values;
  /**
   * @apilevel internal
   */
  private void lessSpecificThan_ConstructorDecl_reset() {
    lessSpecificThan_ConstructorDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean lessSpecificThan(ConstructorDecl m) {
    Object _parameters = m;
    if (lessSpecificThan_ConstructorDecl_values == null) lessSpecificThan_ConstructorDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(lessSpecificThan_ConstructorDecl_values.containsKey(_parameters)) {
      return ((Boolean)lessSpecificThan_ConstructorDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean lessSpecificThan_ConstructorDecl_value = lessSpecificThan_compute(m);
    if (isFinal && num == state().boundariesCrossed) {
      lessSpecificThan_ConstructorDecl_values.put(_parameters, Boolean.valueOf(lessSpecificThan_ConstructorDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lessSpecificThan_ConstructorDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean lessSpecificThan_compute(ConstructorDecl m) {
      int numA = getNumParameter();
      int numB = m.getNumParameter();
      int num = Math.max(numA, numB);
      for (int i = 0; i < num; i++) {
        TypeDecl t1 = getParameter(Math.min(i, numA-1)).type();
        TypeDecl t2 = m.getParameter(Math.min(i, numB-1)).type();
        if (!t1.instanceOf(t2) && !t1.withinBounds(t2, Parameterization.RAW)) {
          return true;
        }
      }
      return false;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isImplicitConstructor() {
    ASTNode$State state = state();
    boolean isImplicitConstructor_value = isImplicitConstructor;

    return isImplicitConstructor_value;
  }
  @ASTNodeAnnotation.Attribute
  public Stmt getImplicitConstructorInvocation() {
    ASTNode$State state = state();
    Stmt getImplicitConstructorInvocation_value = getImplicitConstructorInvocation_compute();
    setChild(getImplicitConstructorInvocation_value, getImplicitConstructorInvocationChildPosition());

    Stmt node = (Stmt) this.getChild(getImplicitConstructorInvocationChildPosition());
    return node;
  }
  /**
   * @apilevel internal
   */
  private Stmt getImplicitConstructorInvocation_compute() {
      if (hostType().isEnumDecl()) {
        ConstructorAccess newAccess;
        if (hasParsedConstructorInvocation()) {
          ExprStmt stmt = (ExprStmt) getParsedConstructorInvocation();
          ConstructorAccess access = (ConstructorAccess) stmt.getExpr();
          newAccess = (ConstructorAccess) access.treeCopyNoTransform();
        } else {
          newAccess = new SuperConstructorAccess("super", new List());
        }
        if (!hostType().original().typeName().equals("java.lang.Enum")) {
          // java.lang.Enum calls the java.lang.Object constructor, with no extra params
          newAccess.getArgList().insertChild(new VarAccess("@p0"),0);
          newAccess.getArgList().insertChild(new VarAccess("@p1"),1);
        }
        return new ExprStmt(newAccess);
      }
      return refined_ImplicitConstructor_ConstructorDecl_getImplicitConstructorInvocation();
    }
  @ASTNodeAnnotation.Attribute
  public boolean hasConstructorInvocation() {
    ASTNode$State state = state();
    boolean hasConstructorInvocation_value = hasParsedConstructorInvocation() || !hostType().isObject();

    return hasConstructorInvocation_value;
  }
  /**
   * @attribute syn
   * @aspect ImplicitConstructor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupConstructor.jrag:355
   */
  @ASTNodeAnnotation.Attribute
  public Stmt getConstructorInvocation() {
    ASTNode$State state = state();
    try {
        if (!isSynthetic() && hostType().isEnumDecl()) {
          return getImplicitConstructorInvocation();
        }
        return refined_ImplicitConstructor_ConstructorDecl_getConstructorInvocation();
      }
    finally {
    }
  }
  protected java.util.Map parameterDeclaration_String_values;
  /**
   * @apilevel internal
   */
  private void parameterDeclaration_String_reset() {
    parameterDeclaration_String_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public SimpleSet parameterDeclaration(String name) {
    Object _parameters = name;
    if (parameterDeclaration_String_values == null) parameterDeclaration_String_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(parameterDeclaration_String_values.containsKey(_parameters)) {
      return (SimpleSet)parameterDeclaration_String_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    SimpleSet parameterDeclaration_String_value = parameterDeclaration_compute(name);
    if (isFinal && num == state().boundariesCrossed) {
      parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return parameterDeclaration_String_value;
  }
  /**
   * @apilevel internal
   */
  private SimpleSet parameterDeclaration_compute(String name) {
      for (int i = 0; i < getNumParameter(); i++) {
        if (getParameter(i).name().equals(name)) {
          return (ParameterDeclaration) getParameter(i);
        }
      }
      return SimpleSet.emptySet;
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
    boolean isPublic_value = getModifiers().isPublic();

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
  /**
   * @apilevel internal
   */
  private void circularThisInvocation_ConstructorDecl_reset() {
    circularThisInvocation_ConstructorDecl_values = null;
  }
  protected java.util.Map circularThisInvocation_ConstructorDecl_values;
  @ASTNodeAnnotation.Attribute
  public boolean circularThisInvocation(ConstructorDecl decl) {
    Object _parameters = decl;
    if (circularThisInvocation_ConstructorDecl_values == null) circularThisInvocation_ConstructorDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    ASTNode$State.CircularValue _value;
    if(circularThisInvocation_ConstructorDecl_values.containsKey(_parameters)) {
      Object _o = circularThisInvocation_ConstructorDecl_values.get(_parameters);
      if(!(_o instanceof ASTNode$State.CircularValue)) {
        return ((Boolean)_o).booleanValue();
      } else {
        _value = (ASTNode$State.CircularValue) _o;
      }
    } else {
      _value = new ASTNode$State.CircularValue();
      circularThisInvocation_ConstructorDecl_values.put(_parameters, _value);
      _value.value = Boolean.valueOf(true);
    }
    ASTNode$State state = state();
    boolean new_circularThisInvocation_ConstructorDecl_value;
    if (!state.IN_CIRCLE) {
      state.IN_CIRCLE = true;
      int num = state.boundariesCrossed;
      boolean isFinal = this.is$Final();
      // TODO: fixme
      // state().CIRCLE_INDEX = 1;
      do {
        _value.visited = new Integer(state.CIRCLE_INDEX);
        state.CHANGE = false;
        new_circularThisInvocation_ConstructorDecl_value = circularThisInvocation_compute(decl);
        if (new_circularThisInvocation_ConstructorDecl_value != ((Boolean)_value.value).booleanValue()) {
          state.CHANGE = true;
          _value.value = Boolean.valueOf(new_circularThisInvocation_ConstructorDecl_value);
        }
        state.CIRCLE_INDEX++;
      } while (state.CHANGE);
      if (isFinal && num == state().boundariesCrossed) {
        circularThisInvocation_ConstructorDecl_values.put(_parameters, new_circularThisInvocation_ConstructorDecl_value);
      } else {
        circularThisInvocation_ConstructorDecl_values.remove(_parameters);
        state.RESET_CYCLE = true;
        boolean $tmp = circularThisInvocation_compute(decl);
        state.RESET_CYCLE = false;
      }
      state.IN_CIRCLE = false;
      state.INTERMEDIATE_VALUE = false;
      return new_circularThisInvocation_ConstructorDecl_value;
    }
    if (!new Integer(state.CIRCLE_INDEX).equals(_value.visited)) {
      _value.visited = new Integer(state.CIRCLE_INDEX);
      new_circularThisInvocation_ConstructorDecl_value = circularThisInvocation_compute(decl);
      if (state.RESET_CYCLE) {
        circularThisInvocation_ConstructorDecl_values.remove(_parameters);
      }
      else if (new_circularThisInvocation_ConstructorDecl_value != ((Boolean)_value.value).booleanValue()) {
        state.CHANGE = true;
        _value.value = new_circularThisInvocation_ConstructorDecl_value;
      }
      state.INTERMEDIATE_VALUE = true;
      return new_circularThisInvocation_ConstructorDecl_value;
    }
    state.INTERMEDIATE_VALUE = true;
    return ((Boolean)_value.value).booleanValue();
  }
  /**
   * @apilevel internal
   */
  private boolean circularThisInvocation_compute(ConstructorDecl decl) {
      if (hasConstructorInvocation()) {
        Expr e = ((ExprStmt) getConstructorInvocation()).getExpr();
        if (e instanceof ConstructorAccess) {
          ConstructorDecl constructorDecl = ((ConstructorAccess) e).decl();
          if (constructorDecl == decl) {
            return true;
          }
          return constructorDecl.circularThisInvocation(decl);
        }
      }
      return false;
    }
  @ASTNodeAnnotation.Attribute
  public boolean hasModifiers() {
    ASTNode$State state = state();
    boolean hasModifiers_value = getModifiers().getNumModifier() > 0;

    return hasModifiers_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasExceptions() {
    ASTNode$State state = state();
    boolean hasExceptions_value = getNumException() > 0;

    return hasExceptions_value;
  }
  @ASTNodeAnnotation.Attribute
  public List<Stmt> blockStmts() {
    ASTNode$State state = state();
    List<Stmt> blockStmts_value = getBlock().getStmtList();

    return blockStmts_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    ASTNode$State state = state();
    TypeDecl type_value = unknownType();

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVoid() {
    ASTNode$State state = state();
    boolean isVoid_value = true;

    return isVoid_value;
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
      Collection c = refined_Attributes_ConstructorDecl_attributes();
      getModifiers().addRuntimeVisibleAnnotationsAttribute(c);
      getModifiers().addRuntimeInvisibleAnnotationsAttribute(c);
      return c;
    }
  /**
   * @apilevel internal
   */
  protected boolean descName_computed = false;
  /**
   * @apilevel internal
   */
  protected String descName_value;
  /**
   * @apilevel internal
   */
  private void descName_reset() {
    descName_computed = false;
    descName_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public String descName() {
    if(descName_computed) {
      return descName_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    descName_value = descName_compute();
    if (isFinal && num == state().boundariesCrossed) {
      descName_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return descName_value;
  }
  /**
   * @apilevel internal
   */
  private String descName_compute() {
      StringBuilder b = new StringBuilder();
      b.append("(");
      // this$0
      if (needsEnclosing()) {
        b.append(enclosing().typeDescriptor());
      }
      if (needsSuperEnclosing()) {
        b.append(superEnclosing().typeDescriptor());
      }
      // args
      for (int i=0; i<getNumParameter(); i++) {
        b.append(getParameter(i).type().typeDescriptor());
      }
      b.append(")V");
      return b.toString();
    }
  protected java.util.Map bytecodes_ConstantPool_values;
  /**
   * @apilevel internal
   */
  private void bytecodes_ConstantPool_reset() {
    bytecodes_ConstantPool_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public CodeGeneration bytecodes(ConstantPool constantPool) {
    Object _parameters = constantPool;
    if (bytecodes_ConstantPool_values == null) bytecodes_ConstantPool_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(bytecodes_ConstantPool_values.containsKey(_parameters)) {
      return (CodeGeneration)bytecodes_ConstantPool_values.get(_parameters);
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    CodeGeneration bytecodes_ConstantPool_value = bytecodes_compute(constantPool);
    if (isFinal && num == state().boundariesCrossed) {
      bytecodes_ConstantPool_values.put(_parameters, bytecodes_ConstantPool_value);
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return bytecodes_ConstantPool_value;
  }
  /**
   * @apilevel internal
   */
  private CodeGeneration bytecodes_compute(ConstantPool constantPool) {
      CodeGeneration gen = new CodeGeneration(constantPool);
      generateBytecodes(gen);
      if (!gen.numberFormatError()) {
        return gen;
      }
      gen = new CodeGeneration(constantPool, true);
      generateBytecodes(gen);
      if (!gen.numberFormatError()) {
        return gen;
      }
      throw new Error("Could not generate code for " + signature() + " in " + hostType().typeName());
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
      int res = refined_Flags_ConstructorDecl_flags();
      if (isVariableArity()) {
        res |= Modifiers.ACC_VARARGS;
      }
      return res;
    }
  @ASTNodeAnnotation.Attribute
  public boolean isBytecodeMethod() {
    ASTNode$State state = state();
    boolean isBytecodeMethod_value = true;

    return isBytecodeMethod_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean flush() {
    ASTNode$State state = state();
    boolean flush_value = false;

    return flush_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean needsEnclosing() {
    ASTNode$State state = state();
    boolean needsEnclosing_value = hostType().needsEnclosing();

    return needsEnclosing_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean needsSuperEnclosing() {
    ASTNode$State state = state();
    boolean needsSuperEnclosing_value = hostType().needsSuperEnclosing();

    return needsSuperEnclosing_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl enclosing() {
    ASTNode$State state = state();
    TypeDecl enclosing_value = hostType().enclosing();

    return enclosing_value;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl superEnclosing() {
    ASTNode$State state = state();
    TypeDecl superEnclosing_value = hostType().superEnclosing();

    return superEnclosing_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean offsetBeforeParameters_computed = false;
  /**
   * @apilevel internal
   */
  protected int offsetBeforeParameters_value;
  /**
   * @apilevel internal
   */
  private void offsetBeforeParameters_reset() {
    offsetBeforeParameters_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int offsetBeforeParameters() {
    if(offsetBeforeParameters_computed) {
      return offsetBeforeParameters_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    offsetBeforeParameters_value = offsetBeforeParameters_compute();
    if (isFinal && num == state().boundariesCrossed) {
      offsetBeforeParameters_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return offsetBeforeParameters_value;
  }
  /**
   * @apilevel internal
   */
  private int offsetBeforeParameters_compute() {
      int i = 1;
      if (hostType().needsEnclosing()) {
        i++;
      }
      if (hostType().needsSuperEnclosing()) {
        i++;
      }
      return i;
    }
  /**
   * @apilevel internal
   */
  protected boolean offsetFirstEnclosingVariable_computed = false;
  /**
   * @apilevel internal
   */
  protected int offsetFirstEnclosingVariable_value;
  /**
   * @apilevel internal
   */
  private void offsetFirstEnclosingVariable_reset() {
    offsetFirstEnclosingVariable_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int offsetFirstEnclosingVariable() {
    if(offsetFirstEnclosingVariable_computed) {
      return offsetFirstEnclosingVariable_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    offsetFirstEnclosingVariable_value = offsetFirstEnclosingVariable_compute();
    if (isFinal && num == state().boundariesCrossed) {
      offsetFirstEnclosingVariable_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return offsetFirstEnclosingVariable_value;
  }
  /**
   * @apilevel internal
   */
  private int offsetFirstEnclosingVariable_compute() {
      int localIndex = offsetBeforeParameters();
      Collection vars = hostType().enclosingVariables();
      if (vars.isEmpty()) {
        return localIndex;
      }
      String name = "val$" + ((Variable)vars.iterator().next()).name();
      for (int i = 0; !getParameter(i).name().equals(name); i++) {
        localIndex += getParameter(i).type().variableSize();
      }
      return localIndex;
    }
  /**
   * @attribute syn
   * @aspect LocalNum
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\LocalNum.jrag:135
   */
  @ASTNodeAnnotation.Attribute
  public int localIndexOfEnclosingVariable(Variable v) {
    ASTNode$State state = state();
    try {
        int localIndex  = offsetFirstEnclosingVariable();
        Iterator iter = hostType().enclosingVariables().iterator();
        Variable varDecl = (Variable)iter.next();
        while(varDecl != v && iter.hasNext()) {
          localIndex += varDecl.type().variableSize();
          varDecl = (Variable)iter.next();
        }
        return localIndex;
      }
    finally {
    }
  }
  /**
   * @apilevel internal
   */
  protected boolean offsetAfterParameters_computed = false;
  /**
   * @apilevel internal
   */
  protected int offsetAfterParameters_value;
  /**
   * @apilevel internal
   */
  private void offsetAfterParameters_reset() {
    offsetAfterParameters_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int offsetAfterParameters() {
    if(offsetAfterParameters_computed) {
      return offsetAfterParameters_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    offsetAfterParameters_value = offsetAfterParameters_compute();
    if (isFinal && num == state().boundariesCrossed) {
      offsetAfterParameters_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return offsetAfterParameters_value;
  }
  /**
   * @apilevel internal
   */
  private int offsetAfterParameters_compute() {
      if (getNumParameter() == 0) {
        return offsetBeforeParameters();
      } else {
        ParameterDeclaration last = getParameter(getNumParameter()-1);
        return last.localNum() + last.type().variableSize();
      }
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
    sourceConstructorDecl_value = this;
    if (isFinal && num == state().boundariesCrossed) {
      sourceConstructorDecl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return sourceConstructorDecl_value;
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:299
   */
  @ASTNodeAnnotation.Attribute
  public boolean applicableBySubtyping(List<Expr> argList) {
    ASTNode$State state = state();
    try {
        if (getNumParameter() != argList.getNumChild()) {
          return false;
        }
        for (int i = 0; i < getNumParameter(); i++) {
          TypeDecl arg = argList.getChild(i).type();
          TypeDecl param = getParameter(i).type();
          if (!param.isTypeVariable()) {
            if (!arg.instanceOf(param)) {
              return false;
            }
          } else {
            if (!arg.withinBounds(param, Parameterization.RAW)) {
              return false;
            }
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:330
   */
  @ASTNodeAnnotation.Attribute
  public boolean applicableByMethodInvocationConversion(List<Expr> argList) {
    ASTNode$State state = state();
    try {
        if (getNumParameter() != argList.getNumChild()) {
          return false;
        }
        for (int i = 0; i < getNumParameter(); i++) {
          TypeDecl arg = argList.getChild(i).type();
          if (!arg.methodInvocationConversionTo(getParameter(i).type())) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:355
   */
  @ASTNodeAnnotation.Attribute
  public boolean applicableVariableArity(List argList) {
    ASTNode$State state = state();
    try {
        for (int i = 0; i < getNumParameter() - 1; i++) {
          TypeDecl arg = ((Expr) argList.getChild(i)).type();
          if (!arg.methodInvocationConversionTo(getParameter(i).type())) {
            return false;
          }
        }
        for (int i = getNumParameter() - 1; i < argList.getNumChild(); i++) {
          TypeDecl arg = ((Expr) argList.getChild(i)).type();
          if (!arg.methodInvocationConversionTo(lastParameter().type().componentType())) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * Note: isGeneric must be called first to check if this declaration is generic.
   * Otherwise this attribute will throw an error!
   * @return original generic declaration of this constructor.
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:397
   */
  @ASTNodeAnnotation.Attribute
  public GenericConstructorDecl genericDecl() {
    ASTNode$State state = state();
    try {
        throw new Error("can not evaulate generic declaration of non-generic constructor");
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:535
   */
  @ASTNodeAnnotation.Attribute
  public boolean potentiallyApplicable(List<Expr> argList) {
    ASTNode$State state = state();
    try {
        int argArity = argList.getNumChild();
        if (!isVariableArity()) {
          if (argArity != arity()) {
            return false;
          }
          for (int i = 0; i < argArity; i++) {
            Expr expr = argList.getChild(i);
            if (!expr.potentiallyCompatible(getParameter(i).type(), this)) {
              return false;
            }
          }
        } else {
        //if (isVariableArity()) {
          if (!(argArity >= arity()-1)) {
            return false;
          }
          for (int i = 0; i < arity() - 2; i++) {
            Expr expr = argList.getChild(i);
            if (!expr.potentiallyCompatible(getParameter(i).type(), this)) {
              return false;
            }
          }
          TypeDecl varArgType = getParameter(arity()-1).type();
          if (argArity == arity()) {
            Expr expr = argList.getChild(argArity - 1);
            if (!expr.potentiallyCompatible(varArgType, this)
                && !expr.potentiallyCompatible(varArgType.componentType(), this)) {
              return false;
            }
          } else if (argArity > arity()) {
            for (int i = arity()-1; i < argArity; i++) {
              Expr expr = argList.getChild(i);
              if (!expr.potentiallyCompatible(varArgType.componentType(), this)) {
                return false;
              }
            }
          }
        }
    
        return true;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public int arity() {
    ASTNode$State state = state();
    int arity_value = getNumParameter();

    return arity_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVariableArity() {
    ASTNode$State state = state();
    boolean isVariableArity_value = getNumParameter() == 0 ? false : getParameter(getNumParameter()-1).isVariableArity();

    return isVariableArity_value;
  }
  @ASTNodeAnnotation.Attribute
  public ParameterDeclaration lastParameter() {
    ASTNode$State state = state();
    ParameterDeclaration lastParameter_value = getParameter(getNumParameter() - 1);

    return lastParameter_value;
  }
  @ASTNodeAnnotation.Attribute
  public ConstructorDecl erasedConstructor() {
    ASTNode$State state = state();
    ConstructorDecl erasedConstructor_value = this;

    return erasedConstructor_value;
  }
  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\GenericsCodegen.jrag:388
   */
  @ASTNodeAnnotation.Attribute
  public boolean needsSignatureAttribute() {
    ASTNode$State state = state();
    try {
        for (int i = 0; i < getNumParameter(); i++) {
          if (getParameter(i).type().needsSignatureAttribute()) {
            return true;
          }
        }
        return false;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasAnnotationSafeVarargs() {
    ASTNode$State state = state();
    boolean hasAnnotationSafeVarargs_value = getModifiers().hasAnnotationSafeVarargs();

    return hasAnnotationSafeVarargs_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hasIllegalAnnotationSafeVarargs() {
    ASTNode$State state = state();
    boolean hasIllegalAnnotationSafeVarargs_value = hasAnnotationSafeVarargs() && !isVariableArity();

    return hasIllegalAnnotationSafeVarargs_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\MethodSignature.jrag:938
   */
  @ASTNodeAnnotation.Attribute
  public boolean applicableByStrictInvocation(Expr expr, List<Expr> argList) {
    ASTNode$State state = state();
    try {
        if (getNumParameter() != argList.getNumChild()) {
          return false;
        }
        for (int i = 0; i < getNumParameter(); i++) {
          Expr arg = argList.getChild(i);
          if (!arg.pertinentToApplicability(expr, this, i)) {
            continue;
          }
          if (!arg.compatibleStrictContext(getParameter(i).type())) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\MethodSignature.jrag:954
   */
  @ASTNodeAnnotation.Attribute
  public boolean applicableByLooseInvocation(Expr expr, List<Expr> argList) {
    ASTNode$State state = state();
    try {
        if (getNumParameter() != argList.getNumChild()) {
          return false;
        }
        for (int i = 0; i < getNumParameter(); i++) {
          Expr arg = argList.getChild(i);
          if (!arg.pertinentToApplicability(expr, this, i)) {
            continue;
          }
          if (!arg.compatibleLooseContext(getParameter(i).type())) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect MethodSignature18
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\MethodSignature.jrag:970
   */
  @ASTNodeAnnotation.Attribute
  public boolean applicableByVariableArityInvocation(Expr expr, List<Expr> argList) {
    ASTNode$State state = state();
    try {
        for (int i = 0; i < getNumParameter() - 1; i++) {
          Expr arg = argList.getChild(i);
          if (!arg.pertinentToApplicability(expr, this, i)) {
            continue;
          }
          if (!arg.compatibleLooseContext(getParameter(i).type())) {
            return false;
          }
        }
        for (int i = getNumParameter() - 1; i < argList.getNumChild(); i++) {
          Expr arg = argList.getChild(i);
          if (!arg.pertinentToApplicability(expr, this, i)) {
            continue;
          }
          if (!arg.compatibleLooseContext(lastParameter().type().componentType())) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:83
   */
  @ASTNodeAnnotation.Attribute
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if (handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(handlesException_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)handlesException_TypeDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean handlesException_TypeDecl_value = getParent().Define_boolean_handlesException(this, null, exceptionType);
    if (isFinal && num == state().boundariesCrossed) {
      handlesException_TypeDecl_values.put(_parameters, Boolean.valueOf(handlesException_TypeDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return handlesException_TypeDecl_value;
  }
  protected java.util.Map handlesException_TypeDecl_values;
  /**
   * @apilevel internal
   */
  private void handlesException_TypeDecl_reset() {
    handlesException_TypeDecl_values = null;
  }
  /**
   * @attribute inh
   * @aspect TypeAnalysis
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeAnalysis.jrag:293
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl unknownType() {
    ASTNode$State state = state();
    TypeDecl unknownType_value = getParent().Define_TypeDecl_unknownType(this, null);

    return unknownType_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:337
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockNoTransform()) {
      return hasConstructorInvocation() ? getConstructorInvocation().isDAafter(v) : isDAbefore(v);
    }
    else {
      return super.Define_boolean_isDAbefore(caller, child, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:856
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockNoTransform()) {
      return hasConstructorInvocation() ? getConstructorInvocation().isDUafter(v) : isDUbefore(v);
    }
    else {
      return super.Define_boolean_isDUbefore(caller, child, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:197
   * @apilevel internal
   */
  public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return throwsException(exceptionType);
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return throwsException(exceptionType);
    }
    else if (caller == getBlockNoTransform()) {
      return throwsException(exceptionType);
    }
    else {
      return getParent().Define_boolean_handlesException(this, caller, exceptionType);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:81
   * @apilevel internal
   */
  public Collection Define_Collection_lookupMethod(ASTNode caller, ASTNode child, String name) {
    if (caller == getImplicitConstructorInvocationNoTransform()){
    Collection c = new ArrayList();
    for (Iterator iter = lookupMethod(name).iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (!hostType().memberMethods(name).contains(m) || m.isStatic()) {
        c.add(m);
      }
    }
    return c;
  }
    else if (caller == getParsedConstructorInvocationOptNoTransform()){
    Collection c = new ArrayList();
    for (Iterator iter = lookupMethod(name).iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (!hostType().memberMethods(name).contains(m) || m.isStatic()) {
        c.add(m);
      }
    }
    return c;
  }
    else {
      return getParent().Define_Collection_lookupMethod(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupVariable.jrag:110
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return parameterDeclaration(name);
    }
    else if (caller == getImplicitConstructorInvocationNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    if (!set.isEmpty()) {
      return set;
    }
    for (Iterator iter = lookupVariable(name).iterator(); iter.hasNext(); ) {
      Variable v = (Variable) iter.next();
      if (!hostType().memberFields(name).contains(v) || v.isStatic()) {
        set = set.add(v);
      }
    }
    return set;
  }
    else if (caller == getParsedConstructorInvocationOptNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    if (!set.isEmpty()) {
      return set;
    }
    for (Iterator iter = lookupVariable(name).iterator(); iter.hasNext(); ) {
      Variable v = (Variable) iter.next();
      if (!hostType().memberFields(name).contains(v) || v.isStatic()) {
        set = set.add(v);
      }
    }
    return set;
  }
    else if (caller == getBlockNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    if (!set.isEmpty()) {
      return set;
    }
    return lookupVariable(name);
  }
    else {
      return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:323
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:324
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:325
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:312
   * @apilevel internal
   */
  public ASTNode Define_ASTNode_enclosingBlock(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return this;
    }
    else {
      return getParent().Define_ASTNode_enclosingBlock(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\SyntacticClassification.jrag:140
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return NameType.EXPRESSION_NAME;
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return NameType.EXPRESSION_NAME;
    }
    else if (caller == getExceptionListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_NameType_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:601
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_enclosingInstance(ASTNode caller, ASTNode child) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return unknownType();
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return unknownType();
    }
    else {
      return getParent().Define_TypeDecl_enclosingInstance(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:171
   * @apilevel internal
   */
  public boolean Define_boolean_inExplicitConstructorInvocation(ASTNode caller, ASTNode child) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return true;
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_inExplicitConstructorInvocation(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:180
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_enclosingExplicitConstructorHostType(ASTNode caller, ASTNode child) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return hostType();
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return hostType();
    }
    else {
      return getParent().Define_TypeDecl_enclosingExplicitConstructorHostType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:193
   * @apilevel internal
   */
  public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return false;
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return false;
    }
    else if (caller == getBlockNoTransform()) {
      return false;
    }
    else {
      return getParent().Define_boolean_inStaticContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:59
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return !hasParsedConstructorInvocation()
      ? true
      : getParsedConstructorInvocation().canCompleteNormally();
    }
    else if (caller == getImplicitConstructorInvocationNoTransform()) {
      return true;
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:79
   * @apilevel internal
   */
  public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_boolean_isMethodParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:80
   * @apilevel internal
   */
  public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    else {
      return getParent().Define_boolean_isConstructorParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:81
   * @apilevel internal
   */
  public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\LocalNum.jrag:164
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return offsetAfterParameters();
    }
    else if (caller == getParameterListNoTransform()) {
      int index = caller.getIndexOfChild(child);
      {
    if (index == 0) {
      return offsetBeforeParameters();
    } else {
      ParameterDeclaration last = getParameter(index-1);
      return last.localNum() + last.type().variableSize();
    }
  }
    }
    else {
      return getParent().Define_int_localNum(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:116
   * @apilevel internal
   */
  public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if (caller == getModifiersNoTransform()) {
      return name.equals("CONSTRUCTOR");
    }
    else {
      return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\VariableArityParameters.jrag:43
   * @apilevel internal
   */
  public boolean Define_boolean_variableArityValid(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int i = caller.getIndexOfChild(child);
      return i == getNumParameter() - 1;
    }
    else {
      return getParent().Define_boolean_variableArityValid(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\EffectivelyFinal.jrag:61
   * @apilevel internal
   */
  public boolean Define_boolean_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getImplicitConstructorInvocationNoTransform()) {
      return false;
    }
    else if (caller == getParsedConstructorInvocationOptNoTransform()) {
      return false;
    }
    else if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      {
    return getBlock().modifiedInScope(var) || getConstructorInvocation().modifiedInScope(var);
  }
    }
    else {
      return getParent().Define_boolean_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PreciseRethrow.jrag:208
   * @apilevel internal
   */
  public boolean Define_boolean_isCatchParam(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_boolean_isCatchParam(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
