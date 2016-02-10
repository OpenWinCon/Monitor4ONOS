package org.onosproject;

import org.onosproject.cluster.NodeId;
import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;

import java.util.Set;

/**
 * Created by hansangyun on 15. 12. 17.
 */
public class EvacueeDevices {
    private static EvacueeDevices SingletonEvacueeDeviceList = new EvacueeDevices();

    protected Set<DeviceId> DeviceList;
    protected NodeId RollBackNodeId;

    static EvacueeDevices getSingletonEvacueeDeviceList() {
        if(SingletonEvacueeDeviceList == null) {
            SingletonEvacueeDeviceList = new EvacueeDevices();
        }
        return SingletonEvacueeDeviceList;
    }

    public Set<DeviceId> getDevices() {
        return DeviceList;
    }

    public void setDevices(Set<DeviceId> deviceList) {
        this.DeviceList = deviceList;
    }

    public NodeId getRollBackNodeId() {
        return this.RollBackNodeId;
    }

    public void setRollBackNodeId(NodeId nodeid) {
        this.RollBackNodeId = nodeid;
    }
}
