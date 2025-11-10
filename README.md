<h1 align="center" style="text-align:center;">
  Snack
</h1>
<p align="center">
	<strong>A Json Dom & JsonPath framework (for Java)</strong>
</p>
<p align="center">
	Compatible with `jayway.jsonpath` and <a href="https://www.rfc-editor.org/rfc/rfc9535.html" target="_blank">IETF JSONPath (RFC 9535)</a> standards (Open customization support)
</p>
<p align="center">
    <a href="https://solon.noear.org/article/snack" target="_blank">https://solon.noear.org/article/snack</a>
</p>
<p align="center">
    <a target="_blank" href="https://deepwiki.com/noear/snack-jsonpath">
        <img src="https://deepwiki.com/badge.svg" alt="Ask DeepWiki" />
    </a>
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

##### Language: English | [中文](README_CN.md) 

<hr />

jdk8 based. Support: Json Dom construction, encoding and decoding, fetching, JsonPath query, JsonSchema validation.

```xml
<dependency>
    <groupId>org.noear</groupId>
    <artifactId>snack4-jsonpath</artifactId>
    <version>...</version>
</dependency>
```

Snach-jsonpath draws on the design of `Javascript` where all variables are declared with `var` and `Xml dom` where everything is `Node`. Everything underneath it is represented by an `ONode`, which stands for `One node` and can be converted to any type.

* It emphasizes the ability to build and manipulate the document tree
* High performance `Json path` queries (much faster than `jayway.jsonpath`), Compatible with `jayway.jsonpath` and [IETF JSONPath (RFC 9535)](https://www.rfc-editor.org/rfc/rfc9535.html) standards (Switch with `optoins`). Deliver the next generation of JsonPath experiences.
* Supports `Json Schema` validation
* Support some 'json5' features (keyless fields, comments, etc...)
* Prefer no-argument constructors + field codec (reduces the risk of triggering actions by injection)


| dependencies                        | description                                |  
|-------------------------------------|--------------------------------------------| 
| `org.noear:snack4`                  | Provides `json dom` building and codec support |   
| `org.noear:snack4-jsonpath`         | Provides `json path` query support         |   
| `org.noear:snack4-jsonschema`       | Provides `json schema` validation support  |  


### JSONPath syntax reference ( [IETF JSONPath (RFC 9535)](https://www.rfc-editor.org/rfc/rfc9535.html) )

| Syntax Element    | Description                                                                |
|-------------------|----------------------------------------------------------------------------|
| `$`               | root node identifier                                                       |
| `@`               | current node identifier (valid only within filter selectors)               |
| `[<selectors>]`   | 	child segment: selects zero or more children of a node                    |
| `.name`           | shorthand for `['name']`                                                   |
| `.*`              | shorthand for `[*]`                                                        |
| `..[<selectors>]` | descendant segment: selects zero or more descendants of a node             |
| `..name`          | shorthand for `..['name']`                                                 |
| `..*`             | shorthand for `..[*]`                                                      |
| `'name'`          | name selector: selects a named child of an object                          |
| `*`               | wildcard selector: selects all children of a node                          |
| `3`               | index selector: selects an indexed child of an array (from 0)              |
| `0:100:5`         | array slice selector: `start:end:step` for arrays                          |
| `?<logical-expr>` | filter selector: selects particular children using a logical expression    |
| `fun(@.foo)`      | filter function: invokes a function in a filter expression (IETF standard) |
| `.fun()`          | aggregate function: Used as a fragment (jayway style)                      |

Filter selector syntax reference:

| Syntax                       | Description            | Precedence |
|------------------------------|------------------------|------------|
| `(...)`                      | Grouping               | 5          |
| `name(...)`                  | Function Expressions   | 5          |
| `!`                          | Logical NOT	           | 4          |
| `==`,`!=`,`<`,`<=`,`>`,`>=`  | Relations              | 3          |
| `&&`                         | Logical AND            | 2          |
| `\|\|`                       | Logical OR	            | 1          |



IETF JSONPath (RFC 9535) Standard definition operators (supported)

| Operator  | Description                                              | Examples         |   
|-----------|----------------------------------------------------------|------------------|
| `==`      | left is equal to right (note that 1 is not equal to '1') | `$[?(@.a == 1)]` |  
| `!=`      | left is not equal to right                               | `$[?(@.a != 1)]`  |  
| `<`       | left is less than right                                  | `$[?(@.a < 1)]`   |  
| `<=`      | left is less or equal to right                           | `$[?(@.a <= 1)]`  |  
| `>`       | left is greater than right                               | `$[?(@.a > 1)]`   |  
| `>=`      | left is greater than or equal to right                   | `$[?(@.a >= 1)]`  |  

jayway.jsonpath Increment operator (supported)

| Operator        | Description                                       | Examples                            |   
|------------|---------------------------------------------------|-------------------------------------|
| `=~`       | left matches regular expression                   | `[?(@.s =~ /foo.*?/i)]`             |  
| `in`       | left exists in right                              | `[?(@.s in ['S', 'M'])]`            |  
| `nin`      | left does not exists in right                     |                                     |  
| `subsetof` | left is a subset of right                         | `[?(@.s subsetof ['S', 'M', 'L'])]` |  
| `anyof`    | left has an intersection with right               | `[?(@.s anyof ['M', 'L'])]`         |  
| `noneof`   | left has no intersection with right               | `[?(@.s noneof ['M', 'L'])]`        |  
| `size`     | size of left (array or string) should match right | `$[?(@.s size @.expected_size)]`    |  
| `empty`    | left (array or string) should be empty            | `$[?(@.s empty false)]`             |  


IETF JSONPath (RFC 9535) Standard definition functions (supported)


| Function      | Description                               | Parameter types | Result types      |
|---------------|-------------------------------------------|-----------------|-------------------|
| `length(x)`   | The length of a string, array, or object  | Value           | Numerical value   |
| `count(x)`    | Size of the node list                     | Node list       | Numerical value   |
| `match(x,y)`  | The regular expression matches exactly    | Value,Value     | Logical value     |
| `search(x,y)` | Regular expression substring matching     | Value,Value     | Logical value     |
| `value(x)`    | The value of a single node in a node list | Node list       | Value             |


jayway.jsonpath Functions (supported)


| Function    | Description                                                                          | Output type          |
|:------------|:-------------------------------------------------------------------------------------|:---------------------|
| `min()`     | Provides the min value of an array of numbers                                        | Double               |
| `max()`     | Provides the max value of an array of numbers                                        | Double               |
| `avg()`     | Provides the average value of an array of numbers                                    | Double               | 
| `stddev()`  | Provides the standard deviation value of an array of numbers                         | Double               | 
| `length()`  | Provides the length of an array                                                      | Integer              |
| `sum()`     | Provides the sum value of an array of numbers                                        | Double               |
| `keys()`    | Provides the property keys (An alternative for terminal tilde `~`)                   | `Set<E>`             |
| `concat(X)` | Provides a concatinated version of the path output with a new item                   | like input           |
| `append(X)` | add an item to the json path output array                                            | like input           |
| `first()`   | Provides the first item of an array                                                  | Depends on the array |
| `last()`    | Provides the last item of an array                                                   | Depends on the array |
| `index(X)`  | Provides the item of an array of index: X, if the X is negative, take from backwards | Depends on the array |


snack-jsonpath Increment operator (supported)


| Operator              | Description                                       | Examples                  |   
|------------------|---------------------------------------------------|---------------------------|
| `startsWith`     | left (string) start matches a right               | `[?(@.s startsWith 'a')]` |  
| `endsWith`       | left (string) end matches the right               | `[?(@.s endsWith 'b')]`   |  
| `contains`       | left (array or string) contains matches the right | `[?(@.s contains 'c')]`   |  



### JSONPath syntax examples

Example JSON Value

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

Example JSONPath Expressions and Their Intended Results When Applied to the Example JSON Value

| JSONPath | Intended Result | 
| -------- | -------- |
| `$.store.book[*].author`     |  the authors of all books in the store     | 
| `$..autho`     |  all authors     | 
| `$.store.*`     |  all things in the store, which are some books and a red bicycle     | 
| `$.store..price`     |  the prices of everything in the store     | 
| `$..book[2]`     |  the third book     | 
| `$..book[2].author`     |  the third book's author     | 
| `$..book[2].publisher`     |  empty result: the third book does not have a "publisher" member     | 
| `$..book[-1]`     |  the last book in order     | 
| `$..book[0,1]`<br/>`$..book[:2]`     |  the first two books     | 
| `$..book[?@.isbn]`     |  all books with an ISBN number     | 
| `$..book[?@.price<10]`     |  all books cheaper than 10     | 
| `$..*`     |  all member values and array elements contained in the input value     | 


### Let's look at some application examples

Support `dom` manipulation

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


Supports `json path` query, build, and delete

```java
ONode.ofBean(store).select("$..book[?@.tags contains 'war'].first()").toBean(Book.class); //RFC9535 规范，可以没有括号
ONode.ofBean(store).select("$..book[?(!(@.category == 'fiction') && @.price < 40)].first()").toBean(Book.class);
ONode.ofJson(store).select("$.store.book.count()");

ONode.ofBean(store).create("$.store.book[0].category").toJson();

ONode.ofBean(store).delete("$..book[-1]");
```


Supports `json schema` validation

```java
JsonSchema schema = JsonSchema.ofJson("{type:'object',properties:{userId:{type:'string'}}}"); //加载架构定义

schema.validate(ONode.ofJson("{userId:'1'}")); //校验格式
```


Supports serialization and deserialization

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

### Path tree interface

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



### Advanced customization

Json codec customization

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

JsonPath Function and operator customization

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


### Special thanks to JetBrains for supporting open-source projects：

<a href="https://jb.gg/OpenSourceSupport">
  <img src="https://user-images.githubusercontent.com/8643542/160519107-199319dc-e1cf-4079-94b7-01b6b8d23aa6.png" align="left" height="100" width="100"  alt="JetBrains">
</a>

