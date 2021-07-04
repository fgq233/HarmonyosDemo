package com.example.abilityintent.slice;

import com.example.abilityintent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

import java.util.HashSet;
import java.util.Set;

public class MainAbilitySlice extends AbilitySlice {

    private Text mainText;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Component btn2 = findComponentById(ResourceTable.Id_btn2);
        btn2.setClickedListener(component -> startSecondAbility());

        Component btn3 = findComponentById(ResourceTable.Id_btn3);
        btn3.setClickedListener(component -> startThirdAbility());

        mainText = (Text) findComponentById(ResourceTable.Id_main_text);
    }

    /**
     * 跳转到 Second Ability
     */
    private void startSecondAbility() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withDeviceId("")
                .withBundleName("com.example.abilityintent")
                .withAbilityName("SecondAbility")
                .build();
        intent.setOperation(operation);
        intent.setParam("key2", "来自MainAbility的值A");
        startAbilityForResult(intent, 1);
    }

    /**
     * 跳转到 Third Ability
     */
    private void startThirdAbility() {
        Intent intent = new Intent();
        Set<String> entries = new HashSet<>();
        entries.add("com.demo.ENTITY3");
        Operation operation = new Intent.OperationBuilder().withDeviceId("")
                .withAction("com.demo.thrid.ACTION3")
                .withEntities(entries)
                .build();
        intent.setOperation(operation);
        intent.setParam("key3", "来自MainAbility的值B");
        startAbilityForResult(intent, 2);
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode != 0 || resultData == null) {
            return;
        }
        String result = resultData.getStringParam("backkey");
        mainText.setText(result);
    }
}
