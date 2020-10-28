使用 wrk 工具进行压测
---
wrk 日志详情
```

Running 1m test @ http://localhost:8088/api/hello
  16 threads and 200 connections
 （对应参数 -t）  （对应参数-c）
  Thread Stats   Avg      Stdev     Max   +/- Stdev
              （平均值） （标准偏差）（最大值）（正负标准差占比）
    Latency    71.96ms  244.28ms   1.72s    92.12%
   （延迟时间）
    Req/Sec     4.55k     0.99k   35.84k    93.23%
（Request Per Seconds)
  Latency Distribution
    （响应时间分布）
     50%    2.36ms
     75%    3.14ms
     90%  101.49ms
     99%    1.33s
  2779691 requests in 1.00m, 331.87MB read
    1min之内一共有2779691个请求，每秒钟读取331.87MB数据量
  Socket errors: connect 0, read 95, write 0, timeout 76
Requests/sec:  46255.05
Transfer/sec:      5.52MB
```



压测命令如下：

`wrk -t16 -c200 -d30s --latency http://localhost:8088/api/hello`


<table>
    <tr>
        <td>GC / Heap（rps）</td>
        <td>512M</td>
        <td>1G</td>
        <td>2G</td>
        <td>4G</td>
        <td>8G</td>
    </tr>
    <tr>
        <td>Serial</td>
        <td>54936.13</td>
        <td>54973.37</td>
        <td>54969.67</td>
        <td>54849.20</td>
        <td>54868.51</td>
    </tr>
    <tr>
        <td>Parallel</td>
        <td>54981.63</td>
        <td>54833.50</td>
        <td>54803.19</td>
        <td>54958.33</td>
        <td>54878.10</td>
    </tr>
    <tr>
        <td>CMS</td>
        <td>54705.15</td>
        <td>54792.40</td>
        <td>54825.20</td>
        <td>54954.49</td>
        <td>54844.30</td>
    </tr>
    <tr>
        <td>G1</td>
        <td>54739.12</td>
        <td>54866.23</td>
        <td>54880.85</td>
        <td>55000.50</td>
        <td>54942.76</td>
    </tr>    
</table>

---

压测数据非常稳定，但也能看出来G1在大堆的情况下，表现更为优异

G1与CMS都呈现一个升降升的趋势，访问延迟在4g堆内存时最为优秀。CMS在更大堆（8g）的情况下，处理压力陡升

ParallelGC在小堆（512M）的情况下性能非常优秀，超过其他GC。但其他情况要逊于G1、CMS

SerialGC在大堆情况下，处理压力飙升。其他情况下都是要逊于G1、CMS、Par