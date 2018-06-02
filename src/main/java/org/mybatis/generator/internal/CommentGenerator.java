package org.mybatis.generator.internal;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.ServiceImplTopLevelClass;
import org.mybatis.generator.api.dom.java.ServiceInterface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

public class CommentGenerator implements org.mybatis.generator.api.CommentGenerator {

	private static final String AUTHOR_KEY = "author";
	private Properties properties;
	private String author;
	private String dateFormatString;
	private boolean addFieldComment;
	private SimpleDateFormat dateFormat;
	private Map<String, IntrospectedColumn> columns;

	private IntrospectedTable introspectedTable;

	public CommentGenerator() {
		this.properties = new Properties();
		this.author = "SunDongsheng";
		this.dateFormatString = "yyyy-MM-dd";
		this.addFieldComment = false;
		this.columns = new HashMap<String, IntrospectedColumn>();
	}

	@Override
	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);
		this.author = properties.getProperty(AUTHOR_KEY, author);
		this.addFieldComment = isTrue(properties.getProperty("addFieldComment"));
		this.dateFormat = new SimpleDateFormat(this.dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT, dateFormatString));
	}

	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
		topLevelClass.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		if (!stringHasValue(remarks)) {
			remarks = topLevelClass.getType().getShortName();
		}
		String[] remarkLines = remarks.split(System.getProperty("line.separator"));
		for (String remarkLine : remarkLines) {
			topLevelClass.addJavaDocLine(" * " + remarkLine);
		}
		topLevelClass.addJavaDocLine(" *");
		setAuthorString(topLevelClass);
		setDateString(topLevelClass);
		topLevelClass.addJavaDocLine(" */");
	}

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		if (!(compilationUnit instanceof Interface) && !(compilationUnit instanceof ServiceImplTopLevelClass)) {
			return;
		}
		JavaElement i = (JavaElement) compilationUnit;
		i.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		if (!stringHasValue(remarks)) {
			remarks = compilationUnit.getType().getShortName();
		}
		String suffix = "的查询类";
		if (compilationUnit instanceof ServiceInterface) {
			suffix = "的接口类";
		}
		if (compilationUnit instanceof ServiceImplTopLevelClass) {
			suffix = "的接口实现类";
		}
		String[] remarkLines = remarks.split(System.getProperty("line.separator"));
		for (int j = 0; j < remarkLines.length; j++) {
			String remarkLine = remarkLines[j];
			i.addJavaDocLine(" * " + remarkLine + (j == (remarkLines.length - 1) ? suffix : ""));
		}
		i.addJavaDocLine(" *");
		setAuthorString(i);
		setDateString(i);
		i.addJavaDocLine(" */");
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		this.columns.put(field.getName(), introspectedColumn);
		if (!addFieldComment) {
			return;
		}
		String remarks = introspectedColumn.getRemarks();
		if (!stringHasValue(remarks)) {
			StringBuilder sb = new StringBuilder();
			sb.append(" * 此字段对应于数据库列：");
			sb.append(introspectedTable.getFullyQualifiedTable());
			sb.append('.');
			sb.append(introspectedColumn.getActualColumnName());
			remarks = sb.toString();
		}
		field.addJavaDocLine("/**");
		String[] remarkLines = remarks.split(System.getProperty("line.separator"));
		for (String remarkLine : remarkLines) {
			field.addJavaDocLine(" * " + remarkLine);
		}
		field.addJavaDocLine(" */");
	}

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
	}

	@Override
	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
	}

	@Override
	public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
	}

	@Override
	public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports) {
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
	}

	@Override
	public void addComment(XmlElement xmlElement) {
	}

	@Override
	public void addRootComment(XmlElement rootElement) {
	}

	private void setAuthorString(JavaElement javaElement) {
		if (!stringHasValue(author)) {
			return;
		}
		javaElement.addJavaDocLine(new StringBuilder(" * ").append("@").append(AUTHOR_KEY).append(" ").append(author).toString());
	}

	private void setDateString(JavaElement javaElement) {
		StringBuilder sb = new StringBuilder(" * ").append("@date ");
		if (null != dateFormat) {
			javaElement.addJavaDocLine(sb.append(dateFormat.format(new Date())).toString());
		} else {
			javaElement.addJavaDocLine(sb.append(new Date().toString()).toString());
		}
	}

}