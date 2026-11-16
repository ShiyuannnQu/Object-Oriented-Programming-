# 本地程序验证记录

验证日期：2026-11-16（Asia/Shanghai）。学号：`25259191`；项目：`A1-25259191`。本记录保存已经完成的本地编译、演示和行为检查结果。

| 检查 | 实际结果 |
|---|---|
| Java 开发环境 | Temurin JDK 21.0.13 |
| 生产源码 | 11 个 Java 文件，无第三方运行依赖 |
| 编译 | `--release 21 -encoding UTF-8 -Xlint:all -Werror` 通过 |
| 行为检查 | `All 186 checks passed.` |
| 脚本演示 | 正常结束，输出 180 行；添加后 9 条，失败添加后仍 9 条，取消后 6 条 |
| 角色演示 | 3 位 GP，外科、骨科、神经内科、儿科、护士各 1 位 |
| 时间表 | 08:00 和 16:00 边界通过，08:15 / 07:30 / 16:30 拒绝；替代时间表可接收 09:15 |
| 封装与身份 | 检查覆盖工作日数组、时间表数组及查询列表的防御复制，以及 ID 相等与预约冲突 |
| 文档 | 11 类 Javadoc 经 `-Xdoclint:all -Werror` 通过 |
| VS Code | 三份项目 JSON 配置可解析，默认编译任务和独立行为检查任务已配置 |
| 独立解压验证 | 编号项目 ZIP 解压到独立目录后重新编译，180 行演示及 186 项检查通过 |
| GitHub | 已上传至 `main/A1-25259191`；仓库文件已逐一校验与本地一致 |

`demo-output.txt` 保存演示输出，`checks-output.txt` 保存行为检查输出。

生成的 Javadoc 位于本机 `javadoc/index.html`；它属于构建输出，不计入源码压缩包。

最终压缩包输出到工作区 `output/A1-25259191.zip`。文件清单、SHA-256 和解压运行结果记录于 `output/A1-25259191-VERIFICATION.md`。

GitHub 仓库：[Object-Oriented-Programming-](https://github.com/ShiyuannnQu/Object-Oriented-Programming-)。代码与文档已经上传并核验，提交平台上的操作状态见项目提交检查表。
