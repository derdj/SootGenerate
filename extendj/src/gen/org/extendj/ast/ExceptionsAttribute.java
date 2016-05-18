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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:181
 */
 class ExceptionsAttribute extends Attribute {
  
    public ExceptionsAttribute(CodeGeneration gen, ExceptionHolder m) {
      super(gen.constantPool(), "Exceptions");
      u2(m.getNumException());
      for (int i = 0; i < m.getNumException(); i++) {
        u2(gen.constantPool().addClass(m.getException(i).type().constantPoolName()));
      }
    }


}
