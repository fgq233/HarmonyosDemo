
package com.example.abilityintent.slice;

import com.example.abilityintent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

/**
 * Second Ability Slice1
 */
public class SecondAbilitySlice1 extends AbilitySlice {

    private Text text2;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_second1);

        Component backBtn = findComponentById(ResourceTable.Id_backBtn);
        backBtn.setClickedListener(component -> terminate());

        Text text1 = (Text) findComponentById(ResourceTable.Id_second_text1);
        text1.setText(intent.getStringParam("key2"));

        Component startBtn = findComponentById(ResourceTable.Id_startBtn);
        startBtn.setClickedListener(component -> presentSecondAbilitySlice2());
        text2 = (Text) findComponentById(ResourceTable.Id_second_text2);
    }


    /**
     * 跳转到 SecondAbilitySlice2
     */
    private void presentSecondAbilitySlice2() {
        presentForResult(new SecondAbilitySlice2(), new Intent(), 0);
    }



    @Override
    protected void onResult(int requestCode, Intent resultIntent) {
        if (requestCode == 0) {
            String result = resultIntent.getStringParam("backkey");
            text2.setText(result);
        }
    }

}
