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

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onlab.metrics.MetricsService;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.cli.Comparators;
import org.onosproject.cluster.ClusterEvent;
import org.onosproject.cluster.ClusterEventListener;
import org.onosproject.cluster.ClusterService;
import org.onosproject.cluster.NodeId;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.mastership.MastershipAdminService;
import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.MastershipRole;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.FlowEntry.FlowEntryState;
import org.onosproject.net.flow.FlowRuleService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.google.common.collect.Lists.newArrayList;


/**
 * Sample Apache Karaf CLI command
 */
@Command(scope = "onos", name = "rollback",
         description = "Roll back devices")
public class RollbackCommand extends AbstractShellCommand {


    private ApplicationId appId;

    private final ClusterEventListener clusterEventListener = new InternalClusterListener();

    protected CoreService coreService;
    protected DeviceService deviceService;
    protected MastershipAdminService mastershipAdminService;
    protected ClusterService clusterService;

    private EvacueeDevices evacueeDevices = EvacueeDevices.getSingletonEvacueeDeviceList();



    @Override
    protected void execute() {
        coreService = get(CoreService.class);
        deviceService = get(DeviceService.class);
        mastershipAdminService = get(MastershipAdminService.class);
        clusterService = get(ClusterService.class);

        NodeId targetNodeId = evacueeDevices.getRollBackNodeId();

        for(DeviceId d : evacueeDevices.getDevices()) {
            mastershipAdminService.setRole(targetNodeId, d, MastershipRole.MASTER);
        }



        print("test");

        log.info("test");


    }




    private class InternalClusterListener implements ClusterEventListener {
        @Override
        public void event(ClusterEvent clusterEvent) {
            log.info("[test-version]Cluster Event" + clusterEvent.type().toString());
            switch (clusterEvent.type()) {
                case INSTANCE_ADDED:
                    log.info("[test-version] INSTANCE_ADDED");
                    break;
                case INSTANCE_REMOVED:
                    log.info("[test-version] INSTANCE_REMOVED");
                    break;
                case INSTANCE_ACTIVATED:
                    log.info("[test-version] INSTANCE_ACTIVATED");
                    break;
                case INSTANCE_DEACTIVATED:
                    log.info("[test-version] INSTANCE_DEACTIVATED");
                    break;
            }
        }
    }
}
