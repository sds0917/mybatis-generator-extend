package org.mybatis.generator.codegen.mybatis3.javaservice;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.ServiceInterface;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImplExtend;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

public class SimpleJavaServiceGenerator extends SimpleJavaClientGenerator {

	private IntrospectedTableMyBatis3SimpleImplExtend batis3SimpleImpl;

	public SimpleJavaServiceGenerator() {
		super(false);
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		batis3SimpleImpl = (IntrospectedTableMyBatis3SimpleImplExtend) introspectedTable;
		progressCallback.startTask(Messages.getString("Generating Service Interface for table {0}", batis3SimpleImpl.getFullyQualifiedTable().toString()));
		ServiceInterface interfaze = new ServiceInterface(batis3SimpleImpl.getMyBatis3JavaServiceType());
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		context.getCommentGenerator().addJavaFileComment(interfaze);

		addDeleteByPrimaryKeyMethod(interfaze);
		addInsertMethod(interfaze);
		addSelectByPrimaryKeyMethod(interfaze);
		addSelectAllMethod(interfaze);
		addUpdateByPrimaryKeyMethod(interfaze);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		answer.add(interfaze);
		List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
		if (extraCompilationUnits != null) {
			answer.addAll(extraCompilationUnits);
		}
		return answer;
	}

	@Override
	public List<CompilationUnit> getExtraCompilationUnits() {
		SimpleJavaServiceImplGenerator serviceImplGenerator = new SimpleJavaServiceImplGenerator(batis3SimpleImpl.getMyBatis3JavaServiceType());
		serviceImplGenerator.setContext(context);
		serviceImplGenerator.setIntrospectedTable(batis3SimpleImpl);
		serviceImplGenerator.setProgressCallback(progressCallback);
		serviceImplGenerator.setWarnings(warnings);
		return serviceImplGenerator.getCompilationUnits();
	}

}