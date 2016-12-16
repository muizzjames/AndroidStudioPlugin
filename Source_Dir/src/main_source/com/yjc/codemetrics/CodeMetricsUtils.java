package com.yjc.codemetrics;
/*                */

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;


import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 10/9/2015.
 */
public final class CodeMetricsUtils {

    public static Map<String, Set<VirtualFile>> getVirtualFiles(Project project)        //Get Hash value the fiel list of project
    {
        Map<String, Set<VirtualFile>> result = new HashMap<String, Set<VirtualFile>>();
        VirtualFile baseDir = project.getBaseDir();
        if (baseDir != null)
        {
            VirtualFile[] children = baseDir.getChildren();
            Set<VirtualFile> filesTobeMerged = new HashSet<VirtualFile>(Arrays.asList(children));

            Set<VirtualFile> modulesFilesTobeMerged = new HashSet<VirtualFile>();
            Module[] modules = ModuleManager.getInstance(project).getModules();
            for (Module module : modules)
            {
                VirtualFile moduleFile = module.getModuleFile();
                if (moduleFile == null)
                    continue;
                modulesFilesTobeMerged.add(moduleFile.getParent());
            }

            Set<VirtualFile> allFilesTobeMerged = new HashSet<VirtualFile>();
            allFilesTobeMerged.addAll(filesTobeMerged);
            allFilesTobeMerged.addAll(modulesFilesTobeMerged);

            result.putAll(getVirtualFiles((VirtualFile[])allFilesTobeMerged.toArray(new VirtualFile[allFilesTobeMerged.size()])));
        }
        return result;
    }

    public static Map<String, Set<VirtualFile>> getVirtualFiles(VirtualFile[] files)
    {
        Map result = new HashMap();
        for (VirtualFile file : files)
        {
            if ((file.getChildren() != null) && (file.getChildren().length != 0))
            {
                Map mapNew = getVirtualFiles(file.getChildren());
                Set<Map.Entry<String, Set<VirtualFile>>> entrySet = mapNew.entrySet();
                Iterator<Map.Entry<String, Set<VirtualFile>>> iterator = entrySet.iterator();
                while (iterator.hasNext())
                {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (result.containsKey(entry.getKey()))
                    {
                        ((Set)result.get(entry.getKey())).addAll((Collection)entry.getValue());
                    }
                    else
                    {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            else {
                if (file.isDirectory())
                    continue;
                String extension = file.getExtension();
                if ((extension == null) || (extension.trim().length() <= 0))
                    continue;
                extension = extension.toLowerCase();
                if (!result.containsKey(extension))
                {
                    result.put(extension, new HashSet());
                }
                Set virtualFiles = (Set)result.get(extension);
                virtualFiles.add(file);
            }
        }
        return result;
    }

    public static int getLineCount(VirtualFile file)        //Get Total Line Count of File
    {
        int lineCouint = 0;
        try
        {
            lineCouint = getLineCount(file.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return lineCouint;
    }

    public static int getCodeLineCount(VirtualFile file)        //Get Total Code Line Count of File
    {
        int codeLineCount = 0;
        try
        {
            codeLineCount = getCodeLineCount(file.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return codeLineCount;
    }

    public static int getCalledCodeLineCount(VirtualFile file)      //Get Total Called Method Count of file
    {
        int codeLineCount = 0;
        try
        {
            codeLineCount = getCalledCodeLineCount(file.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return codeLineCount;
    }

    public static int getHtmlCodeLineCount(VirtualFile file)        //Get Total Html Line Count of file
    {
        int codeLineCount = 0;
        try
        {
            codeLineCount = getHtmlCodeLineCount(file.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return codeLineCount;
    }

    private static int getLineCount(InputStream stream)
    {
        int lineCount = 0;
        try
        {
            BufferedInputStream inputStream = new BufferedInputStream(stream);
            LineNumberReader streamReader = new LineNumberReader(new InputStreamReader(inputStream));
            while (streamReader.ready())
            {
                streamReader.readLine();
                lineCount++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return lineCount;
    }

    private static int getCodeLineCount(InputStream stream)
    {
        boolean isInComment = false;
        Integer total = Integer.valueOf(0);
        Integer blank = Integer.valueOf(0);
        Integer comment = Integer.valueOf(0);
        Integer code = Integer.valueOf(0);
        try
        {
            BufferedInputStream inputStream = new BufferedInputStream(stream);
            LineNumberReader streamReader = new LineNumberReader(new InputStreamReader(inputStream));
            Integer localInteger1;
            while (streamReader.ready())
            {
                String line = streamReader.readLine();
                if (line != null)
                {
                    localInteger1 = total; Integer localInteger2 = total = Integer.valueOf(total.intValue() + 1);
                    line = line.trim();
                    if (line.length() == 0)
                    {
                        localInteger1 = blank; localInteger2 = blank = Integer.valueOf(blank.intValue() + 1);
                        continue;
                    }
                    if (line.startsWith("//"))
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        continue;
                    }
                    if (isInComment)
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        if (!line.contains("*/"))
                            continue;
                        isInComment = false; continue;
                    }

                    if (line.startsWith("/*"))
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        if (line.contains("*/"))
                            continue;
                        isInComment = true; continue;
                    }

                    localInteger1 = code; localInteger2 = code = Integer.valueOf(code.intValue() + 1);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return code;
    }


    private static int getCalledCodeLineCount(InputStream stream)
    {
        boolean isInComment = false;
        boolean isInMethod = false;
        Integer total = Integer.valueOf(0);
        Integer blank = Integer.valueOf(0);
        Integer comment = Integer.valueOf(0);
        Integer code = Integer.valueOf(0);
        Integer calledMethodcode = Integer.valueOf(0);
        try
        {
            BufferedInputStream inputStream = new BufferedInputStream(stream);
            LineNumberReader streamReader = new LineNumberReader(new InputStreamReader(inputStream));
            Integer localInteger1;
            while (streamReader.ready())
            {
                String line = streamReader.readLine();
                if (line != null)
                {
                    localInteger1 = total; Integer localInteger2 = total = Integer.valueOf(total.intValue() + 1);
                    line = line.trim();
                    if (line.length() == 0)
                    {
                        localInteger1 = blank; localInteger2 = blank = Integer.valueOf(blank.intValue() + 1);
                        continue;
                    }
                    if ((line.startsWith("private")|| line.startsWith("public") || line.startsWith("protected")) && !line.contains(";"))
                    {
                        localInteger1 = calledMethodcode; localInteger2 = calledMethodcode = Integer.valueOf(calledMethodcode.intValue() + 1);
                        continue;
                    }
                    if (isInComment)
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        if (!line.contains("*/"))
                            continue;
                        isInComment = false; continue;
                    }

                    if (line.startsWith("/*"))
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        if (line.contains("*/"))
                            continue;
                        isInComment = true; continue;
                    }



                    localInteger1 = code; localInteger2 = code = Integer.valueOf(code.intValue() + 1);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return calledMethodcode;
    }

    private static int getHtmlCodeLineCount(InputStream stream)
    {
        boolean isInComment = false;
        Integer total = Integer.valueOf(0);
        Integer blank = Integer.valueOf(0);
        Integer comment = Integer.valueOf(0);
        Integer code = Integer.valueOf(0);
        try
        {
            BufferedInputStream inputStream = new BufferedInputStream(stream);
            LineNumberReader streamReader = new LineNumberReader(new InputStreamReader(inputStream));
            Integer localInteger1;
            while (streamReader.ready())
            {
                String line = streamReader.readLine();
                if (line != null)
                {
                    localInteger1 = total; Integer localInteger2 = total = Integer.valueOf(total.intValue() + 1);
                    line = line.trim();
                    if (line.length() == 0)
                    {
                        localInteger1 = blank; localInteger2 = blank = Integer.valueOf(blank.intValue() + 1);
                        continue;
                    }
                    if (line.startsWith("//"))
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        continue;
                    }
                    if (isInComment)
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        if (!line.contains("-->"))
                            continue;
                        isInComment = false; continue;
                    }

                    if (line.startsWith("<!--"))
                    {
                        localInteger1 = comment; localInteger2 = comment = Integer.valueOf(comment.intValue() + 1);
                        if (line.contains("-->"))
                            continue;
                        isInComment = true; continue;
                    }

                    localInteger1 = code; localInteger2 = code = Integer.valueOf(code.intValue() + 1);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return code;
    }
    public static ArrayList<String> getActivityName(VirtualFile file)        //Get Total Line Count of File
    {
        ArrayList<String> actFiles = new ArrayList<String>();
        try
        {
            actFiles = getActivityName(file.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return actFiles;
    }
    private static ArrayList<String> getActivityName(InputStream stream)
    {
        boolean isInComment = false;
        ArrayList<String> strAtyName = new ArrayList<String>();

        StringBuffer buff = new StringBuffer();

        try
        {
            BufferedInputStream inputStream = new BufferedInputStream(stream);
            LineNumberReader streamReader = new LineNumberReader(new InputStreamReader(inputStream));
            while (streamReader.ready())
            {
                String line = streamReader.readLine();
                if (line != null)
                {
                    line = line + " ";
                    if( isInComment ) {
                        if (line.contains("*/")) {
                            String tail = line.substring(line.indexOf("*/")+2);
                            line = tail;
                            isInComment = false;
                        }
                        else
                            continue;
                    }
                    if (line.length() == 0)
                    {
                        continue;
                    }
                    if (line.contains("/*"))
                    {
                        String head="", tail="";
                        head = line.substring(0, line.indexOf("/*"));
                        if (line.contains("*/")){
                            tail = line.substring(line.indexOf("*/")+2);
                        }
                        else
                            isInComment = true;
                        line = head + tail;
                    }
                    if (line.contains("//"))
                    {
                        line = line.substring(0, line.indexOf("//"));
                        //continue;
                    }

                    buff.append(line);
                }
            }
            String strTemp = buff.toString();

            int nStart = strTemp.indexOf("class");
            if( nStart < 0)
                return strAtyName;
            int nEnd = strTemp.substring(nStart).indexOf("{");
            if( nEnd < 0)
                return strAtyName;
            String strClassLine = strTemp.substring(nStart, nStart+nEnd);

            String delimiters = " ";
            String pArray[] = strClassLine.split(delimiters);

            if(pArray.length<4){
                return strAtyName;
            }
            else{
                if( pArray[3].indexOf("Activity")> -1){
                    String xml = getLayoutXml(strTemp.toString());
                    strAtyName.add(pArray[1]);
                    strAtyName.add(xml);
                }
                return strAtyName;
            }
        }
        catch (IOException e)
        {
            return strAtyName;
        }
    }
    public static String getLayoutXml(String fileContent){

        int nStartFunc = 0;
        if( (nStartFunc = fileContent.indexOf("onCreate")) > -1){
            int nStartLayout = fileContent.indexOf("setContentView", nStartFunc);
            if( nStartLayout < 0 ) return "";
            int nEndLayout = fileContent.indexOf(")", nStartLayout);
            if( nEndLayout < 0 ) return "";
            String lineLayout = fileContent.substring(nStartLayout,nEndLayout );
            String[] layout = lineLayout.split("\\.");
            if(layout.length < 3)return "";
            return layout[2];
        }
        return "";
    }
    public static ArrayList<String> getXmlTagNames(VirtualFile file)        //Get Total Line Count of File
    {
        ArrayList<String> xmlTags = new ArrayList<String>();
        try
        {
            xmlTags = getXmlTagNames(file.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return xmlTags;
    }
    private static ArrayList<String> getXmlTagNames(InputStream stream)
    {
        boolean isInComment = false;
        ArrayList<String> xmlTags = new ArrayList<String>();
        StringBuffer buff = new StringBuffer();
        try
        {
            BufferedInputStream inputStream = new BufferedInputStream(stream);
            LineNumberReader streamReader = new LineNumberReader(new InputStreamReader(inputStream));
            while (streamReader.ready())
            {
                String line = streamReader.readLine();
                if (line != null)
                {
                    line = line + " ";
                    if( isInComment ) {
                        if (line.contains("-->")) {
                            String tail = line.substring(line.indexOf("-->")+3);
                            line = tail;
                            isInComment = false;
                        }
                        else
                            continue;
                    }
                    if (line.length() == 0)
                    {
                        continue;
                    }
                    if (line.contains("<!--"))
                    {
                        String head="", tail="";
                        head = line.substring(0, line.indexOf("<!--"));
                        if (line.contains("-->")){
                            tail = line.substring(line.indexOf("-->")+3);
                        }
                        else
                            isInComment = true;
                        line = head + tail;
                    }
                    buff.append(line);
                }
            }
            String strTemp = buff.toString();

            String delimiters = "<";
            String pArray[] = strTemp.split(delimiters);
            int nInput = 0, nOutput=0;
            for(int i=0; i<pArray.length; i++ ){
                String[] analysis = pArray[i].split(" ");
                if( analysis.length < 1) continue;
                if((analysis[0].indexOf("/")> -1)||(analysis[0].indexOf("?")> -1)) continue;
                if( analysis[0].contains("Button")||analysis[0].contains("EditText")||
                    analysis[0].contains("RadioGroup")||analysis[0].contains("RadioButton")||
                    analysis[0].contains("ToggleButton")||analysis[0].contains("DatePicker")||
                    analysis[0].contains("TimePicker")||analysis[0].contains("ImageButton")||
                    analysis[0].contains("CheckBox")||analysis[0].contains("Spinner"))
                        nInput++;
                if( analysis[0].contains("TextView")||analysis[0].contains("ListView")||
                    analysis[0].contains("GridView")||analysis[0].contains("View")||
                    analysis[0].contains("ImageView")||analysis[0].contains("ProgressBar")||
                    analysis[0].contains("GroupView"))
                        nOutput++;
            }
            xmlTags.add(String.valueOf(nInput));
            xmlTags.add(String.valueOf(nOutput));
        }
        catch (IOException e)
        {

        }
        return xmlTags;
    }
}
