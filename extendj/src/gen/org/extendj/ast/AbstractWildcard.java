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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\grammar\\Generics.ast:20
 * @production AbstractWildcard : {@link Access};

 */
public abstract class AbstractWildcard extends Access implements Cloneable {
  /**
   * @aspect FunctionalInterface
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java8\\frontend\\FunctionalInterface.jrag:245
   */
  public boolean sameType(AbstractWildcard w) {
    if (this instanceof Wildcard && w instanceof Wildcard) {
      return true;
    } else if (this instanceof WildcardExtends && w instanceof WildcardExtends) {
      Access a1 = ((WildcardExtends) this).getAccess();
      Access a2 = ((WildcardExtends) w).getAccess();
      return a1.sameType(a2);
    } else if (this instanceof WildcardSuper && w instanceof WildcardSuper) {
      Access a1 = ((WildcardSuper) this).getAccess();
      Access a2 = ((WildcardSuper) w).getAccess();
      return a1.sameType(a2);
    } else {
      return false;
    }
  }
  /**
   * @declaredat ASTNode:1
   */
  public AbstractWildcard() {
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
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:33
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:39
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:45
   */
  public AbstractWildcard clone() throws CloneNotSupportedException {
    AbstractWildcard node = (AbstractWildcard) super.clone();
    return node;
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @deprecated Please use treeCopy or treeCopyNoTransform instead
   * @declaredat ASTNode:56
   */
  public abstract AbstractWildcard fullCopy();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:63
   */
  public abstract AbstractWildcard treeCopyNoTransform();
  /**
   * Create a deep copy of the AST subtree at this node.
   * The subtree of this node is traversed to trigger rewrites before copy.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:71
   */
  public abstract AbstractWildcard treeCopy();
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
