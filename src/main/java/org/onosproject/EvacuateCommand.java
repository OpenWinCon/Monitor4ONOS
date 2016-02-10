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
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import io.netty.handler.traffic.TrafficCounter;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.util.ExternalResourceReleasable;
import org.onlab.metrics.MetricsService;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.cli.Comparators;
import org.onosproject.cluster.ClusterEvent;
import org.onosproject.cluster.ClusterEventListener;
import org.onosproject.cluster.ClusterService;
import org.onosproject.cluster.ControllerNode;
import org.onosproject.cluster.NodeId;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.mastership.MastershipAdminService;
import org.onosproject.mastership.MastershipService;
import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.MastershipRole;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;
import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.net.flow.FlowEntry.FlowEntryState;


import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.google.common.collect.Lists.newArrayList;



/**
 * Sample Apache Karaf CLI command
 */
@Command(scope = "onos", name = "evacuate",
         description = "Sample Apache Karaf CLI command")
public class EvacuateCommand extends AbstractShellCommand {

    private static final String FMT =
            "   id=%s, state=%s, bytes=%s, packets=%s, duration=%s, priority=%s, tableId=%s appId=%s, payLoad=%s";
    private static final String TFMT = "      treatment=%s";
    private static final String SFMT = "      selector=%s";

    private ApplicationId appId;

    private final ClusterEventListener clusterEventListener = new InternalClusterListener();


    protected CoreService coreService;
    protected DeviceService deviceService;
    protected MastershipAdminService mastershipAdminService;
    protected ClusterService clusterService;
    protected MastershipService mastershipService;

    private EvacueeDevices evacueeDevices = EvacueeDevices.getSingletonEvacueeDeviceList();



    @Argument(index = 0, name = "node", description = "Node ID", required = true, multiValued = false)
    String node = null;

    //@Argument(index = 1, name = "state", description = "Flow Rule State", required = false, multiValued = false)
    String state = null;

    String uri = null;


    @Override
    protected void execute() {
        coreService = get(CoreService.class);
        deviceService = get(DeviceService.class);
        mastershipAdminService = get(MastershipAdminService.class);
        clusterService = get(ClusterService.class);
        mastershipService = get(MastershipService.class);

        NodeId targetNodeId = new NodeId(node);

        Set<DeviceId> targetDevices = mastershipService.getDevicesOf(targetNodeId);

        for(DeviceId d : targetDevices) {
            mastershipAdminService.setRole(targetNodeId, d, MastershipRole.STANDBY);
//            print(d.uri().toString());
        }

        evacueeDevices.setRollBackNodeId(targetNodeId);
        evacueeDevices.setDevices(targetDevices);

//        while(iter.hasNext()) {
//            DeviceId tempId = iter.next().id();
//            List<DeviceId> deviceIdList = evacueeDevices.getDeviceList();
//            deviceIdList.add(tempId);
//            print(mastershipService.getLocalRole(tempId).toString());
//
//        }



//        FlowRuleService flowRuleService = get(FlowRuleService.class);
//        SortedMap<Device, List<FlowEntry>> sortedFlows = getSortedFlows(deviceService, flowRuleService);
//
//        appId = coreService.registerApplication("org.onosproject.monitor4onos");
//
//        Iterator<Device> iter = deviceService.getAvailableDevices().iterator();
//        while(iter.hasNext()) {
//            print(iter.next().toString());
//        }
//
//        Iterator<PortStatistics> iter2 = deviceService.getPortStatistics(DeviceId.deviceId("of:0000000000000001")).iterator();
//
//        while(iter2.hasNext()) {
//            print(iter2.next().toString());
//        }

//        clusterService.addListener(clusterEventListener)


        print("test");


        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000001")).name());
        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000002")).name());
        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000003")).name());
        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000004")).name());
        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000005")).name());
        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000006")).name());
        print(deviceService.getRole(DeviceId.deviceId("of:0000000000000007")).name());



        log.info("test");



//        if(outputJson()) {
//            print("[outputJson]%s", json(sortedFlows.keySet(), sortedFlows));
//        } else {
//            print("else\n");
//            sortedFlows.forEach((device, flow) -> printFlows(device, flow, coreService));
//        }

    }

    protected SortedMap<Device, List<FlowEntry>> getSortedFlows(DeviceService deviceService, FlowRuleService flowRuleService) {
        SortedMap<Device, List<FlowEntry>> sortedFlows = new TreeMap<>(Comparators.ELEMENT_COMPARATOR);
        List<FlowEntry> flowList;
        FlowEntryState flowEntryState = null;

        if(state != null && !state.equals("any")) {
            flowEntryState = FlowEntryState.valueOf(state.toUpperCase());
        }
        Iterable<Device> devices = null;

        if(uri == null) {
            devices = deviceService.getDevices();
        } else {
            Device d = deviceService.getDevice(DeviceId.deviceId(uri));
            devices = (d == null) ? deviceService.getDevices() : Collections.singletonList(d);
        }

        for(Device d : devices) {
            if(flowEntryState == null) {
                flowList = newArrayList(flowRuleService.getFlowEntries(d.id()));
            } else {
                flowList =  newArrayList();
                for(FlowEntry f : flowRuleService.getFlowEntries(d.id())) {
                    if(f.state().equals(flowEntryState)) {
                        flowList.add(f);
                    }
                }
            }
            flowList.sort(Comparators.FLOW_RULE_COMPARATOR);
            sortedFlows.put(d, flowList);
        }
        return sortedFlows;
    }

    /**
     * Produces a JSON array of flows grouped by the each device.
     *
     * @param devices     collection of devices to group flow by
     * @param flows       collection of flows per each device
     * @return JSON array
     */
    private JsonNode json(Iterable<Device> devices,
                          Map<Device, List<FlowEntry>> flows) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode result = mapper.createArrayNode();
        for (Device device : devices) {
            result.add(json(mapper, device, flows.get(device)));
        }
        return result;
    }

    // Produces JSON object with the flows of the given device.
    private ObjectNode json(ObjectMapper mapper,
                            Device device, List<FlowEntry> flows) {
        ObjectNode result = mapper.createObjectNode();
        ArrayNode array = mapper.createArrayNode();

        flows.forEach(flow -> array.add(jsonForEntity(flow, FlowEntry.class)));

        result.put("device", device.id().toString())
                .put("flowCount", flows.size())
                .set("flows", array);
        return result;
    }

    /**
     * Prints flows.
     *
     * @param d     the device
     * @param flows the set of flows for that device
     * @param coreService core system service
     */
    protected void printFlows(Device d, List<FlowEntry> flows,
                              CoreService coreService) {
        boolean empty = flows == null || flows.isEmpty();
        print("deviceId=%s, flowRuleCount=%d", d.id(), empty ? 0 : flows.size());
        if (!empty) {
            for (FlowEntry f : flows) {
                ApplicationId appId = coreService.getAppId(f.appId());
                print(FMT, Long.toHexString(f.id().value()), f.state(),
                      f.bytes(), f.packets(), f.life(), f.priority(), f.tableId(),
                      appId != null ? appId.name() : "<none>",
                      f.payLoad() == null ? null : f.payLoad().payLoad().toString());
                print(SFMT, f.selector().criteria());
                print(TFMT, f.treatment());
            }
        }
    }



    /*
    private void printMetric(String name, Metric metric) {
        final String heading;

        if (metric instanceof Counter) {
//            heading = format("-- %s : [%s] --", name, "Counter");
//            print(heading);
            Counter counter = (Counter) metric;
            print("          count = %d", counter.getCount());

        } else if (metric instanceof Gauge) {
//            heading = format("-- %s : [%s] --", name, "Gauge");
//            print(heading);
            @SuppressWarnings("rawtypes")
            Gauge gauge = (Gauge) metric;
            final Object value = gauge.getValue();
            if (name.endsWith("EpochMs") && value instanceof Long) {
//                print("          value = %s (%s)", value, new LocalDateTime(value));
            } else {
                print("          value = %s", value);
            }

        } else if (metric instanceof Histogram) {
//            heading = format("-- %s : [%s] --", name, "Histogram");
//            print(heading);
            final Histogram histogram = (Histogram) metric;
            final Snapshot snapshot = histogram.getSnapshot();
            print("          count = %d", histogram.getCount());
            print("            min = %d", snapshot.getMin());
            print("            max = %d", snapshot.getMax());
            print("           mean = %f", snapshot.getMean());
            print("         stddev = %f", snapshot.getStdDev());

        } else if (metric instanceof Meter) {
//            heading = format("-- %s : [%s] --", name, "Meter");
//            print(heading);
            final Meter meter = (Meter) metric;
            print("          count = %d", meter.getCount());
            print("      mean rate = %f", meter.getMeanRate());
            print("  1-minute rate = %f", meter.getOneMinuteRate());
            print("  5-minute rate = %f", meter.getFiveMinuteRate());
            print(" 15-minute rate = %f", meter.getFifteenMinuteRate());

        } else if (metric instanceof Timer) {
//            heading = format("-- %s : [%s] --", name, "Timer");
//            print(heading);
            final Timer timer = (Timer) metric;
            final Snapshot snapshot = timer.getSnapshot();
            print("          count = %d", timer.getCount());
            print("      mean rate = %f per second", timer.getMeanRate());
            print("  1-minute rate = %f per second", timer.getOneMinuteRate());
            print("  5-minute rate = %f per second", timer.getFiveMinuteRate());
            print(" 15-minute rate = %f per second", timer.getFifteenMinuteRate());
            print("            min = %f ms", nanoToMs(snapshot.getMin()));
            print("            max = %f ms", nanoToMs(snapshot.getMax()));
            print("           mean = %f ms", nanoToMs(snapshot.getMean()));
            print("         stddev = %f ms", nanoToMs(snapshot.getStdDev()));
        } else {
//            heading = format("-- %s : [%s] --", name, metric.getClass().getCanonicalName());
//            print(heading);
            print("Unknown Metric type:{}", metric.getClass().getCanonicalName());
        }
//        print(Strings.repeat("-", heading.length()));
    }

    */

    @SuppressWarnings("rawtypes")
    private TreeMultimap<String, Metric> listMetrics(MetricsService metricsService, MetricFilter filter) {
        TreeMultimap<String, Metric> metrics = TreeMultimap.create(Comparator.naturalOrder(), Ordering.arbitrary());

        Map<String, Counter> counters = metricsService.getCounters(filter);
        for (Entry<String, Counter> entry : counters.entrySet()) {
            metrics.put(entry.getKey(), entry.getValue());
        }
        Map<String, Gauge> gauges = metricsService.getGauges(filter);
        for (Entry<String, Gauge> entry : gauges.entrySet()) {
            metrics.put(entry.getKey(), entry.getValue());
        }
        Map<String, Histogram> histograms = metricsService.getHistograms(filter);
        for (Entry<String, Histogram> entry : histograms.entrySet()) {
            metrics.put(entry.getKey(), entry.getValue());
        }
        Map<String, Meter> meters = metricsService.getMeters(filter);
        for (Entry<String, Meter> entry : meters.entrySet()) {
            metrics.put(entry.getKey(), entry.getValue());
        }
        Map<String, Timer> timers = metricsService.getTimers(filter);
        for (Entry<String, Timer> entry : timers.entrySet()) {
            metrics.put(entry.getKey(), entry.getValue());
        }

        return metrics;
    }

    private double nanoToMs(double nano) {
        return nano / 1_000_000D;
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
