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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\ConstantPool.jrag:357
 */
public class ConstantNameAndType extends CPInfo {
  
    private int name;

  
    private int type;

  
    public ConstantNameAndType(int name, int type) {
      this.name = name;
      this.type = type;
    }

  
    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_NameAndType);
      out.writeChar(name);
      out.writeChar(type);
    }

  
    public String toString() {
      return pos + " NameAndType: tag " + ConstantPool.CONSTANT_NameAndType + ", name_index: " + name + ", descriptor_index: " + type;
    }


}
