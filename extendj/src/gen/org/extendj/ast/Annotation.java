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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\grammar\\Annotations.ast:6
 * @production Annotation : {@link Modifier} ::= <span class="component">&lt;ID:String&gt;</span> <span class="component">{@link Access}</span> <span class="component">{@link ElementValuePair}*</span>;

 */
public class Annotation extends Modifier implements Cloneable {
  /**
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:63
   */
  public void checkModifiers() {
    super.checkModifiers();
    if (decl() instanceof AnnotationDecl) {
      AnnotationDecl T = (AnnotationDecl) decl();
      Annotation m = T.annotation(lookupType("java.lang.annotation", "Target"));
      if (m != null && m.getNumElementValuePair() == 1 && m.getElementValuePair(0).getName().equals("value")) {
        ElementValue v = m.getElementValuePair(0).getElementValue();
        //System.out.println("ElementValue: \n" + v.dumpTree());
        //System.out.println("Annotation: \n" + dumpTree());
        if (!v.validTarget(this)) {
          errorf("annotation type %s is not applicable to this kind of declaration", T.typeName());
        }
      }
    }
  }
  /**
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:460
   */
  public void typeCheck() {
    if (!decl().isAnnotationDecl()) {
      /* TypeName names the annotation type corresponding to the annotation. It is a
      compile-time error if TypeName does not name an annotation type.*/
      if (!decl().isUnknown()) {
        errorf("%s is not an annotation type", decl().typeName());
      }
    } else {
      TypeDecl typeDecl = decl();
      /* It is a compile-time error if a declaration is annotated with more than one
      annotation for a given annotation type.*/
      if (lookupAnnotation(typeDecl) != this) {
        errorf("duplicate annotation %s", typeDecl.typeName());
      }
      /* Annotations must contain an element-value pair for every element of the
      corresponding annotation type, except for those elements with default
      values, or a compile-time error occurs. Annotations may, but are not
      required to, contain element-value pairs for elements with default values.*/
      for (int i = 0; i < typeDecl.getNumBodyDecl(); i++) {
        if (typeDecl.getBodyDecl(i) instanceof MethodDecl) {
          MethodDecl decl = (MethodDecl) typeDecl.getBodyDecl(i);
          if (elementValueFor(decl.name()) == null && (!(decl instanceof AnnotationMethodDecl) || !((AnnotationMethodDecl) decl).hasDefaultValue())) {
            errorf("missing value for %s", decl.name());
          }
        }
      }
      /* The Identifier in an ElementValuePair must be the simple name of one of the
      elements of the annotation type identified by TypeName in the containing
      annotation. Otherwise, a compile-time error occurs. (In other words, the
      identifier in an element-value pair must also be a method name in the interface
      identified by TypeName.) */
      for (int i = 0; i < getNumElementValuePair(); i++) {
        ElementValuePair pair = getElementValuePair(i);
        if (typeDecl.memberMethods(pair.getName()).isEmpty()) {
          errorf("can not find element named %s in %s", pair.getName(), typeDecl.typeName());
        }
      }
    }
    checkOverride();
  }
  /**
   * @aspect Java5PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\PrettyPrint.jadd:311
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("@");
    out.print(getAccess());
    if (hasElementValuePair()) {
      out.print("(");
      out.join(getElementValuePairList(), new PrettyPrinter.Joiner() {
        @Override
        public void printSeparator(PrettyPrinter out) {
          out.print(", ");
        }
      });
      out.print(")");
    }
  }
  /**
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:191
   */
  public void appendAsAttributeTo(Attribute buf) {
    ConstantPool cp = hostType().constantPool();
    String typeDescriptor = decl().typeDescriptor();
    int type_index = cp.addUtf8(typeDescriptor);
    buf.u2(type_index);
    buf.u2(getNumElementValuePair());
    for (int i = 0; i < getNumElementValuePair(); i++) {
      int name_index = cp.addUtf8(getElementValuePair(i).getName());
      buf.u2(name_index);
      getElementValuePair(i).getElementValue().appendAsAttributeTo(buf);
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public Annotation() {
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
  public Annotation(String p0, Access p1, List<ElementValuePair> p2) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
  /**
   * @declaredat ASTNode:19
   */
  public Annotation(beaver.Symbol p0, Access p1, List<ElementValuePair> p2) {
    setID(p0);
    setChild(p1, 0);
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
    decl_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:46
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:52
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:58
   */
  public Annotation clone() throws CloneNotSupportedException {
    Annotation node = (Annotation) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:65
   */
  public Annotation copy() {
    try {
      Annotation node = (Annotation) clone();
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
   * @declaredat ASTNode:84
   */
  public Annotation fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:93
   */
  public Annotation treeCopyNoTransform() {
    Annotation tree = (Annotation) copy();
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
   * @declaredat ASTNode:113
   */
  public Annotation treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:120
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node) && (tokenString_ID == ((Annotation)node).tokenString_ID);    
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
   * Replaces the Access child.
   * @param node The new node to replace the Access child.
   * @apilevel high-level
   */
  public void setAccess(Access node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Access child.
   * @return The current node used as the Access child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Access")
  public Access getAccess() {
    return (Access) getChild(0);
  }
  /**
   * Retrieves the Access child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Access child.
   * @apilevel low-level
   */
  public Access getAccessNoTransform() {
    return (Access) getChildNoTransform(0);
  }
  /**
   * Replaces the ElementValuePair list.
   * @param list The new list node to be used as the ElementValuePair list.
   * @apilevel high-level
   */
  public void setElementValuePairList(List<ElementValuePair> list) {
    setChild(list, 1);
  }
  /**
   * Retrieves the number of children in the ElementValuePair list.
   * @return Number of children in the ElementValuePair list.
   * @apilevel high-level
   */
  public int getNumElementValuePair() {
    return getElementValuePairList().getNumChild();
  }
  /**
   * Retrieves the number of children in the ElementValuePair list.
   * Calling this method will not trigger rewrites.
   * @return Number of children in the ElementValuePair list.
   * @apilevel low-level
   */
  public int getNumElementValuePairNoTransform() {
    return getElementValuePairListNoTransform().getNumChildNoTransform();
  }
  /**
   * Retrieves the element at index {@code i} in the ElementValuePair list.
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the ElementValuePair list.
   * @apilevel high-level
   */
  public ElementValuePair getElementValuePair(int i) {
    return (ElementValuePair) getElementValuePairList().getChild(i);
  }
  /**
   * Check whether the ElementValuePair list has any children.
   * @return {@code true} if it has at least one child, {@code false} otherwise.
   * @apilevel high-level
   */
  public boolean hasElementValuePair() {
    return getElementValuePairList().getNumChild() != 0;
  }
  /**
   * Append an element to the ElementValuePair list.
   * @param node The element to append to the ElementValuePair list.
   * @apilevel high-level
   */
  public void addElementValuePair(ElementValuePair node) {
    List<ElementValuePair> list = (parent == null || state == null) ? getElementValuePairListNoTransform() : getElementValuePairList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   */
  public void addElementValuePairNoTransform(ElementValuePair node) {
    List<ElementValuePair> list = getElementValuePairListNoTransform();
    list.addChild(node);
  }
  /**
   * Replaces the ElementValuePair list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   */
  public void setElementValuePair(ElementValuePair node, int i) {
    List<ElementValuePair> list = getElementValuePairList();
    list.setChild(node, i);
  }
  /**
   * Retrieves the ElementValuePair list.
   * @return The node representing the ElementValuePair list.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.ListChild(name="ElementValuePair")
  public List<ElementValuePair> getElementValuePairList() {
    List<ElementValuePair> list = (List<ElementValuePair>) getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * Retrieves the ElementValuePair list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ElementValuePair list.
   * @apilevel low-level
   */
  public List<ElementValuePair> getElementValuePairListNoTransform() {
    return (List<ElementValuePair>) getChildNoTransform(1);
  }
  /**
   * Retrieves the ElementValuePair list.
   * @return The node representing the ElementValuePair list.
   * @apilevel high-level
   */
  public List<ElementValuePair> getElementValuePairs() {
    return getElementValuePairList();
  }
  /**
   * Retrieves the ElementValuePair list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ElementValuePair list.
   * @apilevel low-level
   */
  public List<ElementValuePair> getElementValuePairsNoTransform() {
    return getElementValuePairListNoTransform();
  }
  /**
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java6\\frontend\\Override.jrag:45
   */
   
  public void checkOverride() {
    if (decl().fullName().equals("java.lang.Override") &&
        enclosingBodyDecl() instanceof MethodDecl) {

      MethodDecl method = (MethodDecl)enclosingBodyDecl();
      TypeDecl host = method.hostType();
      SimpleSet ancestors = host.ancestorMethods(method.signature());
      boolean found = false;
      for (Iterator iter = ancestors.iterator(); iter.hasNext(); ) {
        MethodDecl decl = (MethodDecl)iter.next();
        if (method.overrides(decl)) {
          found = true;
          break;
        }
      }
      if (!found) {
        TypeDecl typeObject = lookupType("java.lang", "Object");
        SimpleSet overrides = typeObject.localMethodsSignature(method.signature());
        if (overrides.isEmpty() ||
            !((MethodDecl) overrides.iterator().next()).isPublic())
          error("method does not override a method from a supertype");
      }
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean isAnnotation(String packageName, String name) {
    ASTNode$State state = state();
    boolean isAnnotation_String_String_value = decl().isType(packageName, name);

    return isAnnotation_String_String_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean decl_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl decl_value;
  /**
   * @apilevel internal
   */
  private void decl_reset() {
    decl_computed = false;
    decl_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl decl() {
    if(decl_computed) {
      return decl_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    decl_value = getAccess().type();
    if (isFinal && num == state().boundariesCrossed) {
      decl_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return decl_value;
  }
  /**
   * @attribute syn
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:513
   */
  @ASTNodeAnnotation.Attribute
  public ElementValue elementValueFor(String name) {
    ASTNode$State state = state();
    try {
        for (int i = 0; i < getNumElementValuePair(); i++) {
          ElementValuePair pair = getElementValuePair(i);
          if (pair.getName().equals(name)) {
            return pair.getElementValue();
          }
        }
        return null;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public TypeDecl type() {
    ASTNode$State state = state();
    TypeDecl type_value = getAccess().type();

    return type_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isMetaAnnotation() {
    ASTNode$State state = state();
    boolean isMetaAnnotation_value = hostType().isAnnotationDecl();

    return isMetaAnnotation_value;
  }
  /**
   * @attribute syn
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:88
   */
  @ASTNodeAnnotation.Attribute
  public boolean isRuntimeVisible() {
    ASTNode$State state = state();
    try {
        Annotation a = decl().annotation(lookupType("java.lang.annotation", "Retention"));
        if (a == null) {
          return false;
        }
        ElementConstantValue value = (ElementConstantValue) a.getElementValuePair(0).getElementValue();
        Variable v = value.getExpr().varDecl();
        return v != null && v.name().equals("RUNTIME");
      }
    finally {
    }
  }
  /**
   * @attribute syn
   * @aspect AnnotationsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:100
   */
  @ASTNodeAnnotation.Attribute
  public boolean isRuntimeInvisible() {
    ASTNode$State state = state();
    try {
        Annotation a = decl().annotation(lookupType("java.lang.annotation", "Retention"));
        if (a == null) return true; // default bahavior if not annotated
        ElementConstantValue value = (ElementConstantValue) a.getElementValuePair(0).getElementValue();
        Variable v = value.getExpr().varDecl();
        return v != null &&  v.name().equals("CLASS");
      }
    finally {
    }
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:78
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl lookupType(String packageName, String typeName) {
    ASTNode$State state = state();
    TypeDecl lookupType_String_String_value = getParent().Define_TypeDecl_lookupType(this, null, packageName, typeName);

    return lookupType_String_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:96
   */
  @ASTNodeAnnotation.Attribute
  public boolean mayUseAnnotationTarget(String name) {
    ASTNode$State state = state();
    boolean mayUseAnnotationTarget_String_value = getParent().Define_boolean_mayUseAnnotationTarget(this, null, name);

    return mayUseAnnotationTarget_String_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:330
   */
  @ASTNodeAnnotation.Attribute
  public BodyDecl enclosingBodyDecl() {
    ASTNode$State state = state();
    BodyDecl enclosingBodyDecl_value = getParent().Define_BodyDecl_enclosingBodyDecl(this, null);

    return enclosingBodyDecl_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:503
   */
  @ASTNodeAnnotation.Attribute
  public Annotation lookupAnnotation(TypeDecl typeDecl) {
    ASTNode$State state = state();
    Annotation lookupAnnotation_TypeDecl_value = getParent().Define_Annotation_lookupAnnotation(this, null, typeDecl);

    return lookupAnnotation_TypeDecl_value;
  }
  /**
   * @attribute inh
   * @aspect Annotations
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:641
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl hostType() {
    ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);

    return hostType_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:545
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_enclosingAnnotationDecl(ASTNode caller, ASTNode child) {
    if (caller == getElementValuePairListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
      return decl();
    }
    else {
      return getParent().Define_TypeDecl_enclosingAnnotationDecl(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Annotations.jrag:646
   * @apilevel internal
   */
  public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if (caller == getAccessNoTransform()) {
      return NameType.TYPE_NAME;
    }
    else {
      return getParent().Define_NameType_nameType(this, caller);
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
