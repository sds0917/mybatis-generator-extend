package org.mybatis.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.util.ObjectUtils;

/**
 * 实体类和字段注释生成插件，此功能依赖于表和列的comment属性
 * 
 * @author SunDongsheng
 * @date 2018年6月2日
 */
public class CommentsPlugin extends PluginAdapter {

	private String author;
	private String dateFormatPattern;

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		author = properties.getProperty("author", "SunDongsheng");
		dateFormatPattern = properties.getProperty("dateFormatPattern", "yyyy年MM月dd日");
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		String remarks = introspectedTable.getRemarks();
		if (ObjectUtils.isEmpty(remarks)) {
			remarks = topLevelClass.getType().getShortName();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("/**\n");
		sb.append(" * ").append(remarks).append("\n");
		sb.append(" * @author ").append(author).append("\n");
		sb.append(" * @date ").append(new SimpleDateFormat(dateFormatPattern).format(new Date())).append("\n");
		sb.append(" */");
		topLevelClass.addJavaDocLine(sb.toString());
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		String remarks = introspectedColumn.getRemarks();
		if (ObjectUtils.isEmpty(remarks)) {
			return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("/**\n");
		sb.append("     * ").append(remarks).append("\n");
		sb.append("     */");
		field.addJavaDocLine(sb.toString());
		return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
	}

}