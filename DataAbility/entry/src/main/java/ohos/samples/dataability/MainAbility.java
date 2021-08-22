package ohos.samples.dataability;

import ohos.samples.dataability.slice.MainAbilitySlice;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.security.SystemPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * MainAbility
 */
public class MainAbility extends Ability {
    private static final String TAG = MainAbility.class.getSimpleName();

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, TAG);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        requestPermissions();
    }

    private void requestPermissions() {
        if (verifySelfPermission(SystemPermission.WRITE_USER_STORAGE) != IBundleManager.PERMISSION_GRANTED) {
            requestPermissionsFromUser(new String[] {SystemPermission.WRITE_USER_STORAGE}, 0);
        }
    }

    @Override
    public void onRequestPermissionsFromUserResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || permissions.length == 0 || grantResults == null || grantResults.length == 0) {
            return;
        }
        if (requestCode == 0) {
            if (grantResults[0] == IBundleManager.PERMISSION_GRANTED) {
                writeToDisk();
            }
        }
    }

    private void writeToDisk() {
        String rawFilePath = "entry/resources/rawfile/userdataability.txt";
        String externalFilePath = getFilesDir() + "/userdataability.txt";
        File file = new File(externalFilePath);
        if (file.exists()) {
            return;
        }
        RawFileEntry rawFileEntry = getResourceManager().getRawFileEntry(rawFilePath);
        HiLog.info(LABEL_LOG, "%{public}s", externalFilePath);
        try (FileOutputStream outputStream = new FileOutputStream(new File(externalFilePath))) {
            Resource resource = rawFileEntry.openRawFile();
            // cache length
            byte[] cache = new byte[1024];
            int len = resource.read(cache);
            while (len != -1) {
                outputStream.write(cache, 0, len);
                len = resource.read(cache);
            }
        } catch (IOException exception) {
            HiLog.error(LABEL_LOG, "%{public}s", "writeToDisk: IOException");
        }
    }
}
