# MyBatis Generator Lombok plugin and Comment

## 实现的功能

- 主要整合了lombok插件实现getter/setter等通用方法的自动生成，同时自定义实现了一个注释生成器，
通过抓取数据库表里面的注释作为实体类的注释内容。

## 插件的用法

之后你只要在你的要使用这个插件的项目的pom.xml中加入如下内容便可：

```xml

<plugin>
				<groupId>org.mybatis.generator</groupId>
				<artifactId>mybatis-generator-maven-plugin</artifactId>
				<version>1.3.2</version>
				<configuration>
					<!-- mybatis用于生成代码的配置文件 -->
					<configurationFile>src/main/resources/mybatis/generatorConfig.xml</configurationFile>
					<!-- 允许移动生成文件 -->
					<verbose>true</verbose>
					<!--允许覆盖生成的文件 -->
					<overwrite>true</overwrite>
				</configuration>
				<dependencies>
					<dependency>
						<groupId>com.janus</groupId>
						<artifactId>mybatis-generator-lombok-plugin</artifactId>
						<version>1.0</version>
						<systemPath>${project.basedir}/libs/mybatis-generator-lombok-plugin-1.0.jar</systemPath>
						<scope>system</scope>
					</dependency>
				</dependencies>
			</plugin>

```

同时添加配置文件`generatorConfig.xml`,使用的时候请根据项目需要自行修改对应配置

```xml

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>
	<classPathEntry
		location="E:\repo\mysql\mysql-connector-java\5.1.46\mysql-connector-java-5.1.46.jar" />
	<context id="genrator" defaultModelType="flat">
		
		<!-- 带上序列化接口 -->
		<plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
		<commentGenerator
			type="com.janus.mybatis.generator.plugins.MyCommentGenerator">
			<!-- **阻止**生成注释，默认为false -->
			<property name="suppressAllComments" value="false" />
			<!-- **阻止**生成的注释包含时间戳，默认为false -->
			<property name="suppressDate" value="true" />

			<!-- 生成文件的编码 (需要在eclipse根目录的eclipse.ini最后添加 -Dfile.encoding=UTF-8 -->
			<property name="javaFileEncoding" value="UTF-8" />
			<!-- 当表名或者字段名为SQL关键字的时候，可以设置该属性为true，MBG会自动给表名或字段名添加**分隔符** -->
			<property name="autoDelimitKeywords" value="true"></property>
			<!-- 由于beginningDelimiter和endingDelimiter的默认值为双引号(")，在Mysql中不能这么写，所以还要将这两个默认值改为**反单引号(`)** -->
			<property name="beginningDelimiter" value="`" />
			<property name="endingDelimiter" value="`" />
		</commentGenerator>

		
		<jdbcConnection driverClass="com.mysql.jdbc.Driver"
			connectionURL="jdbc:mysql://192.168.1.51:3306/ailierke"
			userId="ailierke" password="ab3762336cd" /><!-- 数据库连接信息 -->
		<!-- 生成model实体类文件位置 -->
		<javaModelGenerator
			targetPackage="com.diwinet.update.pojo"
			targetProject="E:\workspace_new\dw-spring-boot-demo\src\main\java">
			<property name="enableSubPackages" value="true" />
			<property name="trimStrings" value="true" />
		</javaModelGenerator><!-- 生成在哪个包下 比如：cn.kp.ticketapply.domain，这个会自动生成，不用提前建好 -->
		<!-- 生成的项目路径 比如：ticketapply-domain\src\main\java，这个是已经存在的路径，指明即可 -->
		<!-- 生成mapper.xml配置文件位置 -->
		<sqlMapGenerator targetPackage="mybatis\mapper"
			targetProject="E:\workspace_new\dw-spring-boot-demo/src/main/resources">
			<property name="enableSubPackages" value="true" />
		</sqlMapGenerator>
		<!-- 生成mapper接口文件位置 -->
		<javaClientGenerator
			targetPackage="com.diwinet.update.dao"
			targetProject="E:\workspace_new\dw-spring-boot-demo/src/main/java"
			type="XMLMAPPER">
			<property name="enableSubPackages" value="true" />
		</javaClientGenerator>
		<!-- 需要生成的实体类对应的表名，多个实体类复制多份该配置即可 -->
		<table tableName="t_user" domainObjectName="Tuser"
			enableCountByExample="false" enableUpdateByExample="false"
			enableDeleteByExample="false" enableSelectByExample="false"
			selectByExampleQueryId="false">

			<generatedKey column="id" sqlStatement="Mysql"
				identity="true" />
		</table>
		<!-- <table tableName="%"> <generatedKey column="id" sqlStatement="Mysql"/> 
			</table> -->
	</context>
</generatorConfiguration>

```

## Author
- janus  do or die

