package com.company.jmixhello.view.step

import com.company.jmixhello.entity.Step
import com.company.jmixhello.view.main.MainView
import com.vaadin.flow.router.Route
import io.jmix.flowui.view.EditedEntityContainer
import io.jmix.flowui.view.StandardDetailView
import io.jmix.flowui.view.ViewController
import io.jmix.flowui.view.ViewDescriptor

@Route(value = "steps/:id", layout = MainView::class)
@ViewController(id = "tsu_Step.detail")
@ViewDescriptor(path = "step-detail-view.xml")
@EditedEntityContainer("stepDc")
class StepDetailView : StandardDetailView<Step>() {
}