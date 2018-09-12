package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/8/18.
 */

public class StorageParamsBean {
    private StorageDetailBean storageDetailBean;
    private StorageBean storageBean;
    private String storageType;
    private String todayRevenue;
    private String totalRevenue;
    private int activePower;
    private int apparentPower;
    public static class StorageDetailBean{
        private String serialNum; // 序列号
        private String pChargeText;
        private String pDischargeText;
        private String dataLogSn;
        private String alias;
        private int address;
        private boolean withTime; // 发来的数据中是否自带有时间
        private int status = -1; // Storage Status 0: Operating, 1: Charge, 2:
        // Discharge, 3: Fault, 4: Flash
        private double pCharge; // 充电功率 startIndex: 14
        private double pDischarge; // 放电功率 startIndex: 9
        private float vpv; // 输入PV电压 startIndex: 3
        private float ipv; // 输入PV电流 startIndex: 4
        private float iCharge; // PV端充电电流
        private float iDischarge; // PV端放电电流
        private double ppv; // 面板输入功率
        private float vBuck; // VBUCK
        private float vac; // 电网电压
        private float iacToUser; // 用户侧电流
        private double pacToUser; // 用户侧功率
        private float iacToGrid; // 电网侧电流
        private double pacToGrid; // 电网侧功率
        private float vBat; // 电池电压
        private int capacity; // 电池容量（百分比）
        private float temperature; // 储能机温度
        private float ipmTemperature; // The inside IPM in storage Temperature
        private int errorCode;// 错误码 20141225 add
        private int warnCode;// 警告码 20141225 add

        private int dischargeToStandbyReason;// jehone add 20150130 放电到待机状态原因
        private int chargeToStandbyReason;// jehone add 20150130 充电到待机状态原因
        private int bmsStatus;// jehone add 20150130
        private int bmsError;// jehone add 20150130
        private int gaugeBattteryStatus;// jehone add 20150130
        private int gaugeOperationStatus;// jehone add 20150130
        private int gaugePackStatus;// jehone add 20150130
        private int cycleCount;// jehone add 20150130
        private int maxChargeOrDischargeCurrent;// jehone add 20150130
        private int bmsCurrent;// jehone add 20150130
        private int bmsTemperature;// jehone add 20150130
        private int gaugeICCurrent;// jehone add 20150130
        private int gaugeRM1;// jehone add 20150130
        private int gaugeRM2;// jehone add 20150130

        private float vBus; // jehone add 20150506
        private float batTemp;// jehone add20150506

        private int normalPower;
        private int remoteCntlEn;

        // 20160907 增加SP3000属性
        private float vpv2; // 输入PV电压
        private double ppv2; // 面板输入功率
        private double pCharge2; // 充电功率2
        private double pDischarge2; // 充电功率2
        private float vBuck2;// vBuck
        private double epvToday2; // 当天面板电量
        private double epvTotal2; // 面板累计电量
        private double eChargeToday2; // 今日充电
        private double eChargeTotal2; // 累计充电
        private double eDischargeToday2; // 今日放电
        private double eDischargeTotal2; // 累计放电
        // private int remoteworkmode;// 1:充电 2：放电
        private int remoteCntlFailReason;// 1：未使能 2：SP状态异常
        private boolean isAgain;// 是否为续传
        private int deviceType;// 0:SP2000,1:SP3000
        private String innerCWCode;//对内错误&警告 SP3000
        private int faultCode; // 错误码
        private double epvToday; // 当天面板电量
        private double epvTotal; // 总面板电量
        private double eChargeToday; // 当天充电能量
        private double eChargeTotal; // 总共充电能量
        private double eDischargeToday; // 当天放电能量
        private double eDischargeTotal; // 总共放电能量
        private double eToUserToday; // 当天（电网-用户）多少电
        private double eToUserTotal; // 总共（电网-用户）多少电
        private double eToGridToday; // 当天（用户-电网）多少电
        private double eToGridTotal; // 总共（用户-电网）多少电
        private boolean lost = true; // 是否通讯丢失
        private int chargeWay;// ChargeWay   0: cChargeByPV 1: cChargeByAC


        // SPF5000特有参数

        private float iChargePV1;// iChargePV1 / PV1 charge current
        private float iChargePV2;// iChargePV2/ PV2 charge current
        private double outPutPower;// outPutPower
        private double pAcCharge;// acChargePower  pAcCharge
        private float vGrid;// grid voltage
        private float freqGrid;// Grid frequency
        private float outPutVolt;// out put voltage
        private float freqOutPut;// out put frequency
        private float loadPercent;// LoadPercent
        private float outPutCurrent;// outPutCurrent
        private double eacChargeToday;// eacChargeTotal AC charge Energy today
        private double eacChargeTotal;// eacChargeTotal AC charge Energy total
        private double eBatDisChargeToday;// eBatDisChargeToday Battery discharge Energy today
        private double eBatDisChargeTotal;// eBatDisChargeToTal Battery discharge Energy Total
        private double eacDisChargeToday;// eacDisChargeToday AC discharge Energy today
        private double eacDisChargeTotal;// eacDisChargeTotal AC discharge Energy total
        private double iAcCharge;		//ac充电电流
        private double pAcInPut;		//ac输入功率
        private double pBat;			//bat power

        public String getpChargeText() {
            return pChargeText;
        }

        public void setpChargeText(String pChargeText) {
            this.pChargeText = pChargeText;
        }

        public String getpDischargeText() {
            return pDischargeText;
        }

        public void setpDischargeText(String pDischargeText) {
            this.pDischargeText = pDischargeText;
        }

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public String getDataLogSn() {
            return dataLogSn;
        }

        public void setDataLogSn(String dataLogSn) {
            this.dataLogSn = dataLogSn;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public boolean isWithTime() {
            return withTime;
        }

        public void setWithTime(boolean withTime) {
            this.withTime = withTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getpCharge() {
            return pCharge;
        }

        public void setpCharge(double pCharge) {
            this.pCharge = pCharge;
        }

        public double getpDischarge() {
            return pDischarge;
        }

        public void setpDischarge(double pDischarge) {
            this.pDischarge = pDischarge;
        }

        public float getVpv() {
            return vpv;
        }

        public void setVpv(float vpv) {
            this.vpv = vpv;
        }

        public float getIpv() {
            return ipv;
        }

        public void setIpv(float ipv) {
            this.ipv = ipv;
        }

        public float getiCharge() {
            return iCharge;
        }

        public void setiCharge(float iCharge) {
            this.iCharge = iCharge;
        }

        public float getiDischarge() {
            return iDischarge;
        }

        public void setiDischarge(float iDischarge) {
            this.iDischarge = iDischarge;
        }

        public double getPpv() {
            return ppv;
        }

        public void setPpv(double ppv) {
            this.ppv = ppv;
        }

        public float getvBuck() {
            return vBuck;
        }

        public void setvBuck(float vBuck) {
            this.vBuck = vBuck;
        }

        public float getVac() {
            return vac;
        }

        public void setVac(float vac) {
            this.vac = vac;
        }

        public float getIacToUser() {
            return iacToUser;
        }

        public void setIacToUser(float iacToUser) {
            this.iacToUser = iacToUser;
        }

        public double getPacToUser() {
            return pacToUser;
        }

        public void setPacToUser(double pacToUser) {
            this.pacToUser = pacToUser;
        }

        public float getIacToGrid() {
            return iacToGrid;
        }

        public void setIacToGrid(float iacToGrid) {
            this.iacToGrid = iacToGrid;
        }

        public double getPacToGrid() {
            return pacToGrid;
        }

        public void setPacToGrid(double pacToGrid) {
            this.pacToGrid = pacToGrid;
        }

        public float getvBat() {
            return vBat;
        }

        public void setvBat(float vBat) {
            this.vBat = vBat;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getIpmTemperature() {
            return ipmTemperature;
        }

        public void setIpmTemperature(float ipmTemperature) {
            this.ipmTemperature = ipmTemperature;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public int getWarnCode() {
            return warnCode;
        }

        public void setWarnCode(int warnCode) {
            this.warnCode = warnCode;
        }

        public int getDischargeToStandbyReason() {
            return dischargeToStandbyReason;
        }

        public void setDischargeToStandbyReason(int dischargeToStandbyReason) {
            this.dischargeToStandbyReason = dischargeToStandbyReason;
        }

        public int getChargeToStandbyReason() {
            return chargeToStandbyReason;
        }

        public void setChargeToStandbyReason(int chargeToStandbyReason) {
            this.chargeToStandbyReason = chargeToStandbyReason;
        }

        public int getBmsStatus() {
            return bmsStatus;
        }

        public void setBmsStatus(int bmsStatus) {
            this.bmsStatus = bmsStatus;
        }

        public int getBmsError() {
            return bmsError;
        }

        public void setBmsError(int bmsError) {
            this.bmsError = bmsError;
        }

        public int getGaugeBattteryStatus() {
            return gaugeBattteryStatus;
        }

        public void setGaugeBattteryStatus(int gaugeBattteryStatus) {
            this.gaugeBattteryStatus = gaugeBattteryStatus;
        }

        public int getGaugeOperationStatus() {
            return gaugeOperationStatus;
        }

        public void setGaugeOperationStatus(int gaugeOperationStatus) {
            this.gaugeOperationStatus = gaugeOperationStatus;
        }

        public int getGaugePackStatus() {
            return gaugePackStatus;
        }

        public void setGaugePackStatus(int gaugePackStatus) {
            this.gaugePackStatus = gaugePackStatus;
        }

        public int getCycleCount() {
            return cycleCount;
        }

        public void setCycleCount(int cycleCount) {
            this.cycleCount = cycleCount;
        }

        public int getMaxChargeOrDischargeCurrent() {
            return maxChargeOrDischargeCurrent;
        }

        public void setMaxChargeOrDischargeCurrent(int maxChargeOrDischargeCurrent) {
            this.maxChargeOrDischargeCurrent = maxChargeOrDischargeCurrent;
        }

        public int getBmsCurrent() {
            return bmsCurrent;
        }

        public void setBmsCurrent(int bmsCurrent) {
            this.bmsCurrent = bmsCurrent;
        }

        public int getBmsTemperature() {
            return bmsTemperature;
        }

        public void setBmsTemperature(int bmsTemperature) {
            this.bmsTemperature = bmsTemperature;
        }

        public int getGaugeICCurrent() {
            return gaugeICCurrent;
        }

        public void setGaugeICCurrent(int gaugeICCurrent) {
            this.gaugeICCurrent = gaugeICCurrent;
        }

        public int getGaugeRM1() {
            return gaugeRM1;
        }

        public void setGaugeRM1(int gaugeRM1) {
            this.gaugeRM1 = gaugeRM1;
        }

        public int getGaugeRM2() {
            return gaugeRM2;
        }

        public void setGaugeRM2(int gaugeRM2) {
            this.gaugeRM2 = gaugeRM2;
        }

        public float getvBus() {
            return vBus;
        }

        public void setvBus(float vBus) {
            this.vBus = vBus;
        }

        public float getBatTemp() {
            return batTemp;
        }

        public void setBatTemp(float batTemp) {
            this.batTemp = batTemp;
        }

        public int getNormalPower() {
            return normalPower;
        }

        public void setNormalPower(int normalPower) {
            this.normalPower = normalPower;
        }

        public int getRemoteCntlEn() {
            return remoteCntlEn;
        }

        public void setRemoteCntlEn(int remoteCntlEn) {
            this.remoteCntlEn = remoteCntlEn;
        }

        public float getVpv2() {
            return vpv2;
        }

        public void setVpv2(float vpv2) {
            this.vpv2 = vpv2;
        }

        public double getPpv2() {
            return ppv2;
        }

        public void setPpv2(double ppv2) {
            this.ppv2 = ppv2;
        }

        public double getpCharge2() {
            return pCharge2;
        }

        public void setpCharge2(double pCharge2) {
            this.pCharge2 = pCharge2;
        }

        public double getpDischarge2() {
            return pDischarge2;
        }

        public void setpDischarge2(double pDischarge2) {
            this.pDischarge2 = pDischarge2;
        }

        public float getvBuck2() {
            return vBuck2;
        }

        public void setvBuck2(float vBuck2) {
            this.vBuck2 = vBuck2;
        }

        public double getEpvToday2() {
            return epvToday2;
        }

        public void setEpvToday2(double epvToday2) {
            this.epvToday2 = epvToday2;
        }

        public double getEpvTotal2() {
            return epvTotal2;
        }

        public void setEpvTotal2(double epvTotal2) {
            this.epvTotal2 = epvTotal2;
        }

        public double geteChargeToday2() {
            return eChargeToday2;
        }

        public void seteChargeToday2(double eChargeToday2) {
            this.eChargeToday2 = eChargeToday2;
        }

        public double geteChargeTotal2() {
            return eChargeTotal2;
        }

        public void seteChargeTotal2(double eChargeTotal2) {
            this.eChargeTotal2 = eChargeTotal2;
        }

        public double geteDischargeToday2() {
            return eDischargeToday2;
        }

        public void seteDischargeToday2(double eDischargeToday2) {
            this.eDischargeToday2 = eDischargeToday2;
        }

        public double geteDischargeTotal2() {
            return eDischargeTotal2;
        }

        public void seteDischargeTotal2(double eDischargeTotal2) {
            this.eDischargeTotal2 = eDischargeTotal2;
        }

        public int getRemoteCntlFailReason() {
            return remoteCntlFailReason;
        }

        public void setRemoteCntlFailReason(int remoteCntlFailReason) {
            this.remoteCntlFailReason = remoteCntlFailReason;
        }

        public boolean isAgain() {
            return isAgain;
        }

        public void setAgain(boolean again) {
            isAgain = again;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public String getInnerCWCode() {
            return innerCWCode;
        }

        public void setInnerCWCode(String innerCWCode) {
            this.innerCWCode = innerCWCode;
        }

        public int getFaultCode() {
            return faultCode;
        }

        public void setFaultCode(int faultCode) {
            this.faultCode = faultCode;
        }

        public double getEpvToday() {
            return epvToday;
        }

        public void setEpvToday(double epvToday) {
            this.epvToday = epvToday;
        }

        public double getEpvTotal() {
            return epvTotal;
        }

        public void setEpvTotal(double epvTotal) {
            this.epvTotal = epvTotal;
        }

        public double geteChargeToday() {
            return eChargeToday;
        }

        public void seteChargeToday(double eChargeToday) {
            this.eChargeToday = eChargeToday;
        }

        public double geteChargeTotal() {
            return eChargeTotal;
        }

        public void seteChargeTotal(double eChargeTotal) {
            this.eChargeTotal = eChargeTotal;
        }

        public double geteDischargeToday() {
            return eDischargeToday;
        }

        public void seteDischargeToday(double eDischargeToday) {
            this.eDischargeToday = eDischargeToday;
        }

        public double geteDischargeTotal() {
            return eDischargeTotal;
        }

        public void seteDischargeTotal(double eDischargeTotal) {
            this.eDischargeTotal = eDischargeTotal;
        }

        public double geteToUserToday() {
            return eToUserToday;
        }

        public void seteToUserToday(double eToUserToday) {
            this.eToUserToday = eToUserToday;
        }

        public double geteToUserTotal() {
            return eToUserTotal;
        }

        public void seteToUserTotal(double eToUserTotal) {
            this.eToUserTotal = eToUserTotal;
        }

        public double geteToGridToday() {
            return eToGridToday;
        }

        public void seteToGridToday(double eToGridToday) {
            this.eToGridToday = eToGridToday;
        }

        public double geteToGridTotal() {
            return eToGridTotal;
        }

        public void seteToGridTotal(double eToGridTotal) {
            this.eToGridTotal = eToGridTotal;
        }

        public boolean isLost() {
            return lost;
        }

        public void setLost(boolean lost) {
            this.lost = lost;
        }

        public int getChargeWay() {
            return chargeWay;
        }

        public void setChargeWay(int chargeWay) {
            this.chargeWay = chargeWay;
        }

        public float getiChargePV1() {
            return iChargePV1;
        }

        public void setiChargePV1(float iChargePV1) {
            this.iChargePV1 = iChargePV1;
        }

        public float getiChargePV2() {
            return iChargePV2;
        }

        public void setiChargePV2(float iChargePV2) {
            this.iChargePV2 = iChargePV2;
        }

        public double getOutPutPower() {
            return outPutPower;
        }

        public void setOutPutPower(double outPutPower) {
            this.outPutPower = outPutPower;
        }

        public double getpAcCharge() {
            return pAcCharge;
        }

        public void setpAcCharge(double pAcCharge) {
            this.pAcCharge = pAcCharge;
        }

        public float getvGrid() {
            return vGrid;
        }

        public void setvGrid(float vGrid) {
            this.vGrid = vGrid;
        }

        public float getFreqGrid() {
            return freqGrid;
        }

        public void setFreqGrid(float freqGrid) {
            this.freqGrid = freqGrid;
        }

        public float getOutPutVolt() {
            return outPutVolt;
        }

        public void setOutPutVolt(float outPutVolt) {
            this.outPutVolt = outPutVolt;
        }

        public float getFreqOutPut() {
            return freqOutPut;
        }

        public void setFreqOutPut(float freqOutPut) {
            this.freqOutPut = freqOutPut;
        }

        public float getLoadPercent() {
            return loadPercent;
        }

        public void setLoadPercent(float loadPercent) {
            this.loadPercent = loadPercent;
        }

        public float getOutPutCurrent() {
            return outPutCurrent;
        }

        public void setOutPutCurrent(float outPutCurrent) {
            this.outPutCurrent = outPutCurrent;
        }

        public double getEacChargeToday() {
            return eacChargeToday;
        }

        public void setEacChargeToday(double eacChargeToday) {
            this.eacChargeToday = eacChargeToday;
        }

        public double getEacChargeTotal() {
            return eacChargeTotal;
        }

        public void setEacChargeTotal(double eacChargeTotal) {
            this.eacChargeTotal = eacChargeTotal;
        }

        public double geteBatDisChargeToday() {
            return eBatDisChargeToday;
        }

        public void seteBatDisChargeToday(double eBatDisChargeToday) {
            this.eBatDisChargeToday = eBatDisChargeToday;
        }

        public double geteBatDisChargeTotal() {
            return eBatDisChargeTotal;
        }

        public void seteBatDisChargeTotal(double eBatDisChargeTotal) {
            this.eBatDisChargeTotal = eBatDisChargeTotal;
        }

        public double getEacDisChargeToday() {
            return eacDisChargeToday;
        }

        public void setEacDisChargeToday(double eacDisChargeToday) {
            this.eacDisChargeToday = eacDisChargeToday;
        }

        public double getEacDisChargeTotal() {
            return eacDisChargeTotal;
        }

        public void setEacDisChargeTotal(double eacDisChargeTotal) {
            this.eacDisChargeTotal = eacDisChargeTotal;
        }

        public double getiAcCharge() {
            return iAcCharge;
        }

        public void setiAcCharge(double iAcCharge) {
            this.iAcCharge = iAcCharge;
        }

        public double getpAcInPut() {
            return pAcInPut;
        }

        public void setpAcInPut(double pAcInPut) {
            this.pAcInPut = pAcInPut;
        }

        public double getpBat() {
            return pBat;
        }

        public void setpBat(double pBat) {
            this.pBat = pBat;
        }
    }
    public static class StorageBean{

        private String serialNum; // 序列号
        private String modelText;
        private String portName; // 通讯口信息 通讯口类型和地址
        private String dataLogSn; // DataLog serial number
        private int groupId = -1; // Inverter分组
        private String alias; // 别名
        private String location; // 位置
        private int addr = 0; // Inverter地址
        private String fwVersion; // 固件版本
        private int model;// 模式
        private String innerVersion;// 内部版本号
        private boolean lost = true; // 是否通讯丢失
        private int status = -1; // Storage Status 0: Operating, 1: Charge, 2:
        // Discharge, 3: Fault, 4: Flash
        private String tcpServerIp; // TCP服务器Ip地址
        private boolean statusLed1;
        private boolean statusLed2;
        private boolean statusLed3;
        private boolean statusLed4;
        private boolean statusLed5;
        private boolean statusLed6;

        private int deviceType;// 设备类型，0:SP2000,1:SP3000,2:SPF5000

        //SPF5000
        private int batteryType;	//电池类型	0 Lead_Acid; 1 Lithium; 2 CustomLead_Acid; //20170504
        //20170705  SPF5000设置参数
        private int outputConfig;   //AC输出源      0: BAT First; 1: PV First; 2: UTI First;
        private int chargeConfig;   //充电源           0: PV first; 1: PV&UTI; 2: PV Only;
        private int utiOutStart;    //市电输出      0-23
        private int utiOutEnd;
        private int utiChargeStart; //市电充电      0-23
        private int utiChargeEnd;
        private int pvModel;        //光伏接入模式   0:Independent; 1: Parallel;
        private int acInModel;      //AC输入模式      0: APL,90-280VAC; 1: UPS,170-280VAC;
        private int outputVoltType; //AC输出电压       0: 208VAC; 1: 230VAC 2: 240VAC
        private int outputFreqType; //AC输出频率       0: 50Hz; 1: 60Hz
        private int overLoadRestart;//过载重启     0:Yes; 1:No; 2: Swith to UTI;
        private int overTempRestart;//过温重启      0:Yes; 1:No;
        private int buzzerEN;       //蜂鸣器      1:Enable; 0:Disable;
        private int maxChargeCurr;  //最大充电电流
        private double batLowToUtiVolt; //电池低压点
        private double bulkChargeVolt; //充电恒压点
        private double floatChargeVolt; //浮充电压点
        private int rateWatt;//额定有功功率
        private int rateVA;//额定视在功率

        //是否正在升级
        private boolean updating;


        private double pCharge; // 充电功率 startIndex: 14
        private double pDischarge; // 放电功率 startIndex: 9

        public String getModelText() {
            return modelText;
        }

        public void setModelText(String modelText) {
            this.modelText = modelText;
        }

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public String getPortName() {
            return portName;
        }

        public void setPortName(String portName) {
            this.portName = portName;
        }

        public String getDataLogSn() {
            return dataLogSn;
        }

        public void setDataLogSn(String dataLogSn) {
            this.dataLogSn = dataLogSn;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getAddr() {
            return addr;
        }

        public void setAddr(int addr) {
            this.addr = addr;
        }

        public String getFwVersion() {
            return fwVersion;
        }

        public void setFwVersion(String fwVersion) {
            this.fwVersion = fwVersion;
        }

        public int getModel() {
            return model;
        }

        public void setModel(int model) {
            this.model = model;
        }

        public String getInnerVersion() {
            return innerVersion;
        }

        public void setInnerVersion(String innerVersion) {
            this.innerVersion = innerVersion;
        }

        public boolean isLost() {
            return lost;
        }

        public void setLost(boolean lost) {
            this.lost = lost;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTcpServerIp() {
            return tcpServerIp;
        }

        public void setTcpServerIp(String tcpServerIp) {
            this.tcpServerIp = tcpServerIp;
        }

        public boolean isStatusLed1() {
            return statusLed1;
        }

        public void setStatusLed1(boolean statusLed1) {
            this.statusLed1 = statusLed1;
        }

        public boolean isStatusLed2() {
            return statusLed2;
        }

        public void setStatusLed2(boolean statusLed2) {
            this.statusLed2 = statusLed2;
        }

        public boolean isStatusLed3() {
            return statusLed3;
        }

        public void setStatusLed3(boolean statusLed3) {
            this.statusLed3 = statusLed3;
        }

        public boolean isStatusLed4() {
            return statusLed4;
        }

        public void setStatusLed4(boolean statusLed4) {
            this.statusLed4 = statusLed4;
        }

        public boolean isStatusLed5() {
            return statusLed5;
        }

        public void setStatusLed5(boolean statusLed5) {
            this.statusLed5 = statusLed5;
        }

        public boolean isStatusLed6() {
            return statusLed6;
        }

        public void setStatusLed6(boolean statusLed6) {
            this.statusLed6 = statusLed6;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public int getBatteryType() {
            return batteryType;
        }

        public void setBatteryType(int batteryType) {
            this.batteryType = batteryType;
        }

        public int getOutputConfig() {
            return outputConfig;
        }

        public void setOutputConfig(int outputConfig) {
            this.outputConfig = outputConfig;
        }

        public int getChargeConfig() {
            return chargeConfig;
        }

        public void setChargeConfig(int chargeConfig) {
            this.chargeConfig = chargeConfig;
        }

        public int getUtiOutStart() {
            return utiOutStart;
        }

        public void setUtiOutStart(int utiOutStart) {
            this.utiOutStart = utiOutStart;
        }

        public int getUtiOutEnd() {
            return utiOutEnd;
        }

        public void setUtiOutEnd(int utiOutEnd) {
            this.utiOutEnd = utiOutEnd;
        }

        public int getUtiChargeStart() {
            return utiChargeStart;
        }

        public void setUtiChargeStart(int utiChargeStart) {
            this.utiChargeStart = utiChargeStart;
        }

        public int getUtiChargeEnd() {
            return utiChargeEnd;
        }

        public void setUtiChargeEnd(int utiChargeEnd) {
            this.utiChargeEnd = utiChargeEnd;
        }

        public int getPvModel() {
            return pvModel;
        }

        public void setPvModel(int pvModel) {
            this.pvModel = pvModel;
        }

        public int getAcInModel() {
            return acInModel;
        }

        public void setAcInModel(int acInModel) {
            this.acInModel = acInModel;
        }

        public int getOutputVoltType() {
            return outputVoltType;
        }

        public void setOutputVoltType(int outputVoltType) {
            this.outputVoltType = outputVoltType;
        }

        public int getOutputFreqType() {
            return outputFreqType;
        }

        public void setOutputFreqType(int outputFreqType) {
            this.outputFreqType = outputFreqType;
        }

        public int getOverLoadRestart() {
            return overLoadRestart;
        }

        public void setOverLoadRestart(int overLoadRestart) {
            this.overLoadRestart = overLoadRestart;
        }

        public int getOverTempRestart() {
            return overTempRestart;
        }

        public void setOverTempRestart(int overTempRestart) {
            this.overTempRestart = overTempRestart;
        }

        public int getBuzzerEN() {
            return buzzerEN;
        }

        public void setBuzzerEN(int buzzerEN) {
            this.buzzerEN = buzzerEN;
        }

        public int getMaxChargeCurr() {
            return maxChargeCurr;
        }

        public void setMaxChargeCurr(int maxChargeCurr) {
            this.maxChargeCurr = maxChargeCurr;
        }

        public double getBatLowToUtiVolt() {
            return batLowToUtiVolt;
        }

        public void setBatLowToUtiVolt(double batLowToUtiVolt) {
            this.batLowToUtiVolt = batLowToUtiVolt;
        }


        public double getBulkChargeVolt() {
            return bulkChargeVolt;
        }

        public void setBulkChargeVolt(double bulkChargeVolt) {
            this.bulkChargeVolt = bulkChargeVolt;
        }

        public double getFloatChargeVolt() {
            return floatChargeVolt;
        }

        public void setFloatChargeVolt(double floatChargeVolt) {
            this.floatChargeVolt = floatChargeVolt;
        }

        public int getRateWatt() {
            return rateWatt;
        }

        public void setRateWatt(int rateWatt) {
            this.rateWatt = rateWatt;
        }

        public int getRateVA() {
            return rateVA;
        }

        public void setRateVA(int rateVA) {
            this.rateVA = rateVA;
        }

        public boolean isUpdating() {
            return updating;
        }

        public void setUpdating(boolean updating) {
            this.updating = updating;
        }

        public double getpCharge() {
            return pCharge;
        }

        public void setpCharge(double pCharge) {
            this.pCharge = pCharge;
        }

        public double getpDischarge() {
            return pDischarge;
        }

        public void setpDischarge(double pDischarge) {
            this.pDischarge = pDischarge;
        }
    }

    public StorageDetailBean getStorageDetailBean() {
        return storageDetailBean;
    }

    public void setStorageDetailBean(StorageDetailBean storageDetailBean) {
        this.storageDetailBean = storageDetailBean;
    }

    public StorageBean getStorageBean() {
        return storageBean;
    }

    public void setStorageBean(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(String todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getActivePower() {
        return activePower;
    }

    public void setActivePower(int activePower) {
        this.activePower = activePower;
    }

    public int getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(int apparentPower) {
        this.apparentPower = apparentPower;
    }
}
