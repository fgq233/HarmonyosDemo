package com.example.abilityintent.slice;

import com.example.abilityintent.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;

/**
 * Third Ability Slice
 */
public class ThirdAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_thrid);

        Component backBtn = findComponentById(ResourceTable.Id_backBtn);
        backBtn.setClickedListener(component -> terminate());

        Text text = (Text) findComponentById(ResourceTable.Id_third_text);
        text.setText(intent.getStringParam("key3"));
    }
}
