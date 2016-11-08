/**
 *    Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior
 *    University
 *
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 **/

package org.onosproject.floodlightpof.protocol;

import org.onosproject.floodlightpof.protocol.table.OFFlowTableResource;
import org.onosproject.floodlightpof.protocol.table.OFTableMod;

import java.lang.reflect.Constructor;

/**
 * Modified by Song Jian (jack.songjian@huawei.com), Huawei Technologies Co., Ltd.
 * Modified Based on POF white paper.
 *      Original OFType include:
 *          HELLO,
 *          ERROR,
 *          ECHO_REQUEST,
 *          ECHO_REPLY
 *          VENDOR,
 *
 *          FEATURES_REQUEST,
 *          FEATURE_REPLY,
 *          GET_CONFIG_REQEUST,
 *          GET_CONFIG_REPLY
 *          SET_CONFIG,
 *
 *          PACKET_IN,
 *          FLOW_REMOVED,
 *          PORT_STATUS,
 *
 *          PACKET_OUT,
 *          FLOW_MOD,
 *          PORT_MOD,
 *
 *          STATS_REQUEST,
 *          STATS_REPLY,
 *
 *          BARRIER_REQEUST,
 *          BARRIER_REPLY,
 *
 *      Based on POF white paper, OFType include:
 *          HELLO,
 *          ERROR,
 *          ECHO_REQUEST,
 *          ECHO_REPLY
 *          EXPERIMENTER,       //changed the name from VENDOR
 *
 *          FEATURES_REQUEST,
 *          FEATURE_REPLY,
 *          GET_CONFIG_REQEUST,
 *          GET_CONFIG_REPLY
 *          SET_CONFIG,
 *
 *          PACKET_IN,
 *          FLOW_REMOVED,
 *          PORT_STATUS,
 *          RESOURCE_REPORT,    //new added, for switch report switch's resources, e.g. tables resource
 *
 *          PACKET_OUT,
 *          FLOW_MOD,
 *          GROUP_MOD,          //new added, based on OpenFlow 1.3
 *          PORT_MOD,
 *          TABLE_MOD,          //new added, controller could create/delete tables
 *
 *          MULTIPART_REQUEST,   //changed name from STATS_REQUEST
 *          MULTIPART_REPLY,    //changed name from STATS_REPLY
 *
 *          BARRIER_REQEUST,
 *          BARRIER_REPLY,
 *
 *          QUEUE_GET_CONFIG_REQUEST,   //new added, based on OpenFlow 1.3, not used in POF yet
 *          QUEUE_GET_CONFIG_REPLY,     //new added, based on OpenFlow 1.3, not used in POF yet
 *
 *          ROLE_REQUEST,               //new added, based on OpenFlow 1.3, not used in POF yet
 *          ROLE_REPLY,                 //new added, based on OpenFlow 1.3, not used in POF yet
 *
 *          GET_ASYNC_REQUEST,          //new added, based on OpenFlow 1.3, not used in POF yet
 *          GET_ASYNC_REPLY,            //new added, based on OpenFlow 1.3, not used in POF yet
 *          SET_ASYNC,                  //new added, based on OpenFlow 1.3, not used in POF yet
 *
 *          METER_MOD,          //new added, to meter and limit the flow rate
 *
 *          COUNTER_MOD,        //new added, new/reset the counter
 *          COUNTER_REQUEST,    //new added, to request the counter from switch
 *          COUNTER_REPLY       //new added, to get the counter from switch
 *
 */

/**
 * List of OpenFlow types and mappings to wire protocol value and derived
 * classes.
 *
 *
 */
public enum OFType {
    /* Immutable messages. */
    HELLO(0, OFHello.class, new OFHelloInstantiable()),
    ERROR(1, OFError.class, new OFErrorInstantiable()),
    ECHO_REQUEST(2, OFEchoRequest.class, new OFEchoRequestInstantiable()),
    ECHO_REPLY(3, OFEchoReply.class, new OFEchoReplyInstantiable()),

    EXPERIMENTER(4, OFExperimenter
            .class, new OFExperimenterInstantiable()), //VENDOR has to change into EXPERIMENTR after OpenFlow 1.1

    /* Switch configuration messages. */
    FEATURES_REQUEST(5, OFFeaturesRequest.class, new OFFeaturesRequestInstantiable()),
    FEATURES_REPLY(6, OFFeaturesReply.class, new OFFeaturesReplyInstantiable()),
    GET_CONFIG_REQUEST(7, OFGetConfigRequest.class, new OFGetConfigRequestInstantiable()),
    GET_CONFIG_REPLY(8, OFGetConfigReply.class, new OFGetConfigReplyInstantiable()),
    SET_CONFIG(9, OFSetConfig.class, new OFSetConfigInstantiable()),

    /* Asynchronous messages. */
    PACKET_IN(10, OFPacketIn.class, new OFPacketInInstantiable()),
    FLOW_REMOVED(11, OFFlowRemoved.class, new OFFlowRemovedInstantiable()),
    PORT_STATUS(12, OFPortStatus.class, new OFPortStatusInstantiable()),
    RESOURCE_REPORT(13, OFFlowTableResource.class, new OFFlowTableResourceInstantiable()),

    /* Controller command messages. */
    PACKET_OUT(14, OFPacketOut.class, new OFPacketOutInstantiable()),
    FLOW_MOD(15, OFFlowMod.class, new OFFlowModInstantiable()),
    GROUP_MOD(16, OFGroupMod.class, new OFGroupModInstantiable()),
    PORT_MOD(17, OFPortStatus.class, new OFPortStatusInstantiable()),
    TABLE_MOD(18, OFTableMod.class, new OFTableModInstantiable()),

    /* Multipart messages. */
    MULTIPART_REQUEST(19, OFMultipartRequest.class, new OFMultipartRequestInstantiable()),
    MULTIPART_REPLY(20, OFMultipartReply.class, new OFMultipartReplyInstantiable()),

    /* Barrier messages. */
    BARRIER_REQUEST(21, OFBarrierRequest.class, new OFBarrierRequestInstantiable()),
    BARRIER_REPLY(22, OFBarrierReply.class, new OFBarrierReplyInstantiable()),

    /* Queue Configuration messages. */
    QUEUE_GET_CONFIG_REQUEST(23, OFQueueGetConfigRequest.class, new OFQueueGetConfigRequestInstantiable()),
    QUEUE_GET_CONFIG_REPLY(24, OFQueueGetConfigReply.class, new OFQueueGetConfigReplyInstantiable()),

    /* Controller role change request messages. */
    ROLE_REQUEST(25, OFRoleRequest.class, new OFRoleRequestInstantiable()),
    ROLE_REPLY(26, OFRoleReply.class, new OFRoleReplyInstantiable()),

    /* Asynchronous message configuration. */
    GET_ASYNC_REQUEST(27, OFGetAsyncRequest.class, new OFGetAsyncRequestInstantiable()),


    GET_ASYNC_REPLY(28, OFGetAsyncReply.class, new OFGetAsyncReplyInstantiable()),
    SET_ASYNC(29, OFSetAsync.class, new OFSetAsyncInstantiable()),


    /* Meters and rate limiters configuration messages. */
    METER_MOD(30, OFMeterMod.class, new OFMeterModInstantiable()),

    COUNTER_MOD(31, OFCounterMod.class, new OFCounterModInstantiable()),
    COUNTER_REQUEST(32, OFCounterRequest.class, new OFCounterRequestInstantiable()),
    COUNTER_REPLY(33, OFCounterReply.class, new OFCounterReplyInstantiable());

    static OFType[] mapping;

    protected Class<? extends OFMessage> clazz;
    protected Constructor<? extends OFMessage> constructor;
    protected Instantiable<OFMessage> instantiable;
    protected byte type;

    /**
     * Store some information about the OpenFlow type, including wire protocol
     * type number, length, and derived class.
     *
     * @param type Wire protocol number associated with this OFType
     * @param clazz The Java class corresponding to this type of OpenFlow
     *              message
     * @param instantiator An Instantiator<OFMessage> implementation that creates an
     *          instance of the specified OFMessage
     */
    OFType(int type, Class<? extends OFMessage> clazz, Instantiable<OFMessage> instantiator) {
        this.type = (byte) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFType.addMapping(this.type, this);
    }

    /**
     * Adds a mapping from type value to OFType enum.
     *
     * @param i OpenFlow wire protocol type
     * @param t type
     */
    public static void addMapping(byte i, OFType t) {
        if (mapping == null) {
            mapping = new OFType[64];
        }
        OFType.mapping[i] = t;
    }

    /**
     * Remove a mapping from type value to OFType enum.
     *
     * @param i OpenFlow wire protocol type
     */
    public static void removeMapping(byte i) {
        OFType.mapping[i] = null;
    }

    /**
     * Given a wire protocol OpenFlow type number, return the OFType associated
     * with it.
     *
     * @param i wire protocol number
     * @return OFType enum type
     */

    public static OFType valueOf(Byte i) {
        return OFType.mapping[i];
    }

    /**
     * @return Returns the wire protocol value corresponding to this OFType
     */
    public byte getTypeValue() {
        return this.type;
    }

    /**
     * @return return the OFMessage subclass corresponding to this OFType
     */
    public Class<? extends OFMessage> toClass() {
        return clazz;
    }

    /**
     * Returns the no-argument Constructor of the implementation class for
     * this OFType.
     * @return the constructor
     */
    public Constructor<? extends OFMessage> getConstructor() {
        return constructor;
    }

    /**
     * Returns a new instance of the OFMessage represented by this OFType.
     * @return the new object
     */
    public OFMessage newInstance() {
        return instantiable.instantiate();
    }

    /**
     * @return the instantiable
     */
    public Instantiable<OFMessage> getInstantiable() {
        return instantiable;
    }

    /**
     * @param instantiable the instantiable to set
     */
    public void setInstantiable(Instantiable<OFMessage> instantiable) {
        this.instantiable = instantiable;
    }

    public static class OFHelloInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFHello();
        } }

    public static class OFErrorInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFError();
        } }

    public static class OFEchoRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFEchoRequest();
        } }
    public static class OFEchoReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFEchoReply();
        } }


    public static class OFExperimenterInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFExperimenter();
        } }

    public static class OFFeaturesRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFFeaturesRequest();
        } }
    public static class OFFeaturesReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFFeaturesReply();
        } }
    public static class OFGetConfigRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFGetConfigRequest();
        } }
    public static class OFGetConfigReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFGetConfigReply();
        } }
    public static class OFSetConfigInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFSetConfig();
        } }
    public static class OFPacketInInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFPacketIn();
        } }
    public static class OFFlowRemovedInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFFlowRemoved();
        } }
    public static class OFPortStatusInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFPortStatus();
        } }
    public static class OFFlowTableResourceInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFFlowTableResource();
        } }
    public static class OFPacketOutInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFPacketOut();
        } }
    public static class OFFlowModInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFFlowMod();
        } }
    public static class OFGroupModInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFGroupMod();
        } }

    public static class OFTableModInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFTableMod();
        } }
    public static class OFMultipartRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFMultipartRequest();
        } }
    public static class OFMultipartReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFMultipartReply();
        } }
    public static class OFBarrierRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFBarrierRequest();
        } }

    public static class OFBarrierReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFBarrierReply();
        } }
    public static class OFQueueGetConfigRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFQueueGetConfigRequest();
        } }
    public static class OFQueueGetConfigReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFQueueGetConfigReply();
        } }
    public static class OFRoleRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFRoleRequest();
        } }
    public static class OFRoleReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFRoleReply();
        } }


    public static class OFGetAsyncRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFGetAsyncRequest();
        } }

    public static class OFGetAsyncReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFGetAsyncReply();
        } }

    public static class OFSetAsyncInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFSetAsync();
        } }

    public static class OFMeterModInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFMeterMod();
        } }

    public static class OFCounterModInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFCounterMod();
        } }

    public static class OFCounterRequestInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFCounterRequest();
        } }

    public static class OFCounterReplyInstantiable implements Instantiable<OFMessage> {
        @Override
        public OFMessage instantiate() {
            return new OFCounterReply();
        } }
}
