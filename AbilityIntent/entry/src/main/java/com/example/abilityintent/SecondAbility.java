package com.example.abilityintent;

import com.example.abilityintent.slice.SecondAbilitySlice1;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * Second Ability
 */
public class SecondAbility extends Ability {
    private static final String ABILITY_2_VALUE = "SecondAbility2返回值";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SecondAbilitySlice1.class.getName());
    }

    @Override
    protected void onActive() {
        super.onActive();
        Intent intent = new Intent();
        intent.setParam("backkey", ABILITY_2_VALUE);
        setResult(0, intent);
    }
}
