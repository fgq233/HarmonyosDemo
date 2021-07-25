package ohos.samples.serviceability;


import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.IRemoteObject;
import ohos.rpc.RemoteException;

/**
 * RemoteAbility
 */
public class RemoteAbility extends Ability {

    private static final String TAG = RemoteAbility.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, TAG);
    private static final String DESCRIPTOR = "ohos.samples.serviceability.RemoteAbility";

    RemoteAgentStub remoteAgentStub = new RemoteAgentStub(DESCRIPTOR) {
        @Override
        public void setRemoteObject(String param) throws RemoteException {
            showTips(RemoteAbility.this, param);
        }
    };

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "%{public}s",  "onStart");
        showTips(this, "RemoteService onStart");
    }

    @Override
    protected void onCommand(Intent intent, boolean restart, int startId) {
        super.onCommand(intent, restart, startId);
        showTips(this, "RemoteService onCommand");
    }

    @Override
    protected IRemoteObject onConnect(Intent intent) {
        showTips(RemoteAbility.this, "RemoteService onConnect");
        return remoteAgentStub;
    }

    @Override
    public void onDisconnect(Intent intent) {
        super.onDisconnect(intent);
        showTips(RemoteAbility.this, "RemoteService onDisconnect");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showTips(this, "RemoteService onStop");
    }

    private void showTips(Context context, String msg) {
        new ToastDialog(context).setText(msg).show();
    }
}
