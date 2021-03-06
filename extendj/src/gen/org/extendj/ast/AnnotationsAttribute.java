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
 * @aspect AnnotationsCodegen
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java5\\backend\\AnnotationsCodegen.jrag:77
 */
 class AnnotationsAttribute extends Attribute {
  
    public AnnotationsAttribute(ConstantPool cp, Collection annotations, String name) {
      super(cp, name);
      u2(annotations.size());
      for (Iterator iter = annotations.iterator(); iter.hasNext(); ) {
        ((Annotation) iter.next()).appendAsAttributeTo(this);
      }
    }


}
