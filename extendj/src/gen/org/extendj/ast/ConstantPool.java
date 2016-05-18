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
 * @ast class
 * @aspect ConstantPool
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\ConstantPool.jrag:38
 */
public class ConstantPool extends java.lang.Object {
  
    public TypeDecl typeDecl;

  
    public ConstantPool(TypeDecl typeDecl) {
      this.typeDecl = typeDecl;
    }

  

    public static final byte CONSTANT_Class              = 7;

  
    public static final byte CONSTANT_Fieldref           = 9;

  
    public static final byte CONSTANT_Methodref          = 10;

  
    public static final byte CONSTANT_InterfaceMethodref = 11;

  
    public static final byte CONSTANT_String             = 8;

  
    public static final byte CONSTANT_Integer            = 3;

  
    public static final byte CONSTANT_Float              = 4;

  
    public static final byte CONSTANT_Long               = 5;

  
    public static final byte CONSTANT_Double             = 6;

  
    public static final byte CONSTANT_NameAndType        = 12;

  
    public static final byte CONSTANT_Utf8               = 1;

  

    private int posCounter = 1;

  

    private ArrayList list = new ArrayList();

  

    private void addCPInfo(CPInfo info) {
      info.pos = posCounter;
      posCounter += info.size();
      if (posCounter > 0xFFFF) {
        throw new Error("Too many constants in class!");
      }
      list.add(info);
    }

  

    // for debugging purposes
    public int numElements() {
      return list.size();
    }

  
    public String toString() {
      StringBuffer s = new StringBuffer();
      for(Iterator iter = list.iterator(); iter.hasNext(); ) {
        s.append(iter.next().toString());
        s.append("\n");
      }
      return s.toString();
    }

  

    public void emit(DataOutputStream out) throws IOException {
      out.writeChar(posCounter);
      for(Iterator iter = list.iterator(); iter.hasNext(); ) {
        CPInfo info = (CPInfo)iter.next();
        info.emit(out);
      }
    }

  

    private int labelCounter = 1;

  
    public int newLabel() {
      return labelCounter++;
    }

  

    private HashMap classConstants = new HashMap();

  
    public int addClass(String name) {
      Map map = classConstants;
      Object key = name;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantClass(addUtf8(name.replace('.', '/')));
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap fieldrefConstants = new HashMap();

  
    public int addFieldref(String classname, String name, String type) {
      Map map = fieldrefConstants;
      Object key = classname + name + type;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantFieldref(addClass(classname), addNameAndType(name, type));
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap methodrefConstants = new HashMap();

  
    public int addMethodref(String classname, String name, String desc) {
      Map map = methodrefConstants;
      Object key = classname + name + desc;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantMethodref(addClass(classname), addNameAndType(name, desc));
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap interfaceMethodrefConstants = new HashMap();

  
    public int addInterfaceMethodref(String classname, String name, String desc) {
      Map map = interfaceMethodrefConstants;
      Object key = classname + name + desc;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantInterfaceMethodref(addClass(classname), addNameAndType(name, desc));
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap nameAndTypeConstants = new HashMap();

  
    public int addNameAndType(String name, String type) {
      Map map = nameAndTypeConstants;
      Object key = name + type;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantNameAndType(addUtf8(name), addUtf8(type));
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap utf8Constants = new HashMap();

  
    public int addUtf8(String name) {
      Map map = utf8Constants;
      Object key = name;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantUtf8(name);
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap stringConstants = new HashMap();

  

    /**
     * Add value to constant pool.
     * @return index of value in constant pool
     */
    public int addConstant(String val) {
      Map map = stringConstants;
      Object key = val;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantString(addUtf8(val));
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap intConstants = new HashMap();

  

    /**
     * Add value to constant pool.
     * @return index of value in constant pool
     */
    public int addConstant(int val) {
      Map map = intConstants;
      Object key = new Integer(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantInteger(val);
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap floatConstants = new HashMap();

  

    /**
     * Add value to constant pool.
     * @return index of value in constant pool
     */
    public int addConstant(float val) {
      Map map = floatConstants;
      Object key = new Float(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantFloat(val);
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap longConstants = new HashMap();

  

    /**
     * Add value to constant pool.
     * @return index of value in constant pool
     */
    public int addConstant(long val) {
      Map map = longConstants;
      Object key = new Long(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantLong(val);
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

  

    private HashMap doubleConstants = new HashMap();

  

    /**
     * Add value to constant pool.
     * @return index of value in constant pool
     */
    public int addConstant(double val) {
      Map map = doubleConstants;
      Object key = new Double(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantDouble(val);
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }


}
