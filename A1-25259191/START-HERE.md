# 从这里开始

项目名为 `A1-25259191`，学号为 `25259191`，使用 VS Code 和 JDK 21。项目包含 GP、普外科、骨科、神经内科、儿科和护士六种具体医护类型，以及患者、预约、管理类和控制台演示。

GitHub 仓库为 [Object-Oriented-Programming-](https://github.com/ShiyuannnQu/Object-Oriented-Programming-)；最终压缩包输出到工作区 `output/A1-25259191.zip`。

建议按下面的顺序使用本项目：

1. 阅读 [COURSE-CHECK.md](COURSE-CHECK.md)，结合 Modules 1–3 复习实际使用的 Java 概念。开发版本已确定为 JDK 21。
2. 用 VS Code 打开整个项目文件夹，在终端运行 `powershell -NoProfile -ExecutionPolicy Bypass -File .\run.ps1`，观察正常和失败操作。
3. 阅读 [README.md](README.md)，对照源码说明每个类的责任、四大 OOP 支柱和业务假设。
4. 运行 `powershell -NoProfile -ExecutionPolicy Bypass -File .\verify.ps1`，核对实际检查结果；当前已有 186 项行为检查通过。
5. 保存实际 GitHub 提交与 WakaTime 项目记录，并确认能解释每个类、预约规则和失败案例。
6. 使用 [VIDEO-PREPARATION.md](VIDEO-PREPARATION.md) 自测理解，再亲自准备并录制讲解。该文档不是逐字稿。
7. 按 [SUBMISSION-CHECKLIST.md](SUBMISSION-CHECKLIST.md) 逐项核对提交材料和仍待完成的事项。

默认预约开始时间为 08:00 到 16:00、每 30 分钟一档，包含 16:00；按患者手机号取消会删除全部匹配预约。固定工作日保存在医护资料中，未附加非工作日拒约规则。这些设计选择应能够在演示中解释。

当前仍需准备完整的 WakaTime 项目报告和不超过 5 分钟的个人讲解视频；GitHub 上传、远端核验及最终 ZIP 解压验证状态见提交检查表。
