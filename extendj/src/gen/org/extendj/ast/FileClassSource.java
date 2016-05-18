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
 * @aspect PathPart
 * @declaredat C:\\Users\\Daniel\\Desktop\\masterzeugs\\extension-base\\extendj\\java4\\frontend\\PathPart.jadd:159
 */
public class FileClassSource extends ClassSource {
  
    private final String filePath;

  

    public FileClassSource(PathPart sourcePath, String path) {
      super(sourcePath);
      this.filePath = path;
    }

  

    @Override
    public long lastModified() {
      // last modification time computed only if needed
      File file = new File(filePath);
      return file.lastModified();
    }

  

    @Override
    public InputStream openInputStream() throws IOException {
      File file = new File(filePath);
      return new FileInputStream(file);
    }

  

    @Override
    public String pathName() {
      return filePath;
    }


}
