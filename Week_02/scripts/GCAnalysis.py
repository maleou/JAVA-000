# GC 分析脚本
import os
import shlex, subprocess

if __name__ == "__main__":
    command = ["java", "xmx", "xms", "gc", "-Xloggc:log/gc_%s_%s_%d.log", "GCLogAnalysis"]

    for memory in ["128m", "512m", "1g", "2g", "4g", "8g"]:
        for gc in ["-XX:+UseSerialGC", "-XX:+UseParallelGC", "-XX:+UseConcMarkSweepGC", "-XX:+UseG1GC"]:

            xlog = command[4]

            try:
                for _ in range(0, 10):
                    cmd_command = []
                    cmd_command.append(command[0])
                    cmd_command.append("-Xmx" + memory)
                    cmd_command.append("-Xms" + memory)
                    cmd_command.append(gc)
                    cmd_command.append(xlog % (memory, gc[5:], _))
                    cmd_command.append(command[5])
                    print(cmd_command[5])

                    print(" ".join(cmd_command))
                    args = shlex.split(" ".join(cmd_command))
                    result = subprocess.check_output(args)

                    # if type(result) is int:
                    #     print(int(result))
                    # else:
                    #     print(result.decode("utf-8"))
            except Exception as e:
                elog = open("./log/gc" + gc + ".log", mode='a', encoding='utf-8')
                print(memory + gc + "::OOM", file=elog)
