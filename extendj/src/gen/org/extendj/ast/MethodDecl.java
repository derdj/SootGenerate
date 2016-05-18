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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:92
 * @production MethodDecl : {@link MemberDecl} ::= <span class="component">{@link Modifiers}</span> <span class="component">TypeAccess:{@link Access}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">Parameter:{@link ParameterDeclaration}*</span> <span class="component">Exception:{@link Access}*</span> <span class="component">[{@link Block}]</span>;

 */
public class MethodDecl extends MemberDecl implements Cloneable, SimpleSet, Iterator, ExceptionHolder {
  /**
   * @aspect BoundNames
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BoundNames.jrag:97
   */
  public Access createBoundAccess(List args) {
    if (isStatic()) {
      return hostType().createQualifiedAccess().qualifiesAccess(
        new BoundMethodAccess(name(), args, this)
      );
    }
    return new BoundMethodAccess(name(), args, this);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:177
   */
  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:181
   */
  public boolean isSingleton() { return true; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:182
   */
  public boolean isSingleton(Object o) { return contains(o); }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:185
   */
  private MethodDecl iterElem;
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:186
   */
  public Iterator iterator() { iterElem = this; return this; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:187
   */
  public boolean hasNext() { return iterElem != null; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:188
   */
  public Object next() { Object o = iterElem; iterElem = null; return o; }
  /**
   * @aspect DataStructures
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DataStructures.jrag:189
   */
  public void remove() { throw new UnsupportedOperationException(); }
  /**
   * @aspect NameCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:131
   */
  public void nameCheck() {
    // 8.4
    // 8.4.2
    if (hostType().methodsSignature(signature()).size() > 1) {
      errorf("method with signature %s is multiply declared in type %s", signature(),
          hostType().typeName());
    }
    // 8.4.3.4
    if (isNative() && hasBlock()) {
      error("native methods must have an empty semicolon body");
    }
    // 8.4.5
    if (isAbstract() && hasBlock()) {
      error("abstract methods must have an empty semicolon body");
    }
    // 8.4.5
    if (!hasBlock() && !(isNative() || isAbstract())) {
      error("only abstract and native methods may have an empty semicolon body");
    }
  }
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PrettyPrint.jadd:66
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
    if (hasBlock()) {
      out.print(" ");
      out.print(getBlock());
    } else {
      out.print(";");
    }
  }
  /**
   * @aspect TypeCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:453
   */
  public void typeCheck() {
    // Thrown vs super class method see MethodDecl.nameCheck
    // 8.4.4
    TypeDecl exceptionType = typeThrowable();
    for (int i = 0; i < getNumException(); i++) {
      TypeDecl typeDecl = getException(i).type();
      if (!typeDecl.instanceOf(exceptionType)) {
        errorf("%s throws non throwable type %s", signature(), typeDecl.fullName());
      }
    }

    // check returns
    if (!isVoid() && hasBlock() && getBlock().canCompleteNormally()) {
      error("the body of a non void method may not complete normally");
    }

  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:504
   */
  public void emitInvokeMethod(CodeGeneration gen, TypeDecl hostType) {
    if (hostType.isInterfaceDecl()) {
      String classname = hostType.constantPoolName();
      String      desc = descName();
      String      name = name();
      int index = gen.constantPool().addInterfaceMethodref(classname, name, desc);
      int numArg = 1; // instance
      for (int i = 0; i < getNumParameter(); i++) {
        numArg += getParameter(i).type().variableSize();
      }
      int stackChange = type().variableSize() - numArg;
      gen.emit(Bytecode.INVOKEINTERFACE, stackChange)
            .add2(index).add(numArg).add(0);
    } else {
      String classname = hostType.constantPoolName();
      String      desc = descName();
      String      name = name();
      int index = gen.constantPool().addMethodref(classname, name, desc);
      int stackChange = type().variableSize();
      for (int i = 0; i < getNumParameter(); i++) {
          stackChange -= getParameter(i).type().variableSize();
      }
      if (isStatic()) {
        gen.emit(Bytecode.INVOKESTATIC, stackChange).add2(index);
      } else {
        stackChange -= 1;
        gen.emit(Bytecode.INVOKEVIRTUAL, stackChange).add2(index);
      }
    }
  }
  /**
   * @aspect CodeGeneration
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CodeGeneration.jrag:535
   */
  public void emitInvokeSpecialMethod(CodeGeneration gen, TypeDecl hostType) {
    String classname = hostType.constantPoolName();
    String      desc = descName();
    String      name = name();
    int index = gen.constantPool().addMethodref(classname, name, desc);
    int stackChange = type().variableSize() - 1;
    for (int i = 0; i < getNumParameter(); i++) {
      stackChange -= getParameter(i).type().variableSize();
    }
    gen.emit(Bytecode.INVOKESPECIAL, stackChange).add2(index);
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:106
   */
  private void generateBytecodes(CodeGeneration gen) {
    int label = gen.variableScopeLabel();
    int paramLength = 0;
    if (!isStatic()) {
      paramLength += 1;
      gen.addLocalVariableEntryAtCurrentPC("this", hostType().typeDescriptor(), 0, label);
    }
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
    if (type() instanceof VoidType) // TODO: canCompleteNormally check as well
      gen.emitReturn();
    gen.addVariableScopeLabel(label);
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:160
   */
  public void createBCode(CodeGeneration gen) {
    try {
      if (hasBlock()) {
        gen.maxLocals = Math.max(gen.maxLocals, getBlock().localNum());
        getBlock().createBCode(gen);
      }
    } catch (Error e) {
      System.err.println(hostType().typeName() + ": " + this);
      throw e;
    }
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:283
   */
  public void generateMethod(DataOutputStream out, ConstantPool cp) throws IOException {
    out.writeChar(flags());
    out.writeChar(cp.addUtf8(name()));
    out.writeChar(cp.addUtf8(descName()));
    out.writeChar(attributes().size());
    for (Iterator itera = attributes().iterator(); itera.hasNext();) {
      ((Attribute) itera.next()).emit(out);
    }
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:305
   */
  public void touchMethod(ConstantPool cp) {
    cp.addUtf8(name());
    cp.addUtf8(descName());
    attributes();
  }
  /**
   * @aspect GenerateClassfile
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\GenerateClassfile.jrag:420
   */
  public boolean clear() {
    if (hasBlock()) {
      getBlock().clear();
      setBlock(new Block(new List()));
    }
    bytecodes_ConstantPool_values = null;
    return false;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:240
   */
  public MethodDecl createAccessor(TypeDecl methodQualifier) {
    MethodDecl m = (MethodDecl) methodQualifier.getAccessor(this, "method");
    if (m != null) {
      return m;
    }

    int accessorIndex = methodQualifier.accessorCounter++;

    List parameterList = new List();
    for (int i = 0; i < getNumParameter(); i++) {
      parameterList.add(new ParameterDeclaration(
            // We don't need to create a qualified access to the type here
            // since there can be no ambiguity concerning unqualified
            // type names in an inner/enclosing class
            // Jesper 2012-05-04
            // FALSE! We need to create a qualified access in case the
            // method we are generating an access for is not declared
            // in the methodQualifier type
            getParameter(i).type().createQualifiedAccess(),
            getParameter(i).name()));
    }
    List exceptionList = new List();
    for (int i = 0; i < getNumException(); i++) {
      exceptionList.add((Access) getException(i).treeCopyNoTransform());
    }

    // add synthetic flag to modifiers
    Modifiers modifiers = new Modifiers(new List());
    if (getModifiers().isStatic()) {
      modifiers.addModifier(new Modifier("static"));
    }
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));
    // build accessor declaration
    m = new MethodDecl(
      modifiers,
      getTypeAccess().type().createQualifiedAccess(),
      name() + "$access$" + accessorIndex,
      parameterList,
      exceptionList,
      new Opt(
        new Block(
          new List().add(
            createAccessorStmt()
          )
        )
      )
    );
    m = methodQualifier.addMemberMethod(m);
    methodQualifier.addAccessor(this, "method", m);
    return m;
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:293
   */
  private Stmt createAccessorStmt() {
    List argumentList = new List();
    for (int i = 0; i < getNumParameter(); i++) {
      argumentList.add(new VarAccess(getParameter(i).name()));
    }
    Access access = new BoundMethodAccess(name(), argumentList, this);
    if (!isStatic()) {
      access = new ThisAccess("this").qualifiesAccess(access);
    }
    return isVoid() ? (Stmt) new ExprStmt(access) : new ReturnStmt(new Opt(access));
  }
  /**
   * @aspect InnerClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\InnerClasses.jrag:305
   */
  public MethodDecl createSuperAccessor(TypeDecl methodQualifier) {
    MethodDecl m = (MethodDecl) methodQualifier.getAccessor(this, "method_super");
    if (m != null) {
      return m;
    }

    int accessorIndex = methodQualifier.accessorCounter++;
    List parameters = new List();
    List args = new List();
    for (int i = 0; i < getNumParameter(); i++) {
      parameters.add(new ParameterDeclaration(getParameter(i).type(), getParameter(i).name()));
      args.add(new VarAccess(getParameter(i).name()));
    }
    Stmt stmt;
    if (type().isVoid()) {
      stmt = new ExprStmt(new SuperAccess("super").qualifiesAccess(new MethodAccess(name(), args)));
    } else {
      stmt = new ReturnStmt(new Opt(new SuperAccess("super").qualifiesAccess(new MethodAccess(name(), args))));
    }
    m = new MethodDecl(
      new Modifiers(new List().add(new Modifier("synthetic"))),
      type().createQualifiedAccess(),
      name() + "$access$" + accessorIndex,
      parameters,
      new List(),
      new Opt(
        new Block(
          new List().add(stmt)
        )
      )
    );
    m = methodQualifier.addMemberMethod(m);
    methodQualifier.addAccessor(this, "method_super", m);
    return m;
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:1365
   */
  public BodyDecl substitutedBodyDecl(Parameterization parTypeDecl) {
    MethodDecl m = new MethodDeclSubstituted(
      (Modifiers) getModifiers().treeCopyNoTransform(),
      getTypeAccess().type().substituteReturnType(parTypeDecl),
      getID(),
      getParameterList().substitute(parTypeDecl),
      getExceptionList().substitute(parTypeDecl),
      substituteBody(parTypeDecl),
      this
    );
    return m;
  }
  /**
   * @aspect LookupParTypeDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Generics.jrag:1378
   */
  public Opt substituteBody(Parameterization parTypeDecl) {
    return new Opt();
  }
  /**
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:110
   */
  public void addRuntimeVisibleParameterAnnotationsAttribute(Collection c) {
    boolean foundVisibleAnnotations = false;
    Collection annotations = new ArrayList(getNumParameter());
    for (int i = 0; i < getNumParameter(); i++) {
      Collection a = getParameter(i).getModifiers().runtimeVisibleAnnotations();
      if (!a.isEmpty()) {
        foundVisibleAnnotations = true;
      }
      annotations.add(a);
    }
    if (foundVisibleAnnotations) {
      c.add(new ParameterAnnotationsAttribute(hostType().constantPool(), annotations, "RuntimeVisibleParameterAnnotations"));
    }
  }
  /**
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:136
   */
  public void addRuntimeInvisibleParameterAnnotationsAttribute(Collection c) {
    boolean foundInvisibleAnnotations = false;
    Collection annotations = new ArrayList(getNumParameter());
    for (int i = 0; i < getNumParameter(); i++) {
      Collection a = getParameter(i).getModifiers().runtimeInvisibleAnnotations();
      if (!a.isEmpty()) {
        foundInvisibleAnnotations = true;
      }
      annotations.add(a);
    }
    if (foundInvisibleAnnotations) {
      c.add(new ParameterAnnotationsAttribute(hostType().constantPool(), annotations, "RuntimeInvisibleParameterAnnotations"));
    }
  }
  /**
   * @aspect GenericsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\GenericsCodegen.jrag:238
   */
  public void transformation() {
    super.transformation();
    HashSet processed = new HashSet();
    for (Iterator iter = hostType().bridgeCandidates(signature()).iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (this.overrides(m)) {
        MethodDecl erased = m.erasedMethod();
        if (!erased.signature().equals(signature()) || erased.type().erasure() != type().erasure()) {
          StringBuilder keyBuffer = new StringBuilder();
          for (int i = 0; i < getNumParameter(); i++) {
            keyBuffer.append(erased.getParameter(i).type().erasure().fullName());
          }
          keyBuffer.append(erased.type().erasure().fullName());
          String key = keyBuffer.toString();
          if (!processed.contains(key)) {
            processed.add(key);

            List args = new List();
            List parameters = new List();
            for (int i = 0; i < getNumParameter(); i++) {
              args.add(new CastExpr(getParameter(i).type().erasure().createBoundAccess(), new VarAccess("p" + i)));
              parameters.add(new ParameterDeclaration(erased.getParameter(i).type().erasure(), "p" + i));
            }
            Stmt stmt;
            if (type().isVoid()) {
              stmt = new ExprStmt(
                  createBoundAccess(
                    args
                    )
                  );
            } else {
              stmt = new ReturnStmt(
                  createBoundAccess(
                    args
                    )
                  );
            }
            List modifiersList = new List();
            if (isPublic()) {
              modifiersList.add(new Modifier("public"));
            } else if (isProtected()) {
              modifiersList.add(new Modifier("protected"));
            } else if (isPrivate()) {
              modifiersList.add(new Modifier("private"));
            }
            MethodDecl bridge = new BridgeMethodDecl(
                new Modifiers(modifiersList),
                erased.type().erasure().createBoundAccess(),
                erased.name(),
                parameters,
                (List) getExceptionList().treeCopyNoTransform(),
                new Opt(
                  new Block(
                    new List().add(stmt)
                    )
                  )
                );
            hostType().addBodyDecl(bridge);
          }
        }
      }
    }
  }
  /**
   * Check if the method is missing a SafeVarargs annotation.
   * @aspect SafeVarargs
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\SafeVarargs.jrag:117
   */
  public void checkWarnings() {
    // Check for illegal use of @SafeVarargs.
    super.checkWarnings();

    if (!suppressWarnings("unchecked")
        && !hasAnnotationSafeVarargs()
        && isVariableArity()
        && !getParameter(getNumParameter()-1).type().isReifiable()) {
      warning("possible heap pollution for "
          + "variable arity parameter");
    }
  }
  /**
   * Checks that the argument exception is a subtype to all exceptions
   * in the methods throws-clause. This takes the position of the type
   * parameters into account.
   * @aspect FunctionDescriptor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\FunctionDescriptor.jrag:209
   */
  public boolean subtypeThrowsClause(Access exception) {
    boolean foundCompatible = false;
    for (Access throwsException : getExceptionList()) {
      if (exception.type().strictSubtype(throwsException.type())) {
        foundCompatible = true;
        break;
      }
    }
    return foundCompatible;
  }
  /**
   * Checks that the argument exception is a subtype to all exceptions
   * in the methods throws-clause. Performs erasure on all types before
   * comparing them.
   * @aspect FunctionDescriptor
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\FunctionDescriptor.jrag:225
   */
  public boolean subtypeThrowsClauseErased(Access exception) {
    boolean foundCompatible = false;
    for (Access throwsException : getExceptionList()) {
      if (exception.type().erasure().strictSubtype(throwsException.type().erasure())) {
        foundCompatible = true;
        break;
      }
    }
    return foundCompatible;
  }
  /**
   * @declaredat ASTNode:1
   */
  public MethodDecl() {
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
    children = new ASTNode[5];
    setChild(new List(), 2);
    setChild(new List(), 3);
    setChild(new Opt(), 4);
  }
  /**
   * @declaredat ASTNode:16
   */
  public MethodDecl(Modifiers p0, Access p1, String p2, List<ParameterDeclaration> p3, List<Access> p4, Opt<Block> p5) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
    setChild(p4, 3);
    setChild(p5, 4);
  }
  /**
   * @declaredat ASTNode:24
   */
  public MethodDecl(Modifiers p0, Access p1, beaver.Symbol p2, List<ParameterDeclaration> p3, List<Access> p4, Opt<Block> p5) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
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
    throwsException_TypeDecl_reset();
    signature_reset();
    lessSpecificThan_MethodDecl_reset();
    overrideCandidate_MethodDecl_reset();
    overrides_MethodDecl_reset();
    hides_MethodDecl_reset();
    parameterDeclaration_String_reset();
    type_reset();
    attributes_reset();
    descName_reset();
    bytecodes_ConstantPool_reset();
    flags_reset();
    offsetBeforeParameters_reset();
    offsetAfterParameters_reset();
    resultOffset_reset();
    usesTypeVariable_reset();
    sourceMethodDecl_reset();
    subsignatureTo_MethodDecl_reset();
    returnTypeSubstitutableFor_MethodDecl_reset();
    isDefault_reset();
    handlesException_TypeDecl_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:75
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:81
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:87
   */
  public MethodDecl clone() throws CloneNotSupportedException {
    MethodDecl node = (MethodDecl) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:94
   */
  public MethodDecl copy() {
    try {
      MethodDecl node = (MethodDecl) clone();
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
   * @declaredat ASTNode:113
   */
  public MethodDecl fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:122
   */
  public MethodDecl treeCopyNoTransform() {
    MethodDecl tree = (MethodDecl) copy();
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
   * @declaredat ASTNode:142
   */
  public MethodDecl treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:149
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((MethodDecl)node).tokenString_ID);    
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
   * Replaces the Parameter list.
   * @param list The new list node to be used as the Parameter list.
   * @apilevel high-level
   */
  public void setParameterList(List<ParameterDeclaration> list) {
    setChild(list, 2);
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
    List<ParameterDeclaration> list = (List<ParameterDeclaration>) getChild(2);
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
    return (List<ParameterDeclaration>) getChildNoTransform(2);
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
    setChild(list, 3);
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
    List<Access> list = (List<Access>) getChild(3);
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
    return (List<Access>) getChildNoTransform(3);
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
   * Replaces the optional node for the Block child. This is the <code>Opt</code>
   * node containing the child Block, not the actual child!
   * @param opt The new node to be used as the optional node for the Block child.
   * @apilevel low-level
   */
  public void setBlockOpt(Opt<Block> opt) {
    setChild(opt, 4);
  }
  /**
   * Replaces the (optional) Block child.
   * @param node The new node to be used as the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    getBlockOpt().setChild(node, 0);
  }
  /**
   * Check whether the optional Block child exists.
   * @return {@code true} if the optional Block child exists, {@code false} if it does not.
   * @apilevel high-level
   */
  public boolean hasBlock() {
    return getBlockOpt().getNumChild() != 0;
  }
  /**
   * Retrieves the (optional) Block child.
   * @return The Block child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   */
  public Block getBlock() {
    return (Block) getBlockOpt().getChild(0);
  }
  /**
   * Retrieves the optional node for the Block child. This is the <code>Opt</code> node containing the child Block, not the actual child!
   * @return The optional node for child the Block child.
   * @apilevel low-level
   */
  @ASTNodeAnnotation.OptChild(name="Block")
  public Opt<Block> getBlockOpt() {
    return (Opt<Block>) getChild(4);
  }
  /**
   * Retrieves the optional node for child Block. This is the <code>Opt</code> node containing the child Block, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Block.
   * @apilevel low-level
   */
  public Opt<Block> getBlockOptNoTransform() {
    return (Opt<Block>) getChildNoTransform(4);
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Enums.jrag:768
   */
   
  public void refined_Enums_MethodDecl_checkModifiers() {
    super.checkModifiers();
    if (hostType().isClassDecl()) {
      // 8.4.3.1
      if (!hostType().isEnumDecl() && isAbstract() && !hostType().isAbstract()) {
        error("class must be abstract to include abstract methods");
      }
      // 8.4.3.1
      if (isAbstract() && isPrivate()) {
        error("method may not be abstract and private");
      }
      // 8.4.3.1
      // 8.4.3.2
      if (isAbstract() && isStatic()) {
        error("method may not be abstract and static");
      }
      if (isAbstract() && isSynchronized()) {
        error("method may not be abstract and synchronized");
      }
      // 8.4.3.4
      if (isAbstract() && isNative()) {
        error("method may not be abstract and native");
      }
      if (isAbstract() && isStrictfp()) {
        error("method may not be abstract and strictfp");
      }
      if (isNative() && isStrictfp()) {
        error("method may not be native and strictfp");
      }
    }
    if (hostType().isInterfaceDecl()) {
      // 9.4
      if (isStatic()) {
        errorf("interface method %s in %s may not be static", signature(), hostType().typeName());
      }
      if (isStrictfp()) {
        errorf("interface method %s in %s may not be strictfp", signature(), hostType().typeName());
      }
      if (isNative()) {
        errorf("interface method %s in %s may not be native", signature(), hostType().typeName());
      }
      if (isSynchronized()) {
        errorf("interface method %s in %s may not be synchronized", signature(),
            hostType().typeName());
      }
      if (isProtected()) {
        errorf("interface method %s in %s may not be protected", signature(),
            hostType().typeName());
      }
      if (isPrivate()) {
        errorf("interface method %s in %s may not be private", signature(), hostType().typeName());
      } else if (isFinal()) {
        errorf("interface method %s in %s may not be final", signature(), hostType().typeName());
      }
    }
  }
  /**
   * @aspect Modifiers
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\Modifiers.jrag:40
   */
   
  public void checkModifiers() {
    super.checkModifiers();
    if (hostType().isClassDecl()) {
      // 8.4.3.1
      if (!hostType().isEnumDecl() && isAbstract() && !hostType().isAbstract()) {
        error("class must be abstract to include abstract methods");
      }
      // 8.4.3.1
      if (isAbstract() && isPrivate()) {
        error("method may not be abstract and private");
      }
      // 8.4.3.1
      // 8.4.3.2
      if (isAbstract() && isStatic()) {
        error("method may not be abstract and static");
      }
      if (isAbstract() && isSynchronized()) {
        error("method may not be abstract and synchronized");
      }
      // 8.4.3.4
      if (isAbstract() && isNative()) {
        error("method may not be abstract and native");
      }
      if (isAbstract() && isStrictfp()) {
        error("method may not be abstract and strictfp");
      }
      if (isNative() && isStrictfp()) {
        error("method may not be native and strictfp");
      }
      if (isDefault()) {
        error("non-interface methods may not use the default modifier");
      }
    }
    if (hostType().isInterfaceDecl()) {
      // 9.4
      if (isAbstract()) {
        if (isStatic()) {
          errorf("interface method %s in %s can not be both abstract and static",
              signature(), hostType().typeName());
        }
        if (isDefault()) {
          errorf("interface method %s in %s can not be both abstract and default",
              signature(), hostType().typeName());
        }
        if (isStrictfp()) {
          errorf("interface method %s in %s can not be both abstract and strictfp",
              signature(), hostType().typeName());
        }
      }
      if (isStatic() && isDefault()) {
        errorf("interface method %s in %s can not be both static and default",
            signature(), hostType().typeName());
      }
      if (isNative()) {
        errorf("interface method %s in %s may not be native", signature(), hostType().typeName());
      }
      if (isSynchronized()) {
        errorf("interface method %s in %s may not be synchronized",
            signature(), hostType().typeName());
      }
      if (isProtected()) {
        errorf("interface method %s in %s may not be protected",
            signature(), hostType().typeName());
      }
      if (isPrivate()) {
        errorf("interface method %s in %s may not be private", signature(), hostType().typeName());
      } else if (isFinal()) {
        errorf("interface method %s in %s may not be final", signature(), hostType().typeName());
      }
    }
  }
  /**
   * @aspect MethodDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:217
   */
  private boolean refined_MethodDecl_MethodDecl_sameSignature_MethodDecl(MethodDecl other)
{ return signature().equals(other.signature()); }
  /**
   * @aspect Attributes
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:221
   */
  private Collection refined_Attributes_MethodDecl_attributes()
{
    ArrayList l = new ArrayList();
    CodeGeneration bytecodes = bytecodes(hostType().constantPool());
    l.add(new ExceptionsAttribute(bytecodes, this));
    if (isAbstract() || isNative()) {
      return l;
    } else {
      l.add(new CodeAttribute(bytecodes, this));
      if (getModifiers().isSynthetic()) {
        l.add(new SyntheticAttribute(hostType().constantPool()));
      }
      return l;
    }
  }
  /**
   * @aspect Flags
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Flags.jrag:62
   */
  private int refined_Flags_MethodDecl_flags()
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
    if (isStatic()) {
      res |= Modifiers.ACC_STATIC;
    }
    if (isFinal()) {
      res |= Modifiers.ACC_FINAL;
    }
    if (isSynchronized()) {
      res |= Modifiers.ACC_SYNCHRONIZED;
    }
    if (isNative()) {
      res |= Modifiers.ACC_NATIVE;
    }
    if (isAbstract()) {
      res |= Modifiers.ACC_ABSTRACT;
    }
    if (isStrictfp() || (hostType().isStrictfp() && !hostType().isInterfaceDecl())) {
      res |= Modifiers.ACC_STRICT;
    }
    return res;
  }
  /**
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:44
   */
  private Collection refined_AnnotationsCodegen_MethodDecl_attributes()
{
    Collection c = refined_Attributes_MethodDecl_attributes();
    getModifiers().addRuntimeVisibleAnnotationsAttribute(c);
    getModifiers().addRuntimeInvisibleAnnotationsAttribute(c);
    addRuntimeVisibleParameterAnnotationsAttribute(c);
    addRuntimeInvisibleParameterAnnotationsAttribute(c);
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
  @ASTNodeAnnotation.Attribute
  public int lineNumber() {
    ASTNode$State state = state();
    int lineNumber_value = getLine(IDstart);

    return lineNumber_value;
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
  @ASTNodeAnnotation.Attribute
  public String name() {
    ASTNode$State state = state();
    String name_value = getID();

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
      StringBuilder sb = new StringBuilder();
      sb.append(name() + "(");
      for (int i = 0; i < getNumParameter(); i++) {
        if (i != 0) {
          sb.append(", ");
        }
        sb.append(getParameter(i).type().erasure().typeName());
      }
      sb.append(")");
      return sb.toString();
    }
  /**
   * @return Method signature, including generic parameters.
   * @attribute syn
   * @aspect MethodDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:197
   */
  @ASTNodeAnnotation.Attribute
  public String fullSignature() {
    ASTNode$State state = state();
    try {
        StringBuilder sb = new StringBuilder();
        sb.append(name() + "(");
        for (int i = 0; i < getNumParameter(); i++) {
          if (i != 0) {
            sb.append(", ");
          }
          if (getParameter(i).type() instanceof PrimitiveType) {
            sb.append(getParameter(i).type().typeName());
          } else {
            sb.append(getParameter(i).type().fullName());
          }
        }
        sb.append(")");
        return sb.toString();
      }
    finally {
    }
  }
  /**
   * 8.4.2 Method Signature
   * @param other
   * @return {@code true} if the signature of this method is same as the
   * the signature of the argument method
   * @attribute syn
   * @aspect MethodDecl
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:217
   */
  @ASTNodeAnnotation.Attribute
  public boolean sameSignature(MethodDecl other) {
    ASTNode$State state = state();
    try {
        if (!refined_MethodDecl_MethodDecl_sameSignature_MethodDecl(other)) {
          return false;
        }
        for (int i = 0; i < getNumParameter(); ++i) {
          TypeDecl p1 = getParameter(i).type();
          TypeDecl p2 = other.getParameter(i).type();
          // JLSv7 $8.4.8.1 exception: if one parameter type is raw, then don't check type bounds
          if (p1 != p2 && !p1.isRawType() && !p2.isRawType()) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean moreSpecificThan(MethodDecl m) {
    ASTNode$State state = state();
    boolean moreSpecificThan_MethodDecl_value = m.lessSpecificThan(this) && !this.lessSpecificThan(m);

    return moreSpecificThan_MethodDecl_value;
  }
  protected java.util.Map lessSpecificThan_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void lessSpecificThan_MethodDecl_reset() {
    lessSpecificThan_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean lessSpecificThan(MethodDecl m) {
    Object _parameters = m;
    if (lessSpecificThan_MethodDecl_values == null) lessSpecificThan_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(lessSpecificThan_MethodDecl_values.containsKey(_parameters)) {
      return ((Boolean)lessSpecificThan_MethodDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean lessSpecificThan_MethodDecl_value = lessSpecificThan_compute(m);
    if (isFinal && num == state().boundariesCrossed) {
      lessSpecificThan_MethodDecl_values.put(_parameters, Boolean.valueOf(lessSpecificThan_MethodDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return lessSpecificThan_MethodDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean lessSpecificThan_compute(MethodDecl m) {
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
  protected java.util.Map overrideCandidate_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void overrideCandidate_MethodDecl_reset() {
    overrideCandidate_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean overrideCandidate(MethodDecl m) {
    Object _parameters = m;
    if (overrideCandidate_MethodDecl_values == null) overrideCandidate_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(overrideCandidate_MethodDecl_values.containsKey(_parameters)) {
      return ((Boolean)overrideCandidate_MethodDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean overrideCandidate_MethodDecl_value = !isStatic() && !m.isPrivate() && m.accessibleFrom(hostType());
    if (isFinal && num == state().boundariesCrossed) {
      overrideCandidate_MethodDecl_values.put(_parameters, Boolean.valueOf(overrideCandidate_MethodDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return overrideCandidate_MethodDecl_value;
  }
  protected java.util.Map overrides_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void overrides_MethodDecl_reset() {
    overrides_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean overrides(MethodDecl m) {
    Object _parameters = m;
    if (overrides_MethodDecl_values == null) overrides_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(overrides_MethodDecl_values.containsKey(_parameters)) {
      return ((Boolean)overrides_MethodDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean overrides_MethodDecl_value = !isStatic() && !m.isPrivate() && m.accessibleFrom(hostType())
          && hostType().instanceOf(m.hostType()) && m.signature().equals(signature());
    if (isFinal && num == state().boundariesCrossed) {
      overrides_MethodDecl_values.put(_parameters, Boolean.valueOf(overrides_MethodDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return overrides_MethodDecl_value;
  }
  protected java.util.Map hides_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void hides_MethodDecl_reset() {
    hides_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean hides(MethodDecl m) {
    Object _parameters = m;
    if (hides_MethodDecl_values == null) hides_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(hides_MethodDecl_values.containsKey(_parameters)) {
      return ((Boolean)hides_MethodDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean hides_MethodDecl_value = isStatic() && !m.isPrivate() && m.accessibleFrom(hostType())
          && hostType().instanceOf(m.hostType()) && m.signature().equals(signature());
    if (isFinal && num == state().boundariesCrossed) {
      hides_MethodDecl_values.put(_parameters, Boolean.valueOf(hides_MethodDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return hides_MethodDecl_value;
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
  /**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:268
   */
  @ASTNodeAnnotation.Attribute
  public boolean isAbstract() {
    ASTNode$State state = state();
    try {
        return getModifiers().isAbstract() || (hostType().isInterfaceDecl() && !isStatic() && !isDefault());
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStatic() {
    ASTNode$State state = state();
    boolean isStatic_value = getModifiers().isStatic();

    return isStatic_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isFinal() {
    ASTNode$State state = state();
    boolean isFinal_value = getModifiers().isFinal() || hostType().isFinal() || isPrivate();

    return isFinal_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSynchronized() {
    ASTNode$State state = state();
    boolean isSynchronized_value = getModifiers().isSynchronized();

    return isSynchronized_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isNative() {
    ASTNode$State state = state();
    boolean isNative_value = getModifiers().isNative();

    return isNative_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isStrictfp() {
    ASTNode$State state = state();
    boolean isStrictfp_value = getModifiers().isStrictfp();

    return isStrictfp_value;
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
    type_value = getTypeAccess().type();
    if (isFinal && num == state().boundariesCrossed) {
      type_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isVoid() {
    ASTNode$State state = state();
    boolean isVoid_value = type().isVoid();

    return isVoid_value;
  }
  /**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:360
   */
  @ASTNodeAnnotation.Attribute
  public boolean mayOverride(MethodDecl m) {
    ASTNode$State state = state();
    try {
        // 9.4.3
        if (isDefault() && m.hostType() == type().typeObject() && !m.isPrivate()) {
          return false;
        } else {
          return returnTypeSubstitutableFor(m);
        }
      }
    finally {
    }
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
      Collection c = refined_AnnotationsCodegen_MethodDecl_attributes();
      if (needsSignatureAttribute()) {
        c.add(new SignatureAttribute(hostType().constantPool(), methodTypeSignature()));
      }
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
      for (int i=0; i<getNumParameter(); i++) {
        b.append(getParameter(i).type().typeDescriptor());
      }
      b.append(")");
      if (type().elementType().isUnknown()) {
        System.err.println(getTypeAccess().dumpTree());
        throw new Error("Error generating descName for " + signature() + ", did not expect unknown return type");
      }
      b.append(type().typeDescriptor());
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
      int res = refined_Flags_MethodDecl_flags();
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
    offsetBeforeParameters_value = isStatic() ? 0 : 1;
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
  /**
   * @apilevel internal
   */
  protected boolean resultOffset_computed = false;
  /**
   * @apilevel internal
   */
  protected int resultOffset_value;
  /**
   * @apilevel internal
   */
  private void resultOffset_reset() {
    resultOffset_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int resultOffset() {
    if(resultOffset_computed) {
      return resultOffset_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    resultOffset_value = type().isVoid() ? 0 : type().variableSize();
    if (isFinal && num == state().boundariesCrossed) {
      resultOffset_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return resultOffset_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean annotationMethodOverride() {
    ASTNode$State state = state();
    boolean annotationMethodOverride_value = !hostType().ancestorMethods(signature()).isEmpty();

    return annotationMethodOverride_value;
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
    usesTypeVariable_value = getModifiers().usesTypeVariable() || getTypeAccess().usesTypeVariable()
          || getParameterList().usesTypeVariable() || getExceptionList().usesTypeVariable();
    if (isFinal && num == state().boundariesCrossed) {
      usesTypeVariable_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return usesTypeVariable_value;
  }
  @ASTNodeAnnotation.Attribute
  public MethodDecl erasedMethod() {
    ASTNode$State state = state();
    MethodDecl erasedMethod_value = this;

    return erasedMethod_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean sourceMethodDecl_computed = false;
  /**
   * @apilevel internal
   */
  protected MethodDecl sourceMethodDecl_value;
  /**
   * @apilevel internal
   */
  private void sourceMethodDecl_reset() {
    sourceMethodDecl_computed = false;
    sourceMethodDecl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public MethodDecl sourceMethodDecl() {
    if(sourceMethodDecl_computed) {
      return sourceMethodDecl_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    sourceMethodDecl_value = this;
    if (isFinal && num == state().boundariesCrossed) {
      sourceMethodDecl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return sourceMethodDecl_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean visibleTypeParameters() {
    ASTNode$State state = state();
    boolean visibleTypeParameters_value = !isStatic();

    return visibleTypeParameters_value;
  }
  /**
   * Note: isGeneric must be called first to check if this declaration is generic.
   * Otherwise this attribute will throw an error!
   * @return the original generic declaration of this method.
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\MethodSignature.jrag:386
   */
  @ASTNodeAnnotation.Attribute
  public GenericMethodDecl genericDecl() {
    ASTNode$State state = state();
    try {
        throw new Error("can not evaulate generic declaration of non-generic method");
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
  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\GenericsCodegen.jrag:375
   */
  @ASTNodeAnnotation.Attribute
  public boolean needsSignatureAttribute() {
    ASTNode$State state = state();
    try {
        if (type().needsSignatureAttribute()) {
          return true;
        }
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
  /**
   * @attribute syn
   * @aspect GenericsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\GenericsCodegen.jrag:508
   */
  @ASTNodeAnnotation.Attribute
  public String methodTypeSignature() {
    ASTNode$State state = state();
    try {
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        for (int i = 0; i < getNumParameter(); i++) {
          buf.append(getParameter(i).type().classTypeSignature());
        }
        buf.append(")");
        buf.append(type().classTypeSignature());
        for (int i = 0; i < getNumException(); i++) {
          buf.append("^" + getException(i).type().classTypeSignature());
        }
        return buf.toString();
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
    boolean hasIllegalAnnotationSafeVarargs_value = hasAnnotationSafeVarargs() && (!isVariableArity() || (!isFinal() && !isStatic()));

    return hasIllegalAnnotationSafeVarargs_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean suppressWarnings(String type) {
    ASTNode$State state = state();
    boolean suppressWarnings_String_value = hasAnnotationSuppressWarnings(type) || withinSuppressWarnings(type);

    return suppressWarnings_String_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    boolean modifiedInScope_Variable_value = hasBlock() && getBlock().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  protected java.util.Map subsignatureTo_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void subsignatureTo_MethodDecl_reset() {
    subsignatureTo_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean subsignatureTo(MethodDecl m) {
    Object _parameters = m;
    if (subsignatureTo_MethodDecl_values == null) subsignatureTo_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(subsignatureTo_MethodDecl_values.containsKey(_parameters)) {
      return ((Boolean)subsignatureTo_MethodDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean subsignatureTo_MethodDecl_value = subsignatureTo_compute(m);
    if (isFinal && num == state().boundariesCrossed) {
      subsignatureTo_MethodDecl_values.put(_parameters, Boolean.valueOf(subsignatureTo_MethodDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return subsignatureTo_MethodDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean subsignatureTo_compute(MethodDecl m) {
      if (fullSignature().equals(m.fullSignature())) {
        return true;
      } else if (fullSignature().equals(m.signature())) {
        return true;
      } else {
        return false;
      }
    }
  protected java.util.Map returnTypeSubstitutableFor_MethodDecl_values;
  /**
   * @apilevel internal
   */
  private void returnTypeSubstitutableFor_MethodDecl_reset() {
    returnTypeSubstitutableFor_MethodDecl_values = null;
  }
  @ASTNodeAnnotation.Attribute
  public boolean returnTypeSubstitutableFor(MethodDecl m) {
    Object _parameters = m;
    if (returnTypeSubstitutableFor_MethodDecl_values == null) returnTypeSubstitutableFor_MethodDecl_values = new org.jastadd.util.RobustMap(new java.util.HashMap());
    if(returnTypeSubstitutableFor_MethodDecl_values.containsKey(_parameters)) {
      return ((Boolean)returnTypeSubstitutableFor_MethodDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    boolean returnTypeSubstitutableFor_MethodDecl_value = returnTypeSubstitutableFor_compute(m);
    if (isFinal && num == state().boundariesCrossed) {
      returnTypeSubstitutableFor_MethodDecl_values.put(_parameters, Boolean.valueOf(returnTypeSubstitutableFor_MethodDecl_value));
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return returnTypeSubstitutableFor_MethodDecl_value;
  }
  /**
   * @apilevel internal
   */
  private boolean returnTypeSubstitutableFor_compute(MethodDecl m) {
      TypeDecl t1 = type();
      TypeDecl t2 = m.type();
      if (t1 instanceof VoidType && t2 instanceof VoidType) {
        return true;
      } else if (t1 instanceof PrimitiveType && t2 instanceof PrimitiveType) {
        PrimitiveType p1 = (PrimitiveType)t1;
        PrimitiveType p2 = (PrimitiveType)t2;
        return p1 == p2;
      } else if (t1.isReferenceType() && t2.isReferenceType()) {
        if (t1.strictSubtype(t2)) {
          return true;
        } else {
          return t1 == t2.erasure();
        }
      } else {
        return false;
      }
    }
  /**
   * @apilevel internal
   */
  protected boolean isDefault_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean isDefault_value;
  /**
   * @apilevel internal
   */
  private void isDefault_reset() {
    isDefault_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isDefault() {
    if(isDefault_computed) {
      return isDefault_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    isDefault_value = getModifiers().isDefault();
    if (isFinal && num == state().boundariesCrossed) {
      isDefault_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return isDefault_value;
  }
  /**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:84
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
   * @aspect LookupMethod
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupMethod.jrag:36
   */
  @ASTNodeAnnotation.Attribute
  public MethodDecl unknownMethod() {
    ASTNode$State state = state();
    MethodDecl unknownMethod_value = getParent().Define_MethodDecl_unknownMethod(this, null);

    return unknownMethod_value;
  }
  /**
   * @attribute inh
   * @aspect SuppressWarnings
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\SuppressWarnings.jrag:39
   */
  @ASTNodeAnnotation.Attribute
  public boolean withinSuppressWarnings(String annot) {
    ASTNode$State state = state();
    boolean withinSuppressWarnings_String_value = getParent().Define_boolean_withinSuppressWarnings(this, null, annot);

    return withinSuppressWarnings_String_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:479
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockOptNoTransform()) {
      return v.isFinal() && (v.isClassVariable() || v.isInstanceVariable()) ? true : isDAbefore(v);
    }
    else {
      return super.Define_boolean_isDAbefore(caller, child, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:983
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockOptNoTransform()) {
      return v.isFinal() && (v.isClassVariable() || v.isInstanceVariable()) ? false : true;
    }
    else {
      return super.Define_boolean_isDUbefore(caller, child, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\ExceptionHandling.jrag:179
   * @apilevel internal
   */
  public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
    if (caller == getBlockOptNoTransform()) {
      return throwsException(exceptionType);
    }
    else {
      return getParent().Define_boolean_handlesException(this, caller, exceptionType);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupVariable.jrag:72
   * @apilevel internal
   */
  public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return parameterDeclaration(name);
    }
    else if (caller == getBlockOptNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    // A declaration of a method parameter name shadows any other variable declarations
    if (!set.isEmpty()) {
      return set;
    }
    // Delegate to other declarations in scope
    return lookupVariable(name);
  }
    else {
      return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:312
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:313
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:314
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:315
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeAbstract(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeAbstract(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:316
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:317
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:318
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeSynchronized(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeSynchronized(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:319
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeNative(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeNative(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\Modifiers.jrag:320
   * @apilevel internal
   */
  public boolean Define_boolean_mayBeStrictfp(ASTNode caller, ASTNode child) {
    if (caller == getModifiersNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_mayBeStrictfp(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:311
   * @apilevel internal
   */
  public ASTNode Define_ASTNode_enclosingBlock(ASTNode caller, ASTNode child) {
    if (caller == getBlockOptNoTransform()) {
      return this;
    }
    else {
      return getParent().Define_ASTNode_enclosingBlock(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\SyntacticClassification.jrag:108
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getExceptionListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return NameType.TYPE_NAME;
    }
    else if (caller == getTypeAccessNoTransform()) {
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_NameType_nameType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:474
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_returnType(ASTNode caller, ASTNode child) {
    if (caller == getBlockOptNoTransform()) {
      return type();
    }
    else {
      return getParent().Define_TypeDecl_returnType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeHierarchyCheck.jrag:190
   * @apilevel internal
   */
  public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
    if (caller == getBlockOptNoTransform()) {
      return isStatic();
    }
    else {
      return getParent().Define_boolean_inStaticContext(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:64
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if (caller == getBlockOptNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:82
   * @apilevel internal
   */
  public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return true;
    }
    else {
      return getParent().Define_boolean_isMethodParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:83
   * @apilevel internal
   */
  public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return false;
    }
    else {
      return getParent().Define_boolean_isConstructorParameter(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\VariableDeclaration.jrag:84
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\LocalNum.jrag:109
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
    if (caller == getBlockOptNoTransform()) {
      return offsetAfterParameters() + resultOffset();
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\LocalNum.jrag:102
   * @apilevel internal
   */
  public int Define_int_resultSaveLocalNum(ASTNode caller, ASTNode child) {
    if (caller == getBlockOptNoTransform()) {
      return offsetAfterParameters();
    }
    else {
      return getParent().Define_int_resultSaveLocalNum(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:113
   * @apilevel internal
   */
  public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if (caller == getModifiersNoTransform()) {
      return name.equals("METHOD");
    }
    else {
      return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\VariableArityParameters.jrag:44
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
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PreciseRethrow.jrag:79
   * @apilevel internal
   */
  public boolean Define_boolean_inhModifiedInScope(ASTNode caller, ASTNode child, Variable var) {
    if (caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return hasBlock() && getBlock().modifiedInScope(var);
    }
    else {
      return getParent().Define_boolean_inhModifiedInScope(this, caller, var);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\PreciseRethrow.jrag:209
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
