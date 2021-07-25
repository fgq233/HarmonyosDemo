package ohos.samples.serviceability.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.bundle.ElementName;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.IRemoteObject;
import ohos.rpc.RemoteException;
import ohos.samples.serviceability.RemoteAgentProxy;
import ohos.samples.serviceability.ResourceTable;

import java.security.SecureRandom;
import java.util.List;


public class MainAbilitySlice extends AbilitySlice {

    private static final String TAG = MainAbilitySlice.class.getSimpleName();

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, TAG);

    private static final int EVENT_ABILITY_CONNECT_DONE = 0x1000001;

    private static final int EVENT_ABILITY_DISCONNECT_DONE = 0x1000002;

    private static final String REMOTE_BUNDLE = "ohos.samples.serviceability";

    private static final String REMOTE_SERVICE = "RemoteAbility";

    private static final String LOCAL_BUNDLE = "ohos.samples.serviceability";

    private static final String NORMAL_SERVICE = "LocalServiceAbility";

    private static final String FOREGROUND_SERVICE = "ForegroundServiceAbility";

    private EventHandler eventHandler = new EventHandler(EventRunner.current()) {
        @Override
        protected void processEvent(InnerEvent event) {
            switch (event.eventId) {
                case EVENT_ABILITY_CONNECT_DONE:
                    showTips(MainAbilitySlice.this, "Service connect succeeded");
                    break;
                case EVENT_ABILITY_DISCONNECT_DONE:
                    showTips(MainAbilitySlice.this, "Service disconnect succeeded");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_main_slice);

        initComponents();
    }

    private void initComponents() {
        // 组件初始化
        Component startLocalBtn = findComponentById(ResourceTable.Id_start_local_btn);
        Component stopLocalBtn = findComponentById(ResourceTable.Id_stop_local_btn);
        Component connectLocalBtn = findComponentById(ResourceTable.Id_connect_local_btn);
        Component disconnectLocalBtn = findComponentById(ResourceTable.Id_disconnect_local_btn);
        Component keepRunningBtn = findComponentById(ResourceTable.Id_keep_run_btn);
        Component startRemoteBtn = findComponentById(ResourceTable.Id_start_remote_btn);
        Component stopRemoteBtn = findComponentById(ResourceTable.Id_stop_remote_btn);
        Component connectRemoteBtn = findComponentById(ResourceTable.Id_connect_remote_btn);
        Component disconnectRemoteBtn = findComponentById(ResourceTable.Id_disconnect_remote_btn);

        // 本地服务
        startLocalBtn.setClickedListener(component -> startService(false));
        stopLocalBtn.setClickedListener(component -> stopService(false));
        connectLocalBtn.setClickedListener(component -> connectService(false));
        disconnectLocalBtn.setClickedListener(component -> disconnectAbility(connection));

        keepRunningBtn.setClickedListener(component -> startForegroundService());

        // 远程服务
        startRemoteBtn.setClickedListener(component -> startService(true));
        connectRemoteBtn.setClickedListener(component -> connectService(true));
        stopRemoteBtn.setClickedListener(component -> stopService(true));
        disconnectRemoteBtn.setClickedListener(component -> disconnectAbility(connection));
    }

    /**
     * 启动服务
     *
     * @param isRemote 是否远程
     */
    private void startService(boolean isRemote) {
        startAbility(getServiceIntent(isRemote, false));
    }

    /**
     * 启动Foreground服务
     */
    private void startForegroundService() {
        startAbility(getServiceIntent(false, true));
    }

    /**
     * 获取Intent
     *
     * @param isRemote            是否远程服务
     * @param isForegroundService 是否Foreground服务
     */
    private Intent getServiceIntent(boolean isRemote, boolean isForegroundService) {
        Operation operation;
        if (isRemote) {
            operation = new Intent.OperationBuilder().withDeviceId(getRemoteDeviceId())
                    .withBundleName(REMOTE_BUNDLE)
                    .withAbilityName(REMOTE_SERVICE)
                    .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                    .build();
        } else {
            operation = new Intent.OperationBuilder().withDeviceId("")
                    .withBundleName(LOCAL_BUNDLE)
                    .withAbilityName(isForegroundService ? FOREGROUND_SERVICE : NORMAL_SERVICE)
                    .build();
        }
        Intent intent = new Intent();
        intent.setOperation(operation);
        return intent;
    }


    /**
     * 连接服务
     *
     * @param isConnectRemote 是否连接远程服务
     */
    private void connectService(boolean isConnectRemote) {
        connectAbility(getServiceIntent(isConnectRemote, false), connection);
    }

    /**
     * 停止服务
     *
     * @param isStopRemote 是否停止远程服务
     */
    private void stopService(boolean isStopRemote) {
        stopAbility(getServiceIntent(isStopRemote, false));
    }

    private String getRemoteDeviceId() {
        List<DeviceInfo> infoList = DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ALL_DEVICE);
        if ((infoList == null) || (infoList.size() == 0)) {
            return "";
        }
        int random = new SecureRandom().nextInt(infoList.size());
        return infoList.get(random).getDeviceId();
    }

    private IAbilityConnection connection = new IAbilityConnection() {
        @Override
        public void onAbilityConnectDone(ElementName elementName, IRemoteObject iRemoteObject, int resultCode) {
            HiLog.info(LABEL_LOG, "%{public}s", "onAbilityConnectDone resultCode : " + resultCode);
            eventHandler.sendEvent(EVENT_ABILITY_CONNECT_DONE);
            RemoteAgentProxy remoteAgentProxy = new RemoteAgentProxy(iRemoteObject);
            try {
                remoteAgentProxy.setRemoteObject("This param from client");
            } catch (RemoteException e) {
                HiLog.error(LABEL_LOG, "%{public}s", "onAbilityConnectDone RemoteException");
            }
        }

        @Override
        public void onAbilityDisconnectDone(ElementName elementName, int resultCode) {
            HiLog.info(LABEL_LOG, "%{public}s", "onAbilityDisconnectDone resultCode : " + resultCode);
            eventHandler.sendEvent(EVENT_ABILITY_DISCONNECT_DONE);
        }
    };

    private void showTips(Context context, String msg) {
        new ToastDialog(context).setText(msg).show();
    }
}
