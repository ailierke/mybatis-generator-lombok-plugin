package com.janus.mybatis.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;
public class MyCommentGenerator implements CommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;
    private SimpleDateFormat dateFormat;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addJavaFileComment(org.mybatis.generator.api.dom.java.CompilationUnit)
     */
    public void addJavaFileComment(final CompilationUnit compilationUnit) {
        // add no file level comments by default
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     *
     * @param xmlElement
     *            the xml element
     */
    public void addComment(final XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        final StringBuilder sb = new StringBuilder();
//        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
//        xmlElement
//                .addElement(new TextElement(
//                        "  This element is automatically generated by MyBatis Generator, do not modify.")); //$NON-NLS-1$
//
//        final String s = getDateString();
//        if (s != null) {
//            sb.setLength(0);
//            sb.append("  This element was generated on "); //$NON-NLS-1$
//            sb.append(s);
//            sb.append('.');
//            xmlElement.addElement(new TextElement(sb.toString()));
//        }

        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addRootComment(org.mybatis.generator.api.dom.xml.XmlElement)
     */
    public void addRootComment(final XmlElement rootElement) {
        // add no document level comments by default
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addConfigurationProperties(java.util.Properties)
     */
    public void addConfigurationProperties(final Properties properties) {
        this.properties.putAll(properties);

        this.properties.putAll(properties);

        suppressDate = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

       public static boolean isTrue(String s) {
            return "true".equalsIgnoreCase(s); //$NON-NLS-1$
       }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement
     *            the java element
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    protected void addJavadocTag(final JavaElement javaElement,
            final boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        final StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
        }
        final String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addClassComment(final InnerClass innerClass,
            final IntrospectedTable introspectedTable) {
    	 if (suppressAllComments) {
             return;
         }

         StringBuilder sb = new StringBuilder();

         innerClass.addJavaDocLine("/**");
         sb.append(" * ");
         sb.append(introspectedTable.getFullyQualifiedTable());
         innerClass.addJavaDocLine(sb.toString());

         sb.setLength(0);
         sb.append(" * @author ");
         sb.append(properties.getProperty("user.name"));
         sb.append(" ");
         sb.append(currentDateStr);

         //      addJavadocTag(innerClass, markAsDoNotDelete);

         innerClass.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addTopLevelClassComment(org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addModelClassComment(final TopLevelClass topLevelClass,
            final IntrospectedTable introspectedTable) {
       
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addEnumComment(org.mybatis.generator.api.dom.java.InnerEnum, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addEnumComment(final InnerEnum innerEnum,
            final IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

//        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
//        innerEnum
//                .addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);

        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addFieldComment(final Field field,
            final IntrospectedTable introspectedTable,
            final IntrospectedColumn introspectedColumn) {
    	if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString());

        //      addJavadocTag(field, false);

        field.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addFieldComment(final Field field, final IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        final StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field
                .addJavaDocLine(" * This field was generated by MyBatis Generator."); //$NON-NLS-1$

        sb.append(" * This field corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(sb.toString());

        addJavadocTag(field, false);

        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGeneralMethodComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addGeneralMethodComment(final Method method,
            final IntrospectedTable introspectedTable) {
    	 if (suppressAllComments) {
             return;
         }

         StringBuilder sb = new StringBuilder();

         method.addJavaDocLine("/**"); //$NON-NLS-1$
         List<Parameter> parameters =  method.getParameters();
         for (Parameter parameter : parameters) {
             sb.append(" *@param  ");
             sb.append(parameter.getName());
             sb.append(".");
             method.addJavaDocLine(sb.toString());
             sb.delete(0, sb.length());
         }
         if(method.getReturnType() != null) {
        	 method.addJavaDocLine(method.getReturnType().getShortName()==null ? ""
                     :" *@return "+method.getReturnType().getShortName()+".");
             addJavadocTag(method, false);
         }

         method.addJavaDocLine(" */"); 
    }

    public void addGetterComment(final Method method,
            final IntrospectedTable introspectedTable,
            final IntrospectedColumn introspectedColumn) {
    	if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");

        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        //      addJavadocTag(method, false);

        method.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addSetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addSetterComment(final Method method,
            final IntrospectedTable introspectedTable,
            final IntrospectedColumn introspectedColumn) {
    	 if (suppressAllComments) {
             return;
         }


         method.addJavaDocLine("/**");
         StringBuilder sb = new StringBuilder();
         sb.append(" * ");
         sb.append(introspectedColumn.getRemarks());
         method.addJavaDocLine(sb.toString());

         Parameter parm = method.getParameters().get(0);
         sb.setLength(0);
         sb.append(" * @param ");
         sb.append(parm.getName());
         sb.append(" ");
         sb.append(introspectedColumn.getRemarks());
         method.addJavaDocLine(sb.toString());

         //      addJavadocTag(method, false);

         method.addJavaDocLine(" */");
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable, boolean)
     */
    public void addClassComment(final InnerClass innerClass,
            final IntrospectedTable introspectedTable, final boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(" * @author ");
        sb.append(properties.getProperty("user.name"));
        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass.addJavaDocLine(" * "); //$NON-NLS-1$

        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
}