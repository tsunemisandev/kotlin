package com.company.jmixhello.view.step

import com.company.jmixhello.entity.Step
import com.company.jmixhello.view.main.MainView
import com.vaadin.flow.router.Route
import io.jmix.flowui.view.*


@Route(value = "steps", layout = MainView::class)
@ViewController(id = "tsu_Step.list")
@ViewDescriptor(path = "step-list-view.xml")
@LookupComponent("stepsDataGrid")
@DialogMode(width = "64em")
class StepListView : StandardListView<Step>() {
}