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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\ConstantPool.jrag:340
 */
public class ConstantInterfaceMethodref extends CPInfo {
  
    private int classname;

  
    private int nameandtype;

  
    public ConstantInterfaceMethodref(int classname, int nameandtype) {
      this.classname = classname;
      this.nameandtype = nameandtype;
    }

  
    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_InterfaceMethodref);
      out.writeChar(classname);
      out.writeChar(nameandtype);
    }

  
    public String toString() {
      return pos + " ConstantInterfaceMethodref: tag " + ConstantPool.CONSTANT_InterfaceMethodref + ", class_index: " + classname + ", name_and_type_index: " + nameandtype;
    }


}
