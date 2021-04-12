package com.target.targetcasestudy.baseDi


object BaseInjector {

    private lateinit var baseComponent: BaseComponent

    fun getBaseComponent(): BaseComponent {

        baseComponent = if (BaseInjector::baseComponent.isInitialized ) baseComponent else DaggerBaseComponent.builder()
                .baseModule(BaseModule()).build()
        return baseComponent
    }
}