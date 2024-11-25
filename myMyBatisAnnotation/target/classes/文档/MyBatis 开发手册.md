#                                                                                                                  MyBatis 开发手册

# 1、MyBatis是什么

MyBatis 是一个开源、轻量级的数据持久化框架，是 JDBC 和 Hibernate 的替代方案。MyBatis 内部封装了 JDBC，简化了加载驱动、创建连接、创建 statement 等繁杂的过程，开发者只需要关注 SQL 语句本身。

![Mybatis图标](http://c.biancheng.net/uploads/allimg/210708/1-210FQ43322D7.png)

> 数据持久化是将内存中的数据模型转换为存储模型，以及将存储模型转换为内存中数据模型的统称。例如，文件的存储、数据的读取以及对数据表的增删改查等都是数据持久化操作。

MyBatis 支持定制化 SQL、存储过程以及高级映射，<font color = Tomato size=3 face="楷书">可以在实体类和 SQL 语句之间建立映射关系</font>，是一种半自动化的 ORM 实现。其封装性低于 Hibernate，但性能优秀、小巧、简单易学、应用广泛。

> ORM（Object Relational Mapping，对象关系映射）是一种数据持久化技术，它在对象模型和关系型数据库之间建立起对应关系，并且提供了一种机制，通过 JavaBean 对象去操作数据库表中的数据。

MyBatis 前身为 IBatis，2002 年由 Clinton Begin 发布。2010 年从 Apache 迁移到 Google，并改名为 MyBatis，2013 年又迁移到了 Github。

MyBatis 的主要思想是将程序中的大量 SQL 语句剥离出来，使用 XML 文件或注解的方式实现 SQL 的灵活配置，将 SQL 语句与程序代码分离，在不修改程序代码的情况下，直接在配置文件中修改 SQL 语句。

MyBatis 与其它持久性框架最大的不同是，MyBatis 强调使用 SQL，而其它框架（例如 Hibernate）通常使用自定义查询语言，即 HQL（Hibernate查询语言）或 EJB QL（Enterprise JavaBeans查询语言）。

MyBatis 官方文档：https://mybatis.org/mybatis-3/zh/

## 优点

- MyBatis 是免费且开源的。
- 与 JDBC 相比，减少了 50% 以上的代码量。
- MyBatis 是最简单的持久化框架，小巧并且简单易学。
- MyBatis 相当灵活，不会对应用程序或者数据库的现有设计强加任何影响，SQL 写在 XML 中，和程序逻辑代码分离，降低耦合度，便于同一管理和优化，提高了代码的可重用性。
- 提供 XML 标签，支持编写动态 SQL 语句。
- 提供映射标签，支持对象与数据库的 ORM 字段关系映射。
- 支持存储过程。MyBatis 以存储过程的形式封装 SQL，可以将业务逻辑保留在数据库之外，增强应用程序的可移植性、更易于部署和测试。

## 缺点

- 编写 SQL 语句工作量较大，对开发人员编写 SQL 语句的功底有一定要求。
- SQL 语句依赖于数据库，导致数据库移植性差，不能随意更换数据库。

## 使用场景

MyBatis 专注于 SQL 本身，是一个足够灵活的 DAO 层解决方案。适用于性能要求高，且需求变化较多的项目，如互联网项目。

如果您想了解 MyBatis 与 Hibernate 的区别，可阅读学习《[MyBatis和Hibernate的区别](http://c.biancheng.net/mybatis/mybatis-hibernate.html)》一节。

## 拓展

Mybatis-Plus（简称 MP）是 Mybatis 的增强工具，在 Mybatis 的基础上只做增强不做改变，支持 Mybatis 所有原生的特性，为简化开发、提高效率而生。有兴趣的小伙伴可以参考 [MyBatis-Plus 官网](https://mp.baomidou.com/#/)。



# 2、MyBatis XML配置

## XML 映射配置文件

MyBatis 的配置文件包含了影响 MyBatis 行为甚深的设置（settings）和属性（properties）信息。

## 属性（properties）

这些属性都是可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来传递。例如：

```
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>
```

其中的属性就可以在整个配置文件中使用来替换需要动态配置的属性值。比如:

```
<dataSource type="POOLED">
  <property name="driver" value="${driver}"/>
  <property name="url" value="${url}"/>
  <property name="username" value="${username}"/>
  <property name="password" value="${password}"/>
</dataSource>
```

这个例子中的 username 和 password 将会由 properties 元素中设置的相应值来替换。 driver 和 url 属性将会由 config.properties 文件中对应的值来替换。这样就为配置提供了诸多灵活选择。

属性也可以被传递到 SqlSessionBuilder.build()方法中。例如：

```
    SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader, props);

    // ... or ...

    SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader, environment, props);
```

如果属性在不只一个地方进行了配置，那么 MyBatis 将按照下面的顺序来加载：

- 在 properties 元素体内指定的属性首先被读取。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件或根据 url 属性指定的路径读取属性文件，并覆盖已读取的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖已读取的同名属性。

因此，通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的是 properties 属性中指定的属性。

## 设置（settings）

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。下表描述了设置中各项的意图、默认值等。

| 设置参数                  | 描述                                                         | 有效值                                                       | 默认值                                                       |
| ------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| cacheEnabled              | 该配置影响的所有映射器中配置的缓存的全局开关。               | true,false                                                   | true                                                         |
| lazyLoadingEnabled        | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置`fetchType`属性来覆盖该项的开关状态。 | true,false                                                   | false                                                        |
| aggressiveLazyLoading     | 当启用时，对任意延迟属性的调用会使带有延迟加载属性的对象完整加载；反之，每种属性将会按需加载。 | true,false                                                   |                                                              |
| multipleResultSetsEnabled | 是否允许单一语句返回多结果集（需要兼容驱动）。               | true,false                                                   | true                                                         |
| useColumnLabel            | 使用列标签代替列名。不同的驱动在这方面会有不同的表现， 具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。 | true,false                                                   | true                                                         |
| useGeneratedKeys          | 允许 JDBC 支持自动生成主键，需要驱动兼容。 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（比如 Derby）。 | true,false                                                   | False                                                        |
| autoMappingBehavior       | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示取消自动映射；PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。 FULL 会自动映射任意复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                                          | PARTIAL                                                      |
| defaultExecutorType       | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。 | SIMPLE REUSE BATCH                                           | SIMPLE                                                       |
| defaultStatementTimeout   | 设置超时时间，它决定驱动等待数据库响应的秒数。               | Any positive integer                                         | Not Set (null)                                               |
| safeRowBoundsEnabled      | 允许在嵌套语句中使用分页（RowBounds）。                      | true,false                                                   | False                                                        |
| mapUnderscoreToCamelCase  | 是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。 | true, false                                                  | False                                                        |
| localCacheScope           | MyBatis 利用本地缓存机制（Local Cache）防止循环引用（circular references）和加速重复嵌套查询。 默认值为 SESSION，这种情况下会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地会话仅用在语句执行上，对相同 SqlSession 的不同调用将不会共享数据。 | SESSION,STATEMENT                                            | SESSION                                                      |
| jdbcTypeForNull           | 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。 某些驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType enumeration. Most common are: NULL, VARCHAR and OTHER | OTHER                                                        |
| lazyLoadTriggerMethods    | 指定哪个对象的方法触发一次延迟加载。                         | A method name list separated by commas                       | equals,clone,hashCode,toString                               |
| defaultScriptingLanguage  | 指定动态 SQL 生成的默认语言。                                | A type alias or fully qualified class name.                  | org.apache.ibatis.scripting.xmltags.XMLDynamicLanguageDriver |
| callSettersOnNulls        | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。注意基本类型（int、boolean等）是不能设置成 null 的。 | true,false                                                   | false                                                        |
| logPrefix                 | 指定 MyBatis 增加到日志名称的前缀。                          | Any String                                                   | Not set                                                      |
| logImpl                   | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J,LOG4J,LOG4J2,JDK_LOGGING,COMMONS_LOGGING,STDOUT_LOGGING,NO_LOGGING | Not set                                                      |
| proxyFactory              | 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。    | CGLIB JAVASSIST                                              | CGLIB                                                        |
| vfslmpl                   | 指定 VFS 的实现                                              | 自定义 VFS 的实现的类全限定名，以逗号分隔。                  | no set                                                       |
| useActualParamName        | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的项目必须采用 Java 8 编译，并且加上 `-parameters` 选项。（新增于 3.4.1） | true \| false                                                | true                                                         |
| configurationFactory      | 指定一个提供 `Configuration `实例的类。 这个被返回的 Configuration 实例用来加载被反序列化对象的延迟加载属性值。 这个类必须包含一个签名为`static Configuration getConfiguration() `的方法。（新增于 3.2.3） | 类型别名或者全类名.                                          | no set                                                       |

一个配置完整的 settings 元素的示例如下：

```
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

## 类型别名（typeAliases）

类型别名是为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。例如:

```
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

当这样配置时，`Blog`可以用在任何使用`domain.blog.Blog`的地方。

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如:

```
<typeAliases>
  <package name="domain.blog"/>
</typeAliases>
```

每一个在包 `domain.blog` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author`；若有注解，则别名为其注解值。看下面的例子：

```
    @Alias("author")
    public class Author {
        ...
    }
```

已经为许多常见的 Java 类型内建了相应的类型别名。它们都是大小写不敏感的，需要注意的是由基本类型名称重复导致的特殊处理。

| 别名       | 映射的类型 |
| ---------- | ---------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDecimal |
| object     | Object     |
| map        | Map        |
| hashmap    | HashMap    |
| list       | List       |
| arraylist  | ArrayList  |
| collection | Collection |
| iterator   | Iterator   |

## 类型处理器（typeHandlers）

无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 都会用类型处理器将获取的值以合适的方式转换成 Java 类型。下表描述了一些默认的类型处理器。

| 类型处理器                | Java 类型                      | JDBC 类型                                                    |
| ------------------------- | ------------------------------ | ------------------------------------------------------------ |
| `BooleanTypeHandler`      | `java.lang.Boolean`, `boolean` | 数据库兼容的 `BOOLEAN`                                       |
| `ByteTypeHandler`         | `java.lang.Byte`, `byte`       | 数据库兼容的 `NUMERIC` 或 `BYTE`                             |
| `ShortTypeHandler`        | `java.lang.Short`, `short`     | 数据库兼容的 `NUMERIC` 或 `SHORT INTEGER`                    |
| `IntegerTypeHandler`      | `java.lang.Integer`, `int`     | 数据库兼容的 `NUMERIC` 或 `INTEGER`                          |
| `LongTypeHandler`         | `java.lang.Long`, `long`       | 数据库兼容的 `NUMERIC` 或 `LONG INTEGER`                     |
| `FloatTypeHandler`        | `java.lang.Float`, `float`     | 数据库兼容的 `NUMERIC` 或 `FLOAT`                            |
| `DoubleTypeHandler`       | `java.lang.Double`, `double`   | 数据库兼容的 `NUMERIC` 或 `DOUBLE`                           |
| `BigDecimalTypeHandler`   | `java.math.BigDecimal`         | 数据库兼容的 `NUMERIC` 或 `DECIMAL`                          |
| `StringTypeHandler`       | `java.lang.String`             | `CHAR`, `VARCHAR`                                            |
| `ClobTypeHandler`         | `java.lang.String`             | `CLOB`, `LONGVARCHAR`                                        |
| `NStringTypeHandler`      | `java.lang.String`             | `NVARCHAR`, `NCHAR`                                          |
| `NClobTypeHandler`        | `java.lang.String`             | `NCLOB`                                                      |
| `ByteArrayTypeHandler`    | `byte[]`                       | 数据库兼容的字节流类型                                       |
| `BlobTypeHandler`         | `byte[]`                       | `BLOB`, `LONGVARBINARY`                                      |
| `DateTypeHandler`         | `java.util.Date`               | `TIMESTAMP`                                                  |
| `DateOnlyTypeHandler`     | `java.util.Date`               | `DATE`                                                       |
| `TimeOnlyTypeHandler`     | `java.util.Date`               | `TIME`                                                       |
| `SqlTimestampTypeHandler` | `java.sql.Timestamp`           | `TIMESTAMP`                                                  |
| `SqlDateTypeHandler`      | `java.sql.Date`                | `DATE`                                                       |
| `SqlTimeTypeHandler`      | `java.sql.Time`                | `TIME`                                                       |
| `ObjectTypeHandler`       | Any                            | `OTHER` 或未指定类型                                         |
| `EnumTypeHandler`         | Enumeration Type               | VARCHAR-任何兼容的字符串类型，存储枚举的名称（而不是索引）   |
| `EnumOrdinalTypeHandler`  | Enumeration Type               | 任何兼容的 `NUMERIC` 或 `DOUBLE` 类型，存储枚举的索引（而不是名称）。 |

你可以重写类型处理器或创建你自己的类型处理器来处理不支持的或非标准的类型。 具体做法为：实现 `org.apache.ibatis.type.TypeHandler` 接口， 或继承一个很便利的类 `org.apache.ibatis.type.BaseTypeHandler`， 然后可以选择性地将它映射到一个 JDBC 类型。比如：

```
    // ExampleTypeHandler.java
    @MappedJdbcTypes(JdbcType.VARCHAR)
    public class ExampleTypeHandler extends BaseTypeHandler {

      @Override
      public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
      }

      @Override
      public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
      }

      @Override
      public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
      }

      @Override
      public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
      }
    }
<!-- mybatis-config.xml -->
<typeHandlers>
  <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
</typeHandlers>
```

使用这个的类型处理器将会覆盖已经存在的处理 Java 的 String 类型属性和 VARCHAR 参数及结果的类型处理器。 要注意 MyBatis 不会窥探数据库元信息来决定使用哪种类型，所以你必须在参数和结果映射中指明那是 VARCHAR 类型的字段， 以使其能够绑定到正确的类型处理器上。 这是因为：MyBatis 直到语句被执行才清楚数据类型。

通过类型处理器的泛型，MyBatis 可以得知该类型处理器处理的 Java 类型，不过这种行为可以通过两种方法改变：

- 在类型处理器的配置元素（typeHandler element）上增加一个 `javaType` 属性（比如：`javaType="String"`）；
- 在类型处理器的类上（TypeHandler class）增加一个 `@MappedTypes` 注解来指定与其关联的 Java 类型列表。 如果在 `javaType` 属性中也同时指定，则注解方式将被忽略。

可以通过两种方式来指定被关联的 JDBC 类型：

- 在类型处理器的配置元素上增加一个 `javaType` 属性（比如：`javaType="VARCHAR"`）；
- 在类型处理器的类上（TypeHandler class）增加一个 `@MappedJdbcTypes` 注解来指定与其关联的 JDBC 类型列表。 如果在 `javaType` 属性中也同时指定，则注解方式将被忽略。

最后，可以让 MyBatis 为你查找类型处理器：

```
<!-- mybatis-config.xml -->
<typeHandlers>
  <package name="org.mybatis.example"/>
</typeHandlers>
```

注意在使用自动检索（autodiscovery）功能的时候，只能通过注解方式来指定 JDBC 的类型。

你能创建一个泛型类型处理器，它可以处理多于一个类。为达到此目的， 需要增加一个接收该类作为参数的构造器，这样在构造一个类型处理器的时候 MyBatis 就会传入一个具体的类。

```
    //GenericTypeHandler.java
    public class GenericTypeHandler extends BaseTypeHandler {

      private Class type;

      public GenericTypeHandler(Class type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
      }
      ...
```

`EnumTypeHandler` 和 `EnumOrdinalTypeHandler` 都是泛型类型处理器（generic TypeHandlers）， 我们将会在接下来的部分详细探讨。

## 处理枚举类型

若想映射枚举类型 `Enum`，则需要从 `EnumTypeHandler` 或者 `EnumOrdinalTypeHandler` 中选一个来使用。

比如说我们想存储取近似值时用到的舍入模式。默认情况下，MyBatis 会利用 `EnumTypeHandler` 来把 `Enum` 值转换成对应的名字。

**注意 `EnumTypeHandler` 在某种意义上来说是比较特别的，其他的处理器只针对某个特定的类，而它不同，它会处理任意继承了 `Enum` 的类。**

不过，我们可能不想存储名字，相反我们的 DBA 会坚持使用整形值代码。那也一样轻而易举： 在配置文件中把 `EnumOrdinalTypeHandler` 加到 `typeHandlers` 中即可， 这样每个 `RoundingMode` 将通过他们的序数值来映射成对应的整形。

```
<!-- mybatis-config.xml -->
<typeHandlers>
  <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
</typeHandlers>
```

但是怎样能将同样的 `Enum` 既映射成字符串又映射成整形呢？

自动映射器（auto-mapper）会自动地选用 `EnumOrdinalTypeHandler` 来处理， 所以如果我们想用普通的 `EnumTypeHandler`，就非要为那些 SQL 语句显式地设置要用到的类型处理器不可。

（下一节才开始讲映射器文件，所以如果是首次阅读该文档，你可能需要先越过这一步，过会再来看。）

```
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="org.apache.ibatis.submitted.rounding.Mapper">
    <resultMap type="org.apache.ibatis.submitted.rounding.domain.User" id="usermap">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="funkyNumber" property="funkyNumber"/>
        <result column="roundingMode" property="roundingMode"/>
    </resultMap>

    <select id="getUser" resultMap="usermap">
        select * from users
    </select>
    <insert id="insert">
        insert into users (id, name, funkyNumber, roundingMode) values (
            #{id}, #{name}, #{funkyNumber}, #{roundingMode}
        )
    </insert>

    <resultMap type="org.apache.ibatis.submitted.rounding.domain.User" id="usermap2">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="funkyNumber" property="funkyNumber"/>
        <result column="roundingMode" property="roundingMode" typeHandler="org.apache.ibatis.type.EnumTypeHandler"/>
    </resultMap>
    <select id="getUser2" resultMap="usermap2">
        select * from users2
    </select>
    <insert id="insert2">
        insert into users2 (id, name, funkyNumber, roundingMode) values (
            #{id}, #{name}, #{funkyNumber}, #{roundingMode, typeHandler=org.apache.ibatis.type.EnumTypeHandler}
        )
    </insert>

</mapper>
```

注意，这里的 select 语句强制使用 `resultMap` 来代替 `resultType`。

## 对象工厂（objectFactory）

MyBatis 每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。 如果想覆盖对象工厂的默认行为，则可以通过创建自己的对象工厂来实现。比如：

```
    // ExampleObjectFactory.java
    public class ExampleObjectFactory extends DefaultObjectFactory {
      public Object create(Class type) {
        return super.create(type);
      }
      public Object create(Class type, List constructorArgTypes, List constructorArgs) {
        return super.create(type, constructorArgTypes, constructorArgs);
      }
      public void setProperties(Properties properties) {
        super.setProperties(properties);
      }
      public  boolean isCollection(Class type) {
        return Collection.class.isAssignableFrom(type);
      }}
<!-- mybatis-config.xml -->
<objectFactory type="org.mybatis.example.ExampleObjectFactory">
  <property name="someProperty" value="100"/>
</objectFactory>
```

ObjectFactory 接口很简单，它包含两个创建用的方法，一个是处理默认构造方法的，另外一个是处理带参数的构造方法的。 最后，setProperties 方法可以被用来配置 ObjectFactory，在初始化你的 ObjectFactory 实例后， objectFactory 元素体中定义的属性会被传递给 setProperties 方法。

## 插件（plugins）

MyBatis 允许你在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

这些类中方法的细节可以通过查看每个方法的签名来发现，或者直接查看 MyBatis 的发行包中的源代码。 假设你想做的不仅仅是监控方法的调用，那么你应该很好的了解正在重写的方法的行为。 因为如果在试图修改或重写已有方法的行为的时候，你很可能在破坏 MyBatis 的核心模块。 这些都是更低层的类和方法，所以使用插件的时候要特别当心。

通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定了想要拦截的方法签名即可。

```
    // ExamplePlugin.java
    @Intercepts({@Signature(
      type= Executor.class,
      method = "update",
      args = {MappedStatement.class,Object.class})})
    public class ExamplePlugin implements Interceptor {
      public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
      }
      public Object plugin(Object target) {
        return Plugin.wrap(target, this);
      }
      public void setProperties(Properties properties) {
      }
    }
<!-- mybatis-config.xml -->
<plugins>
  <plugin interceptor="org.mybatis.example.ExamplePlugin">
    <property name="someProperty" value="100"/>
  </plugin>
</plugins> 
```

上面的插件将会拦截在 Executor 实例中所有的 "update" 方法调用， 这里的 Executor 是负责执行低层映射语句的内部对象。

NOTE **覆盖配置类**

除了用插件来修改 MyBatis 核心行为之外，还可以通过完全覆盖配置类来达到目的。只需继承后覆盖其中的每个方法，再把它传递到 sqlSessionFactoryBuilder.build(myConfig) 方法即可。再次重申，这可能会严重影响 MyBatis 的行为，务请慎之又慎。

## 配置环境（environments）

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中， 现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置；或者共享相同 Schema 的多个生产数据库， 想使用相同的 SQL 映射。许多类似的用例。

**不过要记住：尽管可以配置多个环境，每个 SqlSessionFactory 实例只能选择其一。**

所以，如果你想连接两个数据库，就需要创建两个 SqlSessionFactory 实例，每个数据库对应一个。而如果是三个数据库，就需要三个实例，依此类推，记起来很简单：

- **每个数据库对应一个 SqlSessionFactory 实例**

为了指定创建哪种环境，只要将它作为可选的参数传递给 SqlSessionFactoryBuilder 即可。可以接受环境配置的两个方法签名是：

```
    SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader, environment);
    SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader, environment,properties);
```

如果忽略了环境参数，那么默认环境将会被加载，如下所示：

```
    SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader);
    SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader,properties);
```

环境元素定义了如何配置环境。

```
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

注意这里的关键点:

- 默认的环境 ID（比如:default="development"）。
- 每个 environment 元素定义的环境 ID（比如:id="development"）。
- 事务管理器的配置（比如:type="JDBC"）。
- 数据源的配置（比如:type="POOLED"）。

默认的环境和环境 ID 是一目了然的。随你怎么命名，只要保证默认环境要匹配其中一个环境ID。

**事务管理器（transactionManager）**

在 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）：

- JDBC – 这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务范围。
- MANAGED – 这个配置几乎没做什么。它从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接，然而一些容器并不希望这样，因此需要将 closeConnection 属性设置为 false 来阻止它默认的关闭行为。例如:

```
<transactionManager type="MANAGED">
  <property name="closeConnection" value="false"/>
</transactionManager>
```

NOTE如果你正在使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。

这两种事务管理器类型都不需要任何属性。它们不过是类型别名，换句话说，你可以使用 TransactionFactory 接口的实现类的完全限定名或类型别名代替它们。

```
    public interface TransactionFactory {
      void setProperties(Properties props);
      Transaction newTransaction(Connection conn);
      Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
    }
```

任何在 XML 中配置的属性在实例化之后将会被传递给 setProperties() 方法。你也需要创建一个 Transaction 接口的实现类，这个接口也很简单：

```
    public interface Transaction {
      Connection getConnection() throws SQLException;
      void commit() throws SQLException;
      void rollback() throws SQLException;
      void close() throws SQLException;
    }
```

使用这两个接口，你可以完全自定义 MyBatis 对事务的处理。

**数据源（dataSource）**

dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

- 许多 MyBatis 的应用程序将会按示例中的例子来配置数据源。然而它并不是必须的。要知道为了方便使用延迟加载，数据源才是必须的。

有三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）：

**UNPOOLED**– 这个数据源的实现只是每次被请求时打开和关闭连接。虽然一点慢，它对在及时可用连接方面没有性能要求的简单应用程序是一个很好的选择。 不同的数据库在这方面表现也是不一样的，所以对某些数据库来说使用连接池并不重要，这个配置也是理想的。UNPOOLED 类型的数据源仅仅需要配置以下 5 种属性：

- `driver` – 这是 JDBC 驱动的 Java 类的完全限定名（并不是JDBC驱动中可能包含的数据源类）。
- `url` – 这是数据库的 JDBC URL 地址。
- `username` – 登录数据库的用户名。
- `password` – 登录数据库的密码。
- `defaultTransactionIsolationLevel` – 默认的连接事务隔离级别。

作为可选项，你也可以传递属性给数据库驱动。要这样做，属性的前缀为"driver."，例如：

- `driver.encoding=UTF8`

这将通过DriverManager.getConnection(url,driverProperties)方法传递值为 `UTF8` 的 `encoding` 属性给数据库驱动。

**POOLED**– 这种数据源的实现利用"池"的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这是一种使得并发 Web 应用快速响应请求的流行处理方式。

除了上述提到 UNPOOLED 下的属性外，会有更多属性用来配置 POOLED 的数据源：

- `poolMaximumActiveConnections` – 在任意时间可以存在的活动（也就是正在使用）连接数量，默认值：10
- `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
- `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
- `poolTimeToWait` – 这是一个底层设置，如果获取连接花费的相当长的时间，它会给连接池打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直安静的失败），默认值：20000 毫秒（即 20 秒）。
- `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否处在正常工作秩序中并准备接受请求。默认是"NO PING QUERY SET"，这会导致多数数据库驱动失败时带有一个恰当的错误消息。
- `poolPingEnabled` – 是否启用侦测查询。若开启，也必须使用一个可执行的 SQL 语句设置 `poolPingQuery` 属性（最好是一个非常快的 SQL），默认值：false。
- `poolPingConnectionsNotUsedFor` – 配置 poolPingQuery 的使用频度。这可以被设置成匹配具体的数据库连接超时时间，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

**JNDI**– 这个数据源的实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用。这种数据源配置只需要两个属性：

- `initial_context` – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么 data_source 属性将会直接从 InitialContext 中寻找。
- `data_source` – 这是引用数据源实例位置的上下文的路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。

和其他数据源配置类似，可以通过添加前缀"env."直接把属性传递给初始上下文。比如：

- `env.encoding=UTF8`

这就会在初始上下文（InitialContext）实例化时往它的构造方法传递值为 `UTF8` 的 `encoding` 属性。

通过需要实现接口 `org.apache.ibatis.datasource.DataSourceFactory` ， 也可使用任何第三方数据源，：

```
    public interface DataSourceFactory {
      void setProperties(Properties props);
      DataSource getDataSource();
    }
```

`org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory` 可被用作父类来构建新的数据源适配器，比如下面这段插入 C3P0 数据源所必需的代码：

```
    import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
    import com.mchange.v2.c3p0.ComboPooledDataSource;

    public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

      public C3P0DataSourceFactory() {
        this.dataSource = new ComboPooledDataSource();
      }
    }
```

为了令其工作，为每个需要 MyBatis 调用的 setter 方法中增加一个属性。下面是一个可以连接至 PostgreSQL 数据库的例子：

```
<dataSource type="org.myproject.C3P0DataSourceFactory">
  <property name="driver" value="org.postgresql.Driver"/>
  <property name="url" value="jdbc:postgresql:mydb"/>
  <property name="username" value="postgres"/>
  <property name="password" value="root"/>
</dataSource>
```

## databaseIdProvider

MyBatis 可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的 `databaseId` 属性。 MyBatis 会加载不带 `databaseId` 属性和带有匹配当前数据库 `databaseId` 属性的所有语句。 如果同时找到带有 `databaseId` 和不带 `databaseId` 的相同语句，则后者会被舍弃。 为支持多厂商特性只要像下面这样在 mybatis-config.xml 文件中加入 `databaseIdProvider` 即可：

```
<databaseIdProvider type="DB_VENDOR" />
```

这里的 DB_VENDOR 会通过 `DatabaseMetaData#getDatabaseProductName()` 返回的字符串进行设置。 由于通常情况下这个字符串都非常长而且相同产品的不同版本会返回不同的值，所以最好通过设置属性别名来使其变短，如下：

```
<databaseIdProvider type="DB_VENDOR">
  <property name="SQL Server" value="sqlserver"/>
  <property name="DB2" value="db2"/>        
  <property name="Oracle" value="oracle" />
</databaseIdProvider>
```

在有 properties 时，DB_VENDOR databaseIdProvider 的将被设置为第一个能匹配数据库产品名称的属性键对应的值，如果没有匹配的属性将会设置为 "null"。 在这个例子中，如果 `getDatabaseProductName()` 返回"Oracle (DataDirect)"，databaseId 将被设置为"oracle"。

你可以通过实现接口 `org.apache.ibatis.mapping.DatabaseIdProvider` 并在 mybatis-config.xml 中注册来构建自己的 DatabaseIdProvider：

```
    public interface DatabaseIdProvider {
      void setProperties(Properties p);
      String getDatabaseId(DataSource dataSource) throws SQLException;
    }
```

## 映射器（mappers）

既然 MyBatis 的行为已经由上述元素配置完了，我们现在就要定义 SQL 映射语句了。但是首先我们需要告诉 MyBatis 到哪里去找到这些语句。 Java 在自动查找这方面没有提供一个很好的方法，所以最佳的方式是告诉 MyBatis 到哪里去找映射文件。你可以使用相对于类路径的资源引用， 或完全限定资源定位符（包括 `file:///` 的 URL），或类名和包名等。例如：

```
<!-- Using classpath relative resources -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- Using url fully qualified paths -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- Using mapper interface classes -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- Register all interfaces in a package as mappers -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

这些配置会告诉了 MyBatis 去哪里找映射文件，剩下的细节就应该是每个 SQL 映射文件了，也就是接下来我们要讨论的。



# 3、MyBatis XML映射文件

## Mapper XML 文件

MyBatis 的真正强大在于它的映射语句，也是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 就是针对 SQL 构建的，并且比普通的方法做的更好。

SQL 映射文件有很少的几个顶级元素（按照它们应该被定义的顺序）：

- `cache` – 给定命名空间的缓存配置。
- `cache-ref` – 其他命名空间缓存配置的引用。
- `resultMap` – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
- `sql` – 可被其他语句引用的可重用语句块。
- `insert` – 映射插入语句
- `update` – 映射更新语句
- `delete` – 映射删除语句
- `select` – 映射查询语句

下一部分将从语句本身开始来描述每个元素的细节。

### select

查询语句是 MyBatis 中最常用的元素之一，光能把数据存到数据库中价值并不大，如果还能重新取出来才有用，多数应用也都是查询比修改要频繁。对每个插入、更新或删除操作，通常对应多个查询操作。这是 MyBatis 的基本原则之一，也是将焦点和努力放到查询和结果映射的原因。简单查询的 select 元素是非常简单的。比如：

```
<select id="selectPerson" parameterType="int" resultType="hashmap">
  SELECT * FROM PERSON WHERE ID = #{id}
</select>    
```

***这个语句被称作 selectPerson，接受一个 int（或 Integer）类型的参数，并返回一个 HashMap 类型的对象，其中的键是列名，值便是结果行中的对应值。***

<font color = Tomato size=4 face="楷书">注意参数符号：</font>

```
#{id}
```

***这就告诉 MyBatis 创建一个预处理语句参数，通过 JDBC，这样的一个参数在 SQL 中会由一个 "?" 来标识，并被传递到一个新的预处理语句中，就像这样：***

```
    // Similar JDBC code, NOT MyBatis…
    String selectPerson = "SELECT * FROM PERSON WHERE ID=?";
    PreparedStatement ps = conn.prepareStatement(selectPerson);
    ps.setInt(1,id);
```

当然，这需要很多单独的 JDBC 的代码来提取结果并将它们映射到对象实例中，这就是 MyBatis 节省你时间的地方。我们需要深入了解参数和结果映射，细节部分我们下面来了解。

<font color = Tomato size=3 face="楷书">select 元素有很多属性允许你配置，来决定每条语句的作用细节。</font>

```
 <select
  id="selectPerson"
  parameterType="int"
  parameterMap="deprecated"
  resultType="hashmap"
  resultMap="personResultMap"
  flushCache="false"
  useCache="true"
  timeout="10000"
  fetchSize="256"
  statementType="PREPARED"
  resultSetType="FORWARD_ONLY">    
```

Select Attributes

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| id            | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| parameterType | 将会传入这条语句的参数类的完全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过 TypeHandler 推断出具体传入语句的参数，默认值为 unset。 |
| resultType    | 从这条语句中返回的期望类型的类的完全限定名或别名。注意如果是集合情形，那应该是集合可以包含的类型，而不能是集合本身。使用 resultType 或 resultMap，但不能同时使用。 |
| resultMap     | 外部 resultMap 的命名引用。结果集的映射是 MyBatis 最强大的特性，对其有一个很好的理解的话，许多复杂映射的情形都能迎刃而解。使用 resultMap 或 resultType，但不能同时使用。 |
| flushCache    | 将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：false。 |
| useCache      | 将其设置为 true，将会导致本条语句的结果被二级缓存，默认值：对 select 元素为 true。 |
| timeout       | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为 unset（依赖驱动）。 |
| fetchSize     | 这是尝试影响驱动程序每次批量返回的结果行数和这个设置值相等。默认值为 unset（依赖驱动）。 |
| statementType | STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| resultSetType | FORWARD_ONLY，SCROLL_SENSITIVE 或 SCROLL_INSENSITIVE 中的一个，默认值为 unset （依赖驱动）。 |
| databaseId    | 如果配置了 databaseIdProvider，MyBatis 会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。 |
| resultOrdered | 这个设置仅针对嵌套结果 select 语句适用：如果为 true，就是假设包含了嵌套结果集或是分组了，这样的话当返回一个主结果行的时候，就不会发生有对前面结果集的引用的情况。这就使得在获取嵌套的结果集的时候不至于导致内存不够用。默认值：false。 |
| resultSets    | 这个设置仅对多结果集的情况适用，它将列出语句执行后返回的结果集并每个结果集给一个名称，名称是逗号分隔的。 |

### insert, update 和 delete

数据变更语句 insert，update 和 delete 的实现非常接近：

```
<insert
  id="insertAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  keyProperty=""
  keyColumn=""
  useGeneratedKeys=""
  timeout="20">

<update
  id="updateAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">

<delete
  id="deleteAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">
```

Insert, Update 和 Delete 的属性

| id               | 命名空间中的唯一标识符，可被用来代表这条语句。               |
| ---------------- | ------------------------------------------------------------ |
| parameterType    | 将要传入语句的参数的完全限定类名或别名。这个属性是可选的，因为 MyBatis 可以通过 TypeHandler 推断出具体传入语句的参数，默认值为 unset。 |
| 属性             | 描述                                                         |
| flushCache       | 将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：true（对应插入、更新和删除语句）。 |
| timeout          | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为 unset（依赖驱动）。 |
| statementType    | STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| useGeneratedKeys | （仅对 insert 和 update 有用）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段），默认值：false。 |
| keyProperty      | （仅对 insert 和 update 有用）唯一标记一个属性，MyBatis 会通过 getGeneratedKeys 的返回值或者通过 insert 语句的 selectKey 子元素设置它的键值，默认：unset。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| keyColumn        | （仅对 insert 和 update 有用）通过生成的键值设置表中的列名，这个设置仅在某些数据库（像 PostgreSQL）是必须的，当主键列不是表中的第一列的时候需要设置。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| databaseId       | 如果配置了 databaseIdProvider，MyBatis 会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。 |

下面就是 insert，update 和 delete 语句的示例：

```
<insert id="insertAuthor">
  insert into Author (id,username,password,email,bio)
  values (#{id},#{username},#{password},#{email},#{bio})
</insert>

<update id="updateAuthor">
  update Author set
    username = #{username},
    password = #{password},
    email = #{email},
    bio = #{bio}
  where id = #{id}
</update>

<delete id="deleteAuthor">
  delete from Author where id = #{id}
</delete>
```

如前所述，插入语句的配置规则更加丰富，在插入语句里面有一些额外的属性和子元素用来处理主键的生成，而且有多种生成方式。

首先，如果你的数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），那么你可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置到目标属性上就OK了。例如，如果上面的 Author 表已经对 id 使用了自动生成的列类型，那么语句可以修改为:

```
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username,password,email,bio)
  values (#{username},#{password},#{email},#{bio})
</insert>
```

如果你的数据库还支持多行插入, 你也可以传入一个Authors数组或集合，并返回自动生成的主键。

```
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username, password, email, bio) values
  <foreach item="item" collection="list" separator=",">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </foreach>
</insert>
```

在上面的示例中，selectKey 元素将会首先运行，Author 的 id 会被设置，然后插入语句会被调用。这给你了一个和数据库中来处理自动生成的主键类似的行为，避免了使 Java 代码变得复杂。

selectKey 元素描述如下：

```
<selectKey
  keyProperty="id"
  resultType="int"
  order="BEFORE"
  statementType="PREPARED">
```

selectKey 的属性

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| keyProperty   | selectKey 语句结果应该被设置的目标属性。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| keyColumn     | 匹配属性的返回结果集中的列名称。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| resultType    | 结果的类型。MyBatis 通常可以推算出来，但是为了更加确定写上也不会有什么问题。MyBatis 允许任何简单类型用作主键的类型，包括字符串。如果希望作用于多个生成的列，则可以使用一个包含期望属性的 Object 或一个 Map。 |
| order         | 这可以被设置为 BEFORE 或 AFTER。如果设置为 BEFORE，那么它会首先选择主键，设置 keyProperty 然后执行插入语句。如果设置为 AFTER，那么先执行插入语句，然后是 selectKey 元素 - 这和像 Oracle 的数据库相似，在插入语句内部可能有嵌入索引调用。 |
| statementType | 与前面相同，MyBatis 支持 STATEMENT，PREPARED 和 CALLABLE 语句的映射类型，分别代表 PreparedStatement 和 CallableStatement 类型。 |

### sql

这个元素可以被用来定义可重用的 SQL 代码段，可以包含在其他语句中。它可以静态地(在加载阶段)参数化。不同的属性值可以在include实例中有所不同。 比如：

```
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```

这个 SQL 片段可以被包含在其他语句中，例如：

```
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```

属性值也可以用于 include refid 属性或 include 子句中的属性值，例如:

```
<sql id="sometable">
  ${prefix}Table
</sql>

<sql id="someinclude">
  from
    <include refid="${include_target}"/>
</sql>

<select id="select" resultType="map">
  select
    field1, field2, field3
  <include refid="someinclude">
    <property name="prefix" value="Some"/>
    <property name="include_target" value="sometable"/>
  </include>
</select>
```

### 参数（Parameters）

前面的所有语句中你所见到的都是简单参数的例子，实际上参数是 MyBatis 非常强大的元素，对于简单的做法，大概 90% 的情况参数都很少，比如：

```
<select id="selectUsers" resultType="domain.User">
  select id, username, password
  from users
  where id = #{id}
</select>
```

上面的这个示例说明了一个非常简单的命名参数映射。参数类型被设置为 domain.User，这样这个参数就可以被设置成任何内容。原生的类型或简单数据类型（比如整型和字符串）因为没有相关属性，它会完全用参数值来替代。然而，如果传入一个复杂的对象，行为就会有一点不同了。比如：

```
<insert id="insertUser" parameterType="domain.User">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</insert>
```

如果 domain.User 类型的参数对象传递到了语句中，id、username 和 password 属性将会被查找，然后将它们的值传入预处理语句的参数中。

这点对于向语句中传参是比较好的而且又简单，不过参数映射的功能远不止于此。

首先，像 MyBatis 的其他部分一样，参数也可以指定一个特殊的数据类型。

```
    #{property,javaType=int,jdbcType=NUMERIC}
```

像 MyBatis 的剩余部分一样，javaType 通常可以从参数对象中来去确定，前提是只要对象不是一个 HashMap。那么 javaType 应该被确定来保证使用正确类型处理器。

<font color = Tomato size=3 face="楷书">NOTE 如果 null 被当作值来传递，对于所有可能为空的列，JDBC Type 是需要的。你可以自己通过阅读预处理语句的 setNull() 方法的 JavaDocs 文档来研究这种情况。</font>

为了以后定制类型处理方式，你也可以指定一个特殊的类型处理器类（或别名），比如：

```
#{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
```

尽管看起来配置变得越来越繁琐，但实际上是很少去设置它们。

对于数值类型，还有一个小数保留位数的设置，来确定小数点后保留的位数。

```
#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}
```

最后，mode 属性允许你指定 IN，OUT 或 INOUT 参数。如果参数为 OUT 或 INOUT，参数对象属性的真实值将会被改变，就像你在获取输出参数时所期望的那样。如果 mode 为 OUT（或 INOUT），而且 jdbcType 为 CURSOR(也就是 Oracle 的 REFCURSOR)，你必须指定一个 resultMap 来映射结果集到参数类型。要注意这里的 javaType 属性是可选的，如果左边的空白是 jdbcType 的 CURSOR 类型，它会自动地被设置为结果集。

```
#{department, mode=OUT, jdbcType=CURSOR, javaType=ResultSet, resultMap=departmentResultMap}
```

MyBatis 也支持很多高级的数据类型，比如结构体，但是当注册 out 参数时你必须告诉它语句类型名称。比如（<font color = Tomato size=3 face="楷书">再次提示，在实际中要像这样不能换行</font>）：

```
#{middleInitial, mode=OUT, jdbcType=STRUCT, jdbcTypeName=MY_TYPE, resultMap=departmentResultMap}
```

尽管所有这些强大的选项很多时候你只简单指定属性名，其他的事情 MyBatis 会自己去推断，最多你需要为可能为空的列名指定 `jdbcType`。

```
#{firstName}
#{middleInitial,jdbcType=VARCHAR}
#{lastName}
```

#### 字符串替换

默认情况下,使用#{}格式的语法会导致 MyBatis 创建预处理语句属性并安全地设置值（比如?）。这样做更安全，更迅速，通常也是首选做法，不过有时你只是想直接在 SQL 语句中插入一个不改变的字符串。比如，像 ORDER BY，你可以这样来使用：

```
ORDER BY ${columnName}
```

这里 MyBatis 不会修改或转义字符串。<font color = Tomato size= 3 face="楷书">NOTE 以这种方式接受从用户输出的内容并提供给语句中不变的字符串是不安全的，会导致潜在的 SQL 注入攻击，因此要么不允许用户输入这些字段，要么自行转义并检验。</font>

### Result Maps

resultMap 元素是 MyBatis 中最重要最强大的元素。它就是让你远离 90%的需要从结果 集中取出数据的 JDBC 代码的那个东西, 而且在一些情形下允许你做一些 JDBC 不支持的事 情。 事实上, 编写相似于对复杂语句联合映射这些等同的代码, 也许可以跨过上千行的代码。 ResultMap 的设计就是简单语句不需要明确的结果映射,而很多复杂语句确实需要描述它们 的关系。

你已经看到简单映射语句的示例了,但没有明确的 resultMap。比如:

```
<select id="selectUsers" resultType="map">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

这样一个语句简单作用于所有列被自动映射到 HashMap 的键上,这由 `resultType` 属性 指定。这在很多情况下是有用的,但是 HashMap 不能很好描述一个领域模型。那样你的应 用程序将会使用 JavaBeans 或 POJOs(Plain Old Java Objects,普通 Java 对象)来作为领域 模型。MyBatis 对两者都支持。看看下面这个 JavaBean:

```
    package com.someapp.model;
    public class domain.User {
      private int id;
      private String username;
      private String hashedPassword;

      public int getId() {
        return id;
      }
      public void setId(int id) {
        this.id = id;
      }
      public String getUsername() {
        return username;
      }
      public void setUsername(String username) {
        this.username = username;
      }
      public String getHashedPassword() {
        return hashedPassword;
      }
      public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
      }
    }
```

基于 JavaBean 的规范,上面这个类有 3 个属性:`id`,`username `和` hashedPassword`。这些 在 select 语句中会精确匹配到列名。

这样的一个 JavaBean 可以被映射到结果集,就像映射到 HashMap 一样简单。

```
<select id="selectUsers" resultType="com.someapp.model.domain.User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

要记住类型别名是你的伙伴。使用它们你可以不用输入类的全路径。比如:

```
<!-- In mybatis-config.xml file -->
<typeAlias type="com.someapp.model.domain.User" alias="domain.User"/>

<!-- In SQL Mapping XML file -->
<select id="selectUsers" resultType="domain.User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

这些情况下,MyBatis 会在幕后自动创建一个 ResultMap,基于属性名来映射列到 JavaBean 的属性上。如果列名没有精确匹配,你可以在列名上使用 select 字句的别名(一个 基本的 SQL 特性)来匹配标签。比如:

```
<select id="selectUsers" resultType="domain.User">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password     as "hashedPassword"
  from some_table
  where id = #{id}
</select>
```

ResultMap 最优秀的地方你已经了解了很多了,但是你还没有真正的看到一个。这些简 单的示例不需要比你看到的更多东西。 只是出于示例的原因, 让我们来看看最后一个示例中 外部的 resultMap 是什么样子的,这也是解决列名不匹配的另外一种方式。

```
<resultMap id="userResultMap" type="domain.User">
  <id property="id" column="user_id" />
  <result property="username" column="username"/>
  <result property="password" column="password"/>
</resultMap>    
```

引用它的语句使用 resultMap 属性就行了(注意我们去掉了 resultType 属性)。比如:

```
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

如果世界总是这么简单就好了。

#### 高级结果映射

MyBatis 创建的一个想法:数据库不用永远是你想要的或需要它们是什么样的。而我们 最喜欢的数据库最好是第三范式或 BCNF 模式,但它们有时不是。如果可能有一个单独的 数据库映射,所有应用程序都可以使用它,这是非常好的,但有时也不是。结果映射就是 MyBatis 提供处理这个问题的答案。

比如,我们如何映射下面这个语句?

```
<!-- Very Complex Statement -->
<select id="selectBlogDetails" resultMap="detailedBlogResultMap">
  select
       B.id as blog_id,
       B.title as blog_title,
       B.author_id as blog_author_id,
       A.id as author_id,
       A.username as author_username,
       A.password as author_password,
       A.email as author_email,
       A.bio as author_bio,
       A.favourite_section as author_favourite_section,
       P.id as post_id,
       P.blog_id as post_blog_id,
       P.author_id as post_author_id,
       P.created_on as post_created_on,
       P.section as post_section,
       P.subject as post_subject,
       P.draft as draft,
       P.body as post_body,
       C.id as comment_id,
       C.post_id as comment_post_id,
       C.name as comment_name,
       C.comment as comment_text,
       T.id as tag_id,
       T.name as tag_name
  from Blog B
       left outer join Author A on B.author_id = A.id
       left outer join Post P on B.id = P.blog_id
       left outer join Comment C on P.id = C.post_id
       left outer join Post_Tag PT on PT.post_id = P.id
       left outer join Tag T on PT.tag_id = T.id
  where B.id = #{id}
</select>
```

你可能想把它映射到一个智能的对象模型,包含一个作者写的博客,有很多的博文,每 篇博文有零条或多条的评论和标签。 下面是一个完整的复杂结果映射例子 (假设作者, 博客, 博文, 评论和标签都是类型的别名) 我们来看看, 。 但是不用紧张, 我们会一步一步来说明。 当天最初它看起来令人生畏,但实际上非常简单。

```
<!-- Very Complex Result Map -->
<resultMap id="detailedBlogResultMap" type="Blog">
  <constructor>
    <idArg column="blog_id" javaType="int"/>
  </constructor>
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
      <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
      <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
      <case value="1" resultType="DraftPost"/>
    </discriminator>
  </collection>
</resultMap>
```

resultMap 元素有很多子元素和一个值得讨论的结构。 下面是 resultMap 元素的概念视图

#### resultMap

- ```
  constructor
  ```

   

  \- 类在实例化时,用来注入结果到构造方法中

  - `idArg` - ID 参数;标记结果作为 ID 可以帮助提高整体效能
  - `arg` - 注入到构造方法的一个普通结果

- `id` – 一个 ID 结果;标记结果作为 ID 可以帮助提高整体效能

- `result` – 注入到字段或 JavaBean 属性的普通结果

- ```
  association
  ```

   

  – 一个复杂的类型关联;许多结果将包成这种类型

  - 嵌入结果映射 – 结果映射自身的关联,或者参考一个

- ```
  collection
  ```

   

  – 复杂类型的集

  - 嵌入结果映射 – 结果映射自身的集,或者参考一个

- ```
  discriminator
  ```

   

  – 使用结果值来决定使用哪个结果映射

  - ```
    case
    ```

     

    – 基于某些值的结果映射

    - 嵌入结果映射 – 这种情形结果也映射它本身,因此可以包含很多相 同的元素,或者它可以参照一个外部的结果映射。

ResultMap Attributes

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `id`          | 此名称空间中的唯一标识符，可用于引用此结果映射。             |
| `type`        | 一个完全特定的 Java 类名，或者一个类型别名(参见上表中的内置类型别名列表)。 |
| `autoMapping` | 如果存在，MyBatis 将启用或禁用这个 ResultMap 的自动操作。此属性覆盖全局`autoMappingBehavior`。默认值:未设置的。 |

最佳实践 通常逐步建立结果映射。单元测试的真正帮助在这里。如果你尝试创建 一次创建一个向上面示例那样的巨大的结果映射, 那么可能会有错误而且很难去控制它 来工作。开始简单一些,一步一步的发展。而且要进行单元测试!使用该框架的缺点是 它们有时是黑盒(是否可见源代码) 。你确定你实现想要的行为的最好选择是编写单元 测试。它也可以你帮助得到提交时的错误。

下面一部分将详细说明每个元素。

#### id & result

```
<id property="id" column="post_id"/>
<result property="subject" column="post_subject"/>
```

这些是结果映射最基本内容。id 和 result 都映射一个单独列的值到简单数据类型(字符 串,整型,双精度浮点数,日期等)的单独属性或字段。

这两者之间的唯一不同是 id 表示的结果将是当比较对象实例时用到的标识属性。这帮 助来改进整体表现,特别是缓存和嵌入结果映射(也就是联合映射) 。

每个都有一些属性:

```
Id and Result Attributes
```

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `property`    | 映射到列结果的字段或属性。如果匹配的是存在的,和给定名称相同 的 JavaBeans 的属性,那么就会使用。否则 MyBatis 将会寻找给定名称 property 的字段。这两种情形你可以使用通常点式的复杂属性导航。比如,你 可以这样映射一些东西: `"username" `,或者映射到一些复杂的东西: `"address.street.number"` 。 |
| `column`      | 从数据库中得到的列名,或者是列名的重命名标签。这也是通常和会 传递给 `resultSet.getString(columnName)`方法参数中相同的字符串。 |
| `javaType`    | 一个 Java 类的完全限定名,或一个类型别名(参考上面内建类型别名 的列表) 。如果你映射到一个 JavaBean,MyBatis 通常可以断定类型。 然而,如果你映射到的是 HashMap,那么你应该明确地指定 javaType 来保证所需的行为。 |
| `jdbcType`    | 在这个表格之后的所支持的 JDBC 类型列表中的类型。JDBC 类型是仅 仅需要对插入,更新和删除操作可能为空的列进行处理。这是 JDBC jdbcType 的需要,而不是 MyBatis 的。如果你直接使用 JDBC 编程,你需要指定 这个类型-但仅仅对可能为空的值。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性,你可以覆盖默 认的类型处理器。这个属性值是类的完全限定名或者是一个类型处理 器的实现,或者是类型别名。 |

#### 支持的 JDBC 类型

为了未来的参考,MyBatis 通过包含的 jdbcType 枚举型,支持下面的 JDBC 类型。

| `BIT`      | `FLOAT`   | `CHAR`        | `TIMESTAMP`     | `OTHER`   | `UNDEFINED` |
| ---------- | --------- | ------------- | --------------- | --------- | ----------- |
| `TINYINT`  | `REAL`    | `VARCHAR`     | `BINARY`        | `BLOG`    | `NVARCHAR`  |
| `SMALLINT` | `DOUBLE`  | `LONGVARCHAR` | `VARBINARY`     | `CLOB`    | `NCHAR`     |
| `INTEGER`  | `NUMERIC` | `DATE`        | `LONGVARBINARY` | `BOOLEAN` | `NCLOB`     |
| `BIGINT`   | `DECIMAL` | `TIME`        | `NULL`          | `CURSOR`  | `ARRAY`     |

#### 构造方法

```
<constructor>
   <idArg column="id" javaType="int"/>
   <arg column="username" javaType="String"/>
</constructor>
```

对于大多数数据传输对象(Data Transfer Object,DTO)类型,属性可以起作用,而且像 你绝大多数的领域模型, 指令也许是你想使用一成不变的类的地方。 通常包含引用或查询数 据的表很少或基本不变的话对一成不变的类来说是合适的。 构造方法注入允许你在初始化时 为类设置属性的值,而不用暴露出公有方法。MyBatis 也支持私有属性和私有 JavaBeans 属 性来达到这个目的,但是一些人更青睐构造方法注入。构造方法元素支持这个。

看看下面这个构造方法:

```
    public class domain.User {
       //...
       public domain.User(int id, String username) {
         //...
      }
    //...
    }
```

为了向这个构造方法中注入结果,MyBatis 需要通过它的参数的类型来标识构造方法。 Java 没有自查(反射)参数名的方法。所以当创建一个构造方法元素时,保证参数是按顺序 排列的,而且数据类型也是确定的。

```
<constructor>
   <idArg column="id" javaType="int"/>
   <arg column="username" javaType="String"/>
</constructor>
```

剩余的属性和规则和固定的 id 和 result 元素是相同的。

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `column`      | 来自数据库的类名,或重命名的列标签。这和通常传递给 `resultSet.getString(columnName)`方法的字符串是相同的。 |
| `javaType`    | 一个 Java 类的完全限定名,或一个类型别名(参考上面内建类型别名的列表)。 如果你映射到一个 JavaBean,MyBatis 通常可以断定类型。然而,如 果你映射到的是 HashMap,那么你应该明确地指定 javaType 来保证所需的 行为。 |
| `jdbcType`    | 在这个表格之前的所支持的 JDBC 类型列表中的类型。JDBC 类型是仅仅 需要对插入, 更新和删除操作可能为空的列进行处理。这是 JDBC 的需要, jdbcType 而不是 MyBatis 的。如果你直接使用 JDBC 编程,你需要指定这个类型-但 仅仅对可能为空的值。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性,你可以覆盖默认的 类型处理器。 这个属性值是类的完全限定名或者是一个类型处理器的实现, 或者是类型别名。 |
| `select`      | 另一个映射语句的 ID，该语句将加载此属性映射所需的复杂类型。从列属性中指定的列检索的值将作为参数传递给目标 select 语句。更多信息请参见关联元素。 |
| `resultMap`   | 这是 ResultMap 的 ID，它可以将此参数的嵌套结果映射到适当的对象图中。这是对另一个 select 语句调用的替代方法。它允许您将多个表连接到一个结果集中。这样的ResultSet 将包含重复的、重复的数据组，需要对这些数据进行分解并正确地映射到嵌套的对象图中。为了实现这一点，MyBatis 允许将结果映射“链”在一起，以处理嵌套的结果。更多信息请参见下面的关联元素。 |

#### `关联`

```
<association property="author" column="blog_author_id" javaType="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
</association>
```

关联元素处理"有一个"类型的关系。比如,在我们的示例中,一个博客有一个用户。 关联映射就工作于这种结果之上。你指定了目标属性,来获取值的列,属性的 java 类型(很 多情况下 MyBatis 可以自己算出来) ,如果需要的话还有 jdbc 类型,如果你想覆盖或获取的 结果值还需要类型控制器。

关联中不同的是你需要告诉 MyBatis 如何加载关联。MyBatis 在这方面会有两种不同的 方式:

- 嵌套查询:通过执行另外一个 SQL 映射语句来返回预期的复杂类型。
- 嵌套结果:使用嵌套结果映射来处理重复的联合结果的子集。首先,然让我们来查看这个元素的属性。所有的你都会看到,它和普通的只由 select 和

resultMap 属性的结果映射不同。

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `property`    | 映射到列结果的字段或属性。如果匹配的是存在的,和给定名称相同的 property JavaBeans 的属性, 那么就会使用。 否则 MyBatis 将会寻找给定名称的字段。 这两种情形你可以使用通常点式的复杂属性导航。比如,你可以这样映射 一 些 东 西 :`" username "`, 或 者 映 射 到 一 些 复 杂 的 东 西 : `"address.street.number"` 。 |
| `javaType`    | 一个 Java 类的完全限定名,或一个类型别名(参考上面内建类型别名的列 表) 。如果你映射到一个 JavaBean,MyBatis 通常可以断定类型。然而,如 javaType 果你映射到的是 HashMap,那么你应该明确地指定 javaType 来保证所需的 行为。 |
| `jdbcType`    | 在这个表格之前的所支持的 JDBC 类型列表中的类型。JDBC 类型是仅仅 需要对插入, 更新和删除操作可能为空的列进行处理。这是 JDBC 的需要, jdbcType 而不是 MyBatis 的。如果你直接使用 JDBC 编程,你需要指定这个类型-但 仅仅对可能为空的值。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性,你可以覆盖默认的 typeHandler 类型处理器。 这个属性值是类的完全限定名或者是一个类型处理器的实现, 或者是类型别名。 |

#### 关联的嵌套查询

| 属性        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| `column`    | 来自数据库的类名,或重命名的列标签。这和通常传递给 `resultSet.getString(columnName)`方法的字符串是相同的。 column 注 意 : 要 处 理 复 合 主 键 , 你 可 以 指 定 多 个 列 名 通 过` column= " {prop1=col1,prop2=col2} "` 这种语法来传递给嵌套查询语 句。这会引起 prop1 和 prop2 以参数对象形式来设置给目标嵌套查询语句。 |
| `select`    | 另外一个映射语句的 ID,可以加载这个属性映射需要的复杂类型。获取的 在列属性中指定的列的值将被传递给目标 select 语句作为参数。表格后面 有一个详细的示例。 select 注 意 : 要 处 理 复 合 主 键 , 你 可 以 指 定 多 个 列 名 通 过 `column= " {prop1=col1,prop2=col2} " `这种语法来传递给嵌套查询语 句。这会引起 prop1 和 prop2 以参数对象形式来设置给目标嵌套查询语句。 |
| `fetchType` | 可选的。有效值是 lazy 的和 eager 的。如果存在，它将替代此映射的全局配置参数` lazyLoadingEnabled`。 |

示例:

```
<resultMap id="blogResult" type="Blog">
  <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectAuthor" resultType="Author">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</select>
```

我们有两个查询语句:一个来加载博客,另外一个来加载作者,而且博客的结果映射描 述了"selectAuthor"语句应该被用来加载它的 author 属性。

其他所有的属性将会被自动加载,假设它们的列和属性名相匹配。

这种方式很简单, 但是对于大型数据集合和列表将不会表现很好。 问题就是我们熟知的 "N+1 查询问题"。概括地讲,N+1 查询问题可以是这样引起的:

- 你执行了一个单独的 SQL 语句来获取结果列表(就是"+1")。
- 对返回的每条记录,你执行了一个查询语句来为每个加载细节(就是"N")。

这个问题会导致成百上千的 SQL 语句被执行。这通常不是期望的。

MyBatis 能延迟加载这样的查询就是一个好处,因此你可以分散这些语句同时运行的消 耗。然而,如果你加载一个列表,之后迅速迭代来访问嵌套的数据,你会调用所有的延迟加 载,这样的行为可能是很糟糕的。

所以还有另外一种方法。

#### 关联的嵌套结果

| 属性            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| `resultMap`     | 这是结果映射的 ID,可以映射关联的嵌套结果到一个合适的对象图中。这 是一种替代方法来调用另外一个查询语句。这允许你联合多个表来合成到 `resultMap` 一个单独的结果集。这样的结果集可能包含重复,数据的重复组需要被分 解,合理映射到一个嵌套的对象图。为了使它变得容易,MyBatis 让你"链 接"结果映射,来处理嵌套结果。一个例子会很容易来仿照,这个表格后 面也有一个示例。 |
| `columnPrefix`  | 在连接多个表时，必须使用列别名以避免结果集中的重复列名。指定 `columnPrefix` 允许您将这些列映射到外部`resultMap`。请参阅本节后面解释的示例。 |
| `notNullColumn` | 默认情况下，只有在映射到子对象属性的至少一个列非空时，才会创建子对象。有了这个属性，您可以通过指定哪些列必须有一个值来改变这种行为，这样 MyBatis 将只在这些列中的任何一列不为空时创建子对象。可以使用逗号作为分隔符指定多个列名。默认值:未设置的。 |
| `autoMapping`   | 如果存在，MyBatis 将在将结果映射到此属性时启用或禁用自动映射。此属性覆盖全局 `autoMappingBehavior`。注意，它对外部` resultMap` 没有影响，因此与`select`或 `resultMap` 属性一起使用是没有意义的。默认值:未设置的。 |

在上面你已经看到了一个非常复杂的嵌套关联的示例。 下面这个是一个非常简单的示例 来说明它如何工作。代替了执行一个分离的语句,我们联合博客表和作者表在一起,就像:

```
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    B.author_id     as blog_author_id,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</select>
```

注意这个联合查询, 以及采取保护来确保所有结果被唯一而且清晰的名字来重命名。 这使得映射非常简单。现在我们可以映射这个结果:

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```

在上面的示例中你可以看到博客的作者关联代表着"authorResult"结果映射来加载作 者实例。

非常重要: 在嵌套据诶过映射中 id 元素扮演了非常重要的角色。应应该通常指定一个 或多个属性,它们可以用来唯一标识结果。实际上就是如果你离开她了,但是有一个严重的 性能问题时 MyBatis 仍然可以工作。选择的属性越少越好,它们可以唯一地标识结果。主键 就是一个显而易见的选择(尽管是联合主键)。

现在,上面的示例用了外部的结果映射元素来映射关联。这使得 Author 结果映射可以 重用。然而,如果你不需要重用它的话,或者你仅仅引用你所有的结果映射合到一个单独描 述的结果映射中。你可以嵌套结果映射。这里给出使用这种方式的相同示例:

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
  </association>
</resultMap>
```

如果博客有一个共同作者呢?select语句如下:

```
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio,
    CA.id           as co_author_id,
    CA.username     as co_author_username,
    CA.password     as co_author_password,
    CA.email        as co_author_email,
    CA.bio          as co_author_bio
  from Blog B
  left outer join Author A on B.author_id = A.id
  left outer join Author CA on B.co_author_id = CA.id
  where B.id = #{id}
</select>
```

回想一下，Author的resultMap定义如下:

```
<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```

由于结果中的列名与resultMap中定义的列不同，因此您需要指定columnPrefix来重新使用resultMap，以便映射合Author的结果。

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author"
    resultMap="authorResult" />
  <association property="coAuthor"
    resultMap="authorResult"
    columnPrefix="co_" />
</resultMap>
```

上面你已经看到了如何处理"有一个"类型关联。但是"有很多个"是怎样的?下面这 个部分就是来讨论这个主题的。

#### 集合

```
<collection property="posts" ofType="domain.blog.Post">
  <id property="id" column="post_id"/>
  <result property="subject" column="post_subject"/>
  <result property="body" column="post_body"/>
</collection>
```

集合元素的作用几乎和关联是相同的。实际上,它们也很相似,文档的异同是多余的。 所以我们更多关注于它们的不同。

我们来继续上面的示例,一个博客只有一个作者。但是博客有很多文章。在博客类中, 这可以由下面这样的写法来表示:

```
    private List posts;
```

要映射嵌套结果集合到 List 中,我们使用集合元素。就像关联元素一样,我们可以从 连接中使用嵌套查询,或者嵌套结果。

#### 集合的嵌套查询

首先,让我们看看使用嵌套查询来为博客加载文章。

```
<resultMap id="blogResult" type="Blog">
  <collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectPostsForBlog" resultType="Blog">
  SELECT * FROM POST WHERE BLOG_ID = #{id}
</select>
```

这里你应该注意很多东西,但大部分代码和上面的关联元素是非常相似的。首先,你应 该注意我们使用的是集合元素。然后要注意那个新的"ofType"属性。这个属性用来区分 JavaBean(或字段)属性类型和集合包含的类型来说是很重要的。所以你可以读出下面这个 映射:

```
<collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
```

读作: "在 Post 类型的 ArrayList 中的 posts 的集合。"

javaType 属性是不需要的,因为 MyBatis 在很多情况下会为你算出来。所以你可以缩短 写法:

```
<collection property="posts" column="id" ofType="Post" select="selectPostsForBlog"/>
```

#### 集合的嵌套结果

至此,你可以猜测集合的嵌套结果是如何来工作的,因为它和关联完全相同,除了它应 用了一个"ofType"属性

First, let's look at the SQL:

```
<select id="selectBlog" resultMap="blogResult">
  select
  B.id as blog_id,
  B.title as blog_title,
  B.author_id as blog_author_id,
  P.id as post_id,
  P.subject as post_subject,
  P.body as post_body,
  from Blog B
  left outer join Post P on B.id = P.blog_id
  where B.id = #{id}
</select>
```

我们又一次联合了博客表和文章表,而且关注于保证特性,结果列标签的简单映射。现 在用文章映射集合映射博客,可以简单写为:

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <result property="body" column="post_body"/>
  </collection>
</resultMap>
```

同样,要记得 id 元素的重要性,如果你不记得了,请阅读上面的关联部分。

同样, 如果你引用更长的形式允许你的结果映射的更多重用, 你可以使用下面这个替代 的映射:

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post" resultMap="blogPostResult" columnPrefix="post_"/>
</resultMap>

<resultMap id="blogPostResult" type="Post">
  <id property="id" column="id"/>
  <result property="subject" column="subject"/>
  <result property="body" column="body"/>
</resultMap>
```

注意 这个对你所映射的内容没有深度,广度或关联和集合相联合的限制。当映射它们 时你应该在大脑中保留它们的表现。 你的应用在找到最佳方法前要一直进行的单元测试和性 能测试。好在 myBatis 让你后来可以改变想法,而不对你的代码造成很小(或任何)影响。

高级关联和集合映射是一个深度的主题。文档只能给你介绍到这了。加上一点联系,你 会很快清楚它们的用法。

#### 鉴别器

```
<discriminator javaType="int" column="draft">
  <case value="1" resultType="DraftPost"/>
</discriminator>
```

有时一个单独的数据库查询也许返回很多不同 (但是希望有些关联) 数据类型的结果集。 鉴别器元素就是被设计来处理这个情况的, 还有包括类的继承层次结构。 鉴别器非常容易理 解,因为它的表现很像 Java 语言中的 switch 语句。

定义鉴别器指定了 column 和 javaType 属性。 列是 MyBatis 查找比较值的地方。 JavaType 是需要被用来保证等价测试的合适类型(尽管字符串在很多情形下都会有用)。比如:

```
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultMap="carResult"/>
    <case value="2" resultMap="truckResult"/>
    <case value="3" resultMap="vanResult"/>
    <case value="4" resultMap="suvResult"/>
  </discriminator>
</resultMap>
```

在这个示例中, MyBatis 会从结果集中得到每条记录, 然后比较它的 vehicle 类型的值。 如果它匹配任何一个鉴别器的实例,那么就使用这个实例指定的结果映射。换句话说,这样 做完全是剩余的结果映射被忽略(除非它被扩展,这在第二个示例中讨论) 。如果没有任何 一个实例相匹配,那么 MyBatis 仅仅使用鉴别器块外定义的结果映射。所以,如果 carResult 按如下声明:

```
<resultMap id="carResult" type="Car">
  <result property="doorCount" column="door_count" />
</resultMap>
```

那么只有 doorCount 属性会被加载。这步完成后完整地允许鉴别器实例的独立组,尽管 和父结果映射可能没有什么关系。这种情况下,我们当然知道 cars 和 vehicles 之间有关系, 如 Car 是一个 Vehicle 实例。因此,我们想要剩余的属性也被加载。我们设置的结果映射的 简单改变如下。

```
<resultMap id="carResult" type="Car" extends="vehicleResult">
  <result property="doorCount" column="door_count" />
</resultMap>
```

现在 vehicleResult 和 carResult 的属性都会被加载了。

尽管曾经有些人会发现这个外部映射定义会多少有一些令人厌烦之处。 因此还有另外一 种语法来做简洁的映射风格。比如:

```
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultType="carResult">
      <result property="doorCount" column="door_count" />
    </case>
    <case value="2" resultType="truckResult">
      <result property="boxSize" column="box_size" />
      <result property="extendedCab" column="extended_cab" />
    </case>
    <case value="3" resultType="vanResult">
      <result property="powerSlidingDoor" column="power_sliding_door" />
    </case>
    <case value="4" resultType="suvResult">
      <result property="allWheelDrive" column="all_wheel_drive" />
    </case>
  </discriminator>
</resultMap>
```

要记得 这些都是结果映射, 如果你不指定任何结果, 那么 MyBatis 将会为你自动匹配列 和属性。所以这些例子中的大部分是很冗长的,而其实是不需要的。也就是说,很多数据库 是很复杂的,我们不太可能对所有示例都能依靠它。

### 自动映射

正如你在前面一节看到的，在简单的场景下，MyBatis 可以替你自动映射查询结果。 如果遇到复杂的场景，你需要构建一个 result map。 但是在本节你将看到，你也可以混合使用这两种策略。 让我们到深一点的层面上看看自动映射是怎样工作的。

当自动映射查询结果时，MyBatis 会获取 sql 返回的列名并在 java 类中查找相同名字的属性（忽略大小写）。 这意味着如果 Mybatis 发现了 _ID_ 列和 _id_ 属性，Mybatis会将_ID_ 的值赋给 id。

通常数据库列使用大写单词命名，单词间用下划线分隔；而java属性一般遵循驼峰命名法。 为了在这两种命名方式之间启用自动映射，需要将 `mapUnderscoreToCamelCase`设置为 true。

自动映射甚至在特定的 result map下也能工作。在这种情况下，对于每一个 result map,所有的 ResultSet 提供的列， 如果没有被手工映射，则将被自动映射。自动映射处理完毕后手工映射才会被处理。 在接下来的例子中， id 和 _userName_列将被自动映射， _hashedpassword 列将根据配置映射。

```
<select id="selectUsers" resultMap="userResultMap">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password
  from some_table
  where id = #{id}
</select>
<resultMap id="userResultMap" type="domain.User">
  <result property="password" column="hashed_password"/>
</resultMap>
```

有三个自动映射级别:

- `NONE` - 禁用自动映射,只有手动映射属性才会被设置。
- `PARTIAL` - 将自动映射结果，除了那些嵌套结果映射（连接）内的结果。
- `FULL` - 自动映射一切

`默认值是 PARTIAL`,这是有原因的。 使用 FULL 时，将在处理连接结果时执行自动映射，并且连接会检索同一行中的多个不同实体的数据，因此可能会导致不需要的映射。 要了解风险，请查看下面的示例：

```
<select id="selectBlog" resultMap="blogResult">
  select
    B.id,
    B.title,
    A.username,
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</select>
<resultMap id="blogResult" type="Blog">
  <association property="author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <result property="username" column="author_username"/>
</resultMap>
```

在结果中 Blog 和 Author 均将自动映射。但是注意 Author 有一个 id 属性，在 ResultSet 中有一个列名为 id， 所以 Author 的 id 将被填充为 Blog 的 id，这不是你所期待的。所以需要谨慎使用 FULL。

通过添加 autoMapping 属性可以忽略自动映射等级配置，你可以启用或者禁用自动映射指定的 ResultMap。

```
<resultMap id="userResultMap" type="domain.User" autoMapping="false">
  <result property="password" column="hashed_password"/>
</resultMap>
```

### 缓存

MyBatis 包含一个非常强大的查询缓存特性,它可以非常方便地配置和定制。MyBatis 3 中的缓存实现的很多改进都已经实现了,使得它更加强大而且易于配置。

默认情况下是没有开启缓存的,除了局部的 session 缓存,可以增强变现而且处理循环 依赖也是必须的。要开启二级缓存,你需要在你的 SQL 映射文件中添加一行:

```
<cache/>
```

字面上看就是这样。这个简单语句的效果如下:

- 映射语句文件中的所有 select 语句将会被缓存。
- 映射语句文件中的所有 insert,update 和 delete 语句会刷新缓存。
- 缓存会使用 Least Recently Used(LRU,最近最少使用的)算法来收回。
- 根据时间表(比如 no Flush Interval,没有刷新间隔), 缓存不会以任何时间顺序 来刷新。
- 缓存会存储列表集合或对象(无论查询方法返回什么)的 1024 个引用。
- 缓存会被视为是 read/write(可读/可写)的缓存,意味着对象检索不是共享的,而 且可以安全地被调用者修改,而不干扰其他调用者或线程所做的潜在修改。

所有的这些属性都可以通过缓存元素的属性来修改。比如:

```
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```

这个更高级的配置创建了一个 FIFO 缓存,并每隔 60 秒刷新,存数结果对象或列表的 512 个引用,而且返回的对象被认为是只读的,因此在不同线程中的调用者之间修改它们会 导致冲突。

可用的收回策略有:

- `LRU` – 最近最少使用的:移除最长时间不被使用的对象。
- `FIFO` – 先进先出:按对象进入缓存的顺序来移除它们。
- `SOFT` – 软引用:移除基于垃圾回收器状态和软引用规则的对象。
- `WEAK` – 弱引用:更积极地移除基于垃圾收集器状态和弱引用规则的对象。

默认的是 LRU。

flushInterval (刷新间隔)可以被设置为任意的正整数,而且它们代表一个合理的毫秒 形式的时间段。默认情况是不设置,也就是没有刷新间隔,缓存仅仅调用语句时刷新。

size(引用数目)可以被设置为任意正整数,要记住你缓存的对象数目和你运行环境的 可用内存资源数目。默认值是 1024。

readOnly (只读)属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓 存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势。可读写的缓存 会返回缓存对象的拷贝(通过序列化) 。这会慢一些,但是安全,因此默认是 false。

#### 使用自定义缓存

除了这些自定义缓存的方式, 你也可以通过实现你自己的缓存或为其他第三方缓存方案 创建适配器来完全覆盖缓存行为。

```
<cache type="com.domain.something.MyCustomCache"/>
```

这个示 例展 示了 如何 使用 一个 自定义 的缓 存实 现。type 属 性指 定的 类必 须实现 org.mybatis.cache.Cache 接口。这个接口是 MyBatis 框架中很多复杂的接口之一,但是简单 给定它做什么就行。

```
    public interface Cache {
      String getId();
      int getSize();
      void putObject(Object key, Object value);
      Object getObject(Object key);
      boolean hasKey(Object key);
      Object removeObject(Object key);
      void clear();
    }
```

要配置你的缓存, 简单和公有的 JavaBeans 属性来配置你的缓存实现, 而且是通过 cache 元素来传递属性, 比如, 下面代码会在你的缓存实现中调用一个称为 "setCacheFile(String file)" 的方法:

```
<cache type="com.domain.something.MyCustomCache">
  <property name="cacheFile" value="/tmp/my-custom-cache.tmp"/>
</cache>
```

你可以使用所有简单类型作为 JavaBeans 的属性,MyBatis 会进行转换。

记得缓存配置和缓存实例是绑定在 SQL 映射文件的命名空间是很重要的。因此,所有 在相同命名空间的语句正如绑定的缓存一样。 语句可以修改和缓存交互的方式, 或在语句的 语句的基础上使用两种简单的属性来完全排除它们。默认情况下,语句可以这样来配置:

```
<select ... flushCache="false" useCache="true"/>
<insert ... flushCache="true"/>
<update ... flushCache="true"/>
<delete ... flushCache="true"/>
```

因为那些是默认的,你明显不能明确地以这种方式来配置一条语句。相反,如果你想改 变默认的行为,只能设置 flushCache 和 useCache 属性。比如,在一些情况下你也许想排除 从缓存中查询特定语句结果,或者你也许想要一个查询语句来刷新缓存。相似地,你也许有 一些更新语句依靠执行而不需要刷新缓存。

### 参照缓存

回想一下上一节内容, 这个特殊命名空间的唯一缓存会被使用或者刷新相同命名空间内 的语句。也许将来的某个时候,你会想在命名空间中共享相同的缓存配置和实例。在这样的 情况下你可以使用 cache-ref 元素来引用另外一个缓存。

```
<cache-ref namespace="com.someone.application.data.SomeMapper"/>
```

</font>



# 4、MyBatis 动态SQL

## 动态 SQL

MyBatis 的强大特性之一便是它的动态 SQL。如果你有使用 JDBC 或其他类似框架的经验，你就能体会到根据不同条件拼接 SQL 语句有多么痛苦。拼接的时候要确保不能忘了必要的空格，还要注意省掉列名列表最后的逗号。利用动态 SQL 这一特性可以彻底摆脱这种痛苦。

通常使用动态 SQL 不可能是独立的一部分,MyBatis 当然使用一种强大的动态 SQL 语言来改进这种情形,这种语言可以被用在任意的 SQL 映射语句中。

动态 SQL 元素和使用 JSTL 或其他类似基于 XML 的文本处理器相似。在 MyBatis 之前的版本中,有很多的元素需要来了解。MyBatis 3 大大提升了它们,现在用不到原先一半的元素就可以了。MyBatis 采用功能强大的基于 OGNL 的表达式来消除其他元素。

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

## if

动态 SQL 通常要做的事情是有条件地包含 where 子句的一部分。比如:

```
<select id="findActiveBlogWithTitleLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE state = ‘ACTIVE’ 
  <if test="title != null">  这是必选条件
    AND title like #{title}   这里是可选条件，如果传入参数为空，就只查必选条件，也不会报错。
  </if>
</select>
```

<font color = Tomato size=3 face="楷书">这条语句提供了一个可选的文本查找类型的功能。如果没有传入"title"，那么所有处于"ACTIVE"状态的BLOG都会返回；反之若传入了"title"，那么就会把模糊查找"title"内容的BLOG结果返回</font>（就这个例子而言，细心的读者会发现其中的参数值是可以包含一些掩码或通配符的）。

如果想可选地通过"title"和"author"两个条件搜索该怎么办呢？首先，改变语句的名称让它更具实际意义；然后只要加入另一个条件即可。

<font color = blue size=3 face="楷书">先通过第一个if条件查询，然后再在结果中通过第二个条件查询结果。</font>

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’ 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```

## choose, when, otherwise

有些时候，我们不想用到所有的条件语句，而只想从中择其一二。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

还是上面的例子，<font color = Tomato size=3 face="楷书">但是这次变为提供了"title"就按"title"查找，提供了"author"就按"author"查找，若两者都没有提供，就返回所有符合条件的BLOG（实际情况可能是由管理员按一定策略选出BLOG列表，而不是返回大量无意义的随机结果）。</font>

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

## trim, where, set

前面几个例子已经合宜地解决了一个臭名昭著的动态 SQL 问题。现在考虑回到"if"示例，这次我们将"ACTIVE = 1"也设置成动态的条件，看看会发生什么。

```
反面教材
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE 
  <if test="state != null">
    state = #{state}
  </if> 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```

如果这些条件没有一个能匹配上将会怎样？最终这条 SQL 会变成这样：

```
SELECT * FROM BLOG
WHERE
```

这会导致查询失败。如果仅仅第二个条件匹配又会怎样？这条 SQL 最终会是这样:

```
SELECT * FROM BLOG
WHERE
AND title like 'someTitle'
```

这个查询也会失败。这个问题不能简单的用条件句式来解决，如果你也曾经被迫这样写过，那么你很可能从此以后都不想再这样去写了。

<font color = Tomato size=3 face="楷书">MyBatis 有一个简单的处理，这在90%的情况下都会有用。而在不能使用的地方，你可以自定义处理方式来令其正常工作。一处简单的修改就能得到想要的效果：就是把where 改为 <where>     </where>'</font>

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  <where> 
    <if test="state != null">
         state = #{state}
    </if> 
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

<font color = Tomato size=3 face="楷书">where 元素知道只有在一个以上的if条件有值的情况下才去插入"WHERE"子句。而且，若最后的内容是"AND"或"OR"开头的，where 元素也知道如何将他们去除。</font>

如果 where 元素没有按正常套路出牌，我们还是可以通过自定义 trim 元素来定制我们想要的功能。比如，和 where 元素等价的自定义 trim 元素为：

```
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ... 
</trim>
```

prefixOverrides 属性会忽略通过管道分隔的文本序列（注意此例中的空格也是必要的）。它带来的结果就是所有在 prefixOverrides 属性中指定的内容将被移除，并且插入 prefix 属性中指定的内容。

类似的用于动态更新语句的解决方案叫做 set。set 元素可以被用于动态包含需要更新的列，而舍去其他的。比如：

```
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```

这里，set 元素会动态前置 SET 关键字，同时也会消除无关的逗号，因为用了条件语句之后很可能就会在生成的赋值语句的后面留下这些逗号。

若你对等价的自定义 trim 元素的样子感兴趣，那这就应该是它的真面目：

```
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```

注意这里我们忽略的是后缀中的值，而又一次附加了前缀中的值。

## foreach

动态 SQL 的另外一个常用的必要操作是需要对一个集合进行遍历，通常是在构建 IN 条件语句的时候。比如：

```
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

foreach 元素的功能是非常强大的，它允许你指定一个集合，声明可以用在元素体内的集合项和索引变量。它也允许你指定开闭匹配的字符串以及在迭代中间放置分隔符。这个元素是很智能的，因此它不会偶然地附加多余的分隔符。

注意 你可以将一个 List 实例或者数组作为参数对象传给 MyBatis，当你这么做的时候，MyBatis 会自动将它包装在一个 Map 中并以名称为键。List 实例将会以"list"作为键，而数组实例的键将是"array"。

到此我们已经完成了涉及 XML 配置文件和 XML 映射文件的讨论。下一部分将详细探讨 Java API，这样才能从已创建的映射中获取最大利益。

**各种用法**

foreach的主要用在构建in条件中，它可以在SQL语句中进行迭代一个集合。foreach元素的属性主要有*item，index，collection，open，separator，close。*

item表示集合中每一个元素进行迭代时的别名，

index指定一个名字，用于表示在迭代过程中，每次迭代到的位置，

open表示该语句以什么开始，

separator表示在每次进行迭代之间以什么符号作为分隔符，

close表示以什么结束，在使用foreach的时候最关键的也是最容易出错的就是

collection属性，该属性是必须指定的，但是在不同情况下，该属性的值是不一样的，主要有一下3种情况：
				 如果传入的是单参数且参数类型是一个List的时候，collection属性值为list
				如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array
				如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可以封装成map，实际上如果你在传入参数的时候，在MyBatis里面也是会把它封装成一个Map的，map的key就是参数名，所以这个时候collection属性值就是传入的List或array对象在自己封装的map里面的key

下面分别来看看上述三种情况的示例代码：
1.单参数List的类型：
Xml代码   

<select id="dynamicForeachTest" resultType="Blog">  
    select * from t_blog where id in  
    <foreach collection="list" index="index" item="item" open="(" separator="," close=")">  
        #{item}  
    </foreach>  
</select>  
 上述collection的值为list，对应的Mapper是这样的
Java代码   
public List<Blog> dynamicForeachTest(List<Integer> ids);  
 测试代码：
Java代码   
@Test  
public void dynamicForeachTest() {  
    SqlSession session = Util.getSqlSessionFactory().openSession();  
    BlogMapper blogMapper = session.getMapper(BlogMapper.class);  
    List<Integer> ids = new ArrayList<Integer>();  
    ids.add(1);  
    ids.add(3);  
    ids.add(6);  
    List<Blog> blogs = blogMapper.dynamicForeachTest(ids);  
    for (Blog blog : blogs)  
        System.out.println(blog);  
    session.close();  
}  

2.单参数array数组的类型：
Xml代码   
<select id="dynamicForeach2Test" resultType="Blog">  
    select * from t_blog where id in  
    <foreach collection="array" index="index" item="item" open="(" separator="," close=")">  
        #{item}  
    </foreach>  
</select>  
 上述collection为array，对应的Mapper代码：
Java代码   
public List<Blog> dynamicForeach2Test(int[] ids);  
 对应的测试代码：
Java代码   
@Test  
public void dynamicForeach2Test() {  
    SqlSession session = Util.getSqlSessionFactory().openSession();  
    BlogMapper blogMapper = session.getMapper(BlogMapper.class);  
    int[] ids = new int[] {1,3,6,9};  
    List<Blog> blogs = blogMapper.dynamicForeach2Test(ids);  
    for (Blog blog : blogs)  
        System.out.println(blog);  
    session.close();  
}  

3.自己把参数封装成Map的类型
Xml代码   
<select id="dynamicForeach3Test" resultType="Blog">  
    select * from t_blog where title like "%"#{title}"%" and id in  
    <foreach collection="ids" index="index" item="item" open="(" separator="," close=")">  
        #{item}  
    </foreach>  
</select>  
上述collection的值为ids，是传入的参数Map的key，对应的Mapper代码：
Java代码   
public List<Blog> dynamicForeach3Test(Map<String, Object> params);  
  对应测试代码：
Java代码   
@Test  
public void dynamicForeach3Test() {  
    SqlSession session = Util.getSqlSessionFactory().openSession();  
    BlogMapper blogMapper = session.getMapper(BlogMapper.class);  
    final List<Integer> ids = new ArrayList<Integer>();  
    ids.add(1);  
    ids.add(2);  
    ids.add(3);  
    ids.add(6);  
    ids.add(7);  
    ids.add(9);  
    Map<String, Object> params = new HashMap<String, Object>();  
    params.put("ids", ids);  
    params.put("title", "中国");  
    List<Blog> blogs = blogMapper.dynamicForeach3Test(params);  
    for (Blog blog : blogs)  
        System.out.println(blog);  
    session.close();  
} 

## bind 

`bind` 元素可以从 OGNL 表达式中创建一个变量并将其绑定到上下文。比如：

<font color = Tomato size=3 face="楷书">相当于声明变量值</font>,例如声明了pattern的变量，值为"'%' + _parameter.getTitle() + '%'"

```
<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>
```

## Multi-db vendor support 根据不同数据库类型执行不同语句

<font color = Tomato size=3 face="楷书">一个配置了"_databaseId"变量的 databaseIdProvider 对于动态代码来说是可用的，这样就可以根据不同的数据库厂商构建特定的语句。</font>比如下面的例子：

```
<insert id="insert">
  <selectKey keyProperty="id" resultType="int" order="BEFORE">
    <if test="_databaseId == 'oracle'">
      select seq_users.nextval from dual
    </if>
    <if test="_databaseId == 'db2'">
      select nextval for seq_users from sysibm.sysdummy1"
    </if>
  </selectKey>
  insert into users values (#{id}, #{name})
</insert>
```

## 动态 SQL 中可插拔的脚本语言

MyBatis 从 3.2 开始支持可插拔的脚本语言，因此你可以在插入一种语言的驱动（language driver）之后来写基于这种语言的动态 SQL 查询。

可以通过实现下面接口的方式来插入一种语言：

```
    public interface LanguageDriver {
      ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);
      SqlSource createSqlSource(Configuration configuration, XNode script, Class parameterType);
      SqlSource createSqlSource(Configuration configuration, String script, Class parameterType);
    }
```

一旦有了自定义的语言驱动，你就可以在 mybatis-config.xml 文件中将它设置为默认语言：

```
<typeAliases>
  <typeAlias type="org.sample.MyLanguageDriver" alias="myLanguage"/>
</typeAliases>
<settings>
  <setting name="defaultScriptingLanguage" value="myLanguage"/>
</settings>
```

除了设置默认语言，你也可以针对特殊的语句指定特定语言，这可以通过如下的 `lang` 属性来完成：

```
<select id="selectBlog" lang="myLanguage">
  SELECT * FROM BLOG
</select>
```

或者在你正在使用的映射中加上注解 `@Lang` 来完成：

```
    public interface Mapper {
      @Lang(MyLanguageDriver.class)
      @Select("SELECT * FROM BLOG")
      List selectBlog();
    }
```

注意 可以将 Apache Velocity 作为动态语言来使用，更多细节请参考 MyBatis-Velocity 项目。

你前面看到的所有 xml 标签都是默认 MyBatis 语言提供的，它是由别名为 `xml` 语言驱动器 `org.apache.ibatis.scripting.xmltags.XmlLanguageDriver` 驱动的。



# 5、MyBatis Java API

2021-07-26 17:11 更新

## Java API

既然你已经知道如何配置 MyBatis 和创建映射文件,你就已经准备好来提升技能了。 MyBatis 的 Java API 就是你收获你所做的努力的地方。正如你即将看到的,和 JDBC 相比, MyBatis 很大程度简化了你的代码而且保持简洁,很容易理解和维护。MyBatis 3 已经引入 了很多重要的改进来使得 SQL 映射更加优秀。

## 应用目录结构

在我们深入 Java API 之前,理解关于目录结构的最佳实践是很重要的。MyBatis 非常灵 活, 你可以用你自己的文件来做几乎所有的事情。 但是对于任一框架, 都有一些最佳的方式。

让我们看一下典型应用的目录结构:

```
/my_application
  /bin
  /devlib
  /lib                <-- MyBatis *.jar文件在这里。
  /src
    /org/myapp/
      /action
      /data           <-- MyBatis配置文件在这里, 包括映射器类, XML配置, XML映射文件。
        /mybatis-config.xml
        /BlogMapper.java
        /BlogMapper.xml
      /model
      /service
      /view
    /properties       <-- 在你XML中配置的属性 文件在这里。
  /test
    /org/myapp/
      /action
      /data
      /model
      /service
      /view
    /properties
  /web
    /WEB-INF
      /web.xml
```

Remember, these are preferences, not requirements, but others will thank you for using a common directory structure.

这部分内容剩余的示例将假设你使用了这种目录结构。

## SqlSessions

使用 MyBatis 的主要 Java 接口就是 SqlSession。尽管你可以使用这个接口执行命令,获 取映射器和管理事务。我们会讨论 SqlSession 本身更多,但是首先我们还是要了解如果获取 一个 SqlSession 实例。SqlSessions 是由 SqlSessionFactory 实例创建的。SqlSessionFactory 对 象 包 含 创 建 SqlSession 实 例 的 所 有 方 法 。 而 SqlSessionFactory 本 身 是 由 SqlSessionFactoryBuilder 创建的,它可以从 XML 配置,注解或手动配置 Java 来创建 SqlSessionFactory。

NOTE When using MyBatis with a dependency injection framework like Spring or Guice, SqlSessions are created and injected by the DI framework so you don't need to use the SqlSessionFactoryBuilder or SqlSessionFactory and can go directly to the SqlSession section. Please refer to the MyBatis-Spring or MyBatis-Guice manuals for further info.

#### SqlSessionFactoryBuilder

SqlSessionFactoryBuilder 有五个 build()方法,每一种都允许你从不同的资源中创建一个 SqlSession 实例。

```
    SqlSessionFactory build(InputStream inputStream)
    SqlSessionFactory build(InputStream inputStream, String environment)
    SqlSessionFactory build(InputStream inputStream, Properties properties)
    SqlSessionFactory build(InputStream inputStream, String env, Properties props)
    SqlSessionFactory build(Configuration config)
```

第一种方法是最常用的,它使用了一个参照了 XML 文档或上面讨论过的更特定的 mybatis-config.xml 文件的 Reader 实例。 可选的参数是 environment 和 properties。 Environment 决定加载哪种环境,包括数据源和事务管理器。比如:

```
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
        ...
    <dataSource type="POOLED">
        ...
  </environment>
  <environment id="production">
    <transactionManager type="MANAGED">
        ...
    <dataSource type="JNDI">
        ...
  </environment>
</environments>
```

如果你调用了 一个使用 environment 参数 方 式的 build 方法, 那么 MyBatis 将会使用 configuration 对象来配置这个 environment。 当然, 如果你指定了一个不合法的 environment, 你会得到错误提示。 如果你调用了其中之一没有 environment 参数的 build 方法, 那么就使用 默认的 environment(在上面的示例中就会指定为 default="development")。

如果你调用了使用 properties 实例的方法,那么 MyBatis 就会加载那些 properties(属性 配置文件) ,并你在你配置中可使用它们。那些属性可以用${propName}语法形式多次用在 配置文件中。

回想一下,属性可以从 mybatis-config.xml 中被引用,或者直接指定它。因此理解优先 级是很重要的。我们在文档前面已经提及它了,但是这里要再次重申:

如果一个属性存在于这些位置,那么 MyBatis 将会按找下面的顺序来加载它们:

- 在 properties 元素体中指定的属性首先被读取,
- 从 properties 元素的类路径 resource 或 url 指定的属性第二个被读取, 可以覆盖已经 指定的重复属性,
- 作为方法参 数传递 的属性最 后被读 取,可以 覆盖已 经从 properties 元 素体和 resource/url 属性中加载的任意重复属性。

因此,最高优先级的属性是通过方法参数传递的,之后是 resource/url 属性指定的,最 后是在 properties 元素体中指定的属性。

总结一下,前四个方法很大程度上是相同的,但是由于可以覆盖,就允许你可选地指定 environment 和/或 properties。 这里给出一个从 mybatis-config.xml 文件创建 SqlSessionFactory 的示例:

```
    String **resource** = "org/mybatis/builder/mybatis-config.xml";
    InputStream **inputStream** = Resources.getResourceAsStream(resource);
    SqlSessionFactoryBuilder **builder** = new SqlSessionFactoryBuilder();
    SqlSessionFactory **factory** = builder.build(inputStream);
```

注意这里我们使用了 Resources 工具类,这个类在 org.mybatis.io 包中。Resources 类正 如其名,会帮助你从类路径下,文件系统或一个 web URL 加载资源文件。看一下这个类的 源代码或者通过你的 IDE 来查看,就会看到一整套有用的方法。这里给出一个简表:

```
    URL getResourceURL(String resource)
    URL getResourceURL(ClassLoader loader, String resource)
    InputStream getResourceAsStream(String resource)
    InputStream getResourceAsStream(ClassLoader loader, String resource)
    Properties getResourceAsProperties(String resource)
    Properties getResourceAsProperties(ClassLoader loader, String resource)
    Reader getResourceAsReader(String resource)
    Reader getResourceAsReader(ClassLoader loader, String resource)
    File getResourceAsFile(String resource)
    File getResourceAsFile(ClassLoader loader, String resource)
    InputStream getUrlAsStream(String urlString)
    Reader getUrlAsReader(String urlString)
    Properties getUrlAsProperties(String urlString)
    Class classForName(String className)
```

最后一个 build 方法使用了一个 Configuration 实例。configuration 类包含你可能需要了 解 SqlSessionFactory 实例的所有内容。Configuration 类对于配置的自查很有用,包含查找和 操作 SQL 映射(不推荐使用,因为应用正接收请求) 。configuration 类有所有配置的开关, 这些你已经了解了,只在 Java API 中露出来。这里有一个简单的示例,如何手动配置 configuration 实例,然后将它传递给 build()方法来创建 SqlSessionFactory。

```
    DataSource dataSource = BaseDataTest.createBlogDataSource();
    TransactionFactory transactionFactory = new JdbcTransactionFactory();

    Environment environment = new Environment("development", transactionFactory, dataSource);

    Configuration configuration = new Configuration(environment);
    configuration.setLazyLoadingEnabled(true);
    configuration.setEnhancementEnabled(true);
    configuration.getTypeAliasRegistry().registerAlias(Blog.class);
    configuration.getTypeAliasRegistry().registerAlias(Post.class);
    configuration.getTypeAliasRegistry().registerAlias(Author.class);
    configuration.addMapper(BoundBlogMapper.class);
    configuration.addMapper(BoundAuthorMapper.class);

    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    SqlSessionFactory factory = builder.build(configuration);
```

现在你有一个 SqlSessionFactory,可以用来创建 SqlSession 实例。

#### SqlSessionFactory

SqlSessionFactory 有六个方法可以用来创建 SqlSession 实例。通常来说,如何决定是你 选择下面这些方法时:

- **Transaction (事务)**: 你想为 session 使用事务或者使用自动提交(通常意味着很多 数据库和/或 JDBC 驱动没有事务)?
- **Connection (连接)**: 你想 MyBatis 获得来自配置的数据源的连接还是提供你自己
- **Execution (执行)**: 你想 MyBatis 复用预处理语句和/或批量更新语句(包括插入和 删除)?

重载的 openSession()方法签名设置允许你选择这些可选中的任何一个组合。

```
    SqlSession openSession()
    SqlSession openSession(boolean autoCommit)
    SqlSession openSession(Connection connection)
    SqlSession openSession(TransactionIsolationLevel level)
    SqlSession openSession(ExecutorType execType,TransactionIsolationLevel level)
    SqlSession openSession(ExecutorType execType)
    SqlSession openSession(ExecutorType execType, boolean autoCommit)
    SqlSession openSession(ExecutorType execType, Connection connection)
    Configuration getConfiguration();
```

默认的 openSession()方法没有参数,它会创建有如下特性的 SqlSession:

- 会开启一个事务(也就是不自动提交)
- 连接对象会从由活动环境配置的数据源实例中得到。
- 事务隔离级别将会使用驱动或数据源的默认设置。
- 预处理语句不会被复用,也不会批量处理更新。

这些方法大都可以自我解释的。 开启自动提交, "true" 传递 给可选的 autoCommit 参数。 提供自定义的连接,传递一个 Connection 实例给 connection 参数。注意没有覆盖同时设置 Connection 和 autoCommit 两者的方法,因为 MyBatis 会使用当前 connection 对象提供的设 置。 MyBatis 为事务隔离级别调用使用一个 Java 枚举包装器, 称为 TransactionIsolationLevel, 否则它们按预期的方式来工作,并有 JDBC 支持的 5 级 ( NONE,READ_UNCOMMITTED,READ_COMMITTED,REPEA TABLE_READ,SERIALIZA BLE)

还有一个可能对你来说是新见到的参数,就是 ExecutorType。这个枚举类型定义了 3 个 值:

- `ExecutorType.SIMPLE`: 这个执行器类型不做特殊的事情。它为每个语句的执行创建一个新的预处理语句。
- `ExecutorType.REUSE`: 这个执行器类型会复用预处理语句。
- `ExecutorType.BATCH`: 这个执行器会批量执行所有更新语句,如果 SELECT 在它们中间执行还会标定它们是 必须的,来保证一个简单并易于理解的行为。

注意 在 SqlSessionFactory 中还有一个方法我们没有提及,就是 getConfiguration()。这 个方法会返回一个 Configuration 实例,在运行时你可以使用它来自检 MyBatis 的配置。

注意 如果你已经使用之前版本 MyBatis,你要回忆那些 session,transaction 和 batch 都是分离的。现在和以往不同了,这些都包含在 session 的范围内了。你需要处理分开处理 事务或批量操作来得到它们的效果。

#### SqlSession

如上面所提到的,SqlSession 实例在 MyBatis 中是非常强大的一个类。在这里你会发现 所有执行语句的方法,提交或回滚事务,还有获取映射器实例。

在 SqlSession 类中有超过 20 个方法,所以将它们分开成易于理解的组合。

##### 语句执行方法

这些方法被用来执行定义在 SQL 映射的 XML 文件中的 SELECT,INSERT,UPDA E T 和 DELETE 语句。它们都会自行解释,每一句都使用语句的 ID 属性和参数对象,参数可以 是原生类型(自动装箱或包装类) ,JavaBean,POJO 或 Map。

```
     T selectOne(String statement, Object parameter)
     List selectList(String statement, Object parameter)
     Map selectMap(String statement, Object parameter, String mapKey)
    int insert(String statement, Object parameter)
    int update(String statement, Object parameter)
    int delete(String statement, Object parameter)
```

selectOne 和 selectList 的不同仅仅是 selectOne 必须返回一个对象。 如果多余一个, 或者 没有返回 (或返回了 null) 那么就会抛出异常。 , 如果你不知道需要多少对象, 使用 selectList。

如果你想检查一个对象是否存在,那么最好返回统计数(0 或 1) 。因为并不是所有语句都需 要参数,这些方法都是有不同重载版本的,它们可以不需要参数对象。

```
     T selectOne(String statement)
     List selectList(String statement)
     Map selectMap(String statement, String mapKey)
    int insert(String statement)
    int update(String statement)
    int delete(String statement)
```

最后,还有查询方法的三个高级版本,它们允许你限制返回行数的范围,或者提供自定 义结果控制逻辑,这通常用于大量的数据集合。

```
<E> List<E> selectList (String statement, Object parameter, RowBounds rowBounds)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowbounds)
void select (String statement, Object parameter, ResultHandler<T> handler)
void select (String statement, Object parameter, RowBounds rowBounds, ResultHandler<T> handler)
```

RowBounds 参数会告诉 MyBatis 略过指定数量的记录,还有限制返回结果的数量。 RowBounds 类有一个构造方法来接收 offset 和 limit,否则是不可改变的。

```
    int offset = 100;
    int limit = 25;
    RowBounds rowBounds = new RowBounds(offset, limit);
```

不同的驱动会实现这方面的不同级别的效率。对于最佳的表现,使用结果集类型的 SCROLL_SENSITIVE 或 SCROLL_INSENSITIVE(或句话说:不是 FORWARD_ONLY)。

ResultHandler 参数允许你按你喜欢的方式处理每一行。你可以将它添加到 List 中,创 建 Map, 或抛出每个结果而不是只保留总计。 Set 你可以使用 ResultHandler 做很多漂亮的事, 那就是 MyBatis 内部创建结果集列表。

它的接口很简单。

```
    package org.apache.ibatis.session;
    public interface ResultHandler {
      void handleResult(ResultContext context);
    }
```

ResultContext 参数给你访问结果对象本身的方法, 大量结果对象被创建, 你可以使用布 尔返回值的 stop()方法来停止 MyBatis 加载更多的结果。

##### 事务控制方法

控制事务范围有四个方法。 当然, 如果你已经选择了自动提交或你正在使用外部事务管 理器,这就没有任何效果了。然而,如果你正在使用 JDBC 事务管理员,由 Connection 实 例来控制,那么这四个方法就会派上用场:

```
    void commit()
    void commit(boolean force)
    void rollback()
    void rollback(boolean force)
```

默认情况下 MyBatis 不会自动提交事务, 除非它侦测到有插入, 更新或删除操作改变了 数据库。如果你已经做出了一些改变而没有使用这些方法,那么你可以传递 true 到 commit 和 rollback 方法来保证它会被提交(注意,你不能在自动提交模式下强制 session,或者使用 了外部事务管理器时) 。很多时候你不用调用 rollback(),因为如果你没有调用 commit 时 MyBatis 会替你完成。然而,如果你需要更多对多提交和回滚都可能的 session 的细粒度控 制,你可以使用回滚选择来使它成为可能。

NOTE MyBatis-Spring and MyBatis-Guice provide declarative transaction handling. So if you are using MyBatis with Spring or Guice please refer to their specific manuals.

##### 清理 Session 级的缓存

```
void clearCache()
```

SqlSession 实例有一个本地缓存在执行 update,commit,rollback 和 close 时被清理。要 明确地关闭它(获取打算做更多的工作) ,你可以调用 clearCache()。

##### 确保 SqlSession 被关闭

```
void close()
```

你必须保证的最重要的事情是你要关闭所打开的任何 session。保证做到这点的最佳方 式是下面的工作模式:

```
    SqlSession session = sqlSessionFactory.openSession();
    try {
        // following 3 lines pseudocod for "doing some work"
        session.insert(...);
        session.update(...);
        session.delete(...);
        session.commit();
    } finally {
        session.close();
    }
```

Or, If you are using jdk 1.7+ and MyBatis 3.2+, you can use the try-with-resources statement:

```
    try (SqlSession session = sqlSessionFactory.openSession()) {
        // following 3 lines pseudocode for "doing some work"
        session.insert(...);
        session.update(...);
        session.delete(...);
        session.commit();
    }
```

注意 就像 SqlSessionFactory,你可以通过调用 getConfiguration()方法获得 SqlSession 使用的 Configuration 实例

```
    Configuration getConfiguration()
```

##### 使用映射器

```
     T getMapper(Class type)
```

上述的各个 insert,update,delete 和 select 方法都很强大,但也有些繁琐,没有类型安 全,对于你的 IDE 也没有帮助,还有可能的单元测试。在上面的入门章节中我们已经看到 了一个使用映射器的示例。

因此, 一个更通用的方式来执行映射语句是使用映射器类。 一个映射器类就是一个简单 的接口,其中的方法定义匹配于 SqlSession 方法。下面的示例展示了一些方法签名和它们是 如何映射到 SqlSession 的。

```
    public interface AuthorMapper {
      // (Author) selectOne("selectAuthor",5);
      Author selectAuthor(int id);
      // (List) selectList("selectAuthors")
      List selectAuthors();
      // (Map) selectMap("selectAuthors", "id")
      @MapKey("id")
      Map selectAuthors();
      // insert("insertAuthor", author)
      int insertAuthor(Author author);
      // updateAuthor("updateAuthor", author)
      int updateAuthor(Author author);
      // delete("deleteAuthor",5)
      int deleteAuthor(int id);
    }
```

总之, 每个映射器方法签名应该匹配相关联的 SqlSession 方法, 而没有字符串参数 ID。 相反,方法名必须匹配映射语句的 ID。

此外,返回类型必须匹配期望的结果类型。所有常用的类型都是支持的,包括:原生类 型,Map,POJO 和 JavaBean。

映射器接口不需要去实现任何接口或扩展任何类。 只要方法前面可以被用来唯一标识对 应的映射语句就可以了。

映射器接口可以扩展其他接口。当使用 XML 来构建映射器接口时要保证在合适的命名 空间中有语句。 而且, 唯一的限制就是你不能在两个继承关系的接口中有相同的方法签名 (这 也是不好的想法)。

你可以传递多个参数给一个映射器方法。 如果你这样做了, 默认情况下它们将会以它们 在参数列表中的位置来命名,比如:#{param1},#{param2}等。如果你想改变参数的名称(只在多参数 情况下) ,那么你可以在参数上使用@Param("paramName")注解。

你也可以给方法传递一个 RowBounds 实例来限制查询结果。

##### 映射器注解

因为最初设计时,MyBatis 是一个 XML 驱动的框架。配置信息是基于 XML 的,而且 映射语句也是定义在 XML 中的。而到了 MyBatis 3,有新的可用的选择了。MyBatis 3 构建 在基于全面而且强大的 Java 配置 API 之上。这个配置 API 是基于 XML 的 MyBatis 配置的 基础,也是新的基于注解配置的基础。注解提供了一种简单的方式来实现简单映射语句,而 不会引入大量的开销。

注意 不幸的是,Java 注解限制了它们的表现和灵活。尽管很多时间都花调查,设计和 实验上,最强大的 MyBatis 映射不能用注解来构建,那并不可笑。C#属性(做示例)就没 有这些限制,因此 MyBatis.NET 将会比 XML 有更丰富的选择。也就是说,基于 Java 注解 的配置离不开它的特性。

**注解有下面这些:**

| 注解                                                         | 使用对象 | XML 等价形式                             | 描述                                                         |
| :----------------------------------------------------------- | :------- | :--------------------------------------- | :----------------------------------------------------------- |
| `@CacheNamespace`                                            | 类       | `<cache>`                                | 为给定的命名空间（比如类）配置缓存。属性：`implemetation`、`eviction`、`flushInterval`、`size`、`readWrite`、`blocking`、`properties`。 |
| `@Property`                                                  | N/A      | `<property>`                             | 指定参数值或占位符（`placeholder`）（该占位符能被 `mybatis-config.xml` 内的配置属性替换）。属性：`name`、`value`。（仅在 MyBatis 3.4.2 以上可用） |
| `@CacheNamespaceRef`                                         | 类       | `<cacheRef>`                             | 引用另外一个命名空间的缓存以供使用。注意，即使共享相同的全限定类名，在 [XML 映射文件](https://www.w3cschool.cn/mybatis/f4uw1ilx.html)中声明的缓存仍被识别为一个独立的命名空间。属性：`value`、`name`。如果你使用了这个注解，你应设置` value` 或者 `name` 属性的其中一个。`value `属性用于指定能够表示该命名空间的 Java 类型（命名空间名就是该 Java 类型的全限定类名），`name` 属性（这个属性仅在 MyBatis 3.4.2 以上可用）则直接指定了命名空间的名字。 |
| `@ConstructorArgs`                                           | 方法     | `<constructor>`                          | 收集一组结果以传递给一个结果对象的构造方法。属性：value，它是一个 Arg 数组。 |
| `@Arg`                                                       | N/A      | `<arg>``<idArg>`                         | ConstructorArgs 集合的一部分，代表一个构造方法参数。属性：id、column、javaType、jdbcType、typeHandler、select、resultMap。id 属性和 XML 元素 <idArg> 相似，它是一个布尔值，表示该属性是否用于唯一标识和比较对象。从版本 3.5.4 开始，该注解变为可重复注解。 |
| `@TypeDiscriminator`                                         | 方法     | `<discriminator>`                        | 决定使用何种结果映射的一组取值（case）。属性：column、javaType、jdbcType、typeHandler、cases。cases 属性是一个 Case 的数组。 |
| `@Case`                                                      | N/A      | `<case>`                                 | 表示某个值的一个取值以及该取值对应的映射。属性：value、type、results。results 属性是一个 Results 的数组，因此这个注解实际上和 ResultMap 很相似，由下面的 Results 注解指定。 |
| `@Results`                                                   | 方法     | `<resultMap>`                            | 一组结果映射，指定了对某个特定结果列，映射到某个属性或字段的方式。属性：value、id。value 属性是一个 Result 注解的数组。而 id 属性则是结果映射的名称。从版本 3.5.4 开始，该注解变为可重复注解。 |
| `@Result`                                                    | N/A      | `<result>``<id>`                         | 在列和属性或字段之间的单个结果映射。属性：id、column、javaType、jdbcType、typeHandler、one、many。id 属性和 XML 元素 <id> 相似，它是一个布尔值，表示该属性是否用于唯一标识和比较对象。one 属性是一个关联，和 <association> 类似，而 many 属性则是集合关联，和 <collection> 类似。这样命名是为了避免产生名称冲突。 |
| `@One`                                                       | N/A      | `<association>`                          | 复杂类型的单个属性映射。属性： select，指定可加载合适类型实例的映射语句（也就是映射器方法）全限定名； fetchType，指定在该映射中覆盖全局配置参数 lazyLoadingEnabled； resultMap(available since 3.5.5), which is the fully qualified name of a result map that map to a single container object from select result； columnPrefix(available since 3.5.5), which is column prefix for grouping select columns at nested result map. 提示 注解 API 不支持联合映射。这是由于 Java 注解不允许产生循环引用。 |
| `@Many`                                                      | N/A      | `<collection>`                           | 复杂类型的集合属性映射。属性： select，指定可加载合适类型实例集合的映射语句（也就是映射器方法）全限定名； fetchType，指定在该映射中覆盖全局配置参数 lazyLoadingEnabled resultMap(available since 3.5.5), which is the fully qualified name of a result map that map to collection object from select result； columnPrefix(available since 3.5.5), which is column prefix for grouping select columns at nested result map. 提示 注解 API 不支持联合映射。这是由于 Java 注解不允许产生循环引用。 |
| `@MapKey`                                                    | 方法     |                                          | 供返回值为 Map 的方法使用的注解。它使用对象的某个属性作为 key，将对象 List 转化为 Map。属性：value，指定作为 Map 的 key 值的对象属性名。 |
| `@Options`                                                   | 方法     | 映射语句的属性                           | 该注解允许你指定大部分开关和配置选项，它们通常在映射语句上作为属性出现。与在注解上提供大量的属性相比，Options 注解提供了一致、清晰的方式来指定选项。属性：useCache=true、flushCache=FlushCachePolicy.DEFAULT、resultSetType=DEFAULT、statementType=PREPARED、fetchSize=-1、timeout=-1、useGeneratedKeys=false、keyProperty=""、keyColumn=""、resultSets="", databaseId=""。注意，Java 注解无法指定 null 值。因此，一旦你使用了 Options 注解，你的语句就会被上述属性的默认值所影响。要注意避免默认值带来的非预期行为。 The databaseId(Available since 3.5.5), in case there is a configured DatabaseIdProvider, the MyBatis use the Options with no databaseId attribute or with a databaseId that matches the current one. If found with and without the databaseId the latter will be discarded.      注意：keyColumn 属性只在某些数据库中有效（如 Oracle、PostgreSQL 等）。要了解更多关于 keyColumn 和 keyProperty 可选值信息，请查看“insert, update 和 delete”一节。 |
| `@Insert``@Update``@Delete``@Select`                         | 方法     | `<insert>``<update>``<delete>``<select>` | 每个注解分别代表将会被执行的 SQL 语句。它们用字符串数组（或单个字符串）作为参数。如果传递的是字符串数组，字符串数组会被连接成单个完整的字符串，每个字符串之间加入一个空格。这有效地避免了用 Java 代码构建 SQL 语句时产生的“丢失空格”问题。当然，你也可以提前手动连接好字符串。属性：value，指定用来组成单个 SQL 语句的字符串数组。 The databaseId(Available since 3.5.5), in case there is a configured DatabaseIdProvider, the MyBatis use a statement with no databaseId attribute or with a databaseId that matches the current one. If found with and without the databaseId the latter will be discarded. |
| `@InsertProvider``@UpdateProvider``@DeleteProvider``@SelectProvider` | 方法     | `<insert>``<update>``<delete>``<select>` | 允许构建动态 SQL。这些备选的 SQL 注解允许你指定返回 SQL 语句的类和方法，以供运行时执行。（从 MyBatis 3.4.6 开始，可以使用 CharSequence 代替 String 来作为返回类型）。当执行映射语句时，MyBatis 会实例化注解指定的类，并调用注解指定的方法。你可以通过 ProviderContext 传递映射方法接收到的参数、"Mapper interface type" 和 "Mapper method"（仅在 MyBatis 3.4.5 以上支持）作为参数。（MyBatis 3.4 以上支持传入多个参数）属性：type、method。type 属性用于指定类名。method 用于指定该类的方法名（从版本 3.5.1 开始，可以省略 method 属性，MyBatis 将会使用 ProviderMethodResolver 接口解析方法的具体实现。如果解析失败，MyBatis 将会使用名为 provideSql 的降级实现）。提示 接下来的“SQL 语句构建器”一章将会讨论该话题，以帮助你以更清晰、更便于阅读的方式构建动态 SQL。 The databaseId(Available since 3.5.5), in case there is a configured DatabaseIdProvider, the MyBatis will use a provider method with no databaseId attribute or with a databaseId that matches the current one. If found with and without the databaseId the latter will be discarded. |
| `@Param`                                                     | 参数     | N/A                                      | 如果你的映射方法接受多个参数，就可以使用这个注解自定义每个参数的名字。否则在默认情况下，除 RowBounds 以外的参数会以 "param" 加参数位置被命名。例如 #{param1}, #{param2}。如果使用了 @Param("person")，参数就会被命名为 #{person}。 |
| `@SelectKey`                                                 | 方法     | `<selectKey>`                            | 这个注解的功能与 <selectKey> 标签完全一致。该注解只能在 @Insert 或 @InsertProvider 或 @Update 或 @UpdateProvider 标注的方法上使用，否则将会被忽略。如果标注了 @SelectKey 注解，MyBatis 将会忽略掉由 @Options 注解所设置的生成主键或设置（configuration）属性。属性：statement 以字符串数组形式指定将会被执行的 SQL 语句，keyProperty 指定作为参数传入的对象对应属性的名称，该属性将会更新成新的值，before 可以指定为 true 或 false 以指明 SQL 语句应被在插入语句的之前还是之后执行。resultType 则指定 keyProperty 的 Java 类型。statementType 则用于选择语句类型，可以选择 STATEMENT、PREPARED 或 CALLABLE 之一，它们分别对应于 Statement、PreparedStatement 和 CallableStatement。默认值是 PREPARED。 The databaseId(Available since 3.5.5), in case there is a configured DatabaseIdProvider, the MyBatis will use a statement with no databaseId attribute or with a databaseId that matches the current one. If found with and without the databaseId the latter will be discarded. |
| `@ResultMap`                                                 | 方法     | N/A                                      | 这个注解为 @Select 或者 @SelectProvider 注解指定 XML 映射中 <resultMap> 元素的 id。这使得注解的 select 可以复用已在 XML 中定义的 ResultMap。如果标注的 select 注解中存在 @Results 或者 @ConstructorArgs 注解，这两个注解将被此注解覆盖。 |
| `@ResultType`                                                | 方法     | N/A                                      | 在使用了结果处理器的情况下，需要使用此注解。由于此时的返回类型为 void，所以 Mybatis 需要有一种方法来判断每一行返回的对象类型。如果在 XML 有对应的结果映射，请使用 @ResultMap 注解。如果结果类型在 XML 的 <select> 元素中指定了，就不需要使用其它注解了。否则就需要使用此注解。比如，如果一个标注了 @Select 的方法想要使用结果处理器，那么它的返回类型必须是 void，并且必须使用这个注解（或者 @ResultMap）。这个注解仅在方法返回类型是 void 的情况下生效。 |
| `@Flush`                                                     | 方法     | N/A                                      | 如果使用了这个注解，定义在 Mapper 接口中的方法就能够调用 SqlSession#flushStatements() 方法。（Mybatis 3.3 以上可用） |



映射申明样例

这个例子展示了如何使用 @SelectKey 注解来在插入前读取数据库序列的值：

```
@Insert("insert into table3 (id, name) values(#{nameId}, #{name})")
@SelectKey(statement="call next value for TestSequence", keyProperty="nameId", before=true, resultType=int.class)
int insertTable3(Name name);
```

这个例子展示了如何使用 @SelectKey 注解来在插入后读取数据库识别列的值：

```
@Insert("insert into table2 (name) values(#{name})")
@SelectKey(statement="call identity()", keyProperty="nameId", before=false, resultType=int.class)
int insertTable2(Name name);
```

# 6、MyBatis SQL语句构建器

2021-07-26 16:57 更新

## SQL语句构建器 

### 问题

Java程序员面对的最痛苦的事情之一就是在 Java 代码中嵌入 SQL 语句。这么来做通常是由于 SQL 语句需要动态来生成-否则可以将它们放到外部文件或者存储过程中。正如你已经看到的那样，MyBatis 在它的 XML 映射特性中有一个强大的动态 SQL 生成方案。但有时在 Java 代码内部创建SQL语句也是必要的。此时， MyBatis 有另外一个特性可以帮到你，在减少典型的加号,引号,新行,格式化问题和嵌入条件来处理多余的逗号或 AND 连接词之前。事实上，在 Java 代码中来动态生成 SQL 代码就是一场噩梦。例如：

```
    String sql = "SELECT P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME, "
    "P.LAST_NAME,P.CREATED_ON, P.UPDATED_ON " +
    "FROM PERSON P, ACCOUNT A " +
    "INNER JOIN DEPARTMENT D on D.ID = P.DEPARTMENT_ID " +
    "INNER JOIN COMPANY C on D.COMPANY_ID = C.ID " +
    "WHERE (P.ID = A.ID AND P.FIRST_NAME like ?) " +
    "OR (P.LAST_NAME like ?) " +
    "GROUP BY P.ID " +
    "HAVING (P.LAST_NAME like ?) " +
    "OR (P.FIRST_NAME like ?) " +
    "ORDER BY P.ID, P.FULL_NAME";
```

### 解决方案

MyBatis 3 提供了方便的工具类来帮助解决该问题。使用 SQL 类，简单地创建一个实例来调用方法生成SQL语句。上面示例中的问题就像重写 SQL 类那样：

```
    private String selectPersonSql() {
      return new SQL() {{
        SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
        SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
        FROM("PERSON P");
        FROM("ACCOUNT A");
        INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
        INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
        WHERE("P.ID = A.ID");
        WHERE("P.FIRST_NAME like ?");
        OR();
        WHERE("P.LAST_NAME like ?");
        GROUP_BY("P.ID");
        HAVING("P.LAST_NAME like ?");
        OR();
        HAVING("P.FIRST_NAME like ?");
        ORDER_BY("P.ID");
        ORDER_BY("P.FULL_NAME");
      }}.toString();
    }
```

该例中有什么特殊之处？当你仔细看时，那不用担心偶然间重复出现的"AND"关键字，或者在"WHERE"和"AND"之间的选择，抑或什么都不选。该SQL类非常注意"WHERE"应该出现在何处，哪里又应该使用"AND"，还有所有的字符串链接。

### SQL类

这里给出一些示例：

```
    // Anonymous inner class
    public String deletePersonSql() {
      return new SQL() {{
        DELETE_FROM("PERSON");
        WHERE("ID = ${id}");
      }}.toString();
    }

    // Builder / Fluent style
    public String insertPersonSql() {
      String sql = new SQL()
        .INSERT_INTO("PERSON")
        .VALUES("ID, FIRST_NAME", "${id}, ${firstName}")
        .VALUES("LAST_NAME", "${lastName}")
        .toString();
      return sql;
    }

    // With conditionals (note the final parameters, required for the anonymous inner class to access them)
    public String selectPersonLike(final String id, final String firstName, final String lastName) {
      return new SQL() {{
        SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FIRST_NAME, P.LAST_NAME");
        FROM("PERSON P");
        if (id != null) {
          WHERE("P.ID like ${id}");
        }
        if (firstName != null) {
          WHERE("P.FIRST_NAME like ${firstName}");
        }
        if (lastName != null) {
          WHERE("P.LAST_NAME like ${lastName}");
        }
        ORDER_BY("P.LAST_NAME");
      }}.toString();
    }

    public String deletePersonSql() {
      return new SQL() {{
        DELETE_FROM("PERSON");
        WHERE("ID = ${id}");
      }}.toString();
    }

    public String insertPersonSql() {
      return new SQL() {{
        INSERT_INTO("PERSON");
        VALUES("ID, FIRST_NAME", "${id}, ${firstName}");
        VALUES("LAST_NAME", "${lastName}");
      }}.toString();
    }

    public String updatePersonSql() {
      return new SQL() {{
        UPDATE("PERSON");
        SET("FIRST_NAME = ${firstName}");
        WHERE("ID = ${id}");
      }}.toString();
    }
```

###  

| 方法                                                         | 描述                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `SELECT(String)`                                             | 开始新的或追加到已有的 `SELECT`子句。可以被多次调用，参数会被追加到 `SELECT` 子句。 参数通常使用逗号分隔的列名和别名列表，但也可以是数据库驱动程序接受的任意参数。 |
| `SELECT_DISTINCT(String)`                                    | 开始新的或追加到已有的 `SELECT`子句，并添加 `DISTINCT` 关键字到生成的查询中。可以被多次调用，参数会被追加到 `SELECT` 子句。 参数通常使用逗号分隔的列名和别名列表，但也可以是数据库驱动程序接受的任意参数。 |
| `FROM(String)`                                               | 开始新的或追加到已有的 `FROM`子句。可以被多次调用，参数会被追加到 `FROM`子句。 参数通常是一个表名或别名，也可以是数据库驱动程序接受的任意参数。 |
| `JOIN(String)，INNER_JOIN(String)，LEFT_OUTER_JOIN(String)，RIGHT_OUTER_JOIN(String)` | 基于调用的方法，添加新的合适类型的 `JOIN` 子句。 参数可以包含一个由列和连接条件构成的标准连接。 |
| `WHERE(String)`                                              | 插入新的 `WHERE` 子句条件，并使用 `AND` 拼接。可以被多次调用，对于每一次调用产生的新条件，会使用 `AND` 拼接起来。要使用 `OR` 分隔，请使用 `OR()`。 |
| `OR()`                                                       | 使用 `OR` 来分隔当前的 `WHERE` 子句条件。 可以被多次调用，但在一行中多次调用会生成错误的 `SQL`。 |
| `AND()`                                                      | 使用 `AND` 来分隔当前的 `WHERE`子句条件。 可以被多次调用，但在一行中多次调用会生成错误的 `SQL`。由于 `WHERE` 和 `HAVING`都会自动使用 `AND` 拼接, 因此这个方法并不常用，只是为了完整性才被定义出来。 |
| `GROUP_BY(String)`                                           | 追加新的 `GROUP BY` 子句，使用逗号拼接。可以被多次调用，每次调用都会使用逗号将新的条件拼接起来。 |
| `HAVING(String)`                                             | 追加新的 `HAVING` 子句。使用` AND` 拼接。可以被多次调用，每次调用都使用`AND`来拼接新的条件。要使用 `OR` 分隔，请使用 `OR()`。 |
| `ORDER_BY(String)`                                           | 追加新的 `ORDER BY` 子句，使用逗号拼接。可以多次被调用，每次调用会使用逗号拼接新的条件。 |
| `LIMIT(String)``LIMIT(int)`                                  | 追加新的 `LIMIT` 子句。 仅在 `SELECT()`、`UPDATE()`、`DELETE()` 时有效。 当在 `SELECT()` 中使用时，应该配合 `OFFSET()` 使用。（于 3.5.2 引入） |
| `OFFSET(String)``OFFSET(long)`                               | 追加新的 `OFFSET` 子句。 仅在 `SELECT()` 时有效。 当在 `SELECT()` 时使用时，应该配合 `LIMIT()` 使用。（于 3.5.2 引入） |
| `OFFSET_ROWS(String)``OFFSET_ROWS(long)`                     | 追加新的 `OFFSET n ROWS` 子句。 仅在 `SELECT()` 时有效。 该方法应该配合 `FETCH_FIRST_ROWS_ONLY()` 使用。（于 3.5.2 加入） |
| `FETCH_FIRST_ROWS_ONLY(String)``FETCH_FIRST_ROWS_ONLY(int)`  | 追加新的 `FETCH FIRST n ROWS ONLY` 子句。 仅在 `SELECT()` 时有效。 该方法应该配合 `OFFSET_ROWS()` 使用。（于 3.5.2 加入） |
| `DELETE_FROM(String)`                                        | 开始新的 `delete` 语句，并指定删除表的表名。通常它后面都会跟着一个` WHERE` 子句！ |
| `INSERT_INTO(String)`                                        | 开始新的 `insert` 语句，并指定插入数据表的表名。后面应该会跟着一个或多个 `VALUES() `调用，或 `INTO_COLUMNS()` 和 `INTO_VALUES()` 调用。 |
| `SET(String)`                                                | 对 `update` 语句追加 `"set"` 属性的列表                      |
| `UPDATE(String)`                                             | 开始新的 `update` 语句，并指定更新表的表名。后面都会跟着一个或多个 `SET()` 调用，通常也会有一个 `WHERE()` 调用。 |
| `VALUES(String, String)`                                     | 追加数据值到 `insert` 语句中。第一个参数是数据插入的列名，第二个参数则是数据值。 |
| `INTO_COLUMNS(String...)`                                    | 追加插入列子句到 `insert` 语句中。应与 `INTO_VALUES()` 一同使用。 |
| `INTO_VALUES(String...)`                                     | 追加插入值子句到 `insert` 语句中。应与 `INTO_COLUMNS()` 一同使用。 |
| `ADD_ROW()`                                                  | 添加新的一行数据，以便执行批量插入。（于 3.5.2 引入）        |

提示 注意，SQL 类将原样插入 `LIMIT`、`OFFSET`、`OFFSET n ROWS` 以及 `FETCH FIRST n ROWS ONLY `子句。换句话说，类库不会为不支持这些子句的数据库执行任何转换。 因此，用户应该要了解目标数据库是否支持这些子句。如果目标数据库不支持这些子句，产生的 SQL 可能会引起运行错误。



从版本 3.4.2 开始，你可以像下面这样使用可变长度参数：

```
public String selectPersonSql() {
  return new SQL()
    .SELECT("P.ID", "A.USERNAME", "A.PASSWORD", "P.FULL_NAME", "D.DEPARTMENT_NAME", "C.COMPANY_NAME")
    .FROM("PERSON P", "ACCOUNT A")
    .INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID", "COMPANY C on D.COMPANY_ID = C.ID")
    .WHERE("P.ID = A.ID", "P.FULL_NAME like #{name}")
    .ORDER_BY("P.ID", "P.FULL_NAME")
    .toString();
}

public String insertPersonSql() {
  return new SQL()
    .INSERT_INTO("PERSON")
    .INTO_COLUMNS("ID", "FULL_NAME")
    .INTO_VALUES("#{id}", "#{fullName}")
    .toString();
}

public String updatePersonSql() {
  return new SQL()
    .UPDATE("PERSON")
    .SET("FULL_NAME = #{fullName}", "DATE_OF_BIRTH = #{dateOfBirth}")
    .WHERE("ID = #{id}")
    .toString();
}
```



- 从版本 3.5.2 开始，你可以像下面这样构建批量插入语句：

```
public String insertPersonsSql() {
  // INSERT INTO PERSON (ID, FULL_NAME)
  //     VALUES (#{mainPerson.id}, #{mainPerson.fullName}) , (#{subPerson.id}, #{subPerson.fullName})
  return new SQL()
    .INSERT_INTO("PERSON")
    .INTO_COLUMNS("ID", "FULL_NAME")
    .INTO_VALUES("#{mainPerson.id}", "#{mainPerson.fullName}")
    .ADD_ROW()
    .INTO_VALUES("#{subPerson.id}", "#{subPerson.fullName}")
    .toString();
}
```



从版本 3.5.2 开始，你可以像下面这样构建限制返回结果数的 `SELECT` 语句,：

```
public String selectPersonsWithOffsetLimitSql() {
  // SELECT id, name FROM PERSON
  //     LIMIT #{limit} OFFSET #{offset}
  return new SQL()
    .SELECT("id", "name")
    .FROM("PERSON")
    .LIMIT("#{limit}")
    .OFFSET("#{offset}")
    .toString();
}

public String selectPersonsWithFetchFirstSql() {
  // SELECT id, name FROM PERSON
  //     OFFSET #{offset} ROWS FETCH FIRST #{limit} ROWS ONLY
  return new SQL()
    .SELECT("id", "name")
    .FROM("PERSON")
    .OFFSET_ROWS("#{offset}")
    .FETCH_FIRST_ROWS_ONLY("#{limit}")
    .toString();
}
```

### SqlBuilder 和 SelectBuilder (已经废弃)

在3.2版本之前，我们使用了一点不同的做法，通过实现`ThreadLocal`变量来掩盖一些导致`Java DSL`麻烦的语言限制。但这种方式已经废弃了，现代的框架都欢迎人们使用构建器类型和匿名内部类的想法。因此，`SelectBuilder` 和 `SqlBuilder` 类都被废弃了。

下面的方法仅仅适用于废弃的`SqlBuilder` 和 `SelectBuilder` 类。

| 方法                  | 描述                                                         |
| --------------------- | ------------------------------------------------------------ |
| `BEGIN()` / `RESET()` | 这些方法清空`SelectBuilder`类的`ThreadLocal`状态，并且准备一个新的构建语句。开始新的语句时， `BEGIN()`读取得最好。 由于一些原因（在某些条件下，也许是逻辑需要一个完全不同的语句），在执行中清理语句 `RESET()`读取得最好。 |
| `SQL()`               | 返回生成的 `SQL()` 并重置 `SelectBuilder` 状态 (好像 `BEGIN()` 或 `RESET()` 被调用了). 因此，该方法只能被调用一次！ |

`SelectBuilder` 和 `SqlBuilder` 类并不神奇，但是知道它们如何工作也是很重要的。` SelectBuilder` 使用 `SqlBuilder `使用了静态导入和`ThreadLocal`变量的组合来开启整洁语法，可以很容易地和条件交错。使用它们，静态导入类的方法即可，就像这样(一个或其它，并非两者):

```
    import static org.apache.ibatis.jdbc.SelectBuilder.*;
    import static org.apache.ibatis.jdbc.SqlBuilder.*;
```

这就允许像下面这样来创建方法：

```
    /* DEPRECATED */
    public String selectBlogsSql() {
      BEGIN(); // Clears ThreadLocal variable
      SELECT("*");
      FROM("BLOG");
      return SQL();
    }
    /* DEPRECATED */
    private String selectPersonSql() {
      BEGIN(); // Clears ThreadLocal variable
      SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
      SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
      FROM("PERSON P");
      FROM("ACCOUNT A");
      INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
      INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
      WHERE("P.ID = A.ID");
      WHERE("P.FIRST_NAME like ?");
      OR();
      WHERE("P.LAST_NAME like ?");
      GROUP_BY("P.ID");
      HAVING("P.LAST_NAME like ?");
      OR();
      HAVING("P.FIRST_NAME like ?");
      ORDER_BY("P.ID");
      ORDER_BY("P.FULL_NAME");
      return SQL();
    }
```



# 7、MyBatis 日志

2018-08-12 21:20 更新

## Logging

Mybatis内置的日志工厂提供日志功能，具体的日志实现有以下几种工具：

- SLF4J
- Apache Commons Logging
- Log4j 2
- Log4j
- JDK logging

具体选择哪个日志实现工具由MyBatis的内置日志工厂确定。它会使用最先找到的（按上文列举的顺序查找）。 如果一个都未找到，日志功能就会被禁用。

不少应用服务器的classpath中已经包含Commons Logging，如Tomcat和WebShpere， 所以MyBatis会把它作为具体的日志实现。记住这点非常重要。这将意味着，在诸如 WebSphere的环境中——WebSphere提供了Commons Logging的私有实现，你的Log4J配置将被忽略。 这种做法不免让人悲催，MyBatis怎么能忽略你的配置呢？事实上，因Commons Logging已经存 在了，按照优先级顺序，Log4J自然就被忽略了！不过，如果你的应用部署在一个包含Commons Logging的环境， 而你又想用其他的日志框架，你可以根据需要调用如下的某一方法：

```
org.apache.ibatis.logging.LogFactory.useSlf4jLogging();
org.apache.ibatis.logging.LogFactory.useLog4JLogging();
org.apache.ibatis.logging.LogFactory.useJdkLogging();
org.apache.ibatis.logging.LogFactory.useCommonsLogging();
org.apache.ibatis.logging.LogFactory.useStdOutLogging();
```

如果的确需要调用以上的某个方法，请在其他所有MyBatis方法之前调用它。另外，只有在相应日志实现中存在 的前提下，调用对应的方法才是有意义的，否则MyBatis一概忽略。如你环境中并不存在Log4J，你却调用了 相应的方法，MyBatis就会忽略这一调用，代之默认的查找顺序查找日志实现。

关于SLF4J、Apache Commons Logging、Apache Log4J和JDK Logging的API介绍已经超出本文档的范围。 不过，下面的例子可以作为一个快速入门。关于这些日志框架的更多信息，可以参考以下链接：

- [Apache Commons Logging](http://commons.apache.org/proper/commons-logging/)
- [Apache Log4j](http://logging.apache.org/log4j/2.x/)
- [JDK Logging API](http://www.oracle.com/technetwork/java/index.html)

## Logging Configuration

MyBatis可以对包、类、命名空间和全限定的语句记录日志。

具体怎么做，视使用的日志框架而定，这里以Log4J为例。配置日志功能非常简单：添加几个配置文件， 如log4j.properties,再添加个jar包，如log4j.jar。下面是具体的例子，共两个步骤：

#### 步骤1： 添加 Log4J 的 jar 包

因为采用Log4J，要确保在应用中对应的jar包是可用的。要满足这一点，只要将jar包添加到应用的classpath中即可。 Log4J的jar包可以从上面的链接中下载。

具体而言，对于web或企业应用，需要将`log4j.jar` 添加到`WEB-INF/lib` 目录； 对于独立应用， 可以将它添加到jvm的 `-classpath`启动参数中。

#### 步骤2：配置Log4J

配置Log4J比较简单， 比如需要记录这个mapper接口的日志:

```
    package org.mybatis.example;
    public interface BlogMapper {
      @Select("SELECT * FROM blog WHERE id = #{id}")
      Blog selectBlog(int id);
    }
```

只要在应用的classpath中创建一个名称为`log4j.properties`的文件， 文件的具体内容如下：

```
    # Global logging configuration
    log4j.rootLogger=ERROR, stdout
    # MyBatis logging configuration...
    log4j.logger.org.mybatis.example.BlogMapper=TRACE
    # Console output...
    log4j.appender.stdout=org.apache.log4j.ConsoleAppender
    log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

添加以上配置后，Log4J就会把 `org.mybatis.example.BlogMapper` 的详细执行日志记录下来，对于应用中的其它类则仅仅记录错误信息。

也可以将日志从整个mapper接口级别调整到到语句级别，从而实现更细粒度的控制。如下配置只记录 `selectBlog` 语句的日志：

```
log4j.logger.org.mybatis.example.BlogMapper.selectBlog=TRACE
```

与此相对，可以对一组mapper接口记录日志，只要对mapper接口所在的包开启日志功能即可：

```
    log4j.logger.org.mybatis.example=TRACE
```

某些查询可能会返回大量的数据，只想记录其执行的SQL语句该怎么办？为此，Mybatis中SQL语 句的日志级别被设为DEBUG（JDK Logging中为FINE），结果日志的级别为TRACE（JDK Logging中为FINER)。所以，只要将日志级别调整为DEBUG即可达到目的：

```
    log4j.logger.org.mybatis.example=DEBUG
```

要记录日志的是类似下面的mapper文件而不是mapper接口又该怎么呢？

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.mybatis.example.BlogMapper">
  <select id="selectBlog" resultType="Blog">
    select * from Blog where id = #{id}
  </select>
</mapper>
```

对这个文件记录日志，只要对命名空间增加日志记录功能即可：

```
    log4j.logger.org.mybatis.example.BlogMapper=TRACE
```

进一步，要记录具体语句的日志可以这样做：

```
log4j.logger.org.mybatis.example.BlogMapper.selectBlog=TRACE
```

看到了吧，两种配置没差别！

配置文件`log4j.properties`的余下内容是针对日志格式的，这一内容已经超出本 文档范围。关于Log4J的更多内容，可以参考Log4J的网站。不过，可以简单试一下看看，不同的配置 会产生什么不一样的效果。

# 8、MyBatis 3.5.7 新特性

2021-07-28 15:04 更新

当前mybatis的最新版本是 mybatis 3.5.7，发布时间是2021年4月26日，官方下载链接为：[mybatis-3-releases](https://github.com/mybatis/mybatis-3/releases)

## mybatis 3.5.7 的使用

要使用最新版本MyBatis， 只需将 mybatis-3.5.7.jar 文件置于类路径（classpath）中即可。

如果使用 Maven 来构建项目，则需将下面的依赖代码置于 pom.xml 文件中：

```
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.5.7</version>
</dependency>
```

## mybatis 3.5.7 新特征

此版本的mybatis与上一个版本没有什么变化，只是修改了若干bug，完全兼容mybatis 3.5.6。

对此结果，站长表示皆大欢喜，否则新特性的出现必将增加我们Java码农的学习成本。



# 9、MyBatis常见问题合集

2020-11-04 13:35 更新

总结一些基础的常见的Mybatis问题，方便自己，方便读者学习，内容不多

## 1、大于号、小于号在 sql 语句中的转换　　

　　<font color = Tomato size=3 face="楷书">使用 mybatis 时 sql 语句是写在 xml 文件中,如果 sql 中有一些特殊的字符的话,比如< ，<=，>，>=等符号，会引起 xml 格式的错误，需要替换掉，或者不被转义。 有两种方法可以解决：转义字符和标记 CDATA 块。</font>

方式1、转义字符

```
1 <select id="searchByPrice" parameterType="Map" resultType="Product">
2     <!-- 方式1、转义字符 -->
3     select * from Product where price &gt;= #{minPrice} and price &lt;= #{maxPrice}
4 </select>
```

方式2、标记 CDATA

```
1 <select id="searchByPrice" parameterType="Map" resultType="Product">
2   <!-- 方式2、CDATA -->
3   <![CDATA[select * from Product where price >= #{minPrice} and price <= #{maxPrice} ]]>
4 </select>
```

转义字符表

| 转义 | 符号 |
| ---- | ---- |
| `<`  | <    |
| `>`  | >    |
| `&`  | &    |
| `'`  | ’    |
| `"`  | “    |

## 2、MyBatis 中的 resultType 和 resultMap

　　网上的总结很多，简单而言，resultType 用于返回值只有一个字段的类型，resultMap 用于返回值有多个字段的类型。至于结果是 List 还是一个，则在 Mapper 中定义返回值是List还是单个。

使用 resultType：

```
1 <select id="count" resultType="java.lang.Integer">  
2         SELECT count(*) FROM USER  
3 </select>  
```

使用 resultMap：

```
 1 <resultMap type="com.liulanghan.Blog" id="BlogResult">    
 2     <id column="id" property="id"/>    
 3     <result column="title" property="title"/>    
 4     <result column="content" property="content"/>    
 5     <result column="owner" property="owner"/>    
 6 </resultMap>   
 7    
 8 <select id="selectBlog" parameterType="int" resultMap="BlogResult">    
 9       select * from t_blog where id = #{id}    
10 </select>  
```

## 3、参数

Mapper 中需要用 @Param("queryDate")定义参数名称，sql 中用#{queryDate}使用参数，字符串也不需要引号。

参数判断和if的用法：

```
1 <if test="queryDate != null">
2     and queryDate >= #{queryDate}
3 </if>
```

when otherwise 就是 if else

```
1 <choose>
2      <when test="isDelete != null and isDelete == 0">
3           isDelete=0
4       </when>
5       <otherwise>
6           isDelete=1
7        </otherwise>
8 </choose>
```

如果要判断的字符串，则需要加引号

```
1 <when test="gender != null and gender == 'MALE'">
2     gender='MALE'
3 </when>
```

## 4.传入参数参数为0查询条件失效

#### 4.1 场景再现

场景是这样的，需要做一个对账单查询，可以按金额范围进行查询，页面参数写完之后进行条件，输入0测试了无数次均失效。

#### 4.2 原因解析

<font color = Tomato size=3 face="楷书">当页面参数为 0，传入到 mybatis 的 xml 中后，如果不是字符串，需指定数据类型，否则会被误认为 null</font>

```
<if test="data.tatalAmount != null and data.totalAmount !='' ">
and total_Amount=#{data.totalAmount}
</if>123
```

这种情况如果 totalAmount 为 0 时将被误认为是 null，里面的条件不会执行。正确的姿势如下

方法1.添加 0 判断

```
<if test="data.tatalAmount != null and data.totalAmount !='' or tatalAmount==0 ">
and total_Amount=#{data.totalAmount}
</if>123
```

方法2.规定传入参数的类型

```
<if test="data.tatalAmount != null and data.totalAmount !='' ">
and total_Amount=#{data.totalAmount,jdbc.Type=DECIMAL}
</if>
```

# MyBatis和ORM的区别

2020-09-09 17:43 更新

**mybatis属于半orm，因为sql语句需要自己写。**



与其他比较标准的 ORM 框架（比如 Hibernate ）不同， mybatis 并没有将 [java](https://www.w3cschool.cn/java/) 对象与数据库关联起来，而是将 [java](https://www.w3cschool.cn/java/) 方法与 [sql](https://www.w3cschool.cn/sql/) 语句关联起来，mybatis 允许用户充分利用数据库的各种功能，例如存储、视图、各种复杂的查询以及某些数据库的专有特性。



自己写 [sql](https://www.w3cschool.cn/sql/) 语句的好处是，可以根据自己的需求，写出最优的 [sql](https://www.w3cschool.cn/sql/) 语句。灵活性高。但是，由于是自己写 [sql](https://www.w3cschool.cn/sql/) 语句，导致平台可移植性不高。[MySQL](https://www.w3cschool.cn/mysql/) 语句和 [Oracle](https://www.w3cschool.cn/oraclejc/) 语句不同

# MyBatis实现分页功能

2020-09-09 17:28 更新

\1. **原始方法，使用 limit**，需要自己处理分页逻辑：

对于 [mysql](https://www.w3cschool.cn/mysql/) 数据库可以使用 limit ，如：

```
select * from table limit 5; --返回前5行

select * from table limit 0,5; --同上，返回前5行

select * from table limit 5,10; --返回6-15行
```

对于 [oracle](https://www.w3cschool.cn/oraclejc/) 数据库可以使用 rownum ，如：

--如：从表Sys_option（主键为sys_id)中从第10条记录开始检索20条记录，语句如下

```
SELECT * FROM (SELECT ROWNUM R,t1.* From Sys_option where rownum < 30 ) t2

Where t2.R >= 10
```



\2. **拦截StatementHandler**，其实质还是在最后生成limit语句

详见：www.cnblogs.com/jcli/archive/2011/08/09/2132222.html



\3. **使用PageHelper插件**，这是目前比较常见的方法：

由于篇幅太长详细过程看原文：www.cnblogs.com/digdeep/p/4608933.html



# Mybatis四种分页方式

2020-10-10 16:16 更新

## 数组分页

查询出全部数据，然后再list中截取需要的部分。

mybatis接口

```
List<Student> queryStudentsByArray();
```

xml配置文件

```
 <select id="queryStudentsByArray"  resultMap="studentmapper">
        select * from student
 </select>
```

service

```
接口
List<Student> queryStudentsByArray(int currPage, int pageSize);
实现接口
 @Override
    public List<Student> queryStudentsByArray(int currPage, int pageSize) {
        //查询全部数据
        List<Student> students = studentMapper.queryStudentsByArray();
        //从第几条数据开始
        int firstIndex = (currPage - 1) * pageSize;
        //到第几条数据结束
        int lastIndex = currPage * pageSize;
        return students.subList(firstIndex, lastIndex); //直接在list中截取
    }
```

controller

```
    @ResponseBody
    @RequestMapping("/student/array/{currPage}/{pageSize}")
    public List<Student> getStudentByArray(@PathVariable("currPage") int currPage, @PathVariable("pageSize") int pageSize) {
        List<Student> student = StuServiceIml.queryStudentsByArray(currPage, pageSize);
        return student;
    }
```

## sql分页

mybatis接口

```
List<Student> queryStudentsBySql(Map<String,Object> data);
```

xml文件

```
<select id="queryStudentsBySql" parameterType="map" resultMap="studentmapper">
        select * from student limit #{currIndex} , #{pageSize}
</select>
```

service

```
接口
List<Student> queryStudentsBySql(int currPage, int pageSize);
实现类
public List<Student> queryStudentsBySql(int currPage, int pageSize) {
        Map<String, Object> data = new HashedMap();
        data.put("currIndex", (currPage-1)*pageSize);
        data.put("pageSize", pageSize);
        return studentMapper.queryStudentsBySql(data);
    }
```

## 拦截器分页

创建拦截器，拦截mybatis接口方法id以ByPage结束的语句

```
package com.autumn.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @Intercepts 说明是一个拦截器
 * @Signature 拦截器的签名
 * type 拦截的类型 四大对象之一( Executor,ResultSetHandler,ParameterHandler,StatementHandler)
 * method 拦截的方法
 * args 参数,高版本需要加个Integer.class参数,不然会报错
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class MyPageInterceptor implements Interceptor {

    //每页显示的条目数
    private int pageSize;
    //当前现实的页数
    private int currPage;
    //数据库类型
    private String dbType;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取StatementHandler，默认是RoutingStatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //获取statementHandler包装类
        MetaObject MetaObjectHandler = SystemMetaObject.forObject(statementHandler);

        //分离代理对象链
        while (MetaObjectHandler.hasGetter("h")) {
            Object obj = MetaObjectHandler.getValue("h");
            MetaObjectHandler = SystemMetaObject.forObject(obj);
        }

        while (MetaObjectHandler.hasGetter("target")) {
            Object obj = MetaObjectHandler.getValue("target");
            MetaObjectHandler = SystemMetaObject.forObject(obj);
        }

        //获取连接对象
        //Connection connection = (Connection) invocation.getArgs()[0];


        //object.getValue("delegate");  获取StatementHandler的实现类

        //获取查询接口映射的相关信息
        MappedStatement mappedStatement = (MappedStatement) MetaObjectHandler.getValue("delegate.mappedStatement");
        String mapId = mappedStatement.getId();

        //statementHandler.getBoundSql().getParameterObject();

        //拦截以.ByPage结尾的请求，分页功能的统一实现
        if (mapId.matches(".+ByPage$")) {
            //获取进行数据库操作时管理参数的handler
            ParameterHandler parameterHandler = (ParameterHandler) MetaObjectHandler.getValue("delegate.parameterHandler");
            //获取请求时的参数
            Map<String, Object> paraObject = (Map<String, Object>) parameterHandler.getParameterObject();
            //也可以这样获取
            //paraObject = (Map<String, Object>) statementHandler.getBoundSql().getParameterObject();

            //参数名称和在service中设置到map中的名称一致
            currPage = (int) paraObject.get("currPage");
            pageSize = (int) paraObject.get("pageSize");

            String sql = (String) MetaObjectHandler.getValue("delegate.boundSql.sql");
            //也可以通过statementHandler直接获取
            //sql = statementHandler.getBoundSql().getSql();

            //构建分页功能的sql语句
            String limitSql;
            sql = sql.trim();
            limitSql = sql + " limit " + (currPage - 1) * pageSize + "," + pageSize;

            //将构建完成的分页sql语句赋值个体'delegate.boundSql.sql'，偷天换日
            MetaObjectHandler.setValue("delegate.boundSql.sql", limitSql);
        }
        //调用原对象的方法，进入责任链的下一级
        return invocation.proceed();
    }


    //获取代理对象
    @Override
    public Object plugin(Object o) {
        //生成object对象的动态代理对象
        return Plugin.wrap(o, this);
    }

    //设置代理对象的参数
    @Override
    public void setProperties(Properties properties) {
        //如果项目中分页的pageSize是统一的，也可以在这里统一配置和获取，这样就不用每次请求都传递pageSize参数了。参数是在配置拦截器时配置的。
        String limit1 = properties.getProperty("limit", "10");
        this.pageSize = Integer.valueOf(limit1);
        this.dbType = properties.getProperty("dbType", "mysql");
    }
}
```

配置文件SqlMapConfig.xml

```
<configuration>

    <plugins>
        <plugin interceptor="com.autumn.interceptor.MyPageInterceptor">
            <property name="limit" value="10"/>
            <property name="dbType" value="mysql"/>
        </plugin>
    </plugins>

</configuration>
```

mybatis配置

```
<!--接口-->
List<AccountExt> getAllBookByPage(@Param("currPage")Integer pageNo,@Param("pageSize")Integer pageSize);
<!--xml配置文件-->
  <sql id="getAllBooksql" >
    acc.id, acc.cateCode, cate_name, user_id,u.name as user_name, money, remark, time
  </sql>
  <select id="getAllBook" resultType="com.autumn.pojo.AccountExt" >
    select
    <include refid="getAllBooksql" />
    from account as acc
  </select>
```

service

```
    public List<AccountExt> getAllBookByPage(String pageNo,String pageSize) {
        return accountMapper.getAllBookByPage(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
    }
```

controller

```
    @RequestMapping("/getAllBook")
    @ResponseBody
    public Page getAllBook(String pageNo,String pageSize,HttpServletRequest request,HttpServletResponse response){
        pageNo=pageNo==null?"1":pageNo;   //当前页码
        pageSize=pageSize==null?"5":pageSize;   //页面大小
        //获取当前页数据
        List<AccountExt> list = bookService.getAllBookByPage(pageNo,pageSize);
        //获取总数据大小
        int totals = bookService.getAllBook();
        //封装返回结果
        Page page = new Page();
        page.setTotal(totals+"");
        page.setRows(list);
        return page;
    }
```

Page实体类

```
package com.autumn.pojo;

import java.util.List;

/**
 * Created by Autumn on 2018/6/21.
 */
public class Page {
    private String pageNo = null;
    private String pageSize = null;
    private String total = null;
    private List rows = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
```

前端

bootstrap-table接受数据格式

```
{
  "total": 3,
  "rows": [
    {
      "id": 0,
      "name": "Item 0",
      "price": "$0"
    },
    {
      "id": 1,
      "name": "Item 1",
      "price": "$1"
    }
  ]
}
```

boostrap-table用法

```
        var $table = $('#table');
        $table.bootstrapTable({
        url: "/${appName}/manager/bookController/getAllBook",
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        pagination: true, //分页
        sidePagination: "server", //服务端处理分页
        pageList: [5, 10, 25],
        pageSize: 5,
        pageNumber:1,
        //toolbar:"#tb",
        singleSelect: false,
        queryParamsType : "limit",
        queryParams: function queryParams(params) {   //设置查询参数
          var param = {
            pageNo: params.offset/params.limit+1,  //offset为数据开始索引,转换为显示当前页
            pageSize: params.limit  //页面大小
          };
          console.info(params);   //查看参数是什么
          console.info(param);   //查看自定义的参数
          return param;
        },
        cache: false,
        //data-locale: "zh-CN", //表格汉化
        //search: true, //显示搜索框
        columns: [
                {
                    checkbox: true
                },
                {
                    title: '消费类型',
                    field: 'cate_name',
                    valign: 'middle'
                },
                {
                    title: '消费金额',
                    field: 'money',
                    valign: 'middle',
                    formatter:function(value,row,index){
                        if(!isNaN(value)){   //是数字
                            return value/100;
                        }
                    }
                },
                {
                    title: '备注',
                    field: 'remark',
                    valign: 'middle'
                },
                {
                    title: '消费时间',
                    field: 'time',
                    valign: 'middle'
                },
                {
                    title: '操作',
                    field: '',
                    formatter:function(value,row,index){
                        var f = '<a href="#" class="btn btn-gmtx-define1" onclick="delBook(\''+ row.id +'\')">删除</a> ';
                        return f;
                       }
                }
            ]
          });
      });
```

## RowBounds分页

数据量小时，RowBounds不失为一种好办法。但是数据量大时，实现拦截器就很有必要了。

mybatis接口加入RowBounds参数

public List<UserBean> queryUsersByPage(String userName, RowBounds rowBounds);

service

```
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public List<RoleBean> queryRolesByPage(String roleName, int start, int limit) {
        return roleDao.queryRolesByPage(roleName, new RowBounds(start, limit));
    }
```

# MyBatis 中#{}和${}区别

2020-09-10 18:01 更新

**#{}** 是预编译处理，像传进来的数据会加个" "（#将传入的数据都当成一个字符串，会对自动传入的数据加一个双引号）



**${}** 就是字符串替换。直接替换掉占位符。$方式一般用于传入数据库对象，例如传入表名.

使用 ${} 的话会导致 sql 注入。什么是 SQL 注入呢？比如 select * from user where id = ${value}



value 应该是一个数值吧。然后如果对方传过来的是 001  and name = tom。这样不就相当于多加了一个条件嘛？把SQL语句直接写进来了。如果是攻击性的语句呢？001；drop table user，直接把表给删了

<font color = Tomato size=3 face="楷书">所以为了防止 SQL 注入，能用 #{} 的不要去用 ${}</font>

如果非要用 ${} 的话，那要注意防止 SQL 注入问题，可以手动判定传入的变量，进行过滤，一般 SQL 注入会输入很长的一条 SQL 语句

# MyBatis接口绑定的几种方式

2020-09-10 18:05 更新

接口绑定有两种方式

1、使用注解，在接口的方法上面添加@Select@Update等注解，里面写上对应的SQL语句进行SQL语句的绑定。

2、通过映射文件xml方式进行绑定，指定xml映射文件中的namespace对应的接口的全路径名

 

什么时候用注解绑定？什么时候用xml绑定？

当SQL语句比较简单的时候，使用注解绑定就可以了，当SQL语句比较复杂的话，使用xml方式绑定，一般用xml方式绑定比较多



# MyBatis动态sql语句(OGNL语法)

2020-09-25 16:27 更新

## 1、if

```
<select id="select" resultType="Blog">
  SELECT * FROM BLOG
  WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="name!= null">
    AND name like #{title}
  </if>
</select>
```

## 2、where

像上面的那种情况，如果where后面没有条件，然后需要直接写if判断（开头如果是 and / or 的话，会去除掉）

```
<select id="select"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
      <if test="title != null">
        AND title like #{title}
      </if>
      <if test="name!= null">
        AND name like #{title}
      </if>
  <where>
</select>
```

## 3、choose（when、otherwise） 

choose 相当于 java 里面的 switch 语句。otherwise（其他情况）

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

## 4、tirm

tirm

prefix：前缀prefixoverride：去掉第一个and或者是or

```
select * from test
<trim prefix="WHERE" prefixoverride="AND丨OR">
      <if test="a!=null and a!=' '">AND a=#{a}<if>
      <if test="b!=null and b!=' '">AND a=#{a}<if>
</trim>
```



## 5、set

<font color = Tomato size=3 face="楷书">set 元素主要是用在更新操作的时候，如果包含的语句是以逗号结束的话将会把该逗号忽略，如果set包含的内容为空的话则会出错。</font>

```
<update id="dynamicSetTest" parameterType="Blog">  
    update t_blog  
    <set>  
        <if test="title != null">  
            title = #{title},  
        </if>  
        <if test="content != null">  
            content = #{content},  
        </if>  
        <if test="owner != null">  
            owner = #{owner}  
        </if>  
    </set>  
    where id = #{id}  
</update> 
```

##  6、foreach

foreach主要用在构建in条件中

```
<select id="dynamicForeachTest" resultType="Blog">  
        select * from t_blog where id in  
        <foreach collection="list" index="index" item="item" open="(" separator="," close=")">  
            #{item}  
        </foreach>  
    </select>  
```

open separator close

相当于是in （？，？，？）

 如果是个map怎么办

```
<select id="dynamicForeach3Test" resultType="Blog">  
        select * from t_blog where title like "%"#{title}"%" and id in  
        <foreach collection="ids" index="index" item="item" open="(" separator="," close=")">  
            #{item}  
        </foreach>  
    </select>  
```

collection对应map的键，像这样

```
List<Integer> ids = new ArrayList<Integer>();  
 ids.add(1);  
 ids.add(2);  
 ids.add(3);  
 ids.add(6);         
 ids.add(7);
 ids.add(9);  
 Map<String, Object> params = new HashMap<String, Object>();  
 params.put("ids", ids);  
```



# MyBatis Like 模糊查询有几种方式

2020-10-10 16:16 更新

方式1：$ 这种方式，简单，但是无法防止SQL注入，所以不推荐使用

  LIKE '%${name}%'

方式2：#

  LIKE "%"#{name}"%"

有兴趣的可以看一下：[Mybatis 中#{} 和${}区别](https://www.w3cschool.cn/mybatis/mybatis-yta93bpj.html)

方式3：字符串拼接

AND name LIKE CONCAT(CONCAT('%',#{name},'%'))

方式4：bind标签

```
<select id="searchStudents" resultType="com.example.entity.StudentEntity"
  parameterType="com.example.entity.StudentEntity">
  <bind name="pattern1" value="'%' + _parameter.name + '%'" />
  <bind name="pattern2" value="'%' + _parameter.address + '%'" />
  SELECT * FROM test_student
  <where>
   <if test="age != null and age != '' and compare != null and compare != ''">
    age
    ${compare}
    #{age}
   </if>
   <if test="name != null and name != ''">
    AND name LIKE #{pattern1}
   </if>
   <if test="address != null and address != ''">
    AND address LIKE #{pattern2}
   </if>
  </where>
  ORDER BY id

 </select>
```

方式5：java代码里写

param.setUsername("%CD%"); 在 java 代码中传参的时候直接写上

```
           <if test="username!=null"> AND username LIKE #{username}</if>
```

然后 mapper 里面直接写 #{} 就可以了



# 通常一个mapper.XML对应一个DAO接口，DAO是否可以重载？

2020-09-08 16:36 更新

答：不能重载，方法名对应的 mapper.xml 文件里的一个 id，这个与方法名对应，系统会根据 namespace+id 找到对应的方法对应。



Dao 接口即 Mapper 接口。接口的全限名，就是映射文件中的 namespace 的值；接口的方法名，就是映射文件中 Mapper 的 Statement 的 id 值；接口方法内的参数，就是传递给 sql 的参数。Mapper 接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为 key 值，可唯一定位一个 MapperStatement。在 Mybatis 中，每一个、、、标签，都会被解析为一个MapperStatement 对象。



举例：com.mybatis3.mappers.StudentDao.findStudentById，可以唯一找到 namespace 为 com.mybatis3.mappers.StudentDao 下面 id 为findStudentById 的 MapperStatement。



Mapper 接口里的方法，是不能重载的，因为是使用 全限名+方法名 的保存和寻找策略。Mapper 接口的工作原理是 JDK 动态代理，Mybatis 运行时会使用 JDK动态代理为 Mapper 接口生成代理对象 proxy，代理对象会拦截接口方法，转而执行 MapperStatement 所代表的 sql，然后将 sql 执行结果返回。

# MyBatis映射文件中A标签引用B标签，如果B标签在A的后面定义，可以吗？

2020-09-10 18:05 更新

虽然 Mybatis 解析 [Xml 映射文件](https://www.w3cschool.cn/mybatis/f4uw1ilx.html)是按照顺序解析的，但是，被引用的 B 标签依然可以定义在任何地方，Mybatis 都可以正确识别。

## 原理：

Mybatis 解析 A 标签时，发现引用了 B 标签，未解析到 B 标签，此时会把 A 标签标记为未解析状态；

继续解析下面内容，把剩下解析完之后，再解析标记为未解析的标签；

此时已解析到 B 标签，此时再解析A标签时，B标签已经存在，A 标签也就顺利解析完成。

# MyBatis不同映射文件中的id是否可以重复？

2020-09-10 18:05 更新

**可以重复，但是需要映射文件的`namespace`不同**



不同的 [Xml 映射文件](https://www.w3cschool.cn/mybatis/f4uw1ilx.html)，如果配置了 `namespace`，那么 `id` 可以重复；如果没有配置 `namespace`，那么 `id` 不能重复。

原因就是 `namespace`+`id` 是作为 `Map<String, MapperStatement>`的 key使用的，如果没有 `namespace`，就剩下 `id`，那么，`id` 重复会导致数据互相覆盖。

有了 `namespace`，自然 `id` 就可以重复，`namespace` 不同，`namespace`+`id` 自然也就不同。

# MyBatis是否可以映射到枚举类

2020-09-09 20:18 更新

Mybatis 可以映射枚举类。

不单可以映射枚举类，Mybatis 可以映射任何对象到表的一列上。映射方式为自定义一个 TypeHandler ，实现 TypeHandler 的 `setParameter()` 和 `getResult()` 接口方法。 

TypeHandler 有两个作用，一是完成从 javaType 至 jdbcType 的转换，二是完成 jdbcType 至 javaType 的转换，体现为 `setParameter() `和 `getResult()` 两个方法，分别代表设置 sql 问号占位符参数和获取列查询结果。

# MyBatis如何获取自动生成的主键id

2020-09-09 20:19 更新

MySQL：Mapper 文件 insert 语句设置  

```
useGeneratedKeys="true" keyProperty="id"
```

 

Oracle：Mapper 文件 insert 语句增加

```
<selectKey keyProperty="id" order="BEFORE" resultType="Integer">
    select xxx_SEQ.nextval from dual
</selectKey>
```

 MyBatis 传递多个参数

2020-09-25 16:31 更新

## 方法一:使用map接口传递参数

严格来说，map适用几乎所有场景，但是我们用得不多。原因有两个：首先，map是一个键值对应的集合，使用者要通过阅读它的键，才能明了其作用；其次，使用map不能限定其传递的数据类型，因此业务性质不强，可读性差，使用者要读懂代码才能知道需要传递什么参数给它，所以不推荐用这种方式传递多个参数。

```
public List<Role> findRolesByMap(Map<String, Object> parameterMap);
<select id="findRolesByMap" parameterType="map" resultType="role">
    select id, role_name as roleName, note from t_role where role_name like concat('%', #{roleName}, '%') and note like concat('%', #{note}, '%')
</select>
```

## 方法二:使用注解传递多个参数　　

MyBatis为开发者提供了一个注解@Param（org.apache.ibatis.annotations.Param），可以通过它去定义映射器的参数名称，使用它可以得到更好的可读性　　这个时候需要修改映射文件的代码，此时并不需要给出parameterType属性，让MyBatis自动探索便可以了　　使可读性大大提高，使用者也方便了，但是这会带来一个麻烦。如果SQL很复杂，拥有大于10个参数，那么接口方法的参数个数就多了，使用起来就很不容易，不过不必担心，MyBatis还提供传递Java Bean的形式。

```
public List<Role> findRolesByAnnotation(@Param("roleName") String rolename, @Param("note") String note);
<select id="findRolesByAnnotation" resultType="role">
    select id, role_name as roleName, note from t_role where role_name like concat('%', #{roleName}, '%') and note like concat('%', #{note}, '%')
</select>
```

## 方法三:通过Java Bean传递多个参数

```
public List<Role> findRolesByBean(RoleParams roleParam);
<select id="findRolesByBean" parameterType="com.xc.pojo.RoleParams" resultType="role">
    select id, role_name as roleName, note from t_role where role_name like concat('%', #{roleName}, '%') and note like concat('%', #{note}, '%')
</select>
```

## 方法四:混合使用　　

在某些情况下可能需要混合使用几种方法来传递参数。举个例子，查询一个角色，可以通过角色名称和备注进行查询，与此同时还需要支持分页

```
public List<Role> findByMix(@Param("params") RoleParams roleParams, @Param("page") PageParam PageParam);
<select id="findByMix" resultType="role">
    select id, role_name as roleName, note from t_role
    where role_name like concat('%', #{params.roleName}, '%') and note like concat('%', #{params.note}, '%') limit #{page.start}, #{page.limit}
</select>
```

 

## 总结:

描述了4种传递多个参数的方法，对各种方法加以点评和总结，以利于我们在实际操作中的应用。

　　•使用 map 传递参数导致了业务可读性的丧失，导致后续扩展和维护的困难，在实际的应用中要果断废弃这种方式。

　　•使用 @Param 注解传递多个参数，受到参数个数（n）的影响。当 n≤5 时，这是最佳的传参方式，它比用 Java Bean 更好，因为它更加直观；当 n＞5 时，多个参数将给调用带来困难，此时不推荐使用它。

　　•当参数个数多于5个时，建议使用 Java Bean 方式。

　　•对于使用混合参数的，要明确参数的合理性。



# MyBatis缓存机制

2022-03-31 16:08 更新

缓存机制减轻数据库压力，提高数据库性能

mybatis的缓存分为两级：一级缓存、二级缓存

## 一级缓存：

一级缓存为 `SqlSession` 缓存，缓存的数据只在 SqlSession 内有效。在操作数据库的时候需要先创建 SqlSession 会话对象，在对象中有一个 HashMap 用于存储缓存数据，此 HashMap 是当前会话对象私有的，别的 SqlSession 会话对象无法访问。

具体流程：

第一次执行 select 完毕会将查到的数据写入 SqlSession 内的 HashMap 中缓存起来

第二次执行 select 会从缓存中查数据，如果 select 同传参数一样，那么就能从缓存中返回数据，不用去数据库了，从而提高了效率

注意：

1、如果 SqlSession 执行了 DML 操作（insert、update、delete），并 commit 了，那么 mybatis 就会清空当前 SqlSession 缓存中的所有缓存数据，这样可以保证缓存中的存的数据永远和数据库中一致，避免出现差异

2、当一个 SqlSession 结束后那么他里面的一级缓存也就不存在了， mybatis 默认是开启一级缓存，不需要配置

3、 mybatis 的缓存是基于 [namespace:sql语句:参数] 来进行缓存的，意思就是， SqlSession 的 HashMap 存储缓存数据时，是使用 [namespace:sql:参数] 作为 key ，查询返回的语句作为 value 保存的

## 二级缓存：

二级缓存是` mapper` 级别的缓存，也就是同一个 namespace 的 mapper.xml ，当多个 SqlSession 使用同一个 Mapper 操作数据库的时候，得到的数据会缓存在同一个二级缓存区域

二级缓存默认是没有开启的。需要在 setting 全局参数中配置开启二级缓存

开启二级缓存步骤：

1、`conf.xml` 配置全局变量开启二级缓存

```
<settings>
    <setting name="cacheEnabled" value="true"/>默认是false：关闭二级缓存
<settings>
```

2、在` userMapper.xml `中配置

```
<cache eviction="LRU" flushInterval="60000" size="512" readOnly="true"/>当前mapper下所有语句开启二级缓存
```

这里配置了一个 LRU 缓存，并每隔60秒刷新，最大存储512个对象，而返回的对象是只读的

若想禁用当前`select`语句的二级缓存，添加 `useCache="false"`修改如下：

```
<select id="getCountByName" parameterType="java.util.Map" resultType="INTEGER" statementType="CALLABLE" useCache="false">
```

具体流程：

1.当一个` sqlseesion `执行了一次` select` 后，在关闭此` session` 的时候，会将查询结果缓存到二级缓存

2.当另一个` sqlsession `执行` select` 时，首先会在他自己的一级缓存中找，如果没找到，就回去二级缓存中找，找到了就返回，就不用去数据库了，从而减少了数据库压力提高了性能

注意:

1、如果 `SqlSession` 执行了 DML 操作`（insert、update、delete）`，并 `commit` 了，那么 `mybatis` 就会清空当前` mapper` 缓存中的所有缓存数据，这样可以保证缓存中的存的数据永远和数据库中一致，避免出现差异

2、` mybatis` 的一级缓存是基于` [namespace:sql语句:参数] `来进行缓存的，意思就是，`SqlSession` 的 `HashMap` 存储缓存数据时，是使用 `[namespace:sql:参数] `作为 `key` ，查询返回的语句作为 `value` 保存的。



# MyBatis时间timestamp做条件进行查询

2020-09-25 16:31 更新

首先要将条件 转换为 时间戳

```
        long startTime = TimeUtil.parseTimestamp(start);
        long endTime = TimeUtil.parseTimestamp(end);

/*对应工具类*/
   public static long parseTimestamp(String datetime){
		try{
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date	= dateformat.parse(datetime);
			return date.getTime()/1000;		
		}catch(Exception e){
			 e.printStackTrace();
		}

		return 0;
	}
```

然后Mapper.xml中 使用BETWEEN and 和 to_timestamp

```
<if test="startDate !=null and startDate !='' and endDate !=null and endDate !=''">
            AND tdnm.create_time BETWEEN to_timestamp(#{startDate}) AND to_timestamp(#{endDate})
</if>
```

# mybatis的Mapper文件中的大于小于号，为什么要转成“&lt ;”、“&gt ;”，转义后的lt、gt又代表什么?


为什么的Mapper文件中的"<"、">" 要转成“&lt ;”、“&gt ;”
问题分析
mybatis中的mapper文件是xml文件，不允许出现类似“>”这样的字符，会与文件本身的标签"<xxx>"冲突，

就像mysql中有些关键字（如：select、insert等）不允许当做字段名，会引起冲突；

处理方式
1.用转义字符把>和<替换掉，使后台能够正常解析这个xml文件

XML中需要转义的字符有：

字段	符号	说明
&lt ;	<	小于号
&gt ;	>	大于号
&amp ;	&	和
&apos ;	'	单引号
&quot ;	"	双引号
原SQL

select * from table where createTime >'2019-10-10' and createTime <= '2019-10-20' 

修改后SQL：

select * from table where createTime &gt; '2019-10-10' and createTime &lt;= '2019-10-20' 
2.增加声明标志：<![CDATA[]]>

被<![CDATA[]]>这个标记所包含的内容将表示为纯文本，后台会原样解析并执行

SELECT *FROM table
	<where>
		<!-- 录入日期 范围 -->
		<if test="date_st != '' and date_ed != ''">
			<![CDATA[
				and createTime >= #{date_st} and createTime =< #{date_ed}
			]]>
		</if>
	</where>


’另外“&lt ;”，“&gt ;”，“&ge ;”这些转移字符都是什么含义呢？

EQ： EQUAL   →   等于
NE： NOT EQUAL  →   不等于
GT： GREATER THAN   →    大于　
LT ： LESS THAN   →   小于
GE： GREATER THAN OR EQUAL   →   大于等于
LE： LESS THAN OR EQUAL   →   小于等于