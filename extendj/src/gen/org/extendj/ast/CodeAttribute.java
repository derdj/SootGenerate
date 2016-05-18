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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:148
 */
 class CodeAttribute extends Attribute {
  
    public CodeAttribute(CodeGeneration codeGen, MethodDecl m) {
      super(codeGen.constantPool(), "Code");
      u2(codeGen.maxStackDepth());
      u2(codeGen.maxLocals());
      u4(codeGen.pos()); // code_length
      append(codeGen.toArray());
      u2(codeGen.exceptions.size());
      for (Iterator iter = codeGen.exceptions.iterator(); iter.hasNext(); ) {
        CodeGeneration.ExceptionEntry e = (CodeGeneration.ExceptionEntry)iter.next();
        u2(e.start_pc);
        u2(e.end_pc);
        u2(e.handlerPC());
        u2(e.catch_type);
      }

      if (m == null || !m.getModifiers().isSynthetic()) {
        u2(2); // Attribute count
        append(new LineNumberTableAttribute(codeGen));
        append(new LocalVariableTableAttribute(codeGen));
      } else {
        u2(0); // Attribute count
      }
    }


}
