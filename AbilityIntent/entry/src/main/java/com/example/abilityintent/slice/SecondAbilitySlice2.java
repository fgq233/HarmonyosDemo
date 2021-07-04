
package com.example.abilityintent.slice;

import com.example.abilityintent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;

/**
 * Second Ability Slice2
 */
public class SecondAbilitySlice2 extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_second2);

        Component setBtn = findComponentById(ResourceTable.Id_setBtn);
        setBtn.setClickedListener(component -> setVal());
    }


    /**
     * 设置值
     */
    private void setVal() {
        Intent intent = new Intent();
        intent.setParam("backkey", "SecondAbilitySlice2返回值");
        this.setResult(intent);
    }

}
