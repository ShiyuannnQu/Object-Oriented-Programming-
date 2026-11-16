# 课程范围核对表

项目使用 JDK 21。下面整理了代码中实际使用的概念，便于对照 Modules 1–3 复习；目前尚未逐项核对这些课程材料，标为待核对不等于认定超纲。

## 技术清单

| 当前实际使用的内容 | 源码位置或用途 | 状态 |
| --- | --- | --- |
| 类、对象、`private`、`protected`、`public`、`static`、`final` | 11 个类的属性、方法与常量 | 待核对 |
| 构造方法、`this(...)`、`super(...)` 与构造重载 | 医护父类、六类子类、管理类 | 待核对 |
| `abstract` 类和抽象方法 | `HealthProfessional` | 待核对 |
| 继承、方法重写、`@Override`、父类型引用和动态绑定 | 六类子类、`Appointment`、入口循环 | 待核对 |
| 基本数组、增强 `for`、普通循环和防御复制 | 工作日、固定时间段与医护数组 | 待核对 |
| `ArrayList<Appointment>` 及基础泛型 | 预约集合、筛选结果和返回副本 | 待核对 |
| `Comparable<Appointment>`、`compareTo`、`Collections.sort` | 预约自然顺序与管理类排序 | 待核对 |
| `equals(Object)`、`hashCode()`、`instanceof` 与类型转换 | 按医护 ID 比较身份 | 待核对 |
| 方法重载 | 添加、筛选、排序和取消操作 | 待核对 |
| `IllegalArgumentException`、`throw`、`try/catch` | 构造校验、时间解析与管理操作反馈 | 待核对 |
| 字符串、`trim`、`charAt`、`substring`、`Integer.parseInt` | 姓名、手机号和 `HH:mm` 校验 | 待核对 |
| `toString()`、`System.out`、`System.lineSeparator()` | 详情输出和控制台演示 | 待核对 |
| Javadoc 与必要行内注释 | 源码文档 | 待结合课程复习 |

当前生产源码没有使用 `java.util.Arrays`，数组复制以基础循环完成；也没有使用 `enum`、Stream、lambda、record、日期时间 API、GUI、数据库或外部框架。核对的是实际源码，不必为了数量增加技术。

## JDK 与实现假设

- [x] 使用现有 JDK 21，编译目标为 `--release 21`。
- [x] 本机 JDK、VS Code Java 配置及脚本已完成本地验证。
- [ ] 能说明 16:00 作为最后开始时间的设计解释。
- [ ] 能说明按手机号取消全部匹配项的行为。
- [ ] 能说明固定工作日仅保存、不附加当天工作日拒约规则的范围。
- [ ] 能解释简化手机号规则、儿科属性范围及自定义时间表的用途。

复习时可记录相应模块或活动的位置；若调整技术选择或业务规则，应重新运行编译、演示与行为检查。
