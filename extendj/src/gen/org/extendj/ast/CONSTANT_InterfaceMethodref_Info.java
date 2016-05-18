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
 * @aspect BytecodeCONSTANT
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BytecodeCONSTANT.jrag:188
 */
 class CONSTANT_InterfaceMethodref_Info extends CONSTANT_Info {
  
    public int class_index;

  
    public int name_and_type_index;

  

    public CONSTANT_InterfaceMethodref_Info(AbstractClassfileParser parser) throws IOException {
      super(parser);
      class_index = p.u2();
      name_and_type_index = p.u2();
    }

  

    public String toString() {
      return "InterfaceMethodRefInfo: " + p.constantPool[class_index] + " "
        + p.constantPool[name_and_type_index];
    }


}
