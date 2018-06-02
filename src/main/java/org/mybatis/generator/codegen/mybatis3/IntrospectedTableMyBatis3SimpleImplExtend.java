package org.mybatis.generator.codegen.mybatis3;

import java.util.List;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.mybatis3.javaservice.SimpleJavaServiceGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class IntrospectedTableMyBatis3SimpleImplExtend extends IntrospectedTableMyBatis3SimpleImpl {

	private static final String ATTR_MYBATIS3_JAVA_SERVICE_TYPE = "ATTR_MYBATIS3_JAVA_SERVICE_TYPE", ATTR_MYBATIS3_JAVA_SERVICEIMPL_TYPE = "ATTR_MYBATIS3_JAVA_SERVICEIMPL_TYPE", DEFAULT_SERVICE_PACKAGE = "org.mybatis.generator.service";
	private String servicePackage;

	@Override
	public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {
		super.calculateGenerators(warnings, progressCallback);
		this.servicePackage = context.getJavaClientGeneratorConfiguration().getProperties().getProperty("servicePackage", DEFAULT_SERVICE_PACKAGE);
		if (!StringUtility.stringHasValue(servicePackage)) {
			return;
		}
		SimpleJavaServiceGenerator serviceGenerator = new SimpleJavaServiceGenerator();
		initializeAbstractGenerator(serviceGenerator, warnings, progressCallback);
		setMyBatis3JavaServiceType();
		setMyBatis3JavaServiceImplType();
		clientGenerators.add(serviceGenerator);
	}

	public String getMyBatis3JavaServiceImplType() {
		return (String) getAttribute(ATTR_MYBATIS3_JAVA_SERVICEIMPL_TYPE);
	}

	private void setMyBatis3JavaServiceImplType() {
		StringBuilder sb = new StringBuilder();
		sb.append(servicePackage).append('.').append("impl").append(".");
		if (StringUtility.stringHasValue(tableConfiguration.getMapperName())) {
			sb.append(tableConfiguration.getMapperName());
		} else {
			if (StringUtility.stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
				sb.append(fullyQualifiedTable.getDomainObjectSubPackage()).append('.');
			}
			sb.append(fullyQualifiedTable.getDomainObjectName());
		}
		setAttribute(ATTR_MYBATIS3_JAVA_SERVICEIMPL_TYPE, sb.append("ServiceImpl").toString());
	}

	public String getMyBatis3JavaServiceType() {
		return (String) getAttribute(ATTR_MYBATIS3_JAVA_SERVICE_TYPE);
	}

	private void setMyBatis3JavaServiceType() {
		StringBuilder sb = new StringBuilder();
		sb.append(servicePackage).append('.');
		if (StringUtility.stringHasValue(tableConfiguration.getMapperName())) {
			sb.append("I").append(tableConfiguration.getMapperName());
		} else {
			if (StringUtility.stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
				sb.append(fullyQualifiedTable.getDomainObjectSubPackage()).append('.');
			}
			sb.append("I").append(fullyQualifiedTable.getDomainObjectName());
		}
		setAttribute(ATTR_MYBATIS3_JAVA_SERVICE_TYPE, sb.append("Service").toString());

	}

}