使用 wrk 工具进行压测
---



压测命令如下：

```
wrk  -c 40 -d30s http://localhost:8801
wrk  -c 40 -d30s http://localhost:8802
wrk  -c 40 -d30s http://localhost:8803
```

在 单线程 的情况下：rps 9.93

在 不限制线程数量 的情况下：rps 18.63

在 限定线程数量 的情况下：rps 14.39
（与此同时，通过 jvisualvm 观察，固定的40个线程的运行时长为0，且通过浏览器调用接口无任何响应）


