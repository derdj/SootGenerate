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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\grammar\\Java.ast:198
 * @production SwitchStmt : {@link BranchTargetStmt} ::= <span class="component">{@link Expr}</span> <span class="component">{@link Block}</span>;

 */
public class SwitchStmt extends BranchTargetStmt implements Cloneable {
  /**
   * @aspect Java4PrettyPrint
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PrettyPrint.jadd:238
   */
  public void prettyPrint(PrettyPrinter out) {
    out.print("switch (");
    out.print(getExpr());
    out.print(") ");
    out.print(getBlock());
  }
  /**
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1504
   */
  private int emitPad(CodeGeneration gen) {
    int pad = (4 - (gen.pos() % 4)) % 4;
    for (int i = 0; i < pad; i++) {
      gen.emit(Bytecode.NOP);
    }
    if (gen.pos() % 4 != 0) {
      throw new Error("Switch not at 4-byte boundary:" + gen.pos());
    }
    return pad;
  }
  /**
   * Calculate offset to the default label.
   * @return bytecode offset to default label (or zero if there is no
   * default label)
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1520
   */
  private int defaultOffset(CodeGeneration gen, int switch_label) {
    DefaultCase defaultCase = defaultCase();
    if (defaultCase != null) {
      int offset = gen.addressOf(defaultCase.label())
        - gen.addressOf(switch_label);
      return offset;
    }
    return 0;
  }
  /**
   * @aspect EnumsCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\EnumsCodegen.jrag:38
   */
  public void transformation() {
    if (getExpr().type().isEnumDecl()) {
      TypeDecl type = getExpr().type();
      hostType().createEnumArray(type);
      hostType().createEnumMethod(type);
      setExpr(
        hostType().createEnumMethod(type).createBoundAccess(new List()).qualifiesAccess(
        new ArrayAccess(
          ((Expr) getExpr().treeCopyNoTransform()).qualifiesAccess(new MethodAccess("ordinal", new List()))
        ))
      );
    }
    super.transformation();
  }
  /**
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:170
   */
  private void genFirstSwitch(
      CodeGeneration gen,
      TreeMap<Integer, CaseGroup> groups,
      int index_a) {
    int switch_label = gen.constantPool().newLabel();
    int end_label1 = gen.constantPool().newLabel();
    int index_b = localNumB();

    // Initialize switch variable for second switch
    IntegerLiteral.push(gen, 0);
    typeInt().emitStoreLocal(gen, index_a);

    // Store the value of the switch expr so that it is only evaluated once!
    getExpr().createBCode(gen);

    // Push the hash code for the switch instruction
    if (getExpr().isConstant()) {
      typeString().emitStoreLocal(gen, index_b);

      int hashCode = getExpr().constant().stringValue().hashCode();
      IntegerLiteral.push(gen, hashCode);
    } else {
      typeString().emitDup(gen);
      typeString().emitStoreLocal(gen, index_b);
      hashCodeMethod().emitInvokeMethod(gen,
          lookupType("java.lang", "Object"));
    }

    // Emit switch instruction
    int low = groups.isEmpty() ? 0 : groups.firstKey();
    int high = groups.isEmpty() ? 0 : groups.lastKey();

    int tableSwitchSize = 4 * (3 + (high - low + 1));
    int lookupSwitchSize = 4 * (2 + 2 * groups.size());
    int pad;
    int switchSize;
    int switchPos;
    boolean tableSwitch = tableSwitchSize < lookupSwitchSize;

    gen.addLabel(switch_label);

    // Select the switch type which produces the smallest switch instr.
    if (tableSwitch) {
      // TABLESWITCH
      gen.emit(Bytecode.TABLESWITCH);
      switchSize = tableSwitchSize;
    } else {
      // LOOKUPSWITCH
      gen.emit(Bytecode.LOOKUPSWITCH);
      switchSize = lookupSwitchSize;
    }

    pad = emitPad(gen);
    switchPos = gen.pos();

    // leave room for the address table
    gen.skip(switchSize);

    // Code generation for switch body
    for (CaseGroup group : groups.values()) {
      gen.addLabel(group.lbl);

      // Possible hash miss. Check for equality.
      Iterator<CaseLbl> iter = group.lbls.iterator();
      while (iter.hasNext()) {
        CaseLbl lbl = iter.next();
        int thenLbl;
        if (iter.hasNext()) {
          thenLbl = gen.constantPool().newLabel();
        } else
          // last conditional branches to end label
          thenLbl = end_label1;

        typeString().emitLoadLocal(gen, index_b);
        StringLiteral.push(gen, lbl.value);
        equalsMethod().emitInvokeMethod(gen,
            lookupType("java.lang", "Object"));
        gen.emitCompare(Bytecode.IFEQ, thenLbl);
        IntegerLiteral.push(gen, lbl.serial);
        typeInt().emitStoreLocal(gen, index_a);
        gen.emitGoto(end_label1);

        if (iter.hasNext()) {
          gen.addLabel(thenLbl);
        }
      }
    }

    // write jump address table
    int endpos = gen.pos();
    gen.setPos(switchPos);
    if (tableSwitch) {
      int defaultOffset = 1 + pad + switchSize;
      gen.add4(defaultOffset);
      gen.add4(low);
      gen.add4(high);
      for (int i = low; i <= high; i++) {
        if (groups.containsKey(i)) {
          CaseGroup group = groups.get(i);
          int offset = labelOffset(gen, group.lbl, switch_label);
          gen.add4(offset);
        } else {
          gen.add4(defaultOffset);
        }
      }
    } else {
      int defaultOffset = 1 + pad + switchSize;
      gen.add4(defaultOffset);
      gen.add4(groups.size());
      for (CaseGroup group : groups.values()) {
        gen.add4(group.hashCode);
        int offset = labelOffset(gen, group.lbl, switch_label);
        gen.add4(offset);
      }
    }
    gen.setPos(endpos);

    gen.addLabel(end_label1);
  }
  /**
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:290
   */
  private void genSecondSwitch(
      CodeGeneration gen,
      java.util.List<CaseLbl> labels,
      int index_a,
      CaseLbl defaultLbl) {
    int switch_label = gen.constantPool().newLabel();
    int default_label = gen.constantPool().newLabel();

    // push the switch variable
    typeInt().emitLoadLocal(gen, index_a);

    // Emit switch instruction
    gen.addLabel(switch_label);
    gen.emit(Bytecode.TABLESWITCH);
    int high = labels.size();
    int low = 0;
    int pad;
    int switchSize = 4 * (3 + (high - low + 1));
    int switchPos;

    pad = emitPad(gen);
    switchPos = gen.pos();
    gen.skip(switchSize);

    // Code generation for case labels

    for (CaseLbl lbl : labels) {
      gen.addLabel(lbl.lbl);
      lbl.createBCode(gen);
    }

    gen.addLabel(default_label);
    if (defaultLbl != null) {
      defaultLbl.createBCode(gen);
    }

    int endpos = gen.pos();
    gen.setPos(switchPos);
    int defaultOffset = 1 + pad + switchSize;
    gen.add4(defaultOffset);
    gen.add4(low);
    gen.add4(high);
    int offset = labelOffset(gen, default_label, switch_label);
    gen.add4(offset);
    for (CaseLbl lbl : labels) {
      offset = labelOffset(gen, lbl.lbl, switch_label);
      gen.add4(offset);
    }
    gen.setPos(endpos);

    gen.addLabel(end_label());
  }
  /**
   * Generate invocation of method
   * {@code java.lang.Object.hashCode()}.
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:347
   */
  private MethodDecl hashCodeMethod() {
    TypeDecl objectType = lookupType("java.lang", "Object");
    if (objectType.isUnknown()) {
      throw new Error("Could not find java.lang.Object");
    }
    for (MethodDecl method :
        (Collection<MethodDecl>) objectType.memberMethods("hashCode")) {
      if (method.getNumParameter() == 0) {
        return method;
      }
    }
    throw new Error("Could not find java.lang.Object.hashCode()");
  }
  /**
   * Generate invocation of method
   * {@code java.lang.Object.equals(java.lang.Object)}.
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:365
   */
  private MethodDecl equalsMethod() {
    TypeDecl objectType = lookupType("java.lang", "Object");
    if (objectType.isUnknown()) {
      throw new Error("Could not find java.lang.Object");
    }
    for (MethodDecl method :
        (Collection<MethodDecl>) objectType.memberMethods("equals")) {
      if (method.getNumParameter() == 1 &&
          method.getParameter(0).getTypeAccess().type() == objectType)
        return method;
    }
    throw new Error("Could not find java.lang.Object.equals()");
  }
  /**
   * @declaredat ASTNode:1
   */
  public SwitchStmt() {
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
  }
  /**
   * @declaredat ASTNode:13
   */
  public SwitchStmt(Expr p0, Block p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @declaredat ASTNode:20
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:26
   */
  public boolean mayHaveRewrite() {
    return false;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:32
   */
  public void flushAttrCache() {
    super.flushAttrCache();
    isDAafter_Variable_reset();
    isDUafter_Variable_reset();
    canCompleteNormally_reset();
    defaultCase_reset();
    end_label_reset();
    typeInt_reset();
    typeLong_reset();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:45
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @api internal
   * @declaredat ASTNode:51
   */
  public void flushRewriteCache() {
    super.flushRewriteCache();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:57
   */
  public SwitchStmt clone() throws CloneNotSupportedException {
    SwitchStmt node = (SwitchStmt) super.clone();
    return node;
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:64
   */
  public SwitchStmt copy() {
    try {
      SwitchStmt node = (SwitchStmt) clone();
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
   * @declaredat ASTNode:83
   */
  public SwitchStmt fullCopy() {
    return treeCopyNoTransform();
  }
  /**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   * @declaredat ASTNode:92
   */
  public SwitchStmt treeCopyNoTransform() {
    SwitchStmt tree = (SwitchStmt) copy();
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
   * @declaredat ASTNode:112
   */
  public SwitchStmt treeCopy() {
    doFullTraversal();
    return treeCopyNoTransform();
  }
  /**
   * @apilevel internal
   * @declaredat ASTNode:119
   */
  protected boolean is$Equal(ASTNode node) {
    return super.is$Equal(node);    
  }
  /**
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
  /**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Expr")
  public Expr getExpr() {
    return (Expr) getChild(0);
  }
  /**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   */
  public Expr getExprNoTransform() {
    return (Expr) getChildNoTransform(0);
  }
  /**
   * Replaces the Block child.
   * @param node The new node to replace the Block child.
   * @apilevel high-level
   */
  public void setBlock(Block node) {
    setChild(node, 1);
  }
  /**
   * Retrieves the Block child.
   * @return The current node used as the Block child.
   * @apilevel high-level
   */
  @ASTNodeAnnotation.Child(name="Block")
  public Block getBlock() {
    return (Block) getChild(1);
  }
  /**
   * Retrieves the Block child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Block child.
   * @apilevel low-level
   */
  public Block getBlockNoTransform() {
    return (Block) getChildNoTransform(1);
  }
  /**
   * @aspect AutoBoxingCodegen
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AutoBoxingCodegen.jrag:174
   */
    public void refined_AutoBoxingCodegen_SwitchStmt_createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    int cond_label = gen.constantPool().newLabel();
    int switch_label = gen.constantPool().newLabel();

    gen.emitGoto(cond_label);
    getBlock().createBCode(gen);
    if (canCompleteNormally()) {
      gen.emitGoto(end_label());
    }
    gen.addLabel(cond_label);
    getExpr().createBCode(gen);
    if (getExpr().type().isReferenceType()) {
      getExpr().type().emitUnboxingOperation(gen);
    }

    TreeMap map = new TreeMap();
    for (int i = 0; i < getBlock().getNumStmt(); i++) {
      if (getBlock().getStmt(i) instanceof ConstCase) {
        ConstCase ca = (ConstCase) getBlock().getStmt(i);
        map.put(new Integer(ca.getValue().constant().intValue()), ca);
      }
    }

    long low = map.isEmpty() ? 0 : ((Integer) map.firstKey()).intValue();
    long high = map.isEmpty() ? 0 : ((Integer) map.lastKey()).intValue();

    long tableSwitchSize = 8L + (high - low + 1L) * 4L;
    long lookupSwitchSize = 4L + map.size() * 8L;

    gen.addLabel(switch_label);
    if (tableSwitchSize < lookupSwitchSize) {
      gen.emit(Bytecode.TABLESWITCH);
      int pad = emitPad(gen);
      int defaultOffset = defaultOffset(gen, switch_label);
      if (defaultOffset == 0) {
        defaultOffset = 1 + pad + 4 + 4 + 4 + 4 * (int)(high - low + 1);
      }
      gen.add4(defaultOffset);
      gen.add4((int) low);
      gen.add4((int) high);
      for (long i = low; i <= high; i++) {
        ConstCase ca = (ConstCase) map.get(new Integer((int) i));
        if (ca != null) {
          int offset = gen.addressOf(ca.label())
            - gen.addressOf(switch_label);
          gen.add4(offset);
        } else {
          gen.add4(defaultOffset);
        }
      }
    } else {
      gen.emit(Bytecode.LOOKUPSWITCH);
      int pad = emitPad(gen);
      int defaultOffset = defaultOffset(gen, switch_label);
      if (defaultOffset == 0) {
        defaultOffset = 1 + pad + 4 + 4 + 8 * numCase();
      }
      gen.add4(defaultOffset);
      gen.add4(map.size());
      for (Iterator iter = map.values().iterator(); iter.hasNext(); ) {
        ConstCase ca = (ConstCase) iter.next();
        gen.add4(ca.getValue().constant().intValue());
        int offset = gen.addressOf(ca.label())
          - gen.addressOf(switch_label);
        gen.add4(offset);
      }
    }
    gen.addLabel(end_label());
  }
  /**
   * @aspect Enums
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\frontend\\Enums.jrag:566
   */
    public void refined_Enums_SwitchStmt_typeCheck() {
     TypeDecl type = getExpr().type();
    if ((!type.isIntegralType() || type.isLong()) && !type.isEnumDecl()) {
      error("Switch expression must be of char, byte, short, int, or enum type");
    }
  }
  /**
   * Overrides the type checking of the switch statement's expression.
   * 
   * <p>In JSR 334 a switch statement may use an expression of type String.
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\frontend\\StringsInSwitch.jrag:47
   */
    public void typeCheck() {
    TypeDecl type = getExpr().type();
    if ((!type.isIntegralType() || type.isLong()) && !type.isEnumDecl() && !type.isString()) {
      error("Switch expression must be of type char, byte, short, int, enum, or string");
    }
  }
  /**
   * Two switch statements are generated.
   * The first switch will switch on the hash code of the switch expression.
   * The first switch statement computes a value for a variable that selects
   * a case in the second switch statement.
   * 
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:126
   */
   
    public void createBCode(CodeGeneration gen) {
    if (getExpr().type().isString()) {
      // add line number for start of statement
      super.createBCode(gen);

      // Enumerate case labels with same hash value
      TreeMap< Integer, CaseGroup > groups = new TreeMap< Integer, CaseGroup >();
      java.util.List<CaseLbl> labels = new LinkedList<CaseLbl>();

      CaseLbl defaultLbl = null;
      CaseLbl caseLbl = null;
      int serial = 1;
      for (Stmt stmt : getBlock().getStmts()) {
        if (stmt instanceof ConstCase) {
          ConstCase cc = (ConstCase) stmt;
          caseLbl = new CaseLbl(cc, gen);
          caseLbl.serial = serial++;
          labels.add(caseLbl);
          int key = caseLbl.value.hashCode();
          if (groups.containsKey(key)) {
            groups.get(key).addCase(caseLbl);
          } else {
            CaseGroup group = new CaseGroup(this, key);
            group.addCase(caseLbl);
            groups.put(key, group);
          }
        } else if (stmt instanceof DefaultCase) {
          defaultLbl = new CaseLbl(hostType().constantPool().newLabel());
          caseLbl = defaultLbl;
        } else if (caseLbl != null) {
          caseLbl.addStmt(stmt);
        }
      }

      int index_a = localNumA();
      genFirstSwitch(gen, groups, index_a);
      genSecondSwitch(gen, labels, index_a, defaultLbl);

    } else {
      refined_AutoBoxingCodegen_SwitchStmt_createBCode(gen);
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean potentialTargetOf(Stmt branch) {
    ASTNode$State state = state();
    boolean potentialTargetOf_Stmt_value = branch.canBranchTo(this);

    return potentialTargetOf_Stmt_value;
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
      if (!(!noDefaultLabel() || getExpr().isDAafter(v))) {
        return false;
      }
      if (!(!switchLabelEndsBlock() || getExpr().isDAafter(v))) {
        return false;
      }
      if (!assignedAfterLastStmt(v)) {
        return false;
      }
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDAafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  @ASTNodeAnnotation.Attribute
  public boolean assignedAfterLastStmt(Variable v) {
    ASTNode$State state = state();
    boolean assignedAfterLastStmt_Variable_value = getBlock().isDAafter(v);

    return assignedAfterLastStmt_Variable_value;
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
      if (!(!noDefaultLabel() || getExpr().isDUafter(v))) {
        return false;
      }
      if (!(!switchLabelEndsBlock() || getExpr().isDUafter(v))) {
        return false;
      }
      if (!unassignedAfterLastStmt(v)) {
        return false;
      }
      for (Iterator iter = targetBreaks().iterator(); iter.hasNext(); ) {
        BreakStmt stmt = (BreakStmt) iter.next();
        if (!stmt.isDUafterReachedFinallyBlocks(v)) {
          return false;
        }
      }
      return true;
    }
  @ASTNodeAnnotation.Attribute
  public boolean unassignedAfterLastStmt(Variable v) {
    ASTNode$State state = state();
    boolean unassignedAfterLastStmt_Variable_value = getBlock().isDUafter(v);

    return unassignedAfterLastStmt_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean switchLabelEndsBlock() {
    ASTNode$State state = state();
    boolean switchLabelEndsBlock_value = getBlock().getNumStmt() > 0
          && getBlock().getStmt(getBlock().getNumStmt()-1) instanceof ConstCase;

    return switchLabelEndsBlock_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean lastStmtCanCompleteNormally() {
    ASTNode$State state = state();
    boolean lastStmtCanCompleteNormally_value = getBlock().canCompleteNormally();

    return lastStmtCanCompleteNormally_value;
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:100
   */
  @ASTNodeAnnotation.Attribute
  public boolean noStmts() {
    ASTNode$State state = state();
    try {
        for (int i = 0; i < getBlock().getNumStmt(); i++) {
          if (!(getBlock().getStmt(i) instanceof Case)) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public boolean noStmtsAfterLastLabel() {
    ASTNode$State state = state();
    boolean noStmtsAfterLastLabel_value = getBlock().getNumStmt() > 0
          && getBlock().getStmt(getBlock().getNumStmt()-1) instanceof Case;

    return noStmtsAfterLastLabel_value;
  }
  /**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:113
   */
  @ASTNodeAnnotation.Attribute
  public boolean noDefaultLabel() {
    ASTNode$State state = state();
    try {
        for (int i = 0; i < getBlock().getNumStmt(); i++) {
          if (getBlock().getStmt(i) instanceof DefaultCase) {
            return false;
          }
        }
        return true;
      }
    finally {
    }
  }
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_computed = false;
  /**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_value;
  /**
   * @apilevel internal
   */
  private void canCompleteNormally_reset() {
    canCompleteNormally_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public boolean canCompleteNormally() {
    if(canCompleteNormally_computed) {
      return canCompleteNormally_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    canCompleteNormally_value = lastStmtCanCompleteNormally() || noStmts()
          || noStmtsAfterLastLabel()
          || noDefaultLabel() || reachableBreak();
    if (isFinal && num == state().boundariesCrossed) {
      canCompleteNormally_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return canCompleteNormally_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean defaultCase_computed = false;
  /**
   * @apilevel internal
   */
  protected DefaultCase defaultCase_value;
  /**
   * @apilevel internal
   */
  private void defaultCase_reset() {
    defaultCase_computed = false;
    defaultCase_value = null;
  }
  @ASTNodeAnnotation.Attribute
  public DefaultCase defaultCase() {
    if(defaultCase_computed) {
      return defaultCase_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    defaultCase_value = defaultCase_compute();
    if (isFinal && num == state().boundariesCrossed) {
      defaultCase_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return defaultCase_value;
  }
  /**
   * @apilevel internal
   */
  private DefaultCase defaultCase_compute() {
      for (int i= 0; i < getBlock().getNumStmt(); i++) {
        if (getBlock().getStmt(i) instanceof DefaultCase) {
          return (DefaultCase) getBlock().getStmt(i);
        }
      }
      return null;
    }
  /**
   * @apilevel internal
   */
  protected boolean end_label_computed = false;
  /**
   * @apilevel internal
   */
  protected int end_label_value;
  /**
   * @apilevel internal
   */
  private void end_label_reset() {
    end_label_computed = false;
  }
  @ASTNodeAnnotation.Attribute
  public int end_label() {
    if(end_label_computed) {
      return end_label_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    end_label_value = hostType().constantPool().newLabel();
    if (isFinal && num == state().boundariesCrossed) {
      end_label_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return end_label_value;
  }
  /**
   * @attribute syn
   * @aspect CreateBCode
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\CreateBCode.jrag:1494
   */
  @ASTNodeAnnotation.Attribute
  public int numCase() {
    ASTNode$State state = state();
    try {
        int result = 0;
        for (int i = 0; i < getBlock().getNumStmt(); i++) {
          if (getBlock().getStmt(i) instanceof Case) {
            result++;
          }
        }
        return result;
      }
    finally {
    }
  }
  @ASTNodeAnnotation.Attribute
  public int break_label() {
    ASTNode$State state = state();
    int break_label_value = end_label();

    return break_label_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    boolean modifiedInScope_Variable_value = getBlock().modifiedInScope(var);

    return modifiedInScope_Variable_value;
  }
  @ASTNodeAnnotation.Attribute
  public boolean isSwitchWithString() {
    ASTNode$State state = state();
    boolean isSwitchWithString_value = getExpr().type().isString();

    return isSwitchWithString_value;
  }
  @ASTNodeAnnotation.Attribute
  public int localNumA() {
    ASTNode$State state = state();
    int localNumA_value = localNum();

    return localNumA_value;
  }
  @ASTNodeAnnotation.Attribute
  public int localNumB() {
    ASTNode$State state = state();
    int localNumB_value = localNum() + typeInt().variableSize();

    return localNumB_value;
  }
  @ASTNodeAnnotation.Attribute
  public int labelOffset(CodeGeneration gen, int lbl1, int lbl2) {
    ASTNode$State state = state();
    int labelOffset_CodeGeneration_int_int_value = gen.addressOf(lbl1) - gen.addressOf(lbl2);

    return labelOffset_CodeGeneration_int_int_value;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupType.jrag:86
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeInt() {
    if(typeInt_computed) {
      return typeInt_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeInt_value = getParent().Define_TypeDecl_typeInt(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      typeInt_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeInt_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeInt_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeInt_value;
  /**
   * @apilevel internal
   */
  private void typeInt_reset() {
    typeInt_computed = false;
    typeInt_value = null;
  }
  /**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\LookupType.jrag:88
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeLong() {
    if(typeLong_computed) {
      return typeLong_value;
    }
    ASTNode$State state = state();
    boolean intermediate = state.INTERMEDIATE_VALUE;
    state.INTERMEDIATE_VALUE = false;
    int num = state.boundariesCrossed;
    boolean isFinal = this.is$Final();
    typeLong_value = getParent().Define_TypeDecl_typeLong(this, null);
    if (isFinal && num == state().boundariesCrossed) {
      typeLong_computed = true;
    } else {
    }
    state.INTERMEDIATE_VALUE |= intermediate;

    return typeLong_value;
  }
  /**
   * @apilevel internal
   */
  protected boolean typeLong_computed = false;
  /**
   * @apilevel internal
   */
  protected TypeDecl typeLong_value;
  /**
   * @apilevel internal
   */
  private void typeLong_reset() {
    typeLong_computed = false;
    typeLong_value = null;
  }
  /**
   * @attribute inh
   * @aspect StringsInSwitch
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:45
   */
  @ASTNodeAnnotation.Attribute
  public TypeDecl typeString() {
    ASTNode$State state = state();
    TypeDecl typeString_value = getParent().Define_TypeDecl_typeString(this, null);

    return typeString_value;
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BranchTarget.jrag:245
   * @apilevel internal
   */
  public Stmt Define_Stmt_branchTarget(ASTNode caller, ASTNode child, Stmt branch) {
     {
      int childIndex = this.getIndexOfChild(caller);
      return branch.canBranchTo(this) ? this : branchTarget(branch);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:637
   * @apilevel internal
   */
  public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockNoTransform()) {
      return getExpr().isDAafter(v);
    }
    else if (caller == getExprNoTransform()){
    if (((ASTNode) v).isDescendantTo(this)) {
      return false;
    }
    boolean result = isDAbefore(v);
    return result;
  }
    else {
      return getParent().Define_boolean_isDAbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\DefiniteAssignment.jrag:1185
   * @apilevel internal
   */
  public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if (caller == getBlockNoTransform()) {
      return getExpr().isDUafter(v);
    }
    else if (caller == getExprNoTransform()) {
      return isDUbefore(v);
    }
    else {
      return getParent().Define_boolean_isDUbefore(this, caller, v);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:452
   * @apilevel internal
   */
  public boolean Define_boolean_insideSwitch(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return true;
    }
    else {
      return getParent().Define_boolean_insideSwitch(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\NameCheck.jrag:497
   * @apilevel internal
   */
  public Case Define_Case_bind(ASTNode caller, ASTNode child, Case c) {
    if (caller == getBlockNoTransform()){
    Block b = getBlock();
    for (int i = 0; i < b.getNumStmt(); i++) {
      if (b.getStmt(i) instanceof Case && ((Case) b.getStmt(i)).constValue(c)) {
        return (Case) b.getStmt(i);
      }
    }
    return null;
  }
    else {
      return getParent().Define_Case_bind(this, caller, c);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\TypeCheck.jrag:421
   * @apilevel internal
   */
  public TypeDecl Define_TypeDecl_switchType(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return getExpr().type();
    }
    else {
      return getParent().Define_TypeDecl_switchType(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:127
   * @apilevel internal
   */
  public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return reachable();
    }
    else {
      return getParent().Define_boolean_reachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\UnreachableStatements.jrag:220
   * @apilevel internal
   */
  public boolean Define_boolean_reportUnreachable(ASTNode caller, ASTNode child) {
    if (caller == getBlockNoTransform()) {
      return reachable();
    }
    else {
      return getParent().Define_boolean_reportUnreachable(this, caller);
    }
  }
  /**
   * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java7\\backend\\StringsInSwitch.jrag:50
   * @apilevel internal
   */
  public int Define_int_localNum(ASTNode caller, ASTNode child) {
     {
      int childIndex = this.getIndexOfChild(caller);
      return localNum() + typeInt().variableSize() + typeString().variableSize();
    }
  }
  /**
   * @apilevel internal
   */
  public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
