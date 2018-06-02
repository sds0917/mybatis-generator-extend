package org.mybatis.generator.api.dom.java;

public class ServiceImplTopLevelClass extends TopLevelClass {

	public ServiceImplTopLevelClass(String typeName) {
		this(new FullyQualifiedJavaType(typeName));
	}

	public ServiceImplTopLevelClass(FullyQualifiedJavaType type) {
		super(type);
	}

}