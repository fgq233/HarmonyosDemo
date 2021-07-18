package com.example.lifecycle;

import com.example.lifecycle.slice.DemoAbilitySlice;
import com.example.lifecycle.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DemoAbility extends Ability {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DemoAbilitySlice.class.getName());
        System.out.println("★DemoAbility:onStart()");
    }

    @Override
    protected void onActive() {
        super.onActive();
        System.out.println("★DemoAbility:onActive()");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        System.out.println("★DemoAbility:onInactive()");
    }

    @Override
    protected void onBackground() {
        super.onBackground();
        System.out.println("★DemoAbility:onBackground()");
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        System.out.println("★DemoAbility:onForeground()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("★DemoAbility:onStop()");
    }

}
