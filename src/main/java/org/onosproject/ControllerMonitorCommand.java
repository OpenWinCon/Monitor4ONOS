/*
 * Copyright 2014 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject;

import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;


/**
 * Sample Apache Karaf CLI command
 */
@Command(scope = "onos", name = "controller-monitor",
         description = "Monitoring controller's CPU load and Memory laod")
public class ControllerMonitorCommand extends AbstractShellCommand {



    @Override
    protected void execute() {

        OperatingSystemMXBean osMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        print("CPU usage    : " + osMXBean.getSystemLoadAverage()*100 + " percent");
        print("Max memory   : %.3f MB", (double)runtime.maxMemory() / (1024* 1024));
        print("Total memory : %.3f MB", (double)runtime.totalMemory() / (1024*1024));
        print("Used memory  : %.3f MB", (double)(runtime.totalMemory() - runtime.freeMemory()) / (1024*1024));

//        print("Max memory   : " + (double)runtime.maxMemory() / (1024* 1024) + "MB");
//        print("Total memory : " + (double)runtime.totalMemory() / (1024*1024) + "MB");
//        print("Used memory  : " + (double)(runtime.totalMemory() - runtime.freeMemory()) / (1024*1024) + "MB");


        log.info("test");


    }




}
