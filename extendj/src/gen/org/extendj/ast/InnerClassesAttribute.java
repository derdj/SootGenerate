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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\backend\\Attributes.jrag:92
 */
 class InnerClassesAttribute extends Attribute {
  
    public InnerClassesAttribute(TypeDecl typeDecl) {
      super(typeDecl.constantPool(), "InnerClasses");
      ConstantPool c = typeDecl.constantPool();
      Collection list = typeDecl.innerClassesAttributeEntries();
      u2(list.size());
      for (Iterator iter = list.iterator(); iter.hasNext(); ) {
        TypeDecl type = (TypeDecl) iter.next();
        u2(c.addClass(type.constantPoolName())); // inner_class_info_index
        u2(type.isMemberType() ? c.addClass(type.enclosingType().constantPoolName()) : 0); // outer_class_info_index
        u2(type.isAnonymous() ? 0 : c.addUtf8(type.name())); // inner_name_index
        u2(type.isInterfaceDecl() ? (type.flags() | Modifiers.ACC_INTERFACE) : type.flags()); // inner_class_access_flags
      }
    }


}
