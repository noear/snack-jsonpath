<h1 align="center" style="text-align:center;">
  SnackJson
</h1>
<p align="center">
	<strong>一个 Json 框架，支持 JsonDom、JsonPath、JsonSchema（for Java）</strong>
</p>
<p align="center">
	兼容 `jayway.jsonpath` 和 <a href="https://www.rfc-editor.org/rfc/rfc9535.html" target="_blank">IETF JSONPath (RFC 9535)</a> 标准。兼容 JsonSchema draft-07 标准。支持开放式定制。
</p>
<p align="center">
    <a href="https://solon.noear.org/article/snack" target="_blank">https://solon.noear.org/article/snack</a>
</p>
<p align="center">
    <a href="https://deepwiki.com/noear/snackjson"><img src="https://deepwiki.com/badge.svg" alt="Ask DeepWiki"></a>
    <a target="_blank" href="https://central.sonatype.com/artifact/org.noear/snack4">
        <img src="https://img.shields.io/maven-central/v/org.noear/snack4.svg?label=Maven%20Central" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-21-green.svg" alt="jdk-21" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/downloads/">
		<img src="https://img.shields.io/badge/JDK-25-green.svg" alt="jdk-25" />
	</a>
    <br />
    <a target="_blank" href='https://gitee.com/noear/snack-jsonpath/stargazers'>
        <img src='https://gitee.com/noear/snack-jsonpath/badge/star.svg' alt='gitee star'/>
    </a>
    <a target="_blank" href='https://github.com/noear/snack-jsonpath/stargazers'>
        <img src="https://img.shields.io/github/stars/noear/snack-jsonpath.svg?style=flat&logo=github" alt="github star"/>
    </a>
</p>

<hr />

##### 语言： 中文 | [English](README.md) 

<hr />

SnackJson 借鉴了 `Javascript` 所有变量由 `var` 申明，及 `Xml dom` 一切都是 `Node` 的设计。其下一切数据都以`ONode`表示，`ONode`也即 `One node` 之意，代表任何类型，也可以转换为任何类型。

* 强调 `Json dom` 的构建和操控能力
* 可以 `Json path` 高性能查询（比 jayway.jsonpath 快很多），同时兼容 `jayway.jsonpath` 和 [IETF JSONPath (RFC 9535) 标准](https://www.rfc-editor.org/rfc/rfc9535.html) (用 `options` 切换)。提供新一代 JsonPath 体验。
* 提供 `Json schema` 构建与校验，兼容 JsonSchema draft-07、draft-2019 标准
* 支持 `json5` 部分特性（无键字段，注释，等...）
* 优先使用 无参构造函数 + 字段 编解码（可减少注入而触发动作的风险）


| 依赖包                           | 描述                     |  
|-------------------------------|------------------------| 
| `org.noear:snack4`            | 提供 `json dom` 构建和编解码支持 |   
| `org.noear:snack4-jsonpath`   | 提供 `json path` 查询支持    |   
| `org.noear:snack4-jsonschema` | 提供 `json schema` 校验支持  |  


### JSONPath 语法参考（ [IETF JSONPath (RFC 9535)](https://www.rfc-editor.org/rfc/rfc9535.html) ）

| 语法元素              | 描述                           |
|-------------------|------------------------------|
| `$`               | 根节点标识符                       |
| `@`               | 当前节点标识符（仅在过滤选择器中有效）          |
| `[<selectors>]`   | 子段：选择节点的零个或多个子节点             |
| `.name`           | 简写 `['name']`                |
| `.*`              | 简写 `[*]`                     |
| `..[<selectors>]` | 后代段：选择节点的零个或多个后代             |
| `..name`          | 简写 `..['name']`              |
| `..*`             | 简写 `..[*]`                   |
| `'name'`          | 名称选择器：选择对象的命名子对象             |
| `*`               | 通配符选择器：选择节点的所有子节点            |
| `3`               | 索引选择器：选择数组的索引子项（从 0 开始）      |
| `0:100:5`         | 数组切片选择器：数组的 `start:end:step` |
| `?<logical-expr>` | 过滤选择器：使用逻辑表达式选择特定的子项         |
| `fun(@.foo)`      | 过滤函数：在过滤表达式中调用函数（IETF 标准）    |
| `.fun()`          | 聚合函数：作为片段使用（jayway 风格）       |


过滤选择器语法参考：

| 语法                          | 描述       | 优先级 |
|-----------------------------|----------|-----|
| `(...)`                     | 分组       | 5   |
| `name(...)`                 | 函数扩展     | 5   |
| `!`                         | 逻辑 `非`   | 4   |
| `==`,`!=`,`<`,`<=`,`>`,`>=` | 关系比较符    | 3   |
| `&&`                        | 逻辑 `与`   | 2   |
| `\|\|`                      | 逻辑 `或`   | 1   |



IETF JSONPath (RFC 9535) 标准定义操作符（支持）

| 操作符        | 描述                 | 示例               |   
|------------|--------------------|------------------|
| `==`       | 左等于右（注意1不等于'1'）    | `$[?(@.a == 1)]` |  
| `!=`       | 左不等于右              | `$[?(@.a != 1)]` |  
| `<`        | 左比右小               | `$[?(@.a < 1)]`  |  
| `<=`       | 左小于或等于右            | `$[?(@.a <= 1)]` |  
| `>`        | 左大于右               | `$[?(@.a > 1)]`  |  
| `>=`       | 左大于等于右             | `$[?(@.a >= 1)]` |  


jayway.jsonpath 增量操作符（支持）

| 操作符        | 描述                 | 示例                                      |   
|------------|--------------------|-----------------------------------------|
| `=~`       | 左匹配正则表达式           | `[?(@.s =~ /foo.*?/i)]`              |  
| `in`       | 左存在于右              | `[?(@.s in ['S', 'M'])]`             |  
| `nin`      | 左不存在于右             |                                         |  
| `subsetof` | 左是右的子集             | `[?(@.s subsetof ['S', 'M', 'L'])]` |  
| `anyof`    | 左与右有一个交点           | `[?(@.s anyof ['M', 'L'])]`         |  
| `noneof`   | 左与右没有交集            | `[?(@.s noneof ['M', 'L'])]`        |  
| `size`     | 左（数组或字符串）的大小应该与右匹配 | `$[?(@.s size @.expected_size)]`        |  
| `empty`    | Left（数组或字符串）应该为空   | `$[?(@.s empty false)]`                 |  


IETF JSONPath (RFC 9535) 标准定义函数（支持）


| 函数             | 描述                                   | 参数类型   | 结果类型          |
|--------------|-----------------------|-------|----------|
| `length(x)`        | 字符串、数组或对象的长度      |  值          |  数值  |
| `count(x)`         | 节点列表的大小                     |  节点列表          | 数值   |
| `match(x,y)`      | 正则表达式完全匹配               |  值，值          |  逻辑值  |
| `search(x,y)`     | 正则表达式子字符串匹配          |  值，值          |  逻辑值  |
| `value(x)`         | 节点列表中单个节点的值          |  节点列表          |  值  |


jayway.jsonpath 函数（支持）


| 函数          | 描述                             | 输出类型       |
|:------------|:-------------------------------|:-----------|
| `length()`  | 字符串、数组或对象的长度    | Integer    |
| `min()`     | 查找当前数值数组中的最小值                  | Double     |
| `max()`     | 查找当前数值数组中的最大值                  | Double     |
| `avg()`     | 计算当前数值数组中的平均值                  | Double     |
| `stddev()`  | 计算当前数值数组中的标准差                  | Double     |
| `sum()`     | 计算当前数值数组中的总和                   | Double     |
| `keys()`    | 计算当前对象的属性键集合                   | `Set<E>`   |
| `concat(X)` | 将一个项或集合和当前数组连接成一个新数组          | like input |
| `append(X)` | 将一个项或集合 追加到当前路径的输出数组中          | like input |
| `first()`   | 返回当前数组的第一个元素                   | 依赖于数组元素类型  |
| `last()`    | 返回当前数组的最后一个元素                  | 依赖于数组元素类型  |
| `index(X)`  | 返回当前数组中索引为X的元素。X可以是负数（从末尾开始计算） | 依赖于数组元素类型  |


snack-jsonpath 增量操作符（支持）


| 操作符              | 描述                  | 示例                             |   
|------------------|---------------------|--------------------------------|
| `startsWith`     | 左（字符串）开头匹配右         | `[?(@.s startsWith 'a')]`      |  
| `endsWith`       | 左（字符串）结尾匹配右         | `[?(@.s endsWith 'b')]`        |  
| `contains`       | 左（数组或字符串）包含匹配右      | `[?(@.s contains 'c')]`        |  



### JSONPath 语法示例

JSON 样本数据

```json
{ "store": {
    "book": [
      { "category": "reference",
        "author": "Nigel Rees",
        "title": "Sayings of the Century",
        "price": 8.95
      },
      { "category": "fiction",
        "author": "Evelyn Waugh",
        "title": "Sword of Honour",
        "price": 12.99
      },
      { "category": "fiction",
        "author": "Herman Melville",
        "title": "Moby Dick",
        "isbn": "0-553-21311-3",
        "price": 8.99
      },
      { "category": "fiction",
        "author": "J. R. R. Tolkien",
        "title": "The Lord of the Rings",
        "isbn": "0-395-19395-8",
        "price": 22.99
      }
    ],
    "bicycle": {
      "color": "red",
      "price": 399
    }
  }
}
```

示例JSONPath表达式及其应用于示例JSON值时的预期结果

| JSONPath                         | 预期结果                   | 
|----------------------------------|------------------------|
| `$.store.book[*].author`         | 书店里所有书的作者              | 
| `$..autho`                       | 所有作者                   | 
| `$.store.*`                      | 商店里的所有东西，包括一些书和一辆红色的自行车 | 
| `$.store..price`                 | 商店里所有东西的价格             | 
| `$..book[2]`                     | 第三本书                   | 
| `$..book[2].author`              | 第三本书的作者                | 
| `$..book[2].publisher`           | 空结果：第三本书没有“publisher”成员 | 
| `$..book[-1]`                    | 最后一本书                  | 
| `$..book[0,1]`<br/>`$..book[:2]` | 前两本书                   | 
| `$..book[?@.isbn]`               | 所有有国际标准书号的书            | 
| `$..book[?@.price<10]`           | 所有比10便宜的书              | 
| `$..*`                           | 输入值中包含的所有成员值和数组元素      | 




### 放几个应用示例看看

支持 `dom` 操控

```java
ONode oNode = new ONode();
oNode.set("id", 1);
oNode.getOrNew("layout").then(o -> {
    o.addNew().set("title", "开始").set("type", "start");
    o.addNew().set("title", "结束").set("type", "end");
});

oNode.get("id").getInt();
oNode.get("layout").get(0).get("title").getString();

oNode.getOrNew("list").fillJson("[1,2,3,4,5,6]");
```


支持 `json path` 查询、构建、删除

```java
ONode.ofBean(store).select("$..book[?@.tags contains 'war'].first()").toBean(Book.class); //RFC9535 规范，可以没有括号
ONode.ofBean(store).select("$..book[?(!(@.category == 'fiction') && @.price < 40)].first()").toBean(Book.class);
ONode.ofJson(store).select("$.store.book.count()");

ONode.ofBean(store).create("$.store.book[0].category").toJson();

ONode.ofBean(store).delete("$..book[-1]");
```


支持 `json schema` 校验

```java
JsonSchema schema = JsonSchema.ofJson("{type:'object',properties:{userId:{type:'string'}}}"); //加载架构定义

schema.validate(ONode.ofJson("{userId:'1'}")); //校验格式
```


支持序列化、反序列化

```java
User user = new User();
ONode.ofBean(user).toBean(User.class); //可以作为 bean 转换使用
ONode.ofBean(user).toJson();

ONode.ofJson("{}").toBean(User.class);
ONode.ofJson("[{},{}]").toBean((new ArrayList<User>(){}).getClass()); //泛型
ONode.ofJson("[{},{}]").toBean(new TypeRef<List<User>>(){}); //泛型

//快捷方式
String json = ONode.serialize(user);
User user = ONode.deserialize(json, User.class);
```

### 路径树接口

```java
//case1
ONode o = ONode.ofJson(json);
ONode rst = o.select("$.data.list[*].mobile"); //自动为查询到的节点，生成 path 属性
List<String> rstPaths = rst.pathList(); //获取结果节点的路径列表
for(ONode n1 : rst.getArray()) {
    n1.path(); //当前路径
    n1.parent(); //父级节点
}

//case2
ONode o = ONode.ofJson(json).usePaths(); //手动为每个子节点，生成 path 属性
ONode rst = o.get("data").get("list").get(2);
rst.path();
rst.parent();
```



### 高级定制

Json 编解码定制

```java
Options options = Options.of();
//添加编码器
options.addEncoder(Date.class, (ctx, value, target) -> {
    target.setValue(DateUtil.format(data, "yyyy-MM-dd"));
});
//添加解码器
options.addDecoder(Date.class, ...);
//添加创建器（接管类实例化）
options.addCreator(...);

//添加特性
options.addFeature(Feature.Write_PrettyFormat);

//移除特性
options.removeFeature(Feature.Write_PrettyFormat);

//设置日期格式附
options.addFeature(Feature.Write_UseDateFormat); //使用日期格式
options.dateFormat("yyyy-MM");

//..

String json = ONode.ofBean(orderModel, options).toJson();
```

JsonPath 函数与操作符定制

```java
import org.noear.snack4.ONode;
import org.noear.snack4.jsonpath.FunctionLib;

public class FunctionDemo {
    public static void main(String[] args) {
        //定制 floor 函数
        FunctionLib.register("floor", (ctx, argNodes) -> {
            ONode arg0 = argNodes.get(0); //节点列表（选择器的结果）

            if (ctx.isDescendant()) {
                for (ONode n1 : arg0.getArray()) {
                    if (n1.isNumber()) {
                        n1.setValue(Math.floor(n1.getDouble()));
                    }
                }

                return arg0;
            } else {
                ONode n1 = arg0.get(0);

                if (n1.isNumber()) {
                    return ctx.newNode(Math.floor(n1.getDouble()));
                } else {
                    return ctx.newNode();
                }
            }
        });

        //检验效果（在 IETF 规范里以子项进行过滤，即 1,2） //out: 1.0
        System.out.println(ONode.ofJson("{'a':1,'b':2}")
                .select("$.a.floor()")
                .toJson());

        //参考 //out: 2.0
        System.out.println(ONode.ofJson("{'a':1,'b':2}")
                .select("$[?floor(@) > 1].first()")
                .toJson());
    }
}
```

```java
import org.noear.snack4.ONode;
import org.noear.snack4.jsonpath.OperatorLib;

public class OperationDemo {
    public static void main(String[] args) {
        //定制操作符
        OperatorLib.register("startsWith", (ctx, node, term) -> {
            ONode leftNode = term.getLeftNode(ctx, node);

            if (leftNode.isString()) {
                ONode rightNode = term.getRightNode(ctx, node);
                if (rightNode.isNull()) {
                    return false;
                }

                return leftNode.getString().startsWith(rightNode.getString());
            }
            return false;
        });

        //检验效果
        assert ONode.ofJson("{'list':['a','b','c']}")
                .select("$.list[?@ startsWith 'a']")
                .size() == 1;
    }
}
```

### 特别感谢JetBrains对开源项目支持：

<a href="https://jb.gg/OpenSourceSupport">
  <img src="https://user-images.githubusercontent.com/8643542/160519107-199319dc-e1cf-4079-94b7-01b6b8d23aa6.png" align="left" height="100" width="100"  alt="JetBrains">
</a>
