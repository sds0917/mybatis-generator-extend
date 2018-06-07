package org.mybatis.generator.codegen.mybatis3.javaservice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.ServiceInterface;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImplExtend;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class SimpleJavaServiceGenerator extends SimpleJavaClientGenerator {

    private IntrospectedTableMyBatis3SimpleImplExtend batis3SimpleImpl;
    // 是否生成service接口类
    private boolean service;

    public SimpleJavaServiceGenerator() {
        super(false);
        this.service = true;
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        batis3SimpleImpl = (IntrospectedTableMyBatis3SimpleImplExtend) introspectedTable;
        Properties prop = context.getJavaClientGeneratorConfiguration().getProperties();
        prop.setProperty("service", String.valueOf(this.service = StringUtility.isTrue(prop.getProperty("service", "true"))));
        progressCallback.startTask(MessageFormat.format("Generating Service Interface for table {0}", batis3SimpleImpl.getFullyQualifiedTable()));
        ServiceInterface interfaze = new ServiceInterface(batis3SimpleImpl.getMyBatis3JavaServiceType());
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(interfaze);

        addDeleteByPrimaryKeyMethod(interfaze);
        addInsertMethod(interfaze);
        addSelectByPrimaryKeyMethod(interfaze);
        addSelectAllMethod(interfaze);
        addUpdateByPrimaryKeyMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (service) {
            answer.add(interfaze);
        }
        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }
        return answer;
    }

    @Override
    public List<CompilationUnit> getExtraCompilationUnits() {
        List<CompilationUnit> list = new ArrayList<CompilationUnit>();

        SimpleJavaServiceImplGenerator serviceImplGenerator = new SimpleJavaServiceImplGenerator(batis3SimpleImpl.getMyBatis3JavaServiceType());
        serviceImplGenerator.setContext(context);
        serviceImplGenerator.setIntrospectedTable(batis3SimpleImpl);
        serviceImplGenerator.setProgressCallback(progressCallback);
        serviceImplGenerator.setWarnings(warnings);
        list.addAll(serviceImplGenerator.getCompilationUnits());

        SimpleJavaControllerGenerator controllerGenerator = new SimpleJavaControllerGenerator();
        controllerGenerator.setContext(context);
        controllerGenerator.setIntrospectedTable(batis3SimpleImpl);
        controllerGenerator.setProgressCallback(progressCallback);
        controllerGenerator.setWarnings(warnings);
        list.addAll(controllerGenerator.getCompilationUnits());

        return list;
    }

}