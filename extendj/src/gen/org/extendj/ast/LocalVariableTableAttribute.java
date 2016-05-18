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
 * @aspect Attributes
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:121
 */
 class LocalVariableTableAttribute extends Attribute {
  
    public LocalVariableTableAttribute(CodeGeneration gen) {
      super(gen.constantPool(), "LocalVariableTable");
      u2(gen.localVariableTable.size());
      for (Iterator iter = gen.localVariableTable.iterator(); iter.hasNext(); ) {
        CodeGeneration.LocalVariableEntry e = (CodeGeneration.LocalVariableEntry)iter.next();
        u2(e.start_pc);
        u2(e.length);
        u2(e.name_index);
        u2(e.descriptor_index);
        u2(e.index);
      }
    }


}
