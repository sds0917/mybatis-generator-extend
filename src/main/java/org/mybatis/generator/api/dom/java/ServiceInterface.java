package org.mybatis.generator.api.dom.java;

public class ServiceInterface extends org.mybatis.generator.api.dom.java.Interface {

	public ServiceInterface(String type) {
		this(new FullyQualifiedJavaType(type));
	}

	public ServiceInterface(FullyQualifiedJavaType type) {
		super(type);
	}

}