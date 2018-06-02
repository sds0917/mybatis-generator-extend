package org.mybatis.generator.codegen.mybatis3.javaservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.ServiceImplTopLevelClass;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImplExtend;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

public class SimpleJavaServiceImplGenerator extends SimpleJavaClientGenerator {

	private FullyQualifiedJavaType superInterface;
	private String mapperName = "mapper";

	public SimpleJavaServiceImplGenerator(String superInterface) {
		this(new FullyQualifiedJavaType(superInterface));
	}

	public SimpleJavaServiceImplGenerator(FullyQualifiedJavaType superInterface) {
		super(false);
		this.superInterface = superInterface;
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		IntrospectedTableMyBatis3SimpleImplExtend batis3SimpleImpl = (IntrospectedTableMyBatis3SimpleImplExtend) introspectedTable;
		progressCallback.startTask(Messages.getString("Generating Service Interface for table {0}", batis3SimpleImpl.getFullyQualifiedTable().toString()));
		ServiceImplTopLevelClass topLevelClass = new ServiceImplTopLevelClass(batis3SimpleImpl.getMyBatis3JavaServiceImplType());
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		topLevelClass.addAnnotation("@Service");
		context.getCommentGenerator().addJavaFileComment(topLevelClass);
		if (null != superInterface) {
			topLevelClass.addImportedType(superInterface);
			topLevelClass.addSuperInterface(new FullyQualifiedJavaType(superInterface.getShortName()));
		}

		addMapperField(topLevelClass);
		addDeleteByPrimaryKeyMethod(topLevelClass);
		addInsertMethod(topLevelClass);
		addSelectByPrimaryKeyMethod(topLevelClass);
		addSelectAllMethod(topLevelClass);
		addUpdateByPrimaryKeyMethod(topLevelClass);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		answer.add(topLevelClass);
		return answer;
	}

	private void addUpdateByPrimaryKeyMethod(ServiceImplTopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		importedTypes.add(parameterType);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName(introspectedTable.getUpdateByPrimaryKeyStatementId());
		method.addParameter(new Parameter(parameterType, "record"));
		method.addBodyLine(new StringBuffer("return ").append(mapperName).append(".").append(method.getName()).append("(record);").toString());
		if (null != superInterface) {
			method.addAnnotation("@Override");
		}
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	private void addSelectAllMethod(ServiceImplTopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		importedTypes.add(listType);
		returnType.addTypeArgument(listType);
		method.setReturnType(returnType);
		method.setName(introspectedTable.getSelectAllStatementId());
		method.addBodyLine(new StringBuffer("return ").append(mapperName).append(".").append(method.getName()).append("();").toString());
		if (null != superInterface) {
			method.addAnnotation("@Override");
		}
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	private void addInsertMethod(ServiceImplTopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(introspectedTable.getInsertStatementId());
		FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();
		importedTypes.add(parameterType);
		method.addParameter(new Parameter(parameterType, "record"));
		method.addBodyLine(new StringBuffer("return ").append(mapperName).append(".").append(method.getName()).append("(record);").toString());
		if (null != superInterface) {
			method.addAnnotation("@Override");
		}
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	private void addDeleteByPrimaryKeyMethod(ServiceImplTopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName(introspectedTable.getDeleteByPrimaryKeyStatementId());
		StringBuffer sb = new StringBuffer("return ").append(mapperName).append(".").append(method.getName()).append("(");
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
			importedTypes.add(type);
			Parameter parameter = new Parameter(type, introspectedColumn.getJavaProperty());
			method.addParameter(parameter);
			sb.append(parameter.getName());
		}
		method.addBodyLine(sb.append(");").toString());
		if (null != superInterface) {
			method.addAnnotation("@Override");
		}
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	private void addSelectByPrimaryKeyMethod(ServiceImplTopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType returnType = introspectedTable.getRules().calculateAllFieldsClass();
		method.setReturnType(returnType);
		importedTypes.add(returnType);
		method.setName(introspectedTable.getSelectByPrimaryKeyStatementId());
		StringBuffer sb = new StringBuffer("return ").append(mapperName).append(".").append(method.getName()).append("(");
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		for (int i = 0; i < introspectedColumns.size(); i++) {
			FullyQualifiedJavaType type = introspectedColumns.get(i).getFullyQualifiedJavaType();
			importedTypes.add(type);
			Parameter parameter = new Parameter(type, introspectedColumns.get(i).getJavaProperty());
			method.addParameter(parameter);
			if (introspectedColumns.size() != (i + 1)) {
				sb.append(", ");
			}
			sb.append(parameter.getName());
		}
		method.addBodyLine(sb.append(");").toString());
		if (null != superInterface) {
			method.addAnnotation("@Override");
		}
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	private void addMapperField(ServiceImplTopLevelClass topLevelClass) {
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(new FullyQualifiedJavaType(type.getShortName()));
		field.setName(mapperName);
		field.addAnnotation("@Autowired");
		topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
		topLevelClass.addImportedType(type);
		topLevelClass.addField(field);
	}

}