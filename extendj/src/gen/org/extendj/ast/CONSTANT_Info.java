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
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\BytecodeCONSTANT.jrag:156
 */
 class CONSTANT_Info extends java.lang.Object {
  
    protected AbstractClassfileParser p;

  
    public CONSTANT_Info(AbstractClassfileParser parser) {
      p = parser;
    }

  
    public Expr expr() {
      throw new Error("CONSTANT_info.expr() should not be computed for " + getClass().getName());
    }

  
    public Expr exprAsBoolean() {
      return expr();
    }


}
