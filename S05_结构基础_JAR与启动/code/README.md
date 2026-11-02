# S05 多文件项目与完整源码

本目录project下的源码共同组成一个应用；辅助类不需要main，不能把每份Java文件当作独立程序。先按上一级讲义的目录树与逐步命令理解，再在课程根目录的PowerShell运行：

```powershell
.\project.ps1 -Project S05 -Mode Run
.\project.ps1 -Project S05 -Mode Build
```

自己的完整副本放本课my_code/project，用-SourceRoot指定。项目工具编译失败即停，每次使用新产物；无需Python。Jar模式打包并启动，List模式列出应用归档内容。下方网页会按真实路径展开源码；原始文件也保存在project目录中。
