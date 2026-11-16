# A1 当日医疗预约系统

这是一个 Java 控制台脚本演示：通过创建并协作使用对象，展示当天的医护资料与预约操作。共有 11 个源码类，无外部 Java 库依赖。

- 学号：`25259191`
- 项目名称：`A1-25259191`
- 开发环境：VS Code、JDK 21
- GitHub：[Object-Oriented-Programming-](https://github.com/ShiyuannnQu/Object-Oriented-Programming-)
- 交付压缩包：工作区 `output/A1-25259191.zip`

GitHub 上传与远端核验状态以提交检查表中的实际结果为准。

## 类的责任

| 类 | 主要责任 |
| --- | --- |
| `HealthProfessional` | 抽象父类；保存稳定数字 ID、姓名和固定工作日；校验、受控读取及按 ID 比较身份 |
| `GeneralPractitioner` | GP 必选类型；增加 bulk billing 属性和一般诊疗服务说明 |
| `GeneralSurgeon` | 普外科；增加 `surgicalFocus` |
| `OrthopaedicDoctor` | 骨科；增加 `orthopaedicFocus` |
| `Neurologist` | 神经内科；增加 `neurologicalFocus` |
| `Paediatrician` | 儿科；增加 `maximumPatientAge` |
| `Nurse` | 护士；增加 `registrationLevel` |
| `Patient` | 保存经校验且不可变的姓名和手机号 |
| `Appointment` | 关联患者、任意医护子类型和时间；校验基本完整性、转换时间并按时间比较 |
| `AppointmentManager` | 管理预约集合及时间表；添加、冲突检查、筛选、排序、取消及结果格式化 |
| `AssignmentOne` | `main` 入口；使用虚构资料编排正常与失败场景，不承担核心预约管理规则 |

六种医护子类均直接继承共同父类，通过三参构造提供默认专业信息，也提供四参构造。时间采用午夜起算的整数分钟，不以时间文本的字典序判断先后。

## 四大 OOP 支柱

| 支柱 | 源码位置 | 在本问题中的理由 |
| --- | --- | --- |
| 封装 Encapsulation | 核心属性为 `private final`；构造时校验；工作日及时间表数组防御复制；管理类返回集合副本 | 保持身份及对象资料有效，防止调用方通过返回的数组或集合直接改坏内部状态 |
| 抽象 Abstraction | `HealthProfessional` 为抽象类，声明 `getProfessionalType()`、`describeService()` | 统一约定共同能力，避免直接创建没有具体专业类型的医护人员 |
| 继承 Inheritance | 六个具体医护类 `extends HealthProfessional`，构造调用 `super`，详情复用父类 `toString()` | 共同身份、姓名、工作日与校验只维护一份，子类补充自己的资料和行为 |
| 多态 Polymorphism | `AssignmentOne` 用 `HealthProfessional[]` 统一遍历并调用重写方法；`Appointment` 保存父类型引用 | 同一处理过程支持不同专业类型，预约无需为每种医护类型增加一套专用代码 |

方法重写与多态有关；构造和管理方法的重载是另外的概念，不应把二者混为一谈。

## 当前业务假设

- 一个管理器代表当前一天的预约。没有日期字段、跨日预约或自动午夜清空机制，也没有界面、菜单、登录或持久化。
- 默认时间表是 08:00、08:30……16:00，包含 16:00 作为开始时间；这是本项目对时间端点的设计解释。
- 可通过构造方法替换整个时间表。自定义 `int[]` 必须非空、元素为一天内的有效分钟、严格升序且不重复；默认半小时限制不额外套用到替换表。
- 同一专业人员 ID 加同一时间为冲突；不同专业人员可以在同一时间预约。两个不同对象、甚至不同子类对象只要 ID 相同，就代表同一身份。
- 工作日按固定每周资料保存，当前不额外检查预约当天是否为工作日。
- 按手机号取消会删除所有匹配预约，并反馈删除数量。未添加阻止患者同时预约不同医护人员的额外规则。
- 手机号为保留前导零的 `String`；只允许去除首尾空格后 7–15 位 ASCII 数字。这是简化教学规则，不是通用国际号码验证。
- 儿科的最大患者年龄属性限定为 1–18，是教学假设。`Patient` 不保存年龄，预约管理也不执行儿科患者年龄准入。
- 测试患者和医护姓名均为虚构演示数据；服务说明不是医疗建议。

## 主要操作 API

| 操作 | 方法及结果 |
| --- | --- |
| 创建完整预约 | `Appointment(Patient, int timeInMinutes, HealthProfessional)`；无效基本资料抛出 `IllegalArgumentException` |
| 转换时间 | `Appointment.parseTime(String)` 要求 `HH:mm`；`formatTime(int)` 返回时间标签 |
| 默认／自定义时间表 | `AppointmentManager()`／`AppointmentManager(int[] allowedTimeSlots)` |
| 添加 | `addAppointment(Appointment)` 或 `addAppointment(String patientName, String mobilePhone, String time, HealthProfessional)`；返回成功或 `WARNING` 文本 |
| 查看／筛选 | `getAppointments()`、`getAppointments(int professionalId)`、`getAppointments(String mobilePhone)`；返回独立列表，无匹配时为空 |
| 查看／筛选并排序 | 对应三个 `getSortedAppointments(...)` 重载；返回从早到晚的列表副本 |
| 指定预约取消 | `cancelAppointment(int professionalId, int timeInMinutes)`；时间可先由 `parseTime` 转换 |
| 按手机号取消 | `cancelAppointment(String mobilePhone)`；删除全部匹配项 |
| 显示结果 | `formatAppointments(ArrayList<Appointment>)`；空结果返回警告，调用方负责打印 |
| 状态与时间表 | `getAppointmentCount()`、`getAllowedTimeSlots()` |

预约自身检查完整性及一天内的有效时间；管理类再检查配置时间段与集合中的冲突。只有成功添加后，内部预约数量才增加。

## 编译、运行和验证

在 VS Code 中打开整个项目文件夹，然后在该文件夹的 PowerShell 终端执行：

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\run.ps1
powershell -NoProfile -ExecutionPolicy Bypass -File .\verify.ps1
```

`run.ps1` 编译 `src` 并运行 `AssignmentOne`；`verify.ps1` 重新编译并运行 `checks` 中的行为检查。项目使用 JDK 21。本机脚本支持已配置的默认 JDK、`JAVA_HOME` 或 `PATH` 回退；换电脑时应检查 JDK 安装与 VS Code Java 配置。

手动编译和运行的等价示例（按本机实际 JDK 路径调整）：

```powershell
$a1Jdk = Join-Path $env:USERPROFILE '.jdks\jdk-21.0.13+9'
$a1Sources = @(Get-ChildItem -LiteralPath .\src -Filter '*.java' -File | ForEach-Object { $_.FullName })
New-Item -ItemType Directory -Path .\bin -Force | Out-Null
& (Join-Path $a1Jdk 'bin\javac.exe') --release 21 -encoding UTF-8 -Xlint:all -Werror -d .\bin @a1Sources
& (Join-Path $a1Jdk 'bin\java.exe') -cp .\bin AssignmentOne
```

只有前一条编译命令成功后，才运行后一条命令，以免误运行旧的 `.class` 文件。可选生成 Javadoc（沿用上面的变量）：

```powershell
& (Join-Path $a1Jdk 'bin\javadoc.exe') -encoding UTF-8 -charset UTF-8 -Xdoclint:all -Werror -d .\javadoc @a1Sources
```

2026-11-16 的本地整体验证已通过：11 个生产源码文件使用 `--release 21 -encoding UTF-8 -Xlint:all -Werror` 编译成功；行为检查输出 `All 186 checks passed.`；11 类 Javadoc 经 `-Xdoclint:all -Werror` 检查通过，无警告。

主演示实际运行正常结束，输出 180 行：添加后 9 个预约，失败请求后仍为 9 个；按 ID 加时间取消 1 个，再按手机号取消剩余 2 个，最后为 6 个。替换时间表使用独立管理器。详见 [验证记录](reports/VERIFICATION.md)、[演示输出](reports/demo-output.txt)和[检查输出](reports/checks-output.txt)。

## 需求到实现与演示

| brief 部分 | 实现位置 | `AssignmentOne` 中的证明 |
| --- | --- | --- |
| Part 1：共同医护资料 | `HealthProfessional` | 输出数字 ID、姓名和固定工作日 |
| Part 2：至少四种类型且包含 GP | 六个具体医护子类 | 六类的完整资料和不同服务说明 |
| Part 3：统一处理医护对象 | 父类型数组及重写方法 | 三位 GP，其他五类各一位；同一循环处理八个对象 |
| Part 4：预约关联及时间顺序 | `Patient`、`Appointment` | 创建关联对象，输出患者、时间和医护完整详情；排序调用自然顺序 |
| Part 5：集合管理 | `AppointmentManager` | 添加、两种筛选、全体及两种子集排序、两种取消、缺失结果警告及自定义时间表 |
| Part 6：端到端演示 | `AssignmentOne` | 同人同时冲突、不同人同时成功、非法数据、取消不存在预约、失败前后数量及取消后的结果 |

项目 WakaTime 报告和个人讲解视频仍待完成。CLI 的 `6 mins` 是当天账户汇总，未限定本项目，不能作为完整作业报告；参见 [WakaTime 状态](reports/WAKATIME-STATUS.md)。GitHub 上传及远端核验须记录实际结果；持续开发历史需要真实的过程提交，本次上传本身不代表已有整个作业周期的进度记录。
