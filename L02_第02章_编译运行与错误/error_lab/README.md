# 错误标本 · 预期行为说明

这些是课程实验，故意保留错误。每次独立编译一个，不与正常示例混编；在my_code副本中修复。

| 文件 | 预期编译 | 预期运行或结果 | 观察重点 |
|---|---|---|---|
| MissingSemicolon.java | 失败 | 不运行 | println后少分号 |
| WrongFileName.java | 失败 | 不运行 | public类名与文件名不一致 |
| MissingMain.java | 成功 | 启动失败 | 缺少main入口 |
| RuntimeCrash.java | 成功 | 运行失败 | 数组访问越界 |
| WrongFormula.java | 成功 | 正常结束但结果不合题意 | 长4宽3面积应为12，程序输出14 |

运行命令用根目录java.ps1。只有编译成功才启动，因此前两个应直接停在编译阶段。编译诊断具体文字可能随JDK和语言环境变化，以失败原因判断。
