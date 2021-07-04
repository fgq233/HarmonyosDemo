package com.example.abilityintent;

import com.example.abilityintent.slice.ThirdAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * Third Ability
 */
public class ThirdAbility extends Ability {

    private static final String ABILITY_3_VALUE = "SecondAbility3返回值";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ThirdAbilitySlice.class.getName());
    }

    @Override
    protected void onActive() {
        super.onActive();
        Intent intent = new Intent();
        intent.setParam("backkey", ABILITY_3_VALUE);
        setResult(0, intent);
    }
}
