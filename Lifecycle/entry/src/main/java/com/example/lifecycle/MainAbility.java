package com.example.lifecycle;

import com.example.lifecycle.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        System.out.println("★MainAbility:onStart()");
    }

    @Override
    protected void onActive() {
        super.onActive();
        System.out.println("★MainAbility:onActive()");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        System.out.println("★MainAbility:onInactive()");
    }

    @Override
    protected void onBackground() {
        super.onBackground();
        System.out.println("★MainAbility:onBackground()");
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        System.out.println("★MainAbility:onForeground()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("★MainAbility:onStop()");
    }

}
