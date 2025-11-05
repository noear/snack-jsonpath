
### 4.0.7

* 添加 TypeRef:listOf, setOf,mapOf 方法
* 添加 EgggUtil:getClassEggg 方法
* 优化 ONode:setAll, addAll 允许传入 null（兼容 snack3）
* eggg 升为 1.0.3

### 4.0.6

* 调整 Read_ConvertSnakeToCamel 特性更名为 Read_ConvertSnakeToSmlCamel
* 调整 Write_UseSnakeStyle 特性更名为 Write_UseSmlSnakeStyle
* 添加 Read_ConvertCamelToSmlSnake 特性
* 添加 Write_UseSmlCamelStyle 特性
* eggg 升为 1.0.2

### 4.0.5

* eggg 升为 1.0.1

### 4.0.4

* 优化 与 snack3 的效果兼容性

### 4.0.3

* 调整 泛型处理切抱为 eggg

### 4.0.2

* 添加 Write_BigDecimalAsPlain 特性
* 调整 ONode:nodeType,getType 合并为 `type()` 与 `options()` 保持相同风格
* 调整 QueryContext:isInFilter 更名为 `isFiltered()`
* 调整 Write_BigNumbersAsString 更名为 Write_DoubleAsString
* 优化 与 snack3 的效果兼容性

### 4.0.1

* 添加 ONodeCreator 静态方法的支持（普通类）
* 添加 ONodeAttr:ignore 注解属性支持
* 添加 Write_BooleanAsNumber 新特性
* 添加 Read_UseBigDecimalMode 新特性
* 添加 Read_UseBigIntegerMode 新特性
* 添加 DecodeContext:hasFeature, EncodeContext:hasFeature 新特性
* 优化 Write_Nulls 完善对 Map 输出的控制
* 优化 Write_BrowserCompatible 写入性能


### 4.0.0

* 重构整个项目（除了名字没变，其它都变了） 
* 单测覆盖 98%，历时小半年 
* 支持 IETF JSONPath (RFC 9535) 标准（全球首个支持该标准的 Java 框架），同时兼容 `jayway.jsonpath`
* 添加 json-schema 支持

